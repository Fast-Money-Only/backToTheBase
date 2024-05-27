package landZeppelin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameZep game;
    Texture zepImage;
    Texture background;
    OrthographicCamera camera;
    Zeppelin zeppelin;
    float windX;
    float windY;
    boolean gameWon = false;
    boolean gameLost = false;

    public GameScreen(final GameZep game) {
        this.game = game;


        zepImage = new Texture(Gdx.files.internal("landZep/zeppelin.png"));
        background = new Texture(Gdx.files.internal("landZep/zeplandbackground.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        zeppelin = new Zeppelin(400, 300, 192, 64, 60, 30, 30);

        windX = MathUtils.random(-4, 4);
        windY = 0;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (!gameWon && !gameLost) {
            boolean moveLeft = Gdx.input.isKeyPressed(Keys.LEFT);
            boolean moveRight = Gdx.input.isKeyPressed(Keys.RIGHT);
            boolean moveUp = Gdx.input.isKeyPressed(Keys.UP);
            boolean moveDown = Gdx.input.isKeyPressed(Keys.DOWN);

            zeppelin.setWind(windX, windY);
            zeppelin.update(delta, moveLeft, moveRight, moveUp, moveDown);


            if (zeppelin.getBounds().y < 10) {
                if (zeppelin.isLandingSuccessful()) {
                    gameWon = true;
                } else {
                    gameLost = true;
                }
            }
        }

        game.batch.begin();
        game.batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.draw(zepImage, zeppelin.getBounds().x, zeppelin.getBounds().y, zeppelin.getBounds().width, zeppelin.getBounds().height);
        game.font.draw(game.batch, "Vind hastighed: " + windX, 0, 480);
        game.font.draw(game.batch, "Zeppelin fart: " + zeppelin.getVelocity().len(), 0, 460);
        game.font.draw(game.batch, "Husk ikke lande med mere end 15 fart", 0, 420);


        if (gameWon) {
            game.font.draw(game.batch, "Landing successful!  Du vinder!", 300, 300);
        } else if (gameLost) {
            if (zeppelin.isLandingTooFast()) {
                game.font.draw(game.batch, "Landing fejlede! For hurtigt!", 300, 300);
            } else if (zeppelin.isLandingTooEarly()) {
                game.font.draw(game.batch, "Landing fejlede! For tidlig!", 300, 300);
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        zepImage.dispose();
        background.dispose();
    }
}
