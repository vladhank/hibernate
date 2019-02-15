package pojos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private long clientID;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    @Access(AccessType.FIELD)
    @Column(name = "LOGIN", nullable = false, unique = true,insertable = true,updatable = false)
    private String login;

    @Access(AccessType.PROPERTY)
    @Column(name="PASSWORD",nullable = false)
    private String password;


    public Client(String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.login = login;
        this.password = password;
    }
}
