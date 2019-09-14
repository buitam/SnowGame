package com.t3h.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyPanel extends JPanel {
    List<Snow> snows = new ArrayList<>();//vì có nhiều snow nên phải dùng kiểu List
    List<Slidebar> slidebars = new ArrayList<>();
    List<Gift> gifts = new ArrayList<>();
    Score playerScore = new Score(400, 100);
    Player player;
    int score;
    long currentSnow ;
    long currentSlidebar;
    long currentGift;
    long currentPlayerX;
    long currentPlayerY;
    boolean isPressLefft, isPressRight;


    MyPanel(){
        setLocation(0,0);//location so với MyFrame
        setSize(500,700);
        setBackground(Color.GREEN);
        SoundManager.play("runawayfive.wav");
        createSnowList();
        createListSlidebar();
        creategGiftList();
        createPlayer();
        createThread();

    }
    void createSnowList(){ for (int i = 0; i<100; i++){
        Snow snow = createSnow();
        snows.add(snow);
    }}
    void createPlayer(){
        player = new Player();
        Random rd = new Random();
        player.w = 20;
        player.h = 30;
        player.x = rd.nextInt(getWidth()/2)+100;
        player.y =100;
        player.img = new ImageIcon(getClass().getResource("/imgs/nguoituyet.png")).getImage();

    }

    void createListSlidebar(){
        for(int i=0; i<10;i++){
            int y = getHeight() + 85*i;
            Slidebar slidebar = createSlidebar();
            slidebar.y = y;
            slidebars.add(slidebar);
        }
    }

    void creategGiftList(){
        for(int i=0; i<10;i++){
            int y = getHeight() + 300*i;
            Gift gift = createGift();
            gift.y = y;
            gifts.add(gift);
        }
    }
    void moveSnow(){
        long curr = System.currentTimeMillis();
        if(curr-currentSnow >= 10){
        currentSnow = curr;
        for( int i = 0; i < snows.size(); i++){
            Snow s = snows.get(i);
            s.y++;
            if (s.y == getHeight()) {
                s.y = 0;
                Random rd = new Random();
                s.x = rd.nextInt(getWidth()-s.w);
            }}
         }
    }
    void movePlayer(){
        long curr = System.currentTimeMillis();
        if(curr- currentPlayerY >= 8){
            currentPlayerY = curr;
            player.y += 2;
        }
        if(curr - currentPlayerX>=5){
            currentPlayerX=curr;
            if(isPressLefft==true){
                player.x-=2;
            }
            if(isPressRight==true){
                player.x+=2;
            }
        }
        interactPlaySlidebar();
    }
    void interactPlaySlidebar(){
        Rectangle rPlayer = new Rectangle(player.x, player.y, player.w, player.h);
        for( int i = 0; i < slidebars.size(); i++){
            Slidebar sb = slidebars.get(i);
            Rectangle rSlidebar = new Rectangle(sb.x, sb.y,sb.w,sb.h);
            if(rPlayer.intersects(rSlidebar)){

                if(player.y+player.h - sb.y <=3){
                    //thì xảy ra va chạm// sau đó update lại height của player
                    player.y = sb.y - player.h-1;

                }
            }

        }

    }
    void movePlayerX(){
        requestFocus();//bắt sự kiện bàn phím
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int code = e.getKeyCode();
                if(code == 37){
                    isPressLefft=true;
                }
                else if (code == 39){
                    isPressRight=true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                int code = e.getKeyCode();
                if(code == 37){
                    isPressLefft=false;
                }
                else if (code == 39){
                    isPressRight=false;
                }
            }
        });
}
    void moveSlidebar(){

        long curr = System.currentTimeMillis();
        Slidebar slidebar = new Slidebar();
        if(curr-currentSlidebar>=10){
            currentSlidebar = curr;
            for( int i = 0; i < slidebars.size(); i++){
                Slidebar s = slidebars.get(i);
                s.y--;
                if (s.y == -15) {
                    s.y = getHeight();
                    Random rd = new Random();
                    s.x = rd.nextInt(getWidth()-s.w);
                }}
        }
        if(slidebar.y<=0){
            playerScore.score += 1;
            playerScore.scoreText = Integer.toString(playerScore.getScore());
        }
    }
    void moveGift(){
        long curr = System.currentTimeMillis();
        if(curr-currentGift>=10){
            currentGift = curr;
            for( int i = 0; i < gifts.size(); i++){
                Gift s = gifts.get(i);
                s.y--;
                if (s.y == -15) {
                    s.y = getHeight();
                    Random rd = new Random();
                    s.x = rd.nextInt(getWidth()-s.w);
                }}
        }
    }
    void checkDie(){
        if(player.y<=0 || player.y >= getHeight()-player.h){
            int result=JOptionPane.showConfirmDialog(this,"Lose","Die",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                snows.clear();
                SoundManager.play("runawayfive.wav");
                createSnowList();
                slidebars.clear();
                createListSlidebar();
                createPlayer();
            }
            else {
                System.exit(0);
            }
        }
    }

    void createThread(){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true){
                    moveSnow();
                    moveSlidebar();
                    movePlayer();
                    movePlayerX();
                    checkDie();
                    moveGift();
                repaint();// nó sẽ vẽ lại ở hàm paint component
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    Thread t = new Thread(r);
       t.start();
}

    Snow createSnow(){
        Snow snow = new Snow();
        Random rd = new Random();
        snow.w = 2;
        snow.h = 2;
        snow.x = rd.nextInt(getWidth()- snow.w);
        snow.y = rd.nextInt(getHeight());
        Image image = new ImageIcon(
                getClass().getResource("/imgs/cautuyet.png")
        ).getImage();
        snow.img = image;
        return snow;
    }
    Slidebar createSlidebar(){
        Slidebar slidebar = new Slidebar();
        Random rd = new Random();
        slidebar.w = rd.nextInt(41)+ 60;
        slidebar.h = 10;
        slidebar.x = rd.nextInt(getWidth()- slidebar.w);
        slidebar.y = rd.nextInt(getHeight());
        slidebar.delay=10;
        Image image = new ImageIcon(
                getClass().getResource("/imgs/tas.png")
        ).getImage();
        slidebar.img = image;
        return slidebar;

    }



    Gift deleteGift(){
        Gift gift = new Gift();
        Random rd = new Random();
        gift.w = 0;
        gift.h = 0;
        gift.x = rd.nextInt(getWidth()- gift.w);
        gift.y = rd.nextInt(getHeight());
        gift.delay=10;
        Image image = new ImageIcon(
                getClass().getResource("/imgs/hopqua.png")
        ).getImage();
        gift.img = image;
        return gift;
    }

    Gift createGift(){
        Gift gift = new Gift();
        Random rd = new Random();
        gift.w = 20;
        gift.h = 20;
        gift.x = rd.nextInt(getWidth()- gift.w);
        gift.y = rd.nextInt(getHeight());
        gift.delay=10;
        Image image = new ImageIcon(
                getClass().getResource("/imgs/hopqua.png")
        ).getImage();
        gift.img = image;
        return gift;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        Image image = new ImageIcon(
                getClass().getResource("/imgs/nen1.png")
        ).getImage();
        g2d.drawImage(image,0,0,getWidth(),getHeight(), null);
        //vị trí trên giấy vẽ, w-h là chiều dài rộng của ảnh , null là trên localhost,
        //trường hợp vẽ từ internet dùng observer...,
        //đói tượng đồ họa cần có ít nhất :x,y,w,h,img. nếu có những thuộc tính chung thì tạo vào 1 class chung(Object 2D)
        // rồi dùng các class kế thừa

        for(int j=0; j <slidebars.size();j++){
            Slidebar sb = slidebars.get(j);
            sb.draw(g2d);
        } //do tuyết nằm trên snowbar nên phải đặt dòng for này ở trước

        for(int i =0; i<snows.size(); i++){
            Snow s = snows.get(i);
            s.draw(g2d);//g2d gọi đến snow, còn snow sẽ vẽ ở hàm draw bên snow
        }

        for(int i =0; i<gifts.size(); i++){
            Gift gt = gifts.get(i);
            gt.draw(g2d);//g2d gọi đến snow, còn snow sẽ vẽ ở hàm draw bên snow
        }

        player.draw(g2d);
        playerScore.draw(g2d);

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
    }
}
//graphic : bút vẽ, vẽ gì thì sử dụng bút vẽ để vẽ. vẽ ảnh
