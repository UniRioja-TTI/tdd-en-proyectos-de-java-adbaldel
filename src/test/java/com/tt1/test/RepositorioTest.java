package com.tt1.test;

import com.tt1.test.fake.DBFake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class RepositorioTest
{

	// --- TEST UNITARIOS (con clases falsas) -----------------------------------------------

	@Nested
	class TestsUnitarios
	{

		private IDB dbFake;
		private IRepositorio repositorio;
		private IToDo todo1;
		private IToDo todo2;
		private IToDo todo3;

		@BeforeEach
		public void setUp()
		{
			dbFake = new DBFake();
			repositorio = new Repositorio(dbFake);
			todo1 = new ToDo("ToDo 1", "Descripción 1", new Date());
			todo2 = new ToDo("ToDo 2", "Descripción 2", new Date(System.currentTimeMillis()+1000*60*60*24));
			todo3 = new ToDo("ToDo 3", "Descripción 3", new Date(System.currentTimeMillis()-1000*60*60*24));

		}

		@AfterEach
		public void tearDown()
		{
			dbFake = null;
			repositorio = null;
			todo1 = null;
			todo2 = null;
			todo3 = null;
		}

		// --- getToDo -----------------------------------------------

		@Test
		public void testGetToDoDevuelveElToDoCorrecto()
		{
			IToDo resultado = repositorio.getToDo(new ToDo());

			assertNotNull(resultado);
			assertEquals(todo1, resultado);
			assertEquals(todo1.getNombre(), resultado.getNombre());
			assertEquals(todo1.getDescripcion(), resultado.getDescripcion());
			assertEquals(todo1.getFechaLimite(), resultado.getFechaLimite());
		}

		// --- getAllToDos -----------------------------------------------

		@Test
		public void testGetAllToDosDevuelveLosToDosCorrectos()
		{
			Collection<IToDo> resultado = repositorio.getAllToDos();

			assertTrue(resultado.contains(todo1));
			assertTrue(resultado.contains(todo2));
			assertTrue(resultado.contains(todo3));
			assertEquals(3, resultado.size());
		}

		// --- getUnfinishedToDos -----------------------------------------------

		@Test
		public void testGetUnfinishedToDosDevuelveLosToDosCorrectos()
		{
			Collection<IToDo> resultado = repositorio.getUnfinishedToDos();

			assertTrue(resultado.contains(todo1));
			assertTrue(resultado.contains(todo2));
			assertTrue(resultado.contains(todo3));
			assertEquals(3, resultado.size());
		}

		// --- getExpiredToDos -----------------------------------------------

		@Test
		public void testGetExpiredToDosDevuelveLosToDosCorrectos()
		{
			Collection<IToDo> resultado = repositorio.getExpiredToDos();

			assertTrue(resultado.contains(todo3));
			assertEquals(1, resultado.size());
		}
	}

	// --- TEST DE INTEGRACIÓN (con clases reales) -----------------------------------------------

	@Nested
	class TestsDeIntegracion
	{

		private IDB db;
		private IRepositorio repositorio;
		private IToDo todo1;
		private IToDo todo2;
		private IToDo todo3;
		private IToDo todo1DTO; // DTO: Data Transfer Object (para buscar en la base de datos el objeto completo)
		private IToDo todo2DTO; // DTO: Data Transfer Object (para buscar en la base de datos el objeto completo)
		private IToDo todo3DTO;
		private Calendar calendarToDo1;
		private Calendar calendarToDo2;
		private Calendar calendarToDo3;

		@BeforeEach
		public void setUp()
		{
			db = new DBStub();
			repositorio = new Repositorio(db);

			calendarToDo1 = Calendar.getInstance();
			calendarToDo1.add(Calendar.DAY_OF_YEAR, 5);
			todo1 = new ToDo("Tarea 1", "Descripción 1", calendarToDo1.getTime());
			todo1DTO = new ToDo("Tarea 1", null, calendarToDo1.getTime());

			calendarToDo2 = (Calendar) calendarToDo1.clone();
			calendarToDo2.add(Calendar.DAY_OF_YEAR, 1);
			todo2 = new ToDo("Tarea 2", "Descripción 2", calendarToDo2.getTime());
			todo2DTO = new ToDo("Tarea 2", null, calendarToDo2.getTime());

			calendarToDo3 = Calendar.getInstance();
			calendarToDo3.add(Calendar.DAY_OF_YEAR, -1);
			todo3 = new ToDo("Tarea 3", "Descripción 3", calendarToDo3.getTime());
			todo3DTO = new ToDo("Tarea 3", null, calendarToDo3.getTime());
		}

		@AfterEach
		public void tearDown()
		{
			db = null;
			repositorio = null;
			todo1 = null;
			todo2 = null;
			todo3 = null;
			todo1DTO = null;
			todo2DTO = null;
			todo3DTO = null;
			calendarToDo1 = null;
			calendarToDo2 = null;
			calendarToDo3 = null;
		}

		// --- getToDo -----------------------------------------------

		@Test
		public void testGetToDoDevuelveElToDoCorrecto()
		{
			repositorio.addToDo(todo1);

			IToDo resultado = repositorio.getToDo(todo1DTO);

			assertNotNull(resultado);
			assertEquals(todo1, resultado);
			assertEquals(todo1.getNombre(), resultado.getNombre());
			assertEquals(todo1.getDescripcion(), resultado.getDescripcion());
			assertEquals(todo1.getFechaLimite(), resultado.getFechaLimite());
		}

		@Test
		public void testGetToDoDevuelveElToDoCorrectoEnPresenciaDeMasToDos()
		{
			repositorio.addToDo(todo2);
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo3);

			IToDo resultado = repositorio.getToDo(todo1DTO);

			assertNotNull(resultado);
			assertEquals(todo1, resultado);
			assertEquals(todo1.getNombre(), resultado.getNombre());
			assertEquals(todo1.getDescripcion(), resultado.getDescripcion());
			assertEquals(todo1.getFechaLimite(), resultado.getFechaLimite());
		}

		@Test
		public void testGetToDoDevuelveNullSiNoHayToDos()
		{
			IToDo resultado = repositorio.getToDo(todo1DTO);

			assertNull(resultado);
		}

		@Test
		public void testGetToDoDevuelveNullSiNoExiste()
		{
			repositorio.addToDo(todo1);

			IToDo resultado = repositorio.getToDo(todo2DTO);

			assertNull(resultado);
		}

		// --- getAllToDos -----------------------------------------------

		@Test
		public void testGetAllToDosDevuelveLosToDosCorrectos()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.addToDo(todo3);

			Collection<IToDo> resultado = repositorio.getAllToDos();

			assertTrue(resultado.contains(todo1DTO));
			assertTrue(resultado.contains(todo2DTO));
			assertTrue(resultado.contains(todo3DTO));
			assertEquals(3, resultado.size());
		}

		// --- getUnfinishedToDos -----------------------------------------------

		@Test
		public void testGetUnfinishedToDosDevuelveLosToDosCorrectos()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.addToDo(todo3);

			Collection<IToDo> resultado = repositorio.getUnfinishedToDos();

			assertTrue(resultado.contains(todo1DTO));
			assertTrue(resultado.contains(todo2DTO));
			assertTrue(resultado.contains(todo3DTO));
			assertEquals(3, resultado.size());
		}

		@Test
		public void testGetUnfinishedToDosDevuelveLosToDosCorrectosSiHayUnoCompletado()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.addToDo(todo3);
			repositorio.completarToDo(todo2DTO);

			Collection<IToDo> resultado = repositorio.getUnfinishedToDos();

			assertTrue(resultado.contains(todo1DTO));
			assertTrue(resultado.contains(todo3DTO));
			assertEquals(2, resultado.size());
		}

		@Test
		public void testGetUnfinishedToDosDevuelveLosToDosCorrectosSiEstanNoHayUnfinishedToDos()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.addToDo(todo3);
			repositorio.completarToDo(todo1DTO);
			repositorio.completarToDo(todo2DTO);
			repositorio.completarToDo(todo3DTO);

			Collection<IToDo> resultado = repositorio.getUnfinishedToDos();

			assertTrue(resultado.isEmpty());
		}

		// --- getExpiredToDos -----------------------------------------------

		@Test
		public void testGetExpiredToDosDevuelveLosToDosCorrectos()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.addToDo(todo3);

			Collection<IToDo> resultado = repositorio.getExpiredToDos();

			assertTrue(resultado.contains(todo3DTO));
			assertEquals(1, resultado.size());
		}

		@Test
		public void testGetExpiredToDosDevuelveVacioSiNoHayExpiredToDos()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.addToDo(todo3);
			repositorio.completarToDo(todo3DTO);

			Collection<IToDo> resultado = repositorio.getExpiredToDos();

			assertTrue(resultado.isEmpty());
		}

		// --- completarToDo -----------------------------------------------

		@Test
		public void testCompletarToDoMarcaLaTareaComoCompletada()
		{
			repositorio.addToDo(todo1);
			repositorio.completarToDo(todo1DTO);

			IToDo resultado = repositorio.getToDo(todo1DTO);

			assertTrue(resultado.isCompletado());
		}

		@Test
		public void testCompletarToDoMarcaLaTareaComoCompletadaEnLaBaseDeDatos()
		{
			repositorio.addToDo(todo1);
			repositorio.completarToDo(todo1DTO);

			IToDo resultado = db.getToDo(todo1DTO);

			assertTrue(resultado.isCompletado());
		}

		@Test
		public void testCompletarToDoNoAfectaAOtrasTareas()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.completarToDo(todo1DTO);

			IToDo resultado = repositorio.getToDo(todo2DTO);

			assertFalse(resultado.isCompletado());
		}

		@Test
		public void testCompletarToDoNoAfectaAOtrasTareasEnLaBaseDeDatos()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);
			repositorio.completarToDo(todo1DTO);

			IToDo resultado = db.getToDo(todo2DTO);

			assertFalse(resultado.isCompletado());
		}

		// --- addToDo -----------------------------------------------

		@Test
		public void testAddToDoAlmacenaElToDoEnLaBaseDeDatos()
		{
			repositorio.addToDo(todo1);

			IToDo resultado = db.getToDo(todo1DTO);

			assertNotNull(resultado);
			assertEquals(todo1, resultado);
			assertEquals(todo1.getNombre(), resultado.getNombre());
			assertEquals(todo1.getDescripcion(), resultado.getDescripcion());
			assertEquals(todo1.getFechaLimite(), resultado.getFechaLimite());
		}

		@Test
		public void testAddToDoVariasTareasSeAlmacenanTodas()
		{
			repositorio.addToDo(todo1);
			repositorio.addToDo(todo2);

			assertEquals(2, db.getAllToDos().size());
			assertEquals(repositorio.getToDo(todo1DTO), todo1);
			assertEquals(repositorio.getToDo(todo2DTO), todo2);
			assertEquals(db.getToDo(todo1DTO), todo1);
			assertEquals(db.getToDo(todo2DTO), todo2);
		}

		// --- addEmail -----------------------------------------------

		@Test
		public void testAddEmailAlmacenaElEmail()
		{
			repositorio.addEmail("john.doe@ejemplo.com");

			assertTrue(db.getAllEmails().contains("john.doe@ejemplo.com"));
			assertEquals(1, db.getAllEmails().size());
		}

		@Test
		public void testAddEmailVariosEmailsSeAlmacenanTodos()
		{
			repositorio.addEmail("a@a.com");
			repositorio.addEmail("b@b.com");

			assertEquals(2, db.getAllEmails().size());
			assertTrue(db.getAllEmails().contains("a@a.com"));
			assertTrue(db.getAllEmails().contains("b@b.com"));
		}
	}
}
