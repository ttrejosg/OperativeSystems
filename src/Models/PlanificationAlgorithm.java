package Models;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlanificationAlgorithm {

    /**
     * Method that is responsible for simulating the FCFS scheduling algorithm (By order of arrival)
     * @param processes The processes
     */
    public static void FCFS(ArrayList<Process> processes){
        int waitTime = 0, returnTime = 0;
        for(Process currentProcess: processes){
            currentProcess.setWaitTime(waitTime);
            waitTime += currentProcess.getRunTime();
            returnTime += currentProcess.getRunTime();
            currentProcess.setReturnTime(returnTime);
        }
    }

    /**
     * Method that is responsible for simulating the SJF scheduling algorithm (The Shortest Join First)
     * @param processes The processes
     */
    public static void SJF(ArrayList<Process> processes){
        Process shortestProcess = Collections.min(processes, Comparator.comparing(Process::getRunTime));
        processes.remove(shortestProcess);
        processes.add(0, shortestProcess);
        FCFS(processes);
    }

    /**
     * Method that is responsible for simulating the priority scheduling algorithm.
     * @param processes The processes
     */
    public static void priority(ArrayList<Process> processes){
        processes.sort(Comparator.comparing(p -> p.getPriority()));
        FCFS(processes);
    }

    /**
     * Method that is responsible for simulating the Round Robin scheduling algorithm
     * @param processes The processes
     * @param quantum The corresponding quantum
     */
    public static void roundRobin(ArrayList<Process> processes, int quantum){

    }
}
