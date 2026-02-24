package com.tt1.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DBStub implements IDB
{
	private final Map<String, IToDo> todos = new HashMap<>();
	private final Set<String> emails = new HashSet<>();

	@Override
	public void insertToDo(IToDo todo)
	{
		todos.putIfAbsent(todo.getNombre(), todo);
	}

	@Override
	public IToDo getToDo(IToDo todo)
	{
		return todos.get(todo.getNombre());
	}

	@Override
	public Collection<IToDo> getAllToDos()
	{
		return todos.values();
	}

	@Override
	public void updateToDo(IToDo todo)
	{
		todos.put(todo.getNombre(), todo);
	}

	@Override
	public void deleteToDo(IToDo todo)
	{
		todos.remove(todo.getNombre());
	}

	@Override
	public void insertEmail(String email)
	{
		emails.add(email);
	}

	@Override
	public String getEmail(String email)
	{
		return emails.contains(email) ? email : null;
	}

	@Override
	public Collection<String> getAllEmails()
	{
		return emails;
	}

	@Override
	public void updateEmail(String email)
	{
		emails.add(email);
	}

	@Override
	public void deleteEmail(String original)
	{
		emails.remove(original);
	}
}
