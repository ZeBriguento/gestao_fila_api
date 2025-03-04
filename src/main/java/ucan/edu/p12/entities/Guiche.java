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
import ucan.edu.p12.entities.enums.StatusGuicheEnum;

/**
 *
 * @author controljp
 */
@Data
@Entity
@Table
public class Guiche implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pkGuiche;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING) // Armazena o nome do enum no banco
    @Column(nullable = false)
    private StatusGuicheEnum status;

    @ManyToOne
    @JoinColumn(name = "fkUsuario", referencedColumnName = "pkUsuario", nullable = false)
    private Usuario fkUsuario;
}