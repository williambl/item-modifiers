package com.williambl.strange;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.UnaryOperator;

public record ModifierData<T>(ModifierType<T> modifierType, T data) {
    public static final Codec<ModifierData<?>> CODEC = StrangeMod.MODIFIER_TYPE_REGISTRY.byNameCodec().dispatch(
            ModifierData::modifierType,
            ModifierType::componentCodec);

    public static final StreamCodec<RegistryFriendlyByteBuf, ModifierData<?>> STREAM_CODEC = ByteBufCodecs.registry(StrangeMod.MODIFIER_TYPE_REGISTRY_KEY).dispatch(
            ModifierData::modifierType,
            ModifierType::componentStreamCodec);

    public UnaryOperator<Component> itemNameChanger() {
        return this.modifierType().itemNameChanger().apply(this.data());
    }
}
