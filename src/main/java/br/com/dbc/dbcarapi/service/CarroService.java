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

import java.util.List;

@Service
@Slf4j
public class CarroService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> list() throws BancoDeDadosException {
        return carroRepository.list();
    }
    public CarroDTO create(CarroCreateDTO carro) throws BancoDeDadosException {
        log.info("Adicionando o novo carro...");
        Carro carroEntity = convertCarroEntity(carro);
        Carro carroCriado = carroRepository.create(carroEntity);
        CarroDTO carroDTO = convertCarroDTO(carroCriado);
        log.info("O novo carro" + carroDTO.getNome() + " foi adicionado com sucesso.");
        return carroDTO;
    }

    public CarroDTO update(Integer idCarro, CarroCreateDTO carroCreateDTO) throws BancoDeDadosException {
        log.info("Atualizando dados do carro...");
        // verificando se o carro está no repository;
        Carro verifyCarro = carroRepository.findById(idCarro);
        // convertendo carro para carroEntity;
        Carro carroEntity = convertCarroEntity(carroCreateDTO);
        // atualizar dados do carro;
        // TODO - VERIFICAR NO MÉTODO findById (retornando objeto);
        Carro carroAtualizar = carroRepository.update(verifyCarro.getIdCarro(), carroEntity);
        CarroDTO carroDTO = convertCarroDTO(carroAtualizar);
        log.info("Dados do carro atualizados " + carroAtualizar);
        return carroDTO;
    }

    public void delete(Integer idCarro)  throws BancoDeDadosException {
        log.info("Deletando carro do catálogo...");
        Carro verifyCarro = carroRepository.findById(idCarro);
        carroRepository.list().remove(verifyCarro);
        log.info("O carro " + verifyCarro.getNomeCarro() + " foi removido do catálogo com sucesso!");
        CarroDTO carroDTO = convertCarroDTO(verifyCarro);
    }

    private Carro convertCarroEntity(CarroCreateDTO carro) {
        return objectMapper.convertValue(carro, Carro.class);
    }

    private CarroDTO convertCarroDTO(Carro carroEntity) {
        return objectMapper.convertValue(carroEntity, CarroDTO.class);
    }

    public Carro findByIdCarro(Integer idCarro) throws Exception {
        return carroRepository.list().stream()
                .filter(carro -> carro.getIdCarro().equals(idCarro))
                .findFirst()
                .orElseThrow(() -> new Exception ("Carro não encontrado"));
    }
}
