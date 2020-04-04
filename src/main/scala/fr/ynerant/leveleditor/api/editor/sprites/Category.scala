package fr.ynerant.leveleditor.api.editor.sprites

import java.util

object Category {
	def create(name: String, sprites: util.List[Sprite]): Category = {
		val c = new Category
		c.name = name
		c.sprites = sprites
		c
	}
}

class Category private() {
	private var sprites = null: util.List[Sprite]
	private var name = null: String

	def getName: String = name

	def getSprites: util.List[Sprite] = sprites

	override def toString: String = name
}
