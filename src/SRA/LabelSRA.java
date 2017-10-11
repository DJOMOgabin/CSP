package SRA;

import java.util.ArrayList;

import FiltrageSimple.Discours;
import FiltrageSimple.Longueur;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class LabelSRA {

	private Hashtable affectation;
	private ArrayList<Longueur> variable;
	private boolean consistance;
	private String text="",text2="";
	
	public LabelSRA(Hashtable affectation,ArrayList<Longueur> variable,boolean consistance) {
		// TODO Auto-generated constructor stub
		this.affectation=affectation;
		this.variable=variable;
		this.consistance=consistance;
		Ecrit();
	}

	public void Ecrit(){
		if(isConsistance()){
			if(getAffectation().size()==0)text2="";
			else{
				text2+="(("+getVariable().get(0).getIndex()+","+getVariable().get(0).getLongueur()+"),("+
				((Longueur)getAffectation().get(getVariable().get(0))).getIndex()+","+
				((Longueur)getAffectation().get(getVariable().get(0))).getLongueur()+"))";
				for(int i=1;i<getAffectation().size();i++){
					text2+=";(("+getVariable().get(i).getIndex()+","+getVariable().get(i).getLongueur()+"),("+
					((Longueur)getAffectation().get(getVariable().get(i))).getIndex()+","+
					((Longueur)getAffectation().get(getVariable().get(i))).getLongueur()+"))";
				}
			}
			text+="A = {"+text2+"}"+" est une affectation consistante";
			if(getAffectation().size()==getVariable().size()){
				text+=" et complete.\n";
				text+="----------------------------------------------------\n";
				SRA.compteur++;
			}else text+=".\n";
			Discours.setDiscours(text);
		}else{
			text2+="(("+getVariable().get(0).getIndex()+","+getVariable().get(0).getLongueur()+"),("+
			((Longueur)getAffectation().get(getVariable().get(0))).getIndex()+","+
			((Longueur)getAffectation().get(getVariable().get(0))).getLongueur()+"))";
			for(int i=1;i<getAffectation().size();i++){
				text2+=";(("+getVariable().get(i).getIndex()+","+getVariable().get(i).getLongueur()+"),("+
				((Longueur)getAffectation().get(getVariable().get(i))).getIndex()+","+
				((Longueur)getAffectation().get(getVariable().get(i))).getLongueur()+"))";
			}
			text+="A = {"+text2+"}"+" est une affectation inconsistante.\n";
			Discours.setDiscours(text);
		}
		
	}
	
	public Hashtable getAffectation() {
		return affectation;
	}

	public void setAffectation(Hashtable affectation) {
		this.affectation = affectation;
	}


	public ArrayList<Longueur> getVariable() {
		return variable;
	}

	public void setVariable(ArrayList<Longueur> variable) {
		this.variable = variable;
	}

	public boolean isConsistance() {
		return consistance;
	}

	public void setConsistance(boolean consistance) {
		this.consistance = consistance;
	}

	
}
