package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class IdWithoutIdentity {
    @Id
    private Long id;

    @Column
    private String name;
}