package com.daw.kiosko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.kiosko.model.Pedido;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Integer>{

}
