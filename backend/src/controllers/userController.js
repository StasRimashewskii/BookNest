const { prisma } = require('../utils/db');
const bcrypt = require('bcryptjs');

const userController = {
  // Получение профиля пользователя
  async getProfile(req, res) {
    try {
      const user = await prisma.user.findUnique({
        where: { id: req.user.id },
        select: {
          id: true,
          email: true,
          name: true,
          createdAt: true,
          updatedAt: true,
          _count: {
            select: {
              books: true
            }
          }
        }
      });

      if (!user) {
        return res.status(404).json({ error: 'User not found' });
      }

      res.json({
        message: 'Profile retrieved successfully',
        user
      });
    } catch (error) {
      console.error('Get profile error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Обновление профиля пользователя
  async updateProfile(req, res) {
    try {
      const { name, email } = req.body;

      // Проверяем, не занят ли email другим пользователем
      if (email && email !== req.user.email) {
        const existingUser = await prisma.user.findUnique({
          where: { email }
        });

        if (existingUser) {
          return res.status(400).json({ error: 'Email is already taken' });
        }
      }

      const updatedUser = await prisma.user.update({
        where: { id: req.user.id },
        data: {
          ...(name && { name }),
          ...(email && { email })
        },
        select: {
          id: true,
          email: true,
          name: true,
          createdAt: true,
          updatedAt: true
        }
      });

      res.json({
        message: 'Profile updated successfully',
        user: updatedUser
      });
    } catch (error) {
      console.error('Update profile error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Смена пароля
  async changePassword(req, res) {
    try {
      const { currentPassword, newPassword } = req.body;

      if (!currentPassword || !newPassword) {
        return res.status(400).json({ 
          error: 'Current password and new password are required' 
        });
      }

      if (newPassword.length < 6) {
        return res.status(400).json({ 
          error: 'New password must be at least 6 characters long' 
        });
      }

      // Получаем пользователя с паролем
      const user = await prisma.user.findUnique({
        where: { id: req.user.id }
      });

      if (!user) {
        return res.status(404).json({ error: 'User not found' });
      }

      // Проверяем текущий пароль
      const isCurrentPasswordValid = await bcrypt.compare(
        currentPassword, 
        user.password
      );

      if (!isCurrentPasswordValid) {
        return res.status(401).json({ error: 'Current password is incorrect' });
      }

      // Хэшируем новый пароль
      const saltRounds = 12;
      const hashedNewPassword = await bcrypt.hash(newPassword, saltRounds);

      // Обновляем пароль
      await prisma.user.update({
        where: { id: req.user.id },
        data: { password: hashedNewPassword }
      });

      res.json({ message: 'Password changed successfully' });
    } catch (error) {
      console.error('Change password error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Получение статистики пользователя
  async getStats(req, res) {
    try {
      const stats = await prisma.user.findUnique({
        where: { id: req.user.id },
        select: {
          _count: {
            select: {
              books: true
            }
          },
          books: {
            select: {
              status: true
            }
          }
        }
      });

      if (!stats) {
        return res.status(404).json({ error: 'User not found' });
      }

      // Подсчитываем книги по статусам
      const statusCounts = {
        WANT_TO_READ: 0,
        READING: 0,
        READ: 0
      };

      stats.books.forEach(book => {
        statusCounts[book.status]++;
      });

      res.json({
        message: 'Stats retrieved successfully',
        stats: {
          totalBooks: stats._count.books,
          byStatus: statusCounts
        }
      });
    } catch (error) {
      console.error('Get stats error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Удаление аккаунта
  async deleteAccount(req, res) {
    try {
      const { password } = req.body;

      if (!password) {
        return res.status(400).json({ error: 'Password is required' });
      }

      // Получаем пользователя с паролем
      const user = await prisma.user.findUnique({
        where: { id: req.user.id }
      });

      if (!user) {
        return res.status(404).json({ error: 'User not found' });
      }

      // Проверяем пароль
      const isPasswordValid = await bcrypt.compare(password, user.password);
      if (!isPasswordValid) {
        return res.status(401).json({ error: 'Password is incorrect' });
      }

      // Удаляем пользователя (каскадно удалятся все его книги)
      await prisma.user.delete({
        where: { id: req.user.id }
      });

      res.json({ message: 'Account deleted successfully' });
    } catch (error) {
      console.error('Delete account error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  }
};

module.exports = userController;