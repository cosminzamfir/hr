package hr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HrApp {

	private class DoubleHolder {
		private double d;

		public DoubleHolder(double d) {
			this.d = d;
		}

		public double get() {
			return d;
		}

		public double add(double value) {
			d += value;
			return d;
		}

	}

	private boolean verbose = false;
	private List<Resource> resources;
	private List<Transformation> transformations = new ArrayList<>();
	private List<ResourceAssignment> assignments = new ArrayList<>();
	private List<Requirement> uncoveredSkills = new ArrayList<>();
	private List<Requirement> coveredSkills = new ArrayList<>();
	private List<Resource> availableResources;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: java SkillApp <input_file_name> [-v|--verbose]");
			System.exit(-1);
		}
		String fileName = args[0];
		Input input = new JsonParser().parseFile(fileName);
		HrApp app = new HrApp(input);
		if(args.length ==2 && (args[1].equals("-v") || args[0].equals("--verbose"))) {
			app.verbose = true;
		}
		app.process();
	}
	
	public static String run(Input input, boolean verbose) {
		HrApp app = new HrApp(input);
		app.verbose = verbose;
		return app.process();
	}

	private HrApp(Input input) {
		resources = input.getResources();
		availableResources = new ArrayList<>(resources);
		uncoveredSkills = input.getTarget();
		transformations = input.getTransformations();
		print(input.toString());
	}

	public String process() {
		Output res = new Output();
		print("Processing. Initial cost: " + totalCost() + " ...");

		if (uncoveredSkills.isEmpty()) {
			res.setAvailableResources(this.availableResources);

			String json = new JsonParser().getJson(res);
			print(res.toString());
			System.out.println(json);
		}

		Iterator<Resource> resourceIter = resources.iterator();
		while (resourceIter.hasNext()) {
			Resource resource = resourceIter.next();
			Skill targetSkill = null;
			double maxSaving = 0;
			double assignmentCost = 0;
			Iterator<Requirement> uncoveredSkillsIt = uncoveredSkills.iterator();
			while (uncoveredSkillsIt.hasNext()) {
				Skill skill = uncoveredSkillsIt.next().getSkill();
				double cost = assignmentCost(resource, skill);
				double saving = skill.getCost() - cost;
				if (saving > maxSaving) {
					targetSkill = skill;
					maxSaving = saving;
					assignmentCost = cost;
				}
			}
			if (maxSaving > 0) {
				skillCovered(targetSkill);
				ResourceAssignment ass = new ResourceAssignment(resource, targetSkill, assignmentCost);
				assignments.add(ass);
				print("Created assignment " + ass + ";Saving: " + maxSaving + "; New cost: " + totalCost());
				availableResources.remove(resource);
			}
		}
		res.setAvailableResources(this.availableResources);
		res.setUncoveredSkills(this.uncoveredSkills);
		res.setAssignments(assignments);
		res.setCoveredSkills(coveredSkills);
		res.setTotalCost(totalCost());

		String json = new JsonParser().getJson(res);
		System.out.println(json);
		return json;
	}

	/**
	 * remove one count of target skill from uncoverdSkills and add it to coveredSkills
	 * @param targetSkill
	 */
	private void skillCovered(Skill targetSkill) {
		Iterator<Requirement> it = uncoveredSkills.iterator();
		while (it.hasNext()) {
			Requirement req = it.next();
			if (!req.getSkill().equals(targetSkill)) {
				continue;
			}
			if (req.getCount() == 1) {
				it.remove();
			} else {
				req.setCount(req.getCount() - 1);
			}
		}
		for (Requirement req : coveredSkills) {
			if (req.getClass().equals(targetSkill)) {
				req.setCount(req.getCount() + 1);
				return;
			}
		}
		coveredSkills.add(new Requirement(targetSkill, 1));
	}

	private double totalCost() {
		DoubleHolder res = new DoubleHolder(0);
		uncoveredSkills.stream().forEach(req -> res.add(req.getSkill().getCost() * req.getCount()));
		assignments.stream().forEach(assignment -> res.add(assignment.getCost()));
		return res.get();
	}

	private double assignmentCost(Resource resource, Skill target) {
		if (resource.hasSkill(target)) {
			return 0;
		}
		double minCost = Double.POSITIVE_INFINITY;
		for (Skill skill : resource.getSkills()) {
			Transformation t = Transformations.instance(transformations).get(skill, target);
			if (t != null && t.getCost() < minCost) {
				minCost = t.getCost();
			}
		}
		return minCost;
	}
	
	private void print(String s) {
		if(verbose) {
			System.out.println(s);
		}
	}

}
