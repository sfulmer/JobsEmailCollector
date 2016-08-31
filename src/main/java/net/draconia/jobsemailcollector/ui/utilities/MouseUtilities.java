package net.draconia.jobsemailcollector.ui.utilities;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class MouseUtilities implements Serializable
{
	private static final long serialVersionUID = -4524614418795710398L;
	
	public MouseUtilities()
	{ }
	
	public void installPopupListener(final JComponent objComponent, final JPopupMenu mnuPopup)
	{
		objComponent.addMouseListener(new MouseAdapter()
		{
			protected Component findComponentUnderMouse(final Point ptLocation)
			{
				for(Window wnd : Window.getWindows())
					{
					boolean bIsActive, bIsShowing = objComponent.isShowing();
					
					if(bIsShowing)
						bIsActive = wnd.isActive();
					else
						bIsActive = false;
					
					if(bIsActive)
						for(Component objComponent : wnd.getComponents())
							{
							boolean bContainsPoint;
							
							bContainsPoint = new Rectangle(objComponent.getLocationOnScreen(), objComponent.getSize()).contains(ptLocation);
							
							if(bContainsPoint)
								{
								boolean bIsContainer = (objComponent instanceof Container);
								
								if(bIsContainer)
									return(findComponentUnderMouse((Container)(objComponent), ptLocation));
								else
									return(objComponent);
								}
							}
					}
				
				return(null);
			}
			
			protected Component findComponentUnderMouse(final Container objContainer, final Point ptLocation)
			{
				for(Component objComponent : objContainer.getComponents())
					{
					boolean bContainsPoint, bIsContainer, bIsShowing = objComponent.isShowing();
					
					if(bIsShowing)
						bContainsPoint = new Rectangle(objComponent.getLocationOnScreen(), objComponent.getSize()).contains(ptLocation);
					else
						bContainsPoint = false;
					
					bIsContainer = (objComponent instanceof Container);
					
					if(bIsShowing && bContainsPoint)
						if(bIsContainer)
							return(findComponentUnderMouse((Container)(objComponent), ptLocation));
						else
							return(objContainer);
					}
				
				return(objContainer);
			}
			
			protected void mouseHappened(final MouseEvent objMouseEvent)
			{
				if(objMouseEvent.isPopupTrigger())
					{
					Component objInvoker = findComponentUnderMouse(objMouseEvent.getLocationOnScreen());
					
					mnuPopup.show(objInvoker, objMouseEvent.getX(), objMouseEvent.getY());
					}
			}
			
			public void mousePressed(final MouseEvent objMouseEvent)
			{
				mouseHappened(objMouseEvent);
			}
			
			public void mouseReleased(final MouseEvent objMouseEvent)
			{
				mouseHappened(objMouseEvent);
			}
		});
	}
}