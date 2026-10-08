package com.threecolumnsstudio.dontbreakmyendereyes.neoforge;

import com.threecolumnsstudio.dontbreakmyendereyes.DBMEEConstants;
import com.threecolumnsstudio.dontbreakmyendereyes.client.screen.DBMEEConfigScreen;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class DBMEENeoForgeClient {

    private DBMEENeoForgeClient() {}

    public static void registerConfigScreen() {
        ModList.get().getModContainerById(DBMEEConstants.MOD_ID).ifPresent(container ->
            container.registerExtensionPoint(IConfigScreenFactory.class,
                (modContainer, parent) -> new DBMEEConfigScreen(parent)));
    }
}
