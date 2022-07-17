package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.CarroDTO;
import br.com.dbc.dbcarapi.dto.FuncionarioCreateDTO;
import br.com.dbc.dbcarapi.dto.FuncionarioDTO;
import br.com.dbc.dbcarapi.entity.Carro;
import br.com.dbc.dbcarapi.entity.Cliente;
import br.com.dbc.dbcarapi.entity.Funcionario;
import br.com.dbc.dbcarapi.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FuncionarioService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<FuncionarioDTO> list() throws SQLException {
        return funcionarioRepository.list().stream()
                .map(funcionario -> objectMapper.convertValue(funcionario, FuncionarioDTO.class))
                .collect(Collectors.toList());
    }

    public FuncionarioDTO create(FuncionarioCreateDTO funcionario) throws SQLException {
        log.info("Adicionando o novo funcionário...");
        Funcionario funcionarioEntity = convertFuncionarioEntity(funcionario);
        Funcionario funcionarioCriado = funcionarioRepository.create(funcionarioEntity);
        FuncionarioDTO funcionarioDTO = convertFuncionarioDTO(funcionarioCriado);
        log.info("O novo funcionário " + funcionarioDTO.getNome() + " foi adicionado com sucesso.");
        return funcionarioDTO;
    }

    public FuncionarioDTO update(Integer idFuncionario, FuncionarioCreateDTO funcionarioCreateDTO) throws Exception {
        log.info("Atualizando dados do funcionário...");
        findByIdFuncionario(idFuncionario);
        Funcionario funcionarioEntity = convertFuncionarioEntity(funcionarioCreateDTO);
        Funcionario funcionarioAtualizar = funcionarioRepository.update(idFuncionario, funcionarioEntity);
        FuncionarioDTO funcionarioDTO = convertFuncionarioDTO(funcionarioAtualizar);
        funcionarioDTO.setIdFuncionario(idFuncionario);
        log.info("Dados do funcionário atualizados " + funcionarioAtualizar);
        return funcionarioDTO;
    }

    public void delete(Integer idFuncionario) throws SQLException {
        log.info("Removendo cliente...");
        Funcionario verifyFuncionario = funcionarioRepository.findById(idFuncionario);
        funcionarioRepository.delete(idFuncionario);
        log.info("O funcionário " + verifyFuncionario.getNome() + " foi removido com sucesso!");
    }

    public FuncionarioDTO findByIdFuncionario(Integer idFuncionario) throws Exception {
        Funcionario funcionarioRecuperado = funcionarioRepository.findById(idFuncionario);
        if(funcionarioRecuperado != null) {
            return convertFuncionarioDTO(funcionarioRecuperado);
        } else {
            throw new Exception("Carro não encontrado");
        }
    }

    public Funcionario convertFuncionarioEntity(FuncionarioCreateDTO funcionario) {
        return objectMapper.convertValue(funcionario, Funcionario.class);
    }

    public FuncionarioDTO convertFuncionarioDTO(Funcionario funcionarioEntity) {
        return objectMapper.convertValue(funcionarioEntity, FuncionarioDTO.class);
    }
}
