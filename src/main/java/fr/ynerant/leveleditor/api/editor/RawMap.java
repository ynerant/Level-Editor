package fr.ynerant.leveleditor.api.editor;

import fr.ynerant.leveleditor.editor.Map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RawMap {
    private List<RawCase> cases;
    private int width;
    private int height;
    private transient BufferedImage font;

    public static RawMap create(List<RawCase> cases, int width, int height) {
        RawMap rm = new RawMap();
        rm.cases = cases;
        rm.width = width;
        rm.height = height;
        return rm;
    }

    public static RawMap create(Map map) {
        RawMap raw = new RawMap();
        raw.width = map.getWidth();
        raw.height = map.getHeight();
        raw.cases = new ArrayList<>();
        for (Case c : map.getAllCases()) {
            RawCase rc = RawCase.create(c);
            raw.cases.add(rc);
        }
        return raw;
    }

    public List<RawCase> getCases() {
        return cases;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getFont() {
        return font;
    }

    public void setFont(BufferedImage font) {
        this.font = font;
    }
}
