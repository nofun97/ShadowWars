import org.newdawn.slick.SlickException;

public class SineShooter extends ShootingAliens {
    /**
     * sine movement values
     */
    private int amplitude = 48;
    private int period = 1500;
    private int delay = 2;
    private int time = 0;
    private float initialX;
    private static int shootPeriod = 3500;
    /**
     * Instantiates a new Shooting aliens.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public SineShooter(String imageSource, float x, float y) throws SlickException {
        super(imageSource, x, y);
        initialX = getX();
        setShootPeriod(shootPeriod);
    }

    /**
     * behaviour for this alien
     * moves in a sine wave and shooting
     * @param delta the delta
     * @throws SlickException
     */
    @Override
    public void setBehaviour(int delta) throws SlickException {
        if(getBehaviourNumber() == 0){
            time += delta;
            float offsetX = (float) (amplitude * Math.sin((float) 2 *
                    Math.PI/period * (time - delay)));
            setX(initialX + offsetX);
            setTimeSinceShot(getTimeSinceShot() + delta);
            if (getTimeSinceShot() >= getShootPeriod()){
                setTimeSinceShot(0);
                shoot();
            }
            updateY(DOWN, delta);
        }
    }
}
