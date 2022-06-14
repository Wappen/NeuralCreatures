package me.wappen.neuralcreatures.debug;

import java.util.LinkedHashMap;
import java.util.Map;

public class Debugger {
    private final Map<String, String> info;
    private String title = "TITLE";

    public Debugger() {
        this.info = new LinkedHashMap<>();
    }

    public void setInfo(String name, Object value) {
        info.put(name, value.toString());
    }

    public Map<String, String> getAllInfo() {
        return info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
