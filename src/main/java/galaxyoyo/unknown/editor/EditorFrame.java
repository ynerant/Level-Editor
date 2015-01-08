package galaxyoyo.unknown.editor;

import galaxyoyo.unknown.api.editor.sprites.Category;
import galaxyoyo.unknown.api.editor.sprites.SpriteRegister;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorFrame extends JFrame implements ComponentListener, MouseListener, ChangeListener
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
		repaint();
		
		tabs.addTab("Carte", new JPanel());
		tabs.addTab("\u00c9vennements", tabEvents);
		tabs.addTab("Collisions", tabColl);
		tabs.addMouseListener(this);
		tabs.addChangeListener(this);
		
		content.add(tabs);
		
		content.add(mapPanel);
		
		resources.addTab("1", new JScrollPane(couche1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		resources.addTab("2", new JScrollPane(couche2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		resources.addTab("3", new JScrollPane(couche3, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		resources.addMouseListener(this);
		resources.addChangeListener(this);
		
		content.add(resources);
		
		this.componentResized(null);
		
		drawResources();
	}

	private void drawResources()
	{
		JScrollPane scroll1 = (JScrollPane) resources.getComponentAt(1);
		JScrollPane scroll2 = (JScrollPane) resources.getComponentAt(2);
		JScrollPane scroll3 = (JScrollPane) resources.getComponentAt(3);
		
		scroll1.getHorizontalScrollBar().setMaximum(0);
		scroll2.getHorizontalScrollBar().setMaximum(0);
		scroll3.getHorizontalScrollBar().setMaximum(0);
		
		couche1.setBackground(Color.white);
		couche2.setBackground(Color.white);
		couche3.setBackground(Color.white);
		
		for (Category cat : SpriteRegister.getAllCategories())
		{
			
		}
	}

	@Override
	public void componentHidden(ComponentEvent paramComponentEvent)
	{
	}

	@Override
	public void componentMoved(ComponentEvent paramComponentEvent)
	{
	}

	@Override
	public void componentResized(ComponentEvent paramComponentEvent)
	{
			tabs.setPreferredSize(new Dimension(getWidth(), getHeight() / 5));
			tabs.setLocation(0, 0);
			mapPanel.setPreferredSize(new Dimension(getWidth() / 4 * 3, getHeight() / 5 * 4));
			mapPanel.setLocation(0, getHeight() / 5);
			resources.setPreferredSize(new Dimension(getWidth() / 4, getHeight() / 5 * 4));
			resources.setLocation(getWidth() / 4 * 3, getHeight() / 5);
	}

	@Override
	public void componentShown(ComponentEvent paramComponentEvent)
	{
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent)
	{
		this.componentResized(null);
	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent)
	{
		this.componentResized(null);
	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent)
	{
		this.componentResized(null);
	}

	@Override
	public void mousePressed(MouseEvent paramMouseEvent)
	{
		this.componentResized(null);
	}

	@Override
	public void mouseReleased(MouseEvent paramMouseEvent)
	{
		this.componentResized(null);
	}

	@Override
	public void stateChanged(ChangeEvent paramChangeEvent)
	{
		this.componentResized(null);
	}
}
