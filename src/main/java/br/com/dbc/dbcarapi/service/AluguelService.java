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
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            Carro carro = carroRepository.findById(aluguel.getIdCarro());
            if(carro.getAlugado().equals(true)) {
                aluguel.setValor(calcularDiarias(aluguelEntity));
                aluguelEntity = aluguelRepository.create(aluguelEntity);
                carroRepository.editarAlugado(aluguel.getIdCarro(), false);
            } else {
                throw new Exception("Carro indisponível para aluguel");
            }
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

    private Double calcularDiarias(Aluguel aluguel) throws SQLException {
        LocalDate d2 = LocalDate.parse(aluguel.getDiaDoAluguel().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d1 = LocalDate.parse(aluguel.getDiaDaEntrega().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
        long diffDays = diff.toDays();
        Carro carro = carroRepository.findById(aluguel.getIdCarro());
        if (carro.getClasse().getTipo() == "A") {
            return diffDays * carro.getPrecoDiaria() * 1.5;
        } else if (carro.getClasse().getTipo() == "B") {
            return diffDays * carro.getPrecoDiaria() * 1.2;
        } else {
            return diffDays * carro.getPrecoDiaria();
        }
    }

//    public List<CarroDTO> carrosAlugados () throws SQLException {
//        return carroRepository.list().stream()
//                .map(carro -> {
//                    CarroDTO carroDTO = new CarroDTO();
//                    if (carro.getAlugado().equals(false)) {
//                        carroDTO.setIdCarro(carro.getIdCarro());
//                        carroDTO.setAlugado(carro.getAlugado());
//                        carroDTO.setNomeCarro(carro.getNomeCarro());
//                        carroDTO.setMarca(carro.getMarca());
//                        carroDTO.setClasse(carro.getClasse());
//                        carroDTO.setQntPassageiros(carro.getQntPassageiros());
//                        carroDTO.setKmRodados(carro.getKmRodados());
//                        carroDTO.setPrecoDiaria(carro.getPrecoDiaria());
//                    }
//                        try {
//                            aluguelRepository.create(new Aluguel());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            e.getCause();
//                        }
//                    return carroDTO;
//                })
//                .toList();
//    }

    public Aluguel convertAluguelEntity(AluguelCreateDTO aluguel) {
        return objectMapper.convertValue(aluguel, Aluguel.class);
    }

    public AluguelDTO convertAluguelDTO(Aluguel aluguelEntity) {
        return objectMapper.convertValue(aluguelEntity, AluguelDTO.class);
    }
}
