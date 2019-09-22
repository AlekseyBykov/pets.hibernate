//
// Feel free to use these solutions in your work.
//
package alekseybykov.portfolio.hibernate.entities.core;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FirstEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
