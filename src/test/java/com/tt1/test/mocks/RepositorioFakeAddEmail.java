package com.tt1.test.mocks;

import com.tt1.test.IRepositorio;
import com.tt1.test.IToDo;

import java.util.Collection;
import java.util.List;

public class RepositorioFakeAddEmail implements IRepositorio
{
	private boolean success;

	public RepositorioFakeAddEmail(boolean success)
	{
		this.success = success;
	}

	@Override
	public boolean addToDo(IToDo toDo)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean completarToDo(IToDo toDo)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Collection<IToDo> getUnfinishedToDos()
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Collection<IToDo> getExpiredToDos()
	{
		return List.of();
	}

	@Override
	public boolean addEmail(String email)
	{
		return success;
	}

	@Override
	public Collection<String> getAllEmails()
	{
		return List.of();
	}
}
