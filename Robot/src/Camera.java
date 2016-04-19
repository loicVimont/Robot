

import java.awt.*;
import javax.swing.*;
import java.util.*;
import Terminal.*;

class Camera extends JFrame{
	
	private JPanel pn;
	private JLabel lb;
	private ImageIcon i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16,i17,i18,i19,i20,i21,i22,i23,i24,i25,i26,i27,i28,i29;

    public Camera(String nom) {
		
		i1=new ImageIcon("Robot/images/horizon.png");
		i2=new ImageIcon("Robot/images/horizon+rock.png");
		i3=new ImageIcon("Robot/images/horizon+cratere.png");
		i4=new ImageIcon("Robot/images/horizon+water.png");
		i5=new ImageIcon("Robot/images/horizon+minerai.png");
		i6=new ImageIcon("Robot/images/horizon+R2D2.png");
		i7=new ImageIcon("Robot/images/horizon+WALL-E.png");
		i8=new ImageIcon("Robot/images/horizon+VINCENT.png");
		i9=new ImageIcon("Robot/images/horizon+MARVIN.png");
		i10=new ImageIcon("Robot/images/horizon+rock2.png");
		i11=new ImageIcon("Robot/images/horizon+cratere2.png");
		i12=new ImageIcon("Robot/images/horizon+water2.png");
		i13=new ImageIcon("Robot/images/horizon+minerai2.png");
		i14=new ImageIcon("Robot/images/horizon+R2D22.png");
		i15=new ImageIcon("Robot/images/horizon+WALL-E2.png");
		i16=new ImageIcon("Robot/images/horizon+VINCENT2.png");
		i17=new ImageIcon("Robot/images/horizon+MARVIN2.png");
		i18=new ImageIcon("Robot/images/horizon+rock3.png");
		i19=new ImageIcon("Robot/images/horizon+cratere3.png");
		i20=new ImageIcon("Robot/images/horizon+water3.png");
		i21=new ImageIcon("Robot/images/horizon+minerai3.png");
		i22=new ImageIcon("Robot/images/horizon+R2D23.png");
		i23=new ImageIcon("Robot/images/horizon+WALL-E3.png");
		i24=new ImageIcon("Robot/images/horizon+VINCENT3.png");
		i25=new ImageIcon("Robot/images/horizon+MARVIN3.png");
		i26=new ImageIcon("Robot/images/horizon+tourbillon.png");
		i27=new ImageIcon("Robot/images/horizon+tourbillon2.png");
		i28=new ImageIcon("Robot/images/horizon+tourbillon3.png");
		i29=new ImageIcon("Robot/images/coule.png");

		this.setTitle(nom);
        this.setBounds(1000,(int)(Math.random()*500),150,150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		
		pn=new JPanel();
		lb=new JLabel(i1);
		pn.add(lb);
		this.add(pn);
		this.pack();
		this.setVisible(true);
    }
	public void setImage(int n, int dist){// numero sujet et distance 
		if(dist==1){
			if(n==1) lb.setIcon(i1);
			else if(n==2) lb.setIcon(i2);
			else if(n==3) lb.setIcon(i3);
			else if(n==4) lb.setIcon(i4);
			else if(n==5) lb.setIcon(i5);
			else if(n==6) lb.setIcon(i6);
			else if(n==7) lb.setIcon(i7);
			else if(n==8) lb.setIcon(i8);
			else if(n==9) lb.setIcon(i9);
			else if(n==10) lb.setIcon(i26);
			else if(n==11) lb.setIcon(i29);
		}
		else if(dist==2){
			if(n==2) lb.setIcon(i10);
			else if(n==3) lb.setIcon(i11);
			else if(n==4) lb.setIcon(i12);
			else if(n==5) lb.setIcon(i13);
			else if(n==6) lb.setIcon(i14);
			else if(n==7) lb.setIcon(i15);
			else if(n==8) lb.setIcon(i16);
			else if(n==9) lb.setIcon(i17);
			else if(n==10) lb.setIcon(i27);
		}
		else{
			if(n==2) lb.setIcon(i18);
			else if(n==3) lb.setIcon(i19);
			else if(n==4) lb.setIcon(i20);
			else if(n==5) lb.setIcon(i21);
			else if(n==6) lb.setIcon(i22);
			else if(n==7) lb.setIcon(i23);
			else if(n==8) lb.setIcon(i24);
			else if(n==9) lb.setIcon(i25);
			else if(n==10) lb.setIcon(i28);
		}
	}
	public void rafraichir(){
		pn.repaint();
    }
    public void fermer(){
		this.dispose();
    }
    
}
/*class ImageCam extends JPanel{

	private ImageIcon image;
	
	ImageCam(){
		
    }
	
	public void paintComponent(Graphics g){
    	super.paintComponent(g);  
		g.drawImage(image,0,0,this);	
    }
}*/