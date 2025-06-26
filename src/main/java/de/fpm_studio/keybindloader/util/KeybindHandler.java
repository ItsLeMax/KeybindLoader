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
 * Enthält die Logik zum Laden und Speichern der Tastenbelegungen
 *
 * @author ItsLeMax
 * @since 24.06.2025
 */
public final class KeybindHandler {

    private final File CONFIG_FILE = new File(Minecraft.getMinecraft().gameDir,
            "config/keybindloader.json"
    );

    /**
     * Lädt die Tastenbelegungen
     *
     * @author ItsLeMax
     * @since 24.06.2025
     */
    public void loadKeyBindings() {

        if (!CONFIG_FILE.exists())
            return;

        // Datei wird gelesen

        try (final Reader reader = new InputStreamReader(Files.newInputStream(CONFIG_FILE.toPath()), StandardCharsets.UTF_8)) {

            // Diese wird hier zu einem brauchbaren JSON-Objekt konvertiert

            final JsonObject jsonFile = new JsonParser().parse(reader).getAsJsonObject();
            final KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;

            // Alle Tastenbelegungen werden abgeglichen mit denen der Datei bzw. des JSON-Objekts

            for (KeyBinding keyBinding : keyBindings) {

                if (!jsonFile.has(keyBinding.getKeyDescription()))
                    continue;

                // Bei Übereinstimmung wird die Einstellung übernommen

                final int keyCode = jsonFile.get(keyBinding.getKeyDescription()).getAsInt();
                keyBinding.setKeyCode(keyCode);

            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    /**
     * Speichert die Tastenbelegungen in eine Datei
     *
     * @author ItsLeMax
     * @since 24.06.2025
     */
    public void saveKeyBindings() {

        final KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;

        // Die Datei wird erstellt

        final JsonObject jsonFile = new JsonObject();

        // Alle Tastenbelegungen werden Stück für Stück in dieser hinterlegt

        for (final KeyBinding key : keyBindings) {
            jsonFile.addProperty(key.getKeyDescription(), key.getKeyCode());
        }

        // Die Datei wird gespeichert

        try (final Writer writer = new OutputStreamWriter(Files.newOutputStream(CONFIG_FILE.toPath()), StandardCharsets.UTF_8)) {

            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonFile, writer);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}