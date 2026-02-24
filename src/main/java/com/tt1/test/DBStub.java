package com.tt1.test;

import java.util.Collection;
import java.util.List;

public class DBStub implements IDB
{
	@Override
	public void insertToDo(IToDo todo)
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
	public void updateToDo(IToDo todo)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void deleteToDo(IToDo todo)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void insertEmail(String email)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public String getEmail(String email)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public Collection<String> getAllEmails()
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void updateEmail(String email)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}

	@Override
	public void deleteEmail(String original)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}
}
