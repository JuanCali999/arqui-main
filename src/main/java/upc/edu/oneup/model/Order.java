package upc.edu.oneup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String name;



    @Column(name = "order_value", nullable = false, precision = 10, scale = 2)
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1000000.0")
    private BigDecimal orderValue;

    @Column(name = "quantity", nullable = false)
    @NotNull
    @Min(1)
    @Max(10000)
    private int quantity;

    @Column(name = "date", nullable = false, length = 15)
    @NotNull
    @Size(max = 15)
    private String date;


    @Column(name = "status", nullable = false, length = 20)
    @NotNull
    @Size(max = 20)
    private String status;

    @JsonIgnore
    @JsonBackReference("user-orders")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;


}
