package com.sresta.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam; // camera for the game
    protected Vector3 mouse; // x,y,z co-ordinate system for pointer/mouse
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt); // dt(delta time: time between two frames rendered)
    public abstract void render(SpriteBatch sb); //SpriteBatch is a container for elements to be
                                                // rendered on screen
    public abstract void dispose();
}
