package pojos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "TRANSACTION_TIME", nullable = false)
    private LocalDateTime transactionTime;

    public Transaction(long cardNumber, long accountID, String transactionType, double amountMoney, LocalDateTime transactionTime) {
        this.cardNumber = cardNumber;
        this.accountID = accountID;
        this.transactionType = transactionType;
        this.amountMoney = amountMoney;
        this.transactionTime = transactionTime;
    }
}
