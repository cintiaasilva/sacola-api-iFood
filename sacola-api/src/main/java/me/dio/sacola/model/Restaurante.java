package me.dio.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurante {
    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Produto> cardapio;
    @Embedded
    private Endereco endereco;
}
