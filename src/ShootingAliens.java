import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * The type Shooting aliens.
 * @author novan
 */
public abstract class ShootingAliens extends Enemy{
    private static final float LASER_SPEED = (float) 0.7;
    private static final float SPEED = (float) 0.2;
    private float yCoordinate;
    private float xCoordinate;

    /**
     * Instantiates a new Shooting aliens.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @throws SlickException the slick exception
     */
    public ShootingAliens(String imageSource, float x, float y)
            throws SlickException{
        super(imageSource, x, y);
        setLaser(new ArrayList<>());
        setShooter(true);
        setSpeed(SPEED);
        setShotDirection(DOWN);
        setLaserSpeed(LASER_SPEED);
        setLaserImagePath("assets/enemy-shot.png");
        setBoundary(false);
    }

    /**
     * render if its active
     */
    @Override
    public void render(){
        if (isActive()){
            super.render();
        }
    }

    /**
     * offsets the laser and shoots them
     * @throws SlickException
     */
    @Override
    public void shoot() throws SlickException{
        super.shoot();
        for (int i = getShotCounter() - getNumberOfGuns(); i < getShotCounter();
             i++){
            this.getLaser().get(i).offsetY(this.getLaser().get(i).getHeight()
                    + this.getHeight());
        }
    }

    /**
     * Gets coordinate.
     *
     * @return the coordinate
     */
    public float getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets coordinate.
     *
     * @param yCoordinate the y coordinate
     */
    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets x coordinate.
     *
     * @return the coordinate
     */
    public float getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Sets y coordinate.
     *
     * @param xCoordinate the x coordinate
     */
    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * shoot with a certain amount of times.
     *
     * @param delta the delta
     * @throws SlickException the slick exception
     */
    public void durationalShoot(int delta) throws SlickException{
        setTimeSinceShot(getTimeSinceShot() + delta);
        if (World.worldTime <= getTimeRecord() && getTimeSinceShot() >=
                getShootPeriod()) {
            setTimeSinceShot(0);
            shoot();
        } else if (World.worldTime > getTimeRecord()) {
            setFinishedShoot(true);
        }
    }
}
