

import java.io.*;
import Terminal.*;

class Terrain{
	
	private char[][]tabTerrain;
	private String nom;
	private int compt$;
	private int comptC, comptC1, comptC2;//si est dans cratere: 1 temps.
	private String nomR1, nomR2;
	
	Terrain(String nomT){
		
		this.nom=nomT;
		
		int nblig=0;
		try{	
			FileReader fr = new FileReader(nomT);
			BufferedReader br = new BufferedReader(fr);
			String ligne;
			String[] prov = new String[100];
			
			for (int i=0; i<100; i++){
				ligne = br.readLine();
				if (ligne == null){
				nblig=i;
				break;
				}
				prov[i]=ligne;
			} 
			tabTerrain=new char[nblig][prov[0].length()];
			for (int i=0; i<nblig; i++){
				for (int j=0; j<prov[i].length();j++){
				tabTerrain[i][j]=prov[i].charAt(j);
				}
				for (int j=prov[i].length(); j< prov[0].length(); j++){
				tabTerrain[i][j]=' ';
				}
			}
		}
		catch(FileNotFoundException e){
			Terminal.ecrireString("Fichier absent ou nom incorrect !!!");
		}
		catch (IOException e){
			e.printStackTrace();
		}	
		comptC=0;
		compt$=0;
		for(int i=0; i<nblig; i++)
			for(int j=0; j<tabTerrain[0].length; j++)
				if(tabTerrain[i][j]=='$')
					compt$++;		
	}
	String getNom(){
		return nom;
	}
	char[][] getTab(){
		return tabTerrain;
	}
	int getLargeur(){
		return tabTerrain[0].length;
	}
	int getHauteur(){
		return tabTerrain.length;
	}
	char getChar(int y, int x){
		return tabTerrain[y][x];
	}
	int getCompt$(){
		return compt$;
	}
	void setChar(int y, int x, char c){
		tabTerrain[y][x]=c;
	}
	void setCompt$(int c){
		compt$=c;
	}
	void setNomR1(String n){
		nomR1=n;
	}
	void setNomR2(String n){
		nomR2=n;
	}
	void afficher(){
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		for(int i=0; i<tabTerrain.length; i++){
			Terminal.ecrireString("\t\t");
			for(int j=0; j<tabTerrain[0].length; j++){
				Terminal.ecrireChar(tabTerrain[i][j]);
			}
			Terminal.sautDeLigne();
		}
		if(compt$==0)
			Terminal.ecrireStringln("\t\tPas de minerai sur ce terrain. Fin de mission.");
		else Terminal.ecrireStringln("\t\tQuantite de minerai present: "+compt$);
		Terminal.sautDeLigne();
	}
	void actualiser(Robot r, IGRobot igr, int k){
		char c;
		if(k==1)//si joueur 1
			c='A';
		else//joueur 2
			c='R';
		if(r.getComptCratere()==1)
				r.setComptCratere(2);
		else			
			for(int i=0; i<tabTerrain.length; i++){
				for(int j=0; j<tabTerrain[0].length; j++){
					
					if(tabTerrain[i][j]==c){
						tabTerrain[i][j]=' ';
						igr.modifierCase(' ',j,i);
						if(tabTerrain[r.getPosY()][r.getPosX()]=='#'){
							tabTerrain[r.getPosY()][r.getPosX()]='@';
							igr.modifierCase('@',r.getPosX(),r.getPosY());
							r.setCoule(true);
							r.setFonctionne(false);
							break;
						}
						else if(tabTerrain[r.getPosY()][r.getPosX()]=='@'){
							r.setCoule(true);
							r.setFonctionne(false);
							break;
						}
						else if(tabTerrain[r.getPosY()][r.getPosX()]=='%'){
							if(k==1){
								tabTerrain[r.getPosY()][r.getPosX()]='c';
								igr.modifierCase('c',r.getPosX(),r.getPosY());
							}else{
								tabTerrain[r.getPosY()][r.getPosX()]='d';
								igr.modifierCase('d',r.getPosX(),r.getPosY());
							}
							r.setComptCratere(1);
							break;
						}
						else{ tabTerrain[r.getPosY()][r.getPosX()]=c;
							igr.modifierCase(c,r.getPosX(),r.getPosY());
							break;
						}
					}else if(k==1&&tabTerrain[i][j]=='c'&&r.getComptCratere()==2&&!r.getNoMove()){
						tabTerrain[i][j]='%';
						igr.modifierCase('%',j,i);
						tabTerrain[r.getPosY()][r.getPosX()]=c;
						igr.modifierCase(c,r.getPosX(),r.getPosY());
						r.setComptCratere(0);
						break;
					}else if(k==2&&tabTerrain[i][j]=='d'&&r.getComptCratere()==2&&!r.getNoMove()){
						tabTerrain[i][j]='%';
						igr.modifierCase('%',j,i);
						tabTerrain[r.getPosY()][r.getPosX()]=c;
						igr.modifierCase(c,r.getPosX(),r.getPosY());
						r.setComptCratere(0);
						break;
					}
				}
			}
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		for(int i=0; i<tabTerrain.length; i++){
			Terminal.ecrireString("\t\t");
			for(int j=0; j<tabTerrain[0].length; j++){
				Terminal.ecrireChar(tabTerrain[i][j]);
			}
			Terminal.sautDeLigne();
		}
		Terminal.ecrireStringln("\t\tQuantite de minerai present: "+compt$);
		Terminal.sautDeLigne();
	}
	void voirDevant(Robot r, Camera c){
		int dir=r.getDirection();
		char ch=' ';
		int i=1;
		if(r.getCoule())
			c.setImage(11,1);
		else{
			if(dir==0){
				for(;i<4;i++){
					ch=tabTerrain[r.getPosY()-i][r.getPosX()];
					if(ch!=' ') break;
				}
			}else if(dir==1){
				for(;i<4;i++){
					ch=tabTerrain[r.getPosY()][r.getPosX()+i];
					if(ch!=' ') break;
				}
			}else if(dir==2){
				for(;i<4;i++){
					ch=tabTerrain[r.getPosY()+i][r.getPosX()];
					if(ch!=' ') break;
				}
			}else{
				for(;i<4;i++){
					ch=tabTerrain[r.getPosY()][r.getPosX()-i];
					if(ch!=' ') break;
				}
			}
			switch(ch){
				case ' ': c.setImage(1,1); break;
				case '*': c.setImage(2,i); break;
				case '%': c.setImage(3,i); break;
				case '#': c.setImage(4,i); break;
				case '$': c.setImage(5,i); break;
				case 'R':	if(nomR2.equals("R2D2")){
								c.setImage(6,i); break;
							}else if(nomR2.equals("WALL-E")){
								c.setImage(7,i); break;
							}else if(nomR2.equals("VINCENT")){
								c.setImage(8,i); break;
							}else if(nomR2.equals("MARVIN")){
								c.setImage(9,i); break;
							}
				case 'A':	if(nomR1.equals("R2D2")){
								c.setImage(6,i); break;
							}else if(nomR1.equals("WALL-E")){
								c.setImage(7,i); break;
							}else if(nomR1.equals("VINCENT")){
								c.setImage(8,i); break;
							}else if(nomR1.equals("MARVIN")){
								c.setImage(9,i); break;
							}
				case '@': c.setImage(10,i);	 
			}
		}
	}
}

