package com.knoldus.implement.book

import com.knoldus.models._
import com.knoldus.validator.BookValidator

import scala.collection.mutable.ListBuffer

class BookImplementation(titleValidate: BookValidator) extends BookRepo {
  private val listOfBook: ListBuffer[Book] = ListBuffer[Book]().empty

  override def create(book: Book): Either[Error, List[Book]] = {
    if (titleValidate.isBookValidated(book)) {
      val check = listOfBook.find(list => list.id == book.id || list.title == book.title)
      check match {
        case Some(_) => Left(AlreadyExist("Having similar book ID, Cannot create new Book"))
        case None =>
          Right((listOfBook += book).toList)
      }
    }
    else Left(NotValidated("Book length is more than 20 characters"))

  }

  override def get(id: Int): Option[Book] = {
    listOfBook.find(list => list.id == id)
  }

  override def getAll(): Either[Error, List[Book]] = {
    if (listOfBook.isEmpty) Left(EmptyList("List of Book is Empty"))
    else Right(listOfBook.toList)
  }

  override def put(book: Book): Either[Error, Unit] = {
    val check = listOfBook.find(list => list.id == book.id)
    check match {
      case Some(_) =>
        listOfBook.map { list =>
          if (list.id == book.id) book
          else list
        }
        Right(println("Updated"))
      case None => Left(IdMisMatch("The Book ID is not similar to exist Book ID"))
    }
  }

  override def delete(id: Int): Option[List[Book]] = {
    val check = listOfBook.find(list => list.id == id)
    check match {
      case Some(value) => Some((listOfBook -= value).toList)
      case None => None
    }
  }
}

//object MainApplication extends App {
//  val book = Book(1212, "Scala Fundamentals", 230, 21431232, 101)
//  val newBook = book.copy(112, "Java", 5000)
//  val createBook = new BookImplementation(new BookValidator)
//  createBook.create(book)
//  createBook.create(newBook)
//  val result = createBook.delete(1212)
//  println(result)
//
//}