package com.threecolumnsstudio.dontbreakmyendereyes.neoforge;

import com.threecolumnsstudio.dontbreakmyendereyes.DBMEEConstants;
import com.threecolumnsstudio.dontbreakmyendereyes.config.DBMEEConfig;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;

@Mod(DBMEEConstants.MOD_ID)
public final class DBMEENeoForge {
    public DBMEENeoForge() {
        DBMEEConfig.load(FMLPaths.CONFIGDIR.get());
    }
}
