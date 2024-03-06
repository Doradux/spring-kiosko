package com.daw.kiosko.service;

import java.util.List;
import java.util.Optional;

import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.model.Usuario;

public interface IPedidoService {
	List<Pedido> findAll();
	Pedido save(Pedido pedido);
	String generarNumeroPedido();
	List<Pedido> findByUsuario(Usuario usuario);
	Optional<Pedido> findById(Integer id);
}
