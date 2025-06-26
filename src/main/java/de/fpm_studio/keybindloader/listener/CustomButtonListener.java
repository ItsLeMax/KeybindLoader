package de.fpm_studio.keybindloader.listener;

import de.fpm_studio.keybindloader.util.KeybindHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Contains the logic for all button related events
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
public final class CustomButtonListener {

    private final KeybindHandler keybindHandler = new KeybindHandler();

    private final int SAVE_BUTTON_ID = 9001;
    private final int LOAD_BUTTON_ID = 9002;

    private GuiButton saveButton;
    private GuiButton loadButton;

    private long hoverUpdateTime;
    private int lastInteraction;

    @SubscribeEvent
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {

        if (!(event.getGui() instanceof GuiControls))
            return;

        // Buttons to save and load the keybindings will be created under these coordinates

        final int x = 10;
        final int y = 10;

        final int width = 175;
        final int height = 20;

        saveButton = new GuiButton(SAVE_BUTTON_ID, x, y, width, height, I18n.format("button.save.text"));
        loadButton = new GuiButton(LOAD_BUTTON_ID, x, (y + 25), width, height, I18n.format("button.load.text"));

        event.getButtonList().add(saveButton);
        event.getButtonList().add(loadButton);

    }

    @SubscribeEvent
    public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {

        if (!(event.getGui() instanceof GuiControls))
            return;

        // Every keybinding will be saved

        if (event.getButton().id == SAVE_BUTTON_ID) {
            keybindHandler.saveKeyBindings();
            lastInteraction = SAVE_BUTTON_ID;
        }

        // Every keybinding will be loaded

        if (event.getButton().id == LOAD_BUTTON_ID) {
            keybindHandler.loadKeyBindings();
            lastInteraction = LOAD_BUTTON_ID;
        }

        hoverUpdateTime = System.currentTimeMillis() + 3000;

    }

    @SubscribeEvent
    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {

        final GuiScreen gui = event.getGui();

        if (!(gui instanceof GuiControls))
            return;

        String tooltip = null;
        final boolean interactLately = System.currentTimeMillis() < hoverUpdateTime;

        // Text will be shown when hovering on the buttons, with special information after pressing a button

        if (saveButton.isMouseOver()) {

            tooltip = lastInteraction == SAVE_BUTTON_ID && interactLately
                    ? I18n.format("button.save.success")
                    : I18n.format("button.save.hover");

        }

        if (loadButton.isMouseOver()) {

            tooltip = lastInteraction == LOAD_BUTTON_ID && interactLately
                    ? I18n.format("button.load.success")
                    : I18n.format("button.load.hover");

        }

        if (tooltip == null)
            return;

        gui.drawHoveringText(tooltip, event.getMouseX(), event.getMouseY());

    }

}