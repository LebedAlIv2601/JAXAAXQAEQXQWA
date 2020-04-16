package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private boolean win;
    private Music instrumental;
    private Music oceanSurf;
    private float audioVolume;
    private Sound waterDrop;
    private Label starfishLabel;
    private DialogBox dialogBox;

    public void initialize() {
        final BaseActor ocean = new BaseActor(0, 0, mainStage);
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

        starfishLabel= new Label("Starfish left: ", BaseGame.labelStyle);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button (buttonStyle);
//        restartButton.setColor(Color.CYAN);


        restartButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)) {

                    return false;
                }
                instrumental.dispose();
                oceanSurf.dispose();
                StarfishGame.setActiveScreen(new LevelScreen());
                return false;
            }
        }
        );

        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();
        Texture buttonTex2 = new Texture(Gdx.files.internal("audio.png"));
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable(buttonRegion2);

        Button muteButton = new Button(buttonStyle2);

        muteButton.addListener(
                new EventListener() {
                    @Override
                    public boolean handle(Event e) {
                        if (!LevelScreen.this.isTouchDownEvent(e)) {
                            return false;
                        }
                        audioVolume = 1 - audioVolume;
                        instrumental.setVolume(audioVolume/3);
                        oceanSurf.setVolume(audioVolume/10);
                        return true;
                    }
                }
                );

//        mainTheme =  Gdx.audio.newMusic(Gdx.files.internal("goae.mp3"));
//        mainTheme.setLooping(true);
//        mainTheme.play();

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(muteButton).top();
        uiTable.add(restartButton).top();


        Sign sign1 = new Sign(20, 400, mainStage);
        sign1.setText("West Starfish Bay");

        Sign sign2 = new Sign(600, 300, mainStage);
        sign2.setText("East Starfish Bay");

        Sign sign3 = new Sign(1140, 0, mainStage);
        sign3.setText("Artyom, pashel nahuy! >:/");

        dialogBox = new DialogBox(0,0,uiStage);
//        dialogBox.setBackgroundColor(Color.CYAN);
//        dialogBox.setFontColor(Color.BLUE);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.8f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.row();
        uiTable.add(dialogBox).colspan(4);

        waterDrop = Gdx.audio.newSound(Gdx.files.internal("waterDrop.mp3"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("instrumental.wav"));
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("oceanSurf.mp3"));

        audioVolume = 1.0f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume/3);
        instrumental.play();
        oceanSurf.setLooping(true);
        oceanSurf.setVolume(audioVolume/10);
        oceanSurf.play();

        win = false;
    }

    public void update(float dt) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, "Rock")){
            turtle.preventOverlap(rockActor);
        }

        for (BaseActor starfishActor: BaseActor.getList(mainStage, "Starfish")) {
            Starfish starfish = (Starfish) starfishActor;

            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();
                waterDrop.play();
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

        for(BaseActor signActor : BaseActor.getList(mainStage, "Sign")){
            Sign sign = (Sign)signActor;

            turtle.preventOverlap(sign);
            boolean nearby = turtle.isWithinDistance(4, sign);
            if(nearby && !sign.isViewing()){
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if(sign.isViewing() && !nearby){
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }

        starfishLabel.setText("Starfish left: "+BaseActor.count(mainStage,"Starfish"));
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE){
            turtle.setHitting(true);
        }
        return false;
    }
}
