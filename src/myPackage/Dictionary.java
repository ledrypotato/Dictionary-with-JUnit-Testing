//AUTHORS: TAYLOR / SOCHAJ
//Mini-Projet L3 Info
//DATE: 04/21

package myPackage;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
	private String name; //attribut pour le nom du dictionnaire
	public HashMap<ArrayList<String>, ArrayList<String>> translationFR_EN; //Hash Map pour les traductions francais vers anglais
	public HashMap<ArrayList<String>, ArrayList<String>> translationEN_FR;	//Hash Map pour les traductions anglais vers francais
	
	//Constructor
	Dictionary(String name) {
		this.name = name;
		this.translationFR_EN = new HashMap<ArrayList<String>, ArrayList<String>>();
		this.translationEN_FR = new HashMap<ArrayList<String>, ArrayList<String>>();
	}
	
	//Getter
	public String getName() {
		return this.name;
	}
	
	//Setter
	public void setName(String name) {
		this.name = name;
	}
	
	//Add the translation to the Hash Map
	public void addTranslation(ArrayList<String> fr, ArrayList<String> en) {
		translationFR_EN.put(fr, en);
		translationEN_FR.put(en, fr);
	}
	
	//Combined getTranlsation and getMultipleTranslations into one method
	public ArrayList<String> getTranslation(ArrayList<String> word){
		ArrayList<String> resultEN_FR = translationEN_FR.get(word);
		ArrayList<String> resultFR_EN = translationFR_EN.get(word);
		ArrayList<String> temp = new ArrayList<String>();
		for(ArrayList<String> i : translationEN_FR.keySet()) {
			if(i.contains(word.get(0))) {
				temp = i;
				resultEN_FR = translationEN_FR.get(temp);
			}
		}
		if(resultEN_FR != null) { //check if the arraylist is empty
				return resultEN_FR; //if it is not then return it
		}
		if(resultFR_EN !=null) { //same principle here
				return resultFR_EN;
		}
		else return null;
	}
	
	//Display the entire Dictionary (Hash Map)
	public void showHashMap() {
		for(ArrayList<String> i : translationFR_EN.keySet()) {
			System.out.println("Francais: " + i + " Anglais: " + translationFR_EN.get(i));
		}
	}
}