package entities.mapping.one2one.fkassoc.bidirectional;

import lombok.Data;

import javax.persistence.*;

/**
 * @author  aleksey.n.bykov@gmail.com
 * @version 1.0
 * @since   2019-06-05
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