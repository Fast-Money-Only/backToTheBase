package com.mygdx.game;
// e2

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import sun.java2d.CRenderer;

import static com.mygdx.game.Constants.PPM;
import static com.mygdx.game.Constants.map0;

public class IsoMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen; // e3

    public IsoMapHelper(GameScreen gameScreen) {   // e3: parametre
        this.gameScreen = gameScreen;
    }

    public IsometricTiledMapRenderer setupMap(String map) {
        tiledMap = new TmxMapLoader().load(map);
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
        return new IsometricTiledMapRenderer(tiledMap);
    }


    // e3
    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {


            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }

            if (mapObject instanceof TiledMapTileMapObject) {
                // her skal vi finde ud af hvordan vi viser det tileImg der bruges
            }

            // e4 start
            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName.equals("player")) {
                    Body body = BodyHelperService.createBody(
                            rectangle.getX()  + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld()
                    );
                    // e4 Hop over til GameScreen og definer Player øverst og setPlayer i bunden
                    gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));
                }



            }
            // e4 slut
        }
    }


    // e3
    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject, tiledMap);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    // e3
    private Shape createPolygonShape(PolygonMapObject polygonMapObject, TiledMap map) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);

        float offsetX = map.getProperties().get("height", Integer.class);
        float offsetY = (float) ((offsetX / 2) + 0.5);

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / PPM; // Assuming PPM is some scale factor
            worldVertices[i].y = (mapHeight - vertices[i * 2 + 1]) / PPM; // Invert and scale y

            // Convert to isometric + deal with the offset
            Vector2 isoVert = TwoDToIso(new Vector2(worldVertices[i].x, worldVertices[i].y));
            worldVertices[i].x = isoVert.x + offsetX;
            worldVertices[i].y = isoVert.y + offsetY;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }


    public static Vector2 TwoDToIso(Vector2 point){
        Vector2 vel2 = new Vector2();

        vel2.x = point.x - point.y;
        vel2.y = -(point.x + point.y) / 2;
        return vel2;
    }


}
