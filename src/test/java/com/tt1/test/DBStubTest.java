package com.tt1.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Collection;

public class DBStubTest
{

	private DBStub db;
	private IToDo todo1;
	private IToDo todo2;
	private IToDo todo1DTO; // DTO: Data Transfer Object (para buscar en la base de datos el objeto completo)
	private IToDo todo2DTO; // DTO: Data Transfer Object (para buscar en la base de datos el objeto completo)
	private Calendar calendarToDo1;
	private Calendar calendarToDo2;

	// --- Arrange compartido -----------------------------------------------

	@BeforeEach
	public void setUp()
	{
		db = new DBStub();

		calendarToDo1 = Calendar.getInstance();
		calendarToDo1.add(Calendar.DAY_OF_YEAR, 5);
		todo1 = new ToDo("Tarea 1", "Descripción 1", calendarToDo1.getTime());
		todo1DTO = new ToDo("Tarea 1", null, calendarToDo1.getTime());

		calendarToDo2 = (Calendar) calendarToDo1.clone();
		calendarToDo2.add(Calendar.DAY_OF_YEAR, 1);
		todo2 = new ToDo("Tarea 2", "Descripción 2", calendarToDo2.getTime());
		todo2DTO = new ToDo("Tarea 2", null, calendarToDo2.getTime());
	}

	@AfterEach
	public void tearDown()
	{
		db = null;
		todo1 = null;
		todo2 = null;
		todo1DTO = null;
		todo2DTO = null;
		calendarToDo1 = null;
		calendarToDo2 = null;
	}

	// --- CRUD de ToDos -----------------------------------------------

	// CREATE
	@Test
	public void testInsertToDoAumentaElTamano()
	{
		db.insertToDo(todo1);

		assertEquals(1, db.getAllToDos().size());
	}

	@Test
	public void testInsertVariosToDosLosAlmacenaTodos()
	{
		db.insertToDo(todo1);
		db.insertToDo(todo2);

		assertEquals(2, db.getAllToDos().size());
	}

	@Test
	public void testInsertVariosToDosIgualesAlmacenaSoloUno()
	{
		db.insertToDo(todo1);
		db.insertToDo(todo1);

		assertEquals(1, db.getAllToDos().size());
	}

	// READ
	@Test
	public void testGetToDoDevuelveElToDoCorrecto()
	{
		db.insertToDo(todo1);

		IToDo resultado = db.getToDo(todo1DTO);

		assertNotNull(resultado);
		assertEquals(todo1, resultado);
		assertEquals(todo1.getNombre(), resultado.getNombre());
		assertEquals(todo1.getDescripcion(), resultado.getDescripcion());
		assertEquals(todo1.getFechaLimite(), resultado.getFechaLimite());
	}

	@Test
	public void testGetToDoDevuelveNullSiNoHayToDos()
	{
		IToDo resultado = db.getToDo(todo1DTO);

		assertNull(resultado);
	}

	@Test
	public void testGetToDoDevuelveNullSiNoExiste()
	{
		db.insertToDo(todo1);

		IToDo resultado = db.getToDo(todo2DTO);

		assertNull(resultado);
	}

	@Test
	public void testGetAllToDosEstaVacioAlIniciar()
	{
		assertTrue(db.getAllToDos().isEmpty());
	}

	@Test
	public void testGetAllToDosDevuelveTodosLosElementos()
	{
		db.insertToDo(todo1);
		db.insertToDo(todo2);

		Collection<IToDo> todos = db.getAllToDos();

		assertTrue(todos.contains(todo1));
		assertTrue(todos.contains(todo2));
	}

	// UPDATE
	@Test
	public void testUpdateToDoModificaElToDoExistente()
	{
		db.insertToDo(todo1);
		todo1.setDescripcion("Descripción modificada");
		db.updateToDo(todo1);

		IToDo resultado = db.getToDo(todo1DTO);

		assertEquals("Descripción modificada", resultado.getDescripcion());
		assertEquals(todo1, resultado);
		assertEquals(todo1.getNombre(), resultado.getNombre());
		assertEquals(todo1.getDescripcion(), resultado.getDescripcion());
		assertEquals(todo1.getFechaLimite(), resultado.getFechaLimite());
	}

	// DELETE
	@Test
	public void testDeleteToDoEliminaElToDo()
	{
		db.insertToDo(todo1);
		db.deleteToDo(todo1DTO);

		assertNull(db.getToDo(todo1DTO));
	}

	@Test
	public void testDeleteToDoReduceElTamano()
	{
		db.insertToDo(todo1);
		db.insertToDo(todo2);
		db.deleteToDo(todo1DTO);

		assertEquals(1, db.getAllToDos().size());
	}

	// --- Gestión de emails -----------------------------------------------

	@Test
	public void testInsertEmailAlmacenaElEmail()
	{
		db.insertEmail("john.doe@ejemplo.com");

		assertTrue(db.getAllEmails().contains("john.doe@ejemplo.com"));
		assertEquals(1, db.getAllEmails().size());
	}

	@Test
	public void testInsertVariosEmailsLosAlmacenaTodos()
	{
		db.insertEmail("a@a.com");
		db.insertEmail("b@b.com");

		assertEquals(2, db.getAllEmails().size());
		assertTrue(db.getAllEmails().contains("a@a.com"));
		assertTrue(db.getAllEmails().contains("b@b.com"));
	}

	@Test
	public void testInsertVariosEmailsIgualesAlmacenaSoloUno()
	{
		db.insertEmail("john.doe@ejemplo.com");
		db.insertEmail("john.doe@ejemplo.com");

		assertEquals(1, db.getAllEmails().size());
		assertTrue(db.getAllEmails().contains("john.doe@ejemplo.com"));
	}

	@Test
	public void testGetEmailsEstaVacioAlIniciar()
	{
		assertTrue(db.getAllEmails().isEmpty());
	}
}
