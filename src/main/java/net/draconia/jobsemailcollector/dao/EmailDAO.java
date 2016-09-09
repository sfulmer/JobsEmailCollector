package net.draconia.jobsemailcollector.dao;

import java.io.Serializable;

import net.draconia.jobsemailcollector.domain.Email;

public interface EmailDAO extends Serializable
{
	public void delete(final Email objEmail);
	public void save(final Email objEmail);
}