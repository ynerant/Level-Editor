package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.mobs.Mob
import java.util


class BasicTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "basictower"

	override def getDamagePerShot = 1

	override def getPeriod = 5

	override def getPrice = 10

	override private[towers] def _filterDetectedMobs(mobs: util.Collection[Mob]) = {
		val filtered = new util.ArrayList[Mob]
		mobs.forEach(mob => {
			if ((mob.getX == getX || mob.getY == getY) && Math.abs(mob.getX - getX) <= 3 && Math.abs(mob.getY - getY) <= 3) filtered.add(mob)
		})
		filtered
	}
}
