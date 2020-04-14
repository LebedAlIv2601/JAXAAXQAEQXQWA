package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor{
    public Turtle(float x, float y, Stage s){
        super(x, y, s);

        String[] fileNames = {"Staing.png", "1going.png", "2going.png", "3going.png", "4going.png", "5going.png"};

        loadAnimationFromFiles(fileNames, 0.08f, true);

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        setBoundaryPolygon(8);
    }

    public void act(float dt) {
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched() && Gdx.input.getX()<400)){
            accelerateAtAngle(180);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| (Gdx.input.isTouched() && Gdx.input.getX()>400)){
            accelerateAtAngle(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            accelerateAtAngle(90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            accelerateAtAngle(270);
        }

        applyPhysics(dt);

//        setAnimationPaused(!isMoving());

//        if(getSpeed() > 0){
//            setRotation(getMotionAngle());
//        }
        boundToWorld();
        alignCamera();
    }


}
