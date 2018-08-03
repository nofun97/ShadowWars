import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.IOException;

/**
 * The Main menu.
 * @author novan
 */
public class MainMenu {
    private Image titleImage;
    private LevelMenuButton levelMenuButton;
    private PlayButton playButton;

    /**
     * Instantiates a new Main menu.
     *
     * @throws SlickException the slick exception
     */
    public MainMenu() throws SlickException {
        titleImage = new Image("/assets/UIAssets/Logo.png");
        playButton = new PlayButton(App.SCREEN_WIDTH/2, App.SCREEN_WIDTH/2);
        playButton.setX(playButton.getX() - playButton.getWidth()/2);
        playButton.setY(playButton.getY() - 20);
        levelMenuButton = new LevelMenuButton(App.SCREEN_WIDTH/2, App.SCREEN_WIDTH/2);
        levelMenuButton.setX(levelMenuButton.getX() - levelMenuButton.getWidth()/2);
        levelMenuButton.setY(levelMenuButton.getY() + 100);
    }

    /**
     * Update the buttons.
     *
     * @param input the input
     * @param delta the delta
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public void update(Input input, int delta) throws SlickException, IOException {
        levelMenuButton.setActive(true);
        playButton.setActive(true);
        playButton.update(input, delta);
        levelMenuButton.update(input, delta);
    }

    /**
     * Render the buttons and the title.
     */
    public void render(){
        titleImage.draw((App.SCREEN_WIDTH - titleImage.getWidth())/2 , 50);
        playButton.render();
        levelMenuButton.render();
    }
}
