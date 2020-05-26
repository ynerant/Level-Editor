package fr.ynerant.leveleditor.editor

import fr.ynerant.leveleditor.api.editor.sprites.Sprite
import javax.swing._
import java.awt._


@SerialVersionUID(-6512257366877053285L)
class SpriteComp(val sprite: Sprite, val couche: Int) extends JComponent {
	this.setMinimumSize(new Dimension(32, 32))
	this.setMaximumSize(new Dimension(32, 32))
	this.setPreferredSize(new Dimension(32, 32))
	this.setSize(new Dimension(32, 32))
	repaint()
	private var selected = false

	def getSprite: Sprite = sprite

	def getCouche: Int = couche

	def isSelected: Boolean = selected

	def setSelected(selected: Boolean): Unit = {
		this.selected = selected
	}

	override def paintComponent(g: Graphics): Unit = {
		super.paintComponent(g)
		g.setColor(Color.white)
		g.fillRect(0, 0, getWidth, getHeight)
		g.drawImage(sprite.getImage, 0, 0, 32, 32, Color.white, null)
		if (isSelected) {
			g.setColor(Color.black)
			g.drawLine(0, 0, getWidth - 1, 0)
			g.drawLine(0, 0, 0, getHeight - 1)
			g.drawLine(0, getHeight - 1, getWidth - 1, getHeight - 1)
			g.drawLine(getWidth - 1, 0, getWidth - 1, getHeight - 1)
		}
	}
}
