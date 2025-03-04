/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucan.edu.p12.entities.Localidade;
import ucan.edu.p12.entities.dtos.LocalidadeDTO;
import ucan.edu.p12.repositories.LocalidadeRepository;

/**
 *
 * @author controljp
 */
@Service
public class LocalidadeService {

    @Autowired
    private LocalidadeRepository repository;

    public List<LocalidadeDTO> findAllPaises() {
        return LocalidadeDTO.converterListParaDTO(this.repository.findByfkLocalidadePaiIsNull());
    }

    public List<LocalidadeDTO> findAllByFkLocalidadePai(UUID pkLocalidadePai) {
        List<Localidade> localidades =  this.repository.findAllByFkLocalidadePai(pkLocalidadePai);
        return LocalidadeDTO.converterListParaDTO(localidades);
    }

    /*
     * public Localidade cadastrar(Localidade localidade){
     * this.repository.save(localidade);
     * return localidade;
     * }
     */

}
