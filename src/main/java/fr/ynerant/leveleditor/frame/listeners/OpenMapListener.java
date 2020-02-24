package fr.ynerant.leveleditor.frame.listeners;

import fr.ynerant.leveleditor.api.editor.EditorAPI;
import fr.ynerant.leveleditor.frame.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenMapListener implements ActionListener
{
	/* !CodeTemplates.overridecomment.nonjd!
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (EditorAPI.open() != null)
			MainFrame.getInstance().dispose();
	}
}
