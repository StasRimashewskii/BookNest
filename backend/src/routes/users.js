const express = require('express');
const userController = require('../controllers/userController');
const authMiddleware = require('../middleware/auth');

const router = express.Router();

// Все роуты требуют аутентификации
router.use(authMiddleware);

// Профиль
router.get('/profile', userController.getProfile);
router.put('/profile', userController.updateProfile);

// Пароль
router.put('/password', userController.changePassword);

// Статистика
router.get('/stats', userController.getStats);

// Удаление аккаунта
router.delete('/account', userController.deleteAccount);

module.exports = router;