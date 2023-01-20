package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;


import java.awt.*;
import java.util.*;
import java.util.List;

public class Level implements Screen {

    private Table table;
    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Chef> chefList = new ArrayList<>();
    long timer;
    long idleTime;
    List<List<String>> gridArray;
    String gameMode;
    //TimeUtils.millis();
    long timeToNextCustomer;
    long getTimeToIdleGame;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    float stateTime;
    SpriteBatch batch;
    private Chef chef1;

    final static int MAP_HEIGHT = 10;
    final static int MAP_WIDTH = 7;
    private Stage stage;
    private float GAME_HEIGHT = 100;
    private float GAME_WIDTH = 200;


    @Override
    public void render(float delta) {


        table = new Table();
        table.setFillParent(false);
        table.setPosition(100,50);
        table.sizeBy(100,100);
        //table.setDebug(true);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        batch = new SpriteBatch();
        chefList = new ArrayList<>();


        tiledMapRenderer.render();
        camera.position.set(chef1.getX() + chef1.getWidth() / 2, chef1.getY() + chef1.getHeight() / 2, 0);
        stage = new Stage(new FitViewport(32*MAP_WIDTH, 32*MAP_HEIGHT, camera));
        overlay myOverlay = new overlay();
        myOverlay.setUpTable(stage);
        myOverlay.setTableBackgroundColor(230,0,0,60);
        myOverlay.addText("example Text");
        myOverlay.addText("second tex");

        myOverlay.removeRow(0,1);
        myOverlay.addText("third");
        //stage.addActor(table);

        //Texture  texture = new Texture(Gdx.files.internal("Tiles/kitchen_fridge.png"));
        //TextureRegion upRegion = new TextureRegion(texture, 20, 20, 50, 50);

        //TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        //style.up = new TextureRegionDrawable(upRegion);
        //style.font = new BitmapFont(Gdx.files.internal("bitmapfont/Amble-Regular-26.fnt"));;

       // TextButton button1 = new TextButton("Button 1", style);
       // Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        //bgPixmap.setColor(88);
        //bgPixmap.setColor(69,55,3,150);
       // bgPixmap.fill();
      //  TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        //textureRegionDrawableBg.


       // table.add(button1);
       // table.background(textureRegionDrawableBg);
        stage.draw();


        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        chef1.draw(batch, delta);
        batch.end();
        camera.update();
    }

    @Override
    public void show() {
        tiledMap = new TmxMapLoader().load("gameMaps/level2.tmx");
        chef1 = new Chef(this);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        float aspectRatio = (float) (Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        camera = new OrthographicCamera(GAME_HEIGHT * aspectRatio, GAME_WIDTH * aspectRatio);
        Gdx.input.setInputProcessor(chef1);
    }

    @Override
    public void hide() {
        dispose();
    }

    public long getTimeToNextCustomer(){
        return timeToNextCustomer;
    }


    public void readAssetFile(String gameFile){

        //This function reads read in a gameMap file and populates the gridArray variable which
        //The gridArray is a 2d array that contains a map of the game world

        FileHandle handle =  Gdx.files.local(gameFile);
        String text = handle.readString();
        //System.out.println(text);
        String[] myStrings = text.split("\n");
        for (int i = 0 ; i < myStrings.length ; i ++){
            for (int x = 0 ; x < myStrings.length ; x ++){
                if (x == 0){gridArray.add(new ArrayList<String>());}
                gridArray.get(i).add(String.valueOf(myStrings[i].charAt(x)));
            }


        }
        System.out.println( gridArray.toString());

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    public void initialiseLevel(){

        timeToNextCustomer = 10000;
        getTimeToIdleGame = 15000;

        timer = TimeUtils.millis();


        idleTime = TimeUtils.millis();
        gridArray = new ArrayList<>();
        readAssetFile("gameMaps/gameMap.txt");
    }

    public long getTimeElapsedMilliSeconds(){
        return TimeUtils.timeSinceMillis(timer);
    }
    public long getTimeSinceLastIO(){
        return TimeUtils.timeSinceMillis(idleTime);
    }
    public long getMinIdleTime(){
        return getTimeToIdleGame;

    }
    public void nextCustomer(){
        if (customerList.size() > 0){
            customerList.get(customerList.size() - 1);
            customerList.remove(customerList.size() - 1);
            timer = TimeUtils.millis();
        }
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        batch.dispose();
    }

    public TiledMapTileLayer getMapTileLayer(int i) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(i);
    }
}
