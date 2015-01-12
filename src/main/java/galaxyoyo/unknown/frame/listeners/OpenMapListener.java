package galaxyoyo.unknown.frame.listeners;

import galaxyoyo.unknown.api.editor.EditorAPI;
import galaxyoyo.unknown.frame.MainFrame;

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
