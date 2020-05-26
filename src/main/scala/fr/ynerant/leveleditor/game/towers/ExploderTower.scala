package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.GameFrame

import scala.util.Random

class ExploderTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "explodertower"

	override def getDamagePerShot = if (isUpgraded) 7 else 3

	override def getPeriod = 20

	override def getPrice = 70

	override private[towers] def _shot(game: GameFrame): Unit = {
		var l = game.getMobs.filter(mob => Math.pow(mob.getX - getX, 2) + Math.pow(mob.getY - getY, 2) <= 25).toList
		l = Random.shuffle(l)
		if (l.nonEmpty) {
			val target = l.head
			target.hit(getDamagePerShot)
			game.getMobs.filter(mob => Math.pow(mob.getX - target.getX, 2) + Math.pow(mob.getX - target.getX, 2) <= 9)
				.foreach(mob => mob.hit(getDamagePerShot))
		}
	}
}
