import org.newdawn.slick.*;

import java.io.IOException;
import java.util.*;

/**
 * A class representing each level and its rules.
 * @author novan
 */
public class World {
    /**
     * The constant MESSAGE_SIZE.
     */
    public static final int MESSAGE_SIZE = 100;
    /**
     * The constant MESSAGE_OFFSET.
     */
    public static final int MESSAGE_OFFSET = -50;
    private static final int PLAYER_INIT_LOCATION_X = 480;
    private static final int PLAYER_INIT_LOCATION_Y = 688;

    /**
     * power up drop rate
     */
    private static final int POWER_UP_DROP_RATE = 5;
    private static final int POWER_UP_MAX_DROP_VALUE = 100;
    private static final int POWER_UP_MIN_DROP_VALUE = 1;
    /**
     * the constant WAIT_DURATION duration of waiting for each level
     */
    private static final int WAIT_DURATION = 5000;
    /**
     * the constant for the world to run before win can be achieved
     */
    private static final int TIME_OFFSET = 2000;
    private static App app;
    /**
     * The constant worldTime. Represents how much time has gone in the level
     * made public so that it can be accessed anywhere without passing the
     * World variable everywhere
     */
    public static int worldTime;
    /**
     * duration for how long the escape message to be shown
     */
    private static int escapeMessageDuration = 5000;

    private Background background;
    private Player player;
    private ArrayList<PowerUps> powerUps;
    private ArrayList<Enemy> aliens;
    private String escapeText = "Press ESC to go back to main menu";
    private Graphics message;
    /**
     * default level to be loaded
     */
    private int level = 1;
    /**
     * to record how long after game finishes
     */
    private int waitTime = 0;
    /**
     * counting how many bosses in the world
     */
    private int bossCounter;
    /**
     * counting how many dead bosses
     */
    private int deadBossCounter;
    /**
     * condition to check winning
     */
    private boolean win = false;
    private boolean allEnemiesDead = false;

    /**
     * Instantiates a new World.
     *
     * @param level the level to be loaded
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public World(int level) throws SlickException, IOException{
        this.level = level;
        worldTime = 0;
        background = new Background();
        player = new Player("/assets/spaceship.png",
                PLAYER_INIT_LOCATION_X, PLAYER_INIT_LOCATION_Y);
        powerUps = new ArrayList<>();
        aliens = new ArrayList<>();
        message = new Graphics(MESSAGE_SIZE, MESSAGE_SIZE);
        bossCounter = 0;
        deadBossCounter = 0;

        // load the level files based on the argument
        WaveFileHandler.initializeCommands(this,
                "./assets/levels/waves" +
                        Integer.toString(level)+ ".txt");
    }

    /**
     * Instantiates a new World.
     *
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public World() throws SlickException, IOException {

		// initialise the sprites and initial values
        worldTime = 0;
        background = new Background();
        player = new Player("/assets/spaceship.png", PLAYER_INIT_LOCATION_X, PLAYER_INIT_LOCATION_Y);
        powerUps = new ArrayList<>();
        aliens = new ArrayList<>();
        message = new Graphics(MESSAGE_SIZE, MESSAGE_SIZE);
        WaveFileHandler.initializeCommands(this, "./assets/levels/waves1.txt");
    }

    /**
     * Update the whole sprites.
     *
     * @param input the input
     * @param delta the delta to show how much time has passed
     * @throws SlickException the slick exception
     * @throws IOException    the io exception
     */
    public void update(Input input, int delta) throws SlickException, IOException {
	    worldTime += delta;
		// Update all of the sprites in the game
        background.update(delta);
        player.update(input, delta);

        // checking if player died
        if(player.isDead()){
            waitTime += delta;
            if (waitTime >= WAIT_DURATION){

                getApp().setCurrentStatus(GameStatus.MAIN_MENU);
            }
        }

        // press escape to go back to main menu
        if(input.isKeyPressed(Input.KEY_ESCAPE)){
            getApp().setCurrentStatus(GameStatus.MAIN_MENU);
        }

        // to see if all enemies died
        allEnemiesDead = allEnemiesDead();

        for(Enemy alien: aliens){
            alien.update(input, delta);

            // checking collision between player and aliens
            if (!player.isShieldUp() && player.intersects(alien) &&
                    !alien.getCollided()){
                player.contactSprite(alien);
            }

            /**
             * checking if all bosses or enemies died and advancing to next
             * level
             */
            if (alien instanceof Boss || allEnemiesDead){
                if(alien.getCollided()){
                    deadBossCounter++;
                } else {
                    deadBossCounter = 0;
                }

                if ((deadBossCounter == bossCounter || allEnemiesDead) && World.worldTime >= TIME_OFFSET){
                    win = true;
                    waitTime += delta;
                    if (waitTime >= WAIT_DURATION){
                        getApp().loadLevel(level + 1);
                    }
                }
            }

            /**
             * if alien is a shooter, it will check collision between its lasers
             * and the player
             */
            if (alien instanceof ShootingAliens){
                ArrayList<Laser> laserList = alien.getLaser();
                for (int i = 0; i < laserList.size(); ++i) {
                    if (laserList.get(i).isActive() && !player.isShieldUp()
                            && player.intersects(laserList.get(i))) {
                        player.contactSprite(alien.getLaser().get(i));

                        /**
                         * stunning player if it is a laserbeam
                         */
                        if (laserList.get(i).isLaserBeam() &&
                                !player.isStunned()){
                            player.setStunned(true);
                            player.setTimeRecord(World.worldTime
                                    + Player.STUN_DURATION);
                        } else if (player.isStunned() && worldTime >
                                player.getTimeRecord()){
                            player.setStunned(false);
                        }
                    }
                }
            }


        }

        /**
         * should collision between aliens and laser happen, both sprite and
         * their boundingBoxes will be removed
         */
        for (Laser laser: player.getLaser()){
            if (laser.inScreen()){

                for(Enemy alien: aliens){
                    if(!laser.getCollided() && !alien.getCollided() &&
                            laser.intersects(alien)){
                        alien.contactSprite(laser);

                        /**
                         * should alien died, its score will be added
                         */
                        if(alien.getCollided()){
                            player.setScore(player.getScore() +
                                    alien.getValue());
                            powerUpDrop(alien);
                        }

                    }
                }
            }
        }


        /**
         * checking collision between powerups and player
         */
        for(PowerUps powerUp: powerUps){
            powerUp.update(input, delta);
            if (player.intersects(powerUp) && !powerUp.getCollided()){

                powerUp.contactSprite(player);
            }
        }

	}

