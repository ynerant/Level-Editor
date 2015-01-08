package galaxyoyo.unknown.api.editor;

import galaxyoyo.unknown.editor.Map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EditorAPI
{
	public static RawMap toRawMap(int width, int height)
	{
		List<RawCase> cases = new ArrayList<RawCase>();
		
		for (int y = 1; y < height; y += 16)
		{
			for (int x = 1; x < width; x += 16)
			{
				RawCase c = RawCase.create(x / 16, y / 16, RawSprite.BLANK, RawSprite.BLANK, RawSprite.BLANK);
				cases.add(c);
			}
		}
		
		return RawMap.create(cases);
	}
	
	private static Gson createGson()
	{
		GsonBuilder builder = new GsonBuilder();
		
		builder.enableComplexMapKeySerialization();
		builder.serializeNulls();
		builder.setPrettyPrinting();
		
		return builder.create();
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

	public static void saveAs(RawMap map)
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
		
		save(file, map);
	}
	
	public static void save(File file, RawMap map)
	{
		String json = createGson().toJson(map);
		String crypted = "";
		
		for (char c : json.toCharArray())
		{
			char ch = c;
			
			if (c != '\n')
			{
				ch = (char) (c * 2);
			}
			
			crypted += ch;
		}
		
		try
		{
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(crypted);
			bw.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static Map open()
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
	
	public static Map open(File f)
	{
		String json = null;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(f));
			String text = "";
			String ln;
			while ((ln = br.readLine()) != null)
			{
				text += ln + "\n";
			}
			br.close();
			
			json = "";
			
			for (char c : text.toCharArray())
			{
				char ch = c;
				
				if (c != '\n')
				{
					ch = (char) (c / 2);
				}
				
				json += ch;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		RawMap rm = createGson().fromJson(json, RawMap.class);
		
		return open(rm);
	}

	public static Map open(RawMap map)
	{
		return new Map(map);
	}
}
