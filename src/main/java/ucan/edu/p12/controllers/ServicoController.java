/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.ServicoDTO;
import ucan.edu.p12.services.ServicoService;

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
@RequestMapping("/servico")
public class ServicoController {
    @Autowired
    private ServicoService service;

    @GetMapping()
    public ResponseEntity<List<ServicoDTO>> findAll() {
        List<ServicoDTO> servicos = this.service.findAll();
        return ResponseEntity.ok(servicos);
    }

    @PostMapping
    public ResponseEntity<ServicoDTO> save(@RequestBody ServicoDTO servicoDTO) {
        servicoDTO = service.save(servicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pk}").buildAndExpand(servicoDTO.getPkServico()).toUri();
        return ResponseEntity.created(uri).body(servicoDTO);
    }

    @GetMapping(value = "/{pkServico}")
    public ResponseEntity<ServicoDTO> findById(@PathVariable UUID pkServico) {
        ServicoDTO servicoDTO = service.findById(pkServico);
        return ResponseEntity.ok().body(servicoDTO);
    }

    @PutMapping(value = "/{pkServico}")
    public ResponseEntity<ServicoDTO> update(@PathVariable UUID pkServico, @RequestBody ServicoDTO servicoDTO) {
        servicoDTO = service.update(pkServico, servicoDTO);
        return ResponseEntity.ok().body(servicoDTO);
    }

    @DeleteMapping(value = "/{pkServico}")
    public ResponseEntity<Void> delete(@PathVariable UUID pkServico) {
        service.delete(pkServico);
        return ResponseEntity.noContent().build();
    }
}
