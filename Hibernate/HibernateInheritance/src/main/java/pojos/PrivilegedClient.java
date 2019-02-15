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
public class PrivilegedClient extends Client {
   private Boolean healthInsurance;

    public PrivilegedClient(String firstName, String lastName, String phoneNumber, Address address, LocalDate dateOfBirth, String login, String password, Boolean healthInsurance) {
        super(firstName, lastName, phoneNumber, address, dateOfBirth, login, password);
        this.healthInsurance = healthInsurance;
    }
}
