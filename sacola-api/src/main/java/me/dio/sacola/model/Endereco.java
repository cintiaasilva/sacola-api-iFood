package me.dio.sacola.model;

import lombok.*;

import javax.persistence.Embeddable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
//n vai ser uma tabela no banco de dados
public class Endereco {
    private String cep;
    private String complemento;
}
