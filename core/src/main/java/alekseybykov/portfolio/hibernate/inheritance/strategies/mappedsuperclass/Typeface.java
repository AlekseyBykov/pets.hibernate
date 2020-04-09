package alekseybykov.portfolio.hibernate.inheritance.strategies.mappedsuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-11-12
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public class Typeface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Typeface(String name) {
        this.name = name;
    }
}
