package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.mobs.Mob


class BasicTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "basictower"

	override def getDamagePerShot = 1

	override def getPeriod = 5

	override def getPrice = 10

	override private[towers] def _filterDetectedMobs(mobs: Iterable[Mob]) = {
		mobs.filter(mob => (mob.getX == getX || mob.getY == getY) && Math.abs(mob.getX - getX) <= 3 && Math.abs(mob.getY - getY) <= 3)
	}
}
