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
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private double valorUnitario;
    @Builder.Default //precisa dizer que esse true é default
    private boolean disponivel = true;
    @ManyToOne @JsonIgnore
    private Restaurante restaurante;

}
