
import java.awt.*;
import javax.swing.*;
import java.util.*;
import Terminal.*;

class SpecialPanel extends JPanel{
    char[][] jeu;
    Color blanc = Color.white;
    Color noir = new Color(150,120,120);
    private HashMap<Character,ImageIcon>  images;
    SpecialPanel(char[][]  je, HashMap<Character,ImageIcon> im){
	jeu = je;
	images = im;
    }
    public void paintComponent(Graphics g) {
		super.paintComponent(g);  
		for (int i=0; i<jeu.length; i++){
			for (int j=0; j<jeu[0].length; j++){
			images.get(jeu[i][j]).paintIcon(this,g,j*48,i*48);
			}
		}
    }
}


public class IGRobot extends JFrame{
    private char[][] jeu;
    private HashMap<Character,ImageIcon>  images;
    private SpecialPanel jpane;
	private JLabel info;
	private String texte;
    
	public IGRobot(String n){
	images = new  HashMap<Character,ImageIcon>();
	this.associerCharIcon(' ',"sol.png");
	this.associerCharIcon('*',"rock.png");
	this.associerCharIcon('%',"cratere.png");
	this.associerCharIcon('#',"water.png");
	this.associerCharIcon('@',"tourbillon.png");
	this.associerCharIcon('$',"minerai.png");
	this.associerCharIcon('A',n+".png");
	this.associerCharIcon('c',"cratere+"+n+".png");
    }
	public IGRobot(String n1, String n2){
	images = new  HashMap<Character,ImageIcon>();
	this.associerCharIcon(' ',"sol.png");
	this.associerCharIcon('*',"rock.png");
	this.associerCharIcon('%',"cratere.png");
	this.associerCharIcon('#',"water.png");
	this.associerCharIcon('@',"tourbillon.png");
	this.associerCharIcon('$',"minerai.png");
	this.associerCharIcon('A',n1+".png");
	this.associerCharIcon('R',n2+".png");
	this.associerCharIcon('c',"cratere+"+n1+".png");
	this.associerCharIcon('d',"cratere+"+n2+".png");
    }
    public void creerFenetre(char[][]  je){
	if (jeu != null)
	    throw new FenetreExisteDeja();
	jeu = new char[je.length][je[0].length];
	for (int i = 0; i<je.length; i++){
	    for (int j=0; j<je[0].length; j++){
		jeu[i][j]=je[i][j];
	    }
	}
	this.setTitle("LES ROBOTS SUR LA LUNE");
	ImageIcon im=new ImageIcon("Robot/images/lune.png");
	this.setIconImage(im.getImage());
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.setLayout(new BorderLayout ()); 
	jpane = new SpecialPanel(jeu, images);
	Font police = new Font("Lucida Console", Font.PLAIN, (jeu[0].length<20)?12:16);
	info= new JLabel(texte);
	info.setFont(police);
	info.setForeground(Color.cyan);
	info.setBackground(Color.BLACK);
	info.setOpaque(true);
	info.setPreferredSize(new Dimension(jeu[0].length*48,jeu.length+40));
	info.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
	this.add(jpane, BorderLayout.NORTH);
	this.add(info, BorderLayout.SOUTH);
	jpane.setPreferredSize(new Dimension(jeu[0].length*48,jeu.length*48));
	this.pack();
	this.setVisible(true);
    }
    public void associerCharIcon(char c, String s){
	images.put(c,new ImageIcon("Robot/images/" + s));
    }
    public void modifierCase(char c, int x, int y){
	jeu[y][x]=c;
    }
    public void rafraichir(){
	jpane.repaint();
	info.setText(texte);
    }
    public void fermer(){
	this.dispose();
    }
	public void setTexte(String s){
		texte=s;
	}
}
class FenetreExisteDeja extends RuntimeException{}

