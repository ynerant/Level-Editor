package fr.ynerant.leveleditor.editor

import fr.ynerant.leveleditor.api.editor.sprites.SpriteRegister
import java.awt.image.BufferedImage
import java.util

import fr.ynerant.leveleditor.api.editor.{Case, RawMap}


object Map {
	private var cases = null: util.ArrayList[Case]
}

class Map(val raw: RawMap) {
	final private var frame = null: EditorFrame
	final private var width = 0
	final private var height = 0
	final private val casesMap = new util.HashMap[Integer, util.Map[Integer, Case]]
	final private var font = null: BufferedImage

	Map.cases = new util.ArrayList[Case]
	this.width = raw.getWidth
	this.height = raw.getHeight
	this.font = raw.getFont

	raw.getCases.forEach(rc =>
		Map.cases.add(Case.create(rc.getPosX, rc.getPosY, SpriteRegister.getCategory(rc.getCoucheOne.getCategory).getSprites.get(rc.getCoucheOne.getIndex), SpriteRegister.getCategory(rc.getCoucheTwo.getCategory).getSprites.get(rc.getCoucheTwo.getIndex), SpriteRegister.getCategory(rc.getCoucheThree.getCategory).getSprites.get(rc.getCoucheThree.getIndex), rc.getCollision))
	)

	reorganizeMap()
	frame = new EditorFrame(this)
	getFrame.setVisible(true)

	def getFrame: EditorFrame = frame

	def getWidth: Int = width

	def getHeight: Int = height

	def getCase(x: Int, y: Int): Case = casesMap.getOrDefault(x, new util.HashMap[Integer, Case]).get(y)

	def setCase(x: Int, y: Int, c: Case): Unit = {
		casesMap.get(x).put(y, c)
	}

	def getFont: BufferedImage = font

	private def reorganizeMap(): Unit = {
		for (i <- 0 until width) {
			casesMap.put(i, new util.HashMap[Integer, Case])
		}
		Map.cases.forEach(c => setCase(c.getPosX, c.getPosY, c))
	}

	def getAllCases: util.List[Case] = {
		val list = new util.ArrayList[Case]
		casesMap.values.forEach(l => list.addAll(l.values()))
		list
	}
}
