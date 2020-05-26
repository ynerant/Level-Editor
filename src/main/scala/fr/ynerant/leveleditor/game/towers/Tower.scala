package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.api.editor.sprites.{Sprite, SpriteRegister}
import fr.ynerant.leveleditor.game.GameFrame

abstract class Tower(val x: Int, val y: Int) {
	final private val sprite = SpriteRegister.getCategory(getName).getSprites.head
	private var remainingTicks = 0L
	private var upgraded = false

	def getSprite: Sprite = sprite

	def getName: String

	def getDamagePerShot: Int

	def getPeriod: Long

	def getPrice: Int

	def upgrade: Unit = upgraded = true

	def isUpgraded: Boolean = upgraded

	def shot(game: GameFrame): Unit = if (remainingTicks > 0)
		remainingTicks -= 1
	else {
		remainingTicks = getPeriod
		_shot(game)
	}

	private[towers] def _shot(game: GameFrame): Unit

	def getX: Int = x

	def getY: Int = y
}
