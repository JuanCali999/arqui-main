package upc.edu.oneup.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="suppliers")
public class    Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "direction", nullable = false, length = 30)
    private String direction;



    @Column(name = "phone", nullable = false, length = 30)
    private String phone;



    @Column(name = "email", nullable = false, length = 50)
    private String email;


}
