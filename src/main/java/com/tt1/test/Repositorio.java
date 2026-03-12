package com.tt1.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Repositorio de gestión de tareas
 */
class Repositorio implements IRepositorio
{
	private IDB db;

	/**
	 * Crea un repositorio sobre la base de datos pasada como parámetro.
	 * La base de datos es no nula.
	 *
	 * @param db la base de datos sobre la que crear el repositorio.
	 */
	public Repositorio(IDB db)
	{
		this.db = db;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToDo(IToDo toDo)
	{
		return db.addToDo(toDo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean completarToDo(IToDo toDo)
	{
		toDo.setCompletado(true);
		return db.updateToDo(toDo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IToDo> getUnfinishedToDos()
	{
		Collection<IToDo> toDos = db.getAllToDos();
		Collection<IToDo> unfinishedToDos = new ArrayList<>();

		for (IToDo toDo : toDos)
		{
			if (!toDo.isCompletado())
			{
				unfinishedToDos.add(toDo);
			}
		}

		return unfinishedToDos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IToDo> getExpiredToDos()
	{
		Collection<IToDo> toDos = db.getAllToDos();
		Collection<IToDo> expiredToDos = new ArrayList<>();
		Date now = new Date();

		for (IToDo toDo : toDos)
		{
			if (!toDo.isCompletado() && toDo.getFechaLimite().before(now))
			{
				expiredToDos.add(toDo);
			}
		}

		return expiredToDos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addEmail(String email)
	{
		return db.addEmail(email);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getAllEmails()
	{
		return db.getAllEmails();
	}
}
