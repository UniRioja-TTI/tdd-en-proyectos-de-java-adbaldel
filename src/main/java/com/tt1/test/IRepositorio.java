package com.tt1.test;

import java.util.Collection;

public interface IRepositorio
{
	IToDo getToDo(IToDo todo);

	Collection<IToDo> getAllToDos();

	Collection<IToDo> getUnfinishedToDos();

	Collection<IToDo> getExpiredToDos();

	void completarToDo(IToDo todo);

	void addToDo(IToDo todo);

	Collection<String> getAllEmails();

	void addEmail(String email);
}
