/**
 * @author galaxyoyo
 */
package galaxyoyo.gameengine.frame.listeners;

import galaxyoyo.alice.gameengine.client.main.Main;
import galaxyoyo.gameengine.frame.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author galaxyoyo
 */
public class CreateMapListener implements ActionListener
{
	/* !CodeTemplates.overridecomment.nonjd!
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (Main.launchEditMode())
			MainFrame.getInstance().dispose();
	}
}
