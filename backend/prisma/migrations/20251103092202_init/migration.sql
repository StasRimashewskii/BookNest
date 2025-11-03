/*
  Warnings:

  - You are about to drop the `Book` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `Loan` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `User` table. If the table is not empty, all the data it contains will be lost.

*/
-- CreateEnum
CREATE TYPE "BookStatus" AS ENUM ('WANT_TO_READ', 'READING', 'READ');

-- DropForeignKey
ALTER TABLE "public"."Book" DROP CONSTRAINT "Book_ownerId_fkey";

-- DropForeignKey
ALTER TABLE "public"."Loan" DROP CONSTRAINT "Loan_bookId_fkey";

-- DropForeignKey
ALTER TABLE "public"."Loan" DROP CONSTRAINT "Loan_userId_fkey";

-- DropTable
DROP TABLE "public"."Book";

-- DropTable
DROP TABLE "public"."Loan";

-- DropTable
DROP TABLE "public"."User";

-- CreateTable
CREATE TABLE "users" (
    "id" SERIAL NOT NULL,
    "email" TEXT NOT NULL,
    "password" TEXT NOT NULL,
    "name" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "books" (
    "id" SERIAL NOT NULL,
    "title" TEXT NOT NULL,
    "author" TEXT NOT NULL,
    "description" TEXT,
    "coverImage" TEXT,
    "isbn" TEXT,
    "year" INTEGER,
    "genre" TEXT,
    "status" "BookStatus" NOT NULL DEFAULT 'WANT_TO_READ',
    "personalNotes" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,
    "ownerId" INTEGER NOT NULL,

    CONSTRAINT "books_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "users_email_key" ON "users"("email");

-- AddForeignKey
ALTER TABLE "books" ADD CONSTRAINT "books_ownerId_fkey" FOREIGN KEY ("ownerId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;
