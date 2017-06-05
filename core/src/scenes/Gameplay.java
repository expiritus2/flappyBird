package scenes;


import com.awesome.flappybird.GameMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import bird.Bird;
import ground.GroundBody;
import helpers.GameInfo;

public class Gameplay implements Screen {

    private GameMain game;
    private World world;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private OrthographicCamera debugCamera;
    private Box2DDebugRenderer debugRenderer;

    private Array<Sprite> backgrounds = new Array<Sprite>();
    private Array<Sprite> grounds = new Array<Sprite>();

    private Bird bird;

    private GroundBody groundBody;

    public Gameplay(GameMain game){
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HIGHT / 2f, 0);

        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HIGHT, mainCamera);

        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM, GameInfo.HIGHT / GameInfo.PPM);
        debugCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HIGHT / 2f, 0);

        debugRenderer = new Box2DDebugRenderer();


        createBackgrounds();
        createGrounds();

        world = new World(new Vector2(0, -9.8f), true);
        bird = new Bird(world, GameInfo.WIDTH / 2f - 80, GameInfo.HIGHT / 2f);

        groundBody = new GroundBody(world, grounds.get(0));

    }

    void update(float dt){
        moveBackgrounds();
        moveGrounds();
        birdFlap();
    }

    void birdFlap(){
        if(Gdx.input.justTouched()){
            bird.birdFlap();
        }
    }

    void createBackgrounds(){
        for(int i = 0; i < 3; i++){
            Sprite bg = new Sprite(new Texture("Backgrounds/Day.jpg"));
            bg.setPosition(i * bg.getWidth(), 0);
            backgrounds.add(bg);
        }
    }

    void createGrounds(){
        for(int i = 0; i < 3; i++){
            Sprite ground = new Sprite(new Texture("Backgrounds/Ground.png"));
            ground.setPosition(i * ground.getWidth(), -ground.getHeight() / 2f - 55);
            grounds.add(ground);
        }
    }

    void drawBackgrouns(SpriteBatch batch){
        for(Sprite s : backgrounds){
            batch.draw(s, s.getX(), s.getY());
        }
    }

    void drawGrounds(SpriteBatch batch){
        for(Sprite ground : grounds){
            batch.draw(ground, ground.getX(), ground.getY());
        }
    }

    void moveBackgrounds(){

        for(Sprite bg : backgrounds){
            float x1 = bg.getX() - 2f;
            bg.setPosition(x1, bg.getY());

            if(bg.getX() + GameInfo.WIDTH + (bg.getWidth() / 2f) < mainCamera.position.x){
                float x2 = bg.getX() + bg.getWidth() * backgrounds.size;
                bg.setPosition(x2, bg.getY());
            }
        }

    }

    void moveGrounds(){
        for(Sprite ground : grounds){
            float x1 = ground.getX() - 1f;
            ground.setPosition(x1, ground.getY());

            if(ground.getX() + GameInfo.WIDTH + (ground.getWidth() / 2) < mainCamera.position.x){
                float x2 = ground.getX() + ground.getWidth() * grounds.size;
                ground.setPosition(x2, ground.getY());
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        drawBackgrouns(game.getBatch());
        drawGrounds(game.getBatch());
        bird.drawIdle(game.getBatch());

        game.getBatch().end();

        debugRenderer.render(world, debugCamera.combined);

        bird.updateBird();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
