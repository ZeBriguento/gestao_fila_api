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
import ucan.edu.p12.entities.Sexo;
import ucan.edu.p12.entities.dtos.SexoDTO;
import ucan.edu.p12.repositories.SexoRepository;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;
import ucan.edu.p12.services.exceptions.DatabaseException;

/**
 *
 * @author controljp
 */
@Service
public class SexoService {

    @Autowired
    private SexoRepository repository;

    public List<SexoDTO> findAll() {
        List<Sexo> sexos = repository.findAll();
        return SexoDTO.converterListParaDTO(sexos);
    }

    public SexoDTO save(SexoDTO sexoDTO) {
        Sexo sexo = SexoDTO.toEntity(sexoDTO);
        Sexo sexoSalvo = repository.save(sexo);
        return SexoDTO.toDTO(sexoSalvo);
    }

    public SexoDTO findById(UUID pkSexo) {
        Optional<Sexo> optional = repository.findById(pkSexo);
        Sexo sexo = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        return SexoDTO.toDTO(sexo);
    }


    public SexoDTO update(UUID pkSexo, SexoDTO sexoDTO) {
        try {
            Sexo sexo = repository.getReferenceById(pkSexo);
            sexo.setDescricao(sexoDTO.getDescricao());
            sexo = repository.save(sexo);
            return SexoDTO.toDTO(sexo);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + pkSexo);
        }
    }

    public void delete(UUID pkSexo) {
         try
        {
            Optional<Sexo> optional = repository.findById(pkSexo);
            Sexo sexo = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + pkSexo));

            repository.delete(sexo);

        } catch (DataIntegrityViolationException e)
        {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }

 
}
