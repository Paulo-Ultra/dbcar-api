package br.com.dbc.dbcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente  {
    private Integer idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;

}
