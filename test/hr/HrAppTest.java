package hr;

import org.junit.Assert;
import org.junit.Test;


public class HrAppTest {

	
	private static Skill java = Skill.instance("Java", 1000);
	private static Skill python = Skill.instance("Python", 600);
	private static Skill pm = Skill.instance("ProjectManagement", 1000);
	private static Skill db = Skill.instance("DB", 1200);
	private static Skill ml = Skill.instance("MachineLearning", 2000);
	private static Skill bi = Skill.instance("BusinessIntelligence", 2000);
	private static Skill bigdata = Skill.instance("BigData", 2000);

	
	@Test
	public void test1() throws Exception {
		Input input = new InputBuilder().skills(java).
				resource("Resource1", java).
				target(java, 1).build();
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 0, 1, 0, 1, 0);
	}

	@Test
	public void test2() throws Exception {
		Input input = new InputBuilder().skills(java).
				resource("Resource1", java).
				target(python, 1).build();
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, python.getCost(), 0, 1, 0, 1);
	}

	@Test
	public void test3() throws Exception {
		Input input = new InputBuilder().skills(java).
				resource("Resource1", java).
				target(python, 1).
				transformation(java, python, 99).
				build();
	
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 99, 1, 0, 1, 0);
	}

	@Test
	public void test4() throws Exception {
		Input input = new InputBuilder().skills(java).
				resource("Resource1", java).
				target(python, 2).
				transformation(java, python, 99).
				build();
	
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 99 + python.getCost(), 1, 0, 1, 1);
	}

	@Test
	public void test5() throws Exception {
		Input input = new InputBuilder().skills(java).
				resource("Resource1", java).
				resource("Resource2", java).
				target(python, 2).
				transformation(java, python, 99).
				build();
	
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 2 * 99, 2, 0, 2, 0);
	}
	
	@Test
	public void test6() throws Exception {
		Input input = new InputBuilder().skills(java, python, db, bigdata, ml, pm).
				resource("Resource1", java).
				resource("Resource2", python).
				resource("Resource3", db, bigdata).
				resource("Resource4", ml).
				target(python, 1).
				target(java, 1).
				target(pm, 2).
				transformation(db, pm, 99).
				transformation(ml, pm, 99).
				build();
	
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 2*99, 4, 0, 4, 0);
	}


	@Test
	public void test7() throws Exception {
		Input input = new InputBuilder().skills(java).
				resource("Resource1", java).
				resource("Resource2", python).
				resource("Resource3", db, bigdata).
				resource("Resource4", ml).
				target(python, 1).
				target(java, 1).
				target(pm, 2).
				build();
	
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 2*pm.getCost(), 2, 2, 2, 2);
	}

	private void checkOutput(Output output, double cost, int assignmentsSize, int availableResources, int coveredRequirement, int uncoveredRequirement) {
		Assert.assertEquals("Cost", cost, output.getTotalCost(), 0.01);
		Assert.assertEquals("Assignments size", assignmentsSize, output.getAssignments().size());
		Assert.assertEquals("Available resources", availableResources, output.getAvailableResources().size());
		Assert.assertEquals("Covered Requirements", coveredRequirement, output.getCoveredRequirementsCount());
		Assert.assertEquals("Uncovered Requirements", uncoveredRequirement, output.getUncoveredRequirementsCount());
	}

}
