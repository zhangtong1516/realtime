package scheduler;

import scheduler.ui.MainWindow;

public class Scheduler extends Thread {
	
	private TaskPool taskpool;
	private MainWindow window;
	
	private TaskPool readyQueue;
	private TaskPool blockedQueue;
	
	private Long time;
	private Activator activator;
	
	
	public Scheduler(TaskPool taskpool, MainWindow window, Boolean startActivator) {
		super();
		this.taskpool = taskpool;
		this.window = window;
		
		readyQueue = new TaskPool();
		blockedQueue = new TaskPool();
		
		time = new Long(0);
		
		activator = new Activator(this, taskpool);
		if(startActivator)
			activator.start();
	}
	
	public Scheduler(TaskPool taskpool, MainWindow window) {
		new Scheduler(taskpool, window, true);
	}

	public Scheduler() {
		super();
	}
	
	public TaskPool getReadyQueue() {
		return readyQueue;
	}

	public void setReadyQueue(TaskPool readyQueue) {
		this.readyQueue = readyQueue;
	}

	public TaskPool getBlockedQueue() {
		return blockedQueue;
	}

	public void setBlockedQueue(TaskPool blockedQueue) {
		this.blockedQueue = blockedQueue;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public TaskPool getTaskpool() {
		return taskpool;
	}

	public void setTaskpool(TaskPool taskpool) {
		this.taskpool = taskpool;
	}

	public MainWindow getWindow() {
		return window;
	}

	public void setWindow(MainWindow window) {
		this.window = window;
	}

	public Activator getActivator() {
		return activator;
	}

	public void setActivator(Activator activator) {
		this.activator = activator;
	}

	public void addReady(Task task) {
		addReady(task, false);
	}
	
	public void addReady(Task task, boolean sort) {
		readyQueue.add(task);
		readyQueue.sort();
	}
	
	public void addBlocked(Task task) {
		blockedQueue.add(task);
	}
	
	@Override
	public void run() {
		
	}
	
	public boolean validateDeadlines(int clk) {
		for (Task task : readyQueue) {
			if(clk > task.getRelativeDeadline().intValue()) {
				System.err.println("\t"+"clk: "+clk+" -> "+task.getTaskId()+" HITS DEADLINE ["+task.getComputation()+"] \\o/");
				window.chart.addDeadline(task);
				return false;
			}
		}
		return true;
	}

}
