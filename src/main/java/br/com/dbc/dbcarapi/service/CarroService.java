package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.CarroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarroService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarroRepository carroRepository;

    public List<CarroDTO> list() throws SQLException {
        return carroRepository.list().stream()
                .map(carro -> objectMapper.convertValue(carro, CarroDTO.class))
                .collect(Collectors.toList());
    }

    public CarroDTO create(CarroCreateDTO carro) throws SQLException {
        log.info("Adicionando o novo carro...");
        Carro carroEntity = convertCarroEntity(carro);
        Carro carroCriado = carroRepository.create(carroEntity);
        CarroDTO carroDTO = convertCarroDTO(carroCriado);
        log.info("O novo carro" + carroDTO.getNomeCarro() + " foi adicionado com sucesso.");
        return carroDTO;
    }

    public CarroDTO update(Integer idCarro, CarroCreateDTO carroCreateDTO) throws Exception {
        log.info("Atualizando dados do carro...");
            findByIdCarro(idCarro);
            Carro carroEntity = convertCarroEntity(carroCreateDTO);
            Carro carroAtualizar = carroRepository.update(idCarro, carroEntity);
            CarroDTO carroDTO = convertCarroDTO(carroAtualizar);
            carroDTO.setIdCarro(idCarro);
            log.info("Dados do carro atualizados " + carroAtualizar);
            return carroDTO;

    }

    public void delete(Integer idCarro) throws SQLException {
        log.info("Deletando carro do catálogo...");
        Carro verifyCarro = carroRepository.findById(idCarro);
        carroRepository.delete(idCarro);
        log.info("O carro " + verifyCarro.getNomeCarro() + " foi removido do catálogo com sucesso!");
    }

    public List<CarroDTO> listNaoAlugados() throws SQLException {
        return carroRepository.listarNaoAlugaDos().stream()
                .map(carro -> objectMapper.convertValue(carro, CarroDTO.class))
                .collect(Collectors.toList());
    }

    public CarroDTO findByIdCarro(Integer idCarro) throws Exception {
        Carro carroRecuperado = carroRepository.findById(idCarro);
        if(carroRecuperado != null) {
            return convertCarroDTO(carroRecuperado);
        } else {
            throw new Exception("Carro não encontrado");
        }
    }

    public Carro convertCarroEntity(CarroCreateDTO carro) {
        return objectMapper.convertValue(carro, Carro.class);
    }

    public CarroDTO convertCarroDTO(Carro carroEntity) {
        return objectMapper.convertValue(carroEntity, CarroDTO.class);
    }
}
