package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SecondEntity {
    @Id
    private Long id;

    @Column
    private String name;
}