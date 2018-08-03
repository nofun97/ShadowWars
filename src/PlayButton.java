import org.newdawn.slick.SlickException;

import java.io.IOException;

/**
 * The type Play button.
 * @author novan
 */
public class PlayButton extends UIElements {
    /**
     * Instantiates a new Play button.
     *
     * @param x the x
     * @param y the y
     * @throws SlickException the slick exception
     */
    public PlayButton(float x, float y) throws SlickException {
        super(x, y);
        setText("Play");
        setImage(UIElements.buttonImageSource);
        setActive(true);
    }

    /**
     * on mouse click, it will load level 1
     * @throws SlickException
     * @throws IOException
     */
    @Override
    public void onMouseClick() throws SlickException, IOException {
        getApp().setCurrentStatus(GameStatus.GAME_PLAYING);
        getApp().loadLevel();
        setActive(false);
    }
}
