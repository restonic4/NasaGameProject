package us.desubicaos.core;

import com.restonic4.citadel.core.GameLogic;
import com.restonic4.citadel.registries.RegistryManager;
import us.desubicaos.world.registries.Sounds;

public class Shared implements GameLogic {
    @Override
    public void start() {
        RegistryManager.registerRegistrySet(new Sounds());

        RegistryManager.registerCustom();
    }

    @Override
    public void update() {

    }
}
