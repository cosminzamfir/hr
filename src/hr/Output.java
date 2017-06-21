package hr;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Output {

	private List<ResourceAssignment> assignments = new ArrayList<>();
	private List<Resource> availableResources = new ArrayList<>();
	private List<Requirement> uncoveredSkills = new ArrayList<>();
	private List<Requirement> coveredSkills = new ArrayList<>();
	private double totalCost;

	public Output() {
	}

	public void addAssignment(ResourceAssignment value) {
		this.assignments.add(value);
	}

	public List<ResourceAssignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<ResourceAssignment> assignments) {
		this.assignments = assignments;
	}

	public List<Resource> getAvailableResources() {
		return availableResources;
	}

	public void setAvailableResources(List<Resource> availableResources) {
		this.availableResources = availableResources;
	}

	public List<Requirement> getUncoveredSkills() {
		return uncoveredSkills;
	}

	public void setUncoveredSkills(List<Requirement> uncoveredSkills) {
		this.uncoveredSkills = uncoveredSkills;
	}

	public List<Requirement> getCoveredSkills() {
		return coveredSkills;
	}

	public void setCoveredSkills(List<Requirement> coveredSkills) {
		this.coveredSkills = coveredSkills;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Assignments:\n===============\n");
		coveredSkills.stream().forEach(e -> res.append(e).append("\n"));

		res.append("\n");
		res.append("Uncovered skills:\n=================\n");
		uncoveredSkills.stream().forEach(e -> res.append(e.getSkill().getName() + "[" + e.getCount() + "]").append("\n"));

		res.append("\n");
		res.append("Available resources:\n====================\n");
		if (availableResources.isEmpty()) {
			res.append("None\n");
		} else {
			availableResources.stream().forEach(e -> res.append(e).append("\n"));
		}

		res.append("\n");
		res.append("Total cost:" + totalCost + "\n");
		return res.toString();
	}
	
	@JsonIgnore
	public int getUncoveredRequirementsCount() {
		return uncoveredSkills.stream().mapToInt(req -> req.getCount()).sum();
	}

	@JsonIgnore
	public int getCoveredRequirementsCount() {
		return coveredSkills.stream().mapToInt(req -> req.getCount()).sum();
	}

}
