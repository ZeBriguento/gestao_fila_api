/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities;


import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author controljp
 */

@Entity
@Table
@Data
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pkPessoa;

    @Column(nullable = false)
    private String primeiroNome;

    @Column(nullable = false)
    private String ultimoNome;

    @Column(nullable = false, unique = true)
    private String numeroIdentificacao;

    @Column(nullable = false, unique = true)
    private String email;

    @JoinColumn(name = "fkUsuario", referencedColumnName = "pkUsuario", nullable = false)
    @ManyToOne
    private Usuario fkUsuario;

    @ManyToOne
    @JoinColumn(name = "fkSexo", referencedColumnName = "pkSexo", nullable = false)
    private Sexo fkSexo;
    
     @JoinColumn(name = "fkLocalNascimento", referencedColumnName = "pkLocalidade")
    @ManyToOne
    private Localidade fkLocalNascimento;

}