package pojos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
//-----------Dirty------------
@DynamicUpdate
@SelectBeforeUpdate
@OptimisticLocking(type = OptimisticLockType.DIRTY)
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private long transactionID;
    @Column(name = "CARD_NUMMBER", nullable = false)
    private long cardNumber;
    @Column(name = "TRANSACTION_TYPE", nullable = false)
    private String transactionType;
    @Column(name = "AMOUNT_OF_MONEY", nullable = false)
    private double amountMoney;
    @Column(name = "TRANSACTION_TIME", nullable = false)
    private LocalDateTime transactionTime;
    @ManyToOne
    private CreditCard creditCard;

    public Transaction(long cardNumber, String transactionType, double amountMoney, LocalDateTime transactionTime, CreditCard creditCard) {
        this.cardNumber = cardNumber;
        this.transactionType = transactionType;
        this.amountMoney = amountMoney;
        this.transactionTime = transactionTime;
        this.creditCard = creditCard;
    }
}
