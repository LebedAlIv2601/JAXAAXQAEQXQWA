package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Main extends GameBeta {
	private Turtle turtle;
//	private Music mainTheme;

	private boolean win;
	
	@Override
	public void initialize () {
		BaseActor ocean = new BaseActor(0, 0, mainStage);
		ocean.loadTexture("ocean.png");
		ocean.setSize(1200, 900);
		BaseActor.setWorldBounds(ocean);

		new Starfish(380,380, mainStage);
		new Starfish(500,100, mainStage);
		new Starfish(100,450, mainStage);
		new Starfish(250,200, mainStage);

		new Rock(200, 150, mainStage);
		new Rock(100, 300, mainStage);
		new Rock(300, 350, mainStage);
		new Rock(450, 200, mainStage);

		turtle = new Turtle(20, 20,mainStage);



//		mainTheme =  Gdx.audio.newMusic(Gdx.files.internal("goae.mp3"));
//		mainTheme.setLooping(true);
//		mainTheme.play();

		win = false;
	}

	@Override
	public void update(float dt) {

		for (BaseActor rockActor : BaseActor.getList(mainStage, "Rock")){
			turtle.preventOverlap(rockActor);
		}

		for (BaseActor starfishActor: BaseActor.getList(mainStage, "Starfish")) {
			Starfish starfish = (Starfish) starfishActor;

			if (turtle.overlaps(starfish) && !starfish.isCollected()) {
				starfish.collect();
				Whirlpool whirl = new Whirlpool(0, 0, mainStage);
				whirl.centerAtActor(starfish);
				whirl.setOpacity(0.25f);
			}
		}
		if(BaseActor.count(mainStage, "Starfish")==0 && !win){
			win = true;
			BaseActor winMessage = new BaseActor(0, 0, uiStage);
			winMessage.loadTexture("winMessage.png");
			winMessage.centerAtPosition(400, 300);
			winMessage.setOpacity(0);
			winMessage.addAction(Actions.delay(1));
			winMessage.addAction(Actions.after(Actions.fadeIn(1)));
		}

	}

	@Override
	public void dispose () {
		mainStage.dispose();
//		mainTheme.dispose();
	}
}
