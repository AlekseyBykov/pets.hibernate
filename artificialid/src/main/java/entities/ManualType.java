package entities;

import lombok.Data;
import javax.persistence.*;

@Entity(name = "ManualType")
@Data
public class ManualType {
    @Id
    private Long id;

    @Column
    private String name;
}