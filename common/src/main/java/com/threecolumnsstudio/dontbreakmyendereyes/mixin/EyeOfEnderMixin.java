package com.threecolumnsstudio.dontbreakmyendereyes.mixin;

import com.threecolumnsstudio.dontbreakmyendereyes.config.DBMEEConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnder.class)
public abstract class EyeOfEnderMixin {
    @Shadow
    private boolean surviveAfterDeath;

    @Inject(method = "signalTo", at = @At("TAIL"))
    private void dbmee$applyShatterChance(CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        this.surviveAfterDeath = self.getRandom().nextFloat() >= DBMEEConfig.get().shatterChance();
    }
}
