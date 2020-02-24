package fr.ynerant.leveleditor.frame.listeners;

import fr.ynerant.leveleditor.client.main.Main;
import fr.ynerant.leveleditor.frame.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ÿnérant
 */
public class CreateMapListener implements ActionListener {
    /* !CodeTemplates.overridecomment.nonjd!
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (Main.launchEditMode())
            MainFrame.getInstance().dispose();
    }
}
