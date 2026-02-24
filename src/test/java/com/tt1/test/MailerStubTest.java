package com.tt1.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MailerStubTest
{

	private MailerStub mailer;

	// Captura de System.out para verificar lo que se imprime
	private final ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
	private final PrintStream salidaOriginal = System.out;

	// --- Arrange -----------------------------------------------

	@BeforeEach
	public void setUp()
	{
		mailer = new MailerStub();
		System.setOut(new PrintStream(salidaCapturada)); // redirigir System.out
	}

	@AfterEach
	public void tearDown()
	{
		System.setOut(salidaOriginal); // restaurar System.out
		salidaCapturada.reset();
		mailer = null;
	}

	// --- Tests de retorno -----------------------------------------------

	@Test
	public void testSendEmailDevuelveTrueAlEnviarCorrectamente()
	{
		boolean resultado = mailer.sendEmail("test@test.com", "Hola mundo");

		assertTrue(resultado);
	}

	@Test
	public void testSendEmailConMensajeVacioDevuelveTrue()
	{
		boolean resultado = mailer.sendEmail("test@test.com", "");

		assertTrue(resultado);
	}

	// --- Tests de salida por consola -----------------------------------------------

	@Test
	public void testSendEmailImprimeLaDireccion()
	{
		mailer.sendEmail("destinatario@ejemplo.com", "Mensaje de prueba");
		String salida = salidaCapturada.toString();

		assertTrue(salida.contains("destinatario@ejemplo.com"),
			"La salida debería contener la dirección de correo");
	}

	@Test
	public void testSendEmailImprimeElMensaje()
	{
		mailer.sendEmail("destinatario@ejemplo.com", "Mensaje de prueba");
		String salida = salidaCapturada.toString();

		assertTrue(salida.contains("Mensaje de prueba"),
			"La salida debería contener el cuerpo del mensaje");
	}

	@Test
	public void testSendEmailImprimeAmbosDatos()
	{
		mailer.sendEmail("alerta@empresa.com", "Tienes tareas vencidas");
		String salida = salidaCapturada.toString();

		assertTrue(salida.contains("alerta@empresa.com"));
		assertTrue(salida.contains("Tienes tareas vencidas"));
	}

	// --- Casos límite -----------------------------------------------

	@Test
	public void testSendEmailConMultiplesLlamadas()
	{
		// Verificar que múltiples envíos funcionan correctamente
		assertTrue(mailer.sendEmail("a@a.com", "msg1"));
		assertTrue(mailer.sendEmail("b@b.com", "msg2"));
	}
}
