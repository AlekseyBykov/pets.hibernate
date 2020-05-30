package alekseybykov.portfolio.hibernate.inheritance.strategies.joined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Aleksey Bykov
 * @since 10.11.2019
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
