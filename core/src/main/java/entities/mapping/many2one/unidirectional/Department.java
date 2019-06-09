package entities.mapping.many2one.unidirectional;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String deptName;
}
