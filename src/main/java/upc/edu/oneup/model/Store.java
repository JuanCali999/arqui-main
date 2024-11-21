package upc.edu.oneup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="stores")
public class Store {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "direction", nullable = false, length = 30)
    private String direction;


    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "supervisor", nullable = false, length = 30)
    private String supervisor;

    @JsonIgnore
    @JsonBackReference("user-products")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

}
