package fr.ynerant.leveleditor.api.editor

import fr.ynerant.leveleditor.api.editor.Collision.Collision
import fr.ynerant.leveleditor.api.editor.sprites.Sprite


object Case {
	def create(posX: Int, posY: Int, couche1: Sprite, couche2: Sprite, couche3: Sprite, collision: Collision): Case = {
		val c = new Case
		c.x = posX
		c.y = posY
		c.couche1 = couche1
		c.couche2 = couche2
		c.couche3 = couche3
		c.collision = collision
		c
	}
}

class Case {
	private var x = 0
	private var y = 0
	private var couche1 = null: Sprite
	private var couche2 = null: Sprite
	private var couche3 = null: Sprite
	private var collision = null: Collision

	def getPosX: Int = x

	def getPosY: Int = y

	def getCoucheOne: Sprite = couche1

	def getCoucheTwo: Sprite = couche2

	def getCoucheThree: Sprite = couche3

	def getCollision: Collision = collision

	override def toString: String = "{Case x=" + x + " y=" + y + " couche1=" + couche1 + " couche2=" + couche2 + " couche3=" + couche3 + " collision=" + collision.toString.toUpperCase + "}\n"
}
