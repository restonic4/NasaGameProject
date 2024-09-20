package us.desubicaos.world;

import com.restonic4.citadel.render.cameras.PerspectiveCamera;
import com.restonic4.citadel.sound.SoundManager;
import com.restonic4.citadel.sound.SoundSource;
import com.restonic4.citadel.world.Scene;
import com.restonic4.citadel.world.object.Transform;
import org.joml.Vector3f;
import us.desubicaos.world.registries.Sounds;

public class MainScene extends Scene {
    @Override
    public void init() {
        Transform camTransform = new Transform();
        camTransform.setPosition(0, 0, 100);
        camTransform.setScale(1, 1, 1);

        camera = new PerspectiveCamera(camTransform);
        camera.load();

        SoundSource music = Sounds.MUSIC.createSource(true, true);
        music.setPosition(new Vector3f(0, 0, 0));
        music.play();

        super.init();
    }

    @Override
    public void update() {
        SoundManager.getInstance().updateListenerPosition(camera);

        super.update();
    }
}
