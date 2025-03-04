/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import ucan.edu.p12.entities.dtos.LocalidadeDTO;
import ucan.edu.p12.services.LocalidadeService;

/**
 *
 * @author controljp
 */
@RestController
@RequestMapping("/localidade")
public class LocalidadeController {

    @Autowired
    private LocalidadeService service;

    @GetMapping("/paises")
    public ResponseEntity<List<LocalidadeDTO>> findAllPaises() {
        List<LocalidadeDTO> paises = this.service.findAllPaises();
        return ResponseEntity.ok(paises);
    }

    @GetMapping(value = "/subareas/{fkLocalidadePai}")
    public ResponseEntity<List<LocalidadeDTO>> findAllByFkLocalidadePai(@PathVariable UUID fkLocalidadePai) {
        List<LocalidadeDTO> localidades = this.service.findAllByFkLocalidadePai(fkLocalidadePai);
        return ResponseEntity.ok(localidades); 
    }

     /*
     * //
     * 
     * @PostMapping
     * public LocalidadeDTO cadastrar(@RequestBody LocalidadeDTO localidadeDTO) {
     * // Converte o DTO para a entidade e cadastra
     * Localidade localidade = LocalidadeDTO.toEntity(localidadeDTO);
     * return LocalidadeDTO.toDTO(this.service.cadastrar(localidade));
     * }
     */

}
