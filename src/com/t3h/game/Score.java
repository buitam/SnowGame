package com.t3h.game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Score extends Object2D {

    int score;
    String scoreText;

    public Score(int x, int y) {
        this.scoreText = "0";
        this.score = 0;
    }

    public void update() {

    }

    public void draw(Graphics2D g2d){

        g2d.setColor(Color.white);
        g2d.drawString(scoreText, x, y);
    }

    public int getScore() {

        return score;
    }

    public void reset() {
        scoreText = "0";
        score = 0;
    }
}