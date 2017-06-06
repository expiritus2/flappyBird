package hud;


import com.awesome.flappybird.GameMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import helpers.GameInfo;

public class UIHud {

    private GameMain game;

    private Stage stage;
    private Viewport gameViewport;

    private Label scoreLabel;

    private int score;

    public UIHud(GameMain game){
        this.game = game;

        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        createLabel();

        stage.addActor(scoreLabel);
    }

    void createLabel(){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/04b_19.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;

        BitmapFont font = generator.generateFont(parameter);

        scoreLabel = new Label(String.valueOf(score),
                new Label.LabelStyle(font, Color.WHITE));

        scoreLabel.setPosition(GameInfo.WIDTH / 2f - scoreLabel.getWidth() / 2f,
                GameInfo.HIGHT / 2f + 200);



    }

    public void incrementScore(){
        score++;
        scoreLabel.setText(String.valueOf(score));
    }

    public void showScore(){
        scoreLabel.setText(String.valueOf(score));
        stage.addActor(scoreLabel);
    }

    public Stage getStage(){
        return this.stage;
    }

}
