package fr.ynerant.leveleditor.game.towers

import fr.ynerant.leveleditor.game.GameFrame

class UpgradeTower(override val x: Int, override val y: Int) extends Tower(x, y) {
	override def getName = "upgradetower"

	override def getDamagePerShot = if (isUpgraded) 1 else 0

	override def getPeriod = 60

	override def getPrice = 65

	override private[towers] def _shot(game: GameFrame): Unit = {
		game.getTowers.filter(tower => Math.pow(tower.getX - getX, 2) + Math.pow(tower.getY - getY, 2) <= 25 && tower != this).foreach(tower => tower.upgrade)
		if (isUpgraded)
			game.getMobs.filter(mob => Math.pow(mob.getX - getX, 2) + Math.pow(mob.getY - getY, 2) <= 9).foreach(mob => mob.hit(getDamagePerShot))
	}
}
