package com.simplefinance.simplefinance.service;

import com.simplefinance.simplefinance.dto.DespesasDTO;
import com.simplefinance.simplefinance.exception.CustomException;
import com.simplefinance.simplefinance.model.Despesas;
import com.simplefinance.simplefinance.repository.DespesasRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.time.LocalDateTime;

@Service
public class DespesasService {

    private final DespesasRepository despesasRepository;

    public DespesasService(DespesasRepository despesasRepository){
        this.despesasRepository = despesasRepository;
    }

    @Transactional
    public Despesas criarDespesa(DespesasDTO despesasDTO){

        if(despesasDTO.getVencimento() != null
        & despesasDTO.getVencimento().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("A data de vencimento não pode ser no passado.");

        }
        Despesas novaDespesa = new Despesas();

        novaDespesa.setNome(despesasDTO.getNome());
        novaDespesa.setValor(despesasDTO.getValor());
        novaDespesa.setRepeticao(despesasDTO.getRepeticao());
        novaDespesa.setVencimento(despesasDTO.getVencimento());
        novaDespesa.setDataCriacao(LocalDateTime.now());
        novaDespesa.setDataModificacao(LocalDateTime.now());

        return despesasRepository.save(novaDespesa);
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
            throw new CustomException("Despesa não encontrada com id: " + idDespesa);
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



}



