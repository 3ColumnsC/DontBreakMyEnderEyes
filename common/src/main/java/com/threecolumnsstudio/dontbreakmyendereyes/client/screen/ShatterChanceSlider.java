package com.threecolumnsstudio.dontbreakmyendereyes.client.screen;

import java.util.function.Consumer;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public final class ShatterChanceSlider extends AbstractSliderButton {

    private static final int STEPS = 100;

    private final Component label;
    private final Consumer<Float> onValueChanged;

    public ShatterChanceSlider(int x, int y, int width, int height, Component label, float initialValue, Consumer<Float> onValueChanged) {
        super(x, y, width, height, Component.empty(), toSliderValue(initialValue));
        this.label = label;
        this.onValueChanged = onValueChanged;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        if (this.label == null) {
            return;
        }
        setMessage(Component.translatable(
            "dbmee.config.shatter_chance.value",
            this.label,
            Component.literal(currentPercent() + "%")
        ));
    }

    @Override
    protected void applyValue() {
        updateMessage();
        if (this.onValueChanged != null) {
            this.onValueChanged.accept(currentValue());
        }
    }

    public float currentValue() {
        return (float) (Math.round(this.value * STEPS) / (double) STEPS);
    }

    private int currentPercent() {
        return Math.round(currentValue() * 100.0F);
    }

    private static double toSliderValue(float chance) {
        float clamped = Math.clamp(chance, 0.0F, 1.0F);
        return Math.round(clamped * STEPS) / (double) STEPS;
    }
}
