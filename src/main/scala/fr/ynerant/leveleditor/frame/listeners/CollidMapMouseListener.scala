package fr.ynerant.leveleditor.frame.listeners

import java.awt.event.{MouseAdapter, MouseEvent}

import fr.ynerant.leveleditor.api.editor.{Case, Collision}
import fr.ynerant.leveleditor.editor.{CollidPanel, EditorFrame}


class CollidMapMouseListener(val panel: CollidPanel, val frame: EditorFrame) extends MouseAdapter {
	def getFrame: EditorFrame = frame

	override def mouseReleased(event: MouseEvent): Unit = {
		val map = getFrame.getMap
		val x = panel.getWidth / 2 - map.getFont.getWidth
		val y = panel.getHeight / 2 - map.getFont.getHeight
		val c = map.getCase((event.getX - x + 2) / 34, (event.getY - y + 2) / 34)
		if (c != null && event.getX - x >= 2 && event.getY - y >= 2) {
			val col = c.getCollision match {
				case Collision.ANY => Collision.PARTIAL
				case Collision.PARTIAL => Collision.FULL
				case Collision.FULL => Collision.ANY
			}
			val n = Case.create(c.getPosX, c.getPosY, c.getCoucheOne, c.getCoucheTwo, c.getCoucheThree, col)
			map.setCase((event.getX - x + 2) / 34, (event.getY - y + 2) / 34, n)
			panel.repaint()
		}
	}
}