    /**
     * Render every sprites in the game.
     */
    public void render(){
        // Draw all of the sprites in the game
        background.render();

        if (worldTime <= escapeMessageDuration){
            message.drawString(escapeText, 0, 0);
        }
        for(Enemy enemy: aliens){
            enemy.render();
        }

        for(PowerUps powerUp: powerUps){
            powerUp.render();

            /**
             * activating power until period finishes
             */
            if(powerUp.isActive() && powerUp.getCollided()){
                powerUp.addPower(player);
            }
        }

        player.render();

        /**
         * game ends between these two conditions
         */
        if (win){
            message.drawString("YOU WIN",  App.SCREEN_WIDTH / 2 +
                    MESSAGE_OFFSET, App.SCREEN_HEIGHT / 2);
        } else if (player.isDead()){
            message.drawString("GAME OVER", App.SCREEN_WIDTH / 2 +
                    MESSAGE_OFFSET, App.SCREEN_HEIGHT / 2);
        }


    }

    /**
     * Gets all aliens.
     *
     * @return the aliens
     */
    public ArrayList<Enemy> getAliens() {
        return aliens;
    }

    /**
     * Power up drop with the certain drop rate.
     *
     * @param alien the alien that died so powerups drop in the alien location
     * @throws SlickException the slick exception
     */
    public void powerUpDrop(Sprite alien) throws SlickException{
	    Random rand = new Random();
	    int value = rand.nextInt(POWER_UP_MAX_DROP_VALUE) + POWER_UP_MIN_DROP_VALUE;

	    if (value <= POWER_UP_DROP_RATE){
	       powerUps.add(PowerUps.spawnPowerUp(alien));
        }
    }

    /**
     * Gets app.
     *
     * @return the app
     */
    public static App getApp() {
        return app;
    }


    /**
     * Gets boss counter.
     *
     * @return the boss counter
     */
    public int getBossCounter() {
        return bossCounter;
    }

    /**
     * Sets boss counter.
     *
     * @param bossCounter the boss counter
     */
    public void setBossCounter(int bossCounter) {
        this.bossCounter = bossCounter;
    }

    /**
     * Gets power ups.
     *
     * @return the power ups
     */
    public ArrayList<PowerUps> getPowerUps() {
        return powerUps;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * to check if all enemies in the world died.
     *
     * @return the boolean
     */
    public boolean allEnemiesDead(){
        for (Enemy alien: aliens){
            if (alien.inScreen()){
                return false;
            }
        }
        return true;
    }

    /**
     * sets the app to this world
     * @param app the wrapper class for all game functions
     */
    public static void setApp(App app) {
        World.app = app;
    }
}