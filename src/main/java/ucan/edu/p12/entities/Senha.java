/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import ucan.edu.p12.entities.enums.StatusSenhaEnum;

/**
 *
 * @author controljp
 */
@Data
@Entity
@Table
public class Senha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pkSenha;

    @Column(nullable = false, unique = true)
    private String numero;

    @Enumerated(EnumType.STRING) // Armazena como texto no banco
    @Column(nullable = false)
    private StatusSenhaEnum status;

    @ManyToOne
    @JoinColumn(name = "fkUsuario", referencedColumnName = "pkUsuario", nullable = false)
    private Usuario fkUsuario;

    @ManyToOne
    @JoinColumn(name = "fkServico", referencedColumnName = "pkServico", nullable = false)
    private Servico fkServico;

    @ManyToOne
    @JoinColumn(name = "fkGuiche", referencedColumnName = "pkGuiche")
    private Guiche fkGuiche;

     @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;  

    private LocalDateTime dataFinalizacao; 

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    public void finalizarAtendimento() {
        this.dataFinalizacao = LocalDateTime.now();
    }

}
