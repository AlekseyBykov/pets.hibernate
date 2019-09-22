//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.mapping.many2one.bidirectional;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 1.0
 * @since   2019-06-05
 */
@Entity
@Table(name = "UNIVERSITY")
@Data
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIVERSITY_ID")
    private Long id;

    @Column(name = "UNIVERSITY_NAME")
    private String name;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Student> students;
}
