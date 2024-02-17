package com.michaelyvars.guacamole.configuration.options;

import de.leonhard.storage.Json;
import org.jetbrains.annotations.NotNull;

public record OptionBasic<T>(String path, String[] description, T def) implements Option<T> {

    public OptionBasic(@NotNull String path, @NotNull String[] description, @NotNull T def) {
        this.path = path;
        this.description = description;
        this.def = def;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getValue(Json configuration) {
        if (def instanceof String) {
            return (T) configuration.getOrSetDefault(path, (String) def);
        } else if (def instanceof Integer) {
            return (T) configuration.getOrSetDefault(path, (Integer) def);
        } else if (def instanceof Double) {
            return (T) configuration.getOrSetDefault(path, (Double) def);
        } else if (def instanceof Long) {
            return (T) configuration.getOrSetDefault(path, (Long) def);
        } else if (def instanceof Boolean) {
            return (T) configuration.getOrSetDefault(path, (Boolean) def);
        } else {
            return configuration.get(path, def);
        }
    }
}
