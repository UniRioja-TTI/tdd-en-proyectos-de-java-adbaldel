package com.tt1.test;

import java.util.Collection;

public class Repositorio implements IRepositorio
{
	public Repositorio(IDB dataBase)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public IToDo getToDo(IToDo todo)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public Collection<IToDo> getAllToDos()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public Collection<IToDo> getUnfinishedToDos()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public Collection<IToDo> getExpiredToDos()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void completarToDo(IToDo todo)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void addToDo(IToDo todo)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void addEmail(String email)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}
}
