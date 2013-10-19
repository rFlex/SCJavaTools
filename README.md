SCJavaTools
===========

Heal the pain, from your ass.

You'll found various modules in this project that are commonly used in java projects. This include modules that handle multithreading, string tools, dynamic document creation, simple http requests api, pathfinding, reflections...

Feel free to contribute and add anything you might find useful.

DynamicText
-----------

Create your document of any kind, dynamically.

The DynamicText module allows you to create any kind of text using variables, JSF style.

	DynamicText dynamicText = new DynamicText();
	dynamicText.setText("My CPU is a {processorName} running at {frequency}Mhz");
	dynamicText.put("processorName", "AMD K6");
	dynamicText.put("frequency", 300);
		
	System.out.println(dynamicText.toString());	
	
	// Will output "My CPU is a AMD K6 running at 300Mhz"
  
The Text is parsed just once, when calling the setText method or in the constructor if you use the constructor that takes the text directly. Therefore calling toString() several times, even when changing the input parameters will be fast. Calling toString() will generate the output string, depending on the input string and the input parameters.
The DynamicText currently supports the following instructions:

* {expr} : Execute the expression "expr" and apply toString() on the returned object. If the expression resolves to a null object, returns an empty string.
* {? expr :text} : Execute the expression "expr" that MUST returns to a Boolean object or a boolean. If the boolean returned is "true", the "text" will be outputed. The "str" is dynamic as well, thus you can declare expressions inside it.
* {? !expr :text} : Same as the previous one, but apply a NOT on the returned boolean.
* [expr->inputName :text] : Execute the expression "expr" that MUST returns a Collection or an Object[]. For each object returned in the collection, the "text" will be outputed. Inside the "text" scope, "inputName" will be the identifier that resolves to the currently processed object from the collection.

An expression always starts with the "inputName", then can have an infinite number of method calls or property access behind. A method call is of the form "methodName()", where the "methodName" will be directly called from the object. A method call can have parameters as well, as long as each of them are declared as an expression.
A property access is of the form "propertyName", where "propertyName" will be accessed using these methods (declared in the order in which they are tried):

* getPropertyName()
* isPropertyName()
* propertyName()

If the DynamicText fails to resolve a property or a method call, an exception will be thrown when using toString().

These examples are correct:

	{cpu}
	{cpu.getName()}
	{cpu.name}
	{cpu.setParent(parentCpu)}
	{cpu.parent.setName(cpu.name)}
	
These examples are NOT correct:

	{getCpu()}
	{cpu.parent = parentCpu}
	{cpu.setParent(getParent())}

Special inputs:

* #repeaterInputName : When using the repeater ([collection->repeaterInputName:text]), you can access the collection in an expression if you add a '#' in front of the given inputName.
* #repeaterInputNameNumber : When using the repeater, you can access the Integer that contains the current iteration number by adding a '#' in front of the given inputName and adding 'Number' at the end of the inputName.
* #if : Access the special object that contains some conditions. Check what you can use on this object here: https://github.com/rFlex/SCJavaTools/blob/master/src/me/corsin/javatools/dynamictext/If.java

Check the examples on https://github.com/rFlex/SCJavaTools/blob/master/src/me/corsin/javatools/dynamictext/Tests.java


TaskQueue
----------

Easy async multitasking.

The TaskQueue module allows you to easily handle tasks asynchronously in many threads.
The TaskQueue object keeps the tasks to do and flush them when calling flushTasks(). There is two way of using a TaskQueue:

* You already have your own loop, and want the tasks to be flushed in your thread loop. You will want to use the TaskQueue object directly. Call flushTasks() by yourself anywhere in your loop.
* You want the TaskQueue to handle the loop itself. Use SingleThreadedTaskQueue or MultiThreadedTaskQueue. They both will flush the tasks automatically for you. MultiThreadedTaskQueue will use the number of cores that your machine actually has when using the constructor with no parameter. SingleThreadedTaskQueue will always use one core. You MUST dispose them using the dispose() method though when you don't want to use them anymore. They will otherwise experience thread leaking.

There is also three way of adding a Task to a TaskQueue:

* You add an object that implements Runnable directly. The run() method will be called when flushing.
* You add an object that implements SimpleTask. The perform() method will be called when flushing. SimpleTask can have a listener to know when the task is ended. You can also get the exception (if any) thrown during the perform() execution by calling getThrownException(). If the task is not completed, calling the previous method will block until the result is available. You can ensure that this method will not block using isCompleted().
* You add an object that implements Task<T>. The perform() method will be called when flushing. Task differs from SimpleTask in the fact that the perform() methods must return a T object. You can get the returned value by using getReturnedObject(). Like getThrownException(), calling this method will block until the task is completed.
