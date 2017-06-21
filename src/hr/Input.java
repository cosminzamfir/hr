package hr;

import java.util.ArrayList;
import java.util.List;

public class Input {

	private List<Skill> skills = new ArrayList<>();
	private List<Resource> resources = new ArrayList<>();
	private List<Requirement> target = new ArrayList<>();
	private List<Transformation> transformations = new ArrayList<>();

	public Input() {
		super();
	}

	public void addResource(Resource resource) {
		resources.add(resource);
	}

	public void addSkill(Skill skill) {
		skills.add(skill);
	}

	public void addTarget(Skill skill, int count) {
		for (Requirement target : target) {
			if (target.getSkill().equals(skill)) {
				target.setCount(target.getCount() + count);
				return;
			}
		}
		target.add(new Requirement(skill, count));

	}

	public void addTransformation(Transformation transformation) {
		this.transformations.add(transformation);
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Requirement> getTarget() {
		return target;
	}

	public void setTargets(List<Requirement> target) {
		this.target = target;
	}

	public List<Transformation> getTransformations() {
		return transformations;
	}

	public void setTransformations(List<Transformation> transformations) {
		this.transformations = transformations;
	}
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Skills:\n==================\n");
		skills.stream().forEach(e -> res.append(e).append("\n"));

		res.append("Current resources:\n==================\n");
		resources.stream().forEach(e -> res.append(e).append("\n"));

		res.append("\n");
		res.append("Target skills:\n==============\n");
		target.stream().forEach(req -> res.append(req.getSkill().getName() + "[" + req.getCount() + "]").append("\n"));

		res.append("\n");
		res.append(Transformations.instance(transformations).toString());

		return res.toString();
	}


}
