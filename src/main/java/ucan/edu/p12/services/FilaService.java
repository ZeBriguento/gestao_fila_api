/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ucan.edu.p12.entities.Fila;
import ucan.edu.p12.entities.Senha;
import ucan.edu.p12.entities.Servico;
import ucan.edu.p12.entities.dtos.FilaDTO;
import ucan.edu.p12.repositories.FilaRepository;
import ucan.edu.p12.repositories.SenhaRepository;
import ucan.edu.p12.repositories.ServicoRepository;
import ucan.edu.p12.services.exceptions.DatabaseException;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;

/**
 *
 * @author controljp
 */
@Service
public class FilaService {
    @Autowired
    private FilaRepository repository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private SenhaRepository senhaRepository;

    public List<FilaDTO> findAll() {
        List<Fila> filas = repository.findAll();
        return FilaDTO.converterListParaDTO(filas);
    }

    public FilaDTO save(FilaDTO filaDTO) {
        Servico servico = validateAndGetServico(filaDTO.getFkServico());
        Senha senha = validateAndGetSenha(filaDTO.getFkSenha());

        Fila fila = FilaDTO.toEntity(filaDTO);
        fila.setFkServico(servico);
        fila.setFkSenha(senha);

        Fila filaSalva = repository.save(fila);
        return FilaDTO.toDTO(filaSalva);
    }

    private Servico validateAndGetServico(UUID fkServico) {
        if (fkServico != null) {
            return servicoRepository.findById(fkServico)
                    .orElseThrow(() -> new ResourceNotFoundException("Servico not found for ID: " + fkServico));
        }
        return null;
    }

    private Senha validateAndGetSenha(UUID fkSenha) {
        if (fkSenha != null) {
            return senhaRepository.findById(fkSenha)
                    .orElseThrow(() -> new ResourceNotFoundException("Senha not found for ID: " + fkSenha));
        }
        return null;
    }

    public FilaDTO findById(UUID pkFila) {
        Fila fila = repository.findById(pkFila)
                .orElseThrow(() -> new ResourceNotFoundException("Fila not found for ID: " + pkFila));
        return FilaDTO.toDTO(fila);
    }

    public FilaDTO update(UUID pkFila, FilaDTO filaDTO) {
        Fila fila = repository.findById(pkFila)
                .orElseThrow(() -> new ResourceNotFoundException("Fila not found for ID: " + pkFila));

        Servico servico = validateAndGetServico(filaDTO.getFkServico());
        Senha senha = validateAndGetSenha(filaDTO.getFkSenha());

        fila.setFkServico(servico);
        fila.setFkSenha(senha);
        fila.setPrioridade(filaDTO.getPrioridade());

        Fila filaAtualizada = repository.save(fila);
        return FilaDTO.toDTO(filaAtualizada);
    }

    public void delete(UUID pkFila) {
        try {
            Fila fila = repository.findById(pkFila)
                    .orElseThrow(() -> new ResourceNotFoundException("Fila not found for ID: " + pkFila));
            repository.delete(fila);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }

    
    
    

}
