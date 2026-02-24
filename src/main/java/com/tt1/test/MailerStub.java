package com.tt1.test;

public class MailerStub implements IMailer
{
	@Override
	public boolean sendEmail(String address, String message)
	{
		System.out.println("Para: " + address);
		System.out.println("Mensaje: " + message);
		return true;
	}
}
