package fr.ynerant.leveleditor.editor

import java.awt._
import java.awt.image.BufferedImage

import fr.ynerant.leveleditor.api.editor.Collision
import javax.swing._


@SerialVersionUID(-138754019431984881L)
object CollidPanel {
	def isEmpty(image: BufferedImage): Boolean = {
		var allrgba = 0
		for (x <- 0 until image.getWidth) {
			for (y <- 0 until image.getHeight) {
				allrgba += image.getRGB(x, y) + 1
			}
		}
		allrgba == 0
	}
}

@SerialVersionUID(-138754019431984881L)
class CollidPanel(val frame: EditorFrame) extends JPanel {
	override def paintComponent(g: Graphics): Unit = {
		g.fillRect(0, 0, getWidth, getHeight)
		val img = getMap.getFont
		val x = getWidth / 2 - img.getWidth
		val y = getHeight / 2 - img.getHeight
		val width = img.getWidth * 2
		val height = img.getHeight * 2
		g.drawImage(getMap.getFont, x, y, width, height, null)
		getMap.getAllCases.foreach(c => {
			if (!CollidPanel.isEmpty(c.getCoucheOne.getImage)) {
				g.drawImage(c.getCoucheOne.getImage, x + c.getPosX * 34 + 2, y + c.getPosY * 34 + 2, 32, 32, null)
				if (!CollidPanel.isEmpty(c.getCoucheTwo.getImage)) {
					g.drawImage(c.getCoucheTwo.getImage, x + c.getPosX * 34 + 2, y + c.getPosY * 34 + 2, 32, 32, null)
					if (!CollidPanel.isEmpty(c.getCoucheThree.getImage))
						g.drawImage(c.getCoucheThree.getImage, x + c.getPosX * 34 + 2, y + c.getPosY * 34 + 2, 32, 32, null)
				}
			}
		})

		getMap.getAllCases.foreach(c => {
			if (c.getCollision.equals(Collision.ANY)) {
				val alpha = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)
				if (c.getCollision eq Collision.FULL) {
					val grap = alpha.createGraphics
					grap.setColor(new Color(0, 0, 0, 100))
					grap.fillRect(0, 0, 16, 16)
					grap.dispose()
				}
				else if (c.getCollision eq Collision.PARTIAL) {
					val grap = alpha.createGraphics
					grap.setColor(new Color(255, 0, 255, 70))
					grap.fillRect(0, 0, 16, 16)
					grap.dispose()
				}
				g.drawImage(alpha, x + c.getPosX * 34 + 2, y + c.getPosY * 34 + 2, 32, 32, null)
			}
		})
	}

	def getMap: GMap = frame.getMap
}
