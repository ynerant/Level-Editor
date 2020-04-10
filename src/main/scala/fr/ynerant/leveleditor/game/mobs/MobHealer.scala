package fr.ynerant.leveleditor.game.mobs

import fr.ynerant.leveleditor.game.GameFrame

class MobHealer extends Mob {
	override def getMaxHP = 20

	override def _getSlowness = 60

	override def getReward = 20

	override def getName = "mobhealer"

	override def _tick(game: GameFrame): Unit = {
		game.getMobs.filter(mob => Math.pow(mob.getX - getX, 2) + Math.pow(mob.getY - getY, 2) <= 9).foreach(mob => mob.heal(1))
	}
}
