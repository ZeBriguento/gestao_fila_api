/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.entities;

/**
 *
 * @author controljp
 */
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
public class Log {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pkLog;

    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private String nivel; // INFO, WARN, ERROR

    @Column(nullable = false)
    private LocalDateTime dataHora;

    public Log() {
        this.dataHora = LocalDateTime.now();
    }

    public Log(String mensagem, String nivel) {
        this.mensagem = mensagem;
        this.nivel = nivel;
        this.dataHora = LocalDateTime.now();
    }

    // Getters e Setters
}


