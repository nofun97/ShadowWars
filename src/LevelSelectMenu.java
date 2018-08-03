import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Menu for level select.
 * @author novan
 *
 */
public class LevelSelectMenu {
    /**
     * The constant MAX_LEVELS.
     */
    public static final int MAX_LEVELS = 60;
    /**
     * The constant LEVEL_BUTTON_DELTA.
     */
    public static final int LEVEL_BUTTON_DELTA = 10;
    /**
     * The constant how many levels shown horizontal.
     */
    public static final int LEVEL_HORIZONTAL = 3;
    /**
     * The constant how many levels shown vertical.
     */
    public static final int LEVEL_VERTICAL = 4;
    /**
     * The constant LEVELS_PER_PAGE.
     */
    public static final int LEVELS_PER_PAGE = LEVEL_HORIZONTAL * LEVEL_VERTICAL;


    private Graphics message;
    private String text;
    private int startLevelInPage = 0;
    private ArrayList<UIElements> buttons;


    /**
     * Instantiates a new Level select menu.
     *
     * @throws SlickException the slick exception
     */
    public LevelSelectMenu() throws SlickException {
        buttons = new ArrayList<>();

        // message to render
        text = "Use the arrow keys to navigate through the level";
        message = new Graphics(World.MESSAGE_SIZE, World.MESSAGE_SIZE);

        // creating the level buttons
        int l = 0;
        for (int i = 0; i < MAX_LEVELS; i+=LEVELS_PER_PAGE){
            for (int j = 0; j < LEVEL_HORIZONTAL; j++){
                for (int k = 0; k < LEVEL_VERTICAL; k++){
                    if(buttons.size() >= MAX_LEVELS){
                        break;
                    }
                    buttons.add(new UILevelButtons
                            (200, 150, l + 1));
                    l++;
                    UILevelButtons currentButton =
                            (UILevelButtons) buttons.get(buttons.size() - 1);
                    currentButton.setX(currentButton.getX() +
                            (currentButton.getWidth() + LEVEL_BUTTON_DELTA) *
                                    j);
                    currentButton.setY(currentButton.getY() +
                            (currentButton.getHeight() + LEVEL_BUTTON_DELTA)
                                    * k);
                }
            }
        }
    }

    /**
     * Update the buttons
     *
     * @param input the input
     * @param delta the delta
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public void update(Input input, int delta) throws SlickException,
            IOException {

        // navigation
        if (input.isKeyPressed(Input.KEY_LEFT)){
            if (startLevelInPage > 0){
                startLevelInPage -= LEVELS_PER_PAGE;
            }
        } else if (input.isKeyPressed(Input.KEY_RIGHT)){
            if (startLevelInPage < MAX_LEVELS - LEVELS_PER_PAGE){
                startLevelInPage += LEVELS_PER_PAGE;
            }
        }

        // resetting buttons
        for (int i = 0; i < MAX_LEVELS; i++){
            buttons.get(i).setActive(false);
        }

        // taking input for shown buttons
        for (int i = startLevelInPage; i < startLevelInPage +
                LEVELS_PER_PAGE; i++){
            buttons.get(i).setActive(true);
            buttons.get(i).update(input, delta);
        }
    }

    /**
     * Render the selected buttons and the message.
     */
    public void render(){
        for (int i = startLevelInPage; i < startLevelInPage +
                LEVELS_PER_PAGE; i++){
            buttons.get(i).render();
        }
        message.drawString(text, (float) 0.25 * App.SCREEN_WIDTH, 30);
    }

    /**
     * Gets start level in page.
     *
     * @return the start level in page
     */
    public int getStartLevelInPage() {
        return startLevelInPage;
    }

    /**
     * Sets start level in page.
     *
     * @param startLevelInPage the start level in page
     */
    public void setStartLevelInPage(int startLevelInPage) {
        this.startLevelInPage = startLevelInPage;
    }
}
