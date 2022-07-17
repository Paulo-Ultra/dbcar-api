package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.AluguelCreateDTO;
import br.com.dbc.dbcarapi.dto.AluguelDTO;
import br.com.dbc.dbcarapi.entity.Aluguel;
import br.com.dbc.dbcarapi.repository.AluguelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AluguelService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AluguelRepository aluguelRepository;

    public List<AluguelDTO> list() throws SQLException {
        return aluguelRepository.list().stream()
                .map(aluguel -> objectMapper.convertValue(aluguel, AluguelDTO.class))
                .collect(Collectors.toList());
    }

    public AluguelDTO create(AluguelCreateDTO aluguel) throws SQLException {
        log.info("Adicionando o novo aluguel...");
        Aluguel aluguelEntity = convertAluguelEntity(aluguel);
        Aluguel aluguelCriado = aluguelRepository.create(aluguelEntity);
        AluguelDTO aluguelDTO = convertAluguelDTO(aluguelCriado);
        log.info("O novo aluguel no dia " + aluguelDTO.getDiaDoAluguel() + " foi adicionado com sucesso.");
        return aluguelDTO;
    }

    public AluguelDTO update(Integer idAluguel, AluguelCreateDTO aluguelCreateDTO) throws Exception {
        log.info("Atualizando dados do aluguel...");
        findByIdAluguel(idAluguel);
        Aluguel aluguelEntity = convertAluguelEntity(aluguelCreateDTO);
        Aluguel aluguelAtualizar = aluguelRepository.create(aluguelEntity);
        AluguelDTO aluguelDTO = convertAluguelDTO(aluguelAtualizar);
        aluguelDTO.setIdAluguel(idAluguel);
        log.info("Dados do aluguel atualizados " + aluguelAtualizar);
        return aluguelDTO;
    }

    public void delete(Integer idAluguel) throws SQLException {
        log.info("Deletando aluguel do catálogo...");
        Aluguel verifyAluguel = aluguelRepository.findById(idAluguel);
        aluguelRepository.delete(idAluguel);
        log.info("O carro " + verifyAluguel.getCarro() + " foi removido do catálogo de aluguéis com sucesso!");
    }

    public AluguelDTO findByIdAluguel(Integer idAluguel) throws Exception {
        Aluguel aluguelRecuperado = aluguelRepository.findById(idAluguel);
        if(aluguelRecuperado != null) {
            return convertAluguelDTO(aluguelRecuperado);
        } else {
            throw new Exception("Carro não encontrado");
        }
    }
    public Aluguel convertAluguelEntity(AluguelCreateDTO aluguel) {
        return objectMapper.convertValue(aluguel, Aluguel.class);
    }

    public AluguelDTO convertAluguelDTO(Aluguel aluguelEntity) {
        return objectMapper.convertValue(aluguelEntity, AluguelDTO.class);
    }
}
