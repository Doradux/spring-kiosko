package com.daw.kiosko.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.model.Usuario;
import com.daw.kiosko.service.IPedidoService;
import com.daw.kiosko.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	@GetMapping("/registro")
	public String registro() {
		return "/usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		LOGGER.info("Usuario registro {}", usuario);
		usuario.setTipo("USER");
		usuarioService.save(usuario);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/usuario/login";
	}
	
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		LOGGER.info("Acceso : {}", usuario);
		
		Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
		LOGGER.info("Usuario obtenido: {}", user);
		
		if (user.isPresent()) {
			session.setAttribute("idusuario", user.get().getId());
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
			}
		} else {
			LOGGER.info("USUARIO NO EXISTE");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/compras")
	public String compras(HttpSession session, Model model) {
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Pedido> pedidos = pedidoService.findByUsuario(usuario);	
		
		model.addAttribute("pedidos", pedidos);
		return "/usuario/compras";
	}
	
	@GetMapping("detalle/{id}")
	public String detalle(@PathVariable Integer id, HttpSession session, Model model) {
		
		model.addAttribute("session", session.getAttribute("idusuario"));
		LOGGER.info("Id del pedido: {}", id);
		
		Optional<Pedido> pedido = pedidoService.findById(id);
		
		model.addAttribute("detalles", pedido.get().getDetalle());
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}
