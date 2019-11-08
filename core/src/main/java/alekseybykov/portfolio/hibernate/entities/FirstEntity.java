//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-06
 */
@Entity
@Data
public class FirstEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
