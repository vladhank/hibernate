package pojos;

import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor

@EqualsAndHashCode
@ToString
@Entity
public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private long accountID;
    @Column(name = "BANK_ACCOUNT",unique = true,nullable = false)
    private String bankAccount;
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "CLIENT_ID",nullable = false)
    private Client client;
    @Column(name = "ACCOUNT_BALANCE", nullable = false)
    @Check(constraints = "BALANCE>0")
    private double balance;

    public Bank(String bankAccount, Client client, double balance) {
        this.bankAccount = bankAccount;
        this.client = client;
        this.balance = balance;
    }
}
