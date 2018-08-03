import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * The class of all sprites wraps players and enemies.
 * @author novan
 */
public abstract class ActionCharacters extends Sprite {
    /**
     * The constant PRECISION.
     */
    public static final float PRECISION = (float) 0.3;
    /**
     * The constant LASERBEAM_PATH.
     */
    public static final String LASERBEAM_PATH = "/assets/lazerbeam.png";

    /**
     * a variable to set time for anything that requires event period
     */
    private int timeRecord;
    private boolean active = false;
    private int maxShots = 25;
    private int shotCounter = 0;
    private int numberOfGuns = 1;
    private ArrayList<Laser> laser;
    private int shotDirection;
    private float laserSpeed;
    private String laserImagePath;

    /**
     * to check if a character is a shooter
     */
    private boolean shooter = false;

    /**
     * Instantiates a new Action characters.
     *
     * @param imageSource the image source
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @throws SlickException the slick exception
     */
    public ActionCharacters(String imageSource, float x, float y)
            throws SlickException{
        super(imageSource, x, y);
    }

    /**
     * update the characters should it be active
     * @param input the input
     * @param delta the time that has passed
     * @throws SlickException
     */
    @Override
    public void update(Input input, int delta) throws SlickException {
        if(isActive()){
            super.update(input, delta);
            if(isShooter()){
                for(int i = 0; i < laser.size(); i++){
                    this.laser.get(i).update(input, delta);
                }

            }
        }
    }

    /**
     * render the character
     */
    @Override
    public void render(){
        if(isActive()){
            super.render();

            // rendering laser if character is a shooter
            if(isShooter()){
                for (int i = 0; i < laser.size(); i++){
                    this.laser.get(i).render();
                }
            }
        }
    }

    /**
     * Shoot method.
     *
     * @throws SlickException the slick exception
     */
    public void shoot() throws SlickException{
        // resetting shotCounter if it exceeded the limit
        if (shotCounter >= maxShots - numberOfGuns){
            shotCounter = 0;
        }
        // adding laser
        for(int i = 0; i < numberOfGuns; i++){
            if (laser.size() >= maxShots){
                laser.set(shotCounter, new Laser(laserImagePath,
                        this, shotDirection));
            } else {
                laser.add(shotCounter, new Laser(laserImagePath,
                        this, shotDirection));
            }

            laser.get(shotCounter).setSpeed(laserSpeed);

            shotCounter++;
        }
    }

    /**
     * Moving the character to a certain point.
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param delta the delta
     */
    public void move(float x, float y, int delta){
        /**
         * updating to the point based on the character's position
         */
        if (getY() > y){
            updateY(UP, delta);
        } else if (getY() < y){
            updateY(DOWN, delta);
        }

        if (getX() > x){
            updateX(LEFT, delta);
        } else if (getX() < x){
            updateX(RIGHT, delta);
        }
    }

    /**
     * to check if a certain point is reached by certain precision.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean reached(float x, float y){
        return (Math.abs(getX() - x) <= PRECISION) &&
                (Math.abs(getY() - y) <= PRECISION);
    }

    /**
     * to check if a character has waited a certain time.
     *
     * @param waitingTime the waiting time
     * @return the boolean
     */
    public boolean finishedWait(int waitingTime){
        return World.worldTime >= timeRecord + waitingTime;
    }

    /**
     * Gets time record.
     *
     * @return the time record
     */
    public int getTimeRecord() {
        return timeRecord;
    }

    /**
     * Sets time record.
     *
     * @param timeRecord the time record
     */
    public void setTimeRecord(int timeRecord) {
        this.timeRecord = timeRecord;
    }

    /**
     * character active condition.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets max shots.
     *
     * @return the max shots
     */
    public int getMaxShots() {
        return maxShots;
    }

    /**
     * Sets max shots.
     *
     * @param maxShots the max shots
     */
    public void setMaxShots(int maxShots) {
        this.maxShots = maxShots;
    }

    /**
     * Gets shot counter.
     *
     * @return the shot counter
     */
    public int getShotCounter() {
        return shotCounter;
    }

    /**
     * Sets shot counter.
     *
     * @param shotCounter the shot counter
     */
    public void setShotCounter(int shotCounter) {
        this.shotCounter = shotCounter;
    }

    /**
     * Gets number of guns.
     *
     * @return the number of guns
     */
    public int getNumberOfGuns() {
        return numberOfGuns;
    }

    /**
     * Sets number of guns.
     *
     * @param numberOfGuns the number of guns
     */
    public void setNumberOfGuns(int numberOfGuns) {
        this.numberOfGuns = numberOfGuns;
    }

    /**
     * Gets laser.
     *
     * @return the laser
     */
    public ArrayList<Laser> getLaser() {
        return laser;
    }

    /**
     * Sets laser.
     *
     * @param laser the laser
     */
    public void setLaser(ArrayList<Laser> laser) {
        this.laser = laser;
    }

    /**
     * Gets shot direction.
     *
     * @return the shot direction
     */
    public int getShotDirection() {
        return shotDirection;
    }

    /**
     * Sets shot direction.
     *
     * @param shotDirection the shot direction
     */
    public void setShotDirection(int shotDirection) {
        this.shotDirection = shotDirection;
    }

    /**
     * Gets laser speed.
     *
     * @return the laser speed
     */
    public float getLaserSpeed() {
        return laserSpeed;
    }

    /**
     * Sets laser speed.
     *
     * @param laserSpeed the laser speed
     */
    public void setLaserSpeed(float laserSpeed) {
        this.laserSpeed = laserSpeed;
    }

    /**
     * Gets laser image path.
     *
     * @return the laser image path
     */
    public String getLaserImagePath() {
        return laserImagePath;
    }

    /**
     * Sets laser image path.
     *
     * @param laserImagePath the laser image path
     */
    public void setLaserImagePath(String laserImagePath) {
        this.laserImagePath = laserImagePath;
    }

    /**
     * Is shooter boolean.
     *
     * @return the boolean
     */
    public boolean isShooter() {
        return shooter;
    }

    /**
     * Sets if a character is a shooter.
     *
     * @param shooter the shooter
     */
    public void setShooter(boolean shooter) {
        this.shooter = shooter;
    }
}
