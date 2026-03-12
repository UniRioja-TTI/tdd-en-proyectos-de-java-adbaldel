package com.tt1.test.mocks;

import com.tt1.test.IMailer;

public class MailerFakeSendEmail implements IMailer
{
	private boolean success;

	public MailerFakeSendEmail(boolean success)
	{
		this.success = success;
	}

	@Override
	public boolean sendEmail(String email, String mensaje)
	{
		return success;
	}
}
