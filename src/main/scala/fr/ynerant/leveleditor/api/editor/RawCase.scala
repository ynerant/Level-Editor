package fr.ynerant.leveleditor.api.editor

import fr.ynerant.leveleditor.api.editor.Collision.Collision

object RawCase {
	def create(posX: Int, posY: Int, couche1: RawSprite, couche2: RawSprite, couche3: RawSprite, collision: Collision): RawCase = {
		new RawCase(posX, posY, couche1, couche2, couche3, collision)
	}

	def create(c: Case): RawCase = {
		new RawCase(c.getPosX, c.getPosY, RawSprite.create(c.getCoucheOne), RawSprite.create(c.getCoucheTwo), RawSprite.create(c.getCoucheThree), c.getCollision)
	}
}

case class RawCase(var x: Int, var y: Int, var couche1: RawSprite, var couche2: RawSprite, var couche3: RawSprite, var collision: Collision) {
	def getPosX: Int = x

	def getPosY: Int = y

	def getCoucheOne: RawSprite = couche1

	def getCoucheTwo: RawSprite = couche2

	def getCoucheThree: RawSprite = couche3

	def getCollision: Collision = collision

	def setCollision(collision: Collision): Unit = {
		this.collision = collision
	}
}
