package com.tt1.test;

import java.util.Collection;

/**
 * Interfaz de los servicios que expone el programa de gestión de tareas.
 */
public interface IServicio
{
	/**
	 * Si la tarea pasada como parámetro tiene nombre no vacío, y descripción y fecha límite no nulas, añade la tarea
	 * pasada como parámetro a la base de datos.
	 * Comprueba si hay ToDos sin completar cuya fecha límite haya pasado y enviar automáticamente un correo de alerta a
	 * todas las direcciones de la agenda.
	 * Si se consigue añadir la tarea a la base de datos y se consiguen enviar los emails a todas las direcciones de la
	 * agenda devuelve cierto, en caso contrario devuelve falso.
	 *
	 * @param toDo la tarea a añadir a la base de datos.
	 * @return cierto si se añade la tarea a la base de datos, falso en caso contrario.
	 */
	boolean addToDo(IToDo toDo);

	/**
	 * Completa la tarea de la base de datos que es "igual" a la tarea pasada como parámetro.
	 * Comprueba si hay ToDos sin completar cuya fecha límite haya pasado y enviar automáticamente un correo de alerta a
	 * todas las direcciones de la agenda.
	 * Si se consigue modificar la tarea de la base de datos para marcarla como completada y se consiguen enviar los
	 * emails a todas las direcciones de la agenda devuelve cierto, en caso contrario devuelve falso.
	 *
	 * @param toDo la tarea "igual" a la de la base de datos a completar.
	 * @return cierto si se completa la tarea de la base de datos, falso en caso contrario.
	 */
	boolean completarToDo(IToDo toDo);

	/**
	 * Devuelve todas las tareas de la base de datos que están sin completar.
	 * Comprueba si hay ToDos sin completar cuya fecha límite haya pasado y enviar automáticamente un correo de alerta a
	 * todas las direcciones de la agenda.
	 *
	 * @return una colección con todas las tareas de la base de datos sin completar.
	 */
	Collection<IToDo> getUnfinishedToDos();

	/**
	 * Si el email valida la regex ^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$, añade el email pasado como parámetro a la base de
	 * datos.
	 * Comprueba si hay ToDos sin completar cuya fecha límite haya pasado y enviar automáticamente un correo de alerta a
	 * todas las direcciones de la agenda.
	 * Si se consigue añadir la tarea a la base de datos y se consiguen enviar los emails a todas las direcciones de la
	 * agenda devuelve cierto, falso en caso contrario.
	 *
	 * @param email el email a añadir a la base de datos.
	 * @return cierto si se añade el email a la base de datos, falso en caso contrario.
	 */
	boolean addEmail(String email);
}
