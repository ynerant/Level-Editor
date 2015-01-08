package galaxyoyo.unknown.api.editor;

import galaxyoyo.unknown.editor.Editor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorAPI
{
	public static Byte[] toBytes(int width, int height)
	{
		List<Byte> bytes = new ArrayList<Byte>();
		
		for (int y = 1; y < height; y += 16)
		{
			for (int x = 1; x < width; x += 16)
			{
				bytes.add((byte) 0);
				bytes.add((byte) 0);
				bytes.add((byte) (Byte.MIN_VALUE / 2));
			}
			bytes.remove(bytes.lastIndexOf((byte) (Byte.MIN_VALUE / 2)));
			bytes.add(Byte.MIN_VALUE);
		}
		
		bytes.remove(bytes.lastIndexOf(Byte.MIN_VALUE));
		
		return bytes.toArray(new Byte[0]);
	}
	
	public static JFileChooser createJFC()
	{
		JFileChooser jfc = new JFileChooser();
		
		jfc.setFileFilter(new FileNameExtensionFilter("Fichiers monde (*.gworld, *.dat)", "gworld", "dat"));
		jfc.setFileHidingEnabled(true);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		File dir = new File("maps");
		dir.mkdirs();
		jfc.setCurrentDirectory(dir);
		
		return jfc;
	}

	public static void saveAs(byte ... bytes)
	{		
		JFileChooser jfc = createJFC();
		File file = null;
		while (file == null)
		{
			jfc.showSaveDialog(null);
			file = jfc.getSelectedFile();
		}
		
		if (!file.getName().toLowerCase().endsWith(".gworld") && !file.getName().toLowerCase().endsWith(".dat"))
		{
			file = new File(file.getParentFile(), file.getName() + ".gworld");
		}
		
		save(file, bytes);
	}
	
	public static void save(File file, byte ... bytes)
	{
		try
		{
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(bytes);
			bos.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static Editor open()
	{
		JFileChooser jfc = createJFC();
		File file = null;
		
		while (file == null)
		{
			jfc.showOpenDialog(null);
			file = jfc.getSelectedFile();
			System.out.println(file);
		}
		
		return open(file);
	}
	
	public static Editor open(File f)
	{
		byte[] bytes = null;
		try
		{
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			bytes = new byte[(int) f.length()];
			while (bis.read(bytes) != -1);
			for (byte b : bytes)
			{
				System.err.println(b);
			}
			bis.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return open(bytes);
	}

	public static Editor open(byte[] bytes)
	{
		return new Editor(bytes);
	}
}
