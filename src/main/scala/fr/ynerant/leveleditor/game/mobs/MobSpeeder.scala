package fr.ynerant.leveleditor.game.mobs

import fr.ynerant.leveleditor.game.GameFrame

class MobSpeeder extends Mob {
	override def getMaxHP = 25

	override def _getSlowness = 60

	override def getReward = 30

	override def getName = "mobspeeder"

	override def _tick(game: GameFrame): Unit = {
		game.getMobs.filter(mob => Math.pow(mob.getX - getX, 2) + Math.pow(mob.getY - getY, 2) <= 9).foreach(mob => mob.speedup(3))
	}
}
