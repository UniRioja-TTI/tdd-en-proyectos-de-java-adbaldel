package com.tt1.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoTest
{

	private ToDo todo;
	private Calendar calendar;

	// --- Arrange compartido -----------------------------------------------

	@BeforeEach
	public void setUp()
	{
		// Se crea un Calendar reutilizable en cada test
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 5);

		// Se crea un ToDo base reutilizable en cada test
		todo = new ToDo(
			"Comprar leche",
			"Ir al supermercado a comprar leche entera",
			calendar.getTime()
		);
	}

	@AfterEach
	public void tearDown()
	{
		todo = null;
		calendar = null;
	}

	// --- Constructor y getters -----------------------------------------------

	@Test
	public void testConstructorEstableceNombreCorrectamente()
	{
		assertEquals("Comprar leche", todo.getNombre());
	}

	@Test
	public void testConstructorEstableceDescripcionCorrectamente()
	{
		assertEquals("Ir al supermercado a comprar leche entera", todo.getDescripcion());
	}

	@Test
	public void testConstructorEstableceFechaLimiteCorrectamente()
	{
		assertEquals(calendar.getTime(), todo.getFechaLimite());
	}

	@Test
	public void testCompletadoEsFalsePorDefecto()
	{
		assertFalse(todo.isCompletado());
	}

	// --- Setters -----------------------------------------------

	@Test
	public void testSetNombreCambiaElNombre()
	{
		todo.setNombre("Comprar pan");

		assertEquals("Comprar pan", todo.getNombre());
	}

	@Test
	public void testSetDescripcionCambiaLaDescripcion()
	{
		todo.setDescripcion("Ir a la panadería a comprar pan integral");

		assertEquals("Ir a la panadería a comprar pan integral", todo.getDescripcion());
	}

	@Test
	public void testSetFechaLimiteCambiaLaFecha()
	{
		Calendar nuevaFecha = (Calendar) calendar.clone();
		nuevaFecha.add(Calendar.DAY_OF_YEAR, 1);

		todo.setFechaLimite(nuevaFecha.getTime());

		assertEquals(nuevaFecha.getTime(), todo.getFechaLimite());
	}

	@Test
	public void testSetCompletadoCambiaElEstado()
	{
		todo.setCompletado(true);

		assertTrue(todo.isCompletado());
	}

	@Test
	public void testSetCompletadoPuedeVolverAFalse()
	{
		todo.setCompletado(true);
		todo.setCompletado(false);

		assertFalse(todo.isCompletado());
	}

	// --- Casos límite -----------------------------------------------

	@Test
	public void testFechaLimitePuedeSerHoy()
	{
		todo.setFechaLimite(new Date());

		assertEquals(new Date(), todo.getFechaLimite());
	}

	@Test
	public void testFechaLimitePuedeSerPasada()
	{
		Calendar ayer = Calendar.getInstance();
		ayer.add(Calendar.DAY_OF_YEAR, -1);

		todo.setFechaLimite(ayer.getTime());

		assertTrue(todo.getFechaLimite().before(new Date()));
	}

	@Test
	public void testToDoConDescripcionVacia()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 5);

		ToDo todoSinDesc = new ToDo("Tarea", "", calendar.getTime());

		assertEquals("", todoSinDesc.getDescripcion());
	}
}
