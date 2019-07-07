package entities.mapping.one2one.fkassoc.unidirectional;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID")
    private Long id;

    @Column(name = "PERSON_NAME")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PASSPORT_ID")
    private Passport passport;
}