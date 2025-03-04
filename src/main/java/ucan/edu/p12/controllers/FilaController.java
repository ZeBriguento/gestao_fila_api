/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.FilaDTO;
import ucan.edu.p12.services.FilaService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author controljp
 */
@RestController
@RequestMapping("/fila")

public class FilaController {
    @Autowired
    private FilaService service;

    @GetMapping()
    public ResponseEntity<List<FilaDTO>> findAll() {
        List<FilaDTO> filas = this.service.findAll();
        return ResponseEntity.ok(filas);
    }

    @PostMapping
    public ResponseEntity<FilaDTO> save(@RequestBody FilaDTO filaDTO) {
        filaDTO = service.save(filaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pk}").buildAndExpand(filaDTO.getPkFila()).toUri();
        return ResponseEntity.created(uri).body(filaDTO);
    }

    @GetMapping(value = "/{pkFila}")
    public ResponseEntity<FilaDTO> findById(@PathVariable UUID pkFila) {
        FilaDTO filaDTO = service.findById(pkFila);
        return ResponseEntity.ok().body(filaDTO);
    }

    @PutMapping(value = "/{pkFila}")
    public ResponseEntity<FilaDTO> update(@PathVariable UUID pkFila, @RequestBody FilaDTO filaDTO) {
        filaDTO = service.update(pkFila, filaDTO);
        return ResponseEntity.ok().body(filaDTO);
    }

    @DeleteMapping(value = "/{pkFila}")
    public ResponseEntity<Void> delete(@PathVariable UUID pkFila) {
        service.delete(pkFila);
        return ResponseEntity.noContent().build();
    }

   
}

