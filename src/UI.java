import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.IOException;

/**
 * The UI.
 * @author novan
 */
public class UI {
    private Background background;
    private LevelSelectMenu levelSelectMenu;
    private MainMenu mainMenu;
    private App app;

    /**
     * Instantiates a new Ui.
     *
     * @param app the app
     * @throws SlickException the slick exception
     */
    public UI(App app) throws SlickException {
        background = new Background();
        this.app = app;
        levelSelectMenu = new LevelSelectMenu();
        mainMenu = new MainMenu();
    }

    /**
     * Updating the ui based on the game state.
     *
     * @param input the input
     * @param delta the delta
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public void update(Input input, int delta) throws SlickException,
            IOException {
        background.update(delta);
        if (app.getCurrentStatus().status(GameStatus.MAIN_MENU)){
            mainMenu.update(input, delta);
        } else if (app.getCurrentStatus().status(GameStatus.LEVEL_SELECT)){
            levelSelectMenu.update(input, delta);
        }

    }

    /**
     * Rendering based on the game state.
     */
    public void render(){
        background.render();
        if (app.getCurrentStatus().status(GameStatus.MAIN_MENU)){
            mainMenu.render();
        } else if (app.getCurrentStatus().status(GameStatus.LEVEL_SELECT)){
            levelSelectMenu.render();
        }
    }
}
