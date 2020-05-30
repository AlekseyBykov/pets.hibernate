package alekseybykov.portfolio.hibernate.annotations.enumerated;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Aleksey Bykov
 * @since 08.11.2019
 */
@Entity
@Data
public final class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_in_string")
    @Enumerated(EnumType.STRING)
    private Day dayInString;

    @Column(name = "day_in_ordinal")
    @Enumerated(EnumType.ORDINAL)
    private Day dayInOrdinal;
}
