package galaxyoyo.unknown.frame.listeners;

import galaxyoyo.unknown.api.editor.EditorAPI;

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
		EditorAPI.open();
	}
}
