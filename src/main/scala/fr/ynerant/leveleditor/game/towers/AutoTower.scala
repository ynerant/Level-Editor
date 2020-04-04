package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.mobs.Mob
import java.util


class AutoTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "autotower"

	override def getDamagePerShot = 20

	override def getPeriod = 10

	override def getPrice = 142

	override private[towers] def _filterDetectedMobs(mobs: util.Collection[Mob]) = mobs
}
