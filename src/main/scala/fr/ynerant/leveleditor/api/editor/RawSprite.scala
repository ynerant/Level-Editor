package fr.ynerant.leveleditor.api.editor

import fr.ynerant.leveleditor.api.editor.sprites.Sprite


object RawSprite {
	val BLANK = new RawSprite("blank", 0)

	def create(spr: Sprite): RawSprite = {
		new RawSprite(spr.getCategory.getName, spr.getIndex)
	}
}

case class RawSprite(var category: String, protected var index: Int) {
	if (category == null) category = "blank"

	def getCategory: String = category

	def getIndex: Int = index
}
