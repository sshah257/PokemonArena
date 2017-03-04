/**
 *Samveg Shah
 *05/01/2014
 *Pokemon.java
 *This classis used to store the data for a pokemon
 *I know i was suppose to do the attack in this class but i finished the program andthen realized which was far too late
 *Most this methods are just getters and setters, which are self explainatory, so i will not be commenting on them a lot
 */

import java.util.*;
public class Pokemon {
	//ok so this class is used to keep track of all the stats of one pokemon
	private String name;
	private int hp;
	private int maxhp;
	private	int energy;
    private String type;
	private String resistance;
	private String weakness;
	private boolean alive;
	private String state;
	ArrayList <Attack> attacks=new ArrayList <Attack>();
    public Pokemon(String stats){
    	//this just starts everyone of according to the textfile and the energy always starts at 50
    	String [] pokestats=stats.split(",");
    	name=pokestats[0];
    	hp=Integer.parseInt(pokestats[1]);
    	maxhp=Integer.parseInt(pokestats[1]);
    	energy=50;
    	type=pokestats[3];
    	resistance=pokestats[3];
    	weakness=pokestats[4];
    	alive=true;
    	state="";
    	for (int i=0;i<Integer.parseInt(pokestats[5]);i++){
    		attacks.add(new Attack(pokestats[i*4+6],pokestats[i*4+9],Integer.parseInt(pokestats[i*4+7]),Integer.parseInt(pokestats[i*4+8])));
    	}
    }
    public String getName(){
    //method to help for display
		return name;
    }
    public int getHp(){
    //gets the hp of the pokemon
    	if (hp<0){
    		hp=0;
    	}
    	return hp;
    }
    public int getEnergy(){
    	return energy;
    }
    public String getWeakness(){
    	return weakness;
    }
    public String getResistance(){
    	return resistance;
	}
	public String getType(){
		return type;
	}
	public void useEnergy(int used){
		energy-=used;
	}
	public void setHp(int lost){
		hp-=lost;
		if (hp<=0){
			alive=false;
		}
	}
	public boolean state(){
		return alive;
	}
	public void heal(){
		if (hp<maxhp){
			hp+=20;
		}
		if (hp>maxhp){//make sure it doesn't go above the limit
			hp=maxhp;
		}
	}
	public void setEnergy(){
		if (energy<50){
			energy+=10;
		}
		if (energy>50){//make sure it doesn't go above the limit
			energy=50;
		}
	}
	public void setState(String s){
		state=s;
	}
	public String getState(){
		return state;
	}
	public void allStats(){
	// its useful for me display the current condition of the pokemon
		System.out.println("name: "+name+"\nhealth: "+hp+"\nenergy: "+energy+"\nstate: "+state+"\n");
	}
	public void getAttackName(){
	//gives the all the attack names so the user knows what attack he prefers
		for (int i=0;i<attacks.size();i++){
			System.out.println(i+") "+attacks.get(i).getName());
		}
	}
	public int getDamage(int attnum){
	//these are used for getting the damage of a certain attack
		return attacks.get(attnum).getDamage();
	}
	public int getEnergyCost(int attnum){
	//these are used for getting the energy cost of a certain attack
		return attacks.get(attnum).getEnergy();
	}
	public String getSpecial (int attnum){
	//these are used for getting the special of a certain attack
		return attacks.get(attnum).getSpecial();
	}
	public void resetState(){
	//resets the state after each turn so the pokemon is not trapped forever
		state="";
	}
	
}
