package io.github.atakanaksit13211.java_dbTest;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "borrowing", schema = "public")
@JsonPropertyOrder({ "borrowing_id"})
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          borrowing_id;

    private ZonedDateTime borrowed_timestamp;
    private ZonedDateTime due_timestamp;

    private boolean       returned;

    @ManyToOne
    @JoinColumn(name = "book_id")
    //@JsonManagedReference
    private Book book;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //@JsonManagedReference //prevent infinite recursion as user will also try to print borrowings
    private User user;

    public Borrowing(Long borrowing_id, ZonedDateTime borrowed_timestamp, ZonedDateTime due_timestamp, boolean returned, Book book, User user) {
        this.borrowing_id = borrowing_id;
        this.borrowed_timestamp = borrowed_timestamp;
        this.due_timestamp = due_timestamp;
        this.returned = returned;
        this.book = book;
        this.user = user;
    }

    public Borrowing() {
    }

    public Long getBorrowing_id() {
        return borrowing_id;
    }

    public void setBorrowing_id(Long borrowing_id) {
        this.borrowing_id = borrowing_id;
    }

    public ZonedDateTime getBorrowed_timestamp() {
        return borrowed_timestamp;
    }

    public void setBorrowed_timestamp(ZonedDateTime borrowed_timestamp) {
        this.borrowed_timestamp = borrowed_timestamp;
    }

    public ZonedDateTime getDue_timestamp() {
        return due_timestamp;
    }

    public void setDue_timestamp(ZonedDateTime due_timestamp) {
        this.due_timestamp = due_timestamp;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }





    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Borrowing borrowing = (Borrowing) o;
        return (Objects.equals(getBorrowing_id(), borrowing.getBorrowing_id()) &&
                Objects.equals(getBorrowed_timestamp(), borrowing.getBorrowed_timestamp()) &&
                Objects.equals(getDue_timestamp(), borrowing.getDue_timestamp()) &&
                Objects.equals(getBook(), borrowing.getBook()) &&
                Objects.equals(getUser(), borrowing.getUser())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBorrowing_id(), getBorrowed_timestamp(), getDue_timestamp(), getBook(), getUser());
    }


    @Override
    public String toString() {
        return "Borrowing{" +
                "borrowing_id=" + getBorrowing_id() +
                ", borrowed_timestamp=" + getBorrowed_timestamp() +
                ", due_timestamp=" + getDue_timestamp() +
                ", returned=" + isReturned() +
                ", book=" + getBook() +
                ", user=" + getUser() +
                '}';
    }
}
