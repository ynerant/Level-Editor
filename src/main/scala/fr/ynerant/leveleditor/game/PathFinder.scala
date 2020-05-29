package fr.ynerant.leveleditor.game

import java.util

import fr.ynerant.leveleditor.api.editor.{Collision, RawCase}

case class PathFinder(game: GameFrame) {
	var pred: Map[Int, RawCase] = Map(): Map[Int, RawCase]

	def invalidate(): Unit = calculatePath()

	def calculatePath(): Unit = {
		pred = Map()
		val queueactu = new util.ArrayDeque[RawCase]
		val queuesuivdroit = new util.ArrayDeque[(RawCase, RawCase)]
		val queuesuivdiag = new util.ArrayDeque[(RawCase, RawCase)]
		var edges = List(): List[(RawCase, RawCase)]

		for (i <- 0 until game.getMap.getHeight / 16) {
			val start = game.getMap.getCase(0, i)
			if (!start.getCollision.equals(Collision.FULL)) {
				pred += (coords(start) -> null)
				queueactu.add(start)
			}
		}

		while (!queueactu.isEmpty) {
			val visiting = queueactu.poll
			game.getMap.getNeighbours(visiting).foreach(neighbour => {
				if ((neighbour != null && !neighbour.collision.equals(Collision.FULL)) && !edges.contains(visiting, neighbour)
					&& (!game.getMap.getCase(neighbour.getPosX, visiting.getPosY).collision.equals(Collision.FULL)
					&& !game.getMap.getCase(visiting.getPosX, neighbour.getPosY).collision.equals(Collision.FULL))) {
					edges ::= (visiting, neighbour)
					if (visiting.getPosY == neighbour.getPosY || visiting.getPosX == neighbour.getPosX) {
						queuesuivdroit.add((neighbour, visiting))
					}
					else {
						queuesuivdiag.add((neighbour, visiting))
					}
				}
			})

			if (queueactu.isEmpty) {
				while (!queuesuivdroit.isEmpty) {
					val (actu, prev) = queuesuivdroit.poll
					if (!pred.contains(coords(actu))) {
						pred += (coords(actu) -> prev)
						queueactu.add(actu)
					}
				}
				while (!queuesuivdiag.isEmpty) {
					val (actu, prev) = queuesuivdiag.poll
					if (!pred.contains(coords(actu))) {
						pred += (coords(actu) -> prev)
						queueactu.add(actu)
					}
				}
			}
		}
	}

	def coords(RawCase: RawCase): Int = RawCase.getPosY * game.getMap.getWidth / 16 + RawCase.getPosX

	def nextPos(x: Int, y: Int): RawCase = {
		pred.getOrElse(y * game.getMap.getWidth / 16 + x, null)
	}
}
