package me.dio.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Sacola {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //um cliente pode ter várias sacolas
    //fetch é a aprensentação do item dessa tabela (não vai ver o cliente dentro dessa tabela)
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JsonIgnore
    private Cliente cliente;
    //varios itens pode está numa sacola
    //cascade exclui a Sacola exclui todos os itens dessa sacola
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itensSacola;
    private double valorTotal;
    @Enumerated
    private FormaPagamento formaPagamento;
    private boolean fechada;
}
