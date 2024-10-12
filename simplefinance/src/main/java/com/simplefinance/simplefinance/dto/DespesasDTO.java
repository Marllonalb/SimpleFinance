package com.simplefinance.simplefinance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DespesasDTO {

    @NotNull
    private BigDecimal valor;

    @NotNull
    private Integer repeticao;

    @NotNull
    private LocalDateTime vencimento;

    @NotNull
    private String nome;


}
