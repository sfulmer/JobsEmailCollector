package net.draconia.jobsemailcollector.ui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class GridLayoutPanel extends JPanel
{
	private static final long serialVersionUID = 4599944166293958183L;
	
	public GridLayoutPanel(final int iRows, final int iColumns, final Component[] arrComponents)
	{
		this(iRows, iColumns, 0, 0, arrComponents);
	}
	
	public GridLayoutPanel(final int iRows, final int iColumns, final int iHorizontalGap, final int iVerticalGap, final Component[] arrComponents)
	{
		super(new GridLayout(iRows, iColumns, iHorizontalGap, iVerticalGap));
		
		for(Component objComponent : arrComponents)
			add(objComponent);
	}
}