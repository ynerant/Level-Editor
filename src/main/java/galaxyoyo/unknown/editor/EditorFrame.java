package galaxyoyo.unknown.editor;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EditorFrame extends JFrame implements ComponentListener
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
		this.setContentPane(content);
		this.addComponentListener(this);
		
		revalidate();
		
		tabs.addTab("Carte", new JPanel());
		tabs.addTab("\u00c9vennements", tabEvents);
		tabs.addTab("Collisions", tabColl);
		
		content.add(tabs);
		
		content.add(mapPanel);
		
		resources.addTab("1", couche1);
		resources.addTab("2", couche2);
		resources.addTab("3", couche3);
		
		content.add(resources);
		
		revalidate();
		repaint();
	}

	@Override
	public void componentHidden(ComponentEvent event)
	{
	}

	@Override
	public void componentMoved(ComponentEvent event)
	{
		componentShown(event);
	}

	@Override
	public void componentResized(ComponentEvent event)
	{
		componentShown(event);
	}

	@Override
	public void componentShown(ComponentEvent event)
	{
		revalidate();
	}
	
	@Override
	public void revalidate()
	{
		tabs.setBounds(0, 0, getWidth(), getHeight() / 5);
		mapPanel.setBounds(0, getWidth() / 5, getWidth() / 4 * 3, getHeight() / 5 * 4);
		resources.setBounds(getWidth() / 4 * 3, getHeight() / 5, getWidth() / 4, getHeight() / 5 * 4);
		
		tabs.revalidate();
		mapPanel.revalidate();
		resources.revalidate();
		super.revalidate();
	}
}
