package alekseybykov.portfolio.hibernate.entities.core;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SecondEntity {
    @Id
    private Long id;

    @Column
    private String name;
}