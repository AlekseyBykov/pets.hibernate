package alekseybykov.portfolio.hibernate.annotations.id;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity with manually assigned identifier.
 *
 * @author  aleksey.n.bykov@gmail.com
 * @version 2019-06-06
 */
@Data
@Entity
@Table(name = "manually_identified")
public final class ManuallyIdentifiedEntity {
    @Id
    private Long id;

    @Column
    private String name;
}
