package fr.ynerant.leveleditor.frame.listeners

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import fr.ynerant.leveleditor.client.main.Main
import fr.ynerant.leveleditor.frame.MainFrame


/**
 * @author ÿnérant
 */
class CreateMapListener extends ActionListener {
	/* !CodeTemplates.overridecomment.nonjd!
		* @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		*/ override def actionPerformed(event: ActionEvent): Unit = {
		if (Main.launchEditMode) MainFrame.getInstance.dispose()
	}
}
