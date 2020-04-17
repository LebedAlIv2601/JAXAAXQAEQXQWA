package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sign extends BaseActor{
    private String text;
    private boolean viewing;
    public Sign(float x, float y, Stage s){
        super(x,y,s);
        loadTexture("dialogDesk.png");
        text = " ";
        viewing = false;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setViewing(boolean v){
        viewing = v;
    }

    public boolean isViewing(){
        return viewing;
    }
}
