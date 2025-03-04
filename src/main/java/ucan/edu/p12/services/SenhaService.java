/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import ucan.edu.p12.entities.Fila;
import ucan.edu.p12.entities.Guiche;
import ucan.edu.p12.entities.Senha;
import ucan.edu.p12.entities.Servico;
import ucan.edu.p12.entities.Usuario;
import ucan.edu.p12.entities.dtos.AtendimentosPorServicoDTO;
import ucan.edu.p12.entities.dtos.SenhaDTO;
import ucan.edu.p12.entities.enums.PrioridadeServicoEnum;
import ucan.edu.p12.entities.enums.StatusGuicheEnum;
import ucan.edu.p12.entities.enums.StatusSenhaEnum;
import ucan.edu.p12.logs.producer.LogProducer;
import ucan.edu.p12.repositories.FilaRepository;
import ucan.edu.p12.repositories.GuicheRepository;
import ucan.edu.p12.repositories.SenhaRepository;
import ucan.edu.p12.repositories.ServicoRepository;
import ucan.edu.p12.repositories.UsuarioRepository;
import ucan.edu.p12.services.exceptions.DatabaseException;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;

/**
 *
 * @author controljp
 */
@Service
public class SenhaService {
    @Autowired
    private SenhaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private GuicheRepository guicheRepository;

    @Autowired
    private SenhaRepository senhaRepository;
    @Autowired
    private FilaRepository filaRepository;

    @Autowired
    private LogProducer logProducer; 

    public List<AtendimentosPorServicoDTO> contarAtendimentosPorServico(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Object[]> resultados = senhaRepository.contarAtendimentosPorServicoRaw(dataInicio, dataFim);

        // Convertendo os resultados brutos para DTOs
        return resultados.stream()
            .map(obj -> new AtendimentosPorServicoDTO(
                dataInicio, 
                dataFim, 
                (String) obj[0], 
                (Long) obj[1]
            ))
            .collect(Collectors.toList());
    }
    
    public List<SenhaDTO> findAll() {
        List<Senha> senhas = repository.findAll();
        return SenhaDTO.converterListParaDTO(senhas);
    }

    public SenhaDTO save(SenhaDTO senhaDTO) {
        Usuario usuario = validateAndGetUsuario(senhaDTO.getFkUsuario());
        Servico servico = validateAndGetServico(senhaDTO.getFkServico());
        Guiche guiche = validateAndGetGuiche(senhaDTO.getFkGuiche());

        Senha senha = SenhaDTO.toEntity(senhaDTO);
        senha.setFkUsuario(usuario);
        senha.setFkServico(servico);
        senha.setFkGuiche(guiche);

        Senha senhaSalva = repository.save(senha);
        return SenhaDTO.toDTO(senhaSalva);
    }

