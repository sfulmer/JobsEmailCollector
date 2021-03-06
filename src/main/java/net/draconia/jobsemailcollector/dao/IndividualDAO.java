package net.draconia.jobsemailcollector.dao;

import java.io.Serializable;

import java.util.List;

import net.draconia.jobsemailcollector.domain.Individual;

public interface IndividualDAO extends Serializable
{
	public void delete(final Individual objIndividual);
	public Individual getIndividualById(final Integer iId);
	public Individual getIndividualByName(final String sName);
	public Long getIndividualCount();
	public List<Individual> list();
	public void save(final Individual objIndividual);
}