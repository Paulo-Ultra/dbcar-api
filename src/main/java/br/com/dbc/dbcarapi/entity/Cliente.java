package br.com.dbc.dbcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario {
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        return Objects.equals(getIdUsuario(), cliente.getIdUsuario()) && Objects.equals(getNome(), cliente.getNome())
                && Objects.equals(getCpf(), cliente.getCpf()) && Objects.equals(telefone, cliente.telefone)
                && Objects.equals(endereco, cliente.endereco)
                && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getNome(), cpf, telefone, endereco, email);
    }
}
