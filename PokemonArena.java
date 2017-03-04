/**
 *Samveg Shah
 *05/01/2014
 *PokemonArena.java
 *
 *
 *This program is used to play a visual text based pokemon game
 *This file does 3 major things
 *	-It creates the data using the textfile
 *	-It runs the main game
 *	-It does the damaging and figthing
 *
 */

import java.util.*;
import java.io.*;
public class PokemonArena {
	public static ArrayList <String> stat;//might have to change this later on
	private static Scanner kb=new Scanner(System.in);//so i don't have to make a new object for each method
	private static ArrayList <Pokemon> stuffchosen=new ArrayList <Pokemon> ();//keeps track of the pokemons chosen by the player
	private static ArrayList <Pokemon> enemies=new ArrayList <Pokemon>();//keeps track of the enemies, they are put in a random order
	private static int option;//numbers that are chosen
	private static int pokenum;//keeps track of the pokmon you are using
	private static int attnumber;//keeps track of what attack you want to do
	public static String choose(){
		//this method is used to first choose the four pokemons the player starts of with
		int num;
		for (int i=1; i<stat.size();i++){
			System.out.println(i+") "+stat.get(i).split(",")[0]);
		}
		while (true){//in order to make sure his number doesn't go out of bounds i put it in a loop till the user gets it right
			num=kb.nextInt();
			if (num>0 && num<stat.size()){
				break;
			}
			else{
				System.out.println("please pick a valid number using the options given");
			}
		}

		String pokemonchosen=stat.get(num);
		stat.remove(num);//to avoid choosing the same pokemon twice i remove the pokemon and show the user the options agian
		return pokemonchosen;

	}//easier and looks nicer in the main code, choses the main pokemons that the user wanted
    public static double getSpecialDamage(Pokemon a, Pokemon d){
   	//decides the speical damage by resistance and weakness
   	// a means the attacking pokemon and d means the defending pokemon
    	double specialdamage=1;//this the amount the overall damage will multiply by
    	if (a.getType().equals(d.getWeakness())){
    		specialdamage=2.0;
    		return specialdamage;
    	}
    	else if (a.getType().equals(d.getResistance())){
    		specialdamage=0.5;
    		return specialdamage;
    	}
    	else{
    		return specialdamage;
    	}
    }
    private static void setSpecial(Pokemon a,Pokemon d,int attnum){
    	//this method is used to set the specialstate that the attacking or defending pokemon is feeling
    	//it does the probability here and it 50/50 chance for both
			//these are the different status effects
    	if (a.getSpecial(attnum).equals("stun") && Math.random()*100>=50){
    		d.setState("Stun");
    	}
    	if (a.getSpecial(attnum).equals("wild card")&& Math.random()*100>=50){
    		a.setState("Wild Card");
    	}
    	if(a.getSpecial(attnum).equals("disable")){
    		for(int i=0;i<d.attacks.size();i++){
    			d.attacks.get(i).setDamage(10);
    			d.attacks.get(i).makeSure();//makes sure that the damage set counter is not less than zero
    		}
    	}
    	if (a.getSpecial(attnum).equals("wild storm") && Math.random()*100>=50){
    		d.setState("Wild Storm");
    	}
    	if (a.getSpecial(attnum).equals("recharge")){
    		a.setEnergy();//sets ten each time so lets call it twice instead, i should probably change this
    		a.setEnergy();
    	}
    }
   	public static boolean fight(Pokemon a,Pokemon d,int attacknumber,String porAi){
   		// i realize that you asked to put this in our Pokemon class instead
   		// but it was far too late for me to change it
   		// does the damage between 2 pokemons
   		//pokemon a is the attacking pokemon and pokemon d is the defending pokemon.
   		//porAi is used to determin wheter its a person or Ai as the attacking pokemon
   		// attacknumber is used in order to determin what attack the attaccking pokemon is going to do
		double specdamage= getSpecialDamage(a,d);//gets if there is any special damage being done by their types
		int num=(int)(Math.random()*100);
		setSpecial(a,d, attacknumber);//checks if the move will do any type of special damage to the enemy or the player
		if (!a.getState().equals("Stun")){//if the attacking pokemon is stunned it can't move
			if (d.getState().equals("Wild Storm")){//if the defending pokemon is in a wild strom it could get affected
				while (num>50){//50/50 chance of attacking
					if (a.getEnergy()>a.getEnergyCost(attacknumber)){
			    		d.setHp((int)((a.getDamage(attacknumber))*specdamage));
			    		System.out.println("During he wild storm, "+a.getName()+" used "+a.attacks.get(attacknumber).getName()+" and did "+a.getDamage(attacknumber)*specdamage+" damage to "+ d.getName()+"!");
			    		System.out.println(d.getName()+" has "+d.getHp()+".\n");
					}
					num=(int)(Math.random()*100);
				}
				return true;
			}
			else{
				if (!a.getState().equals("Wild Card")){//wild card doesn't allow for the person to attack
					if (a.getEnergy()>a.getEnergyCost(attacknumber)){
						a.useEnergy(a.getEnergyCost(attacknumber));//need to still do special move cases
			    		d.setHp((int)((a.getDamage(attacknumber))*specdamage));
			    		System.out.println(a.getName()+" used "+a.attacks.get(attacknumber).getName()+" and did "+a.getDamage(attacknumber)*specdamage+" damage to "+ d.getName()+"!");
			    		System.out.println(d.getName()+" has "+d.getHp()+".\n");//this is just all the interface part to make it easier for the player
			    		return true;
					}
		    		else{
			    		if (porAi.equals("p")){
			    			System.out.println("Pokemon is too tired to do this move!\nPick another move or another option.");
			    			playerMove();//needs to still work
			    		}
			    		else{
			    			System.out.println("The enemey pokemon is too tired to move!");
			    		}
			    		return false;
					}
				}
				else{
					//pokemon was affected by wildcard
					System.out.println(a.getName()+" was affected by wild card and cannot attack!");
					return false;
				}

			}

    	}
    	else{//if a pokemon is stunned it cannot attack
    		System.out.println(a.getName()+" is stunned!");
    		return false;
    	}
	}
	public static void choosebadguy(){
		//this method is used to choose the badguys
		//its just the remaining pokemons that are not chosen but picked in random order
		//so then the enemy pokemon will always be the first one in the list
		stat.remove(0);
		while (stat.size()>0){
			int badguynumber=(int)(Math.random()*stat.size());//generate badguy pokemons in a random order
			enemies.add(new Pokemon(stat.get(badguynumber)));
			stat.remove(badguynumber);
		}
	}


