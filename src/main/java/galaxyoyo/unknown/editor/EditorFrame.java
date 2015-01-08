package galaxyoyo.unknown.editor;

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
	private final ResourcePanel couche1 = new ResourcePanel();
	private final ResourcePanel couche2 = new ResourcePanel();
	private final ResourcePanel couche3 = new ResourcePanel();

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
		this.setVisible(true);
		this.setVisible(false);
		
		tabs.addTab("Carte", new JPanel());
		tabs.addTab("\u00c9vennments", tabEvents);
		tabs.addTab("Collisions", tabColl);
		tabs.addMouseListener(this);
		tabs.addChangeListener(this);
		
		content.add(tabs);
		
		content.add(mapPanel);
		
		JScrollPane scroll1 = new JScrollPane(couche1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane scroll2 = new JScrollPane(couche2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane scroll3 = new JScrollPane(couche3, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroll1.getHorizontalScrollBar().setMaximum(0);
		scroll2.getHorizontalScrollBar().setMaximum(0);
		scroll3.getHorizontalScrollBar().setMaximum(0);
		
		resources.addTab("1", scroll1);
		resources.addTab("2", scroll2);
		resources.addTab("3", scroll3);
		resources.addMouseListener(this);
		resources.addChangeListener(this);
		
		content.add(resources);
		
		this.componentResized(null);
		
		for (int i = 0; i < 3; ++i)
		{
			ResourcePanel rp = (ResourcePanel) ((JScrollPane) resources.getComponentAt(i)).getViewport().getComponent(0);
			rp.repaint();
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
