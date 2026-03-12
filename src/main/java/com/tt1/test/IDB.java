package com.tt1.test;

import java.util.Collection;

/**
 * Interfaz para emulaciones de una base de datos de gestión de tareas y emails.
 */
public interface IDB
{
	/**
	 * Si no hay otra tarea igual en la base de datos, añade la tarea pasada como parámetro a la base de datos. Devuelve
	 * cierto si la tarea es añadida, falso si no se consigue añadir la tarea.
	 * La tarea añadida solo podrá ser modificada por la base de datos y sus métodos.
	 * La tarea pasada como parámetro tiene nombre, descripción y fecha límite no nulas.
	 *
	 * @param toDo la tarea a añadir a la base de datos
	 * @return cierto si se añade la tarea, falso en caso contrario.
	 */
	boolean addToDo(IToDo toDo);

	/**
	 * Devuelve todas las tareas almacenadas en la base de datos.
	 *
	 * @return todas las tareas almacenadas en la base de datos.
	 */
	Collection<IToDo> getAllToDos();

	/**
	 * Modifica la tarea de la base de datos que es "igual" a la tarea pasada como parámetro, para contener la misma
	 * información que esta última. Devuelve cierto si en la base de datos hay una tarea "igual" a la pasada como
	 * parámetro y se ha conseguido modificar su información, falso en caso contrario.
	 *
	 * @param toDo la tarea "igual" a la de la base de datos a modificar, con la información a actualizar
	 * @return cierto si se modifica la tarea en la base de datos, falso en caso contrario.
	 */
	boolean updateToDo(IToDo toDo);

	/**
	 * Elimina la tarea de la base de datos que es "igual" a la tarea pasada como parámetro. Devuelve cierto si en la
	 * base de datos hay una tarea "igual" a la pasada como parámetro y se ha conseguido eliminar, falso en caso
	 * contrario.
	 *
	 * @param toDo la tarea "igual" a la de la base de datos a eliminar.
	 * @return cierto si se elimina la tarea en la base de datos, falso en caso contrario.
	 */
	boolean deleteToDo(IToDo toDo);

	/**
	 * Si no hay otro email igual (sin importar diferencias en la capitalización) en la base de datos, añade el email a
	 * la base de datos. Devuelve cierto si se consigue añadir el email a la base de datos, falso en caso contrario.
	 * El email añadido solo podrá ser modificado por la base de datos y sus métodos.
	 * El email debe validar la regex ^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$
	 *
	 * @param email el email a añadir a la base de datos.
	 * @return cierto si se añade, falso en caso contrario.
	 */
	boolean addEmail(String email);

	/**
	 * Devuelve todos los emails almacenados en la base de datos.
	 *
	 * @return todos los emails almacenados en la base de datos.
	 */
	Collection<String> getAllEmails();

	/**
	 * Elimina el email de la base de datos que es igual (salvo capitalización) al email pasado como parámetro. Devuelve
	 * cierto si se consigue eliminar el email, falso en caso contario.
	 * El email debe validar la regex ^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$
	 *
	 * @param email el email igual al email a eliminar de la base de datos.
	 * @return cierto si se elimina, falso en caso contrario.
	 */
	boolean deleteEmail(String email);
}
