package us.desubicaos.core;

import com.restonic4.citadel.core.GameLogic;
import com.restonic4.citadel.world.SceneManager;
import us.desubicaos.world.MainScene;

public class Client implements GameLogic {
    @Override
    public void start() {
        SceneManager.loadScene(new MainScene());
    }

    @Override
    public void update() {

    }
}
