package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
@Access(AccessType.PROPERTY)
public class Address {
    String city;
    String street;
    String house;
    String apartment;
}
