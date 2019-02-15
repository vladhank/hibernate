package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
public class GoldClient extends Client {
    private boolean healthInsurance;
    private boolean gym;

    public GoldClient(String firstName, String lastName, String phoneNumber, Address address, LocalDate dateOfBirth, String login, String password, boolean healthInsurance, boolean gym) {
        super(firstName, lastName, phoneNumber, address, dateOfBirth, login, password);
        this.healthInsurance = healthInsurance;
        this.gym = gym;
    }

}
