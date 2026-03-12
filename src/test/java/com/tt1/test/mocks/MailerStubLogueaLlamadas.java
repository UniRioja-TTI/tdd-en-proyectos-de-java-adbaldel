package com.tt1.test.mocks;

import com.tt1.test.IMailer;

import java.util.ArrayList;
import java.util.List;

public class MailerStubLogueaLlamadas implements IMailer
{
	private List<String> emails;
	private List<String> mensajes;

	public MailerStubLogueaLlamadas()
	{
		emails = new ArrayList<>();
		mensajes = new ArrayList<>();
	}

	@Override
	public boolean sendEmail(String email, String mensaje)
	{
		emails.add(email);
		mensajes.add(mensaje);

		return true;
	}

	public int getNumberOfSentEmails()
	{
		return emails.size();
	}

	public String getEmail(int index)
	{
		return emails.get(index);
	}

	public String getMensaje(int index)
	{
		return mensajes.get(index);
	}

	public List<String> getEmails()
	{
		return emails;
	}

	public List<String> getMensajes()
	{
		return mensajes;
	}

	public void reset()
	{
		emails.clear();
		mensajes.clear();
	}
}
