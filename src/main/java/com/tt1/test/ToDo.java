package com.tt1.test;

import java.util.Date;

/**
 * Implementación de la interfaz IToDo como JavaBean. Además, redefine el equals para especificar que dos tareas son
 * iguales si tienen el mismo nombre y fecha límite.
 */
public class ToDo implements IToDo
{
	private String nombre, descripcion;
	private Date fecha;
	private boolean completado;

	/**
	 * Crea una tarea con el nombre, descripción, fecha límite y estado pasados como parámetros.
	 *
	 * @param nombre      el nombre de la tarea.
	 * @param descripcion la descripción de la tarea.
	 * @param fechaLimite la fecha límite de la tarea.
	 * @param completado  cierto si la tarea está completada, falso en caso contrario.
	 */
	public ToDo(String nombre, String descripcion, Date fechaLimite, boolean completado)
	{
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fechaLimite;
		this.completado = completado;
	}

	/**
	 * Crea una tarea por hacer (sin completar) con el nombre, descripción y fecha límite pasados como parámetros.
	 *
	 * @param nombre      el nombre de la tarea.
	 * @param descripcion la descripción de la tarea.
	 * @param fechaLimite la fecha límite de la tarea.
	 */
	public ToDo(String nombre, String descripcion, Date fechaLimite)
	{
		this(nombre, descripcion, fechaLimite, false);
	}

	/**
	 * Crea una tarea por hacer (sin completar) con nombre, descripción y fecha límite nulos.
	 */
	public ToDo()
	{
		this(null, null, null, false);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNombre()
	{
		return nombre;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescripcion()
	{
		return descripcion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getFechaLimite()
	{
		return fecha;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFechaLimite(Date fechaLimite)
	{
		this.fecha = fechaLimite;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCompletado()
	{
		return completado;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCompletado(boolean completado)
	{
		this.completado = completado;
	}

	/**
	 * Compara esta tarea (this) con el objeto pasado como parámetro. Devuelve cierto si el objeto es igual a esta tarea
	 * (definido a continuación), falso en caso contrario. Un objeto es igual a una tarea si es una tarea con el mismo
	 * nombre, salvo diferencias en la capitalización, y la misma fecha límite.
	 *
	 * @param other la referencia al objeto con el que comparar.
	 * @return cierto si el objeto es "igual" falso en caso contrario.
	 */
	@Override
	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (other == null || this.getClass() != other.getClass())
			return false;

		ToDo otherToDo = (ToDo) other;

		return this.getNombre().equalsIgnoreCase(otherToDo.getNombre())
			&& this.getFechaLimite().equals(otherToDo.getFechaLimite());
	}
}
