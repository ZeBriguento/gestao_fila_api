/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import org.springframework.stereotype.Service;

/**
 *
 * @author controljp
 */
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class LogService {

    
    private final Sinks.Many<String> sink;

    public LogService() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void enviarLog(String mensagem) {
        sink.tryEmitNext(mensagem);
    }

    public Flux<String> receberLogs() {
        return sink.asFlux();
    }
}