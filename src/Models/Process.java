package Models;

public class Process {
    private String name;
    private int runTime;
    private int waitTime;
    private int returnTime;
    private int priority;

    public Process(String name, int runTime, int priority) {
        this.name = name;
        this.runTime = runTime;
        this.priority = priority;
    }

    public Process(Process anotherProcess){
        this.name = anotherProcess.name;
        this.runTime = anotherProcess.runTime;
        this.priority = anotherProcess.priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(int returnTime) {
        this.returnTime = returnTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
