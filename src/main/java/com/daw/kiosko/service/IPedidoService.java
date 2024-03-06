package com.daw.kiosko.service;

import java.util.List;

import com.daw.kiosko.model.Pedido;

public interface IPedidoService {
	List<Pedido> findAll();
	Pedido save(Pedido pedido);
	String generarNumeroPedido();
}
