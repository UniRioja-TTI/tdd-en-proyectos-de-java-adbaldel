package com.tt1.test.mocks;

import com.tt1.test.IDB;
import com.tt1.test.IToDo;

import java.util.Collection;
import java.util.List;

public class DBFakeGetAllToDos implements IDB
{
	private Collection<IToDo> toDos;

	public DBFakeGetAllToDos(Collection<IToDo> toDos)
	{
		this.toDos = toDos;
	}

	@Override
	public boolean addToDo(IToDo toDo)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Collection<IToDo> getAllToDos()
	{
		return toDos;
	}

	@Override
	public boolean updateToDo(IToDo toDo)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean deleteToDo(IToDo toDo)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean addEmail(String email)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Collection<String> getAllEmails()
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean deleteEmail(String email)
	{
		throw new UnsupportedOperationException("Not supported.");
	}
}
