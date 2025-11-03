const { prisma } = require('../utils/db');
const bookController = {
  // Получение всех книг пользователя
  async getBooks(req, res) {
    try {
      const { status, search, page = 1, limit = 20 } = req.query;
      const skip = (page - 1) * limit;

      let where = { ownerId: req.user.id };

      // Фильтрация по статусу
      if (status && ['WANT_TO_READ', 'READING', 'READ'].includes(status)) {
        where.status = status;
      }

      // Поиск по названию и автору
      if (search) {
        where.OR = [
          { title: { contains: search, mode: 'insensitive' } },
          { author: { contains: search, mode: 'insensitive' } },
          { isbn: { contains: search } }
        ];
      }

      const books = await prisma.book.findMany({
        where,
        skip,
        take: parseInt(limit),
        orderBy: { createdAt: 'desc' },
        select: {
          id: true,
          title: true,
          author: true,
          coverImage: true,
          status: true,
          year: true,
          genre: true,
          createdAt: true
        }
      });

      const total = await prisma.book.count({ where });

      res.json({
        books,
        pagination: {
          page: parseInt(page),
          limit: parseInt(limit),
          total,
          pages: Math.ceil(total / limit)
        }
      });
    } catch (error) {
      console.error('Get books error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Получение одной книги
  async getBook(req, res) {
    try {
      const { id } = req.params;

      const book = await prisma.book.findFirst({
        where: {
          id: parseInt(id),
          ownerId: req.user.id
        }
      });

      if (!book) {
        return res.status(404).json({ error: 'Book not found' });
      }

      res.json({ book });
    } catch (error) {
      console.error('Get book error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Создание книги
  async createBook(req, res) {
    try {
      const {
        title,
        author,
        description,
        coverImage,
        isbn,
        year,
        genre,
        status,
        personalNotes
      } = req.body;

      if (!title || !author) {
        return res.status(400).json({ error: 'Title and author are required' });
      }

      const book = await prisma.book.create({
        data: {
          title,
          author,
          description: description || null,
          coverImage: coverImage || null,
          isbn: isbn || null,
          year: year ? parseInt(year) : null,
          genre: genre || null,
          status: status || 'WANT_TO_READ',
          personalNotes: personalNotes || null,
          ownerId: req.user.id
        }
      });

      res.status(201).json({
        message: 'Book created successfully',
        book
      });
    } catch (error) {
      console.error('Create book error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Обновление книги
  async updateBook(req, res) {
    try {
      const { id } = req.params;
      const updateData = req.body;

      // Проверяем существование книги и владельца
      const existingBook = await prisma.book.findFirst({
        where: {
          id: parseInt(id),
          ownerId: req.user.id
        }
      });

      if (!existingBook) {
        return res.status(404).json({ error: 'Book not found' });
      }

      const book = await prisma.book.update({
        where: { id: parseInt(id) },
        data: updateData
      });

      res.json({
        message: 'Book updated successfully',
        book
      });
    } catch (error) {
      console.error('Update book error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  },

  // Удаление книги
  async deleteBook(req, res) {
    try {
      const { id } = req.params;

      const existingBook = await prisma.book.findFirst({
        where: {
          id: parseInt(id),
          ownerId: req.user.id
        }
      });

      if (!existingBook) {
        return res.status(404).json({ error: 'Book not found' });
      }

      await prisma.book.delete({
        where: { id: parseInt(id) }
      });

      res.json({ message: 'Book deleted successfully' });
    } catch (error) {
      console.error('Delete book error:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  }
};

module.exports = bookController;