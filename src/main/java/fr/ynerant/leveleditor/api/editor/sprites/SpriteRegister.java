package fr.ynerant.leveleditor.api.editor.sprites;

import com.google.gson.Gson;
import fr.ynerant.leveleditor.client.main.Main;
import org.apache.logging.log4j.LogManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SpriteRegister {
    private static Map<String, List<List<Double>>> nameToCoords;
    private static final Map<String, Category> sprites = new HashMap<>();

    public static void unpack() throws IOException {
        if (Main.isInDevelopmentMode()) {
            try {
                File dir = new File(SpriteRegister.class.getResource("/assets").toURI()).getParentFile();
                unpackDir(dir);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else {
            @SuppressWarnings("deprecation")
            String path = URLDecoder.decode(SpriteRegister.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File jarFile = new File(path);

            if (jarFile.isFile()) {
                JarFile jar = new JarFile(jarFile);
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry je = entries.nextElement();
                    String name = je.getName();
                    if (name.startsWith("assets/")) {
                        File f = new File(name);
                        if (name.endsWith("/")) {
                            if (!f.mkdirs() && !f.isDirectory())
                                throw new IOException("Unable to make dir: " + f);
                        }
                        else if (!f.isFile())
                            Files.copy(jar.getInputStream(je), Paths.get(f.toURI()));
                    }
                }
                jar.close();
            }
        }
    }

    private static void unpackDir(File dir) throws IOException {
        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.isDirectory()) {
                unpackDir(f);
                continue;
            }

            String path = f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(File.separatorChar + "assets") + 1);
            File local = new File(path);
            assert local.getParentFile().mkdirs();
            assert !local.exists() || local.delete();
            Files.copy(Paths.get(f.toURI()), Paths.get(local.toURI()));
        }
    }

    @SuppressWarnings("unchecked")
    public static void refreshAllSprites() {
        if (nameToCoords != null && !nameToCoords.isEmpty() && !sprites.isEmpty()) {
            return;
        }

        File assetsDir = new File("assets");
        List<String> assets = new ArrayList<>();

        for (File dir : Objects.requireNonNull(assetsDir.listFiles())) {
            assets.add(dir.getName());
        }

        for (String asset : assets) {
            try {
                File f = new File(assetsDir.getAbsolutePath() + "/" + asset + "/textures/sprites");
                assert f.mkdirs();
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(f, "sprites.json"))));
                nameToCoords = new Gson().fromJson(br, Map.class);
                br.close();

                for (String key : nameToCoords.keySet()) {
                    try {
                        BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(f, key + ".png")));
                        BufferedImage img = ImageIO.read(is);
                        Category cat = Category.create(key, new ArrayList<>());

                        for (List<Double> list : nameToCoords.get(key)) {
                            int x = list.get(0).intValue();
                            int y = list.get(1).intValue();
                            BufferedImage child = img.getSubimage(x, y, 16, 16);
                            new Sprite(child, cat, nameToCoords.get(key).indexOf(list));
                        }

                        sprites.put(key, cat);
                    } catch (Throwable t) {
                        LogManager.getLogger("SpriteRegister").fatal("Erreur lors de la lecture du sprite '" + key + "'", t);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Category getCategory(String name) {
        return sprites.get(name);
    }

    public static List<Category> getAllCategories() {
        return new ArrayList<>(sprites.values());
    }

}
