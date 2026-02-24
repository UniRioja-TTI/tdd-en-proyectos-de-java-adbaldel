package com.tt1.test;

import java.util.Collection;

public interface IDB
{
	void insertToDo(IToDo todo);

	IToDo getToDo(IToDo todo);

	Collection<IToDo> getAllToDos();

	void updateToDo(IToDo todo);

	void deleteToDo(IToDo todo);

	void insertEmail(String email);

	String getEmail(String email);

	Collection<String> getAllEmails();

	void updateEmail(String email);

	void deleteEmail(String email);
}
