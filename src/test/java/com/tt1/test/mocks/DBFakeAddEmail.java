package com.tt1.test.mocks;

import com.tt1.test.IDB;
import com.tt1.test.IToDo;

import java.util.Collection;
import java.util.List;

public class DBFakeAddEmail implements IDB
{
	private boolean success;

	public DBFakeAddEmail(boolean success)
	{
		this.success = success;
	}

	@Override
	public boolean addToDo(IToDo toDo)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Collection<IToDo> getAllToDos()
	{
		throw new UnsupportedOperationException("Not supported.");
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
		return success;
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
