package com.tt1.test.fake;

import com.tt1.test.IMailer;

public class MailerFake implements IMailer {

	// Si se pone a false, sendEmail devolverá false (simula fallo).
	public boolean simularFallo = false;

	@Override
	public boolean sendEmail(String direccion, String mensaje) {
		return !simularFallo;
	}
}
