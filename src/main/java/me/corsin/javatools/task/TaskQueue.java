/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// TaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 7:33:18 PM
////////

package me.corsin.javatools.task;

import java.io.Closeable;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A TaskQueue is a very thread safe object that holds a Queue (FIFO) of task to process.
 * The default implementation does not process the task directly, they need to be flushed using
 * flushTasks() (for processing all the pending task) or handleNextTask() (for processing the next task).
 * Tasks can be any Runnable object. If you want a TaskQueue that process the task automatically, you can
 * use one of the provided subclasses ThreadedConcurrentTaskQueue, ThreadedSequentialTaskQueue or ThreadedPeriodicTaskQueue.
 * @author simoncorsin
 *
 */
public class TaskQueue implements Closeable {

	////////////////////////
	// VARIABLES
	////////////////

	private static TaskQueue mainTaskQueue;
	// This variable is notified each time a Runnable is added or when it is empty
	final private Queue<Runnable> tasks;
	// This variable is notified when it reaches zero
	private Object tasksReachesZero;
	private int runningTasks;
	private boolean closed;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public TaskQueue() {
		this.tasks = new ArrayDeque<Runnable>();
		this.tasksReachesZero = new Object();
		this.runningTasks = 0;
	}

	////////////////////////
	// METHODS
	////////////////

	private Runnable getNextTask() {
		Runnable task = null;
		synchronized (this.tasks) {
			if (!this.tasks.isEmpty()) {
				task = this.tasks.poll();
				if (this.tasks.isEmpty()) {
					this.tasks.notifyAll();
				}
			}
		}
		return task;
	}

	/**
	 * Process the next pending Task synchronously.
	 * @return true if a task has been processed, false otherwise (no task is ready to be processed)
	 */
	public boolean handleNextTask() {
		Runnable task = this.getNextTask();

		if (task != null) {
			synchronized (this.tasksReachesZero) {
				this.runningTasks++;
			}
			try {
				task.run();
			} catch (Throwable t) {
				t.printStackTrace();
			}

			synchronized (this.tasksReachesZero) {
				this.runningTasks--;
				if (this.runningTasks == 0) {
					this.tasksReachesZero.notifyAll();
				}
			}

			synchronized (task) {
				task.notifyAll();
			}
			return true;
		}
		return false;
	}

	/**
	 * Process synchronously every pending task
	 */
	public void flushTasks() {
		while (!this.closed && this.handleNextTask()) {

		}
	}

	@Override
	public void close() {
		this.closed = true;
		synchronized (this.tasks) {
			this.tasks.clear();
			this.tasks.notifyAll();
		}
	}

	/**
	 * Called when one task has been added to the TaskQueue
	 */
	protected void onTaskAdded() {

	}

	/**
	 * Called when the TaskQueue has now at least one pending task ready to be processed
	 */
	protected void onTaskBecameNotEmpty() {

	}

	/**
	 * Add a Task to the queue. It will be run when flushTasks() is called (this is automatically called by
	 * some direct subclasses like ThreadedSequentialTaskQueue)
	 * @param the runnable to be executed
	 * @return the runnable, as a convenience method
	 */
	public <T extends Runnable> T executeAsync(T runnable) {
		synchronized (this.tasks) {
			this.tasks.add(runnable);
			// Notify that a task has been added
			this.tasks.notifyAll();

			if (this.tasks.size() == 1) {
				this.onTaskBecameNotEmpty();
			}

			this.onTaskAdded();
		}
		return runnable;
	}

	/**
	 * Add a Task to the queue and wait until it's run. It is guaranteed that the Runnable will be processed
	 * when this method returns.
	 * @param the runnable to be executed
	 * @return the runnable, as a convenience method
	 */
	public <T extends Runnable> T executeSync(T runnable) {
		synchronized (runnable) {
			this.executeAsync(runnable);
			try {
				runnable.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return runnable;
	}

	/**
	 * Add a Task to the queue and wait until it's run. The Task will be executed after "inMs" milliseconds. It is guaranteed that the Runnable will be processed
	 * when this method returns.
	 * @param the runnable to be executed
	 * @param inMs The time after which the task should be processed
	 * @return the runnable, as a convenience method
	 */
	public <T extends Runnable> T executeSyncTimed(T runnable, long inMs) {
		try {
			Thread.sleep(inMs);
			this.executeSync(runnable);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return runnable;
	}

	/**
	 * Add a Task to the queue. The Task will be executed after "inMs" milliseconds.
	 * @param the runnable to be executed
	 * @param inMs The time after which the task should be processed
	 * @return the runnable, as a convenience method
	 */

	public <T extends Runnable> T executeAsyncTimed(T runnable, long inMs) {
		final Runnable theRunnable = runnable;

		// This implementation is not really suitable for now as the timer uses its own thread
		// The TaskQueue itself should be able in the future to handle this without using a new thread
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				TaskQueue.this.executeAsync(theRunnable);
			}
		}, inMs);

		return runnable;
	}

	/**
	 * Wait until the TaskQueue has a Task ready to process. If the TaskQueue is closed this method will also return
	 * but giving the value "false".
	 * @return true if the TaskQueue has a Task ready to process, false if it has been closed
	 */
	protected boolean waitForTasks() {
		synchronized (this.tasks) {
			while (!this.closed && !this.hasTaskPending()) {
				try {
					this.tasks.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return !this.closed;
	}

	/**
	 * Wait that every pending task are processed.
	 * This method will return once it has no pending task or once it has been closed
	 */
	public void waitAllTasks() {
		synchronized (this.tasks) {
			while (this.hasTaskPending()) {
				try {
					this.tasks.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			synchronized (this.tasksReachesZero) {
				if (this.runningTasks > 0) {
					try {
						this.tasksReachesZero.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * @return the currentTaskQueue if the current Thread was created by one of the ThreadedTaskQueue subclass
	 */
	public static TaskQueue currentTaskQueue() {
		Thread currentThread = Thread.currentThread();
		TaskQueue taskQueue = mainTaskQueue;

		if (currentThread instanceof TaskQueueThread) {
			TaskQueueThread taskQueueThread = (TaskQueueThread)currentThread;
			taskQueue = taskQueueThread.getTaskQueue();
		}

		return taskQueue;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public boolean hasTaskPending() {
		return !this.tasks.isEmpty();
	}

	public int getRunningTasks() {
		return this.runningTasks;
	}

	public boolean isClosed() {
		return this.closed;
	}

	public static void setMainTaskQueue(TaskQueue taskQueue) {
		mainTaskQueue = taskQueue;
	}

	public static TaskQueue getMainTaskQueue() {
		return mainTaskQueue;
	}
}
