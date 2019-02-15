package inheritance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "CARD")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CARD_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("C")
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "CARD_NUMBER")
    private long cardNumber;
    // VISA  or MasterCard
    @Column(name = "CARD_COMPANY", nullable = false)
    private String cardCompany;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EXP_DATE", nullable = false)
    private LocalDate expDate;
    @Column(name = "CVV", nullable = false)
    private int cvv;
    @Column(name = "PIN", nullable = false)
    private int pin;
    @Column(name = "ANNUAL_FEE")
    private int annualFee;
    @Column(name = "CARD_STATUS", nullable = false)
    private String status;
    @Column(name = "BANK_ACCOUNT", nullable = false)
    private String bankAccount;
    @Column(name = "CLIENT_ID", nullable = false)
    private long clientID;

    public Card(long cardNumber, String cardCompany, String firstName, String lastName, LocalDate expDate, int cvv, int pin, int annualFee, String status, String bankAccount, long clientID) {
        this.cardNumber = cardNumber;
        this.cardCompany = cardCompany;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expDate = expDate;
        this.cvv = cvv;
        this.pin = pin;
        this.annualFee = annualFee;
        this.status = status;
        this.bankAccount = bankAccount;
        this.clientID = clientID;
    }
}
