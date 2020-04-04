package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.mobs.Mob
import java.util


class NullTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "nulltower"

	override def getDamagePerShot: Int = Integer.MAX_VALUE

	override def getPeriod = 1

	override def getPrice = 5

	override private[towers] def _filterDetectedMobs(mobs: util.Collection[Mob]) = new util.ArrayList[Mob]
}
