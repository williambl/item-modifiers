package com.williambl.strange.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.williambl.strange.StrangeMod;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {
    @ModifyReturnValue(method = "getHoverName", at = @At("RETURN"))
    private Component strange$modifyHoverName(Component name) {
        var modifiers = this.get(StrangeMod.MODIFIER_DATA_COMPONENT_TYPE.value());
        if (modifiers != null) {
            for (var modifier : modifiers) {
                name = modifier.itemNameChanger().apply(name);
            }
        }

        return name;
    }
}
