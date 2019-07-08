package entities.mapping.one2one.fkassoc.unidirectional;

import lombok.Data;

import javax.persistence.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 1.0
 * @since   2019-06-05
 */
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