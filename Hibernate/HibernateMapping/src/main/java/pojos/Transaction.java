package pojos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRANSACTION_ID")
    private long transactionID;
    @Column(name = "CARD_NUMMBER", nullable = false)
    private long cardNumber;
    @Column(name = "ACCOUNT_ID", nullable = false)
    private long accountID;
    @Column(name = "TRANSACTION_TYPE", nullable = false)
    private String transactionType;
    @Column(name = "AMOUNT_OF_MONEY", nullable = false)
    private double amountMoney;
    @Column(name = "DATE_AND_TIME", nullable = false)
    private LocalDateTime transactionTime;

    public Transaction(long cardNumber, long accountID, String transactionType, double amountMoney, LocalDateTime transactionTime) {
        this.cardNumber = cardNumber;
        this.accountID = accountID;
        this.transactionType = transactionType;
        this.amountMoney = amountMoney;
        this.transactionTime = transactionTime;
    }
}
