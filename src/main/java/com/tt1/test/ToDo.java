package com.tt1.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDo implements IToDo
{
	private String nombre;
	private String descripcion;
	private Date fechaLimite;
	private boolean completado;

	public ToDo()
	{
		this.completado = false;
	}

	public ToDo(String nombre, String descripcion, Date fechaLimite)
	{
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaLimite = fechaLimite;
		this.completado = false;
	}

	@Override
	public String getNombre()
	{
		return nombre;
	}

	@Override
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	@Override
	public String getDescripcion()
	{
		return descripcion;
	}

	@Override
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	@Override
	public Date getFechaLimite()
	{
		return fechaLimite;
	}

	@Override
	public void setFechaLimite(Date fechaLimite)
	{
		this.fechaLimite = fechaLimite;
	}

	@Override
	public boolean isCompletado()
	{
		return completado;
	}

	@Override
	public void setCompletado(boolean completado)
	{
		this.completado = completado;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		if (getClass() != obj.getClass())
		{
			return false;
		}

		ToDo other = (ToDo) obj;

		return other.getNombre().equals(this.getNombre());
	}

	@Override
	public int hashCode()
	{
		return nombre == null ? 0 : nombre.hashCode();
	}
}
