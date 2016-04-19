


import Terminal.*;


class Robot{

	private String nom;
	private int posX, posY;
	private int direction;
	private int portee;
	private int comptExtract;
	private int comptMinerai;
	private int comptCratere;
	private boolean fonctionne;
	private boolean noMove;
	private boolean coule;
	
	
	Robot(String nomR){
		
		nom=nomR;
		portee=1;
		comptExtract=0;
		comptMinerai=0;
		comptCratere=0;
		fonctionne=true;
		noMove=false;
		coule=false;
		
	}
	String getNom(){
		return nom;
	}
	int getPosX(){
		return posX;
	}
	int getPosY(){
		return posY;
	}
	int getDirection(){
		return direction;
	}
	int getPortee(){
		return portee;
	}
	int getComptExtract(){
		return comptExtract;
	}
	int getComptMinerai(){
		return comptMinerai;
	}
	int getComptCratere(){
		return comptCratere;
	}
	boolean getFonctionne(){
		return fonctionne;
	}
	boolean getNoMove(){
		return noMove;
	}
	boolean getCoule(){
		return coule;
	}
	void setPosX(int x){
		posX=x;
	}
	void setPosY(int y){
		posY=y;
	}
	void setDirection(int d){
		direction=d;
	}
	void setPortee(int p){
		portee=p;
	}
	void setComptExtract(int c){
		comptExtract=c;
	}
	void setComptMinerai(int c){
		comptMinerai=c;
	}
	void setComptCratere(int c){
		comptCratere=c;
	}
	void setFonctionne(boolean b){
		fonctionne=b;
	}
	void setNoMove(boolean b){
		noMove=b;
	}
	void setCoule(boolean b){
		coule=b;
	}
	void lancerRobot(Terrain ter, int d){
		char [][]tab=ter.getTab();
		boolean estPlace=false;// s'il y a un A pour placer le robot au debut
		int i=0,j=0;
		for(i=0;i<tab.length;i++){
			for(j=0;j<tab[0].length;j++)
				if(tab[i][j]=='A'){
					estPlace=true;
					break;
				}
			if(estPlace)
				break;
		}
		if(estPlace&&d!=2){
			posX=j;
			posY=i;
		}else{
			int initX=1;
			int initY=1;
			while(posX!=initX){
				initX=1+(int)(Math.random()*(ter.getLargeur()-1));
				initY=1+(int)(Math.random()*(ter.getHauteur()-1));
				if(ter.getChar(initY, initX)==' '){
					posX=initX;
					posY=initY;
				}
			}
			if(d==2)//si joueur 2
				ter.setChar(initY, initX, 'R');
			else ter.setChar(initY, initX, 'A');
		}		
	}
		
}