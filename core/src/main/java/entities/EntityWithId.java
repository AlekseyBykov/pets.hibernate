package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EntityWithId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}