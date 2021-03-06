package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.AluguelCreateDTO;
import br.com.dbc.dbcarapi.dto.AluguelDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.entity.Aluguel;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.enums.Alugado;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.AluguelRepository;
import br.com.dbc.dbcarapi.repository.CarroRepository;
import br.com.dbc.dbcarapi.repository.ClienteRepository;
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
    @Autowired
    private EmailService emailService;

    @Autowired
    private ClienteRepository clienteRepository;

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
            if(carro.getAlugado().equals(Alugado.DISPONIVEL)) {
            aluguelEntity = aluguelRepository.create(aluguelEntity);
            carroRepository.editarAlugado(aluguel.getIdCarro(), false);
            aluguelEntity.setValor(calcularDiarias(aluguelEntity));
            } else {
                throw new Exception("Carro indispon??vel para aluguel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        AluguelDTO aluguelDTO = convertAluguelDTO(aluguelEntity);
        log.info("O novo aluguel no dia " + aluguelDTO.getDiaDoAluguel() + " foi adicionado com sucesso.");
        String tipo = "alugado";
        emailService.sendEmailCarroCliente(clienteRepository.findByIdCliente(aluguel.getIdCliente()), aluguelDTO, tipo);
        return aluguelDTO;
    }

    public AluguelDTO update(Integer idAluguel, AluguelCreateDTO aluguelCreateDTO) throws Exception {
        log.info("Atualizando dados do aluguel...");
        findByIdAluguel(idAluguel);
        Aluguel aluguelEntity = convertAluguelEntity(aluguelCreateDTO);
        try {
            carroRepository.findById(aluguelCreateDTO.getIdCarro());
                aluguelEntity = aluguelRepository.update(idAluguel, aluguelEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        AluguelDTO aluguelDTO = convertAluguelDTO(aluguelEntity);
        aluguelDTO.setIdAluguel(idAluguel);
        aluguelDTO.setValor(calcularDiarias(aluguelEntity));
        log.info("Dados do aluguel atualizados " + aluguelDTO);
        return aluguelDTO;
    }

    public void delete(Integer idAluguel) throws SQLException {
        log.info("Deletando aluguel do cat??logo...");
        try {
            Aluguel verifyAluguel = aluguelRepository.findById(idAluguel);
            aluguelRepository.delete(idAluguel);
            log.info("O aluguel " + verifyAluguel.getIdAluguel() + " foi removido do cat??logo de alugu??is com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } catch (NullPointerException e) {
            throw new NullPointerException("O identificador (ID) informado n??o consta em nosso banco de dados!");
        }
    }

    public AluguelDTO findByIdAluguel(Integer idAluguel) throws Exception {
        Aluguel aluguelRecuperado = aluguelRepository.findById(idAluguel);
        if (aluguelRecuperado != null) {
            return convertAluguelDTO(aluguelRecuperado);
        } else {
            throw new Exception("Aluguel n??o encontrado");
        }
    }

    private Double calcularDiarias(Aluguel aluguel) throws SQLException {
        LocalDate d1 = LocalDate.parse(aluguel.getDiaDoAluguel().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d2 = LocalDate.parse(aluguel.getDiaDaEntrega().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
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

    public Aluguel convertAluguelEntity(AluguelCreateDTO aluguel) {
        return objectMapper.convertValue(aluguel, Aluguel.class);
    }

    public AluguelDTO convertAluguelDTO(Aluguel aluguelEntity) {
        return objectMapper.convertValue(aluguelEntity, AluguelDTO.class);
    }
}
