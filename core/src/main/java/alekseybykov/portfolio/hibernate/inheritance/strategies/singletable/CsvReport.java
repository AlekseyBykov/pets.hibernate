//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.inheritance.strategies.singletable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-09
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CsvReport extends Report {
    private boolean quoted;
}
