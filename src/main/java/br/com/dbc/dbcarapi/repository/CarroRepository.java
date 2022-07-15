package br.com.dbc.dbcarapi.repository;

import br.com.dbc.dbcarapi.connection.ConexaoBancoDeDados;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.enums.Alugado;
import br.com.dbc.dbcarapi.enums.ClasseCarro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class CarroRepository implements Repository {

    @Autowired
    private Connection con;

    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_carro.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public List findAll() {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CARRO");
            ResultSet result = stmt.executeQuery();

            List<Carro> carros = new ArrayList<>();

            while (result.next()) {
                carros.add(compile(result));
            }

            return carros;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
        return null;
    }

    public Carro create(Carro carro) throws BancoDeDadosException {
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            carro.setIdCarro(proximoId);

            String sql = "INSERT INTO CARRO\n" +
                    "(ID_CARRO, ALUGADO, NOME, MARCA, CLASSE, QUANTIDADE_PASSAGEIROS, KM_RODADOS, PRECO_DIARIA)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, carro.getIdCarro());
            stmt.setInt(2, carro.getAlugado().getStatus());
            stmt.setString(3, carro.getNomeCarro());
            stmt.setString(4, carro.getMarca());
            stmt.setInt(5, carro.getClasse().getTipo());
            stmt.setInt(6, carro.getQntPassageiros());
            stmt.setInt(7, carro.getKmRodados());
            stmt.setDouble(8, carro.getPrecoDiaria());

            int res = stmt.executeUpdate();
//            System.out.println("adicionarCarro.res=" + res);
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


    public Carro update(Integer idCarro, Carro carro) throws BancoDeDadosException {
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CARRO SET ");
            sql.append(" Alugado = ?, ");
            sql.append(" Nome do Carro = ?, ");
            sql.append(" Marca = ?, ");
            sql.append(" Classe = ?, ");
            sql.append(" Quantidade de passageiros = ?, ");
            sql.append(" Kilômetros Rodados ?, ");
            sql.append(" Preço Diária = ?, ");
            sql.append(" WHERE id_carro = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, carro.getAlugado().getStatus());
            stmt.setString(2, carro.getNomeCarro());
            stmt.setString(3, carro.getMarca());
            stmt.setInt(4, carro.getClasse().getTipo());
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

    public void delete(Integer idCarro) throws BancoDeDadosException {
        try {
            con = ConexaoBancoDeDados.getConnection();

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

    public Carro findById(Integer idCarro) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CARRO WHERE id = " + idCarro);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return compile(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBancoDeDados.closeConnection();
        }
        return null;
    }

    public static Carro compile(ResultSet result) {
        try {
            Carro carro = new Carro();

            carro.setIdCarro(result.getInt("id_carro"));
            carro.setAlugado(Alugado.ofTipo(result.getInt("alugado")));
            carro.setNomeCarro(result.getString("nome"));
            carro.setMarca(result.getString("marca"));
            carro.setClasse(ClasseCarro.ofTipo(result.getInt("classe")));
            carro.setQntPassageiros(result.getInt("quantidade_pasageiros"));
            carro.setKmRodados(result.getInt("km_rodados"));
            carro.setPrecoDiaria(result.getDouble("preco_diaria"));
            return carro;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
