package com.tt1.test;

import com.tt1.test.fake.DBFake;
import com.tt1.test.fake.MailerFake;
import com.tt1.test.fake.RepositorioFake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Calendar;

public class ServicioTest
{
	// --- TEST UNITARIOS (con clases falsas) -----------------------------------------------

	@Nested
	class TestsUnitarios
	{
		private RepositorioFake repositorioFake;
		private MailerFake mailerFake;
		private Servicio servicio;

		private final ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
		private final PrintStream salidaOriginal = System.out;
		private final InputStream entradaOriginal = System.in;

		// --- Arrange compartido -----------------------------------------------

		@BeforeEach
		public void setUp()
		{
			repositorioFake = new RepositorioFake();
			mailerFake = new MailerFake();
			servicio = new Servicio(repositorioFake, mailerFake);
			System.setOut(new PrintStream(salidaCapturada));
		}

		@AfterEach
		public void tearDown()
		{
			System.setOut(salidaOriginal);
			System.setIn(entradaOriginal);
			salidaCapturada.reset();
			repositorioFake = null;
			mailerFake = null;
			servicio = null;
		}

		private void simularEntrada(String entrada)
		{
			System.setIn(new ByteArrayInputStream(entrada.getBytes()));
		}

		// --- getUnfinishedToDos -----------------------------------------------

		@Test
		public void testGetUnfinishedToDosImprimeLasTareasPendientes()
		{
			servicio.getUnfinishedToDos();
			String salida = salidaCapturada.toString();

			assertTrue(salida.contains("ToDo 1"));
			assertTrue(salida.contains("ToDo 2"));
			assertTrue(salida.contains("ToDo 3"));
		}

		// --- getExpiredToDos -----------------------------------------------

		@Test
		public void testGetExpiredToDosImprimeLasTareasVencidas()
		{
			servicio.getExpiredToDos();
			String salida = salidaCapturada.toString();

			assertTrue(salida.contains("ToDo 3"));
		}

		@Test
		public void testGetExpiredToDosNoImprimeLosToDosNoVencidos()
		{
			servicio.getExpiredToDos();
			String salida = salidaCapturada.toString();

			assertFalse(salida.contains("ToDo 1"));
			assertFalse(salida.contains("ToDo 2"));
		}

		// --- broadcastExpiredToDos -----------------------------------------------

		@Test
		public void testBroadcastExpiredToDosNoLanzaExcepcion()
		{
			assertDoesNotThrow(() -> servicio.broadcastExpiredToDos());
		}

		@Test
		public void testBroadcastExpiredToDosConFalloEnMailerNoLanzaExcepcion()
		{
			mailerFake.simularFallo = true;

			assertDoesNotThrow(() -> servicio.broadcastExpiredToDos());
		}

		// --- addToDo -----------------------------------------------

		@Test
		public void testAddToDoLeeLaEntradaYNoLanzaExcepcion()
		{
			simularEntrada("Mi tarea\nDescripción de la tarea\n25/12/2026\n");

			assertDoesNotThrow(() -> servicio.addToDo());
		}

		// --- addEmail -----------------------------------------------

		@Test
		public void testAddEmailLeeLaEntradaYNoLanzaExcepcion()
		{
			simularEntrada("test@test.com\n");

			assertDoesNotThrow(() -> servicio.addEmail());
		}

		// --- completarToDo -----------------------------------------------

		@Test
		public void testCompletarToDoLeeLaEntradaYNoLanzaExcepcion()
		{
			simularEntrada("ToDo 1\n");

			assertDoesNotThrow(() -> servicio.completarToDo());
		}
	}

	// --- TESTS DE INTEGRACIÓN (con clases reales) -----------------------------------------------

	@Nested
	class TestsDeIntegracion
	{
		private IDB db;
		private IRepositorio repositorio;
		private MailerStub mailer;
		private Servicio servicio;

		private final ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
		private final PrintStream salidaOriginal = System.out;
		private final InputStream entradaOriginal = System.in;

		// --- Arrange compartido -----------------------------------------------

		@BeforeEach
		public void setUp()
		{
			db = new DBStub();
			repositorio = new Repositorio(db);
			mailer = new MailerStub();
			servicio = new Servicio(repositorio, mailer);
			System.setOut(new PrintStream(salidaCapturada));
		}

		@AfterEach
		public void tearDown()
		{
			System.setOut(salidaOriginal);
			System.setIn(entradaOriginal);
			salidaCapturada.reset();
			db = null;
			repositorio = null;
			mailer = null;
			servicio = null;
		}

		private void simularEntrada(String entrada)
		{
			System.setIn(new ByteArrayInputStream(entrada.getBytes()));
		}

		// --- addToDo -----------------------------------------------

