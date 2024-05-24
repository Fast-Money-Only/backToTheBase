package balancerZeppeliner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    final BalancerZeppeliner game;
    OrthographicCamera camera;

    Viewport viewport;

    Zeppeliner zeppeliner;

    Weight person, bomb, water, fuel, food;
    private Weight selectedWeight, newWeight;

    Background background;

    Texture zeppelinImg, personImg, bombImg, waterImg, fuelImg, foodImg, BGimg;

    Array<Weight> vægtObjekter;

    public GameScreen(BalancerZeppeliner game) {
        this.game = game;

        BGimg = new Texture(Gdx.files.internal("skyBG.jpg"));
        zeppelinImg = new Texture(Gdx.files.internal("zeppelin.png"));
        personImg = new Texture(Gdx.files.internal("person.png"));
        bombImg = new Texture(Gdx.files.internal("bombe.png"));
        waterImg = new Texture(Gdx.files.internal("water.png"));
        fuelImg = new Texture(Gdx.files.internal("fuel.png"));
        foodImg = new Texture(Gdx.files.internal("food.png"));


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1792, 1344);

        background = new Background(BGimg,0,camera.viewportHeight);


        zeppeliner = new Zeppeliner(zeppelinImg, 450, 500 );
        person = new Weight(70, personImg, 100, 10);
        bomb = new Weight(10, bombImg, 300, 10);
        water = new Weight(10, waterImg, 650, 10);
        fuel = new Weight(30, fuelImg, 900, 10);
        food = new Weight(2, foodImg, 1300, 10);

        vægtObjekter = new Array<>();
        vægtObjekter.add(person);
        vægtObjekter.add(bomb);
        vægtObjekter.add(water);
        vægtObjekter.add(fuel);
        vægtObjekter.add(food);

        selectedWeight = null;
        newWeight = null;
    }

    @Override
    public void show() {

    }

    public void rotateShip(){

    }


    public void grabWeight(){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            double posY = touchPos.y;
            double posX = touchPos.x;

            selectedWeight = null;

            for (Weight weight : vægtObjekter) {
                if (posY > weight.getY() && posY < (weight.getY() + weight.getSize().height)
                        && posX > weight.getX() && posX < (weight.getX() + weight.getSize().width)) {
                    selectedWeight = weight;
                    newWeight = new Weight(weight.getVægt(), weight.getTexture(), (float) weight.getX(), (float) weight.getY());
                    vægtObjekter.add(newWeight);
                    break;
                }
            }
        }
    }




    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(BGimg, 0, 0);
        game.batch.draw(zeppeliner.getTexture(), (float) zeppeliner.getX(), (float) zeppeliner.getY());
        for (Weight w : vægtObjekter){
            game.batch.draw(w.getTexture(), (float) w.getX(), (float) w.getY());
        }


        game.batch.end();

        grabWeight();
        if (newWeight != null) {
            handleWeightMovementAndRotation();
        }

    }

    private void handleWeightMovementAndRotation() {
        // If the screen is touched
        if (Gdx.input.isTouched()) {
            // Get the touch position
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // Move the new weight
            newWeight.setX(touchPos.x - newWeight.getSize().width / 2);
            newWeight.setY(touchPos.y - newWeight.getSize().height / 2);
        } else {
            // Check if the new weight is on the zeppeliner
            if (newWeight.getX() > zeppeliner.getX() && newWeight.getX() < (zeppeliner.getX() + zeppeliner.getTexture().getWidth())
                    && newWeight.getY() > zeppeliner.getY() && newWeight.getY() < (zeppeliner.getY() + zeppeliner.getTexture().getHeight())) {

                // Calculate the rotation based on the weight's position relative to the zeppeliner's center
                float zeppelinerCenterX = (float) (zeppeliner.getX() + zeppeliner.getTexture().getWidth() / 2);
                float rotationFactor = newWeight.getVægt(); // Adjust this value to control the rotation amount

                if (newWeight.getX() < zeppelinerCenterX) {
                    zeppeliner.rotate(rotationFactor);
                } else {
                    zeppeliner.rotate(-rotationFactor);
                }
            }

            // Reset the new weight to null after handling its movement and rotation
            newWeight = null;
        }
    }

    @Override
    public void resize(int width, int height) {

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
