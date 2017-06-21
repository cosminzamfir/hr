package hr;

public class ResourceAssignment {

	private Resource resource;
	private Skill skill;
	private double cost;

	
	public ResourceAssignment() {
		super();
	}

	public ResourceAssignment(Resource resource, Skill skill, double cost) {
		super();
		this.resource = resource;
		this.skill = skill;
		this.cost = cost;
	}

	public Resource getResource() {
		return resource;
	}

	public Skill getSkill() {
		return skill;
	}
	
	public double getCost() {
		return cost;
	}
	
	public String toString() {
		return resource.getName() + "->" + skill.getName();
	}

}
