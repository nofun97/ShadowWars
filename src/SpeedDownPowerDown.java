import org.newdawn.slick.SlickException;

public class SpeedDownPowerDown extends PowerUps {
    private static String imageSource = "assets/PowerUps/speeddown-powerup.png";
    private static float NORMAL_SPEED = (float) 0.5;
    private static float LOW_SPEED = (float) 0.25;
    /**
     * Instantiates a new Power ups.
     *
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public SpeedDownPowerDown(float x, float y) throws SlickException {
        super(imageSource, x, y);
    }

    @Override
    public void addPower(Player player) {
        if(World.worldTime <= getTimeLimit()){
            player.setSpeed(LOW_SPEED);
        } else {
            player.setSpeed(NORMAL_SPEED);
            setActive(false);
        }
    }
}
