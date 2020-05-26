package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.GameFrame

class LaserTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "lasertower"

	override def getDamagePerShot = if (isUpgraded) 8 else 3

	override def getPeriod = 40

	override def getPrice = 80

	override private[towers] def _shot(game: GameFrame): Unit = {
		game.getMobs.filter(mob => mob.getX == getX || mob.getY == getY).foreach(mob => mob.hit(getDamagePerShot))
	}
}
