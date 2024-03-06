package com.daw.kiosko.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daw.kiosko.model.DetallePedido;
import com.daw.kiosko.model.Pedido;
import com.daw.kiosko.model.Producto;
import com.daw.kiosko.model.Usuario;
import com.daw.kiosko.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

import com.daw.kiosko.service.IDetallePedidoService;
import com.daw.kiosko.service.IPedidoService;
import com.daw.kiosko.service.IProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	@Autowired
	private IDetallePedidoService detallePedidoService;
	
	//almacenar datos pedido
	List<DetallePedido> detalles = new ArrayList<DetallePedido>();
	Pedido pedido = new Pedido();
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		LOGGER.info("Sesion del usuario: {}", session.getAttribute("idusuario"));
		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productohome(@PathVariable Integer id, Model model) {
		LOGGER.info("Id producto enviado como parametro {}", id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		DetallePedido detallePedido = new DetallePedido();
		Producto producto = new Producto();
		double total = 0;
		
		Optional<Producto> optionalProducto = productoService.get(id);
		LOGGER.info("Producto anadido: {}", optionalProducto.get());
		LOGGER.info("Cantidad: {}", cantidad);
		
		producto = optionalProducto.get();
		detallePedido.setCantidad(cantidad);
		detallePedido.setPrecio(producto.getPrecio());
		detallePedido.setNombre(producto.getNombre());
		detallePedido.setTotal(producto.getPrecio()*cantidad);
		detallePedido.setProducto(producto);
		
		//validar duplicado
		Integer idProducto = producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		
		if (!ingresado) {
			detalles.add(detallePedido);
		}
		
		total=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		pedido.setTotal(total);
		
		model.addAttribute("cart", detalles);
		model.addAttribute("pedido", pedido);		
		
		return "usuario/carrito";
	}
	
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		List<DetallePedido> pedidoNuevo = new ArrayList<DetallePedido>();
		
		for(DetallePedido detallePedido: detalles) {
			if (detallePedido.getProducto().getId() != id) {
				pedidoNuevo.add(detallePedido);
			}
		}
		
		detalles=pedidoNuevo;
		
		double total = 0;
		total=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		pedido.setTotal(total);
		model.addAttribute("cart", detalles);
		model.addAttribute("pedido", pedido);	
		
		return "usuario/carrito";
	}
	
	
	@GetMapping("/getCart")
	public String getCart(Model model) {
		model.addAttribute("cart", detalles);
		model.addAttribute("pedido", pedido);
		
		return "/usuario/carrito";
	}
	
	@GetMapping("/pedido")
	public String pedido(Model model, HttpSession session) {
		
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		model.addAttribute("cart", detalles);
		model.addAttribute("pedido", pedido);
		model.addAttribute("usuario", usuario);
		
		
		return "usuario/resumenpedido";
	}
	
	@GetMapping("/savePedido")
	public String savePedido(HttpSession session) {
		
		Date fechaCreacion = new Date();
		pedido.setFechaCreacion(fechaCreacion);
		pedido.setNumero(pedidoService.generarNumeroPedido());
		
		//usuario
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		pedido.setUsuario(usuario);
		pedidoService.save(pedido);
		
		//guardar
		for (DetallePedido dt:detalles) {
			dt.setPedido(pedido);
			detallePedidoService.save(dt);
		}
		
		//limpiar lista y pedido
		pedido = new Pedido();
		detalles.clear();
		
		
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model model) {
		LOGGER.info("Nombre del producto: {}", nombre);
		//filtra productos
		List<Producto> productos = productoService.findAll().stream().filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase())).collect(Collectors.toList());
		model.addAttribute("productos", productos);
		
		return "/usuario/home";
	}
}
