package br.com.dbc.dbcarapi.repository;

import br.com.dbc.dbcarapi.connection.ConexaoBancoDeDados;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Usuario;
import br.com.dbc.dbcarapi.enums.Alugado;
import br.com.dbc.dbcarapi.enums.ClasseCarro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    @Autowired
    private ConexaoBancoDeDados conexaoBancoDeDados;

    public Integer getProximoId(Connection connection) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        String sql = "SELECT seq_carro.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public List<Usuario> list() throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CARRO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Usuario user = compile(res);
                usuarios.add(user);
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
        return usuarios;
    }

//    public Usuario create(Usuario usuario) throws BancoDeDadosException {
//        try {
//            Integer proximoId = this.getProximoId(con);
//            usuario.setIdUsuario(proximoId);
//
//            String sql = "INSERT INTO CARRO\n" +
//                    "(ID_USUARIO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA)\n" +
//                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";
//
//            PreparedStatement stmt = con.prepareStatement(sql);
//
//            stmt.setInt(1, carro.getIdCarro());
//            stmt.setString(2, carro.getAlugado().getStatus());
//            stmt.setString(3, carro.getNomeCarro());
//            stmt.setString(4, carro.getMarca());
//            stmt.setString(5, carro.getClasse().getTipo());
//            stmt.setInt(6, carro.getQntPassageiros());
//            stmt.setInt(7, carro.getKmRodados());
//            stmt.setDouble(8, carro.getPrecoDiaria());
//
//            int res = stmt.executeUpdate();
//            return carro;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public static Usuario compile(ResultSet result) {
        try {
            Usuario user = new Usuario();
            user.setIdUsuario(result.getInt("id_usuario"));
            user.setNome(result.getString("nome"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
