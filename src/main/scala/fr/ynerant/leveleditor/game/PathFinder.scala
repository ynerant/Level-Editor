package fr.ynerant.leveleditor.game

import java.util

import fr.ynerant.leveleditor.api.editor.{Collision, RawCase}

case class PathFinder(game: GameFrame) {
	var pred: Map[Int, RawCase] = Map(): Map[Int, RawCase]

	def invalidate(): Unit = calculatePath()

	def calculatePath(): Unit = {
		pred = Map()
		val queue = new util.ArrayDeque[RawCase]

		for (i <- 0 until game.getMap.getHeight / 16) {
			val start = game.getMap.getCase(0, i)
			if (!start.getCollision.equals(Collision.FULL)) {
				pred += (coords(start) -> null)
				queue.add(start)
			}
		}

		while (!queue.isEmpty) {
			val visiting = queue.poll
			game.getMap.getNeighbours(visiting).foreach(neighbour => {
				if (neighbour != null && !neighbour.collision.equals(Collision.FULL) && !pred.contains(coords(neighbour))) {
					pred += (coords(neighbour) -> visiting)
					queue.add(neighbour)
				}
			})
		}
	}

	def coords(rawCase: RawCase): Int = rawCase.getPosY * game.getMap.getWidth / 16 + rawCase.getPosX

	def nextPos(x: Int, y: Int): RawCase = {
		pred.getOrElse(y * game.getMap.getWidth / 16 + x, null)
	}
}
