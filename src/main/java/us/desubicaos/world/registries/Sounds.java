package us.desubicaos.world.registries;

import com.restonic4.citadel.registries.AbstractRegistryInitializer;
import com.restonic4.citadel.registries.AssetLocation;
import com.restonic4.citadel.registries.Registries;
import com.restonic4.citadel.registries.Registry;
import com.restonic4.citadel.registries.built_in.types.Sound;
import us.desubicaos.core.GameConstants;

public class Sounds extends AbstractRegistryInitializer {
    public static Sound GLASS;
    public static Sound MUSIC;

    @Override
    public void register() {
        GLASS = Registry.register(Registries.SOUND, new AssetLocation(GameConstants.VANILLA_NAMESPACE, "glass"), new Sound());
        MUSIC = Registry.register(Registries.MUSIC, new AssetLocation(GameConstants.VANILLA_NAMESPACE, "music"), new Sound());
    }
}
