package com.simplefinance.simplefinance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Despesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private Integer repeticao;

    @NotNull
    @CreatedDate
    private LocalDateTime dataCriacao;

    @NotNull
    @LastModifiedDate
    private LocalDateTime dataModificacao;

    @NotNull
    private LocalDateTime vencimento;

    @NotNull
    private String nome;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Despesas despesas = (Despesas) o;
        return id != null && id.equals(despesas.id);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }


}
