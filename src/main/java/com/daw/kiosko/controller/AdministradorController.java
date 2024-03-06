package com.daw.kiosko.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.model.Producto;
import com.daw.kiosko.service.IPedidoService;
import com.daw.kiosko.service.IProductoService;
import com.daw.kiosko.service.IUsuarioService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	@GetMapping("")
	public String home(Model model) {
		
		List<Producto> productos = productoService.findAll();
		model.addAttribute("productos", productos);
				
		return "administrador/home";
	}

	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		
		model.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}
	
	@GetMapping("/pedidos")
	public String pedidos(Model model) {
		
		model.addAttribute("pedidos", pedidoService.findAll());
		return "administrador/pedidos";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Integer id, Model model) {
		LOGGER.info("Id del pedido: {}", id);
		Pedido pedido = pedidoService.findById(id).get();
		
		model.addAttribute("detalles", pedido.getDetalle());
		return "administrador/detallepedido";
	}
}
