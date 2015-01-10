package galaxyoyo.unknown.editor;

import galaxyoyo.unknown.api.editor.sprites.Category;
import galaxyoyo.unknown.api.editor.sprites.Sprite;
import galaxyoyo.unknown.api.editor.sprites.SpriteRegister;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorFrame extends JFrame implements ComponentListener, MouseListener, ChangeListener
{
	private static final long serialVersionUID = -2705122356101556462L;
	
	private final Map map;
	
	private final JPanel content = new JPanel();
	
	private final JTabbedPane tabs = new JTabbedPane();
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
		content.setLayout(new BorderLayout());
		this.setContentPane(content);
		this.addComponentListener(this);
		this.setVisible(true);
		this.setVisible(false);
		
		tabs.addTab("Carte", mapPanel);
		tabs.addTab("\u00c9vennments", tabEvents);
		tabs.addTab("Collisions", tabColl);
		tabs.addMouseListener(this);
		tabs.addChangeListener(this);
		
		content.add(tabs, BorderLayout.CENTER);
		
		couche1.setLayout(new WrapLayout(WrapLayout.LEFT));
		couche2.setLayout(new WrapLayout(WrapLayout.LEFT));
		couche3.setLayout(new WrapLayout(WrapLayout.LEFT));
		
		JScrollPane scroll1 = new JScrollPane(couche1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane scroll2 = new JScrollPane(couche2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane scroll3 = new JScrollPane(couche3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroll1.getHorizontalScrollBar().setMaximum(0);
		scroll2.getHorizontalScrollBar().setMaximum(0);
		scroll3.getHorizontalScrollBar().setMaximum(0);
		
		resources.addTab("", new ImageIcon(getClass().getResource("/assets/unknown/textures/layer 1.png")), scroll1);
		resources.addTab("", new ImageIcon(getClass().getResource("/assets/unknown/textures/layer 2.png")), scroll2);
		resources.addTab("", new ImageIcon(getClass().getResource("/assets/unknown/textures/layer 3.png")), scroll3);
		resources.addMouseListener(this);
		resources.addChangeListener(this);
		
		content.add(resources, BorderLayout.EAST);
		
		this.componentResized(null);
		resize();
		
		drawResources();
		
		drawMap();
	}

	private void drawMap()
	{
		if (mapPanel.getGraphics() == null)
		{
			mapPanel.repaint();
		}
		
		mapPanel.getGraphics().drawImage(map.getFont(), 0, 0, null);
		mapPanel.revalidate();
		mapPanel.repaint();
		repaint();
	}

	private void drawResources()
	{
		int x = 0;
		int y = 0;
		
		couche1.removeAll();
		couche2.removeAll();
		couche3.removeAll();
		
		if (couche1.getComponents().length > 0)
		{
			return;
		}
		
		if (couche1.getWidth() == 0 || couche2.getWidth()  == 0 || couche3.getWidth() == 0)
		{
			couche1.repaint();
			couche2.repaint();
			couche3.repaint();
		}
		
		for (Category cat : SpriteRegister.getAllCategories())
		{
			for (Sprite spr : cat.getSprites())
			{
				SpriteComp sprc1 = new SpriteComp(spr, 1);
				SpriteComp sprc2 = new SpriteComp(spr, 2);
				SpriteComp sprc3 = new SpriteComp(spr, 3);
				sprc1.setLocation(x, y);
				sprc2.setLocation(x, y);
				sprc3.setLocation(x, y);
				couche1.add(sprc1);
				couche2.add(sprc2);
				couche3.add(sprc3);
				
				x += 48;
				if (couche1.getWidth() - x < 48)
				{
					x = 0;
					y += 48;
				}
			}
		}
		
		couche1.revalidate();
		couche2.revalidate();
		couche3.revalidate();
		couche1.repaint();
		couche2.repaint();
		couche3.repaint();
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
	}
	
	public void resize()
	{

		int cursorPos = ((JScrollPane) resources.getSelectedComponent()).getVerticalScrollBar().getValue();
		tabs.setPreferredSize(new Dimension(getWidth(), getHeight() / 5));
		tabs.setLocation(0, 0);
		mapPanel.setPreferredSize(new Dimension(getWidth() / 4 * 3, getHeight() / 5 * 4));
		mapPanel.setLocation(0, getHeight() / 5);
		resources.setPreferredSize(new Dimension(getWidth() / 4 - 15, getHeight() / 5 * 4 - 40));
		resources.setLocation(getWidth() / 4 * 3, getHeight() / 5);
		
		JScrollPane scroll1 = (JScrollPane) resources.getComponent(0);
		JScrollPane scroll2 = (JScrollPane) resources.getComponent(1);
		JScrollPane scroll3 = (JScrollPane) resources.getComponent(2);
		
		scroll1.getHorizontalScrollBar().setMaximum(0);
		scroll2.getHorizontalScrollBar().setMaximum(0);
		scroll3.getHorizontalScrollBar().setMaximum(0);
		
		drawResources();
		
		((JScrollPane) resources.getSelectedComponent()).getVerticalScrollBar().setValue(cursorPos);
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
