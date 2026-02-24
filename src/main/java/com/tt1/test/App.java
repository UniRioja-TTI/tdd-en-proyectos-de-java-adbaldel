package com.tt1.test;

import java.util.Scanner;

public class App
{
	public static void main(String[] args)
	{
		IDB db = new DBStub();
		IRepositorio repositorio = new Repositorio(db);
		IMailer mailer = new MailerStub();
		Servicio servicio = new Servicio(repositorio, mailer);

		Scanner scanner = new Scanner(System.in);
		int opcion = -1;

		do
		{
			System.out.println();
			System.out.println("=== Gestor de Tareas ===");
			System.out.println("1. Añadir tarea");
			System.out.println("2. Añadir dirección de correo");
			System.out.println("3. Marcar tarea como completada");
			System.out.println("4. Ver tareas pendientes");
			System.out.println("5. Ver tareas vencidas");
			System.out.println("0. Salir");
			System.out.print("Elige una opción: ");

			try
			{
				opcion = Integer.parseInt(scanner.nextLine().trim());
			}
			catch (NumberFormatException e)
			{
				System.out.println("Opción no válida. Introduce un número del 0 al 5.");
				continue;
			}

			System.out.println();

			switch (opcion)
			{
				case 1:
					servicio.addToDo();
					break;
				case 2:
					servicio.addEmail();
					break;
				case 3:
					servicio.completarToDo();
					break;
				case 4:
					servicio.getUnfinishedToDos();
					break;
				case 5:
					servicio.getExpiredToDos();
					break;
				case 0:
					System.out.println("¡Hasta luego!");
					break;
				default:
					System.out.println("Opción no válida. Introduce un número del 0 al 5.");
			}
		}
		while (opcion != 0);

		scanner.close();
	}
}
