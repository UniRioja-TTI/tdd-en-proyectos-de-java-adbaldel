package com.tt1.test;

import java.sql.Array;
import java.util.*;

/**
 * Simulación de la base de datos para la gestión de tareas y emails utilizando listas no ordenadas.
 */
public class DBStub implements IDB
{
	private List<IToDo> toDos;
	private List<String> emails;

	public DBStub()
	{
		toDos = new ArrayList<>();
		emails = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToDo(IToDo toDo)
	{
		IToDo toDoCopy;

		if (!toDos.contains(toDo))
		{
			toDoCopy = new ToDo(toDo.getNombre(), toDo.getDescripcion(), toDo.getFechaLimite(), toDo.isCompletado());
			toDos.add(toDoCopy);
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IToDo> getAllToDos()
	{
		List<IToDo> toDosCopy = new ArrayList<>();
		IToDo toDoCopy;

		for (IToDo toDo : toDos)
		{
			toDoCopy = new ToDo(toDo.getNombre(), toDo.getDescripcion(), toDo.getFechaLimite(), toDo.isCompletado());
			toDosCopy.add(toDoCopy);
		}

		return toDosCopy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateToDo(IToDo toDo)
	{
		int i;

		if ((i = toDos.indexOf(toDo)) != -1)
		{
			toDos.set(i, toDo);
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deleteToDo(IToDo toDo)
	{
		return toDos.remove(toDo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addEmail(String email)
	{
		if (!emails.contains(email))
		{
			emails.add(email);
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getAllEmails()
	{
		return new ArrayList<>(emails);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deleteEmail(String email)
	{
		return emails.remove(email);
	}
}
