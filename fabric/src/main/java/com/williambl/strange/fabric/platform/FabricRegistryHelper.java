package com.williambl.strange.fabric.platform;

import com.mojang.brigadier.CommandDispatcher;
import com.williambl.strange.platform.services.IRegistryHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T> Holder<T> register(Registry<? super T> registry, ResourceLocation name, Supplier<T> sup) {
        return (Holder<T>) Registry.registerForHolder(registry, name, sup.get());
    }

    @Override
    public <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryResourceKey) {
        return FabricRegistryBuilder.createSimple(registryResourceKey).buildAndRegister();
    }

    @Override
    public <T> void createDynamicRegistry(ResourceKey<Registry<T>> registryResourceKey) {
        //TODO
    }

    @Override
    public void registerCommand(BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext> consumer) {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> consumer.accept(dispatcher, registryAccess));
    }
}
