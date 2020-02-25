package fr.ynerant.leveleditor.api.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.ynerant.leveleditor.editor.Map;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class EditorAPI {
    private static File LAST_FILE;

    public static RawMap toRawMap(int width, int height) {
        List<RawCase> cases = new ArrayList<>();

        for (int y = 1; y < height; y += 16) {
            for (int x = 1; x < width; x += 16) {
                RawCase c = RawCase.create(x / 16, y / 16, RawSprite.BLANK, RawSprite.BLANK, RawSprite.BLANK, Collision.ANY);
                cases.add(c);
            }
        }

        return RawMap.create(cases, width, height);
    }

    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();

        builder.enableComplexMapKeySerialization();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        return builder.create();
    }

    public static JFileChooser createJFC() {
        JFileChooser jfc = new JFileChooser();

        jfc.setFileFilter(new FileNameExtensionFilter("Fichiers monde (*.gmap, *.dat)", "gmap", "dat"));
        jfc.setFileHidingEnabled(true);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File dir = new File("maps");
        assert dir.mkdirs();
        jfc.setCurrentDirectory(dir);

        return jfc;
    }

    public static void saveAs(RawMap map) {
        JFileChooser jfc = createJFC();
        File file;
        jfc.showSaveDialog(null);
        file = jfc.getSelectedFile();

        if (file == null)
            return;

        if (!file.getName().toLowerCase().endsWith(".gmap") && !file.getName().toLowerCase().endsWith(".dat")) {
            file = new File(file.getParentFile(), file.getName() + ".gmap");
        }

        LAST_FILE = file;

        save(file, map);
    }

    public static void save(RawMap map) {
        if (LAST_FILE != null)
            save(LAST_FILE, map);
        else
            saveAs(map);
    }

    public static void save(File file, RawMap map) {
        String json = createGson().toJson(map);

        try {
            assert file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            bos.write(json.getBytes(StandardCharsets.UTF_8));

            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Map open() {
        JFileChooser jfc = createJFC();
        File file;

        jfc.showOpenDialog(null);
        file = jfc.getSelectedFile();

        if (file == null)
            return null;

        LAST_FILE = file;

        return open(file);
    }

    public static RawMap getRawMap(File f) {
        String json = null;
        try {
            GZIPInputStream gis = new GZIPInputStream(new BufferedInputStream(new FileInputStream(f)));
            byte[] bytes = new byte[512 * 1024];
            int count;
            StringBuilder text = new StringBuilder();
            while ((count = gis.read(bytes)) != -1) {
                text.append(new String(bytes, 0, count, StandardCharsets.UTF_8));
            }
            gis.close();

            json = text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createGson().fromJson(json, RawMap.class);
    }

    public static Map open(File f) {
        return open(getRawMap(f));
    }

    public static Map open(RawMap map) {
        if (map.getFont() == null) {
            int baseWidth = map.getWidth();
            int baseHeight = map.getHeight();
            int width = baseWidth + (baseWidth / 16) + 1;
            int height = baseHeight + (baseHeight / 16) + 1;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.black);
            g.drawLine(0, 0, width, 0);
            g.drawLine(0, 0, 0, height);
            for (int x = 17; x <= width; x += 17) {
                g.drawLine(x, 0, x, height);
            }

            for (int y = 17; y <= height; y += 17) {
                g.drawLine(0, y, width, y);
            }

            map.setFont(image);
        }

        return new Map(map);
    }
}
