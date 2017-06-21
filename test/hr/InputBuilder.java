package hr;

import java.util.ArrayList;
import java.util.List;

public class InputBuilder {

	private List<Skill> skills = new ArrayList<>();
	private List<Resource> resources = new ArrayList<>();
	private List<Requirement> target = new ArrayList<>();
	private List<Transformation> transformations = new ArrayList<>();

	public InputBuilder skills(Skill ...skills ) {
		for (Skill skill : skills) {
			this.skills.add(skill);
		}
		return this;
	}
	
	public InputBuilder resource(String name, Skill ...skills) {
		resources.add(Resource.instance(name, skills));
		return this;
	}
	
	public InputBuilder target(Skill skill, int count) {
		target.add(new Requirement(skill, count));
		return this;
	}
	
	public InputBuilder transformation(Skill origin, Skill target, double cost) {
		transformations.add(Transformation.instance(origin, target, cost));
		return this;
	}
	
	public Input build() {
		Input input = new Input();
		input.setResources(resources);
		input.setSkills(skills);
		input.setTargets(target);
		input.setTransformations(transformations);
		return input;
	}
}
