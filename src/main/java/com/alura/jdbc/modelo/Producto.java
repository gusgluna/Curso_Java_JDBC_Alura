package com.alura.jdbc.modelo;

public class Producto {

	private Integer id;
	
	private String nombre;
	
	private String descripcion;
	
	private Integer cantidad;
	
	public Producto(String nombre, String descipcion, Integer cantidad) {
		this.nombre = nombre;
		this.descripcion = descipcion;
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad
				+ "]";
	}
	
	
}