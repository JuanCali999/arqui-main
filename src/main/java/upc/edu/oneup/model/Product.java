package upc.edu.oneup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;


    @Column(name = "product_price", nullable = false, length = 10)
    private Double productPrice;

    @Column(name = "quantity", nullable = false, length = 10)
    private int quantity;

    @Column(name = "date", nullable = false, length = 15)
    @NotNull
    @Size(max = 15)
    private String date;

    @Column(name = "availability", nullable = false, length = 50)
    private String availability;

    @Column(name = "product_image_url", nullable = false, length = 50000)
    private String productImageUrl;


    @JsonIgnore
    @JsonBackReference("user-products")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;



}
