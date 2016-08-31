package net.draconia.jobsemailcollector.ui.actions.popup;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class TextDelete extends AbstractAction
{
	private static final long serialVersionUID = 3965383888469417477L;
	
	public TextDelete()
	{
		super("Delete");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_D);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		JPopupMenu mnuPopup = (JPopupMenu)(((Component)(objActionEvent.getSource())).getParent());
		JTextComponent txtComponent = ((JTextComponent)(mnuPopup.getInvoker()));
		
		if(txtComponent.getSelectionStart() != txtComponent.getSelectionEnd())
			txtComponent.selectAll();
		
		txtComponent.replaceSelection("");
	}
}