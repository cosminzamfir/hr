package hr;

import org.junit.Assert;
import org.junit.Test;


public class HrAppTest {

	
	private static Skill java = Skill.instance("Java", 1000);
	private static Skill python = Skill.instance("Python", 1000);
	private static Skill pm = Skill.instance("ProjectManagement", 1000);
	private static Skill db = Skill.instance("DB", 1000);
	private static Skill ml = Skill.instance("MachineLearning", 1000);
	private static Skill bi = Skill.instance("BusinessIntelligence", 1000);
	private static Skill bigdata = Skill.instance("BigData", 1000);

	
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


	@Test
	public void test8() throws Exception {
		Input input = new InputBuilder().skills(java, python, db, bi, bigdata, ml,pm).
				resource("Resource1", java).
				resource("Resource2", python).
				resource("Resource3", db).
				resource("Resource4", pm).
				resource("Resource5", bi).
				resource("Resource6", bigdata).
				resource("Resource7", ml).
				
				target(java, 2).
				target(python, 2).
				target(db, 2).
				target(pm, 2).
				target(bi, 2).
				target(bigdata, 2).
				target(ml,2).
				build();
	
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, true);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 7000, 7, 0, 7, 7);
	}

	@Test
	public void test9() throws Exception {
		Input input = new InputBuilder().skills(java, python, db, bi, bigdata, ml,pm).
				resource("Resource1", java).
				resource("Resource2", python).
				resource("Resource3", db).
				resource("Resource4", pm).
				resource("Resource5", bi).
				resource("Resource6", bigdata).
				resource("Resource7", ml).
				
				resource("Resource1.1", java).
				resource("Resource2.1", python).
				resource("Resource3.1", db).
				resource("Resource4.1", pm).
				resource("Resource5.1", bi).
				resource("Resource6.1", bigdata).
				resource("Resource7.1", ml).

				target(java, 1).
				target(python, 1).
				target(db, 1).
				target(pm, 1).
				target(bi, 1).
				target(bigdata, 1).
				target(ml,8).
				
				transformation(java, ml, 99).
				transformation(python, ml, 99).
				transformation(pm, ml, 99).
				transformation(db, ml, 99).
				transformation(bi, ml, 99).
				transformation(bigdata, ml, 99).
				build();
	
		System.out.println(new JsonParser().getJson(input));
		String s = HrApp.run(input, false);
		Output output = new JsonParser().parseString(s, Output.class);
		checkOutput(output, 594, 14, 0, 14, 0);
	}

	private void checkOutput(Output output, double cost, int assignmentsSize, int availableResources, int coveredRequirement, int uncoveredRequirement) {
		Assert.assertEquals("Cost", cost, output.getTotalCost(), 0.01);
		Assert.assertEquals("Assignments size", assignmentsSize, output.getAssignments().size());
		Assert.assertEquals("Available resources", availableResources, output.getAvailableResources().size());
		Assert.assertEquals("Covered Requirements", coveredRequirement, output.getCoveredRequirementsCount());
		Assert.assertEquals("Uncovered Requirements", uncoveredRequirement, output.getUncoveredRequirementsCount());
	}

}
