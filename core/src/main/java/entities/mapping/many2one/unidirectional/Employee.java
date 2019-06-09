package entities.mapping.many2one.unidirectional;

import lombok.Data;

import javax.persistence.*;

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