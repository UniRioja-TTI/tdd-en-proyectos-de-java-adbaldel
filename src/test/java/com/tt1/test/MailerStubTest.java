package com.tt1.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailerStubTest
{
	String email;
	String message;
	IMailer mailer;

	// --- Arrange Before/After each test -------------------------------------------------------------------

	@BeforeEach
	void setUp()
	{
		email = "john.doe@example.com";
		message = "Hello World!";
		mailer = new MailerStub();
	}

	@AfterEach
	void tearDown()
	{
		email = null;
		message = null;
		mailer = null;
	}

	// --- Test sendEmail -------------------------------------------------------------------

	@Test
	void sendEmail()
	{
		boolean success = mailer.sendEmail(email, message);

		assertTrue(success);
	}
}
