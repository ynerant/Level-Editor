package fr.ynerant.leveleditor.game.mobs

import fr.ynerant.leveleditor.game.GameFrame

class MobBreaker extends Mob {
	override def getMaxHP = 110

	override def _getSlowness = 120

	override def getReward = 70

	override def getName = "mobhealer"

	override def _tick(game: GameFrame): Unit = {
		game.getTowers.filter(tower => Math.abs(tower.getX - getX) + Math.abs(tower.getY - getY) <= 1).foreach(tower => game.breakTower(tower))
		game.getPathFinder.invalidate
	}
}