	public static void loadFile(){
		//this method is used to just openfile and put everything in a arraylist
		//i create the objects afterwards once you pickthe pokemon
		Scanner inFile=null;
    	try{
    		inFile=new Scanner (new BufferedReader (new FileReader("pokemon.txt")));
    	}
    	catch(IOException ex){
				//catch error if file was not created or file could not be properly loaded
    		System.out.println("Did you forget to make the pokemon.txt?");
    	}
    	int pos=1;
    	stat=new ArrayList();
    	while (inFile.hasNextLine()){
    			stat.add(inFile.nextLine());
    	}
	}//cleaner and looks nicer in the main loop
	public static void playerMove(){
		//this method is just give the user options to what he can do
		System.out.println("What will you do?\n 1)attack\n 2)retreat (pokemon must not be stunned)\n 3)pass");
		while (true){//this is to ensure that the user makes the right choice
			option=kb.nextInt();
			if (option>0 && option<4){
				break;
			}
			else{
				//ensure that a valid option is selected
				System.out.println("Please pick a valid option.");
			}
		}
		if (option==1){//this is the attack part
			stuffchosen.get(pokenum).getAttackName();
			while(true){//in order to make sure the personpicks a valid number
				attnumber=kb.nextInt();
				if (attnumber>=0 && attnumber<stuffchosen.get(pokenum).attacks.size()){
					break;
				}
				else{
					System.out.println("Please pick a option from the ones given.");
				}
			}
			fight(stuffchosen.get(pokenum),enemies.get(0),attnumber,"p");
		}
		else if (option==2){//makes sure if you're stunned than you cannot retreat
			if ("Stun".equals(stuffchosen.get(pokenum).getState())){
				System.out.println("Your pokemon is stunned, it cannot retreat!");
				playerMove();//will stay in the recursive loop while the player makes the correct decision
			}
			else{
				System.out.println("Pick your Pokemon!");
				for(int i=0;i<stuffchosen.size();i++){
					System.out.println(i+") "+stuffchosen.get(i).getName());
				}
				while(true){
					pokenum=kb.nextInt();
					if (pokenum>0 && pokenum<stuffchosen.size()){
						break;
					}
					else{
						System.out.println("Please pick a option from the ones given.");
					}
				}
			}
		}

		else if(option==3){
			//you do nothing if its pass
		}
	} //looks cleaner in the main while loop
	public static void BadGuyMove(){
		//this method decides on what attcck the bad guy does
		//what it does is first finds all the possible attacks
		//and then it picks one at random
		ArrayList <Integer> possible= getPossibleAttacks(enemies.get(0));
		int bgmove=(int)(Math.random()*possible.size());
		fight(enemies.get(0),stuffchosen.get(pokenum),bgmove,"ai");
	}//looks cleaner in the main while loop
	public static ArrayList<Integer> getPossibleAttacks(Pokemon a){
	//finds all the possible attacks, used for bad guy move
		ArrayList <Integer> possible= new ArrayList <Integer>();
		for (int i=0; i<a.attacks.size();i++){
			if (a.attacks.get(i).getEnergy()<a.getEnergy()){
				possible.add(i);
			}
		}
		return possible;
	}

