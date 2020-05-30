package alekseybykov.portfolio.hibernate.inheritance.strategies.mappedsuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Aleksey Bykov
 * @since 12.11.2019
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
