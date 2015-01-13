package galaxyoyo.unknown.editor;

import galaxyoyo.unknown.api.editor.EditorAPI;
import galaxyoyo.unknown.api.editor.RawMap;
import galaxyoyo.unknown.api.editor.sprites.Category;
import galaxyoyo.unknown.api.editor.sprites.Sprite;
import galaxyoyo.unknown.api.editor.sprites.SpriteRegister;
import galaxyoyo.unknown.frame.listeners.CollidMapMouseListener;
import galaxyoyo.unknown.frame.listeners.CreateMapListener;
import galaxyoyo.unknown.frame.listeners.MapMouseListener;
import galaxyoyo.unknown.frame.listeners.OpenMapListener;
import galaxyoyo.unknown.frame.listeners.SpriteMouseListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorFrame extends JFrame implements ChangeListener, ActionListener, WindowListener
{
	private static final long serialVersionUID = -2705122356101556462L;
	
	private final Map map;
	
	private final JPanel content = new JPanel();
	
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fichier = new JMenu("Fichier");
	private final JMenu tools = new JMenu("Outils");
	private final JMenuItem nouveau = new JMenuItem("Nouveau");
	private final JMenuItem open = new JMenuItem("Ouvrir");
	private final JMenuItem save = new JMenuItem("Sauvegarder");
	private final JMenuItem saveAs = new JMenuItem("Sauvegarder sous ...");
	private final JMenuItem exit = new JMenuItem("Quitter");
	private final JMenu selectionMode = new JMenu("Mode de s\u00e9lection");
	ButtonGroup group = new ButtonGroup();
	private final JRadioButtonMenuItem pen = new JRadioButtonMenuItem("Pinceau");
	private final JRadioButtonMenuItem pot = new JRadioButtonMenuItem("Pot de peinture");
	private final JTabbedPane tabs = new JTabbedPane();
	private final JPanel tabEvents = new JPanel();
	private final CollidPanel tabColl;
	private final MapPanel mapPanel;
	private final JTabbedPane resources = new JTabbedPane();
	private final JPanel couche1 = new JPanel();
	private final JPanel couche2 = new JPanel();
	private final JPanel couche3 = new JPanel();
	private SpriteComp selectedSprite;

	public EditorFrame(Map map)
	{
		this.map = map;
		this.setSize(600, 600);
		this.setPreferredSize(getSize());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
		content.setLayout(new BorderLayout());
		this.setContentPane(content);
		this.setVisible(true);
		this.setVisible(false);
		
		fichier.setMnemonic(KeyEvent.VK_F + KeyEvent.ALT_DOWN_MASK);
		
		nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK, true));
		nouveau.addActionListener(new CreateMapListener());
		fichier.add(nouveau);
		
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK, true));
		open.addActionListener(new OpenMapListener());
		fichier.add(open);
		
		fichier.addSeparator();
		
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK, true));
		save.addActionListener(this);
		fichier.add(save);
		
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK, true));
		saveAs.addActionListener(this);
		fichier.add(saveAs);
		
		fichier.addSeparator();
		
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK, true));
		exit.addActionListener(this);
		fichier.add(exit);
		
		menuBar.add(fichier);
		
		pen.setSelected(true);
		pen.addActionListener(this);
		pot.addActionListener(this);
		group.add(pen);
		group.add(pot);
		selectionMode.add(pen);
		selectionMode.add(pot);

		tools.setMnemonic(KeyEvent.VK_O + KeyEvent.ALT_DOWN_MASK);
		
		tools.add(selectionMode);
		
		menuBar.add(tools);
		
		this.setJMenuBar(menuBar);
		
		mapPanel = new MapPanel(this);
		mapPanel.addMouseListener(new MapMouseListener(mapPanel, this));
		mapPanel.addMouseMotionListener(new MapMouseListener(mapPanel, this));
		
		tabColl = new CollidPanel(this);
		tabColl.addMouseListener(new CollidMapMouseListener(tabColl, this));
		tabColl.addMouseMotionListener(new CollidMapMouseListener(tabColl, this));
		
		JScrollPane scrollMap = new JScrollPane(mapPanel);
		scrollMap.getHorizontalScrollBar().setUnitIncrement(34);
		scrollMap.getVerticalScrollBar().setUnitIncrement(34);
		JScrollPane scrollCollidMap = new JScrollPane(tabColl);
		scrollCollidMap.getHorizontalScrollBar().setUnitIncrement(34);
		scrollCollidMap.getVerticalScrollBar().setUnitIncrement(34);
		
		tabs.addTab("Carte", scrollMap);
		tabs.addTab("\u00c9vennments", new JScrollPane(tabEvents));
		tabs.addTab("Collisions", scrollCollidMap);
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
		
		resources.addTab("", new ImageIcon(new File("assets/unknown/textures/layer 1.png").getAbsolutePath()), scroll1);
		resources.addTab("", new ImageIcon(new File("assets/unknown/textures/layer 2.png").getAbsolutePath()), scroll2);
		resources.addTab("", new ImageIcon(new File("assets/unknown/textures/layer 3.png").getAbsolutePath()), scroll3);
		resources.addChangeListener(this);
		resources.setBackgroundAt(0, Color.white);
		resources.setBackgroundAt(1, Color.white);
		resources.setBackgroundAt(2, Color.white);
		
		content.add(resources, BorderLayout.EAST);
		
		resize();
		
		drawResources();
		
		revalidate();
		repaint();
	}

	private void drawResources()
	{
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
				SpriteComp sprc1 = new SpriteComp(spr, 0);
				SpriteComp sprc2 = new SpriteComp(spr, 1);
				SpriteComp sprc3 = new SpriteComp(spr, 2);
				sprc1.addMouseListener(new SpriteMouseListener(sprc1, this));
				sprc2.addMouseListener(new SpriteMouseListener(sprc2, this));
				sprc3.addMouseListener(new SpriteMouseListener(sprc3, this));
				couche1.add(sprc1);
				couche2.add(sprc2);
				couche3.add(sprc3);
			}
		}
		
		couche1.revalidate();
		couche2.revalidate();
		couche3.revalidate();
		couche1.repaint();
		couche2.repaint();
		couche3.repaint();
	}
	
	public void resize()
	{

		int cursorPos = ((JScrollPane) resources.getSelectedComponent()).getVerticalScrollBar().getValue();
		tabs.setPreferredSize(new Dimension(getWidth(), getHeight() / 5));
		tabs.setLocation(0, 0);
		BufferedImage img = getMap().getFont();
		int width = img.getWidth() * 2;
		int height = img.getHeight() * 2;
		mapPanel.setPreferredSize(new Dimension(width, height));
		mapPanel.setLocation(0, getHeight() / 5);
		tabColl.setPreferredSize(new Dimension(width, height));
		tabColl.setLocation(0, getHeight() / 5);
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

	public Map getMap()
	{
		return map;
	}

	public SpriteComp getSelectedSprite()
	{
		return selectedSprite;
	}

	public void setSelectedSprite(SpriteComp sprite)
	{
		this.selectedSprite = sprite;
	}

	@Override
	public void stateChanged(ChangeEvent event)
	{
		if (event.getSource() == resources)
		{
			if (getSelectedLayerIndex() == 0)
			{
				resources.setBackgroundAt(0, Color.white);
				resources.setBackgroundAt(1, Color.white);
				resources.setBackgroundAt(2, Color.white);
			}
			else if (getSelectedLayerIndex() == 1)
			{
				resources.setBackgroundAt(0, Color.black);
				resources.setBackgroundAt(1, Color.white);
				resources.setBackgroundAt(2, Color.white);
			}
			else if (getSelectedLayerIndex() == 2)
			{
				resources.setBackgroundAt(0, Color.black);
				resources.setBackgroundAt(1, Color.black);
				resources.setBackgroundAt(2, Color.white);
			}
			
			repaint();
		}
		else if (event.getSource() == tabs)
		{
			resources.setEnabled(tabs.getSelectedIndex() == 0);
			couche1.setEnabled(resources.isEnabled());
			couche2.setEnabled(resources.isEnabled());
			couche3.setEnabled(resources.isEnabled());
			
			repaint();
		}
	}

	public int getSelectedLayerIndex()
	{
		return resources.getSelectedIndex();
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == save)
		{
			EditorAPI.save(RawMap.create(map));
		}
		else if (event.getSource() == saveAs)
		{
			EditorAPI.saveAs(RawMap.create(map));
		}
		else if (event.getSource() == exit)
		{
			int result = JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder votre carte avant de quitter ? Toute modification sera perdue", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if (result == 0)
				save.doClick();
			
			if (result != 2)
				dispose();
		}
	}
	
	public int getSelectedPaintingMode()
	{
		return pen.isSelected() ? 0 : pot.isSelected() ? 1 : -1;
	}

	@Override
	public void windowActivated(WindowEvent event)
	{
	}

	@Override
	public void windowClosed(WindowEvent event)
	{
	}

	@Override
	public void windowClosing(WindowEvent event)
	{
		int result = JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder avant de quitter ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
		
		if (result == 0)
		{
			EditorAPI.save(RawMap.create(map));
		}
		
		if (result != 2)
		{
			dispose();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent event)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent event)
	{
	}

	@Override
	public void windowIconified(WindowEvent event)
	{
	}

	@Override
	public void windowOpened(WindowEvent event)
	{
	}
}