	//this is the main class that will run the game
    public static void main(String [] args)throws IOException{
    	//this is the where i call all my methods and the main gameplay happens
    	//first i load the text file
    	//then i let the user pick and then bad guy pick
    	//then i let the bad guy pick
    	//then the fighting starts
    	//where its randomized on who starts and the turn alternates between the user and the computer
    	//finally if you lose all four pokemons or beat the computer the game ends
    	int count;
    	boolean SupremeTrainer=false;
    	loadFile();//just load the file and puts it in an arraylist
    	for(int i=0;i<4;i++){//you choose the pokemon you want (i remove the pokemon that was chosen and show the options again)
    		stuffchosen.add(new Pokemon(choose()));
    	}
    	choosebadguy();//randomly puts the badguys
	  	System.out.println("Pick your Pokemon!");
		for(int i=0;i<stuffchosen.size();i++){//asks the user what pokemon to start with
			System.out.println((i+1)+") "+stuffchosen.get(i).getName());
		}
		while (true){//make sure a valid number is chosen
			pokenum=kb.nextInt()-1;
			if (pokenum>=0 && pokenum<4){
				break;
			}
			else{
				System.out.println("Please pick a valid number");
			}
		}

		System.out.println(stuffchosen.get(pokenum).getName()+", I choose you!");
		count=((int)(Math.random()*2))+1;
		//Let the fighting begin
    	while (true){
    		//this is the main loop while the battle ends
    	{//checks for deaths and other stuff and prints all the stats
    		if (enemies.get(0).state()==false){
    			System.out.println(enemies.get(0).getName()+" has fainted!");
    			enemies.remove(0);
    			for (int i=0;i<stuffchosen.size();i++){//heals all the pokemons for the user
    				stuffchosen.get(i).heal();
    			}
    		}
    		if (enemies.size()<=0){
    			SupremeTrainer=true;
    			break;
    		}
    		if (stuffchosen.size()==0){
    			break;
    		}


    	}
    		if (count%2==0){
	    		if (stuffchosen.get(pokenum).state()){//.state() return whter its alive or not, if its not then you pick another pokemon
	    			playerMove();//User moves
	    		}
					//alternate turns
	    		if (enemies.get(0).state()){
	    			BadGuyMove();//Bad Guy Moves
	    		}
    		}
    		else{
    			if (enemies.get(0).state()){
	    			BadGuyMove();//Bad Guy Moves
	    		}
	    		if (stuffchosen.get(pokenum).state()){
	    			playerMove();//User moves
	    		}
    		}
    	{//This part here checks for players pokemon's death
    		if (stuffchosen.get(pokenum).state()==false){
    			System.out.println(stuffchosen.get(pokenum).getName()+" has fainted!\n pick another pokemon.");
    			stuffchosen.remove(pokenum);
    			for(int i=0;i<stuffchosen.size();i++){
					System.out.println((i+1)+") "+stuffchosen.get(i).getName());
					}
				//pick another pokemon
				while (true){
					pokenum=kb.nextInt()-1;
					if (pokenum>=0 && pokenum<stuffchosen.size()){
						break;
					}
					else{
						System.out.println("Pick an option from the given ones.");
					}
				}

				System.out.println(stuffchosen.get(pokenum).getName()+", I choose you!");
	    		}
    		stuffchosen.get(pokenum).setEnergy();
    		enemies.get(0).setEnergy();
    	    stuffchosen.get(pokenum).resetState();
    		enemies.get(0).resetState();
      		stuffchosen.get(pokenum).allStats();
    		enemies.get(0).allStats();
    		//stuns, and all the states have been reset

    	}
			//if you've won enough games, you will become a supreme trainer
    	if (SupremeTrainer){
    		System.out.println("Congratulations you are now a Supreme Trainer!");
    		}
    	else{
    		System.out.println("Thanks for play Pokemon Arena!");
    	}
   		}
    }



}
