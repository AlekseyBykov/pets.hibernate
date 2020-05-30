package alekseybykov.portfolio.hibernate.inheritance.strategies.joined;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Aleksey Bykov
 * @since 10.11.2019
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "employment_agreement")
@EqualsAndHashCode(callSuper = true)
public class EmploymentAgreement extends Document {

    private Double salary;

    public EmploymentAgreement(String state, Double salary) {
        super(state);
        this.salary = salary;
    }
}
