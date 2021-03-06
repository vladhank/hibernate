package pojos;

import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
public class CreditCard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
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
    @Column(name = "CASHBACK")
    private int cashBack;
    @Column(name = "CARD_STATUS", nullable = false)
    private String status;
    @ManyToOne
//    @Column(name = "BANK_ACCOUNT", nullable = false)
    private Bank bankAccount;
    @OneToMany(mappedBy = "creditCard",orphanRemoval = true)
    @Column(name ="TRANSACTIONS",nullable = true)
    private List<Transaction> transactionList;
    @ManyToOne
//   @JoinColumn(name = "CLIENT_ID",nullable = false)
    private Client client;

    public CreditCard(long cardNumber, String cardCompany, String firstName, String lastName, LocalDate expDate, int cvv, int pin, int cashBack, String status, Bank bankAccount, List<Transaction> transactionList, Client client) {
        this.cardNumber = cardNumber;
        this.cardCompany = cardCompany;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expDate = expDate;
        this.cvv = cvv;
        this.pin = pin;
        this.cashBack = cashBack;
        this.status = status;
        this.bankAccount = bankAccount;
        this.transactionList = transactionList;
        this.client = client;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber=" + cardNumber +
                ", cardCompany='" + cardCompany + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", expDate=" + expDate +
                ", cvv=" + cvv +
                ", pin=" + pin +
                ", cashBack=" + cashBack +
                ", status='" + status + '\'' +
                '}';
    }

}