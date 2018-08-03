import org.newdawn.slick.SlickException;

public class SpeedUpPowerUp extends PowerUps {
    private static String imageSource = "assets/PowerUps/speedup-powerup.png";
    private static float NORMAL_SPEED = (float) 0.5;
    private static float POWER_SPEED = (float) 0.75;
    /**
     * Instantiates a new Power ups.
     *
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public SpeedUpPowerUp(float x, float y) throws SlickException {
        super(imageSource, x, y);
    }

    /**
     * making the player faster
     * @param player the player
     */
    @Override
    public void addPower(Player player) {
        if(World.worldTime <= getTimeLimit()){
            player.setSpeed(POWER_SPEED);
        } else {
            player.setSpeed(NORMAL_SPEED);
            setActive(false);
        }
    }
}
