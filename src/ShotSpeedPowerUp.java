import org.newdawn.slick.SlickException;

/**
 * The type Shot speed power up.
 * @author novan
 */
public class ShotSpeedPowerUp extends PowerUps {
    private static String imageSource = "assets/PowerUps/shotspeed-powerup.png";
    private static final int PLAYER_NORMAL_SHOT_SPEED = 350;
    private static final int PLAYER_HIGH_SHOT_SPEED = 150;

    /**
     * Instantiates a new Shot speed power up.
     *
     * @param x the x
     * @param y the y
     * @throws SlickException the slick exception
     */
    public ShotSpeedPowerUp (float x, float y) throws SlickException {
        super(imageSource, x, y);
    }

    /**
     * shortening the player shot period
     * @param player the player
     */
    @Override
    public void addPower(Player player) {
        if(World.worldTime <= getTimeLimit()){
            player.setTimePerShot(PLAYER_HIGH_SHOT_SPEED);
        } else {
            player.setTimePerShot(PLAYER_NORMAL_SHOT_SPEED);
            setActive(false);
        }
    }
}
