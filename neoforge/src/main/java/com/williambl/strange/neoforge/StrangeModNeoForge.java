package com.williambl.strange.neoforge;


import com.williambl.strange.StrangeMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(StrangeMod.MOD_ID)
public class StrangeModNeoForge {

    public StrangeModNeoForge(IEventBus eventBus) {
        StrangeMod.init();
    }
}