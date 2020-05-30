package alekseybykov.portfolio.hibernate.mapping.one2one.fkassoc.bidirectional;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Aleksey Bykov
 * @since 09.06.2019
 */
@Entity
@Table(name = "PUBLISHER")
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUBLISHER_ID")
    private Long id;

    @Column(name = "PUBLISHER_NAME")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private Book book;
}
