package net.draconia.jobsemailcollector.dao;

import java.io.Serializable;

import net.draconia.jobsemailcollector.domain.EmailAddress;

public interface EmailAddressDAO extends Serializable
{
	public void delete(final EmailAddress objEmailAddress);
	public void save(final EmailAddress objEmailAddress);
}