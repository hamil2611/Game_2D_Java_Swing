package org.example;

import org.example.utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PanelTest extends JPanel {
    private MainTest mainTest;
    protected int count = 0;
    int cnt=0;
    int cntDir=0;
    BufferedImage img;
    BufferedImage[] imgs;
    int xPos=100;
    public PanelTest(MainTest mainTest){
        this.mainTest = mainTest;
        img = LoadSave.GetSpriteAtLas("effect2.png");
        imgs = new BufferedImage[4];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = img.getSubimage(i * 16, 0, 16, 16);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i=0;i<5;i++){
            g.drawImage(imgs[count], 100+ 30*i, 100, 80,80,null);
        }

        cnt++;
        if(cnt==200){
            count++;
            if(count==4)
                count=0;
            cnt=0;
        }
        cntDir++;
        if(cntDir==20){
            xPos+=5;
            cntDir=0;
        }
        repaint();
    }
}
