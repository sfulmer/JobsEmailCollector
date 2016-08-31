package net.draconia.jobsemailcollector.parsers;

import java.io.Serializable;

public interface Parser extends Serializable
{
	public void parse() throws Exception;
}