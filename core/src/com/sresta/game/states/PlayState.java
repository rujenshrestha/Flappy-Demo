package com.sresta.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sresta.game.FlappyDemo;
import com.sresta.game.sprites.Bird;
import com.sresta.game.sprites.Tube;

import java.awt.TextArea;

public class PlayState extends State {

    private static final int TUBE_SPACING = 125;//space between tubes
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Array<Tube> tubes;
    private Vector2 groundPos1;
    private Vector2 groundPos2;

    private int score;
    private String flappyBirdScore;
    private BitmapFont flappyBirdScoreBitmap;

    public PlayState(GameStateManager gsm){
        super(gsm);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth / 2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - (cam.viewportWidth / 2))+ground.getWidth(),
                                    GROUND_Y_OFFSET);

        tubes = new Array<Tube>();
        for(int i=1; i<= TUBE_COUNT; i++){
            tubes.add(new Tube(i* (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
        bird = new Bird(50,300);

        score=0;
        flappyBirdScore = "SCORE : 0";
        flappyBirdScoreBitmap = new BitmapFont();

        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
    }
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){// gives input
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x +80;

        for(int i =0 ; i < tubes.size; i++){
            Tube tube = tubes.get(i);

            if(cam.position.x - (cam.viewportWidth/2) >
                    tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                /*
                cam.position.x - (cam.viewportWidth/2 gives the left X-axis of the screen
                tube.getPosTopTube().x + tube.getTopTube().getWidth() gives the end X-axis
                of the tube
                */
                score++;
                flappyBirdScore = "SCORE : "+score;
                tube.reposition(tube.getPosTopTube().x +
                        ((tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds())){
                gsm.set(new GameOverState(gsm, score));
            }
        }

        if(bird.getPosition().y <= ground.getHeight()+ GROUND_Y_OFFSET){
            gsm.set(new GameOverState(gsm, score));
        }
        cam.update();
    }

    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        /*To adjust the SpriteBatch so it knows the co-ordinates we are working with in relation
        to our camera*/
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2),0);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        flappyBirdScoreBitmap.setColor(1.0f,1.0f,1.0f,1.0f);
        flappyBirdScoreBitmap.draw(sb, flappyBirdScore,
                cam.position.x - (cam.viewportWidth / 2) + 10, 400);

        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        flappyBirdScoreBitmap.dispose();
        for(Tube tube: tubes){
            tube.dispose();
        }
        System.out.println("PLAY STATE DISPOSED");
    }
}
