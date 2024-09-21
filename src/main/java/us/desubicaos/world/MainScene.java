package us.desubicaos.world;

import com.restonic4.citadel.core.Window;
import com.restonic4.citadel.files.parsers.mesh.MeshLoader;
import com.restonic4.citadel.input.KeyListener;
import com.restonic4.citadel.input.MouseListener;
import com.restonic4.citadel.physics.bounding.OBB;
import com.restonic4.citadel.render.cameras.PerspectiveCamera;
import com.restonic4.citadel.sound.SoundManager;
import com.restonic4.citadel.sound.SoundSource;
import com.restonic4.citadel.util.Time;
import com.restonic4.citadel.world.Scene;
import com.restonic4.citadel.world.object.GameObject;
import com.restonic4.citadel.world.object.Mesh;
import com.restonic4.citadel.world.object.Transform;
import com.restonic4.citadel.world.object.components.LightComponent;
import com.restonic4.citadel.world.object.components.ModelRendererComponent;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import us.desubicaos.world.registries.Sounds;

public class MainScene extends Scene {
    public Mesh cubeMesh;
    public GameObject objectA, objectB;
    public OBB aOBB, bOBB;

    @Override
    public void init() {
        Transform camTransform = new Transform();
        camTransform.setPosition(0, 0, 100);
        camTransform.setScale(1, 1, 1);

        camera = new PerspectiveCamera(camTransform);
        camera.load();

        SoundSource music = Sounds.MUSIC.createSource(true, true);
        music.setPosition(new Vector3f(0, 0, 0));
        //music.play();

        cubeMesh = MeshLoader.loadMesh("assets/models/cube.obj");

        objectA = new GameObject(false);
        objectA.transform.setPosition(-10, 0, 0);
        objectA.addComponent(new ModelRendererComponent(cubeMesh));
        this.addGameObject(objectA);

        objectB = new GameObject(false);
        objectB.transform.setPosition(10, 0, 0);
        objectB.addComponent(new ModelRendererComponent(cubeMesh));
        this.addGameObject(objectB);

        GameObject sun = new GameObject(false);
        sun.transform.setPosition(0, 1, 0);
        sun.addComponent(new LightComponent(LightComponent.LightType.DIRECTIONAL));
        this.addGameObject(sun);

        aOBB = new OBB(objectA.transform.getPosition(), cubeMesh.getVertices(), objectA.transform.getRotation());
        bOBB = new OBB(objectB.transform.getPosition(), cubeMesh.getVertices(), objectB.transform.getRotation());

        super.init();
    }

    @Override
    public void update() {
        if (KeyListener.isKeyPressedOnce(GLFW.GLFW_KEY_X)) {
            Window.getInstance().setCursorLocked(!Window.getInstance().isCursorLocked());
        }

        float velocity = 50;

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            velocity = 200;
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_A)) {
            camera.transform.addLocalPositionX((float) (-velocity * Time.getDeltaTime()));
        }
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_D)) {
            camera.transform.addLocalPositionX((float) (velocity * Time.getDeltaTime()));
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_W)) {
            camera.transform.addLocalPositionZ((float) (-velocity * Time.getDeltaTime()));
        }
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_S)) {
            camera.transform.addLocalPositionZ((float) (velocity * Time.getDeltaTime()));
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_Q)) {
            camera.transform.addLocalPositionY((float) (-velocity * Time.getDeltaTime()));
        }
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_E)) {
            camera.transform.addLocalPositionY((float) (velocity * Time.getDeltaTime()));
        }

        if (Window.getInstance().isCursorLocked()) {
            float sensitivity = 0.005f;

            float xMouseDelta = MouseListener.getDy() * sensitivity;
            float yMouseDelta = MouseListener.getDx() * sensitivity;

            Quaternionf pitchRotation = new Quaternionf().rotateX(xMouseDelta);
            Quaternionf yawRotation = new Quaternionf().rotateY(yMouseDelta);

            camera.transform.addRotationQuaternion(yawRotation);
            camera.transform.addRotationQuaternion(pitchRotation);
        }

        objectA.transform.addLocalRotationEuler((float) Time.getDeltaTime(), (float) Time.getDeltaTime(), (float) Time.getDeltaTime());
        objectB.transform.addLocalRotationEuler((float) Time.getDeltaTime(), (float) Time.getDeltaTime(), (float) Time.getDeltaTime());

        float speed = 5;
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_I)) {
            objectA.transform.addPositionX((float) (-Time.getDeltaTime() * speed));
        }
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_O)) {
            objectA.transform.addPositionX((float) (Time.getDeltaTime() * speed));
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_K)) {
            objectB.transform.addPositionX((float) (-Time.getDeltaTime() * speed));
        }
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_L)) {
            objectB.transform.addPositionX((float) (Time.getDeltaTime() * speed));
        }

        aOBB.update(objectA.transform.getPosition(), objectA.transform.getRotation(), objectA.transform.getScale());
        bOBB.update(objectB.transform.getPosition(), objectB.transform.getRotation(), objectB.transform.getScale());

        if (aOBB.intersects(bOBB)) {
            cubeMesh.setTint(new Vector4f(1, 0, 0, 1));
        }
        else {
            cubeMesh.setTint(new Vector4f(0, 1, 0, 1));
        }

        SoundManager.getInstance().updateListenerPosition(camera);

        super.update();
    }
}
