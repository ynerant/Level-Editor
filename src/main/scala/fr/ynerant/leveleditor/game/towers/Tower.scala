package fr.ynerant.leveleditor.game.towers

import java.util.Random

import fr.ynerant.leveleditor.api.editor.sprites.{Sprite, SpriteRegister}
import fr.ynerant.leveleditor.game.mobs.Mob


object Tower {
	private val RANDOM = new Random
}

abstract class Tower(val x: Int, val y: Int) {
	final private val sprite = SpriteRegister.getCategory(getName).getSprites.head
	private var remainingTicks = 0L

	def getSprite: Sprite = sprite

	def getName: String

	def getDamagePerShot: Int

	def getPeriod: Long

	def getPrice: Int

	def filterDetectedMobs(mobs: Iterable[Mob]): Iterable[Mob] = if (remainingTicks > 0) {
		remainingTicks -= 1
		Nil
	}
	else {
		remainingTicks = getPeriod
		_filterDetectedMobs(mobs)
	}

	private[towers] def _filterDetectedMobs(mobs: Iterable[Mob]): Iterable[Mob]

	def getX: Int = x

	def getY: Int = y
}
