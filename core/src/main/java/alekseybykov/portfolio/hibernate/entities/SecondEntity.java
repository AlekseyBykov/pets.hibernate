//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-06
 */
@Entity
@Data
public class SecondEntity {
    @Id
    private Long id;

    @Column
    private String name;
}