		@Test
		public void testAddToDoAlmacenaLaTareaEnElRepositorio()
		{
			simularEntrada("Tarea nueva\nDescripción\n25/12/2026\n");

			servicio.addToDo();

			IToDo resultado = repositorio.getToDo(new ToDo("Tarea nueva", null, null));
			assertNotNull(resultado);
			assertEquals("Tarea nueva", resultado.getNombre());
		}

		@Test
		public void testAddToDoAlmacenaLaTareaEnLaBaseDeDatos()
		{
			simularEntrada("Tarea nueva\nDescripción\n25/12/2026\n");

			servicio.addToDo();

			IToDo resultado = db.getToDo(new ToDo("Tarea nueva", null, null));
			assertNotNull(resultado);
			assertEquals("Tarea nueva", resultado.getNombre());
		}

		@Test
		public void testAddToDoVariasTareasSeAlmacenanTodas()
		{
			simularEntrada("Tarea 1\nDesc 1\n25/12/2026\n");
			servicio.addToDo();

			simularEntrada("Tarea 2\nDesc 2\n26/12/2026\n");
			servicio.addToDo();

			assertEquals(2, db.getAllToDos().size());
		}

		// --- addEmail -----------------------------------------------

		@Test
		public void testAddEmailAlmacenaElEmailEnLaBaseDeDatos()
		{
			simularEntrada("nuevo@correo.com\n");

			servicio.addEmail();

			assertTrue(db.getAllEmails().contains("nuevo@correo.com"));
		}

		@Test
		public void testAddEmailVariosEmailsSeAlmacenanTodos()
		{
			simularEntrada("a@a.com\n");
			servicio.addEmail();

			simularEntrada("b@b.com\n");
			servicio.addEmail();

			assertEquals(2, db.getAllEmails().size());
			assertTrue(db.getAllEmails().contains("a@a.com"));
			assertTrue(db.getAllEmails().contains("b@b.com"));
		}

		// --- completarToDo -----------------------------------------------

		@Test
		public void testCompletarToDoMarcaLaTareaComoCompletada()
		{
			simularEntrada("Tarea completar\nDesc\n25/12/2026\n");
			servicio.addToDo();

			simularEntrada("Tarea completar\n");
			servicio.completarToDo();

			IToDo resultado = repositorio.getToDo(new ToDo("Tarea completar", null, null));
			assertTrue(resultado.isCompletado());
		}

		@Test
		public void testCompletarToDoMarcaLaTareaComoCompletadaEnLaBaseDeDatos()
		{
			simularEntrada("Tarea completar\nDesc\n25/12/2026\n");
			servicio.addToDo();

			simularEntrada("Tarea completar\n");
			servicio.completarToDo();

			IToDo resultado = db.getToDo(new ToDo("Tarea completar", null, null));
			assertTrue(resultado.isCompletado());
		}

		@Test
		public void testCompletarToDoNoAfectaAOtrasTareas()
		{
			simularEntrada("Tarea 1\nDesc\n25/12/2026\n");
			servicio.addToDo();
			simularEntrada("Tarea 2\nDesc\n26/12/2026\n");
			servicio.addToDo();

			simularEntrada("Tarea 1\n");
			servicio.completarToDo();

			IToDo resultado = repositorio.getToDo(new ToDo("Tarea 2", null, null));
			assertFalse(resultado.isCompletado());
		}

		// --- getUnfinishedToDos -----------------------------------------------

		@Test
		public void testGetUnfinishedToDosImprimeLasTareasPendientes()
		{
			simularEntrada("Tarea pendiente\nDesc\n25/12/2026\n");
			servicio.addToDo();
			salidaCapturada.reset();

			servicio.getUnfinishedToDos();

			assertTrue(salidaCapturada.toString().contains("Tarea pendiente"));
		}

		@Test
		public void testGetUnfinishedToDosNoImprimeToDoCompletado()
		{
			simularEntrada("Tarea a completar\nDesc\n25/12/2026\n");
			servicio.addToDo();
			simularEntrada("Tarea a completar\n");
			servicio.completarToDo();
			salidaCapturada.reset();

			servicio.getUnfinishedToDos();

			assertFalse(salidaCapturada.toString().contains("Tarea a completar"));
		}

		@Test
		public void testGetUnfinishedToDosDevuelveVacioSiTodosCompletados()
		{
			simularEntrada("Tarea 1\nDesc\n25/12/2026\n");
			servicio.addToDo();
			simularEntrada("Tarea 1\n");
			servicio.completarToDo();
			salidaCapturada.reset();

			servicio.getUnfinishedToDos();

			assertTrue(repositorio.getUnfinishedToDos().isEmpty());
		}

		// --- getExpiredToDos -----------------------------------------------

