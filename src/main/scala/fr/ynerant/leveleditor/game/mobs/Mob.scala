package fr.ynerant.leveleditor.game.mobs

import fr.ynerant.leveleditor.api.editor.RawCase
import fr.ynerant.leveleditor.api.editor.sprites.{Sprite, SpriteRegister}
import fr.ynerant.leveleditor.game.GameFrame

import scala.util.Random

object Mob {
	private val RANDOM = new Random

	def getRandomMob: Mob = RANDOM.nextInt(6) match {
		case 0 =>
			new Mob1
		case 1 =>
			new Mob2
		case 2 =>
			new MobStrong
		case 3 =>
			new MobHealer
		case 4 =>
			new MobBreaker
		case 5 =>
			new MobSpeeder
	}
}

abstract class Mob() {
	private var hp = getMaxHP
	private var tickRemains = 0L
	private var initialTicks = 0L
	private var sprite = null: Sprite
	private var x = 0
	private var y = 0
	private var freezeTime = 0
	private var speedMultiplier = 1

	initialTicks = getSlowness
	tickRemains = initialTicks

	def getMaxHP: Int

	def _getSlowness: Long

	def getSlowness: Long = {
		(_getSlowness * Random.between(0.95, 1.05) * (if (freezeTime > 0) 2 else 1) / speedMultiplier).toLong
	}

	def progress: Float = tickRemains.toFloat / initialTicks.toFloat

	def getReward: Int

	def getName: String

	def getSprite: Sprite = {
		if (sprite == null) sprite = SpriteRegister.getCategory(getName).getSprites.head
		sprite
	}

	def getX: Int = x

	def getY: Int = y

	def move(x: Int, y: Int): Unit = {
		this.x = x
		this.y = y
	}

	def freeze(time: Int): Unit = {
		if (freezeTime == 0) {
			initialTicks += tickRemains
			tickRemains *= 2
		}
		freezeTime = time
	}

	def speedup(multiplier: Int): Unit = speedMultiplier = multiplier

	def heal(hp: Int): Unit = {
		this.hp = Math.min(hp + 1, getMaxHP)
	}

	def getHP: Int = hp

	def isDead: Boolean = hp <= 0

	def setHP(hp: Int): Unit = {
		this.hp = hp
	}

	def hit(damage: Int): Boolean = {
		if (!isDead) {
			this.hp -= damage
			return true
		}
		false
	}

	/**
	 * Called each game tick
	 */
	def tick(game: GameFrame): Unit = {
		if (freezeTime > 0)
			freezeTime -= 1

		if (tickRemains > 0) tickRemains -= 1
		else {
			initialTicks = getSlowness
			tickRemains = initialTicks
			val current = game.getMap.getCase(getX, getY)
			if (current.getPosX == 0) {
				move(-1, getY)
				return
			}

			_tick(game)

			val newCase: RawCase = game.getPathFinder.nextPos(getX, getY)
			move(newCase.getPosX, newCase.getPosY)
		}
	}

	/**
	 * Custom mobs override this function to do some custom stuff
	 */
	def _tick(game: GameFrame): Unit = ()

	override def toString: String = "Mob{" + "sprite=" + sprite + ", x=" + x + ", y=" + y + '}'
}
