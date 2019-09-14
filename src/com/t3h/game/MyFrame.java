package com.t3h.game;

import javax.swing.*;

public class MyFrame extends JFrame {
    MyPanel myPanel;//tạo thuộc tính
    MyFrame(){
        //B1: Tạo khung tranh// Class MyFrame
        setSize(500,700);
        setLocationRelativeTo(null);
        setLayout(null);//để tương thích với jframe
        myPanel = new MyPanel();
        //dán giấy lên Frame
        add(myPanel);

    }

}
