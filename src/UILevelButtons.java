import org.newdawn.slick.SlickException;
import java.io.IOException;

/**
 * The type UI level select buttons.
 * @author novan
 */
public class UILevelButtons extends UIElements{
    private int level;
    private String levelName;

    /**
     * Instantiates a new Ui level buttons.
     *
     * @param x     the x
     * @param y     the y
     * @param level the level
     * @throws SlickException the slick exception
     */
    public UILevelButtons(float x, float y, int level) throws SlickException{
        super(x, y);
        setImage(UIElements.buttonImageSource);
        this.level = level;
        this.levelName = "Level " + Integer.toString(level);
        setText(levelName);
        setActive(false);
    }


    /**
     * loading certain levels based on the button
     * @throws SlickException
     * @throws IOException
     */
    @Override
    public void onMouseClick() throws SlickException, IOException {
        getApp().setCurrentStatus(GameStatus.GAME_PLAYING);
        getApp().loadLevel(level);

    }

}
