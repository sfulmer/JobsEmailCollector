package net.draconia.jobsemailcollector.service;

import java.io.Serializable;

import net.draconia.jobsemailcollector.domain.Email;

public interface EmailService extends Serializable
{
	public void delete(final Email objEmail);
	public void save(final Email objEmail);
}