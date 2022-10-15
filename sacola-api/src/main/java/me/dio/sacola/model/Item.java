package me.dio.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne //um produto só tem um item
    private Produto produto;
    private int quantidade;
    @ManyToOne @JsonIgnore
    private Sacola sacola;
}
