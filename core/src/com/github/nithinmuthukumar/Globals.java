package com.github.nithinmuthukumar;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.nithinmuthukumar.components.*;

import java.util.Arrays;
import java.util.Comparator;

public class Globals {
    public static final float PPM = 100;
    public static World world = new World(new Vector2(0,-0.09f*PPM), false);
    public static final float GROUNDLEVEL = -2.18f;
    public static final SpriteBatch batch = new SpriteBatch();
    public static OrthographicCamera mainCamera;
    public static Engine engine = new Engine();
    public static SpidGame game;
    public static Player player;
    public static final RayHandler rayHandler= new RayHandler(world);
    public static Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));



    public static final ComponentMapper<CTransform> cmTransform = ComponentMapper.getFor(CTransform.class);
    public static final ComponentMapper<CRenderable> cmRenderable = ComponentMapper.getFor(CRenderable.class);
    public static final ComponentMapper<CState> cmState = ComponentMapper.getFor(CState.class);
    public static final ComponentMapper<CAnimation> cmAnimation = ComponentMapper.getFor(CAnimation.class);
    public static final ComponentMapper<CBody> cmBody = ComponentMapper.getFor(CBody.class);
    public static final ComponentMapper<CEnemy> cmEnemy = ComponentMapper.getFor(CEnemy.class);
    public static final ComponentMapper<CLight> cmLight = ComponentMapper.getFor(CLight.class);
    public static final ComponentMapper<CRemoval> cmRemoval = ComponentMapper.getFor(CRemoval.class);
    public static final ComponentMapper<CHealth> cmHealth = ComponentMapper.getFor(CHealth.class);

    public static final ComponentMapper<CWeapon> cmWeapon = ComponentMapper.getFor(CWeapon.class);
    public static final ComponentMapper<CDecay> cmDecay = ComponentMapper.getFor(CDecay.class);
    public static final ComponentMapper<CPlayer> cmPlayer = ComponentMapper.getFor(CPlayer.class);

    public static final ComponentMapper<CFollow> cmFollow = ComponentMapper.getFor(CFollow.class);




    //compares entities by their z values so that they can be sorted for drawing
    public static Comparator<Entity> zyComparator = (e1, e2) -> {

        if (cmTransform.get(e1).z == cmTransform.get(e2).z) {
            return (int) Math.signum(cmTransform.get(e2).getRenderY() - cmTransform.get(e1).getRenderY());
        } else {
            return (int) Math.signum(cmTransform.get(e1).z - cmTransform.get(e2).z);
        }

    };

    public static void print(String file, String... message) {
        System.out.print(file + ": ");
        for (String s : message) {
            System.out.print(s + " ");

        }
        System.out.print("\n");

    }

    //joins an array into one string
    public static String joinArray(String[] strings) {
        //string builder joins Strings efficiently
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {

            stringBuilder.append(s);

        }

        return stringBuilder.toString();


    }
    //return the angle of e relative to e
    //checks if a value is within the upper and lower bound
    public static boolean inBounds(int lowerBound, int upperBound, float val) {
        return lowerBound < val && val < upperBound;

    }
    //returns the camera coordinates of the point that is inputted in terms of the camera
    public static int screenToCameraY(float y, OrthographicCamera camera) {

        return MathUtils.round(camera.position.y + Gdx.graphics.getHeight() / 2 - y);
    }

    public static int screenToCameraX(float x, OrthographicCamera camera) {

        return MathUtils.round(camera.position.x - Gdx.graphics.getWidth() / 2 + x);
    }
    public static FileHandle[] listFiles(FileHandle f) {
        //List all files in a directory that arent .DS_Store
        //mac problem
        //also sorts it that pictures always come out the proper order
        FileHandle[] fileHandles = f.list(n -> !new FileHandle(n).extension().equals("DS_Store"));
        Arrays.sort(fileHandles, Comparator.comparing(FileHandle::name));
        return fileHandles;
    }
//TODO implement camera shake
    public static void shakeCamera() {
    }
}
