package com.williambl.strange.platform.services;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface IRegistryHelper {
    <T> Holder<T> register(Registry<? super T> registry, ResourceLocation name, Supplier<T> sup);

    <T> Registry<T> createRegistry(ResourceKey<Registry<T>> registryResourceKey);
    <T> void createDynamicRegistry(ResourceKey<Registry<T>> registryResourceKey);

    void registerCommand(BiConsumer<CommandDispatcher<CommandSourceStack>, CommandBuildContext> consumer);
}