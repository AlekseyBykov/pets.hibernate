//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.annotations.enumerated;

import lombok.Data;

import javax.persistence.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-08
 */
@Entity
@Data
public final class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_in_string")
    @Enumerated(EnumType.STRING)
    private Day dayInString;

    @Column(name = "day_in_ordinal")
    @Enumerated(EnumType.ORDINAL)
    private Day dayInOrdinal;
}
