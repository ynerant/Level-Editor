package fr.ynerant.leveleditor.frame.listeners;

import fr.ynerant.leveleditor.frame.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ChangeLAFListener implements ActionListener
{
	private final JMenuItem item;
	private final JFrame frame;
	
	public ChangeLAFListener(JMenuItem LAF, MainFrame f)
	{
		this.item = LAF;
		this.frame = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (item.getText().toLowerCase().contains("sys"))
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception e)
			{
				new ExceptionInInitializerError("Erreur lors du changement de 'look and feel'").printStackTrace();
				System.err.print("Caused by ");
				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		else if (item.getText().toLowerCase().contains("java"))
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
			catch (Exception e)
			{
				new ExceptionInInitializerError("Erreur lors du changement de 'look and feel'").printStackTrace();
				System.err.print("Caused by ");
				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		else if (item.getText().toLowerCase().contains("sombre"))
		{
			try
			{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			}
			catch (Exception e)
			{
				new ExceptionInInitializerError("Erreur lors du changement de 'look and feel'").printStackTrace();
				System.err.print("Caused by ");
				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
	}
}
