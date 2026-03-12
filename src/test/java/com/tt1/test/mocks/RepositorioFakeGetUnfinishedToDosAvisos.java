package com.tt1.test.mocks;

import com.tt1.test.IRepositorio;
import com.tt1.test.IToDo;

import java.util.Collection;
import java.util.List;

public class RepositorioFakeGetUnfinishedToDosAvisos implements IRepositorio
{
	private Collection<IToDo> unfinishedToDos;
	private Collection<IToDo> expiredToDos;
	private Collection<String> emails;

	public RepositorioFakeGetUnfinishedToDosAvisos(Collection<IToDo> unfinishedToDos, Collection<IToDo> expiredToDos, Collection<String> emails)
	{
		this.unfinishedToDos = unfinishedToDos;
		this.expiredToDos = expiredToDos;
		this.emails = emails;
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
		return unfinishedToDos;
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
