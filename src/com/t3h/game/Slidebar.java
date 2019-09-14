package com.t3h.game;

import java.awt.*;

public class Slidebar extends Object2D{
    int delay;
    void draw(Graphics2D g2d){//muốn vẽ cần bút vẽ, ko có thì truyền vào: là grabphic g2d
       /* g2d.setColor(Color.WHITE);*/
        /*  g2d.drawString("*",x,y);*/
        g2d.drawImage(img,x,y,w,h, null);


    }
}
