package com.tt1.test;

public class MailerStub implements IMailer
{
	@Override
	public boolean sendEmail(String address, String message)
	{
		throw new UnsupportedOperationException("Clase aún no implementada.");
	}
}
