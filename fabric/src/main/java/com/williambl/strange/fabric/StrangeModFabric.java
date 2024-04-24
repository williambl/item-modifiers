package com.williambl.strange.fabric;

import com.williambl.strange.StrangeMod;
import net.fabricmc.api.ModInitializer;

public class StrangeModFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        StrangeMod.init();
    }
}
