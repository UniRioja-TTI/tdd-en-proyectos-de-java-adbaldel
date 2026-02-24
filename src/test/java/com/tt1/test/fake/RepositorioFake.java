package com.tt1.test.fake;

import com.tt1.test.IRepositorio;
import com.tt1.test.IToDo;
import com.tt1.test.ToDo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class RepositorioFake implements IRepositorio
{
	@Override
	public IToDo getToDo(IToDo todo)
	{
		return new ToDo("ToDo fake", "Descripción fake", new Date());
	}

	@Override
	public Collection<IToDo> getAllToDos()
	{
		Collection<IToDo> todos = new ArrayList<>();
		IToDo todo1 = new ToDo("ToDo 1", "Descripción 1", new Date());
		IToDo todo2 = new ToDo("ToDo 2", "Descripción 2", new Date(System.currentTimeMillis()+1000*60*60*24));
		IToDo todo3 = new ToDo("ToDo 3", "Descripción 3", new Date(System.currentTimeMillis()-1000*60*60*24));
		todos.add(todo1);
		todos.add(todo2);
		todos.add(todo3);

		return todos;
	}

	@Override
	public Collection<IToDo> getUnfinishedToDos()
	{
		Collection<IToDo> todos = new ArrayList<>();
		IToDo todo1 = new ToDo("ToDo 1", "Descripción 1", new Date());
		IToDo todo2 = new ToDo("ToDo 2", "Descripción 2", new Date(System.currentTimeMillis()+1000*60*60*24));
		IToDo todo3 = new ToDo("ToDo 3", "Descripción 3", new Date(System.currentTimeMillis()-1000*60*60*24));
		todos.add(todo1);
		todos.add(todo2);
		todos.add(todo3);

		return todos;
	}

	@Override
	public Collection<IToDo> getExpiredToDos()
	{
		Collection<IToDo> todos = new ArrayList<>();
		IToDo todo3 = new ToDo("ToDo 3", "Descripción 3", new Date(System.currentTimeMillis()-1000*60*60*24));
		todos.add(todo3);

		return todos;
	}

	@Override
	public void completarToDo(IToDo todo)
	{
	}

	@Override
	public void addToDo(IToDo todo)
	{
	}

	@Override
	public void addEmail(String email)
	{
	}

	@Override
	public Collection<String> getAllEmails()
	{
		Collection<String> emails = new ArrayList<>();
		emails.add("john.doe@example.com");
		return emails;
	}
}
