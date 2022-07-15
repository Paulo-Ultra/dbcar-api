package br.com.dbc.dbcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario extends Usuario{
    private String matricula;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Funcionario)) {
            return false;
        }
        Funcionario funcionario = (Funcionario) o;
        return Objects.equals(getIdUsuario(), funcionario.getIdUsuario()) && Objects.equals(getNome(), funcionario.getNome())
                && Objects.equals(matricula, funcionario.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getNome(), matricula);
    }

    @Override
    public String toString() {
        return "{"
                + "\"idUsuario\": " + getIdUsuario() + ", "
                + "\"Nome\": \"" + getNome() + "\", "
                + "\"Matrícula\": \"" + matricula
                + "\"}";
    }
}
