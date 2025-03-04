/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import ucan.edu.p12.entities.Servico;
import ucan.edu.p12.entities.dtos.ServicoDTO;
import ucan.edu.p12.repositories.ServicoRepository;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;
import ucan.edu.p12.services.exceptions.DatabaseException;
/**
 *
 * @author controljp
 */
@Service
public class ServicoService {
@Autowired
    private ServicoRepository repository;

    public List<ServicoDTO> findAll() {
        List<Servico> servicos = repository.findAll();
        return ServicoDTO.converterListParaDTO(servicos);
    }

    public ServicoDTO save(ServicoDTO servicoDTO) {
        Servico servico = ServicoDTO.toEntity(servicoDTO);
        Servico servicoSalvo = repository.save(servico);
        return ServicoDTO.toDTO(servicoSalvo);
    }

    public ServicoDTO findById(UUID pkServico) {
        Optional<Servico> optional = repository.findById(pkServico);
        Servico servico = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        return ServicoDTO.toDTO(servico);
    }

    public ServicoDTO update(UUID pkServico, ServicoDTO servicoDTO) {
        try {
            Servico servico = repository.getReferenceById(pkServico);
            servico.setNome(servicoDTO.getNome());
            servico.setDescricao(servicoDTO.getDescricao());
            servico.setPrioridade(servicoDTO.getPrioridade());
            servico.setAtivo(servicoDTO.getAtivo());
            servico = repository.save(servico);
            return ServicoDTO.toDTO(servico);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + pkServico);
        }
    }

    public void delete(UUID pkServico) {
         try {
            Optional<Servico> optional = repository.findById(pkServico);
            Servico servico = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + pkServico));
            repository.delete(servico);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }
}
