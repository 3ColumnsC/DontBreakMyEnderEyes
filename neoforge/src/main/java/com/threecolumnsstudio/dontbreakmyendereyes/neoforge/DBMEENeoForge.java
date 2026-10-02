package com.threecolumnsstudio.dontbreakmyendereyes.neoforge;

import com.threecolumnsstudio.dontbreakmyendereyes.DBMEEConstants;
import com.threecolumnsstudio.dontbreakmyendereyes.client.screen.DBMEEConfigScreen;
import com.threecolumnsstudio.dontbreakmyendereyes.config.DBMEEConfig;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(DBMEEConstants.MOD_ID)
public final class DBMEENeoForge {
    public DBMEENeoForge() {
        DBMEEConfig.load(FMLPaths.CONFIGDIR.get());
        if (FMLEnvironment.dist.isClient()) {
            registerConfigScreen();
        }
    }

    private void registerConfigScreen() {
        ModList.get().getModContainerById(DBMEEConstants.MOD_ID).ifPresent(container ->
            container.registerExtensionPoint(IConfigScreenFactory.class,
                (modContainer, parent) -> new DBMEEConfigScreen(parent)));
    }
}
