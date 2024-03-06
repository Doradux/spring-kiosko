package com.daw.kiosko.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.model.Usuario;
import com.daw.kiosko.repository.IPedidoRepository;

@Service
public class PeidoServiceImpl implements IPedidoService {

	@Autowired
	private IPedidoRepository pedidoRepository;

	@Override
	public Pedido save(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}
	
	public String generarNumeroPedido() {
		Integer numero = 0;
		String numeroConcatenado = "";
		
		List<Pedido> pedidos = findAll();
		
		//por si DB devuelve en string
		List<Integer> numeros = new ArrayList<Integer>();
		
		pedidos.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero() ) ));
		
		if (pedidos.isEmpty()) {
			numero = 1;
		} else {
			//mayor numero de la lista
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}
		
		if (numero < 10) {
			numeroConcatenado = "00" + String.valueOf(numero);
		} else if (numero < 100) {
			numeroConcatenado = "0" + String.valueOf(numero);
		} else if (numero < 1000) {
			numeroConcatenado = "" + String.valueOf(numero);
		}
		
		return numeroConcatenado;
	}

	@Override
	public List<Pedido> findByUsuario(Usuario usuario) {
		return pedidoRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Pedido> findById(Integer id) {
		return pedidoRepository.findById(id);
	}

}
