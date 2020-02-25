package fr.ynerant.leveleditor.api.editor;

import fr.ynerant.leveleditor.editor.Map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RawMap {
    private List<RawCase> cases;
    private java.util.Map<Integer, RawCase> cases_map;
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

    public RawCase getCase(int x, int y) {
        if (cases_map == null) {
            cases_map = new HashMap<>();
            for (RawCase c : getCases())
                cases_map.put(c.getPosY() * width + c.getPosX(), c);
        }

        return cases_map.get(y * getWidth() + x);
    }

    public Collection<RawCase> getNeighbours(RawCase c) {
        List<RawCase> list = new ArrayList<>();
        list.add(getCase(c.getPosX() - 1, c.getPosY()));
        list.add(getCase(c.getPosX(), c.getPosY() - 1));
        list.add(getCase(c.getPosX() + 1, c.getPosY()));
        list.add(getCase(c.getPosX(), c.getPosY() + 1));
        return list.stream().filter(_c -> _c != null && _c.getCollision() == Collision.ANY).collect(Collectors.toList());
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
