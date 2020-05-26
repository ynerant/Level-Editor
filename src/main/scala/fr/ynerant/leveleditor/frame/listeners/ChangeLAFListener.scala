package fr.ynerant.leveleditor.frame.listeners

import javax.swing._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener


class ChangeLAFListener(val item: JMenuItem, val frame: JFrame) extends ActionListener {
	override def actionPerformed(event: ActionEvent): Unit = {
		if (item.getText.toLowerCase.contains("sys")) {
			try UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
			catch {
				case e: Exception =>
					new ExceptionInInitializerError("Erreur lors du changement de 'look and feel'").printStackTrace()
					System.err.print("Caused by ")
					e.printStackTrace()
			}
			SwingUtilities.updateComponentTreeUI(frame)
		}
		else if (item.getText.toLowerCase.contains("java")) {
			try UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName)
			catch {
				case e: Exception =>
					new ExceptionInInitializerError("Erreur lors du changement de 'look and feel'").printStackTrace()
					System.err.print("Caused by ")
					e.printStackTrace()
			}
			SwingUtilities.updateComponentTreeUI(frame)
		}
		else if (item.getText.toLowerCase.contains("sombre")) {
			try UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel")
			catch {
				case e: Exception =>
					new ExceptionInInitializerError("Erreur lors du changement de 'look and feel'").printStackTrace()
					System.err.print("Caused by ")
					e.printStackTrace()
			}
			SwingUtilities.updateComponentTreeUI(frame)
		}
	}
}
