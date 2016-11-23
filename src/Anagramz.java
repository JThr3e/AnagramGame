import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Anagramz {
	
	//TODO: Change these ugly default colors
	public static void changeJOP()
	{
		UIManager.put("Label.font", new FontUIResource (new Font("Tempus Sans ITC", Font.BOLD, 58)));
		UIManager.put("OptionPane.messageForeground",new Color(100,0,150));
		UIManager.put("TextField.background", Color.yellow);
		UIManager.put("TextField.font", new FontUIResource(new Font("Dialog", Font.ITALIC, 24)));
		UIManager.put("TextField.foreground", Color.red);
		UIManager.put("Panel.background",new Color(255,0,132));
		UIManager.put("OptionPane.background",new Color(255,0,0));
		UIManager.put("Button.background",new Color(132,112,255));
		UIManager.put("Button.foreground", new Color(72,61,139));
		UIManager.put("Button.font", new FontUIResource	(new Font("Tempus Sans ITC", Font.BOLD, 14)));
	}
	
	public static void main(String [] args)
	{
		double start = System.currentTimeMillis(); 
		changeJOP();
		
		//Maps lexicographically ordered characters (The aID) to words that use them (anagrams)
		HashMap<String, ArrayList<Word>> anagrams = new HashMap<String, ArrayList<Word>>();
		try {
			//Scan Through a file of words in the English language to organize words into teh HashMap
			Scanner file = new Scanner(new File("googleWords.txt"));
			while (file.hasNextLine()) {
				String word = file.nextLine();
				char a[] = word.toCharArray();
				Arrays.sort(a);
				String aID = "";
				for(int i = 0; i < a.length; i++)
				{
					aID += a[i];
				}
				ArrayList<Word> ana = anagrams.get(aID);
				if(ana == null){
					ana = new ArrayList<Word>();
				}
				ana.add(new Word(word, aID));
				anagrams.put(aID, ana);
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//This code counts up the number of anagrams found in a certain amount of time
		//(mostly to debug and test efficiency)
		int count = 0;
		for(String s: anagrams.keySet()){
			ArrayList<Word> words = anagrams.get(s);
			if(words.size() > 1){
				count++;
			}
		}
		double stop = System.currentTimeMillis(); 
		System.out.println(count+" anagrams found in "+(stop-start)+" ms");
		/////
		
		
		//Find anagrams of size 8 or more to use as the base word
		HashSet<String> usableAnagrams = new HashSet<String>();
		for(String aID: anagrams.keySet()){
			if(aID.length() > 7 && anagrams.get(aID).size() > 1){
				usableAnagrams.add(aID);
			}
		}
		
		//Randomly select an anagram ID and use that to calculate all 
		//anagrams that can be formed from a combination of letters of
		//the chosen aID
		String chosenAID = "";
		HashSet<String> wordsWithChosenAID = new HashSet<String>();
		HashSet<String> allPossibleAIDCombos = new HashSet<String>();
		do{
			//Random Select
			int randAID = (int)(Math.random()*usableAnagrams.size());
			int index = 0;
			for(String aID : usableAnagrams){
				index++;
				if(index == randAID) chosenAID = aID;
			}
			
			//Find all aID combos using a Utility
			for(int i = 3; i < chosenAID.length(); i++){
				HashSet<String> combos = new HashSet<String>();
				Combinations.findCombinations(chosenAID.toCharArray(), chosenAID.length(), i, combos);
				allPossibleAIDCombos.addAll(combos);
			}
			
			//Find all possible words
			for(String aID : allPossibleAIDCombos){
				if(anagrams.get(aID) != null){
					for(Word s : anagrams.get(aID)){
						wordsWithChosenAID.add(s.toString());
					}
				}
			}
		}while(wordsWithChosenAID.size() < 20); //If less than 20 words redo
		
		//print stuff to terminal
		System.out.println(chosenAID);
		for(String word : wordsWithChosenAID){
			System.out.println(word);
		}
		System.out.println(wordsWithChosenAID.size());
		
		//TODO: Improve UI (by a lot)
		//TODO: Add a time limit
		//TODO: Add point system... maybe?
		boolean quit = false;
		while(wordsWithChosenAID.size() > 0 && !quit){
			String in = JOptionPane.showInputDialog("Enter words that use these letters: "+chosenAID+"\n"+wordsWithChosenAID.size()+" words left."+"\n Enter ZZZZZ to give up!");
			wordsWithChosenAID.remove(in);
			if(in.equals("ZZZZZ")){
				String missed = "";
				int mod = 0;
				for(String s : wordsWithChosenAID){
					mod++;
					missed += (s + " ");
					if(mod%20 == 0) missed += "\n";
				}
				JOptionPane.showMessageDialog(null, "You missed\n"+missed);
				quit = true;
			}
		}
	}
	
	
}

