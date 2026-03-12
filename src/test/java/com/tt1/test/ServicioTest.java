package com.tt1.test;

import com.tt1.test.mocks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTest
{
	private Servicio servicio;
	private IRepositorio repositorio;
	private IMailer mailer;
	private Calendar calendar, expiredCalendarAvisos;
	private IToDo toDo;
	private String email;
	private Collection<IToDo> expiredToDosAvisos;
	private Collection<String> emailsAvisos;

	// --- Arrange Before/After each test -------------------------------------------------------------------

	@BeforeEach
	void setUp()
	{
		repositorio = null;
		mailer = null;
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		toDo = new ToDo("Tarea test", "Tara para test", calendar.getTime());
		email = "john.doe@example.com";
		expiredCalendarAvisos = Calendar.getInstance();
		expiredCalendarAvisos.add(Calendar.DAY_OF_YEAR, -1);
		expiredToDosAvisos = List.of(
			new ToDo("Tarea expirada test 1", "Tarea expirada para test de avisos",
				expiredCalendarAvisos.getTime()),
			new ToDo("Tarea expirada test 2", "Tarea expirada para test de avisos",
				expiredCalendarAvisos.getTime())
		);
		emailsAvisos = List.of(
			email,
			"pepe@example.com",
			"maria.garcia@example.com"
		);
	}

	@AfterEach
	void tearDown()
	{
		servicio = null;
		repositorio = null;
		mailer = null;
		calendar = null;
		expiredCalendarAvisos = null;
		toDo = null;
		email = null;
		expiredToDosAvisos = null;
		emailsAvisos = null;
	}

	// --- -------------- -------------------------------------------------------------------
	// --- TEST UNITARIOS -------------------------------------------------------------------
	// --- -------------- -------------------------------------------------------------------

	// --- Test addToDo -------------------------------------------------------------------

	@Test
	void addToDoSobreFake()
	{
		repositorio = new RepositorioFakeAddToDo(true);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addToDo(toDo);

		assertTrue(success);
	}

	@Test
	void addToDoRepetidoSobreFake()
	{
		repositorio = new RepositorioFakeAddToDo(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConNombreNuloSobreFake()
	{
		repositorio = new RepositorioFakeAddToDo(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setNombre(null);

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConNombreVacioSobreFake()
	{
		repositorio = new RepositorioFakeAddToDo(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setNombre(" ");

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConDescripcionNulaSobreFake()
	{
		repositorio = new RepositorioFakeAddToDo(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setDescripcion(null);

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConFechaLimiteNulaSobreFake()
	{
		repositorio = new RepositorioFakeAddToDo(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setFechaLimite(null);

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoAvisosSobreFake()
	{
		repositorio = new RepositorioFakeAddToDoAvisos(true, expiredToDosAvisos, emailsAvisos);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		servicio.addToDo(toDo);

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	// --- Test completarToDo -------------------------------------------------------------------

	@Test
	void completarToDoNoExistenteSobreFake()
	{
		repositorio = new RepositorioFakeCompletarToDo(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.completarToDo(toDo);

		assertFalse(success);
	}

	@Test
	void completarToDoExistenteSobreFake()
	{
		repositorio = new RepositorioFakeCompletarToDo(true);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.completarToDo(toDo);

		assertTrue(success);
	}

	@Test
	void completarToDoExistenteAvisosSobreFake()
	{
		repositorio = new RepositorioFakeCompletarToDoAvisos(false, expiredToDosAvisos, emailsAvisos);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		servicio.completarToDo(toDo);

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	// --- Test getUnfinishedToDos -------------------------------------------------------------------

	@Test
	void getUnfinishedToDosSobreFake()
	{
		IToDo toDo2, toDo3, toDo4;
		Collection<IToDo> unfinishedToDos;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime());
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime());
		repositorio = new RepositorioFakeGetUnfinishedToDos(List.of(toDo, toDo2, toDo3, toDo4));
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);

		unfinishedToDos = servicio.getUnfinishedToDos();

		assertEquals(4,  unfinishedToDos.size());
		assertTrue(unfinishedToDos.contains(toDo));
		assertTrue(unfinishedToDos.contains(toDo2));
		assertTrue(unfinishedToDos.contains(toDo3));
		assertTrue(unfinishedToDos.contains(toDo4));
	}

	@Test
	void getUnfinishedToDosAvisosSobreFake()
	{
		IToDo toDo2, toDo3, toDo4;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime());
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime());
		Collection<IToDo> unfinishedToDos = new ArrayList<>(List.of(toDo, toDo2, toDo3, toDo4));
		unfinishedToDos.addAll(expiredToDosAvisos);
		repositorio = new RepositorioFakeGetUnfinishedToDosAvisos(unfinishedToDos, expiredToDosAvisos, emailsAvisos);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		servicio.getUnfinishedToDos();

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	// --- Test addEmail -------------------------------------------------------------------

	@Test
	void addEmailSobreFake()
	{
		repositorio = new RepositorioFakeAddEmail(true);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addEmail(email);

		assertTrue(success);
	}

	@Test
	void addEmailRepetidoSobreFake()
	{
		repositorio = new RepositorioFakeAddEmail(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addEmail(email);

		assertFalse(success);
	}

	@Test
	void addEmailNoValidoSobreFake()
	{
		repositorio = new RepositorioFakeAddEmail(false);
		mailer = new MailerFakeSendEmail(true);
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addEmail("john.doe@example");

		assertFalse(success);
	}

	@Test
	void addEmailAvisosSobreFake()
	{
		repositorio = new RepositorioFakeAddEmailAvisos(true, expiredToDosAvisos, emailsAvisos);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		servicio.addEmail(email);

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	// --- ------------------- -------------------------------------------------------------------
	// --- TEST DE INTEGRACIÓN -------------------------------------------------------------------
	// --- ------------------- -------------------------------------------------------------------

	// --- Test addToDo -------------------------------------------------------------------

	@Test
	void addToDo()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addToDo(toDo);

		assertTrue(success);
	}

	@Test
	void addToDoRepetido()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		servicio.addToDo(toDo);
		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConNombreNulo()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setNombre(null);

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConNombreVacio()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setNombre(" ");

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConDescripcionNula()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setDescripcion(null);

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addToDoConFechaLimiteNula()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		toDo.setFechaLimite(null);

		success = servicio.addToDo(toDo);

		assertFalse(success);
	}

	@Test
	void addNotExpiredToDoAvisos()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		for (IToDo expiredToDo : expiredToDosAvisos)
			servicio.addToDo(expiredToDo);
		for (String email : emailsAvisos)
			servicio.addEmail(email);
		((MailerStubLogueaLlamadas) mailer).reset();
		servicio.addToDo(toDo);

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	@Test
	void addExpiredToDoAvisos()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);
		toDo.setFechaLimite(expiredCalendarAvisos.getTime());

		for (IToDo expiredToDo : expiredToDosAvisos)
			servicio.addToDo(expiredToDo);
		for (String email : emailsAvisos)
			servicio.addEmail(email);
		((MailerStubLogueaLlamadas) mailer).reset();
		servicio.addToDo(toDo);

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	// --- Test completarToDo -------------------------------------------------------------------

	@Test
	void completarToDoNoExistente()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.completarToDo(toDo);

		assertFalse(success);
	}

	@Test
	void completarToDoExistente()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;
		Collection<IToDo> unfinishedToDos;

		servicio.addToDo(toDo);
		success = servicio.completarToDo(toDo);
		unfinishedToDos = servicio.getUnfinishedToDos();

		assertTrue(success);
		assertEquals(0, unfinishedToDos.size());
	}

	@Test
	void completarToDoExistenteNoExpiradoAvisos()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		for (IToDo expiredToDo : expiredToDosAvisos)
			servicio.addToDo(expiredToDo);
		for (String email : emailsAvisos)
			servicio.addEmail(email);
		servicio.addToDo(toDo);
		((MailerStubLogueaLlamadas) mailer).reset();
		servicio.completarToDo(toDo);

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	@Test
	void completarToDoExistenteExpiradoAvisos()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);
		toDo.setFechaLimite(expiredCalendarAvisos.getTime());

		for (IToDo expiredToDo : expiredToDosAvisos)
			servicio.addToDo(expiredToDo);
		for (String email : emailsAvisos)
			servicio.addEmail(email);
		servicio.addToDo(toDo);
		((MailerStubLogueaLlamadas) mailer).reset();
		servicio.completarToDo(toDo);

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	// --- Test getUnfinishedToDos -------------------------------------------------------------------

	@Test
	void getUnfinishedToDos()
	{
		IToDo toDo2, toDo3, toDo4;
		Collection<IToDo> unfinishedToDos;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime());
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime());
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);

		servicio.addToDo(toDo);
		servicio.addToDo(toDo2);
		servicio.addToDo(toDo3);
		servicio.addToDo(toDo4);
		unfinishedToDos = servicio.getUnfinishedToDos();

		assertEquals(4,  unfinishedToDos.size());
		assertTrue(unfinishedToDos.contains(toDo));
		assertTrue(unfinishedToDos.contains(toDo2));
		assertTrue(unfinishedToDos.contains(toDo3));
		assertTrue(unfinishedToDos.contains(toDo4));
	}

	@Test
	void getUnfinishedToDosAvisos()
	{
		IToDo toDo2, toDo3, toDo4;
		toDo2 = new ToDo("Tarea test 2", "Tara para test", calendar.getTime());
		toDo3 = new ToDo("Tarea test 3", "Tara para test", calendar.getTime());
		toDo4 = new ToDo("Tarea test 4", "Tara para test", calendar.getTime());
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		for (IToDo expiredToDo : expiredToDosAvisos)
			servicio.addToDo(expiredToDo);
		for (String email : emailsAvisos)
			servicio.addEmail(email);
		servicio.addToDo(toDo);
		servicio.addToDo(toDo2);
		servicio.addToDo(toDo3);
		servicio.addToDo(toDo4);
		((MailerStubLogueaLlamadas) mailer).reset();
		servicio.getUnfinishedToDos();

		assertEquals(3, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}

	// --- Test addEmail -------------------------------------------------------------------

	@Test
	void addEmail()
	{
		repositorio = new RepositorioFakeAddEmail(true);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addEmail(email);

		assertTrue(success);
	}

	@Test
	void addEmailRepetido()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		servicio.addEmail(email);
		success = servicio.addEmail(email);

		assertFalse(success);
	}

	@Test
	void addEmailNoValido()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStub();
		servicio = new Servicio(repositorio, mailer);
		boolean success;

		success = servicio.addEmail("john.doe@example");

		assertFalse(success);
	}

	@Test
	void addEmailAvisos()
	{
		IDB db = new DBStub();
		repositorio = new Repositorio(db);
		mailer = new MailerStubLogueaLlamadas();
		servicio = new Servicio(repositorio, mailer);

		for (IToDo expiredToDo : expiredToDosAvisos)
			servicio.addToDo(expiredToDo);

		servicio.addEmail(email);

		assertEquals(1, ((MailerStubLogueaLlamadas) mailer).getNumberOfSentEmails());
	}
}
