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

@Entity @Table(name = "DEBIT_CARD")
@DiscriminatorValue("DC")
public class DebitCard extends Card{
    private static final long serialVersionUID=3L;
    @Column(name = "CASHBACK")
    private int cashBack;

    public DebitCard(long cardNumber, String cardCompany, String firstName, String lastName, LocalDate expDate, int cvv, int pin, int annualFee, String status, String bankAccount, long clientID, int cashBack) {
        super(cardNumber, cardCompany, firstName, lastName, expDate, cvv, pin, annualFee, status, bankAccount, clientID);
        this.cashBack = cashBack;
    }
}
