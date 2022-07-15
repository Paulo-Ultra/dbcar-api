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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario user = (Usuario) o;
        return Objects.equals(idUsuario, user.idUsuario) && Objects.equals(nome, user.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, nome);
    }

    @Override
    public String toString() {
        return "{"
                + "\"idUsuario\": " + getIdUsuario() + ", "
                + "\"nome\": \"" + getNome()
                + "\"}";
    }
}
