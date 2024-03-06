package com.daw.kiosko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.kiosko.model.DetallePedido;
import com.daw.kiosko.repository.IDetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements IDetallePedidoService{

	@Autowired
	private IDetallePedidoRepository detallePedidoRepository;
	
	@Override
	public DetallePedido save(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}

}
