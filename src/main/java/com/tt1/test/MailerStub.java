package com.tt1.test;

/**
 * Servicio de mensajería stub que imprime en el System.out el destinatario y el mesnaje. No envía emails.
 */
public class MailerStub implements IMailer
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sendEmail(String email, String mensaje)
	{
		System.out.println("Para: " + email);
		System.out.println("Mensaje: " + mensaje);
		return true;
	}
}
