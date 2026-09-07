package com.threecolumnsstudio.dontbreakmyendereyes.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.threecolumnsstudio.dontbreakmyendereyes.DBMEEConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public record DBMEEConfig(float shatterChance) {
    public static final float DEFAULT_SHATTER_CHANCE = 0.0F;
    public static final float MIN_SHATTER_CHANCE = 0.0F;
    public static final float MAX_SHATTER_CHANCE = 1.0F;

    private static final Logger LOGGER = LoggerFactory.getLogger(DBMEEConstants.MOD_NAME);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static volatile DBMEEConfig instance = new DBMEEConfig(DEFAULT_SHATTER_CHANCE);

    public DBMEEConfig {
        shatterChance = Math.clamp(shatterChance, MIN_SHATTER_CHANCE, MAX_SHATTER_CHANCE);
    }

    public static DBMEEConfig get() {
        return instance;
    }

    public static void load(Path configDir) {
        Path configFile = configDir.resolve(DBMEEConstants.CONFIG_FILE);
        DBMEEConfig loaded;
        if (Files.exists(configFile)) {
            loaded = read(configFile);
        } else {
            loaded = new DBMEEConfig(DEFAULT_SHATTER_CHANCE);
            write(configFile, loaded);
        }
        instance = loaded;
        LOGGER.info("Loaded config: shatterChance={}", loaded.shatterChance());
    }

    private static DBMEEConfig read(Path configFile) {
        try (Reader reader = Files.newBufferedReader(configFile)) {
            DBMEEConfig parsed = GSON.fromJson(reader, DBMEEConfig.class);
            return parsed != null ? parsed : new DBMEEConfig(DEFAULT_SHATTER_CHANCE);
        } catch (IOException | RuntimeException e) {
            LOGGER.warn("Could not read config {}, using defaults", configFile, e);
            return new DBMEEConfig(DEFAULT_SHATTER_CHANCE);
        }
    }

    private static void write(Path configFile, DBMEEConfig config) {
        try {
            Files.createDirectories(configFile.getParent());
            try (Writer writer = Files.newBufferedWriter(configFile)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException e) {
            LOGGER.warn("Could not write default config {}", configFile, e);
        }
    }
}
