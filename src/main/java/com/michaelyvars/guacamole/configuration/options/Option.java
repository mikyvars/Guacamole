package com.michaelyvars.guacamole.configuration.options;

import de.leonhard.storage.Json;

public interface Option<T> {
    T getValue(Json configuration);
}
