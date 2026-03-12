package com.tt1.test;

import java.util.Collection;

/**
 * Servicio de gestión de tareas.
 */
public class Servicio implements IServicio
{
	private IRepositorio repositorio;
	private IMailer mailer;

	/**
	 * Crea un servicio sobre el repositorio y el mailer pasados como parámetro.
	 * El repositorio y el mailer son no nulos.
	 *
	 * @param repositorio el repositorio que usa el servicio.
	 * @param mailer el mailer que usa el servicio.
	 */
	public Servicio(IRepositorio repositorio, IMailer mailer)
	{
		this.repositorio = repositorio;
		this.mailer = mailer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addToDo(IToDo toDo)
	{
		boolean addSuccess;
		boolean sendWarningSuccess;

		if (toDo.getNombre() != null && !toDo.getNombre().isBlank() && toDo.getDescripcion() != null
			&& toDo.getFechaLimite() != null)
		{
			addSuccess = repositorio.addToDo(toDo);
		}
		else
		{
			addSuccess = false;
		}

		sendWarningSuccess = sendWarningEmail(repositorio.getExpiredToDos());

		return addSuccess && sendWarningSuccess;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean completarToDo(IToDo toDo)
	{
		boolean completarSuccess;
		boolean sendWarningSuccess;

		completarSuccess = repositorio.completarToDo(toDo);
		sendWarningSuccess = sendWarningEmail(repositorio.getExpiredToDos());

		return completarSuccess && sendWarningSuccess;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IToDo> getUnfinishedToDos()
	{
		Collection<IToDo> toDos;

		toDos = repositorio.getUnfinishedToDos();
		sendWarningEmail(repositorio.getExpiredToDos());

		return toDos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addEmail(String email)
	{
		boolean addSuccess;
		boolean sendWarningSuccess;

		if (email.matches("^[\\w\\-\\.]+@([\\w\\-]+\\.)+[\\w\\-]{2,}$"))
		{
			addSuccess = repositorio.addEmail(email);
		}
		else
		{
			addSuccess = false;
		}

		sendWarningSuccess = sendWarningEmail(repositorio.getExpiredToDos());

		return addSuccess && sendWarningSuccess;
	}

	/**
	 * Envía un email a todos las direcciones de correo almacenadas en la base de datos informándoles de que las tareas
	 * pasadas como parámetro están sin completar y su fecha límite ya ha pasado. Devuelve cierto si se consiguen enviar
	 * los emails a todas las direcciones de correo, falso en caso contrario.
	 *
	 * @param expiredToDos la lista de tareas sin completar cuya fecha límite ya ha pasado de las que se va a informar a
	 *                     todos los emails de la base de datos.
	 * @return cierto si se consiguen enviar todos los emails, falso en caso contrario.
	 */
	private boolean sendWarningEmail(Collection<IToDo> expiredToDos)
	{
		Collection<String> emails = repositorio.getAllEmails();
		String mensaje;
		int i = 1;
		boolean success = true;

		if (!expiredToDos.isEmpty() && !emails.isEmpty())
		{
			mensaje = "Los siguientes ToDos están sin completar y su fecha límite ya ha pasado:\n";

			for (IToDo toDo : expiredToDos)
			{
				mensaje = '\t' + i + ' ' + toDo.getNombre() + " (" + toDo.getFechaLimite() + "): "
					+ toDo.getDescripcion() + '\n';
				i++;
			}

			for (String email : emails)
			{
				success = success && mailer.sendEmail(email, mensaje);
			}
		}


		return success;
	 }
}
