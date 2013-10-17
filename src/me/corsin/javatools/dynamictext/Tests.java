/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// Tests.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 6:41:36 PM
////////

package me.corsin.javatools.dynamictext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.corsin.javatools.test.Test;
import me.corsin.javatools.test.TestCase;
import me.corsin.javatools.test.TestRunner;

public class Tests extends Test {

	////////////////////////
	// MODELS
	////////////////
	
	public static class Feature {
		private String name;
		
		public Feature(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class Core {
		private double frequency;
		private List<Feature> features;
		
		public Core(double frequency) {
			this.frequency = frequency;
			this.features = new ArrayList<Tests.Feature>();
		}

		public double getFrequency() {
			return frequency;
		}

		public void setFrequency(double frequency) {
			this.frequency = frequency;
		}

		public List<Feature> getFeatures() {
			return features;
		}

		public void setFeatures(List<Feature> features) {
			this.features = features;
		}
	}
	
	public static class CPU {
		private String name;
		private double frequency;
		private List<Core> cores;
		private boolean x64;
		
		public CPU(String processorName, double frequency) {
			this.name = processorName;
			this.frequency = frequency;
			this.cores = new ArrayList<Core>();
		}

		public String getName() {
			return name;
		}

		public void setName(String processorName) {
			this.name = processorName;
		}

		public double getFrequency() {
			return frequency;
		}

		public void setFrequency(double frequency) {
			this.frequency = frequency;
		}

		public List<Core> getCores() {
			return cores;
		}

		public void setCores(List<Core> cores) {
			this.cores = cores;
		}

		public void setX64(boolean x64) {
			this.x64 = x64;
		}

		public boolean isX64() {
			return this.x64;
		}
	}
	
	public static class DynamicTextDelegate {
		
		
		public boolean isFirstLine(Integer lineNumber) {
			return lineNumber == 0;
		}
		
		public boolean isLastLine(Integer lineNumber, Collection<?> collection) {
			return collection.size() == lineNumber;
		}
		
		public boolean isMoreThanZero(Integer number) {
			return number > 0;
		}
	}

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	////////////////////////
	// METHODS
	////////////////
	
	@TestCase
	public void shortTest() {
		DynamicText dynamicText = new DynamicText();
		dynamicText.setText("My CPU is a {processorName} running at {frequency}Mhz");
		dynamicText.put("processorName", "AMD K6");
		dynamicText.put("frequency", 300);
		
		
		String expectedOutput = "My CPU is a AMD K6 running at 300Mhz";
		
		ensureEquals(expectedOutput, dynamicText.toString());
	}
	
	@TestCase
	public void methodCallTest() {
		CPU cpu = new CPU("AMD Athlon", 1.2);
		
		DynamicText dynamicText = new DynamicText();
		dynamicText.setText("My CPU is a {cpu.name} running at {cpu.getFrequency()}Ghz"); // We could use {cpu.frequency} as well
		dynamicText.put("cpu", cpu);
		
		
		String expectedOutput = "My CPU is a AMD Athlon running at 1.2Ghz";
		
		ensureEquals(expectedOutput, dynamicText.toString());
	}
	
	@TestCase
	public void conditionalTest() {
		CPU cpu = new CPU("AMD Athlon X2", 2.2);
		cpu.getCores().add(new Core(2.2));
		cpu.getCores().add(new Core(1.6));
		
		String text = "" +
				"My CPU is a {cpu.name} with a max speed of {cpu.frequency}Ghz\n" +
				"{? cpu.x64 :64bits compatible}" +
				"{? !cpu.x64 :32bits compatible}";
		
		DynamicText dynamicText = new DynamicText(text);
		dynamicText.put("cpu", cpu);
		
		
		String expectedOutput = "" +
				"My CPU is a AMD Athlon X2 with a max speed of 2.2Ghz\n" +
				"32bits compatible";
		
		ensureEquals(expectedOutput, dynamicText.toString());
	}
	
	@TestCase
	public void repeaterTest() {
		CPU cpu = new CPU("AMD Athlon 64", 2.8);
		cpu.setX64(true);
		cpu.getCores().add(new Core(2.8));
		cpu.getCores().add(new Core(2.4));
		
		String text = "" +
				"My CPU is a {cpu.name} with a max speed of {cpu.frequency}Ghz\n" +
				"{? cpu.x64 :64bits compatible\n}" +
				"{? !cpu.x64 :32bits compatible\n}" +
				"Which has {cpu.cores.size} cores:\n" +
				"[cpu.cores->core:Core #{#number} - Currently running at {core.frequency}Ghz\n]";
		
		DynamicText dynamicText = new DynamicText(text);
		dynamicText.put("cpu", cpu);
		
	
		String expectedOutput = "" +
				"My CPU is a AMD Athlon 64 with a max speed of 2.8Ghz\n" +
				"64bits compatible\n" +
				"Which has 2 cores:\n" +
				"Core #0 - Currently running at 2.8Ghz\n" +
				"Core #1 - Currently running at 2.4Ghz\n";

		ensureEquals(expectedOutput, dynamicText.toString());
	}
	
	@TestCase
	public void multiTest() {
		CPU cpu = new CPU("Intel core i7", 3.6);
		cpu.setX64(true);
		
		Core c1 = new Core(3.6);
		c1.getFeatures().add(new Feature("SSE"));
		c1.getFeatures().add(new Feature("SSE2"));
		c1.getFeatures().add(new Feature("SSE3"));
		c1.getFeatures().add(new Feature("SSE4"));
		c1.getFeatures().add(new Feature("Turbo"));
		c1.getFeatures().add(new Feature("Hyperthreading"));
		
		
		Core c2 = new Core(2.4);
		c2.getFeatures().add(new Feature("SSE"));
		c2.getFeatures().add(new Feature("SSE2"));
		c2.getFeatures().add(new Feature("SSE3"));
		Core c3 = new Core(0.8);
		Core c4 = new Core(0.8);
		
		cpu.getCores().add(c1);
		cpu.getCores().add(c2);
		cpu.getCores().add(c3);
		cpu.getCores().add(c4);
		
		String text = "" +
				"My CPU is a {cpu.name} with a max speed of {cpu.frequency}Ghz\n" +
				"{? cpu.x64 :64bits compatible\n}" +
				"{? !cpu.x64 :32bits compatible\n}" +
				"Which has {cpu.cores.size} cores:\n" +
				"[cpu.cores->core:Core #{#number} - Currently running at {core.frequency}Ghz\n" + //Repeating on each core
				"{? delegate.isMoreThanZero(core.features.size):" + // Display the following only if it has more than one feature
				"\tFeatures: " +
				"[core.features->feature:{? !delegate.isFirstLine(#number) :, } {feature.name}]\n" +
				"}" + // End of the rendering condition
				"]";
		
		DynamicText dynamicText = new DynamicText(text);
		dynamicText.put("cpu", cpu);
		dynamicText.put("delegate", new DynamicTextDelegate());
		
		String expectedOutput = "" +
				"My CPU is a Intel core i7 with a max speed of 3.6Ghz\n" +
				"64bits compatible\n" +
				"Which has 4 cores:\n" +
				"Core #0 - Currently running at 3.6Ghz\n" +
				"\tFeatures:  SSE,  SSE2,  SSE3,  SSE4,  Turbo,  Hyperthreading\n" +
				"Core #1 - Currently running at 2.4Ghz\n" +
				"\tFeatures:  SSE,  SSE2,  SSE3\n" +
				"Core #2 - Currently running at 0.8Ghz\n" +
				"Core #3 - Currently running at 0.8Ghz\n";
		
		ensureEquals(expectedOutput, dynamicText.toString());
	}
	
	public static void main(String[] args) {
		TestRunner.runSingleTest(Tests.class);
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
