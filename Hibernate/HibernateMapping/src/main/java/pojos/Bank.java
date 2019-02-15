package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Bank implements Serializable {
    @Id
    @GeneratedValue
    private long accountID;
    @Column
    private String bankAccount;
    @Column
    private long clientID;
    @Column
    @Check(constraints = "BALANCE>0")
    private double balance;

    public Bank(String bankAccount, long clientID, double balance) {
        this.bankAccount = bankAccount;
        this.clientID = clientID;
        this.balance = balance;
    }
}
