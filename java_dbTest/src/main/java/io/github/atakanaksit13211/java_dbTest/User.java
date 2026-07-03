package io.github.atakanaksit13211.java_dbTest;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "user", schema = "public")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   user_id;

    private  String user_name;
    private  String password_salt;
    private  String password_hash;
    private  String email_address;


    User() {}

    User(
            long user_id,
            String user_name,
            String password_salt,
            String password_hash,
            String email_address
            ) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password_salt = password_salt;
        this.password_hash = password_hash;
        this.email_address = email_address;
    }



    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword_salt() {
        return password_salt;
    }

    public void setPassword_salt(String password_salt) {
        this.password_salt = password_salt;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (Objects.equals(getUser_id(), user.getUser_id()) &&
                Objects.equals(getUser_name(), user.getUser_name()) &&
                Objects.equals(getPassword_salt(), user.getPassword_salt()) &&
                Objects.equals(getPassword_hash(), user.getPassword_hash()) &&
                Objects.equals(getEmail_address(), user.getEmail_address())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getUser_name(), getPassword_salt(), getPassword_hash(), getEmail_address());
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + getUser_id() +
                ", user_name='" + getUser_name() + '\'' +
                ", password_salt='" + getPassword_salt() + '\'' +
                ", password_hash='" + getPassword_hash() + '\'' +
                ", email_address='" + getEmail_address() + '\'' +
                '}';
    }
}
