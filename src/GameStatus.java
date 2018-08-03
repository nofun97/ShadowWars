/**
 * The type Game status acts as game status checker.
 * @author novan
 */
public class GameStatus {
    /**
     * The state MAIN_MENU.
     */
    public static final int MAIN_MENU = 0;
    /**
     * The state LEVEL_SELECT.
     */
    public static final int LEVEL_SELECT = 1;
    /**
     * The state GAME_PLAYING.
     */
    public static final int GAME_PLAYING = 2;

    private int currentStatus;

    /**
     * Instantiates a new Game status.
     */
    public GameStatus(){
        this.currentStatus = MAIN_MENU;
    }

    /**
     * Gets current status.
     *
     * @return the current status
     */
    public int getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Sets current status.
     *
     * @param currentStatus the current status
     */
    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * to check if a status is a certain status.
     *
     * @param status the status
     * @return the boolean
     */
    public boolean status(int status){
        return this.currentStatus == status;
    }
}
