package com.tt1.test;

import java.util.Date;

/**
 * Entidad del modelo de dominio para almacenar y gestionar información sobre tareas.
 */
public interface IToDo
{
	/**
	 * Devuelve el nombre de la tarea.
	 *
	 * @return el nombre de la tarea.
	 */
	String getNombre();

	/**
	 * Cambia el nombre de la tarea por el pasado como parámetro.
	 * El nombre pasado como parámetro es no nulo.
	 *
	 * @param nombre el nuevo nombre de la tarea.
	 */
	void setNombre(String nombre);

	/**
	 * Devuelve la descripción de la tarea.
	 *
	 * @return la descripción de la tarea.
	 */
	String getDescripcion();

	/**
	 * Cambia la descripción de la tarea por la pasada como parámetro.
	 * La nueva descripción pasada como parámetro es no nula. La ausencia de descripción se indicará con una cadena
	 * vacía.
	 *
	 * @param descripcion la nueva descripción de la tarea.
	 */
	void setDescripcion(String descripcion);

	/**
	 * Devuelve la fecha límite de la tarea.
	 *
	 * @return la fecha límite de la tarea.
	 */
	Date getFechaLimite();

	/**
	 * Cambia la fecha límite de la tarea por la pasada como parámetro.
	 * La fecha límite pasada como parámetro es no nula.
	 *
	 * @param fechaLimite la nueva fecha límite de la tarea.
	 */
	void setFechaLimite(Date fechaLimite);

	/**
	 * Devuelve cierto si la tarea está completada, falso en caso contrario.
	 *
	 * @return cierto si la tarea está completada, falso en caso contrario.
	 */
	boolean isCompletado();

	/**
	 * Cambia el estado de la tarea por el pasado como parámetro. Si se pasa un true la tarea pasará a estar completada,
	 * si no, la tarea pasará a no estar completada.
	 *
	 *  @param completado el nuevo estado de la tarea.
	 */
	void setCompletado(boolean completado);
}
