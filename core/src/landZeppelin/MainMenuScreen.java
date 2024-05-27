package landZeppelin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameScreen;

public class MainMenuScreen implements Screen {

    final GameZep game;

    Texture background;

    OrthographicCamera camera;

    public MainMenuScreen(final GameZep game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 1024);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        background = new Texture(Gdx.files.internal("landZepSpil.png"));

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background,0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            //game.setScreen(new GameScreen(game));
            dispose();
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
