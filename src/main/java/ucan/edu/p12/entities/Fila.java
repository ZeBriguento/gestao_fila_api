/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities;

import java.io.Serializable;
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
import jakarta.persistence.Table;
import lombok.Data;
import ucan.edu.p12.entities.enums.PrioridadeServicoEnum;

/**
 *
 * @author controljp
 */
@Data
@Entity
@Table
public class Fila implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pkFila;

    @ManyToOne
    @JoinColumn(name = "fkServico", referencedColumnName = "pkServico", nullable = false)
    private Servico fkServico;


    @ManyToOne
    @JoinColumn(name = "fkSenha", referencedColumnName = "pkSenha", nullable = false)
    private Senha fkSenha;
    
     @Enumerated(EnumType.STRING) // Armazena como texto no banco (ALTA, MEDIA, BAIXA)
    @Column(nullable = false)
    private PrioridadeServicoEnum prioridade;

}