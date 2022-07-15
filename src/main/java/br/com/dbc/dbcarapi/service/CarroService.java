package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.CarroCreateDTO;
import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
import br.com.dbc.dbcarapi.repository.CarroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> list() throws BancoDeDadosException {
        return carroRepository.list();
    }
    public CarroDTO create(CarroCreateDTO carro) throws BancoDeDadosException {
        Carro carroEntity = convertCarroEntity(carro);
        carroRepository.create(carroEntity);
        CarroDTO carroDTO = convertCarroDTO(carroEntity);
        return carroDTO;
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
