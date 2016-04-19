import Terminal.*;

public class Jeu{
	
	static String[] tabNoms={"R2D2","WALL-E","VINCENT","MARVIN"};
	
	public static void main(String[]args){
		
		String nomT, nomR, nomR1, nomR2,nomP, nomP1, nomP2, s, s1, s2;
		char c1,c2,c3,c4;
		boolean enCours=true;
		boolean deuxJoueurs=false;
		int joueurs;
		Terrain ter;
		IGRobot igr;
		Robot r, r1, r2;
		Interpreteur in, in1, in2;
		Camera ca, ca1, ca2;
		Terminal.ecrireStringln("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t\t\tLES ROBOTS SUR LA LUNE");
		Terminal.ecrireStringln("\n\n\n\n\n\n\n\n");
		
		 // intro();
		
		do{
			joueurs=1;
			/* Terminal.ecrireString("\n\nEntrez le nom du terrain: ");
			nomT=Terminal.lireString(); */
			nomT="Robot/petit_ter.txt";
			do{
				Terminal.ecrireString("\n\nMode 2 joueurs ?(o/n): ");
				c4=Terminal.lireString().charAt(0);
				
				if(c4=='o'){// Mode deux joueurs
					deuxJoueurs=true;
					/* Terminal.ecrireString("\n\nEntrez le nom du premier programme: ");
					nomP1=Terminal.lireString(); */
					nomP1="Robot/prog1.txt";
					afficheNoms();//affiche les differents modeles de robots.
					nomR1=tabNoms[Terminal.lireInt()-1];
					/* Terminal.ecrireString("\n\nEntrez le nom du second programme: ");
					nomP2=Terminal.lireString(); */
					nomP2="Robot/prog1.txt";
					afficheNoms();
					nomR2=tabNoms[Terminal.lireInt()-1];
					do{
						/* Terminal.ecrireString("\n\nMode automatique (plus sympa!) ?(o/n): ");
						c1=Terminal.lireString().charAt(0); */
						c1='o';
					}while(c1!='n'&&c1!='o');
					ter=new Terrain(nomT);
					r1=new Robot(nomR1);
					ter.setNomR1(nomR1);
					in1= new Interpreteur(nomP1);
					r1.lancerRobot(ter,joueurs);
					joueurs=2;
					r2=new Robot(nomR2);
					ter.setNomR2(nomR2);
					in2= new Interpreteur(nomP2);
					r2.lancerRobot(ter,joueurs);
					igr = new IGRobot(nomR1,nomR2);
					igr.setTexte("Connexion en cours...");
					igr.creerFenetre(ter.getTab());
					ca1=new Camera(nomR1);
					ca1.rafraichir();
					ca2=new Camera(nomR2);
					ca2.rafraichir();
					temporise(2000);
					s1=infoRobot(ter,r1,in1);
					s2=infoRobot(ter,r2,in2);
					ter.afficher();
					igr.setTexte("<html>"+s1+"<br>"+s2+"</html>");
					igr.rafraichir();
					ter.voirDevant(r1,ca1);//pour l'affichage camera
					ter.voirDevant(r2,ca2);
					ca1.rafraichir();
					ca2.rafraichir();
					
					if(ter.getCompt$()!=0){	
						//boucles du jeu !!!
						if(c1=='n'){
							do{	
								in1.execute(ter,r1,igr);
								ter.actualiser(r1,igr,1);
								in2.execute(ter,r2,igr);
								ter.actualiser(r2,igr,2);
								s1=infoRobot(ter,r1,in1);
								s2=infoRobot(ter,r2,in2);
								igr.setTexte("<html>"+s1+"<br>"+s2+"</html>");
								igr.rafraichir();
								ter.voirDevant(r1,ca1);
								ter.voirDevant(r2,ca2);
								ca1.rafraichir();
								ca2.rafraichir();
								
								enCours=r1.getFonctionne()||r2.getFonctionne();
								
								Terminal.sautDeLigne();
								Terminal.ecrireStringln("\n'Enter'= tour suivant,'q'+'Enter'= quitter");
								c2=Terminal.lireChar();// taper Enter pour continuer.
							}while(c2!='q'&&enCours&&ter.getCompt$()!=0);
							
							ca1.fermer();
							ca2.fermer();
							s1=estGagnant(r1,r2);
							igr.setTexte("<html>"+s1+"</html>");
							igr.rafraichir();
						}
						else if(c1=='o'){
							do{
								in1.execute(ter,r1,igr);
								ter.actualiser(r1,igr,1);
								in2.execute(ter,r2,igr);
								ter.actualiser(r2,igr,2);
								s1=infoRobot(ter,r1,in1);
								s2=infoRobot(ter,r2,in2);
								igr.setTexte("<html>"+s1+"<br>"+s2+"</html>");
								igr.rafraichir();
								ter.voirDevant(r1,ca1);
								ter.voirDevant(r2,ca2);
								ca1.rafraichir();
								ca2.rafraichir();
								enCours=r1.getFonctionne()||r2.getFonctionne();
								temporise(1000);
							}while(enCours&&ter.getCompt$()!=0);
							ca1.fermer();
							ca2.fermer();
							s1=estGagnant(r1,r2);
							igr.setTexte("<html>"+s1+"</html>");
							igr.rafraichir();
						}
					}
				}
				else{// Mode un seul joueur
					Terminal.ecrireString("\n\nEntrez le nom du programme: ");
					nomP=Terminal.lireString();
					afficheNoms();
					nomR=tabNoms[Terminal.lireInt()-1];
					do{
						Terminal.ecrireString("\n\nMode automatique (plus sympa!) ?(o/n): ");
						c1=Terminal.lireString().charAt(0);
					}while(c1!='n'&&c1!='o');
					ter=new Terrain(nomT);
					r=new Robot(nomR);
					in= new Interpreteur(nomP);
					r.lancerRobot(ter,joueurs);
					igr = new IGRobot(nomR);
					igr.setTexte("Connexion en cours...");
					igr.creerFenetre(ter.getTab());
					ca=new Camera(nomR);
					ca.rafraichir();
					temporise(2000);
					ter.afficher();
					s=infoRobot(ter,r,in);
					igr.setTexte("<html>"+s+"</html>");
					igr.rafraichir();
					ter.voirDevant(r,ca);
					ca.rafraichir();
					
					if(ter.getCompt$()!=0){	
						
						//boucles du jeu !!!
						if(c1=='n'){
							do{	
								in.execute(ter,r,igr);
								ter.actualiser(r,igr,1);
								s=infoRobot(ter,r,in);
								igr.setTexte(s);
								igr.rafraichir();
								ter.voirDevant(r,ca);
								ca.rafraichir();
								enCours=r.getFonctionne();
								Terminal.sautDeLigne();
								Terminal.ecrireStringln("\n'Enter'= tour suivant,'q'+'Enter'= quitter");
								c2=Terminal.lireString().charAt(0);// taper Enter pour continuer.
							}while(c2!='q'&&enCours&&ter.getCompt$()!=0);
							if(!enCours){
								ca.fermer();
							}
						}
						else if(c1=='o'){
							do{
								in.execute(ter,r,igr);
								ter.actualiser(r,igr,1);
								s=infoRobot(ter,r,in);
								igr.setTexte(s);
								igr.rafraichir();
								ter.voirDevant(r,ca);
								ca.rafraichir();
								enCours=r.getFonctionne();
								temporise(800);
							}while(enCours&&ter.getCompt$()!=0);
							if(!enCours){
								ca.fermer();
							}
						}
					}
				}
			}while(c4!='o'&&c4!='n');
			Terminal.ecrireString("Fin de partie...tapez Enter");
			Terminal.lireString();
			igr.fermer();
			do{
				Terminal.ecrireString("\n\nVoulez vous une autre partie ?(o/n): ");
				c3=Terminal.lireString().charAt(0);
				
				if(c3=='n'){ 
					Terminal.ecrireString("\n\n\nAu revoir et bon retour sur Terre !!!\n\n");
					temporise(1000);
				}
			}while(c3!='o'&&c3!='n');
		}while(c3!='n');
	}
	static void intro(){
		
		temporise(3000);
		Terminal.ecrireStringln("\t\t Nous sommes en l'an 2032 !!!\n");
		temporise(3000);
		Terminal.ecrireStringln("\t\t Lors de la mission ALPHA IV, des gisements de matieres precieuses\n");
		temporise(3000);
		Terminal.ecrireStringln("\t\t ont ete decouverts a proximite de sources d eau liquide,\n");
		temporise(2500);
		Terminal.ecrireStringln("\t\t sur un zone inexploree de la lune.\n");
		temporise(3000);
		Terminal.ecrireStringln("\t\t Cette decouverte bouleversa le monde scientifique.\n");
		temporise(4000);
		Terminal.ecrireStringln("\n\t\t Un croiseur spatial a alors ete envoye avec a son bord\n");
		temporise(3500);
		Terminal.ecrireStringln("\t\t des robots capable de detecter et de recolter le precieux minerai.\n");
		temporise(4000);
		Terminal.ecrireStringln("\t\t Vous etes en charge de programmer ces robots.\n");
		temporise(3000);
		Terminal.ecrireStringln("\t\t Bon courage ........!!!!\n\n\n\n");
		temporise(5000);
	}
	static String infoRobot(Terrain ter,Robot r,Interpreteur in){	
		String res;
		res=" "+r.getNom()+" ("+in.getNom()+") X= "+r.getPosX()+", Y= "+r.getPosY()+", "+in.getDir()+". Minerai extrait: "+r.getComptMinerai()+" restant: "+ter.getCompt$()+". ";
		if(r.getComptExtract()!=0)
			res+=" EXTRACTION!!! Duree: "+r.getComptExtract()+" tours.";
		else if(r.getNoMove())
			res+=" Obstacle!!!";
		else if(r.getCoule())
			res+=" PLOUF!!! "+r.getNom()+" est HS!!!";
		if(ter.getCompt$()==0)
			res+=" Plus de minerai ! Mission terminee !!!";
		return res;
	}
	static String estGagnant(Robot r1, Robot r2){
		String res;
		if(r1.getComptMinerai()>r2.getComptMinerai())
			res="Le gagnant est "+r1.getNom()+" ! Sa recolte est de "+r1.getComptMinerai()+" pierres.";
		else if(r1.getComptMinerai()<r2.getComptMinerai())
			res="Le gagnant est "+r2.getNom()+" ! Sa recolte est de "+r2.getComptMinerai()+" pierres.";
		else res="Match nul !!! "+r1.getComptMinerai()+" pierres pour chacun !!!";
		return res;
	}
	static void afficheNoms(){
		Terminal.ecrireStringln("\nChoisissez un modele de robot:\n");
		for(int i=0;i<tabNoms.length;i++)
			Terminal.ecrireStringln((i+1)+"- "+tabNoms[i]);
		Terminal.ecrireString("\nEntrez le numero: "); 
	}
	static void temporise(int millis){
		try{
			Thread.sleep(millis);
		}
		catch(InterruptedException e){
				e.printStackTrace();
		}
	}
}