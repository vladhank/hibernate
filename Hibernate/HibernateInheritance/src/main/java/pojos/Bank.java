package pojos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long accountID;
    @Column(name = "BANK_ACCOUNT", nullable = false)
    private String bankAccount;
    @Column(name = "CLIENT_ID", nullable = false)
    private long clientID;
    @Column(name = "ACCOUNT_BALANCE", nullable = false)
    @Check(constraints = "BALANCE>0")
    private double balance;

    public Bank(String bankAccount, long clientID, double balance) {
        this.bankAccount = bankAccount;
        this.clientID = clientID;
        this.balance = balance;
    }
}
