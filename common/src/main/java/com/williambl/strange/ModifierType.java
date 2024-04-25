package com.williambl.strange;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public record ModifierType<T>(Function<T, UnaryOperator<Component>> itemNameChanger, MapCodec<T> dataCodec, StreamCodec<RegistryFriendlyByteBuf, T> dataStreamCodec) {
    public MapCodec<ModifierData<T>> componentCodec() {
        return this.dataCodec().xmap(data -> new ModifierData<>(this, data), ModifierData::data);
    }

    public StreamCodec<RegistryFriendlyByteBuf, ModifierData<T>> componentStreamCodec() {
        return this.dataStreamCodec().map(data -> new ModifierData<>(this, data), ModifierData::data);
    }
}
