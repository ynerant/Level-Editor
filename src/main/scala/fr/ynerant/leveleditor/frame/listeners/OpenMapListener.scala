package fr.ynerant.leveleditor.frame.listeners

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import fr.ynerant.leveleditor.api.editor.EditorAPI
import fr.ynerant.leveleditor.frame.MainFrame


class OpenMapListener extends ActionListener {
	/* !CodeTemplates.overridecomment.nonjd!
		* @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		*/ override def actionPerformed(event: ActionEvent): Unit = {
		if (EditorAPI.open != null) MainFrame.getInstance.dispose()
	}
}
