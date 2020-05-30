package alekseybykov.portfolio.hibernate.mapping.many2one.unidirectional;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Aleksey Bykov
 * @since 09.06.2019
 */
@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private Double salary;

    @JoinColumn(name = "department")
    @ManyToOne(optional = false)
    private Department department;
}
