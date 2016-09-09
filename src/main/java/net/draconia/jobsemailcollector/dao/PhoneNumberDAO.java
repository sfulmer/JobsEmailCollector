package net.draconia.jobsemailcollector.dao;

import java.io.Serializable;

import net.draconia.jobsemailcollector.domain.PhoneNumber;

public interface PhoneNumberDAO extends Serializable
{
	public void delete(final PhoneNumber objPhoneNumber);
	public void save(final PhoneNumber objPhoneNumber);
}