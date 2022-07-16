package br.com.dbc.dbcarapi.repository;

import br.com.dbc.dbcarapi.connection.ConexaoBancoDeDados;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.enums.Alugado;
import br.com.dbc.dbcarapi.enums.ClasseCarro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarroRepository {

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

    public List<Carro> list() throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        List<Carro> carros = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CARRO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Carro carro = compile(res);
                carros.add(carro);
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
        return carros;
    }

    public Carro create(Carro carro) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            Integer proximoId = this.getProximoId(con);
            carro.setIdCarro(proximoId);

            String sql = "INSERT INTO CARRO\n" +
                    "(ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, carro.getIdCarro());
            stmt.setString(2, carro.getAlugado().getStatus());
            stmt.setString(3, carro.getNomeCarro());
            stmt.setString(4, carro.getMarca());
            stmt.setString(5, carro.getClasse().getTipo());
            stmt.setInt(6, carro.getQntPassageiros());
            stmt.setInt(7, carro.getKmRodados());
            stmt.setDouble(8, carro.getPrecoDiaria());

            int res = stmt.executeUpdate();
            return carro;
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

    public Carro update(Integer idCarro, Carro carro) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CARRO SET ");
            sql.append(" alugado = ?, ");
            sql.append(" nome = ?, ");
            sql.append(" marca = ?, ");
            sql.append(" classe = ?, ");
            sql.append(" quantidade_passageiros = ?, ");
            sql.append(" km_rodados = ?, ");
            sql.append(" preco_diaria = ? ");
            sql.append(" WHERE id_carro = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, carro.getAlugado().getStatus());
            stmt.setString(2, carro.getNomeCarro());
            stmt.setString(3, carro.getMarca());
            stmt.setString(4, carro.getClasse().getTipo());
            stmt.setInt(5, carro.getQntPassageiros());
            stmt.setInt(6, carro.getKmRodados());
            stmt.setDouble(7, carro.getPrecoDiaria());
            stmt.setInt(8, idCarro);

            int res = stmt.executeUpdate();
            return carro;
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

    public void delete(Integer idCarro) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            String sql = "DELETE FROM CARRO WHERE id_carro = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCarro);

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

    public Carro findById(Integer idCarro) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            String sql = "SELECT * FROM CARRO WHERE id_carro = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCarro);
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

    public boolean editarAlugado(Integer id, Boolean alugado) throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CARRO SET ");
            sql.append(" Alugado = ? ");
            sql.append(" WHERE id_carro = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, alugado ? "S" : "N");
            stmt.setInt(2, id);

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

    public List<Carro> listarNaoAlugaDos() throws SQLException {
        Connection con = conexaoBancoDeDados.getConnection();
        List<Carro> carros = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CARRO\n" +
                    "WHERE ALUGADO = 'S'";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Carro carro = compile(res);
                carros.add(carro);
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
        return carros;
    }
    public static Carro compile(ResultSet result) {
        try {
            Carro carro = new Carro();
            carro.setIdCarro(result.getInt("id_carro"));
            carro.setAlugado(Alugado.ofTipo(result.getString("alugado")));
            carro.setNomeCarro(result.getString("nome"));
            carro.setMarca(result.getString("marca"));
            carro.setClasse(ClasseCarro.ofTipo(result.getString("classe")));
            carro.setQntPassageiros(result.getInt("quantidade_passageiros"));
            carro.setKmRodados(result.getInt("km_rodados"));
            carro.setPrecoDiaria(result.getDouble("preco_diaria"));
            return carro;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
