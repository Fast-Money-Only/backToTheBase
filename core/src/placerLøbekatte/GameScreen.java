package placerLøbekatte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Drop game;
    Texture dropImage;
    Texture bucketImage;
    Texture traverseCat;
    Texture background;
    Sound dropSound;
    Sound ironSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle staticTraverseCat;

    MovingTravCat mtc;
    Array<Rectangle> traversingCats;
    Array<Rectangle> stackedCats;
    long lastDropTime;
    int gameState;

    int successfulStacks;
    int failedAttempts;
    boolean gameWon;
    boolean gameOver;

    long lastSpacePressTime;
    final long SPACE_COOLDOWN = 1000000000;

    public GameScreen(final Drop game) {
        this.game = game;

        dropImage = new Texture(Gdx.files.internal("placerLøbekat/droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("placerLøbekat/bucket.png"));
        traverseCat = new Texture(Gdx.files.internal("placerLøbekat/Løbekat.png"));
        background = new Texture(Gdx.files.internal("placerLøbekat/backgroundpng.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("placerLøbekat/drop.wav"));
        ironSound = Gdx.audio.newSound(Gdx.files.internal("placerLøbekat/iron.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("placerLøbekat/rain.mp3"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        staticTraverseCat = new Rectangle();
        staticTraverseCat.x = 800 / 2 - 64 / 2;
        staticTraverseCat.y = 0;
        staticTraverseCat.width = 64;
        staticTraverseCat.height = 64;

        traversingCats = new Array<Rectangle>();
        stackedCats = new Array<Rectangle>();
        stackedCats.add(staticTraverseCat);

        Rectangle movingCatRect = new Rectangle(50, 540, 64, 64);
        mtc = new MovingTravCat(movingCatRect, 150);

        successfulStacks = 0;
        failedAttempts = 0;
        gameWon = false;
        gameOver = false;

        lastSpacePressTime = TimeUtils.nanoTime() - SPACE_COOLDOWN;
    }

    public MovingTravCat createMovingRectangle() {
        return mtc;
    }

    private void spawnTraverseCats() {
        Rectangle traverseCat = new Rectangle();
        traverseCat.x = mtc.getRect().x;
        traverseCat.y = 600;
        traverseCat.width = 64;
        traverseCat.height = 64;
        traversingCats.add(traverseCat);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 12.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        mtc.update(delta);

        game.batch.begin();
        game.batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.font.draw(game.batch, "Tryk på mellemrumstasten for at placere en løbekat", 0, 480);
        game.font.draw(game.batch, "Fejlede placeringer: " + failedAttempts,0, 420);
        game.batch.draw(traverseCat, staticTraverseCat.x, staticTraverseCat.y, staticTraverseCat.width, staticTraverseCat.height);
        game.batch.draw(traverseCat, mtc.getRect().x, mtc.getRect().y, mtc.getRect().width, mtc.getRect().height);

        for (Rectangle raindrop : traversingCats) {
            game.batch.draw(traverseCat, raindrop.x, raindrop.y, staticTraverseCat.width, staticTraverseCat.height);
        }

        for (Rectangle stackedCat : stackedCats) {
            game.batch.draw(traverseCat, stackedCat.x, stackedCat.y, stackedCat.width, stackedCat.height);
        }

        if (gameWon) {
            game.font.getData().setScale(2);
            game.font.draw(game.batch, "You Win!", 350, 300);
            game.font.getData().setScale(1);
        } else if (gameOver) {
            game.font.getData().setScale(2);
            game.font.draw(game.batch, "Game Over!", 350, 300);
            game.font.getData().setScale(1);
        }

        game.batch.end();

        if (!gameWon && !gameOver) {
            if (Gdx.input.isKeyJustPressed(Keys.SPACE) && TimeUtils.nanoTime() - lastSpacePressTime >= SPACE_COOLDOWN) {
                spawnTraverseCats();
                lastSpacePressTime = TimeUtils.nanoTime();
            }

            Iterator<Rectangle> iter = traversingCats.iterator();
            while (iter.hasNext()) {
                Rectangle droppingTraverse = iter.next();
                droppingTraverse.y -= 150 * Gdx.graphics.getDeltaTime();

                if (droppingTraverse.y + 64 < 0) {
                    iter.remove();
                    failedAttempts++;
                    if (failedAttempts >= 3) {
                        gameOver = true;
                    }
                }

                for (Rectangle stackedCat : stackedCats) {
                    if (droppingTraverse.overlaps(stackedCat)) {
                        ironSound.play();
                        droppingTraverse.y = stackedCat.y + 64;
                        stackedCats.add(droppingTraverse);
                        iter.remove();
                        successfulStacks++;
                        if (successfulStacks >= 7) {
                            gameWon = true;
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        rainMusic.play();
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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        traverseCat.dispose();
        background.dispose();
    }
}
