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

import java.util.List;

@Controller
@Validated
@RequestMapping("/api/despesas")
public class DespesasController {

    private final DespesasService despesasService;

    public DespesasController (DespesasService despesasService){
        this.despesasService = despesasService;
    }

    @PostMapping
    public  ResponseEntity<DespesasDTO> criarDespesa(@RequestBody DespesasDTO despesasDTO){
        DespesasDTO novaDespesa = despesasService.criarDespesa(despesasDTO);
        return new ResponseEntity<>(novaDespesa, HttpStatus.CREATED);


    }

    @GetMapping("/{idDespesa}")
    public ResponseEntity<DespesasDTO> listarDespesaPorId(@PathVariable Long idDespesa){
            DespesasDTO despesasDTO = despesasService.listarDespesaPorId(idDespesa);
            return ResponseEntity.ok(despesasDTO);
    }

    @GetMapping
    public ResponseEntity<List<DespesasDTO>> listarDespesas(){
            List<DespesasDTO> despesasDTO = despesasService.listarDespesas();
            return ResponseEntity.ok(despesasDTO);


    }

    @PatchMapping("/{idDespesa}")
    public ResponseEntity<DespesasDTO> atualizacaoDespesa(@RequestBody DespesasDTO despesasDTO,
                                                          @PathVariable Long idDespesa){
            List<DespesasDTO> despesaAtualizada = (List<DespesasDTO>) despesasService.atualizarDespesas(despesasDTO, idDespesa);
            return ResponseEntity.ok((DespesasDTO) despesaAtualizada);



    }

    @DeleteMapping("/{idDespesa}")
    public ResponseEntity<Void> excluirDespesa(@PathVariable Long idDespesa){
            despesasService.excluirDespesa(idDespesa);
            return ResponseEntity.noContent().build();

    }

}
