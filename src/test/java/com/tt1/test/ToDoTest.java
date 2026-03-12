package com.tt1.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest
{
	private Calendar calendar;
	private IToDo toDo1;
	private IToDo toDo2;

	// --- Arrange Before/After each test -------------------------------------------------------------------

	@BeforeEach
	void setUp()
	{
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		toDo1 = new ToDo("Tarea 1", "Tarea test", calendar.getTime());
		toDo2 = new ToDo();
	}

	@AfterEach
	void tearDown()
	{
		calendar = null;
		toDo1 = null;
		toDo2 = null;
	}

	// --- Test equals -------------------------------------------------------------------

	@Test
	void testEqualsMismoNombreYFechaLimite()
	{
		toDo2.setNombre("Tarea 1");
		toDo2.setFechaLimite(calendar.getTime());

		assertEquals(toDo1, toDo2);
	}

	@Test
	void testEqualsMismoNombreDescripcionYFechaLimite()
	{
		toDo2.setNombre("Tarea 1");
		toDo2.setDescripcion("Tarea test");
		toDo2.setFechaLimite(calendar.getTime());

		assertEquals(toDo1, toDo2);
	}

	@Test
	void testEqualsMismoNombreYFechaLimiteDiferenteDescripcionYEstado()
	{
		toDo2.setNombre("Tarea 1");
		toDo2.setDescripcion("Tarea diferente");
		toDo2.setFechaLimite(calendar.getTime());
		toDo2.setCompletado(true);

		assertEquals(toDo1, toDo2);
	}

	@Test
	void testEqualsMismoNombre()
	{
		toDo2.setNombre("Tarea 1");

		assertNotEquals(toDo1, toDo2);
	}

	@Test
	void testEqualsMismaFechaLimite()
	{
		toDo2.setFechaLimite(calendar.getTime());

		assertNotEquals(toDo1, toDo2);
	}

	@Test
	void testEqualsMismNombreYDescripcion()
	{
		toDo2.setNombre("Tarea 1");
		toDo2.setDescripcion("Tarea test");

		assertNotEquals(toDo1, toDo2);
	}
}
