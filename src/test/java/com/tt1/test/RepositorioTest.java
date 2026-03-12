package com.tt1.test;

import com.tt1.test.mocks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioTest
{
	private Repositorio repositorio;
	private IDB db;
	private Calendar calendar;
	private IToDo toDo;
	private String email;

	// --- Arrange Before/After each test -------------------------------------------------------------------

	@BeforeEach
	void setUp()
	{
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		toDo = new ToDo("Tarea test", "Tara para test", calendar.getTime());
		email = "john.doe@example.com";
	}

	@AfterEach
	void tearDown()
	{
		repositorio = null;
		db = null;
		calendar = null;
		toDo = null;
		email = null;
	}

	// --- -------------- -------------------------------------------------------------------
	// --- TEST UNITARIOS -------------------------------------------------------------------
	// --- -------------- -------------------------------------------------------------------

	// --- Test addToDo -------------------------------------------------------------------

	@Test
	void addToDoSobreFake()
	{
		db = new DBFakeAddToDo(true);
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.addToDo(toDo);

		assertTrue(success);
	}

	@Test
	void addToDoRepetidoSobreFake()
	{
		db = new DBFakeAddToDo(false);
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoMismoNombreDistintaFechaLimiteSobreFake()
	{
		db = new DBFakeAddToDo(true);
		repositorio = new Repositorio(db);
		boolean success;
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		ToDo toDo2 = new ToDo("Tarea test", "Tara para test", calendar.getTime());

		success = repositorio.addToDo(toDo2);

		assertTrue(success);
	}

	// --- Test completarToDo -------------------------------------------------------------------

	@Test
	void completarToDoNoExistenteSobreFake()
	{
		db = new DBFakeUpdateToDo(false);
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.completarToDo(toDo);

		assertFalse(success);
	}

	@Test
	void completarToDoExistenteSobreFake()
	{
		db = new DBFakeUpdateToDo(true);
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.completarToDo(toDo);

		assertTrue(success);
	}

	// --- Test getUnfinishedToDo -------------------------------------------------------------------

	@Test
	void getUnfinishedToDosSobreFake()
	{
		IToDo toDo2, toDo3, toDo4;
		Collection<IToDo> unfinishedToDos;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime(), true);
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime(), true);
		db = new DBFakeGetAllToDos(List.of(toDo, toDo2, toDo3, toDo4));
		repositorio = new Repositorio(db);

		unfinishedToDos = repositorio.getUnfinishedToDos();

		assertEquals(2,  unfinishedToDos.size());
		assertTrue(unfinishedToDos.contains(toDo));
		assertTrue(unfinishedToDos.contains(toDo3));
	}

	// --- Test getExpiredToDos -------------------------------------------------------------------

	@Test
	void getExpiredToDosSobreFake()
	{
		IToDo toDo2, toDo3, toDo4;
		Collection<IToDo> expiredToDos;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime(), true);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime(), true);
		db = new DBFakeGetAllToDos(List.of(toDo, toDo2, toDo3, toDo4));
		repositorio = new Repositorio(db);

		expiredToDos = repositorio.getExpiredToDos();

		assertEquals(1,  expiredToDos.size());
		assertTrue(expiredToDos.contains(toDo3));
	}

	// --- Test addEmail -------------------------------------------------------------------

	@Test
	void addEmailSobreFake()
	{
		db = new DBFakeAddEmail(true);
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.addEmail(email);

		assertTrue(success);
	}

	@Test
	void addEmailRepetidoSobreFake()
	{
		db = new DBFakeAddEmail(false);
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.addEmail(email);

		assertFalse(success);
	}

	// --- Test getAllEmails -------------------------------------------------------------------

	@Test
	void getAllEmailsSobreFake()
	{
		String email2, email3;
		Collection<String> emails;
		email2 = "pepe@example.com";
		email3 = "maria.garcia@example.com";
		db = new DBFakeGetAllEmails(List.of(email, email2, email3));
		repositorio = new Repositorio(db);

		emails = repositorio.getAllEmails();

		assertEquals(3,  emails.size());
		assertTrue(emails.contains(email));
		assertTrue(emails.contains(email2));
		assertTrue(emails.contains(email3));
	}

	// --- ------------------- -------------------------------------------------------------------
	// --- TEST DE INTEGRACIÓN -------------------------------------------------------------------
	// --- ------------------- -------------------------------------------------------------------

	// --- Test addToDo -------------------------------------------------------------------

	@Test
	void addToDo()
	{
		db = new DBStub();
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.addToDo(toDo);

		assertTrue(success);
	}

	@Test
	void addToDoRepetido()
	{
		db = new DBStub();
		repositorio = new Repositorio(db);
		boolean success;

		repositorio.addToDo(toDo);
		success = repositorio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoMismoNombreDistintaFechaLimite()
	{
		db = new DBStub();
		repositorio = new Repositorio(db);
		boolean success;
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		ToDo toDo2 = new ToDo("Tarea test", "Tara para test", calendar.getTime());

		repositorio.addToDo(toDo);
		success = repositorio.addToDo(toDo2);

		assertTrue(success);
	}

	// --- Test completarToDo -------------------------------------------------------------------

	@Test
	void completarToDoNoExistente()
	{
		db = new DBStub();
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.completarToDo(toDo);

		assertFalse(success);
	}

	@Test
	void completarToDoExistente()
	{
		db = new DBStub();
		repositorio = new Repositorio(db);
		boolean success;
		Collection<IToDo> unfinishedToDos;

		repositorio.addToDo(toDo);
		success = repositorio.completarToDo(toDo);
		unfinishedToDos = repositorio.getUnfinishedToDos();

		assertTrue(success);
		assertEquals(0, unfinishedToDos.size());
	}

	// --- Test getUnfinishedToDo -------------------------------------------------------------------

	@Test
	void getUnfinishedToDos()
	{
		IToDo toDo2, toDo3, toDo4;
		Collection<IToDo> unfinishedToDos;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime(), true);
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime(), true);
		db = new DBStub();
		repositorio = new Repositorio(db);

		repositorio.addToDo(toDo);
		repositorio.addToDo(toDo2);
		repositorio.addToDo(toDo3);
		repositorio.addToDo(toDo4);
		unfinishedToDos = repositorio.getUnfinishedToDos();

		assertEquals(2,  unfinishedToDos.size());
		assertTrue(unfinishedToDos.contains(toDo));
		assertTrue(unfinishedToDos.contains(toDo3));
	}

	// --- Test getExpiredToDos -------------------------------------------------------------------

	@Test
	void getExpiredToDos()
	{
		IToDo toDo2, toDo3, toDo4;
		Collection<IToDo> expiredToDos;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime(), true);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime(), true);
		db = new DBStub();
		repositorio = new Repositorio(db);

		repositorio.addToDo(toDo);
		repositorio.addToDo(toDo2);
		repositorio.addToDo(toDo3);
		repositorio.addToDo(toDo4);
		expiredToDos = repositorio.getExpiredToDos();

		assertEquals(1,  expiredToDos.size());
		assertTrue(expiredToDos.contains(toDo3));
	}

	// --- Test addEmail -------------------------------------------------------------------

	@Test
	void addEmail()
	{
		db = new DBStub();
		repositorio = new Repositorio(db);
		boolean success;

		success = repositorio.addEmail(email);

		assertTrue(success);
	}

	@Test
	void addEmailRepetido()
	{
		db = new DBStub();
		repositorio = new Repositorio(db);
		boolean success;

		repositorio.addEmail(email);
		success = repositorio.addEmail(email);

		assertFalse(success);
	}

	// --- Test getAllEmails -------------------------------------------------------------------

	@Test
	void getAllEmails()
	{
		String email2, email3;
		Collection<String> emails;
		email2 = "pepe@example.com";
		email3 = "maria.garcia@example.com";
		db = new DBStub();
		repositorio = new Repositorio(db);

		repositorio.addEmail(email);
		repositorio.addEmail(email2);
		repositorio.addEmail(email3);
		emails = repositorio.getAllEmails();

		assertEquals(3,  emails.size());
		assertTrue(emails.contains(email));
		assertTrue(emails.contains(email2));
		assertTrue(emails.contains(email3));
	}
}
