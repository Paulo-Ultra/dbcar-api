package br.com.dbc.dbcarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario {
    private Integer idCliente;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getNome(), cpf, telefone, endereco, email);
    }
}
