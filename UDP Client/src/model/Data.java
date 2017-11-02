package model;

import java.io.Serializable;
import java.util.*;

public class Data implements Serializable{
	
	public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<GameObject> enemies = new ArrayList<GameObject>();
}