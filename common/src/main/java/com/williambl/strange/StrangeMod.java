package com.williambl.strange;

import com.williambl.strange.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static net.minecraft.commands.Commands.literal;

public class StrangeMod {

    public static final String MOD_ID = "strange";
    public static final String MOD_NAME = "Strange";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ResourceKey<Registry<ModifierType<?>>> MODIFIER_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(id("modifier_type"));
    public static final Registry<ModifierType<?>> MODIFIER_TYPE_REGISTRY = Services.REGISTRY.createRegistry(MODIFIER_TYPE_REGISTRY_KEY);
    public static final Holder<DataComponentType<List<ModifierData<?>>>> MODIFIER_DATA_COMPONENT_TYPE = Services.REGISTRY.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id("modifiers"), () -> DataComponentType.<List<ModifierData<?>>>builder()
            .persistent(ModifierData.CODEC.listOf())
            .networkSynchronized(ByteBufCodecs.<RegistryFriendlyByteBuf, ModifierData<?>>list().apply(ModifierData.STREAM_CODEC))
            .build());

    public static final Holder<ModifierType<Component>> PREFIX_MODIFIER_TYPE = Services.REGISTRY.register(MODIFIER_TYPE_REGISTRY, id("prefix"), () -> new ModifierType<>(
            mod -> orig -> Component.empty().append(mod).append(orig),
            ComponentSerialization.CODEC.fieldOf("prefix"),
            ComponentSerialization.STREAM_CODEC));

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        Services.REGISTRY.registerCommand((dispatcher, registries) -> {
            dispatcher.register(
                    literal("strangify").executes(ctx -> {
                        var player = ctx.getSource().getPlayerOrException();
                        var item = player.getMainHandItem();
                        if (item == ItemStack.EMPTY) {
                            item = player.getOffhandItem();
                        }

                        if (item == ItemStack.EMPTY) {
                            ctx.getSource().sendFailure(Component.literal("You buffoon."));
                            return 1;
                        }

                        item.applyComponents(DataComponentPatch.builder().set(MODIFIER_DATA_COMPONENT_TYPE.value(),
                                        List.of(new ModifierData<>(PREFIX_MODIFIER_TYPE.value(),
                                                Component.empty().append(Component.literal("STRANGE").withColor(0xCF6A32)).append(" "))))
                                .build());
                        return 0;
                    })
            );
            dispatcher.register(
                    literal("uniquify").executes(ctx -> {
                        var player = ctx.getSource().getPlayerOrException();
                        var item = player.getMainHandItem();
                        if (item == ItemStack.EMPTY) {
                            item = player.getOffhandItem();
                        }

                        if (item == ItemStack.EMPTY) {
                            ctx.getSource().sendFailure(Component.literal("You buffoon."));
                            return 1;
                        }

                        item.applyComponents(DataComponentPatch.builder().set(MODIFIER_DATA_COMPONENT_TYPE.value(),
                                        List.of(new ModifierData<>(PREFIX_MODIFIER_TYPE.value(),
                                                Component.empty().append(Component.literal("UNIQUE").withColor(0xFFD700)).append(" "))))
                                .build());
                        return 0;
                    })
            );
        });
    }
}