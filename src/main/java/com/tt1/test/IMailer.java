package com.tt1.test;

/**
 * Interfaz de un servicio de mensajería.
 */
public interface IMailer
{
	/**
	 * Envía un correo electrónico a la dirección pasada como parámetro con el mensaje pasado como parámetro. Devuelve
	 * cierto si el email se envía correctamente, falso en caso contrario.
	 * El email debe validar la regex ^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$ y el mensaje debe ser no nulo.
	 *
	 * @param email la dirección de correo a la que enviar el mensaje.
	 * @param mensaje el mensaje a enviar.
	 * @return cierto si el email se envía con éxito, falso en caso contrario.
	 */
	boolean sendEmail(String email, String mensaje);
}
