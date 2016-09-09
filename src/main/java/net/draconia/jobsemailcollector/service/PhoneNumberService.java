package net.draconia.jobsemailcollector.service;

import java.io.Serializable;

import net.draconia.jobsemailcollector.domain.PhoneNumber;

public interface PhoneNumberService extends Serializable
{
	public void delete(final PhoneNumber objPhoneNumber);
	public void save(final PhoneNumber objPhoneNumber);
}