package com.sresta.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sresta.game.FlappyDemo;

public class GameOverState extends State {

    private Texture gameOver;
    private Texture background;
    private String finalScore;
    private BitmapFont finalScoreBitmap;

    public GameOverState(GameStateManager gsm, int score){
        super(gsm);
        background = new Texture("bg.png");
        gameOver = new Texture("gameover.png");
        finalScore = "Your score is " + score;
        finalScoreBitmap = new BitmapFont();
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(gameOver, cam.position.x - (gameOver.getWidth()/2),cam.position.y);
        finalScoreBitmap.setColor(1.0f,1.0f,1.0f,1.0f);
        finalScoreBitmap.draw(sb, finalScore,
                cam.position.x - (cam.viewportWidth / 4),
                cam.viewportHeight/2);
        sb.end();

    }

    @Override
    public void dispose() {
        gameOver.dispose();
        System.out.println("GAME OVER STATE DISPOSED");
    }
}
