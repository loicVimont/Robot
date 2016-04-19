
import Terminal.*;
import java.io.*;
import java.util.Scanner;

class Interpreteur{

	private String[][] prog;
	private String[] label;
	private Variable[] tabVar;
	private String nom;
	private String[] lc;// ligne courante a interpreter
	private int curseur;
	private int direction;
	private int portee;
	private int v;//indice de la variable dans tabVar
	private boolean aJoue;
	private boolean passeTour;
	
	Interpreteur(String nom){
		
		this.nom=nom;
		LineNumberReader lr;
		File fichier=new File(nom);	
		int count=0;
		try{
			Scanner scanner = new Scanner(fichier);
			lr = new LineNumberReader(new BufferedReader(new FileReader(fichier)));
            String line=lr.readLine();
			while((line=lr.readLine())!=null){
				count = lr.getLineNumber();
				line=lr.readLine();
            }
			prog=new String[count+1][];	
			for(int i=0; scanner.hasNextLine(); i++){
				line=scanner.nextLine().trim();
				prog[i]=line.split(" ");
			}
			lr.close();
		}
		catch(FileNotFoundException e){
			Terminal.ecrireString("Fichier absent ou nom incorrect!!!");
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		curseur=0;
		portee=1;
		passeTour=false;
		lc=prog[curseur];
		label=new String[prog.length];
		tabVar=new Variable[prog.length];
		
		for(int i=0; i<prog.length-1; i++)// création tableau label
			if(prog[i][0].equals("label"))
				label[i+1]=prog[i][1];
		
		int x=0;//compteur de variables instanciées
		for(int i=0; i<prog.length-1; i++)//création tableau objets variable
			if(prog[i][0].equals("set")||prog[i][0].equals("detect")||prog[i][0].equals("random"))
				if(x>0)
					for(int j=0; j<x; j++){
						if(prog[i][1].equals(tabVar[j].getNom()))
							break;
						else if(j==x-1){
						tabVar[x]=new Variable(prog[i][1]);
						x++;
						}
					}
				else{
					tabVar[x]=new Variable(prog[i][1]);
				    x++;
				}
	}
	void execute(Terrain ter, Robot r, IGRobot igr){
		aJoue=false;
		
		do{
			lc=prog[curseur];
			
			switch(lc[0]){
				
				case "label": curseur++;
							   break;
							   
				case "set": v=trouverVar(lc[1]);
							tabVar[v].setValeur(Integer.parseInt(lc[2]));
							curseur++;
							break;
							
				case "add": v=trouverVar(lc[1]);
							tabVar[v].setValeur(tabVar[v].getValeur()+Integer.parseInt(lc[2]));
							curseur++;
							break;
							
				case "sub": v=trouverVar(lc[1]);
							tabVar[v].setValeur(tabVar[v].getValeur()-Integer.parseInt(lc[2]));
							curseur++;
							break;
							
				case "mult":v=trouverVar(lc[1]);
							tabVar[v].setValeur(tabVar[v].getValeur()*Integer.parseInt(lc[2]));
							curseur++;
							break;
							
				case "div": v=trouverVar(lc[1]);
							tabVar[v].setValeur(tabVar[v].getValeur()/Integer.parseInt(lc[2]));
							curseur++;
							break;
							
				case "random": v=trouverVar(lc[1]);
								tabVar[v].setValeur(1+(int)(Math.random()*(Integer.parseInt(lc[2]))));
								curseur++;
								break;
								
				case "advance": r.setNoMove(false);
								if(passeTour){
									r.setNoMove(true);
									passeTour=false;
									aJoue=true;
									curseur++;
									break;
								}
								if(!Character.isDigit(lc[1].charAt(0))){
									v=trouverVar(lc[1]);
									direction=tabVar[v].getValeur();
								}
								else
									direction=Integer.parseInt(lc[1]);
								r.setDirection(direction);
								
								switch(direction){
								
									case 0: if(ter.getChar(r.getPosY()-1,r.getPosX())==' '||ter.getChar(r.getPosY()-1,r.getPosX())=='%'||ter.getChar(r.getPosY()-1,r.getPosX())=='#'||ter.getChar(r.getPosY()-1,r.getPosX())=='@'){
												r.setPosY(r.getPosY()-1);
												if(ter.getChar(r.getPosY(),r.getPosX())=='%')
													passeTour=true;
												break;
											}
											else r.setNoMove(true);
											break;
											
									case 1: if(ter.getChar(r.getPosY(),r.getPosX()+1)==' '||ter.getChar(r.getPosY(),r.getPosX()+1)=='%'||ter.getChar(r.getPosY(),r.getPosX()+1)=='#'||ter.getChar(r.getPosY(),r.getPosX()+1)=='@'){
												r.setPosX(r.getPosX()+1);
												if(ter.getChar(r.getPosY(),r.getPosX())=='%')
													passeTour=true;
												break;
											}
											else r.setNoMove(true);
											break;
											
									case 2: if(ter.getChar(r.getPosY()+1,r.getPosX())==' '||ter.getChar(r.getPosY()+1,r.getPosX())=='%'||ter.getChar(r.getPosY()+1,r.getPosX())=='#'||ter.getChar(r.getPosY()+1,r.getPosX())=='@'){
												r.setPosY(r.getPosY()+1);
												if(ter.getChar(r.getPosY(),r.getPosX())=='%')
													passeTour=true;
												break;
											}
											else r.setNoMove(true);
											break;
											
									case 3: if(ter.getChar(r.getPosY(),r.getPosX()-1)==' '||ter.getChar(r.getPosY(),r.getPosX()-1)=='%'||ter.getChar(r.getPosY(),r.getPosX()-1)=='#'||ter.getChar(r.getPosY(),r.getPosX()-1)=='@'){
												r.setPosX(r.getPosX()-1);
												if(ter.getChar(r.getPosY(),r.getPosX())=='%')
													passeTour=true;
											}
											else r.setNoMove(true);
								}
								
								aJoue=true;
								curseur++;
								break;
				
				case "goto": for(int i=0; i<label.length; i++){
								if(lc[1].equals(label[i])){
									curseur=i;
									break;
								}
							  }
							  break;
				
				case "jumpEqual": v=trouverVar(lc[1]);
								   if(tabVar[v].getValeur()==Integer.parseInt(lc[2])){
										for(int i=0; i<label.length; i++){
												if(lc[3].equals(label[i])){
													curseur=i;
													break;
												}
										}
										break;
									}
									curseur++;
									break;
									
				case "jumpNEqual": v=trouverVar(lc[1]);
								   if(tabVar[v].getValeur()!=Integer.parseInt(lc[2])){
										for(int i=0; i<label.length; i++){
												if(lc[3].equals(label[i])){
													curseur=i;
													break;
												}
										}
										break;
									}
									curseur++;
									break;
				
				case "jumpLess": v=trouverVar(lc[1]);Terminal.ecrireStringln("jumpLess ok");
								 if(tabVar[v].getValeur()<Integer.parseInt(lc[2])){
										for(int i=0; i<label.length; i++){
												if(lc[3].equals(label[i])){
													curseur=i;
													break;
												}
										}
										break;
								 }
								 curseur++;
								 break; 	
				
				case "jumpLessEqual": v=trouverVar(lc[1]);
									  if(tabVar[v].getValeur()<=Integer.parseInt(lc[2])){
										for(int i=0; i<label.length; i++){
												if(lc[3].equals(label[i])){
													curseur=i;
													break;
												}
										}
										break;
									  }
									  curseur++;
									  break;	
										
				case "jumpGreater": v=trouverVar(lc[1]);
								   if(tabVar[v].getValeur()>Integer.parseInt(lc[2])){
										for(int i=0; i<label.length; i++){
												if(lc[3].equals(label[i])){
													curseur=i;
													break;
												}
										}
										break;
									}
									curseur++;
									break;
									
				case "jumpGreaterEqual": v=trouverVar(lc[1]);
										 if(tabVar[v].getValeur()>=Integer.parseInt(lc[2])){
											for(int i=0; i<label.length; i++){
												if(lc[3].equals(label[i])){
													curseur=i;
													break;
												}
											}
											break;
										 }
										 curseur++;
										 break;
										
				case "detect":  int d;//valeur de la direction
								v=trouverVar(lc[1]);
								tabVar[v].setValeur(0);
								if(!Character.isDigit(lc[3].charAt(0))){
									v=trouverVar(lc[3]);
									portee=tabVar[v].getValeur();
								}else portee=Integer.parseInt(lc[3]);
								if(!Character.isDigit(lc[2].charAt(0))){
									v=trouverVar(lc[2]);
									d=tabVar[v].getValeur();
									if(d>3) d=0;
								}else d=Integer.parseInt(lc[2]);
								v=trouverVar(lc[1]);//indice de la var resultat
								int i;
								switch(d){
								
									case 0: for(i=1;i<=portee;i++){
												if(ter.getChar(r.getPosY()-i,r.getPosX())==' ')
													tabVar[v].setValeur(0);
												else if(ter.getChar(r.getPosY()-i,r.getPosX())=='$'){
													if(i>1){
														tabVar[v].setValeur(4);// 4 correspond à du minerai ou un autre robot à distance.
														break;
													}else{
														tabVar[v].setValeur(1);
														break;
													}
												}
												else if(ter.getChar(r.getPosY()-i,r.getPosX())=='*'){
													tabVar[v].setValeur(2);
													break;
												}
												else if(ter.getChar(r.getPosY()-i,r.getPosX())=='#'){
													tabVar[v].setValeur(3);
													break;
												}
												else if(i>1&&ter.getChar(r.getPosY()-i,r.getPosX())=='A'||ter.getChar(r.getPosY()-i,r.getPosX())=='R'){
													tabVar[v].setValeur(4);
													break;
												}
											}
											break;
											
									case 1: for(i=1;i<=portee;i++){
												if(ter.getChar(r.getPosY(),r.getPosX()+i)==' ')
													tabVar[v].setValeur(0);
												else if(ter.getChar(r.getPosY(),r.getPosX()+i)=='$'){
													if(i>1){
														tabVar[v].setValeur(4);
														break;
													}else{
														tabVar[v].setValeur(1);
														break;
													}
												}
												else if(ter.getChar(r.getPosY(),r.getPosX()+i)=='*'){
													tabVar[v].setValeur(2);
													break;
												}
												else if(ter.getChar(r.getPosY(),r.getPosX()+i)=='#'){
													tabVar[v].setValeur(3);
													break;
												}
												else if(i>1&&ter.getChar(r.getPosY(),r.getPosX()+i)=='A'||ter.getChar(r.getPosY(),r.getPosX()+i)=='R'){
													tabVar[v].setValeur(4);
													break;
												}
											}
											break;
											
									case 2: for(i=1;i<=portee;i++){
												if(ter.getChar(r.getPosY()+i,r.getPosX())==' ')
													tabVar[v].setValeur(0);
												else if(ter.getChar(r.getPosY()+i,r.getPosX())=='$'){
													if(i>1){
														tabVar[v].setValeur(4);
														break;
													}else{
														tabVar[v].setValeur(1);
														break;
													}
												}
												else if(ter.getChar(r.getPosY()+i,r.getPosX())=='*'){
													tabVar[v].setValeur(2);
													break;
												}
												else if(ter.getChar(r.getPosY()+i,r.getPosX())=='#'){
													tabVar[v].setValeur(3);
													break;
												}
												else if(i>1&&ter.getChar(r.getPosY()+i,r.getPosX())=='A'||ter.getChar(r.getPosY()+i,r.getPosX())=='R'){
													tabVar[v].setValeur(4);
													break;
												}
											}
											break;
											
									case 3: for(i=1;i<=portee;i++){
												if(ter.getChar(r.getPosY(),r.getPosX()-i)==' ')
													tabVar[v].setValeur(0);
												else if(ter.getChar(r.getPosY(),r.getPosX()-i)=='$'){
													if(i>1){
														tabVar[v].setValeur(4);
														break;
													}else{
														tabVar[v].setValeur(1);
														break;
													}
												}
												else if(ter.getChar(r.getPosY(),r.getPosX()-i)=='*'){
													tabVar[v].setValeur(2);
													break;
												}
												else if(ter.getChar(r.getPosY(),r.getPosX()-i)=='#'){
													tabVar[v].setValeur(3);
													break;
												}
												else if(i>1&&ter.getChar(r.getPosY(),r.getPosX()-i)=='A'||ter.getChar(r.getPosY(),r.getPosX()-i)=='R'){
													tabVar[v].setValeur(4);
													break;
												}
											}
								}
								curseur++;
								break;
				
				case "extract": if(ter.getChar(r.getPosY()-1,r.getPosX())=='$'){
									if(r.getComptExtract()==0){
										r.setComptExtract(6);
										aJoue=true;
										break;
									}
									r.setComptExtract(r.getComptExtract()-1);
									aJoue=true;
									if(r.getComptExtract()==0){
										ter.setChar(r.getPosY()-1,r.getPosX(),' ');
										igr.modifierCase(' ',r.getPosX(),r.getPosY()-1);
										ter.setCompt$(ter.getCompt$()-1);
										r.setComptMinerai(r.getComptMinerai()+1);
										Terminal.ecrireStringln("\n\tExtraction terminee !!!");
									}
									break;	
								}	
								else if(ter.getChar(r.getPosY(),r.getPosX()+1)=='$'){
									if(r.getComptExtract()==0){
										r.setComptExtract(6);
										aJoue=true;
										break;
									}
									r.setComptExtract(r.getComptExtract()-1);
									aJoue=true;
									if(r.getComptExtract()==0){
										ter.setChar(r.getPosY(),r.getPosX()+1,' ');
										igr.modifierCase(' ',r.getPosX()+1,r.getPosY());
										ter.setCompt$(ter.getCompt$()-1);
										r.setComptMinerai(r.getComptMinerai()+1);
										Terminal.ecrireStringln("\n\tExtraction terminee !!!");
									}
									break;
								}
								else if(ter.getChar(r.getPosY()+1,r.getPosX())=='$'){
									if(r.getComptExtract()==0){
										r.setComptExtract(6);
										aJoue=true;
										break;
									}
									r.setComptExtract(r.getComptExtract()-1);
									aJoue=true;
									if(r.getComptExtract()==0){
										ter.setChar(r.getPosY()+1,r.getPosX(),' ');
										igr.modifierCase(' ',r.getPosX(),r.getPosY()+1);
										ter.setCompt$(ter.getCompt$()-1);
										r.setComptMinerai(r.getComptMinerai()+1);
										Terminal.ecrireStringln("\n\tExtraction terminee !!!");
									}
									break;
								}
								else if(ter.getChar(r.getPosY(),r.getPosX()-1)=='$'){
									if(r.getComptExtract()==0){
										r.setComptExtract(6);
										aJoue=true;
										break;
									}
									r.setComptExtract(r.getComptExtract()-1);
									aJoue=true;
									if(r.getComptExtract()==0){
										ter.setChar(r.getPosY(),r.getPosX()-1,' ');
										igr.modifierCase(' ',r.getPosX()-1,r.getPosY());
										ter.setCompt$(ter.getCompt$()-1);
										r.setComptMinerai(r.getComptMinerai()+1);
										Terminal.ecrireStringln("\n\tExtraction terminee !!!");
									}
									break;
								}
								else{
									r.setComptExtract(0);
								}	
								curseur++;	
								break;
				
				default: curseur++; break;
			}
			if(curseur==prog.length){
				Terminal.ecrireStringln("Le programme "+nom+" de "+r.getNom()+" est fini !");
				r.setFonctionne(false);
			}
		}while(!aJoue&&r.getFonctionne()&&curseur!=prog.length);	
	}//fin execute
	
	int trouverVar(String nom){
		int i=0;
		while(i<tabVar.length&&tabVar[i]!=null&&!(tabVar[i].getNom()).equals(nom)){
			i++;
		}
		return i;
	}
	String getDir(){
		String res="?";
		switch(direction){
			case 0:res="Nord";
					break;
			case 1:res="Est";
					break;
			case 2:res="Sud";
					break;
			case 3:res="Ouest";
		}
		return res;
	}
	String getNom(){
		return nom;
	}
	//methodes de test
	void afficheProg(){
		Terminal.sautDeLigne();
		for(int i=0; i<prog.length; i++){
			for(int j=0; j<prog[i].length; j++)
				Terminal.ecrireString(" | "+prog[i][j]);
			Terminal.ecrireString(" |");
			Terminal.sautDeLigne();
		}
		Terminal.sautDeLigne();
	}
	void afficheLabel(){
		for(String s : label)
			Terminal.ecrireStringln(s);
	}
	void afficheInstru(){
		for(String s : lc)
			Terminal.ecrireString(s+" ");
		Terminal.sautDeLigne();
	}
	void afficheTabVar(){
		for(int i=0; tabVar[i]!=null; i++)
				Terminal.ecrireStringln(tabVar[i].getNom()+"="+tabVar[i].getValeur());
	}	
}

class Variable{

	private String nom;
	private int valeur;
	
	Variable(String n){
		nom=n;
		valeur=0;
	}
	String getNom(){
		return nom;
	}
	int getValeur(){
		return valeur;
	}
	void setValeur(int v){
		valeur=v;
	}
}
	