package de.fpm_studio.keybindloader;

import de.fpm_studio.keybindloader.listener.CustomButtonListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Enthält den Einstiegspunkt der Mod
 *
 * @author ItsLeMax
 * @since 24.06.2025
 */
@Mod(modid = KeybindLoader.MOD_ID, name = KeybindLoader.NAME, version = KeybindLoader.VERSION, clientSideOnly = true)
public final class KeybindLoader {

    public static final String MOD_ID = "KeybindLoader";
    public static final String NAME = "Keybind Loader";
    public static final String VERSION = "24.06.2025";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent preinit) {
        MinecraftForge.EVENT_BUS.register(new CustomButtonListener());
    }

}