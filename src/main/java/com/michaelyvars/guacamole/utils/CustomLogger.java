package com.michaelyvars.guacamole.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {

    private final Logger logger;

    public CustomLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }

    public void logDebug(String logging) {
        logger.debug("\u001B[34m" + logging + "\u001B[0m");
    }
    public void logInfo(String logging) {
        logger.info("\u001B[32m" + logging + "\u001B[0m");
    }

    public void logError(String logging) {
        logger.error("\u001B[31m" + logging + "\u001B[0m");
    }
}
