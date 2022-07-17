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
    private ConexaoBancoDeDados conexaoBancoDeDados;
    public Integer getProximoIdFuncionario(Connection connection) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        String sql = "SELECT seq_funcionario.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    public Funcionario create(Funcionario funcionario) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            Integer proximoId = this.getProximoIdFuncionario(con);
            funcionario.setIdFuncionario(proximoId);

            String sql = "INSERT INTO FUNCIONARIO\n" +
                    "(ID_FUNCIONARIO, NOME, MATRICULA)\n" +
                    "VALUES(?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, funcionario.getIdFuncionario());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getMatricula());

            int res = stmt.executeUpdate();
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


    public Funcionario findByIdFuncionario(Integer idFuncionario) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM FUNCIONARIO");
                    sql.append(" WHERE ID_FUNCIONARIO = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, idFuncionario);

            ResultSet res = stmt.executeQuery();

            if(res.next()){
                return compile(res);
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

    public void delete(Integer idFuncionario) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            String sql = "DELETE FROM FUNCIONARIO WHERE id_funcionario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idFuncionario);

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
    public Funcionario update(Integer idFuncionario, Funcionario funcionario) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE FUNCIONARIO SET ");
            sql.append(" nome = ?, ");
            sql.append(" matricula = ? ");
            sql.append(" WHERE id_funcionario = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getMatricula());
            stmt.setInt(3, idFuncionario);

            int res = stmt.executeUpdate();
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

    public List<Funcionario> list() throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        List<Funcionario> funcionarios = new ArrayList<>();
        try {

            StringBuilder sql = new StringBuilder("SELECT * FROM FUNCIONARIO");

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Funcionario funcionario = compile(res);
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

    private Funcionario compile(ResultSet result) throws SQLException {
        try {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(result.getString("nome"));
            funcionario.setMatricula(result.getString("matricula"));
            funcionario.setIdFuncionario(result.getInt("id_funcionario"));

            return funcionario;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
