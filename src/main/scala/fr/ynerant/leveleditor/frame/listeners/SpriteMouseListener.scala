package fr.ynerant.leveleditor.frame.listeners

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

import fr.ynerant.leveleditor.editor.{EditorFrame, SpriteComp}


class SpriteMouseListener(val sprite: SpriteComp, val frame: EditorFrame) extends MouseAdapter {
	override def mouseReleased(event: MouseEvent): Unit = {
		if (frame.getSelectedSprite != null) {
			frame.getSelectedSprite.setSelected(false)
			frame.getSelectedSprite.repaint()
		}
		frame.setSelectedSprite(sprite)
		sprite.setSelected(true)
		sprite.repaint()
	}
}
