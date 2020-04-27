package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.GameFrame

import scala.util.Random

class BasicTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "basictower"

	override def getDamagePerShot = if (isUpgraded) 3 else 1

	override def getPeriod = 5

	override def getPrice = 10

	override private[towers] def _shot(game: GameFrame): Unit = {
		var l = game.getMobs.filter(mob => Math.pow(mob.getX - getX, 2) + Math.pow(mob.getY - getY, 2) <= 9).toList
		l = Random.shuffle(l)
		if (l.nonEmpty)
			l.head.hit(getDamagePerShot)
	}
}
