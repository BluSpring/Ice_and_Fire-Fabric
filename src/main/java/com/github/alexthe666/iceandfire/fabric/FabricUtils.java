package com.github.alexthe666.iceandfire.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class FabricUtils {
    public static void runForEnv(Runnable client, Runnable server) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            client.run();
        else
            server.run();
    }
}
