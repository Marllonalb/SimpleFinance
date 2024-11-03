package com.simplefinance.simplefinance.service;

import com.simplefinance.simplefinance.dto.DespesasDTO;
import com.simplefinance.simplefinance.exception.CustomException;
import com.simplefinance.simplefinance.model.Despesas;
import com.simplefinance.simplefinance.repository.DespesasRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class DespesasService {

    private final DespesasRepository despesasRepository;

    public DespesasService(DespesasRepository despesasRepository){
        this.despesasRepository = despesasRepository;
    }

    @Transactional
    public DespesasDTO criarDespesa(DespesasDTO despesasDTO){

        if(despesasDTO.getVencimento() != null
        && despesasDTO.getVencimento().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("A data de vencimento não pode ser no passado.");

        }

        Despesas novaDespesa = converterDtoParaEntidade(despesasDTO);
        novaDespesa.setDataCriacao(LocalDateTime.now());
        novaDespesa.setDataModificacao(LocalDateTime.now());

        despesasRepository.save(novaDespesa);
        return mapToDTO(novaDespesa);
    }

    private Despesas converterDtoParaEntidade(DespesasDTO despesasDTO){
        Despesas despesas = new Despesas();
        despesas.setNome(despesasDTO.getNome());
        despesas.setVencimento(despesasDTO.getVencimento());
        despesas.setRepeticao(despesasDTO.getRepeticao());
        despesas.setValor(despesasDTO.getValor());
        return despesas;
    }

    public DespesasDTO atualizarDespesas(DespesasDTO despesasDTO, Long idDespesa){
        Optional<Despesas> despesasOptional = despesasRepository.findById(idDespesa);

        if(despesasOptional.isPresent()){
            Despesas despesaAtualizada = despesasOptional.get();

            if(despesasDTO.getVencimento() != null){
                despesaAtualizada.setVencimento(despesasDTO.getVencimento());
            }
            if(despesasDTO.getRepeticao() != null){
                despesaAtualizada.setRepeticao(despesasDTO.getRepeticao());
            }
            if(despesasDTO.getNome() != null){
                despesaAtualizada.setNome(despesasDTO.getNome());
            }
            if(despesasDTO.getValor() != null){
                despesaAtualizada.setValor(despesasDTO.getValor());
            }

            despesaAtualizada.setDataModificacao(LocalDateTime.now());
            despesasRepository.save(despesaAtualizada);
            return mapToDTO(despesaAtualizada);

        }else{
            throw new EntityNotFoundException("Despesa não encontrada com id: " + idDespesa);
        }

    }

    private DespesasDTO mapToDTO(Despesas despesa) {
        return new DespesasDTO(
                despesa.getValor(),
                despesa.getRepeticao(),
                despesa.getVencimento(),
                despesa.getNome()
        );
    }

    public void excluirDespesa(Long idDespesa){
        Optional<Despesas> despesa = despesasRepository.findById(idDespesa);
        if(despesa.isPresent()){
            despesasRepository.deleteById(idDespesa);
        }else{
            throw new CustomException("Despesa não encontrada com id: " + idDespesa);
        }

    }

    public DespesasDTO listarDespesaPorId(Long idDespesa){
        Despesas despesas = despesasRepository.findById(idDespesa)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada com id: " + idDespesa));
        return mapToDTO(despesas);
    }

    public List<DespesasDTO> listarDespesas() {
        List<Despesas> despesas = despesasRepository.findAll();
        return despesas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



}



