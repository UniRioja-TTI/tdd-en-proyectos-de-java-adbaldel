package com.tt1.test;

import java.util.Collection;

/**
 * Interfaz de repositorio para gestión de tareas.
 */
public interface IRepositorio
{
	/**
	 * Añade la tarea pasada como parámetro a la base de datos. Si se consigue añadir la tarea a la base de datos
	 * devuelve cierto, en caso contrario devuelve falso.
	 * La tarea pasada como parámetro tiene nombre, descripción y fecha límite no nulas.
	 *
	 * @param toDo la tarea a añadir a la base de datos.
	 * @return cierto si se añade la tarea a la base de datos, falso en caso contrario.
	 */
	boolean addToDo(IToDo toDo);

	/**
	 * Completa la tarea de la base de datos que es "igual" a la tarea pasada como parámetro. Si se consigue modificar
	 * la tarea de la base de datos para marcarla como completada devuelve cierto, en caso contrario devuelve falso.
	 *
	 * @param toDo la tarea "igual" a la de la base de datos a completar.
	 * @return cierto si se completa la tarea de la base de datos, falso en caso contrario.
	 */
	boolean completarToDo(IToDo toDo);

	/**
	 * Devuelve todas las tareas de la base de datos que están sin completar.
	 *
	 * @return una colección con todas las tareas de la base de datos sin completar.
	 */
	Collection<IToDo> getUnfinishedToDos();

	/**
	 * Devuelve todas las tareas de la base de datos que están sin completar y su fecha límite ya ha pasado.
	 *
	 * @return una colección con todas las tareas de la base de datos sin completar cuya fecha límite ya haya pasado.
	 */
	Collection<IToDo> getExpiredToDos();

	/**
	 * Añade el email pasado como parámetro a la base de datos. Si se consigue añadir la tarea a la base de datos
	 * devuelve cierto, falso en caso contrario.
	 * El email debe validar la regex ^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$
	 *
	 * @param email el email a añadir a la base de datos.
	 * @return cierto si se añade el email a la base de datos, falso en caso contrario.
	 */
	boolean addEmail(String email);

	/**
	 * Devuelve todos los emails almacenados en la base de datos.
	 *
	 * @return todos los emails almacenados en la base de datos.
	 */
	Collection<String> getAllEmails();
}
