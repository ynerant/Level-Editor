package fr.ynerant.leveleditor.api.editor

object Collision {
	val FULL: String = "FULL"
	val PARTIAL: String = "PARTIAL"
	val ANY: String = "ANY"

	val values: IndexedSeq[String] = IndexedSeq(FULL, PARTIAL, ANY)
}
