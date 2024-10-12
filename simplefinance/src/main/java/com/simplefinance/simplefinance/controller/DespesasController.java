package com.simplefinance.simplefinance.controller;

import com.simplefinance.simplefinance.dto.DespesasDTO;
import com.simplefinance.simplefinance.exception.CustomException;
import com.simplefinance.simplefinance.model.Despesas;
import com.simplefinance.simplefinance.service.DespesasService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping("/api/despesas")
public class DespesasController {

    private final DespesasService despesasService;

    public DespesasController (DespesasService despesasService){
        this.despesasService = despesasService;
    }

    @PostMapping
    public  ResponseEntity<Despesas> criarDespesa(@RequestBody DespesasDTO despesasDTO){
        try{
            Despesas novaDespesa = despesasService.criarDespesa(despesasDTO);
            return new ResponseEntity<>(novaDespesa, HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            throw new CustomException("Erro ao salvar a despesa", e);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }

    @PatchMapping("/{idDespesa}")
    public ResponseEntity<DespesasDTO> atualizacaoDespesa(@RequestBody DespesasDTO despesasDTO,
                                                          @PathVariable Long idDespesa){
        try{
            DespesasDTO despesaAtualizada = despesasService.atualizarDespesas(despesasDTO, idDespesa);
            return ResponseEntity.ok(despesaAtualizada);
        }catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }


    }

}
