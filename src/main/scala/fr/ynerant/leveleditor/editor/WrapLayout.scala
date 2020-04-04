package fr.ynerant.leveleditor.editor

import javax.swing._
import java.awt._


@SerialVersionUID(8777237960365591646L)
class WrapLayout(val alignment: Int) extends FlowLayout(alignment) {
	override def preferredLayoutSize(target: Container): Dimension = layoutSize(target, preferred = true)

	override def minimumLayoutSize(target: Container): Dimension = {
		val minimum = layoutSize(target, preferred = false)
		minimum.width -= (getHgap + 1)
		minimum
	}

	private def layoutSize(target: Container, preferred: Boolean) = {
		var targetWidth = target.getSize.width
		if (targetWidth == 0) targetWidth = Integer.MAX_VALUE
		val hgap = getHgap
		val vgap = getVgap
		val insets = target.getInsets
		val horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2)
		val maxWidth = targetWidth - horizontalInsetsAndGap
		val dim = new Dimension(0, 0)
		var rowWidth = 0
		var rowHeight = 0
		val nmembers = target.getComponentCount
		for (i <- 0 until nmembers) {
			val m = target.getComponent(i)
			if (m.isVisible) {
				val d = if (preferred) m.getPreferredSize
				else m.getMinimumSize
				if (rowWidth + d.width > maxWidth) {
					addRow(dim, rowWidth, rowHeight)
					rowWidth = 0
					rowHeight = 0
				}
				if (rowWidth != 0) rowWidth += hgap
				rowWidth += d.width
				rowHeight = Math.max(rowHeight, d.height)
			}
		}
		addRow(dim, rowWidth, rowHeight)
		dim.width += horizontalInsetsAndGap
		dim.height += insets.top + insets.bottom + vgap * 2
		val scrollPane = SwingUtilities.getAncestorOfClass(classOf[JScrollPane], target)
		if (scrollPane != null) dim.width -= (hgap + 1)
		dim

	}

	private def addRow(dim: Dimension, rowWidth: Int, rowHeight: Int): Unit = {
		dim.width = Math.max(dim.width, rowWidth)
		if (dim.height > 0) dim.height += getVgap
		dim.height += rowHeight
	}
}