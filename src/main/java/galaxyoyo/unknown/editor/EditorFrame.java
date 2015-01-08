package galaxyoyo.unknown.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EditorFrame extends JFrame
{
	private static final long serialVersionUID = -2705122356101556462L;
	
	@SuppressWarnings("unused")
	private final Map map;
	
	private final JPanel content = new JPanel();
	
	private final JTabbedPane tabs = new JTabbedPane(JTabbedPane.BOTTOM);
	private final JPanel tabEvents = new JPanel();
	private final JPanel tabColl = new JPanel();
	private final JPanel mapPanel = new JPanel();
	private final JTabbedPane resources = new JTabbedPane();
	private final JPanel couche1 = new JPanel();
	private final JPanel couche2 = new JPanel();
	private final JPanel couche3 = new JPanel();

	public EditorFrame(Map map)
	{
		this.map = map;
		this.setSize(600, 600);
		this.setPreferredSize(getSize());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridBagLayout());
		this.setContentPane(content);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		
		tabs.addTab("Carte", new JPanel());
		tabs.addTab("\u00c9vennements", tabEvents);
		tabs.addTab("Collisions", tabColl);
		
		content.add(tabs, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 3;
		
		content.add(mapPanel);
		
		resources.addTab("1", couche1);
		resources.addTab("2", couche2);
		resources.addTab("3", couche3);
		
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		
		content.add(resources);
	}
}
