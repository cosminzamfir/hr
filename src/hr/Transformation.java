package hr;

public class Transformation {

	private Skill origin;
	private Skill target;
	private double cost;

	
	public Transformation() {
		super();
	}

	private Transformation(Skill origin, Skill target, double cost) {
		super();
		this.origin = origin;
		this.target = target;
		this.cost = cost;
	}

	public static Transformation instance(Skill origin, Skill target, double cost) {
		return new Transformation(origin, target, cost);
	}

	public Skill getOrigin() {
		return origin;
	}

	public void setOrigin(Skill origin) {
		this.origin = origin;
	}

	public Skill getTarget() {
		return target;
	}

	public void setTarget(Skill target) {
		this.target = target;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		return origin.getName() + "->" + target.getName() + "[cost=" + cost + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return origin.equals(((Transformation) obj).getOrigin()) && target.equals(((Transformation) obj).getTarget());
	}
	
	@Override
	public int hashCode() {
		return origin.hashCode() + target.hashCode();
	}

}
