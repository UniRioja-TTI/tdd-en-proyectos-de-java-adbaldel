package com.tt1.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDo implements IToDo
{
	public ToDo()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	public ToDo(String nombre, String descripcion, Date fechaLimite)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public String getNombre()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void setNombre(String nombre)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public String getDescripcion()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void setDescripcion(String descripcion)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public Date getFechaLimite()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void setFechaLimite(Date fechaLimite)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public boolean isCompletado()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void setCompletado(boolean completado)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
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
}
