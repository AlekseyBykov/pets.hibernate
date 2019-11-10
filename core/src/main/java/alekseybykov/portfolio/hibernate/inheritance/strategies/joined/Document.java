//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.inheritance.strategies.joined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-10
 */
@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;

    public Document(String state) {
        this.state = state;
    }
}
