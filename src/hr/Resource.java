package hr;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Resource {

	private String name;
	private List<Skill> skills = new ArrayList<>();
	private static Map<String, Resource> all = new LinkedHashMap<>();

	
	public Resource() {
		super();
	}

	public static Resource instance(String name, Skill... skills) {
		Resource res = new Resource(name, skills);
		all.put(res.getName(), res);
		return res;
	}
	
	public static Resource get(String name) {
		Resource res = all.get(name);
		return res;
	}


	private Resource(String name, Skill ... skills) {
		this.name = name;
		for (Skill skill : skills) {
			this.skills.add(skill);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append("[");
		for (int i = 0; i < skills.size(); i++) {
			sb.append(skills.get(i).getName());
			if(i < skills.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean hasSkill(Skill skill) {
		return skills.contains(skill);
	}

}
