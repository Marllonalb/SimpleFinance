package com.simplefinance.simplefinance.repository;

import com.simplefinance.simplefinance.model.Despesas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesasRepository extends JpaRepository <Despesas, Long> {
}
