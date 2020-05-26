package fr.ynerant.leveleditor.api.editor.sprites

import scala.collection.mutable.ListBuffer

object Category {
	def create(name: String, sprites: ListBuffer[Sprite]): Category = {
		val c = new Category
		c.name = name
		c.sprites = sprites
		c
	}
}

class Category private() {
	private var sprites = null: ListBuffer[Sprite]
	private var name = null: String

	def getName: String = name

	def getSprites: ListBuffer[Sprite] = sprites

	def addSprite(s: Sprite): Unit = {
		this.sprites += s
	}

	override def toString: String = name
}
