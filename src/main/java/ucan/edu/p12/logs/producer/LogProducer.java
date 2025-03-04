/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ucan.edu.p12.logs.producer;

/**
 *
 * @author controljp
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {

    @Value("${topico.logs}")
    private String topicoLogs;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LogProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarLog(String mensagem, String nivel) {
        String logFormatado = nivel + " - " + mensagem;
        kafkaTemplate.send(topicoLogs, logFormatado);
    }
}

