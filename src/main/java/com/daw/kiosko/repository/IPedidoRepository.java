package com.daw.kiosko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.model.Usuario;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Integer>{
	List<Pedido> findByUsuario(Usuario usuario);
}
