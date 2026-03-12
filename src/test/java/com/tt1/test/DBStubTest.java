package com.tt1.test;

import org.junit.jupiter.api.*;

import java.util.Calendar;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DBStubTest
{
	private DBStub db;
	private Calendar calendar;
	private IToDo toDo;
	private String email;

	// --- Arrange Before/After each test -------------------------------------------------------------------

	@BeforeEach
	void setUp()
	{
		db = new DBStub();
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		toDo = new ToDo("Tarea test", "Tara para test", calendar.getTime());
		email = "john.doe@example.com";
	}

	@AfterEach
	void tearDown()
	{
		db = null;
		calendar = null;
		toDo = null;
		email = null;
	}

	// --- Test addToDo -------------------------------------------------------------------

	@Test
	void addToDo()
	{
		boolean success;

		success = db.addToDo(toDo);

		assertTrue(success);
	}

	@Test
	void addToDoRepetido()
	{
		boolean success;

		db.addToDo(toDo);
		success = db.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoMismoNombreDistintaFechaLimite()
	{
		boolean success;
		db.addToDo(toDo);
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		ToDo toDo2 = new ToDo("Tarea test", "Tara para test", calendar.getTime());

		success = db.addToDo(toDo2);

		assertTrue(success);
	}

	// --- Test getAllToDos -------------------------------------------------------------------

	@Test
	void getAllToDos()
	{
		Collection<IToDo> toDos = db.getAllToDos();

		assertEquals(0, toDos.size());
	}

	// --- Test updateToDo (y addToDo) -------------------------------------------------------------------

	@Test
	void updateToDoNoExistente()
	{
		boolean success;

		success = db.updateToDo(toDo);

		assertFalse(success);
	}

	@Test
	void updateToDoExistente()
	{
		boolean success;

		db.addToDo(toDo);
		success = db.updateToDo(toDo);

		assertTrue(success);
	}

	@Test
	void updateDescripcionToDoExistente()
	{
		boolean success;
		IToDo toDo2 = new ToDo(toDo.getNombre(), "Tara para test diferente", toDo.getFechaLimite());

		db.addToDo(toDo);
		success = db.updateToDo(toDo2);

		assertTrue(success);
	}

	@Test
	void updateCompletadoToDoExistente()
	{
		boolean success;
		IToDo toDo2 = new ToDo(toDo.getNombre(), toDo.getDescripcion(), toDo.getFechaLimite(), true);

		db.addToDo(toDo);
		success = db.updateToDo(toDo2);

		assertTrue(success);
	}

	// --- Test deleteToDo (y addToDo) -------------------------------------------------------------------

	@Test
	void deleteToDoNoExistente()
	{
		boolean success;

		success = db.deleteToDo(toDo);

		assertFalse(success);
	}

	@Test
	void deleteToDoExistente()
	{
		boolean success;

		db.addToDo(toDo);
		success = db.deleteToDo(toDo);

		assertTrue(success);
	}

	// --- Test addEmail -------------------------------------------------------------------

	@Test
	void addEmail()
	{
		boolean success;

		success = db.addEmail(email);

		assertTrue(success);
	}

	void addEmailRepetido()
	{
		boolean success;
		db.addEmail(email);

		success = db.addEmail(email);

		assertFalse(success);
	}

	// --- Test getAllEmails -------------------------------------------------------------------

	@Test
	void getAllEmails()
	{
		Collection<String> emails;

		emails = db.getAllEmails();

		assertEquals(0, emails.size());
	}

	// --- Test deleteEmail (y addEmail) -------------------------------------------------------------------

	@Test
	void deleteEmailNoExistente()
	{
		boolean success;

		success = db.deleteEmail(email);

		assertFalse(success);
	}

	@Test
	void deleteEmailExistente()
	{
		boolean success;
		db.addEmail(email);

		success = db.deleteEmail(email);

		assertTrue(success);
	}

	// --- Test addToDo y getAllToDos -------------------------------------------------------------------

	void addToDoUnicoYGetAllToDos()
	{
		boolean success;
		Collection<IToDo> toDos;

		success = db.addToDo(toDo);
		toDos = db.getAllToDos();

		assertTrue(success); // Redundante con el test addToDoUnico
		assertEquals(1, toDos.size());
		assertTrue(toDos.contains(toDo));
	}

	void addToDoRepetidoYGetAllToDos()
	{
		boolean success;
		Collection<IToDo> toDos;
		db.addToDo(toDo);

		success = db.addToDo(toDo);
		toDos = db.getAllToDos();

		assertFalse(success); // Redundante con el test addToDoRepetido
		assertEquals(1, toDos.size());
		assertTrue(toDos.contains(toDo));
	}

	void addToDoMismoNombreDistintaFechaLimiteYGetAllToDos()
	{
		boolean success;
		Collection<IToDo> toDos;
		db.addToDo(toDo);
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		IToDo toDo2 = new ToDo(toDo.getNombre(), "Tara para test", calendar.getTime());

		success = db.addToDo(toDo2);
		toDos = db.getAllToDos();

		assertTrue(success); // Redundante con el test addToDoMismoNombreDistintaFechaLimite
		assertEquals(2, toDos.size());
		assertTrue(toDos.contains(toDo));
		assertTrue(toDos.contains(toDo2));
	}

	void addToDoDiferentesYRepetidosYGetAllToDos()
	{
		boolean success1, success2, success3, success4, success5, success6;
		IToDo toDo2, toDo3, toDo4;
		Collection<IToDo> toDos;

		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		toDo3 = new ToDo("Tarea test 3", "Tara para test 3", calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		toDo4 = new ToDo("Tarea test 3", "Tarea test 4", calendar.getTime());

		success1 = db.addToDo(toDo); // Lo añade y es igual a toDo1
		toDo.setNombre("Tarea test 2");
		success2 = db.addToDo(toDo); // Lo añade y es igual a toDo2
		success3 = db.addToDo(toDo); // No lo añade porque ya está añadido
		success4 = db.addToDo(toDo2);// No lo añade porque ya hay uno igual
		success5 = db.addToDo(toDo3);// Lo añade y es igual a toDo3
		success6 = db.addToDo(toDo4);// Lo añade y es igual a toDo4
		toDos = db.getAllToDos();
		toDo.setNombre("Tarea test");

		assertTrue(success1);
		assertTrue(success2);
		assertFalse(success3);
		assertFalse(success4);
		assertTrue(success5);
		assertTrue(success6);
		assertEquals(4,  toDos.size());
		assertTrue(toDos.contains(toDo));
		assertTrue(toDos.contains(toDo2));
		assertTrue(toDos.contains(toDo3));
		assertTrue(toDos.contains(toDo4));
	}

	// --- Test updateToDo (y addToDo) y getAllToDos -------------------------------------------------------------------

	@Test
	void updateToDoNoExistenteYGetAllToDos()
	{
		boolean success;
		Collection<IToDo> toDos;

		success = db.updateToDo(toDo);
		toDos = db.getAllToDos();

		assertFalse(success);
		assertEquals(0, toDos.size());
	}

	@Test
	void updateToDoExistenteYGetAllToDos()
	{
		boolean success;
		Collection<IToDo> toDos;

		db.addToDo(toDo);
		success = db.updateToDo(toDo);
		toDos = db.getAllToDos();

		assertTrue(success);
		assertEquals(1, toDos.size());
		assertTrue(toDos.contains(toDo));
	}

	@Test
	void updateDescripcionToDoExistenteYGetAllToDos()
	{
		boolean success;
		IToDo toDo2 = new ToDo(toDo.getNombre(), "Tara para test diferente", toDo.getFechaLimite());
		Collection<IToDo> toDos;

		db.addToDo(toDo);
		success = db.updateToDo(toDo2);
		toDos = db.getAllToDos();

		assertTrue(success);
		assertEquals(1, toDos.size());
		assertTrue(toDos.contains(toDo2));
		assertEquals("Tara para test diferente", toDos.iterator().next().getDescripcion());
	}

	@Test
	void updateCompletadoToDoExistenteYGetAllToDos()
	{
		boolean success;
		IToDo toDo2 = new ToDo(toDo.getNombre(), toDo.getDescripcion(), toDo.getFechaLimite(), true);
		Collection<IToDo> toDos;

		db.addToDo(toDo);
		success = db.updateToDo(toDo2);
		toDos = db.getAllToDos();

		assertTrue(success);
		assertEquals(1, toDos.size());
		assertTrue(toDos.contains(toDo2));
		assertEquals(toDo2.isCompletado(), toDos.iterator().next().isCompletado());
	}

	// --- Test deleteToDo (y addToDo) y getAllToDos -------------------------------------------------------------------

	@Test
	void deleteToDoNoExistenteYGetAllToDos()
	{
		boolean success;
		Collection<IToDo> toDos;

		success = db.deleteToDo(toDo);
		toDos = db.getAllToDos();

		assertFalse(success);
		assertEquals(0, toDos.size());
	}

	@Test
	void deleteToDoExistenteYGetAllToDos()
	{
		boolean success;
		Collection<IToDo> toDos;

		db.addToDo(toDo);
		success = db.deleteToDo(toDo);
		toDos = db.getAllToDos();

		assertTrue(success);
		assertEquals(0, toDos.size());
	}

	// --- Test addEmail y getAllEmails -------------------------------------------------------------------

	void addEmailUnicoYGetAllEmails()
	{
		boolean success;
		Collection<String> emails;

		success = db.addEmail(email);
		emails = db.getAllEmails();

		assertTrue(success); // Redundante con el test addEmailUnico
		assertEquals(1, emails.size());
		assertTrue(emails.contains(email));
	}

	void addEmailRepetidoYGetAllEmails()
	{
		boolean success;
		Collection<String> emails;

		db.addEmail(email);
		success = db.addEmail(email);
		emails = db.getAllEmails();

		assertFalse(success); // Redundante con el test addEmailRepetido
		assertEquals(1, emails.size());
		assertTrue(emails.contains(email));
	}

	// --- Test deleteEmail (y addEmail) y getAllEmails -------------------------------------------------------------------

	@Test
	void deleteEmailNoExistenteYGetAllEmails()
	{
		boolean success;
		Collection<String> emails;

		success = db.deleteEmail(email);
		emails = db.getAllEmails();

		assertFalse(success);
		assertEquals(0, emails.size());
	}

	@Test
	void deleteEmailExistenteYGetAllEmails()
	{
		boolean success;
		Collection<String> emails;

		db.addEmail(email);
		success = db.deleteEmail(email);
		emails = db.getAllEmails();

		assertTrue(success);
		assertEquals(0, emails.size());
	}
}
