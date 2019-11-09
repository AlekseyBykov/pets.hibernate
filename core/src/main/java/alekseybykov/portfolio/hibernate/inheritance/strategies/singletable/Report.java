//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.inheritance.strategies.singletable;

import lombok.Data;

import javax.persistence.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-09
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // default
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String format;
}
