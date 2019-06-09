package entities.mapping.one2one.bidirectional;

import lombok.Data;

import javax.persistence.*;

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