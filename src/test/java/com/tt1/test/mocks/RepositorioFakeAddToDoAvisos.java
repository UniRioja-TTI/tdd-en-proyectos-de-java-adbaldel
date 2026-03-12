package com.tt1.test.mocks;

import com.tt1.test.IRepositorio;
import com.tt1.test.IToDo;

import java.util.Collection;
import java.util.List;

public class RepositorioFakeAddToDoAvisos implements IRepositorio
{
	private boolean success;
	private Collection<IToDo> expiredToDos;
	private Collection<String> emails;


	public RepositorioFakeAddToDoAvisos(boolean success, Collection<IToDo> expiredToDos, Collection<String> emails)
	{
		this.success = success;
		this.expiredToDos = expiredToDos;
		this.emails = emails;
	}

	@Override
	public boolean addToDo(IToDo toDo)
	{
		return success;
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
		return expiredToDos;
	}

	@Override
	public boolean addEmail(String email)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public Collection<String> getAllEmails()
	{
		return emails;
	}
}