		@Test
		public void testGetExpiredToDosImprimeLasTareasVencidas()
		{
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			salidaCapturada.reset();

			servicio.getExpiredToDos();

			assertTrue(salidaCapturada.toString().contains("Tarea vencida"));
		}

		@Test
		public void testGetExpiredToDosNoImprimeTareasNoVencidas()
		{
			Calendar manana = Calendar.getInstance();
			manana.add(Calendar.DAY_OF_YEAR, 1);
			repositorio.addToDo(new ToDo("Tarea futura", "Desc", manana.getTime()));
			salidaCapturada.reset();

			servicio.getExpiredToDos();

			assertFalse(salidaCapturada.toString().contains("Tarea futura"));
		}

		@Test
		public void testGetExpiredToDosNoImprimeTareasVencidasYaCompletadas()
		{
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea completada vencida", "Desc", ayer.getTime()));
			repositorio.completarToDo(new ToDo("Tarea completada vencida", null, null));
			salidaCapturada.reset();

			servicio.getExpiredToDos();

			assertFalse(salidaCapturada.toString().contains("Tarea completada vencida"));
		}

		// --- broadcastExpiredToDos -----------------------------------------------

		@Test
		public void testBroadcastExpiredToDosEnviaCorreoConLaDireccionCorrecta()
		{
			repositorio.addEmail("alerta@test.com");
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			salidaCapturada.reset();

			servicio.broadcastExpiredToDos();

			assertTrue(salidaCapturada.toString().contains("alerta@test.com"));
		}

		@Test
		public void testBroadcastExpiredToDosEnviaCorreoATodasLasDirecciones()
		{
			repositorio.addEmail("uno@test.com");
			repositorio.addEmail("dos@test.com");
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			salidaCapturada.reset();

			servicio.broadcastExpiredToDos();

			String salida = salidaCapturada.toString();
			assertTrue(salida.contains("uno@test.com"));
			assertTrue(salida.contains("dos@test.com"));
		}

		@Test
		public void testBroadcastExpiredToDosNoEnviaCorreoSiNoHayTareasVencidas()
		{
			repositorio.addEmail("alerta@test.com");
			Calendar manana = Calendar.getInstance();
			manana.add(Calendar.DAY_OF_YEAR, 1);
			repositorio.addToDo(new ToDo("Tarea futura", "Desc", manana.getTime()));
			salidaCapturada.reset();

			servicio.broadcastExpiredToDos();

			assertFalse(salidaCapturada.toString().contains("alerta@test.com"));
		}

		@Test
		public void testBroadcastExpiredToDosNoEnviaCorreoSiNoHayEmails()
		{
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			salidaCapturada.reset();

			// No emails registered; the mailer (MailerStub) should never be called,
			// so nothing related to email sending should appear in the output
			servicio.broadcastExpiredToDos();

			assertTrue(salidaCapturada.toString().isEmpty());
		}

		@Test
		public void testBroadcastExpiredToDosImprimeElNombreDeLaTareaVencidaEnElMensaje()
		{
			repositorio.addEmail("alerta@test.com");
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			salidaCapturada.reset();

			servicio.broadcastExpiredToDos();

			assertTrue(salidaCapturada.toString().contains("Tarea vencida"));
		}

		// --- Broadcast automático en otras operaciones -----------------------------------------------

		@Test
		public void testAddToDoActivaBroadcastSiHayTareasVencidas()
		{
			repositorio.addEmail("alerta@test.com");
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			salidaCapturada.reset();

			simularEntrada("Nueva tarea\nDesc\n25/12/2026\n");
			servicio.addToDo();

			assertTrue(salidaCapturada.toString().contains("alerta@test.com"));
		}

		@Test
		public void testGetUnfinishedToDosActivaBroadcastSiHayTareasVencidas()
		{
			repositorio.addEmail("alerta@test.com");
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			salidaCapturada.reset();

			servicio.getUnfinishedToDos();

			assertTrue(salidaCapturada.toString().contains("alerta@test.com"));
		}

		@Test
		public void testCompletarToDoActivaBroadcastSiHayTareasVencidas()
		{
			repositorio.addEmail("alerta@test.com");
			Calendar ayer = Calendar.getInstance();
			ayer.add(Calendar.DAY_OF_YEAR, -1);
			repositorio.addToDo(new ToDo("Tarea vencida", "Desc", ayer.getTime()));
			simularEntrada("Tarea pendiente\nDesc\n25/12/2026\n");
			servicio.addToDo();
			salidaCapturada.reset();

			simularEntrada("Tarea pendiente\n");
			servicio.completarToDo();

			assertTrue(salidaCapturada.toString().contains("alerta@test.com"));
		}
	}
}
