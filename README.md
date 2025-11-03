# BookNest — менеджер личной коллекции книг (веб-сервис)

Веб-сервис для управления личной библиотекой книг с доступом из любого браузера.  
Позволяет добавлять книги, вести статусы чтения, искать по Google Books API и работать частично офлайн через PWA.

---

### Платформа
Веб (десктоп + мобильные браузеры)  
PWA (установка на главный экран, офлайн-доступ)

### Язык разработки
- **Фронтенд**: JavaScript (React.js / Vue.js)  
- **Бэкенд**: Node.js (Express) или Python (FastAPI)  

### Язык интерфейса
HTML, CSS (Tailwind CSS), JSX/Template  

---

## Документация

- [SRS веб-сервиса](https://github.com/StasRimashewskii/BookNest/blob/main/Requirements/SRS_Web.md)  
- [Макеты интерфейса](https://github.com/StasRimashewskii/BookNest/tree/main/Mockups)  
- [Диаграммы](https://github.com/StasRimashewskii/BookNest/tree/main/Diagrams)  

---

## Исходный код

- [Фронтенд (React/Vue + PWA)]()  
- [Бэкенд (REST API)]()  
- [База данных (миграции)]()  

---

## Шаблоны проектирования
- **MVC / MVVM** — разделение логики, представления и данных  
- **Repository Pattern** — абстракция над доступом к БД  
- **Service Layer** — бизнес-логика (поиск, статусы, валидация)  
- **Observer / Event Bus** — реактивные обновления UI  

---

## Технологии

| Слой | Технология |
|------|------------|
| Фронтенд | React + Vite / Vue 3 + Vite, Tailwind CSS, PWA (Workbox) |
| Бэкенд | Node.js (Express) + JWT / Python (FastAPI) + Pydantic |
| БД | PostgreSQL (с индексами по user_id, title, author) |
| API | Google Books API (поиск по ISBN/названию) |
| Хостинг | Vercel / Render / Railway + Supabase / Neon |
| Безопасность | HTTPS, CORS, rate limiting, helmet, bcrypt |

---

## Тестирование

- [План тестирования]()  
- [Юнит-тесты (Jest / PyTest)]()  
- [E2E-тесты (Playwright / Cypress)]()  
- [Результаты тестирования]()  

---

> **BookNest Web** — лёгкий, быстрый и доступный из любого браузера.  
> Никаких установок. Только книги. Только твоя коллекция.
