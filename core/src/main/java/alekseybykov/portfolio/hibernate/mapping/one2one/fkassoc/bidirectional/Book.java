package alekseybykov.portfolio.hibernate.mapping.one2one.fkassoc.bidirectional;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Aleksey Bykov
 * @since 05.06.2019
 */
@Entity
@Table(name = "BOOK")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private Long id;

    @Column(name = "BOOK_TITLE")
    private String title;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Publisher publisher;
}
