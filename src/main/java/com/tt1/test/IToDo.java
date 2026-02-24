package com.tt1.test;

import java.util.Date;

public interface IToDo
{
	String getNombre();

	void setNombre(String nombre);

	String getDescripcion();

	void setDescripcion(String descripcion);

	Date getFechaLimite();

	void setFechaLimite(Date fechaLimite);

	boolean isCompletado();

	void setCompletado(boolean completado);
}
