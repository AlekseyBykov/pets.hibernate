package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EntityWithoutId {
    @Id
    private Long id;

    @Column
    private String name;
}