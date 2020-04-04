package fr.ynerant.leveleditor.api.editor.sprites

import java.awt._
import java.awt.image.BufferedImage
import java.util
import java.util.ArrayList


object Sprite {
	val BLANK = new Sprite(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), Category.create("blank", new util.ArrayList[Sprite]), 0)

	val g: Graphics2D
	= BLANK.getImage.createGraphics
	g.setComposite(AlphaComposite.Clear)
	g.setColor(new Color(0, true))
	g.fillRect(0, 0, 16, 16)

}

class Sprite(val img: BufferedImage, val cat: Category, val index: Int) {
	if (!this.cat.getSprites.contains(this)) this.cat.getSprites.add(this)

	def getImage: BufferedImage = this.img

	def getCategory: Category = cat

	def getIndex: Int = index

	override def hashCode: Int = cat.hashCode ^ getIndex

	override def equals(o: Any): Boolean = {
		if (!o.isInstanceOf[Sprite]) return false
		val other = o.asInstanceOf[Sprite]
		hashCode == other.hashCode
	}

	override def toString: String = "{Sprite img=" + img + " cat=" + cat.getName + "}"
}
