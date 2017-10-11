package FiltrageSimple;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.util.ArrayList;

public class Label {
	private Hashtable affecte;
	private Hashtable nonAffecte;
	private ArrayList<Longueur> listAffecte;
	private ArrayList<Longueur> listNonAffecte;
	private ArrayList<Double> contrainte;
	private String text="";
	private String text2;
	private boolean etat=false;
	
	public Label(Hashtable affecte,Hashtable nonAffecte,ArrayList<Longueur> listAffecte,ArrayList<Longueur> listNonAffecte,ArrayList<Double> contrainte ){
		this.affecte=affecte;
		this.nonAffecte=nonAffecte;
		this.listAffecte=listAffecte;
		this.listNonAffecte=listNonAffecte;
		this.contrainte=contrainte;
		Ecrit();
	}
	
	@SuppressWarnings("unchecked")
	public void Ecrit(){
		
		for(int i=0;i<listNonAffecte.size();i++){//Cas o� un domaine est vide
			if(((ArrayList<Longueur>)nonAffecte.get(listNonAffecte.get(i))).isEmpty()){
				text+="CETTE CONFIGURATION N'A PAS DE SOLUTION!!!\n";
				setEtat(true);
				if(Simple.branche)Discours.setDiscours(getText()+"\n");
				return;
			}
		}
		
		text+="+++ Nous sommes dans le cas suivant: +++\n";
		if(!affecte.isEmpty()){//Je forme l'ensemble d'affectation
			text2="(("+listAffecte.get(0).getIndex()+","+listAffecte.get(0).getLongueur()+"),";
			text2+="("+((Longueur)affecte.get(listAffecte.get(0))).getIndex()+","+
			((Longueur)affecte.get(listAffecte.get(0))).getLongueur()+"))";
			for(int i=1;i<affecte.size();i++){
				text2+=";(("+listAffecte.get(i).getIndex()+","+listAffecte.get(i).getLongueur()+"),";
				text2+="("+((Longueur)affecte.get(listAffecte.get(i))).getIndex()+","
				+((Longueur)affecte.get(listAffecte.get(i))).getLongueur()+"))";			
			}
			if(listNonAffecte.size()==0)TestePuisEnumere.compteur++;
		}else if(affecte.isEmpty()) text2="";
		text+="A = {"+text2+"}\n";
		text+="Le domaine des fils deja decoupes:\n";
		text2="";
		if(!affecte.isEmpty()){//Explication des fils d�coup�s
			for(int i=0;i<affecte.size();i++){
				text2+="Le fil "+(listAffecte.get(i).getIndex()+1)+" de longueur "+listAffecte.get(i).getLongueur()+
				" est coupe dans le rouleau "+(((Longueur)affecte.get(listAffecte.get(i))).getIndex()+1) +" de longueur "
				+((Longueur)affecte.get(listAffecte.get(i))).getLongueur()+" et il reste "+
				this.contrainte.get(((Longueur)affecte.get(listAffecte.get(i))).getIndex()).floatValue()+"\n";
			}
		}//else if(affecte.size()!=listAffecte.size())System.out.println("ERREUR DE TAILLE");
		text+=text2+"\nLe domaine des fils pas encore coupes:";
		text2="";
		
		if(!nonAffecte.isEmpty()){//Domaine des fils � d�couper
			int indice;
			for(int i=0;i<nonAffecte.size();i++){
				text2+="\n\nLe fil "+(listNonAffecte.get(i).getIndex()+1)+" de longueur "+listNonAffecte.get(i).getLongueur()+
				" a pour possibilite de coupage";
				for(int j=0;j<((ArrayList<Longueur>)nonAffecte.get(listNonAffecte.get(i))).size();j++){
					indice=((ArrayList<Longueur>)nonAffecte.get(listNonAffecte.get(i))).get(j).getIndex();
					if(contrainte.get(indice)-listNonAffecte.get(i).getLongueur()>=0){
						text2+=",\n dans le rouleau "+(((ArrayList<Longueur>)nonAffecte.get(listNonAffecte.get(i))).get(j).getIndex()+1)
						+" de longueur "+((ArrayList<Longueur>)nonAffecte.get(listNonAffecte.get(i))).get(j).getLongueur();
					
					}
				}
			}
		}
		text+=text2+"\n";
		Discours.setDiscours(getText()+"\n");
		Simple.branche=false;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}
	
}
