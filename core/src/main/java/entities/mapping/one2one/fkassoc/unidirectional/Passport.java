package entities.mapping.one2one.fkassoc.unidirectional;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PASSPORT")
@Data
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PASSPORT_ID")
    private Long id;

    @Column(name = "PASSPORT_NO")
    private String passportNo;
}