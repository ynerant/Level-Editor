package fr.ynerant.leveleditor.editor

import javax.swing._
import java.awt._
import java.awt.image.BufferedImage


@SerialVersionUID(2629019576253690557L)
class MapPanel(val frame: EditorFrame) extends JPanel {
	def getMap: Map = frame.getMap

	override def paintComponent(g: Graphics): Unit = {
		g.fillRect(0, 0, getWidth, getHeight)
		val img = getMap.getFont
		val x = getWidth / 2 - img.getWidth
		val y = getHeight / 2 - img.getHeight
		val width = img.getWidth * 2
		val height = img.getHeight * 2
		g.drawImage(getMap.getFont, x, y, width, height, null)
		getMap.getAllCases.forEach(c => { //		BufferedImage image;
			if (!isEmpty(c.getCoucheOne.getImage)) g.drawImage(c.getCoucheOne.getImage, x + c.getPosX * 34 + 2, y + c.getPosY * 34 + 2, 32, 32, null)
			/*	if (frame.getSelectedLayerIndex() != 0)
						{
							image = recalculateAplha(c.getCoucheOne().getImage(), 0);
							g.drawImage(image, x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
						}*/ if (!isEmpty(c.getCoucheTwo.getImage) && frame.getSelectedLayerIndex >= 1) g.drawImage(c.getCoucheTwo.getImage, x + c.getPosX * 34 + 2, y + c.getPosY * 34 + 2, 32, 32, null)
			/*	if (frame.getSelectedLayerIndex() != 1)
						{
							image = recalculateAplha(c.getCoucheTwo().getImage(), 1);
							g.drawImage(image, x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
						}*/ if (!isEmpty(c.getCoucheThree.getImage) && frame.getSelectedLayerIndex == 2) g.drawImage(c.getCoucheThree.getImage, x + c.getPosX * 34 + 2, y + c.getPosY * 34 + 2, 32, 32, null)
			/*	if (frame.getSelectedLayerIndex() != 2)
						{
							image = recalculateAplha(c.getCoucheThree().getImage(), 2);
							g.drawImage(image, x + c.getPosX() * 34 + 2, y + c.getPosY() * 34 + 2, 32, 32, null);
						}*/
		})
	}

	@SuppressWarnings(Array("BooleanMethodIsAlwaysInverted")) private def isEmpty(image: BufferedImage) = {
		var allrgba = 0
		for (x <- 0 until image.getWidth) {
			for (y <- 0 until image.getHeight) {
				allrgba += image.getRGB(x, y) + 1
			}
		}
		allrgba == 0
	}
}
