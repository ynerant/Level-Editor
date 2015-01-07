/**
 * @author galaxyoyo
 */
package galaxyoyo.unknown.frame.listeners;

import galaxyoyo.unknown.client.main.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author galaxyoyo
 */
public class CreateMapListener implements ActionListener
{
	/**
	 * 
	 */
	public CreateMapListener()
	{
	}

	/* !CodeTemplates.overridecomment.nonjd!
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		Main.main("--edit", Main.isInDebugMode() ? " --debug true" : "");
	}

}
