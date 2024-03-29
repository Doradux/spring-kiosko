package com.daw.kiosko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.kiosko.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>{

}
