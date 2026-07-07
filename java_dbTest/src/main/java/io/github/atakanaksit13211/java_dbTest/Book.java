package io.github.atakanaksit13211.java_dbTest;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "public")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   book_id;

    private  String title;
    private  String author;
    private  String isbn;
    private  String publisher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book") // books get borrowed multiple times, and we want to see the past too.
    @JsonBackReference
    private List<Borrowing> borrowing;


    public Book(Long book_id, String title, String author, String isbn, String publisher) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public Book() {
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Borrowing> getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(List<Borrowing> borrowing) {
        this.borrowing = borrowing;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return (Objects.equals(getBook_id(), book.getBook_id()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getIsbn(), book.getIsbn()) &&
                Objects.equals(getPublisher(), book.getPublisher())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBook_id(), getTitle(), getAuthor(), getIsbn(), getPublisher());
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + getBook_id() +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", publisher='" + getPublisher() + '\'' +
                '}';
    }
}
