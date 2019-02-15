package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
//@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue
    private long clientID;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private String address;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private String login;
    @Column
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
