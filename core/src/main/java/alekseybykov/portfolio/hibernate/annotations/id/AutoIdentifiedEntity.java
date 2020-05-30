package alekseybykov.portfolio.hibernate.annotations.id;

import lombok.Data;

import javax.persistence.*;

/**
 * Entity with auto generated identifier.
 *
 * @author Aleksey Bykov
 * @since 06.06.2019
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
