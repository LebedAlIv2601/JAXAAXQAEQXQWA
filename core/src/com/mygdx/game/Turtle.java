package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor{
    private Animation <TextureRegion> stand;
    private Animation <TextureRegion> walk;
    private Animation <TextureRegion> hit;
    private boolean at;
    private float elapsedTime;
    public Turtle(float x, float y, Stage s){
        super(x, y, s);

        stand = loadTexture("Staing.png");

        String[] fileNamesForWalk = {"Staing.png", "1going.png", "2going.png", "3going.png", "4going.png", "5going.png"};
        String[] fileNamesForHit = {"1.png","2.png", "3.png", "4.png", "5.png"};

        walk = loadAnimationFromFiles(fileNamesForWalk, 0.1f, true);
        hit = loadAnimationFromFiles(fileNamesForHit, 0.1f, true);

        at = false;

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        setBoundaryPolygon(8);

    }

    public void act(float dt) {
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            accelerateAtAngle(180);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            accelerateAtAngle(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            accelerateAtAngle(90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            accelerateAtAngle(270);
        }


        applyPhysics(dt);


        if(isHitting()){

            setAnimation(hit);

            if(isAnimationFinished()){
                setHitting(false);
            }
        } else if (!isMoving()) {
            setAnimation(stand);
        } else {

            setAnimation(hit);
        }



        if(getSpeed() > 0){
            setRotation(getMotionAngle());
        }
        boundToWorld();
        alignCamera();
    }


    public void setHitting(boolean f){
        at = f;
    }

    public boolean isHitting() {
        return at;
    }

}
