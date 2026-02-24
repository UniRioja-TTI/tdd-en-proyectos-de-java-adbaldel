package com.tt1.test;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class Repositorio implements IRepositorio
{
	private final IDB dataBase;

	public Repositorio(IDB dataBase)
	{
		this.dataBase = dataBase;
	}

	@Override
	public IToDo getToDo(IToDo todo)
	{
		return dataBase.getToDo(todo);
	}

	@Override
	public Collection<IToDo> getAllToDos()
	{
		return dataBase.getAllToDos();
	}

	@Override
	public Collection<IToDo> getUnfinishedToDos()
	{
		return dataBase.getAllToDos().stream()
			.filter(t -> !t.isCompletado())
			.collect(Collectors.toList());
	}

	@Override
	public Collection<IToDo> getExpiredToDos()
	{
		Date ahora = new Date();
		return dataBase.getAllToDos().stream()
			.filter(t -> !t.isCompletado())
			.filter(t -> t.getFechaLimite() != null && t.getFechaLimite().before(ahora))
			.collect(Collectors.toList());
	}

	@Override
	public void completarToDo(IToDo todo)
	{
		IToDo todoCompleto = dataBase.getToDo(todo);
		if (todoCompleto != null)
		{
			todoCompleto.setCompletado(true);
			dataBase.updateToDo(todoCompleto);
		}
	}

	@Override
	public void addToDo(IToDo todo)
	{
		dataBase.insertToDo(todo);
	}

	@Override
	public void addEmail(String email)
	{
		dataBase.insertEmail(email);
	}

	@Override
	public Collection<String> getAllEmails()
	{
		return dataBase.getAllEmails();
	}
}
