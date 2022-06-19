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

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("%s%n", title));
        for (Map.Entry<String, String> entry : info.entrySet())
            sb.append(String.format("  %s: %s%n", entry.getKey(), entry.getValue()));
        return sb.toString();
    }
}
