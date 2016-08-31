package net.draconia.jobsemailcollector.ui.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.tuple.Pair;

import net.draconia.jobsemailcollector.manager.SMTPManager;
import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class SendResume extends AbstractAction
{
	private static final long serialVersionUID = -3590876727267718170L;
	
	private SendResumeDialogModel mObjModel;
	private Window mWndParent;
	
	public SendResume(final SendResumeDialogModel objModel, final Window wndParent)
	{
		super("Send Resume");
		
		try
			{
			putValue(SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/mail.png"))));
			}
		catch(IOException objIOException)
			{
			objIOException.printStackTrace(System.err);
			}
		
		setModel(objModel);
		setParentWindow(wndParent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		try
			{
			//new SMTPManager().sendEmail("seth.fulmer@gmail.com", "Test", "Hello Seth!", new File[] {new File("I:\\Jobs\\Seth Resume.doc")});
			new SMTPManager().sendEmail(getModel().getTo(), getModel().getSubject(), getModel().getBody(), getAttachments());
			}
		catch(AddressException objAddressException)
			{
			objAddressException.printStackTrace(System.err);
			}
	}
	
	protected File[] getAttachments()
	{
		List<File> lstFiles = new ArrayList<File>();
		
		for(Pair<File, Boolean> pairFile : getModel().getAttachmentList())
			lstFiles.add(pairFile.getKey());
		
		return(lstFiles.toArray(new File[0]));
	}
	
	protected SendResumeDialogModel getModel()
	{
		return(mObjModel);
	}
	
	protected Window getParentWindow()
	{
		return(mWndParent);
	}
	
	protected void setModel(final SendResumeDialogModel objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setParentWindow(final Window wndParent)
	{
		mWndParent = wndParent;
	}
}