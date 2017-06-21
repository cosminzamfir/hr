package hr;

public class Requirement {

	private Skill skill;
	private int count;

	public Requirement() {
		super();
	}

	public Requirement(Skill skill, int count) {
		super();
		this.skill = skill;
		this.count = count;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return skill.getName() + "[" + count + "]";
	}

}
