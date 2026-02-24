package com.tt1.test.fake;

import com.tt1.test.IDB;
import com.tt1.test.IToDo;
import com.tt1.test.ToDo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DBFake implements IDB
{
	@Override
	public void insertToDo(IToDo todo)
	{
	}

	@Override
	public IToDo getToDo(IToDo todo)
	{
		IToDo todo1 = new ToDo("ToDo 1", "Descripción 1", new Date());

		return todo1;
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
	public void updateToDo(IToDo todo)
	{
	}

	@Override
	public void deleteToDo(IToDo todo)
	{
	}

	@Override
	public void insertEmail(String email)
	{
	}

	@Override
	public String getEmail(String email)
	{
		return "john.doe@example.com";
	}

	@Override
	public Collection<String> getAllEmails()
	{
		Collection<String> emails = new ArrayList<>();
		emails.add("john.doe@example.com");

		return emails;
	}

	@Override
	public void updateEmail(String email)
	{
	}

	@Override
	public void deleteEmail(String email)
	{
	}
}
