package br.com.dbc.dbcarapi.repository;

import br.com.dbc.dbcarapi.connection.ConexaoBancoDeDados;
import br.com.dbc.dbcarapi.entity.Funcionario;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FuncionarioRepository {

    @Autowired
    private Connection con;
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_funcionario.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    public Funcionario adicionar(Funcionario funcionario) throws BancoDeDadosException {
        try {
            Integer proximoId = this.getProximoId(con);
            funcionario.setIdFuncionario(proximoId);

            String sql = "INSERT INTO FUNCIONARIO\n" +
                    "(ID_FUNCIONARIO, MATRICULA)\n" +
                    "VALUES(?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, funcionario.getIdFuncionario());
            stmt.setString(2, funcionario.getMatricula());

//            int res = stmt.executeUpdate();
//            System.out.println("adicionarFuncionario.res=" + res);
            return funcionario;
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


    public Funcionario selecionar(Integer id) throws BancoDeDadosException {
        try {
            String sql = "SELECT * FROM FUNCIONARIO F\n" +
                    "INNER JOIN USUARIO U ON (U.ID_USUARIO = F.ID_USUARIO)\n" +
                    "WHERE ID_FUNCIONARIO = ?\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                return getFuncionario(res);
            }
            return null;

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

    public boolean delete(Integer id) throws BancoDeDadosException {
        try {
            String sql = "DELETE FROM FUNCIONARIO WHERE id_funcionario = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            int res = stmt.executeUpdate();

            return res > 0;
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
    public boolean update(Integer id, Funcionario funcionario) throws BancoDeDadosException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE FUNCIONARIO SET ");
            sql.append(" matricula = ?,");
            sql.append(" WHERE id_funcionario = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, funcionario.getMatricula());
            stmt.setInt(2, id);

            int res = stmt.executeUpdate();
//            System.out.println("editarFuncionario.res=" + res);

            return res > 0;
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
    public List<Funcionario> list() throws BancoDeDadosException {
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM FUNCIONARIO F\n" +
                    "INNER JOIN USUARIO U ON (U.ID_USUARIO = F.ID_USUARIO)\n" +
                    "WHERE ID_FUNCIONARIO = ?\n";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Funcionario funcionario = getFuncionario(res);
                funcionarios.add(funcionario);
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
        return funcionarios;
    }
    private Funcionario getFuncionario(ResultSet result) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setIdUsuario(result.getInt("id_usuario"));
        funcionario.setNome(result.getString("nome"));
        funcionario.setIdFuncionario(result.getInt("id_funcionario"));
        funcionario.setMatricula(result.getString("matricula"));
        return funcionario;
    }
}
