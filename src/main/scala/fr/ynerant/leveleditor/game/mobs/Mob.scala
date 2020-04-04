package fr.ynerant.leveleditor.game.mobs

import java.util
import java.util.Random

import fr.ynerant.leveleditor.api.editor.RawCase
import fr.ynerant.leveleditor.api.editor.sprites.{Sprite, SpriteRegister}
import fr.ynerant.leveleditor.game.GameFrame


object Mob {
	private val RANDOM = new Random

	def getRandomMob: Mob = RANDOM.nextInt(3) match {
		case 1 =>
			new Mob1
		case 2 =>
			new Mob2
		case _ =>
			new MobCancer
	}
}

abstract class Mob() {
	private var hp = getMaxHP
	private var tickRemains = getSlowness
	private var sprite = null: Sprite
	private var x = 0
	private var y = 0

	def getMaxHP: Int

	def getSlowness: Long

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

	def tick(game: GameFrame): Unit = {
		if (tickRemains > 0) tickRemains -= 1
		else {
			tickRemains = getSlowness
			val current = game.getMap.getCase(getX, getY)
			if (current.getPosX == 0) {
				move(-1, getY)
				return
			}

			var visited = Nil: List[RawCase]
			val queue = new util.ArrayDeque[RawCase]
			var pred = Map(): Map[RawCase, RawCase]
			var last = null: RawCase
			queue.add(current)
			while (!queue.isEmpty) {
				val visiting = queue.poll
				visited ::= visiting
				game.getMap.getNeighbours(visiting).foreach(neighbour => {
					if (neighbour != null && !visited.contains(neighbour)) {
						pred += (neighbour -> visiting)
						queue.add(neighbour)
						if (neighbour.getPosX == 0) {
							last = neighbour
							queue.clear()
							return
						}
					}
				})
				if (last != null) {
					while (pred(last) != current) last = pred(last)
					move(last.getPosX, last.getPosY)
				}
			}
		}
	}

	override def toString: String = "Mob{" + "sprite=" + sprite + ", x=" + x + ", y=" + y + '}'
}