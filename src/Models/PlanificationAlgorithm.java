package Models;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PlanificationAlgorithm {

    public static void FCFS(ArrayList<Process> processes){

    }

    public static void SJF(ArrayList<Process> processes){

    }

    public static void priority(ArrayList<Process> processes, int[] priorities){

    }

    public static void roundRobin(ArrayList<Process> processes, int quantum){
        int onGoingProcesses =  processes.size();
        int onGoingTime = 0;
        while(onGoingProcesses > 0){
            Process currentProcess = processes.remove(0);
            if(currentProcess.getRunTime() - quantum > 0){
                currentProcess.setReturnTime(currentProcess.getReturnTime() + quantum);
                currentProcess.setRunTime(currentProcess.getRunTime() - quantum);
                processes.add(currentProcess);
            }else{
                currentProcess.setReturnTime(currentProcess.getReturnTime() + currentProcess.getRunTime());
                onGoingProcesses--;
            }
        }
    }
}
