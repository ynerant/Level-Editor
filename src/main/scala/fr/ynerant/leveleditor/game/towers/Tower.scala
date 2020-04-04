package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.api.editor.sprites.Sprite
import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister
import fr.ynerant.leveleditor.game.mobs.Mob
import java.util
import java.util.Random


object Tower {
	private val RANDOM = new Random
}

abstract class Tower(val x: Int, val y: Int) {
	final private val sprite = SpriteRegister.getCategory(getName).getSprites.get(0)
	private var remainingTicks = 0L

	def getSprite: Sprite = sprite

	def getName: String

	def getDamagePerShot: Int

	def getPeriod: Long

	def getPrice: Int

	private[towers] def _filterDetectedMobs(mobs: util.Collection[Mob]): util.Collection[Mob]

	def filterDetectedMobs(mobs: util.Collection[Mob]): util.Collection[Mob] = if (remainingTicks > 0) {
		remainingTicks -= 1
		new util.ArrayList[Mob]
	}
	else {
		remainingTicks = getPeriod
		_filterDetectedMobs(mobs)
	}

	def getX: Int = x

	def getY: Int = y
}
