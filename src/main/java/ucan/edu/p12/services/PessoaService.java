/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */

package ucan.edu.p12.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ucan.edu.p12.entities.Localidade;
import ucan.edu.p12.entities.Pessoa;
import ucan.edu.p12.entities.Sexo;
import ucan.edu.p12.entities.Usuario;
import ucan.edu.p12.entities.dtos.PessoaDTO;
import ucan.edu.p12.repositories.LocalidadeRepository;
import ucan.edu.p12.repositories.PessoaRepository;
import ucan.edu.p12.repositories.SexoRepository;
import ucan.edu.p12.repositories.UsuarioRepository;
import ucan.edu.p12.services.exceptions.DatabaseException;
import ucan.edu.p12.services.exceptions.ResourceNotFoundException;

/**
 *
 * @author controljp
 */
@Service
public class PessoaService {
    @Autowired
    private PessoaRepository repository;

    @Autowired
    private SexoRepository sexoRepository;

    @Autowired
    private LocalidadeRepository localidadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PessoaDTO> findAll() {
        List<Pessoa> pessoas = repository.findAll();
        return PessoaDTO.converterListParaDTO(pessoas);
    }

    public PessoaDTO save(PessoaDTO pessoaDTO) {

        // Valida e obtém o objeto Sexo
        Sexo sexo = validateAndGetSexo(pessoaDTO.getFkSexo());

        // Valida e obtém o objeto Localidade
        Localidade localidade = validateAndGetLocalidade(pessoaDTO.getFkLocalNascimento());

        // Valida e obtém o objeto Usuario
        Usuario usuario = validateAndGetUsuario(pessoaDTO.getFkUsuario());

        Pessoa pessoa = PessoaDTO.toEntity(pessoaDTO);
        pessoa.setFkSexo(sexo);
        pessoa.setFkLocalNascimento(localidade);
        pessoa.setFkUsuario(usuario);
        Pessoa pessoaSalvo = repository.save(pessoa);
        return PessoaDTO.toDTO(pessoaSalvo);
    }

    public PessoaDTO findById(UUID pkPessoa) {
        Optional<Pessoa> optional = repository.findById(pkPessoa);
        Pessoa pessoa = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        return PessoaDTO.toDTO(pessoa);
    }

    public PessoaDTO update(UUID pkPessoa, PessoaDTO pessoaDTO) {
        // Busca a entidade Pessoa pelo ID ou lança exceção se não encontrada
        Pessoa pessoa = repository.findById(pkPessoa)
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa not found for ID: " + pkPessoa));
    
        // Valida e obtém o objeto Sexo, se informado
        Sexo sexo = validateAndGetSexo(pessoaDTO.getFkSexo());
    
        // Valida e obtém o objeto Localidade, se informado
        Localidade localidade = validateAndGetLocalidade(pessoaDTO.getFkLocalNascimento());

        // Valida e obtém o objeto Localidade, se informado
        Usuario usuario = validateAndGetUsuario(pessoaDTO.getFkUsuario());
    
        // Atualiza os campos da entidade Pessoa com os dados do DTO
        pessoa.setPrimeiroNome(pessoaDTO.getPrimeiroNome());
        pessoa.setUltimoNome(pessoaDTO.getUltimoNome());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setNumeroIdentificacao(pessoaDTO.getNumeroIdentificacao());
        pessoa.setFkSexo(sexo);
        pessoa.setFkLocalNascimento(localidade);
        pessoa.setFkUsuario(usuario);
    
        // Salva a entidade atualizada no banco de dados
        Pessoa pessoaAtualizada = repository.save(pessoa);
    
        // Retorna o DTO correspondente
        return PessoaDTO.toDTO(pessoaAtualizada);
    }

     public void delete(UUID pkPessoa) {
         try
        {
            Optional<Pessoa> optional = repository.findById(pkPessoa);
            Pessoa pessoa = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + pkPessoa));

            repository.delete(pessoa);

        } catch (DataIntegrityViolationException e)
        {
            throw new DatabaseException("Integrity violation occurred while attempting to delete entity");
        }
    }

    //Metodos adicionais
    private Sexo validateAndGetSexo(UUID fkSexo) {
        if (fkSexo != null) {
            return sexoRepository.findById(fkSexo)
                    .orElseThrow(() -> new ResourceNotFoundException("Sexo not found for ID: " + fkSexo));
        }
        return null; // Se fkSexo for null, não atribuímos um objeto Sexo.
    }

    private Localidade validateAndGetLocalidade(UUID fkLocalNascimento) {
        if (fkLocalNascimento != null) {
            return localidadeRepository.findById(fkLocalNascimento)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Localidade not found for ID: " + fkLocalNascimento));
        }
        return null; // Se fkLocalNascimento for null, não atribuímos um objeto Localidade.
    }

    private Usuario validateAndGetUsuario(UUID fkUsuario) {
        if (fkUsuario != null) {
            return usuarioRepository.findById(fkUsuario)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Usuario not found for ID: " + fkUsuario));
        }
        return null; // Se fkLocalNascimento for null, não atribuímos um objeto Localidade.
    }

   

     

}
