/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities;


import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
/**
 *
 * @author controljp
 */
@Table
@Entity
@Data
public class Localidade implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID pkLocalidade;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "fkLocalidadePai")
    @JsonIgnore
    private List<Localidade> localidadeList;

    @JoinColumn(name = "fkLocalidadePai", referencedColumnName = "pkLocalidade")
    @ManyToOne
    private Localidade fkLocalidadePai;
}
