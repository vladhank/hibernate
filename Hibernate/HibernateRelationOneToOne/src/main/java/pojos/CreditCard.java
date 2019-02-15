package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
public class CreditCard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CARD_NUMBER")
    private long cardNumber;
    // VISA  or MasterCard
    @Column(name = "CARD_COMPANY",nullable = false)
    private String cardCompany;
    @Column(name = "FIRST_NAME",nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME",nullable = false)
    private String lastName;
    @Column(name = "EXP_DATE",nullable = false)
    private LocalDate expDate;
    @Column(name = "CVV",nullable = false)
    private int cvv;
    @Column(name = "PIN",nullable = false)
    private int pin;
    @Column(name = "CASHBACK")
    private int cashBack;
    @Column(name = "CARD_STATUS",nullable = false)
    private String status;
    @Column(name = "BANK_ACCOUNT",nullable = false)
    private String bankAccount;
    @Column(name = "CLIENT_ID",nullable = false)
    private long clientID;

}