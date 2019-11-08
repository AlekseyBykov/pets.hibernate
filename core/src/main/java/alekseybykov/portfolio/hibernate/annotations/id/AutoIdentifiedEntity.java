//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.annotations.id;

import lombok.Data;

import javax.persistence.*;

/**
 * Entity with auto generated identifier.
 *
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-06
 */
@Data
@Entity
@Table(name = "auto_identified")
public final class AutoIdentifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
