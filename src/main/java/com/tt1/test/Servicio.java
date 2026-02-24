package com.tt1.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Servicio
{
	private final IRepositorio repositorio;
	private final IMailer mailer;
	private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy");

	public Servicio(IRepositorio repositorio, IMailer mailer)
	{
		this.repositorio = repositorio;
		this.mailer = mailer;
	}

	public void addToDo()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre de la tarea: ");
		String nombre = scanner.nextLine();

		System.out.print("Descripción: ");
		String descripcion = scanner.nextLine();

		System.out.print("Fecha límite (dd/MM/yyyy): ");
		String fechaStr = scanner.nextLine();

		Date fechaLimite = null;
		try
		{
			fechaLimite = FORMATO_FECHA.parse(fechaStr);
		}
		catch (ParseException e)
		{
			System.out.println("Formato de fecha inválido. Use dd/MM/yyyy.");
		}

		IToDo todo = new ToDo(nombre, descripcion, fechaLimite);
		repositorio.addToDo(todo);

		broadcastExpiredToDos();
	}

	public void addEmail()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Dirección de correo electrónico: ");
		String email = scanner.nextLine();

		repositorio.addEmail(email);

		broadcastExpiredToDos();
	}

	public void completarToDo()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre de la tarea a completar: ");
		String nombre = scanner.nextLine();

		IToDo dto = new ToDo(nombre, null, null);
		repositorio.completarToDo(dto);

		broadcastExpiredToDos();
	}

	public void getUnfinishedToDos()
	{
		Collection<IToDo> pendientes = repositorio.getUnfinishedToDos();

		if (pendientes.isEmpty())
		{
			System.out.println("No hay tareas pendientes.");
		}
		else
		{
			System.out.println("Tareas pendientes:");
			for (IToDo todo : pendientes)
			{
				System.out.println("  - " + todo.getNombre()
					+ " (límite: " + FORMATO_FECHA.format(todo.getFechaLimite()) + ")");
			}
		}

		broadcastExpiredToDos();
	}

	public void getExpiredToDos()
	{
		Collection<IToDo> vencidas = repositorio.getExpiredToDos();

		if (vencidas.isEmpty())
		{
			System.out.println("No hay tareas vencidas.");
		}
		else
		{
			System.out.println("Tareas vencidas:");
			for (IToDo todo : vencidas)
			{
				System.out.println("  - " + todo.getNombre()
					+ " (límite: " + FORMATO_FECHA.format(todo.getFechaLimite()) + ")");
			}
		}

		broadcastExpiredToDos();
	}

	public void broadcastExpiredToDos()
	{
		Collection<IToDo> vencidas = repositorio.getExpiredToDos();
		if (vencidas.isEmpty())
		{
			return;
		}

		Collection<String> emails = repositorio.getAllEmails();
		if (emails.isEmpty())
		{
			return;
		}

		String nombresVencidas = vencidas.stream()
			.map(IToDo::getNombre)
			.collect(Collectors.joining(", "));
		String mensaje = "ALERTA: Las siguientes tareas han vencido: " + nombresVencidas;

		for (String email : emails)
		{
			mailer.sendEmail(email, mensaje);
		}
	}
}
