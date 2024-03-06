package com.daw.kiosko.service;

import java.util.List;
import java.util.Optional;

import com.daw.kiosko.model.Usuario;

public interface IUsuarioService {
	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);
	Optional<Usuario> findByEmail(String email);
	List<Usuario> findAll();

}
