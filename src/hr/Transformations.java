package hr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Transformations {

	private List<Transformation> transformations = new ArrayList<>();
	
	private Transformations(List<Transformation> transformations) {
		this.transformations = transformations;
	}

	public static Transformations instance(List<Transformation> transformations) {
		return new Transformations(transformations);
	}
	
	public void addTransformation(Skill origin, Skill target, Double cost) {
		transformations.add(Transformation.instance(origin, target, cost));
	}
	
	public void setTransformations(List<Transformation> transformations) {
		this.transformations = transformations;
	}
	
	public List<Transformation> getForTarget(Skill target) {
		return transformations.stream().filter(t -> t.getTarget().equals(target)).collect(Collectors.toList()); 
	}
	
	public List<Transformation> getForOrigin(Skill origin) {
		return transformations.stream().filter(t -> t.getTarget().equals(origin)).collect(Collectors.toList()); 
	}
	
	public Transformation get(Skill from, Skill to) {
		return getForTarget(to).stream().filter(t -> t.getOrigin().equals(from)).findAny().orElseGet(() -> null);
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Transformations:\n================\n");
		transformations.stream().forEach(e -> res.append(e.toString()).append("\n"));
		return res.toString();
	}
	
	public List<Transformation> getTransformations() {
		return transformations;
	}
	
}
