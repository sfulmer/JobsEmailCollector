package net.draconia.jobsemailcollector.ui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class InitializablePanel extends JPanel
{
	private static final long serialVersionUID = 587152877392929605L;
	
	public InitializablePanel()
	{
		super();
	}
	
	public InitializablePanel(final boolean bIsDoubleBuffered)
	{
		super(bIsDoubleBuffered);
	}
	
	public InitializablePanel(final LayoutManager objLayoutManager)
	{
		super(objLayoutManager);
	}
	
	public InitializablePanel(final LayoutManager objLayoutManager, final boolean bIsDoubleBuffered)
	{
		super(objLayoutManager, bIsDoubleBuffered);
	}
	
	protected abstract void initPanel();
}