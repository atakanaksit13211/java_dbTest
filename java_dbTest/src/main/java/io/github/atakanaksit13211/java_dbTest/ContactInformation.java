package io.github.atakanaksit13211.java_dbTest;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "contact_information", schema = "public")
public class ContactInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   contact_information_id;

    private  String primary_phone_number;
    private  String alternative_email;

    @OneToOne(mappedBy = "contact_information")
    private User user;


    public ContactInformation(String primary_phone_number, String alternative_email) {
        this.primary_phone_number = primary_phone_number;
        this.alternative_email = alternative_email;
    }

    public ContactInformation() {
    }


    public Long getContact_information_id() {
        return contact_information_id;
    }

    public void setContact_information_id(Long contact_information_id) {
        this.contact_information_id = contact_information_id;
    }

    public String getPrimary_phone_number() {
        return primary_phone_number;
    }

    public void setPrimary_phone_number(String primary_phone_number) {
        this.primary_phone_number = primary_phone_number;
    }

    public String getAlternative_email() {
        return alternative_email;
    }

    public void setAlternative_email(String alternative_email) {
        this.alternative_email = alternative_email;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactInformation that = (ContactInformation) o;
        return (Objects.equals(getContact_information_id(), that.getContact_information_id()) &&
                Objects.equals(getPrimary_phone_number(), that.getPrimary_phone_number()) &&
                Objects.equals(getAlternative_email(), that.getAlternative_email())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContact_information_id(), getPrimary_phone_number(), getAlternative_email());
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
                "contact_information_id=" + getContact_information_id() +
                ", primary_phone_number='" + getPrimary_phone_number() + '\'' +
                ", alternative_email='" + getAlternative_email() + '\'' +
                '}';
    }
}
