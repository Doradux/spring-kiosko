package com.daw.kiosko.service;

import java.util.List;
import java.util.Optional;

import com.daw.kiosko.model.Producto;

public interface ProductoService {
	public Producto save (Producto producto);
	public Optional<Producto> get(Integer id);
	public void update(Producto producto);
	public void delete(int id);
	public List<Producto> findAll();
}
