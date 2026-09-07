package com.threecolumnsstudio.dontbreakmyendereyes.fabric;

import com.threecolumnsstudio.dontbreakmyendereyes.config.DBMEEConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class DBMEEFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DBMEEConfig.load(FabricLoader.getInstance().getConfigDir());
    }
}
