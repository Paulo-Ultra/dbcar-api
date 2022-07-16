package br.com.dbc.dbcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Integer idUsuario;
    private String nome;
    @Override
    public String toString() {
        return "{"
                + "\"idUsuario\": " + getIdUsuario() + ", "
                + "\"nome\": \"" + getNome()
                + "\"}";
    }
}
