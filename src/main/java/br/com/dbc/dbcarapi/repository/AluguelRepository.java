package br.com.dbc.dbcarapi.repository;

import br.com.dbc.dbcarapi.connection.ConexaoBancoDeDados;
import br.com.dbc.dbcarapi.entity.Aluguel;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AluguelRepository {

    @Autowired
    private ConexaoBancoDeDados conexaoBancoDeDados;

    public Integer getProximoIdAluguel(Connection connection) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        String sql = "SELECT seq_aluguel.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    public List<Aluguel> list() throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        List<Aluguel> alugueis = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM ALUGUEL";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Aluguel aluguel = compile(res);
                alugueis.add(aluguel);
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
        return alugueis;
    }

    public Aluguel create(Aluguel aluguel) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            Integer proximoId = this.getProximoIdAluguel(con);
            aluguel.setIdAluguel(proximoId);

            String sql = "INSERT INTO ALUGUEL\n" +
                    "(ID_ALUGUEL, ID_CLIENTE, ID_CARRO, diaDoAluguel, diaDaEntrega, valor)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, aluguel.getIdAluguel());
            stmt.setInt(2, aluguel.getIdCliente());
            stmt.setInt(3, aluguel.getIdCarro());
            stmt.setDate(4, Date.valueOf(aluguel.getDiaDoAluguel()));
            stmt.setDate(5, Date.valueOf(aluguel.getDiaDaEntrega()));
            stmt.setDouble(5, aluguel.getValor());

            int res = stmt.executeUpdate();
            return aluguel;
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

    public Aluguel update(Integer idAluguel, Aluguel aluguel) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ALUGUEL SET ");
            sql.append(" ID_CLIENTE = ?, ");
            sql.append(" ID_CARRO = ?, ");
            sql.append(" Dia do Aluguel = ?, ");
            sql.append(" Dia da Entrega = ?, ");
            sql.append(" Valor = ? ");
            sql.append(" WHERE id_aluguel = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, aluguel.getIdCliente());
            stmt.setInt(2, aluguel.getIdCarro());
            stmt.setDate(3, Date.valueOf(aluguel.getDiaDoAluguel()));
            stmt.setDate(4, Date.valueOf(aluguel.getDiaDaEntrega()));
            stmt.setInt(5, idAluguel);

            int res = stmt.executeUpdate();
            return aluguel;
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

    public void delete(Integer idAluguel) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            String sql = "DELETE FROM ALUGUEL WHERE id_aluguel = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, idAluguel);

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

    public Aluguel findById(Integer idAluguel) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            String sql = "SELECT * FROM ALUGUEL WHERE id_aluguel = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, idAluguel);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return compile(result);
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
        return null;
    }

    public static Aluguel compile(ResultSet result) {
        try {
            Aluguel aluguel = new Aluguel();
            aluguel.setIdAluguel(result.getInt("id_aluguel"));
            aluguel.setIdCliente(result.getInt("id_cliente"));
            aluguel.setIdCarro(result.getInt("id_carro"));
            aluguel.setDiaDoAluguel(LocalDate.parse(String.valueOf(result.getDate("diaDoAluguel"))));
            aluguel.setDiaDaEntrega(LocalDate.parse(String.valueOf(result.getDate("diaDaEntrega"))));
            aluguel.setValor(result.getDouble("valor"));
            return aluguel;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
