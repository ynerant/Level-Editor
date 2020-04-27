package fr.ynerant.leveleditor.game.towers;

import fr.ynerant.leveleditor.game.GameFrame

class FreezerTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "freezertower"

	override def getDamagePerShot = if (isUpgraded) 1 else 0

	override def getPeriod = 10

	override def getPrice = 40

	override private[towers] def _shot(game: GameFrame): Unit = {
		game.getMobs.filter(mob => Math.abs(mob.getX - getX) <= 3 && Math.abs(mob.getY - getY) <= 3).foreach(mob => {
			mob.freeze(if (isUpgraded) 100 else 40)
			mob.hit(getDamagePerShot)
		})
	}
}
