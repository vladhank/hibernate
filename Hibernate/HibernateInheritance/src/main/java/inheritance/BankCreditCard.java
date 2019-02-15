package inheritance;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity @Table(name = "CREDIT_CARD")
@DiscriminatorValue("CC")
public class BankCreditCard extends Card{
    private static final long serialVersionUID=2L;
    @Column(name="CASH_REWARD")
    private int cashReward;
    @Column(name="POINTS_REWARD")
    private int pointsReward;

    public BankCreditCard( long cardNumber, String cardCompany, String firstName, String lastName, LocalDate expDate, int cvv, int pin, int annualFee, String status, String bankAccount, long clientID, int cashReward, int pointsReward) {
        super(cardNumber, cardCompany, firstName, lastName, expDate, cvv, pin, annualFee, status, bankAccount, clientID);
        this.cashReward = cashReward;
        this.pointsReward = pointsReward;
    }
}
