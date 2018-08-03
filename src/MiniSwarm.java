import org.newdawn.slick.SlickException;

/**
 * The type Mini swarm.
 * @author novan
 */
public class MiniSwarm extends Enemy{
    /**
     * placemen multiplier
     */
    private static final int[][] MULTIPLIER =
            new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    private static int VALUE = 50;
    private static float SPEED = (float) 0.2;
    /**
     * movement values
     */
    private static final int RADIUS = 35;
    private static int period = 1500;
    private static int delay = 2;
    private int time = 0;
    private float initialX;
    private float yCoordinate;

    /**
     * Instantiates a new Mini swarm.
     *
     * @param imageSource the image source
     * @param x           the x
     * @param y           the y
     * @param type        the type
     * @param yCoordinate the y coordinate
     * @throws SlickException the slick exception
     */
    public MiniSwarm(String imageSource, float x, float y, int type,
                     float yCoordinate) throws SlickException {
        super(imageSource, x, y);
        setX(getX() + MULTIPLIER[type][0] * RADIUS);
        setY(getY() + MULTIPLIER[type][1] * RADIUS);
        setValue(VALUE);
        setSpeed(SPEED);
        initialX = getX();
        this.yCoordinate = yCoordinate;
    }

    /**
     * behaviour set
     * @param delta the delta
     */
    @Override
    public void setBehaviour(int delta) {

        if (getBehaviourNumber() == 0){
            /**
             * moving down until certain point is reached
             */
            updateY(DOWN, delta);
            if (getY() > yCoordinate){
                setBehaviourNumber(getBehaviourNumber() + 1);
            }
        } else if (getBehaviourNumber() == 1){
            /**
             * moving in a circle
             */
            time += delta;
            float offsetX = (float) (RADIUS * Math.sin((float) Math.PI/period
                    * (time - delay)));
            float offsetY = (float) (RADIUS * Math.cos((float) Math.PI/period
                    * (time - delay)));
            setY(yCoordinate + offsetX);
            setX(initialX + offsetY);
        }
    }
}
