package fr.ynerant.leveleditor.api.editor;

import fr.ynerant.leveleditor.api.editor.sprites.Sprite;

public class RawSprite {
    public static transient final RawSprite BLANK = new RawSprite();
    private String category = "blank";
    private int index = 0;

    public static RawSprite create(Sprite spr) {
        RawSprite raw = new RawSprite();
        raw.category = spr.getCategory().getName();
        raw.index = spr.getIndex();
        return raw;
    }

    public String getCategory() {
        return category;
    }

    public int getIndex() {
        return index;
    }
}
