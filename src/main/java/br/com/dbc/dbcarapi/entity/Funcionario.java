package br.com.dbc.dbcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {
    private Integer idFuncionario;
    private String nome;
    private String matricula;
}
