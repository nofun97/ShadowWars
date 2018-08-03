/**
 * Sample Project for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 */

import org.newdawn.slick.*;

import java.io.IOException;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 * modified by novan to add user interface function
 */
public class App extends BasicGame {
 	/** screen width, in pixels */
    public static final int SCREEN_WIDTH = 1024;
    
    /** screen height, in pixels */
    public static final int SCREEN_HEIGHT = 768;

    public static final int SPEED_MULTIPLIER = 5;

    private static Sound gameSong;
    private World world;
    private UI ui;
    private GameStatus currentStatus;

    public App() {
        super("Shadow Wars");
        currentStatus = new GameStatus();
        UIElements.setApp(this);
        World.setApp(this);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        /**
         * creating ui
         */
        ui = new UI(this);
        try{
            gameSong = new Sound("/assets/audio/game.wav");
        } catch (Exception e){
            gameSong = null;
        }
        if(gameSong != null) gameSong.loop();
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    		throws SlickException {

        // Get data about the current input (keyboard state).
        Input input = gc.getInput();

        // speeding up the game
        if (input.isKeyDown(Input.KEY_S)){
            delta *= SPEED_MULTIPLIER;
        }

        /**
         * playing the game if the status is playing status
         */
        if (world != null && currentStatus.status(GameStatus.GAME_PLAYING)){
            try {
                world.update(input, delta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * showing the ui if a certain status is set
         */
        if(currentStatus.status(GameStatus.MAIN_MENU) ||
                currentStatus.status(GameStatus.LEVEL_SELECT)){
            try {
                ui.update(input, delta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g) {

        /**
         * rendering based on the gamestatus
         */
        if (world != null && currentStatus.status(GameStatus.GAME_PLAYING)){
            world.render();
        } else if (currentStatus.status(GameStatus.MAIN_MENU) ||
                currentStatus.status(GameStatus.LEVEL_SELECT)){
            ui.render();
        }

    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    		throws SlickException {
        AppGameContainer app = new AppGameContainer(new App());
        app.setShowFPS(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }


    /**
     * Load level based on the argumens.
     *
     * @param level the level
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public void loadLevel(int level) throws SlickException, IOException {
        world = new World(level);
    }

    /**
     * Load the default level.
     *
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public void loadLevel() throws SlickException, IOException {
        world = new World();
    }

    /**
     * Gets game's current status.
     *
     * @return the current status
     */
    public GameStatus getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Sets game's current status.
     *
     * @param currentStatus the current status
     */
    public void setCurrentStatus(int currentStatus) {
        this.currentStatus.setCurrentStatus(currentStatus);
    }
}