package fr.ynerant.leveleditor.api.editor.sprites;

import java.util.List;

public class Category {
    private List<Sprite> sprites;
    private String name;

    private Category() {
    }

    public static Category create(String name, List<Sprite> sprites) {
        Category c = new Category();

        c.name = name;
        c.sprites = sprites;

        return c;
    }

    public String getName() {
        return name;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

}
