package pojos;

import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor

@EqualsAndHashCode
@ToString
@Entity
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.ALL)
public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String bankAccount;
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ACCOUNT_ID")
//    private long accountID;
//    @Column(name = "BANK_ACCOUNT",unique = true,nullable = false)
//    private String bankAccount;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;
    @Column(name = "ACCOUNT_BALANCE", nullable = false)
    @Check(constraints = "BALANCE>0")
    private double balance;
    @OneToMany (mappedBy = "bankAccount", orphanRemoval = true)
//    @OneToMany(orphanRemoval = true)
//            @JoinTable(
//                    name = "CARD_BALANCE",
//                    joinColumns = @JoinColumn(name = "ACCOUNT_BALANCE"),
//                    inverseJoinColumns = @JoinColumn(name = "CARD_NUMBER")
//            )
    List<CreditCard> cardList;

    public Bank(String bankAccount, Client client, double balance, List<CreditCard> cardList) {
        this.bankAccount = bankAccount;
        this.client = client;
        this.balance = balance;
        this.cardList = cardList;
    }
}
