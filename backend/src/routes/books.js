const express = require('express');
const bookController = require('../controllers/bookController');
const googleBooksController = require('../controllers/googleBooksController');
const authMiddleware = require('../middleware/auth');

const router = express.Router();

// Все роуты требуют аутентификации
router.use(authMiddleware);

router.get('/', bookController.getBooks);
router.get('/search/external', googleBooksController.searchBooks); // Новый роут для Google Books
router.get('/:id', bookController.getBook);
router.post('/', bookController.createBook);
router.put('/:id', bookController.updateBook);
router.delete('/:id', bookController.deleteBook);

module.exports = router;