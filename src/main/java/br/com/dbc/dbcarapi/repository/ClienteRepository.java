package br.com.dbc.dbcarapi.repository;

import br.com.dbc.dbcarapi.dto.ClienteCreateDTO;
import br.com.dbc.dbcarapi.dto.ClienteDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Cliente;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private Connection con;

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_cliente.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public List<Cliente> list() throws BancoDeDadosException {
        List<Cliente> clientes = new ArrayList<>();
        try {

            StringBuilder sql = new StringBuilder("SELECT * FROM CLIENTE C");
            sql.append(" INNER JOIN USUARIO U ON C.ID_USUARIO = U.ID_USUARIO");

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                clientes.add(compile(result));
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clientes;
    }

    public Cliente create(Cliente cliente) throws BancoDeDadosException {
        try {
            Integer proximoId = this.getProximoId(con);
            cliente.setIdUsuario(proximoId);

            String sql = "INSERT INTO CLIENTE\n" +
                    "(ID_USUARIO, NOME, CPF, TELEFONE, ENDERECO, EMAIL)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cliente.getIdUsuario());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setString(6, cliente.getEmail());

            int res = stmt.executeUpdate();

            return cliente;
        }  catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente update(Integer idCliente, Cliente cliente) throws BancoDeDadosException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CLIENTE SET ");
            sql.append(" cpf = ?, ");
            sql.append(" telefone = ?, ");
            sql.append(" endereco = ?, ");
            sql.append(" email = ? ");
            sql.append(" WHERE id_cliente = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, idCliente);

            int res = stmt.executeUpdate();
            return cliente;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Integer idCliente) throws BancoDeDadosException {
        try {
            String sql = "DELETE FROM CLIENTE WHERE id_cliente = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCliente);

            int res = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente findByIdCliente(Integer idCliente) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM CLIENTE C");
            sql.append(" INNER JOIN USUARIO U ON C.ID_USUARIO = U.ID_USUARIO WHERE id_cliente = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, idCliente);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return compile(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cliente compile(ResultSet result) {
        try {
            Cliente cliente = new Cliente();
            cliente.setIdUsuario(result.getInt("id_usuario"));
            cliente.setIdCliente(result.getInt("id_cliente"));
            cliente.setNome(result.getString("nome"));
            cliente.setCpf(result.getString("cpf"));
            cliente.setTelefone(result.getString("telefone"));
            cliente.setEndereco(result.getString("endereco"));
            cliente.setEmail(result.getString("email"));

            return cliente;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
