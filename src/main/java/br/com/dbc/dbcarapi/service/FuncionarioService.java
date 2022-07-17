package br.com.dbc.dbcarapi.service;

import br.com.dbc.dbcarapi.dto.FuncionarioCreateDTO;
import br.com.dbc.dbcarapi.dto.FuncionarioDTO;
import br.com.dbc.dbcarapi.entity.Funcionario;
import br.com.dbc.dbcarapi.exception.BancoDeDadosException;
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
        log.info("Listando os funcionários da DBCar...");
        try {
            return funcionarioRepository.list().stream()
                    .map(funcionario -> convertFuncionarioDTO(funcionario))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
    }

    public FuncionarioDTO findByIdFuncionario(Integer idFuncionario) throws SQLException {
        Funcionario funcionarioRecuperado = funcionarioRepository.findByIdFuncionario(idFuncionario);
        if(funcionarioRecuperado != null) {
            return convertFuncionarioDTO(funcionarioRecuperado);
        } else {
            throw new SQLException("Funcionário não encontrado no banco de dados da DBCar.");
        }
    }

    public FuncionarioDTO create(FuncionarioCreateDTO funcionario) throws SQLException {
        log.info("Adicionando um novo funcionário ao sistema da DBCar...");
        Funcionario funcionarioEntity = convertFuncionarioEntity(funcionario);
        try {
            funcionarioEntity = funcionarioRepository.create(funcionarioEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        FuncionarioDTO funcionarioDTO = convertFuncionarioDTO(funcionarioEntity);
        log.info("O funcionário " + funcionarioDTO.getNome() + " foi adicionado com sucesso.");
        return funcionarioDTO;
    }

    public FuncionarioDTO update(Integer idFuncionario, FuncionarioCreateDTO funcionarioUpdate) throws SQLException {
        log.info("Atualizando os dados do funcionário...");
        findByIdFuncionario(idFuncionario);
        Funcionario funcionarioEntity = convertFuncionarioEntity(funcionarioUpdate);
        try {
            funcionarioEntity = funcionarioRepository.update(idFuncionario, funcionarioEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        }
        FuncionarioDTO funcionarioDTO = convertFuncionarioDTO(funcionarioEntity);
        funcionarioDTO.setIdFuncionario(idFuncionario);

        log.info("Os dados do funcionário " + funcionarioDTO.getNome() + " foram atualizados com sucesso!");
        return funcionarioDTO;
    }

    public void delete(Integer idFuncionario) throws SQLException {
        log.info("Removendo funcionário da DBCar...");
        try {
            Funcionario verifyFuncionario = funcionarioRepository.findByIdFuncionario(idFuncionario);
            funcionarioRepository.delete(idFuncionario);
            log.info("O funcionário " + verifyFuncionario.getNome() + " foi removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } catch (NullPointerException n) {
            throw new NullPointerException("O identificador (ID) informado não consta em nosso banco de dados!");
        }
    }

    public Funcionario convertFuncionarioEntity(FuncionarioCreateDTO funcionario) {
        return objectMapper.convertValue(funcionario, Funcionario.class);
    }

    public FuncionarioDTO convertFuncionarioDTO(Funcionario funcionarioEntity) {
        return objectMapper.convertValue(funcionarioEntity, FuncionarioDTO.class);
    }
}
