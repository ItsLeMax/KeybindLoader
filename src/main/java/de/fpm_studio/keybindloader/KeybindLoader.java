package de.fpm_studio.keybindloader;

import de.fpm_studio.keybindloader.listener.CustomButtonListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Contains the entry point of the mod
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@Mod(modid = KeybindLoader.MOD_ID, name = KeybindLoader.NAME, version = KeybindLoader.VERSION, clientSideOnly = true)
public final class KeybindLoader {

    static final String MOD_ID = "keybindloader";
    static final String NAME = "Keybind Loader";
    static final String VERSION = "1.0.1";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent preinit) {
        MinecraftForge.EVENT_BUS.register(new CustomButtonListener());
    }

}