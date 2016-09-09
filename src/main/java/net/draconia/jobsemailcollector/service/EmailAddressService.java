package net.draconia.jobsemailcollector.service;

import java.io.Serializable;

import net.draconia.jobsemailcollector.domain.EmailAddress;

public interface EmailAddressService extends Serializable
{
	public void delete(final EmailAddress objEmailAddress);
	public void save(final EmailAddress objEmailAddress);
}