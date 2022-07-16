package br.com.dbc.dbcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario extends Usuario{
    private Integer idFuncionario;
    private String matricula;

    @Override
    public String toString() {
        return "{"
                + "\"idUsuario\": " + getIdUsuario() + ", "
                + "\"Nome\": \"" + getNome() + "\", "
                + "\"Matrícula\": \"" + matricula
                + "\"}";
    }
}
