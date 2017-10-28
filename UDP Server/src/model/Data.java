package model;

import java.io.Serializable;
import java.util.*;

public class Data implements Serializable{
	
	public ArrayList<Player> players = new ArrayList<Player>();

	public ArrayList<Player> getPlayers(){
		return players;
	}
}