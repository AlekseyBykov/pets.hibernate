//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Entity with auto generated identifier.
 *
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-06
 */
@Entity
@Data
public final class FirstEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
