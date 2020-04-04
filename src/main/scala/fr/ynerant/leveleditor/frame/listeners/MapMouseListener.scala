package fr.ynerant.leveleditor.frame.listeners

import java.awt.event.{MouseAdapter, MouseEvent}

import fr.ynerant.leveleditor.api.editor.Case
import fr.ynerant.leveleditor.editor.{EditorFrame, MapPanel}


class MapMouseListener(val panel: MapPanel, val frame: EditorFrame) extends MouseAdapter {
	def getFrame: EditorFrame = frame

	override def mouseClicked(event: MouseEvent): Unit = {
		if (frame.getSelectedPaintingMode == 0) {
			val map = getFrame.getMap
			val x = panel.getWidth / 2 - map.getFont.getWidth
			val y = panel.getHeight / 2 - map.getFont.getHeight
			val c = map.getCase((event.getX - x + 2) / 34, (event.getY - y + 2) / 34): Case
			if (c != null && event.getX - x >= 2 && event.getY - y >= 2) if (getFrame.getSelectedSprite != null) {
				var n = null: Case
				getFrame.getSelectedSprite.getCouche match {
					case 0 =>
						n = Case.create(c.getPosX, c.getPosY, getFrame.getSelectedSprite.getSprite, c.getCoucheTwo, c.getCoucheThree, c.getCollision)

					case 1 =>
						n = Case.create(c.getPosX, c.getPosY, c.getCoucheOne, getFrame.getSelectedSprite.getSprite, c.getCoucheThree, c.getCollision)

					case 2 =>
						n = Case.create(c.getPosX, c.getPosY, c.getCoucheOne, c.getCoucheTwo, getFrame.getSelectedSprite.getSprite, c.getCollision)

					case _ =>
						n = c

				}
				map.setCase(n.getPosX, n.getPosY, n)
				panel.repaint()
			}
		}
		else if (frame.getSelectedPaintingMode == 1) {
			getFrame.getMap.getAllCases.foreach(c => {
				val map = getFrame.getMap
				if (getFrame.getSelectedSprite != null) {
					if (getFrame.getSelectedSprite.getCouche - 1 > getFrame.getSelectedLayerIndex) return
					var n = null: Case
					getFrame.getSelectedSprite.getCouche match {
						case 0 =>
							n = Case.create(c.getPosX, c.getPosY, getFrame.getSelectedSprite.getSprite, c.getCoucheTwo, c.getCoucheThree, c.getCollision)

						case 1 =>
							n = Case.create(c.getPosX, c.getPosY, c.getCoucheOne, getFrame.getSelectedSprite.getSprite, c.getCoucheThree, c.getCollision)

						case 2 =>
							n = Case.create(c.getPosX, c.getPosY, c.getCoucheOne, c.getCoucheTwo, getFrame.getSelectedSprite.getSprite, c.getCollision)

						case _ =>
							n = c

					}
					map.setCase(n.getPosX, n.getPosY, n)
				}
			})
			panel.repaint()
		}
	}

	override def mouseDragged(event: MouseEvent): Unit = {
		if (frame.getSelectedPaintingMode == 0) mouseClicked(event)
	}
}
