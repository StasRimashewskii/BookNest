const axios = require('axios');

const googleBooksController = {
  async searchBooks(req, res) {
    try {
      const { query, isbn } = req.query;
      console.log('Search request:', { query, isbn }); // Логируем запрос

      if (!query && !isbn) {
        return res.status(400).json({ error: 'Query or ISBN is required' });
      }

      let searchUrl = 'https://www.googleapis.com/books/v1/volumes?q=';
      
      if (isbn) {
        searchUrl += `isbn:${isbn}`;
      } else {
        searchUrl += encodeURIComponent(query);
      }

      searchUrl += '&maxResults=20&printType=books&langRestrict=ru';

      console.log('Google Books API URL:', searchUrl); // Логируем URL

      const response = await axios.get(searchUrl);
      console.log('Google Books response items:', response.data.items?.length); // Логируем количество результатов

      const books = response.data.items ? response.data.items.map(item => {
        const volumeInfo = item.volumeInfo;
        return {
          id: item.id,
          title: volumeInfo.title || 'Неизвестное название',
          authors: volumeInfo.authors || ['Неизвестный автор'],
          publishedDate: volumeInfo.publishedDate,
          description: volumeInfo.description,
          isbn: volumeInfo.industryIdentifiers ? 
            volumeInfo.industryIdentifiers.find(id => id.type === 'ISBN_13')?.identifier || 
            volumeInfo.industryIdentifiers[0]?.identifier : null,
          pageCount: volumeInfo.pageCount,
          categories: volumeInfo.categories,
          imageLinks: volumeInfo.imageLinks,
          language: volumeInfo.language
        };
      }) : [];

      res.json({ books });
    } catch (error) {
      console.error('Google Books API error:', error.response?.data || error.message);
      res.status(500).json({ 
        error: 'Failed to fetch books from Google Books API',
        details: error.response?.data || error.message
      });
    }
  }
};

module.exports = googleBooksController;