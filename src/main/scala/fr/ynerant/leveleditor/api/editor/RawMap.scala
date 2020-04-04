package fr.ynerant.leveleditor.api.editor

import fr.ynerant.leveleditor.editor.Map
import java.awt.image.BufferedImage
import java.util
import java.util.stream.Collectors

import com.google.gson.Gson


object RawMap {
	def create(cases: util.List[RawCase], width: Int, height: Int): RawMap = {
		new RawMap(cases, width, height)
	}

	def create(map: Map): RawMap = {
		val raw = new RawMap(new util.ArrayList[RawCase], 0, 0)
		raw.width = map.getWidth
		raw.height = map.getHeight
		raw.cases = new util.ArrayList[RawCase]
		map.getAllCases.forEach(c => raw.cases.add(RawCase.create(c)))
		raw
	}
}

case class RawMap(var cases: util.List[RawCase], var width: Int, var height: Int) {
	private var cases_map = null: util.HashMap[Integer, RawCase]
	private var font = null: BufferedImage

	def getCases: util.List[RawCase] = cases

	def getCase(x: Int, y: Int): RawCase = {
		if (cases_map == null) {
			cases_map = new util.HashMap[Integer, RawCase]
			getCases.forEach(c => cases_map.put(c.getPosY * width + c.getPosX, c))
		}
		cases_map.get(y * getWidth + x)
	}

	def getNeighbours(c: RawCase): util.Collection[RawCase] = {
		val list = new util.ArrayList[RawCase]
		list.add(getCase(c.getPosX - 1, c.getPosY))
		list.add(getCase(c.getPosX, c.getPosY - 1))
		list.add(getCase(c.getPosX + 1, c.getPosY))
		list.add(getCase(c.getPosX, c.getPosY + 1))
		list.stream.filter((_c: RawCase) => _c != null && (_c.getCollision == Collision.ANY)).collect(Collectors.toList())
	}

	def getWidth: Int = width

	def getHeight: Int = height

	def getFont: BufferedImage = font

	def setFont(font: BufferedImage): Unit = {
		this.font = font
	}

	override def toString: String = new Gson().toJson(this)
}
