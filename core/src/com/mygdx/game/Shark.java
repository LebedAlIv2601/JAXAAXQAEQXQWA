package com.mygdx.game;

public class Shark extends ActorBeta {
    private int k=1;
    private int l=1;

    public Shark(){
        super();
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if (getX() >= 60 && getX() <= 740){
            this.moveBy((float) ((int)(Math.random()*7)*l), 0);
        } else if (getX()<60){
            this.setPosition(60, getY());
            l=1;
        } else if (getX() > 740){
            this.setPosition(740, getY());
            l=-1;
        }

        if (getY() >= 60 && getY() <= 540){
            this.moveBy(0, (float) ((int)(Math.random()*7)*k));
        } else if (getY()<60){
            this.setPosition(getX(), 60);
            k=1;
        } else if (getY() > 540){
            this.setPosition(getX(), 540);
            k=-1;
        }

    }
}
