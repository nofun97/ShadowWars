import org.newdawn.slick.SlickException;

/**
 * The button for level select menu.
 * @author novan
 */
public class LevelMenuButton extends UIElements{
    private String text = "Level\nSelect";

    /**
     * Instantiates a new Level menu button.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws SlickException the slick exception
     */
    public LevelMenuButton(float x, float y) throws SlickException {
        super(x, y);
        setImage(UIElements.buttonImageSource);
        setText(text);
    }

    /**
     * mouse click event
     * setting status to level select
     */
    @Override
    public void onMouseClick() {
        getApp().setCurrentStatus(GameStatus.LEVEL_SELECT);
        setActive(false);
    }
}
