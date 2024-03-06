package com.daw.kiosko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.repository.IPedidoRepository;

@Service
public class PeidoServiceImpl implements IPedidoService {

	@Autowired
	private IPedidoRepository pedidoRepository;

	@Override
	public Pedido save(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

}
