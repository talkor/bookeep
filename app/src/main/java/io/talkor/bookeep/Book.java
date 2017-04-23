package io.talkor.bookeep;

/**
 * Created by Tal on 10/04/2017.
 */

public class Book {

    private String bookID;
    private String bookName;
    private String bookAuthor;
    private String bookPages;
    private String bookGenre;
    private String bookProgress;


    public Book() {

    }

    public Book(String bookID, String bookName, String bookAuthor, String bookPages, String bookGenre, String bookProgress) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPages = bookPages;
        this.bookGenre = bookGenre;
        this.bookProgress = bookProgress;
    }

    public String getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookPages() {
        return bookPages;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public String getBookProgress() {
        return bookProgress;
    }
}


/*

    BookName
    BookAuthor
    BookGenre
    BookPicture
    BookPages
    BookYear
    BookMark
    BookRating
 */