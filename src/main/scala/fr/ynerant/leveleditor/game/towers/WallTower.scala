package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.GameFrame

class WallTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "walltower"

	override def getDamagePerShot: Int = Integer.MAX_VALUE

	override def getPeriod = 1

	override def getPrice = 5

	override private[towers] def _shot(game: GameFrame): Unit = ()
}
