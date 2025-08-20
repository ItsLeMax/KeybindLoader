package de.fpm_studio.keybindloader.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Contains the logic to save and load keybindings
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
public final class KeybindHandler {

    private final File CONFIG_FILE = new File(Minecraft.getMinecraft().gameDir,
            "config/keybindloader.json"
    );

    /**
     * Loads the keybindings from a file
     *
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void loadKeyBindings() {

        if (!CONFIG_FILE.exists())
            return;

        // File will be read

        try (final Reader reader = new InputStreamReader(Files.newInputStream(CONFIG_FILE.toPath()), StandardCharsets.UTF_8)) {

            // It will be converted to a usable JSON object here

            final JsonObject jsonFile = new JsonParser().parse(reader).getAsJsonObject();
            final KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;

            // All keybindings will be compared with the ones of the file or JSON object

            for (KeyBinding keyBinding : keyBindings) {

                if (!jsonFile.has(keyBinding.getKeyDescription()))
                    continue;

                // Setting will be applied on match

                final int keyCode = jsonFile.get(keyBinding.getKeyDescription()).getAsInt();
                keyBinding.setKeyCode(keyCode);

            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // Apply changes

        KeyBinding.resetKeyBindingArrayAndHash();

    }

    /**
     * Saves the keybindings in a file
     *
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void saveKeyBindings() {

        final KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;

        // The file will be created

        final JsonObject jsonFile = new JsonObject();

        // All keybindings will be added bit by bit

        for (final KeyBinding key : keyBindings) {
            jsonFile.addProperty(key.getKeyDescription(), key.getKeyCode());
        }

        // The file will be saved

        try (final Writer writer = new OutputStreamWriter(Files.newOutputStream(CONFIG_FILE.toPath()), StandardCharsets.UTF_8)) {

            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonFile, writer);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}