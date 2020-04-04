package fr.ynerant.leveleditor.api.editor

object RawCase {
	def create(posX: Int, posY: Int, couche1: RawSprite, couche2: RawSprite, couche3: RawSprite, collision: String): RawCase = {
		new RawCase(posX, posY, couche1, couche2, couche3, collision)
	}

	def create(c: Case): RawCase = {
		new RawCase(c.getPosX, c.getPosY, RawSprite.create(c.getCoucheOne), RawSprite.create(c.getCoucheTwo), RawSprite.create(c.getCoucheThree), c.getCollision)
	}
}

case class RawCase(var x: Int, var y: Int, var couche1: RawSprite, var couche2: RawSprite, var couche3: RawSprite, var collision: String) {
	def getPosX: Int = x

	def getPosY: Int = y

	def getCoucheOne: RawSprite = couche1

	def getCoucheTwo: RawSprite = couche2

	def getCoucheThree: RawSprite = couche3

	def getCollision: String = collision

	def setCollision(collision: String): Unit = {
		this.collision = collision
	}
}
