package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class IdWithNoname {
    @Id
    private Long id;

    @Column
    private String noname;
}