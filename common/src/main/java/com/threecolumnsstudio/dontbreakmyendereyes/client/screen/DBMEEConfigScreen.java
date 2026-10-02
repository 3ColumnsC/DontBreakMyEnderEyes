package com.threecolumnsstudio.dontbreakmyendereyes.client.screen;

import java.util.function.Consumer;

import com.threecolumnsstudio.dontbreakmyendereyes.config.DBMEEConfig;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.tabs.MenuTabBar;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public final class DBMEEConfigScreen extends Screen {

    private static final int WIDGET_HEIGHT = 20;
    private static final int ROW_SPACING = 6;
    private static final int INITIAL_SCROLL_HEIGHT = 140;
    private static final int BUTTON_WIDTH = 100;
    private static final int CONTENT_TOP_PADDING = 8;
    private static final int CONTENT_H_MARGIN = 24;
    private static final int GENERAL_WIDTH = 240;

    private final Screen parent;
    private final HeaderAndFooterLayout layout;
    private final TabManager tabManager;

    private MenuTabBar tabNavigationBar;

    private float shatterChance;

    public DBMEEConfigScreen(Screen parent) {
        super(Component.translatable("dbmee.config.title"));
        this.parent = parent;
        this.layout = new HeaderAndFooterLayout(this);
        this.tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);
        this.shatterChance = DBMEEConfig.get().shatterChance();
    }

    @Override
    protected void init() {
        ScrollableLayout generalTab = new ScrollableLayout(this.minecraft, buildGeneralContent(), INITIAL_SCROLL_HEIGHT);

        this.tabNavigationBar = MenuTabBar.builder(this.tabManager, this.width)
            .addTab(new ConfigTab(Component.translatable("dbmee.config.tab.general"), generalTab))
            .build();
        this.addRenderableWidget(this.tabNavigationBar);

        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(Component.translatable("dbmee.config.apply"), button -> apply())
            .width(BUTTON_WIDTH)
            .build());
        footer.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> onClose())
            .width(BUTTON_WIDTH)
            .build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.tabNavigationBar.selectTab(0, false);
        this.repositionElements();
    }

    private GridLayout buildGeneralContent() {
        int widgetWidth = Math.min(GENERAL_WIDTH, Math.max(160, this.width - CONTENT_H_MARGIN * 2));
        GridLayout grid = new GridLayout();
        grid.columnSpacing(0).rowSpacing(ROW_SPACING);
        GridLayout.RowHelper rows = grid.createRowHelper(1);

        ShatterChanceSlider slider = new ShatterChanceSlider(0, 0, widgetWidth, WIDGET_HEIGHT,
            Component.translatable("dbmee.config.shatter_chance"), this.shatterChance,
            value -> this.shatterChance = value);
        slider.setTooltip(Tooltip.create(Component.translatable("dbmee.config.server_side")));
        rows.addChild(slider);

        return grid;
    }

    private void apply() {
        DBMEEConfig.save(this.shatterChance);
        onClose();
    }

    @Override
    protected void repositionElements() {
        if (this.tabNavigationBar == null) {
            return;
        }
        this.tabNavigationBar.arrangeElements(this.width);
        int headerBottom = this.tabNavigationBar.getRectangle().bottom();
        ScreenRectangle tabArea = new ScreenRectangle(0, headerBottom, this.width,
            this.height - this.layout.getFooterHeight() - headerBottom);
        this.tabManager.setTabArea(tabArea);
        this.layout.setHeaderHeight(headerBottom);
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    @Override
    protected void extractMenuBackground(GuiGraphicsExtractor graphics) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, CreateWorldScreen.TAB_HEADER_BACKGROUND,
            0, 0, 0.0F, 0.0F, this.width, this.layout.getHeaderHeight(), 16, 16);
        this.extractMenuBackground(graphics, 0, this.layout.getHeaderHeight(), this.width, this.height);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        graphics.blit(RenderPipelines.GUI_TEXTURED, Screen.FOOTER_SEPARATOR,
            0, this.height - this.layout.getFooterHeight() - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
    }

    private static final class ConfigTab implements Tab {

        private final Component title;
        private final ScrollableLayout content;

        private ConfigTab(Component title, ScrollableLayout content) {
            this.title = title;
            this.content = content;
        }

        @Override
        public Component getTabTitle() {
            return this.title;
        }

        @Override
        public Component getTabExtraNarration() {
            return Component.empty();
        }

        @Override
        public void visitChildren(Consumer<AbstractWidget> consumer) {
            this.content.visitWidgets(consumer);
        }

        @Override
        public Layout getLayout() {
            return this.content;
        }

        @Override
        public void doLayout(ScreenRectangle area) {
            this.content.arrangeElements();
            int viewportHeight = area.height() - CONTENT_TOP_PADDING;
            this.content.setMaxHeight(viewportHeight);
            this.content.setMinHeight(viewportHeight);
            this.content.arrangeElements();
            int centeredX = area.left() + (area.width() - this.content.getWidth()) / 2;
            this.content.setPosition(centeredX, area.top() + CONTENT_TOP_PADDING);
        }
    }
}
