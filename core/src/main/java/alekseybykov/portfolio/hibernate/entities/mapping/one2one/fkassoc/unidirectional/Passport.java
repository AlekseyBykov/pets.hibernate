//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.mapping.one2one.fkassoc.unidirectional;

import lombok.Data;

import javax.persistence.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-05
 */
@Entity
@Table(name = "PASSPORT")
@Data
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PASSPORT_ID")
    private Long id;

    @Column(name = "PASSPORT_NO")
    private String passportNo;
}