    private Usuario validateAndGetUsuario(UUID fkUsuario) {
        if (fkUsuario != null) {
            return usuarioRepository.findById(fkUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario not found for ID: " + fkUsuario));
        }
        return null;
    }

    private Servico validateAndGetServico(UUID fkServico) {
        if (fkServico != null) {
            return servicoRepository.findById(fkServico)
                    .orElseThrow(() -> new ResourceNotFoundException("Servico not found for ID: " + fkServico));
        }
        return null;
    }

    private Guiche validateAndGetGuiche(UUID fkGuiche) {
        if (fkGuiche != null) {
            return guicheRepository.findById(fkGuiche)
                    .orElseThrow(() -> new ResourceNotFoundException("Guiche not found for ID: " + fkGuiche));
        }
        return null;
    }

    public SenhaDTO findById(UUID pkSenha) {
        Senha senha = repository.findById(pkSenha)
                .orElseThrow(() -> new ResourceNotFoundException("Senha not found for ID: " + pkSenha));
        return SenhaDTO.toDTO(senha);
    }

    public SenhaDTO update(UUID pkSenha, SenhaDTO senhaDTO) {
        Senha senha = repository.findById(pkSenha)
                .orElseThrow(() -> new ResourceNotFoundException("Senha not found for ID: " + pkSenha));

        Usuario usuario = validateAndGetUsuario(senhaDTO.getFkUsuario());
        Servico servico = validateAndGetServico(senhaDTO.getFkServico());
        Guiche guiche = validateAndGetGuiche(senhaDTO.getFkGuiche());

        senha.setNumero(senhaDTO.getNumero());
        senha.setStatus(senhaDTO.getStatus());
        senha.setFkUsuario(usuario);
        senha.setFkServico(servico);
        senha.setFkGuiche(guiche);

        Senha senhaAtualizada = repository.save(senha);
        return SenhaDTO.toDTO(senhaAtualizada);
    }

    public void delete(UUID pkSenha) {
        try {
            Senha senha = repository.findById(pkSenha)
                    .orElseThrow(() -> new ResourceNotFoundException("Senha not found for ID: " + pkSenha));
            repository.delete(senha);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }

    public SenhaDTO gerarSenha(UUID fkServico, UUID fkUsuario) {
        try {
            Servico servico = servicoRepository.findById(fkServico)
                    .orElseThrow(() -> {
                        logProducer.enviarLog("Erro: Serviço não encontrado para o ID: " + fkServico, "ERROR");
                        return new EntityNotFoundException("Serviço não encontrado para o ID: " + fkServico);
                    });

            Usuario usuario = usuarioRepository.findById(fkUsuario)
                    .orElseThrow(() -> {
                        logProducer.enviarLog("Erro: Usuário não encontrado para o ID: " + fkUsuario, "ERROR");
                        return new EntityNotFoundException("Usuário não encontrado para o ID: " + fkUsuario);
                    });

            String numeroSenha = gerarNumeroSenha(servico);

            Senha senha = new Senha();
            senha.setNumero(numeroSenha);
            senha.setStatus(StatusSenhaEnum.PENDENTE);
            senha.setFkServico(servico);
            senha.setFkUsuario(usuario);
            senha = senhaRepository.save(senha);

            Fila fila = new Fila();
            fila.setFkServico(servico);
            fila.setFkSenha(senha);
            fila.setPrioridade(PrioridadeServicoEnum.MEDIA);
            filaRepository.save(fila);

            logProducer.enviarLog("Senha gerada: " + numeroSenha + " para o serviço " + servico.getNome(), "INFO");

            return SenhaDTO.toDTO(senha);
        } catch (Exception e) {
            logProducer.enviarLog("Erro ao gerar senha: " + e.getMessage(), "ERROR");
            throw e; 
        }
    }

    private String gerarNumeroSenha(Servico servico) {
        long count = senhaRepository.countByFkServicoPkServico(servico.getPkServico());
        return servico.getNome().substring(0, 1).toUpperCase() + String.format("%03d", count + 1);
    }

    public int obterPosicaoNaFila(String numeroSenha) {
        logProducer.enviarLog("Consultando posição na fila para a senha: " + numeroSenha, "INFO");

        Senha senha = senhaRepository.findByNumero(numeroSenha)
                .orElseThrow(() -> {
                    logProducer.enviarLog("Erro: Senha " + numeroSenha + " não encontrada!", "ERROR");
                    return new EntityNotFoundException("Senha não encontrada");
                });

        logProducer.enviarLog("Senha encontrada: " + senha.getNumero() + ", buscando posição na fila...", "INFO");

        try {
            int posicao = filaRepository.findPosicaoNaFila2(
                    senha.getFkServico().getPkServico(),
                    senha.getNumero()) + 1;

            logProducer.enviarLog("Senha " + senha.getNumero() + " está na posição: " + posicao, "INFO");

            return posicao;
        } catch (Exception e) {
            logProducer.enviarLog(
                    "Erro ao obter posição na fila para a senha " + senha.getNumero() + ": " + e.getMessage(), "ERROR");
            throw new RuntimeException("Erro ao consultar posição na fila.");
        }
    }

    public SenhaDTO concluirAtendimento(UUID pkSenha, UUID fkGuiche) {
        logProducer.enviarLog("Iniciando conclusão do atendimento para a senha: " + pkSenha, "INFO");

        Senha senha = senhaRepository.findById(pkSenha)
                .orElseThrow(() -> new ResourceNotFoundException("Senha não encontrada!"));

        logProducer.enviarLog("Senha encontrada: " + senha.getNumero(), "INFO");

        // Verifica se a senha está em atendimento
        if (senha.getStatus() != StatusSenhaEnum.EM_ATENDIMENTO) {
            logProducer.enviarLog("Erro: Senha " + senha.getNumero() + " não está em atendimento!", "ERROR");
            throw new RuntimeException("Senha não está em atendimento!");
        }

        // Verifica se o guichê está vinculado a esta senha
        if (!senha.getFkGuiche().getPkGuiche().equals(fkGuiche)) {
            logProducer.enviarLog(
                    "Erro: Guichê " + fkGuiche + " não corresponde ao atendimento da senha " + senha.getNumero(),
                    "ERROR");
            throw new RuntimeException("Guichê não corresponde ao atendimento desta senha!");
        }

        senha.setStatus(StatusSenhaEnum.CONCLUIDO);
        senha.setDataFinalizacao(LocalDateTime.now());
        senhaRepository.save(senha);
        logProducer.enviarLog("Senha " + senha.getNumero() + " marcada como CONCLUÍDA.", "INFO");

        Guiche guiche = senha.getFkGuiche();
        guiche.setStatus(StatusGuicheEnum.ATIVO);
        guicheRepository.save(guiche);
        logProducer.enviarLog("Guichê " + guiche.getNome() + " liberado para próximo atendimento.", "INFO");

        return SenhaDTO.toDTO(senha);
    }

    public SenhaDTO cancelarSenha(UUID pkSenha, UUID fkGuiche) {
        logProducer.enviarLog("Iniciando cancelamento da senha: " + pkSenha, "INFO");

        Senha senha = senhaRepository.findById(pkSenha)
                .orElseThrow(() -> new ResourceNotFoundException("Senha não encontrada!"));

        logProducer.enviarLog("Senha encontrada: " + senha.getNumero(), "INFO");

        if (senha.getStatus() == StatusSenhaEnum.CONCLUIDO) {
            logProducer.enviarLog(
                    "Erro: Tentativa de cancelar a senha " + senha.getNumero() + " que já está CONCLUÍDA!", "ERROR");
            throw new RuntimeException("Não é possível cancelar uma senha concluída!");
        }

        // Verifica se o guichê está vinculado à senha (caso esteja em atendimento)
        if (senha.getStatus() == StatusSenhaEnum.EM_ATENDIMENTO) {
            if (!senha.getFkGuiche().getPkGuiche().equals(fkGuiche)) {
                logProducer.enviarLog(
                        "Erro: Guichê " + fkGuiche + " não corresponde ao atendimento da senha " + senha.getNumero(),
                        "ERROR");
                throw new RuntimeException("Guichê informado não corresponde ao atendimento desta senha!");
            }

            // Libera o guichê
            Guiche guiche = senha.getFkGuiche();
            guiche.setStatus(StatusGuicheEnum.ATIVO);
            guicheRepository.save(guiche);
            logProducer.enviarLog(
                    "Guichê " + guiche.getNome() + " liberado após cancelamento da senha " + senha.getNumero(), "INFO");
        }

        senha.setStatus(StatusSenhaEnum.CANCELADO);
        senhaRepository.save(senha);
        logProducer.enviarLog("Senha " + senha.getNumero() + " foi CANCELADA.", "INFO");

        return SenhaDTO.toDTO(senha);
    }

    public SenhaDTO chamarProximaSenha(UUID fkGuiche) {
        logProducer.enviarLog("Iniciando chamada da próxima senha para o guichê: " + fkGuiche, "INFO");

        Guiche guiche = guicheRepository.findById(fkGuiche)
                .orElseThrow(() -> new ResourceNotFoundException("Guichê não encontrado!"));

        logProducer.enviarLog("Guichê encontrado: " + guiche.getNome(), "INFO");

        if (guiche.getStatus() != StatusGuicheEnum.ATIVO) {
            logProducer.enviarLog("Erro: Guichê " + guiche.getNome() + " não está disponível!", "ERROR");
            throw new RuntimeException("Guichê não está disponível!");
        }

        List<Fila> filas = repository.buscarProximaSenhaNaFila();

        if (filas.isEmpty()) {
            logProducer.enviarLog("Nenhuma senha disponível na fila!", "WARN");
            throw new RuntimeException("Nenhuma senha na fila!");
        }

        Senha senha = filas.get(0).getFkSenha();
        logProducer.enviarLog("Senha " + senha.getNumero() + " chamada para atendimento no guichê " + guiche.getNome(),
                "INFO");

        senha.setStatus(StatusSenhaEnum.EM_ATENDIMENTO);
        senha.setFkGuiche(guiche);
        senhaRepository.save(senha);

        logProducer.enviarLog("Senha " + senha.getNumero() + " atualizada para EM_ATENDIMENTO.", "INFO");

        guiche.setStatus(StatusGuicheEnum.OCUPADO);
        guicheRepository.save(guiche);

        logProducer.enviarLog("Guichê " + guiche.getNome() + " atualizado para OCUPADO.", "INFO");

        return SenhaDTO.toDTO(senha);
    }

    public List<SenhaDTO> listarSenhasPendentes() {
        List<Fila> filas = repository.listarSenhasPendentes();
        logProducer.enviarLog("Senhas:" + " Senhas pendentes listadas com sucesso.", "INFO");
        return filas.stream()
                .map(fila -> SenhaDTO.toDTO(fila.getFkSenha()))
                .collect(Collectors.toList());
    }

}
