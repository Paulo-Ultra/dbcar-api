package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.AluguelCreateDTO;
import br.com.dbc.dbcarapi.dto.AluguelDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.entity.Aluguel;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.AluguelRepository;
import br.com.dbc.dbcarapi.repository.CarroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AluguelService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private CarroRepository carroRepository;

    public List<AluguelDTO> list() throws SQLException {
        try {
            return aluguelRepository.list().stream()
                    .map(aluguel -> objectMapper.convertValue(aluguel, AluguelDTO.class))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
    }

    public AluguelDTO create(AluguelCreateDTO aluguel) throws Exception {
        log.info("Adicionando o novo aluguel...");
        Aluguel aluguelEntity = convertAluguelEntity(aluguel);
        try {
            aluguelEntity = aluguelRepository.create(aluguelEntity);
            carroRepository.editarAlugado(aluguel.getIdCarro(), false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        AluguelDTO aluguelDTO = convertAluguelDTO(aluguelEntity);
        log.info("O novo aluguel no dia " + aluguelDTO.getDiaDoAluguel() + " foi adicionado com sucesso.");
        return aluguelDTO;
    }

    public AluguelDTO update(Integer idAluguel, AluguelCreateDTO aluguelCreateDTO) throws Exception {
        log.info("Atualizando dados do aluguel...");
        findByIdAluguel(idAluguel);
        Aluguel aluguelEntity = convertAluguelEntity(aluguelCreateDTO);
        try {
            aluguelEntity = aluguelRepository.create(aluguelEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        AluguelDTO aluguelDTO = convertAluguelDTO(aluguelEntity);
        aluguelDTO.setIdAluguel(idAluguel);
        log.info("Dados do aluguel atualizados " + aluguelDTO);
        return aluguelDTO;
    }

    public void delete(Integer idAluguel) throws SQLException {
        log.info("Deletando aluguel do catálogo...");
        try {
            Aluguel verifyAluguel = aluguelRepository.findById(idAluguel);
            aluguelRepository.delete(idAluguel);
            log.info("O aluguel " + verifyAluguel.getIdAluguel() + " foi removido do catálogo de aluguéis com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } catch (NullPointerException e) {
            throw new NullPointerException("O identificador (ID) informado não consta em nosso banco de dados!");
        }
    }

    public AluguelDTO findByIdAluguel(Integer idAluguel) throws Exception {
        Aluguel aluguelRecuperado = aluguelRepository.findById(idAluguel);
        if (aluguelRecuperado != null) {
            return convertAluguelDTO(aluguelRecuperado);
        } else {
            throw new Exception("Carro não encontrado");
        }
    }

    public Double calcularValor(Carro carro, Aluguel aluguel) throws Exception {
        switch (carro.getClasse().getTipo()) {
            case "A":
                return diasComCarro(aluguel) * carro.getPrecoDiaria() * 1.5;
            case "B":
                return diasComCarro(aluguel) * carro.getPrecoDiaria() * 1.2;
            case "C":
                return diasComCarro(aluguel) * carro.getPrecoDiaria();
            default:
                System.out.println("O carro informado não está disponível.");
                return null;
        }
    }

    public Long diasComCarro(Aluguel aluguel) {
        Long diasComCarro = aluguel.getDiaDoAluguel().until(aluguel.getDiaDaEntrega(), ChronoUnit.DAYS);
        return diasComCarro;
    }

    public Aluguel convertAluguelEntity(AluguelCreateDTO aluguel) {
        return objectMapper.convertValue(aluguel, Aluguel.class);
    }

    public AluguelDTO convertAluguelDTO(Aluguel aluguelEntity) {
        return objectMapper.convertValue(aluguelEntity, AluguelDTO.class);
    }
}
