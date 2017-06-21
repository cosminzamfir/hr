package hr;

import java.util.LinkedHashMap;
import java.util.Map;

public class Skill {

	private String name;
	private double cost;
	private static Map<String, Skill> all = new LinkedHashMap<>();

	
	public Skill() {
		super();
	}

	private Skill(String name, double cost) {
		super();
		this.name = name;
		this.cost = cost;
	}

	public static Skill instance(String name, double cost) {
		if(all.containsKey(name)) {
			throw new RuntimeException("Skill " + name + " already defined");
		}
		Skill res = new Skill(name, cost);
		all.put(name, res);
		return res;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public static Skill get(String name) {
		Skill res = all.get(name); 
		if(res == null) {
			throw new RuntimeException("No skill with name " + name + " defined.");
		}
		return res;
	}
	
	@Override
	public String toString() {
		return name + "[cost=" + cost + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return name.equals(((Skill) obj).getName());
	}
	
}
