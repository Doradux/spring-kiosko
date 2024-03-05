package com.daw.kiosko.model;

import java.util.Date;

public class DetallePedido {
	private int id;
	private String numero;
	private Date fechaCreacion;
	private Date fechaRecibida;
	private double total;
	
	public DetallePedido() {
	}

	public DetallePedido(int id, String numero, Date fechaCreacion, Date fechaRecibida, double total) {
		super();
		this.id = id;
		this.numero = numero;
		this.fechaCreacion = fechaCreacion;
		this.fechaRecibida = fechaRecibida;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaRecibida() {
		return fechaRecibida;
	}

	public void setFechaRecibida(Date fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "DetallePedido [id=" + id + ", numero=" + numero + ", fechaCreacion=" + fechaCreacion
				+ ", fechaRecibida=" + fechaRecibida + ", total=" + total + "]";
	}

}
