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
import java.util.List;

public class Samples {

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
			this.features = new ArrayList<Samples.Feature>();
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
		
		
		public String getCommaForLineNumber(Integer lineNumber) {
			if (lineNumber == 0) {
				return "";
			}
			return ", ";
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
	
	private static void shortTest() {
		DynamicText dynamicText = new DynamicText();
		dynamicText.setText("My CPU is a {processorName} running at {frequency}Mhz\n");
		dynamicText.put("processorName", "AMD K6");
		dynamicText.put("frequency", 300);
		
		System.out.println(dynamicText.toString());
	}
	
	private static void methodCallTest() {
		CPU cpu = new CPU("AMD Athlon", 1.2);
		
		DynamicText dynamicText = new DynamicText();
		dynamicText.setText("My CPU is a {cpu.name} running at {cpu.frequency}Ghz\n");
		dynamicText.put("cpu", cpu);
		
		System.out.println(dynamicText.toString());
	}
	
	private static void repeaterTest() {
		CPU cpu = new CPU("AMD Athlon X2", 2.2);
		cpu.getCores().add(new Core(2.2));
		cpu.getCores().add(new Core(1.6));
		
		String text = "" +
				"My CPU is a {cpu.name} with a max speed of {cpu.frequency}Ghz\n" +
				"Which has {cpu.cores.size} cores:\n" +
				"[cpu.cores->core:Core #{#number} - Currently running at {core.frequency}Ghz\n]";
		
		DynamicText dynamicText = new DynamicText(text);
		dynamicText.put("cpu", cpu);
		
		System.out.println(dynamicText);
	}
	
	private static void conditionalTest() {
//		CPU cpu = new CPU("AMD Athlon 64", 2.8);
//		cpu.getCores().add(new Core(2.8));
//		cpu.getCores().add(new Core(2.4));
//		
//		String text = "" +
//				"My CPU is a {cpu.name} with a max speed of {cpu.frequency}Ghz\n" +
//				"Which has {cpu.cores.size} cores:\n" +
//				"[cpu.cores->core:Core #{#number} - Currently running at {core.frequency}Ghz\n]";
//		
//		DynamicText dynamicText = new DynamicText(text);
//		dynamicText.put("cpu", cpu);
//		
//		System.out.println(dynamicText);
	}
	
	private static void multiTest() {
		CPU cpu = new CPU("Intel core i7", 3.6);
		
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
				"Which has {cpu.cores.size} cores:\n" +
				"[cpu.cores->core:Core #{#number} - Currently running at {core.frequency}Ghz\n" +
				"\tFeatures: " +
				"[core.features->feature:{delegate.getCommaForLineNumber(#number)} {feature.name}]\n" +
				"]";
		
		DynamicText dynamicText = new DynamicText(text);
		dynamicText.put("cpu", cpu);
		dynamicText.put("delegate", new DynamicTextDelegate());
		
		System.out.println(dynamicText);
	}
	
	public static void main(String[] args) {
		shortTest();
		methodCallTest();
		conditionalTest();
		repeaterTest();
		multiTest();
	}
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
