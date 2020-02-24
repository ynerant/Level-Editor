package fr.ynerant.leveleditor.api.editor.sprites;

import java.util.List;

public class Category {
    private List<Sprite> sprites;
    private String name;
    private int index;

    private Category() {
    }

    public static Category create(String name, int index, List<Sprite> sprites) {
        Category c = new Category();

        c.name = name;
        c.index = index;
        c.sprites = sprites;

        return c;
    }

    public String getName() {
        return name;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public int getIndex() {
        return index;
    }
}
