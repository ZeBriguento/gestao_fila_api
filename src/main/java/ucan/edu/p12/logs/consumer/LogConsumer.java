/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.logs.consumer;

/**
 *
 * @author controljp
 */
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ucan.edu.p12.entities.Log;
import ucan.edu.p12.repositories.LogRepository;
import ucan.edu.p12.services.LogService;

@Component
public class LogConsumer {

     private final LogRepository logRepository;
    private final LogService logService;

    public LogConsumer(LogRepository logRepository, LogService logService) {
        this.logRepository = logRepository;
        this.logService = logService;
    }

    @KafkaListener(topics = "topico-logs", groupId = "grupo-logs")
    public void consumirLog(String mensagem) {
        String[] partes = mensagem.split(" - ", 2);
        String nivel = partes[0];
        String conteudo = partes.length > 1 ? partes[1] : "Mensagem vazia";

        Log log = new Log(conteudo, nivel);
        logRepository.save(log);
        logService.enviarLog(conteudo);
    }

    
}

