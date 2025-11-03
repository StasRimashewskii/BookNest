const { PrismaClient } = require('@prisma/client');

const prisma = new PrismaClient();

// Функция для проверки подключения к базе
const checkDatabaseConnection = async () => {
  try {
    await prisma.$queryRaw`SELECT 1`;
    console.log('✅ Database connection established');
    return true;
  } catch (error) {
    console.error('❌ Database connection failed:', error);
    return false;
  }
};

// Функция для graceful shutdown
const disconnectDatabase = async () => {
  await prisma.$disconnect();
};

module.exports = {
  prisma,
  checkDatabaseConnection,
  disconnectDatabase
};