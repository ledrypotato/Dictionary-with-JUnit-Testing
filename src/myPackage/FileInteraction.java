//AUTHORS: TAYLOR / SOCHAJ
//Mini-Projet L3 Info
//DATE: 04/21

package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInteraction {
	
	private Dictionary d;
	
	FileInteraction(){
		d = new Dictionary("null");
	}
	
	void setDictionary(Dictionary d) {
		this.d = d;
	}
	
	public Dictionary fileParser(String filePath) {
		try {
			File myFile = new File(filePath);
			Scanner myReader = new Scanner(myFile);
			int line = 0; //initiliase the line number at 0
			while(myReader.hasNextLine()) { //while there are lines in the text file
				if(line == 0) { //if we're reading the first line it's the dictionary name
					d.setName(myReader.nextLine());
				}
				ArrayList<String> fr = new ArrayList<String>();
				ArrayList<String> en = new ArrayList<String>();
				String data = myReader.nextLine();
				String delims = "[;]"; //the delimiter is ";"
				String [] res = data.split(delims); //create an array "res"
				
				
				if(res[1] != null) {
					if(res[1].contains(",")) { //check if res[1] contains a "," means that there is more than 1 word ie: res[1] = boat,ship
						String [] individualWords = res[1].split(","); //if that is the case then we split according to "," and get the individual words
						for(String i : individualWords) { //for all the words
							en.add(i); //add them to our english arraylist
						}
					}
					else {
						en.add(res[1]);	//add the second value of "res" to the ArrayList "fr"	
					}
				}
				fr.add(res[0]); //add the first value of "res" to the ArrayList "fr"
					
				d.addTranslation(fr, en); //call addTranslation from Dictionary class with "fr" and "en" as parameters
				line++; //increment the line number
			}
			myReader.close(); //close the Scanner
			return d; //return the Dictionary
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
			return null;
		}
	}
	
	//This method returns true if the word is in the dictionary, false otherwise. (language specified in parameter)
	public static boolean wordInDictionary(String word, Scanner dictionary, String language) {
		
		dictionary.nextLine(); //skip the dictionary name (line 0)
		for(int i = 0; dictionary.hasNextLine() !=false ; i++) {
			String delims = "[;]"; //the delimiter is ";"
			String [] temp = dictionary.nextLine().split(delims);
			
			if(language.equals("french")) { //easier to test if the language is french since the keys are unique
				if(temp[0].equals(word)) {
					return true;
				}
			}
			if(language.equals("english")) { //if the language is english 
				if(temp[1].contains(",")) { //if there are multiple translations for the same word
					String [] individualWords = temp[1].split(",");
					for(String j : individualWords) {
						if(j.equals(word)) { //we have to check both translations ie: navire;ship,boat
							return true;
						}
					}
				}
				else { //if there is only one translation it's the same as the french if statement above
					if(temp[1].equals(word)) {
						return true;
					}
				}
			}	
		}
		return false; //if none of the conditional statements are met then the word is not in the dictionary -> return false
	}
	
	//This method replaces a specific part of a text file
	public static void replaceLine(String word, String filler) {
		
		FileInteraction rf = new FileInteraction();
		String filePath = "src/myPackage/dictionary_fr_en.txt";
		rf.d = rf.fileParser(filePath);
		try {
			BufferedReader file = new BufferedReader(new FileReader(filePath));
			StringBuffer inputBuffer = new StringBuffer();
			String line;
			
			while((line = file.readLine()) != null) { //while we're not at the end of the file
				inputBuffer.append(line + "\n"); //append the line and end line "\" to our buffer
			}
			file.close();
			String inputStr = inputBuffer.toString();
			inputStr = inputStr.substring(0, inputStr.length()-1); //trim of the end line on the last line
			
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(word);
			temp.addAll(rf.d.getTranslation(temp));

			//this part allows us to replace "bois;wood" with "bois;wood,log"
			String toReplace = temp.get(0) + ";" + temp.get(1) + "," + filler;
			inputStr = inputStr.replace(temp.get(0) + ";" + temp.get(1), toReplace);

			FileOutputStream fileOut = new FileOutputStream(filePath);
			fileOut.write(inputStr.getBytes()); //write inputStr to the file
			fileOut.close(); //close the stream
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Problem reading file.");
		}
	}
	
	//Main method that runs the entire program
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner input = new Scanner(System.in); 
		int choice=0;
		 do{ //if the choice is not valid continue to ask the user
			FileInteraction rf = new FileInteraction();
			String filePath = "src/myPackage/dictionary_fr_en.txt";
			rf.d = rf.fileParser(filePath);
			Scanner myScanner = new Scanner(new File(filePath)); 
			  
			System.out.println("Choose an option:\n1. Translate a word\n2. Add a translation\n3. See all translations\n4. Exit program");
			choice = input.nextInt();
			if(choice < 0 || choice > 4) {
			System.out.println("Please choose a valid number");
			choice = input.nextInt();
			}
			System.out.println(choice);

		//****** TRANSLATE A WORD ******
		if(choice == 1) {
			System.out.println("1. French to English\n2. English to French");
			int languageChoice = input.nextInt();
			input.nextLine(); //called after nextInt() to ignore the rest of the line
			while(languageChoice < 1 || languageChoice > 2) {
				System.out.println("Please choose a valid number");
				languageChoice = input.nextInt();
				input.nextLine();
			}
			
			//****** FRENCH TO ENGLISH ******
			if(languageChoice == 1) {
				System.out.println("Enter the word in French to translate :");
				ArrayList<String> wordToTranslate = new ArrayList<String>(1);
				String word = input.nextLine();
				wordToTranslate.add(word);
				if(wordInDictionary(word, myScanner, "french")) {
					System.out.println(word + " in English is " + rf.d.getTranslation(wordToTranslate));
				}
				else {
					System.out.println(word + " is not in the dictionary");
				}
				continue;
			}
			
			//****** ENGLISH TO FRENCH ******
			if(languageChoice == 2) {
				System.out.println("Enter the word in English to translate:");
				ArrayList<String> wordToTranslate = new ArrayList<String>(1);
				String word = input.nextLine();
				wordToTranslate.add(word);
				if(wordInDictionary(word, myScanner, "english")) {
					System.out.println(word + " in French is " + rf.d.getTranslation(wordToTranslate));
				}
				else {
					System.out.println(word + " is not in the dictionary");
				}
			}
		}
		
		//****** ADD TRANSLATION ******
		if(choice == 2) {
			input.nextLine();
			System.out.println("Enter the English word:");
			String wordEN = input.nextLine();
			System.out.println("Enter the French word:");
			String wordFR = input.nextLine();
			ArrayList<String> en = new ArrayList<String>(1);
			ArrayList<String> fr = new ArrayList<String>(1);
			en.add(wordEN);
			fr.add(wordFR);
			rf.d.addTranslation(fr, en);
			if(!wordInDictionary(wordEN, myScanner, "english")) { //check if the word we are adding is not already in the dictionary
				try {
					FileWriter fw = new FileWriter(filePath, true);
					Scanner myScanner1 = new Scanner(new File(filePath));
					if(wordInDictionary(wordFR, myScanner1, "french")) { //if the french word is already in the dionary we have to go to that line and a "," followed by the English word
						replaceLine(wordFR, wordEN);
					}
					else { //otherwise write at the end of the file
						fw.write("\n" + wordFR + ";" + wordEN);
					}
					fw.close();
				}
				catch(IOException e) {
					System.out.println("An error occured");
					e.printStackTrace();
				}
				System.out.println("The translation: " + wordFR + " -> " + wordEN + " has been added to the dictionary.");
			}
			else { //if it is already in the dictionary, let the user know
				System.out.println("The dictionary already knows this translation.");
			}
		}
		
		//****** DISPLAY THE DICTIONARY ******
		if(choice == 3) {
			rf.d.showHashMap(); //call showHashMap method
		}
		
		//****** EXIT THE PROGRAM ******
		if(choice == 4) {
			System.out.println("You have exited the program.");
		}
	}while(choice!=4);
		input.close();
	}
}