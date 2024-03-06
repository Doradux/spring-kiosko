package com.daw.kiosko.service;

import java.util.List;

import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.model.Usuario;

public interface IPedidoService {
	List<Pedido> findAll();
	Pedido save(Pedido pedido);
	String generarNumeroPedido();
	List<Pedido> findByUsuario(Usuario usuario);
}
