/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */

package ucan.edu.p12.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ucan.edu.p12.entities.dtos.PessoaDTO;
import ucan.edu.p12.services.PessoaService;

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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author controljp
 */
@RestController
@RequestMapping("/pessoa")
public class PessoaController {
 @Autowired
    PessoaService service;

    @GetMapping()
    public ResponseEntity<List<PessoaDTO>> findAll() {
        List<PessoaDTO> pessoas = this.service.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> save(@RequestBody PessoaDTO pessoaDTO)
    {
        pessoaDTO = service.save(pessoaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pk}").buildAndExpand(pessoaDTO.getPkPessoa()).toUri();
        return ResponseEntity.created(uri).body(pessoaDTO);
    }

    @GetMapping(value = "/{pkPessoa}")
    public ResponseEntity<PessoaDTO> findById(@PathVariable UUID pkPessoa)
    {
        PessoaDTO pessoaDTO = service.findById(pkPessoa);
        return ResponseEntity.ok().body(pessoaDTO);
    }

     @PutMapping(value = "/{pkPessoa}")
    public ResponseEntity<PessoaDTO> update(@PathVariable UUID pkPessoa, @RequestBody PessoaDTO pessoaDTO)
    {
        pessoaDTO = service.update(pkPessoa, pessoaDTO);
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @DeleteMapping(value = "/{pkPessoa}")
    public ResponseEntity<PessoaDTO> delete(@PathVariable UUID pkPessoa)
    {
        service.delete(pkPessoa);
        return ResponseEntity.noContent().build();
    }
}
