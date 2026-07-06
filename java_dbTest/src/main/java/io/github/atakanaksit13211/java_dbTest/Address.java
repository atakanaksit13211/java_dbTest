package io.github.atakanaksit13211.java_dbTest;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "address", schema = "public")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   address_id;

    private  String address;
    private  String city;
    private  String county;
    private  String post_code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    @JsonBackReference // prevent infinite recursion as borrowings will also try to print users
    private List<User> users;

    public Address(Long address_id, String address, String city, String county, String post_code) {
        this.address_id = address_id;
        this.address = address;
        this.city = city;
        this.county = county;
        this.post_code = post_code;
    }

    public Address() {
    }

    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return (Objects.equals(getAddress_id(), address1.getAddress_id()) &&
                Objects.equals(getAddress(), address1.getAddress()) &&
                Objects.equals(getCity(), address1.getCity()) &&
                Objects.equals(getCounty(), address1.getCounty()) &&
                Objects.equals(getPost_code(), address1.getPost_code())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress_id(), getAddress(), getCity(), getCounty(), getPost_code());
    }

    @Override
    public String toString() {
        return "Address{" +
                "address_id=" + getAddress_id() +
                ", address='" + getAddress() + '\'' +
                ", city='" + getCity() + '\'' +
                ", county='" + getCounty() + '\'' +
                ", post_code='" + getPost_code() + '\'' +
                '}';
    }
}
