package entities.mapping.manytoone.bidirectional;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "UNIVERSITY")
@Data
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIVERSITY_ID")
    private Long id;

    @Column(name = "UNIVERSITY_NAME")
    private String name;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Student> students;
}
