package Models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;

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
    public static ArrayList<XYChart.Series<Integer, String>> FCFS(ArrayList<Process> processes){
        //--- Gant Diagram ---
        ArrayList<XYChart.Series<Integer, String>> series = new ArrayList<>();
        XYChart.Series transparentSerie = new XYChart.Series();
        //--- Gant Diagram ---

        int waitTime = 0, returnTime = 0;
        for(Process currentProcess: processes){
            currentProcess.setWaitTime(waitTime);
            waitTime += currentProcess.getRunTime();
            returnTime += currentProcess.getRunTime();
            currentProcess.setReturnTime(returnTime);

            //--- Gant Diagram ---
            //Barra
            XYChart.Series<Integer, String> serie = new XYChart.Series<>();
            serie.getData().add(new XYChart.Data<>(currentProcess.getRunTime(), currentProcess.getName()));

            //Barra Transparent
            final XYChart.Data<Integer, String> data = new XYChart.Data<>(currentProcess.getWaitTime(), currentProcess.getName());

            //Cambiar color a la barra
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    data.getNode().setStyle("-fx-bar-fill: transparent;");
                }
            });
            //

            transparentSerie.getData().add(data);
            series.add(serie);
            //--- Gant Diagram ---
        }

        series.add(0, transparentSerie);
        return series;
    }

    /**
     * Method that is responsible for simulating the SJF scheduling algorithm (The Shortest Join First)
     * @param processes The processes
     */
    public static ArrayList<XYChart.Series<Integer, String>> SJF(ArrayList<Process> processes){
        Process shortestProcess = Collections.min(processes, Comparator.comparing(Process::getRunTime));
        processes.remove(shortestProcess);
        processes.add(0, shortestProcess);
        return FCFS(processes);
    }

    /**
     * Method that is responsible for simulating the priority scheduling algorithm.
     * @param processes The processes
     */
    public static ArrayList<XYChart.Series<Integer, String>> priority(ArrayList<Process> processes){
        processes.sort(Comparator.comparing(p -> p.getPriority()));
        return FCFS(processes);
    }

    /**
     * Method that is responsible for simulating the Round Robin scheduling algorithm
     * @param processes The processes
     * @param quantum The corresponding quantum
     */
    public static ArrayList<XYChart.Series<Integer, String>> roundRobin(ArrayList<Process> processes, int quantum) {
        ArrayList<XYChart.Series<Integer, String>> series = new ArrayList<>();

        ArrayList<Process> temp = new ArrayList<>(processes);
        List<Integer> runTimes = processes.stream().map(Process::getRunTime).collect(Collectors.toList());
        List<Integer> waitTimes = temp.stream().map(p -> 0).collect(Collectors.toList());

        int accumulated = 0;
        while (!temp.isEmpty()) {
            //Barra de una parte de un proceso
            XYChart.Series<Integer, String> serie = new XYChart.Series<>();
            Integer currentWaitTime = waitTimes.remove(0);
            waitTimes.replaceAll(integer -> integer + quantum);

            Process currentProcess = temp.remove(0);
            Integer currentRunTime = runTimes.remove(0);

            if (currentRunTime > quantum) {
                currentRunTime -= quantum;
                temp.add(currentProcess);
                runTimes.add(currentRunTime);
                accumulated += quantum;

                waitTimes.add(0);
                serie.getData().add(new XYChart.Data<>(quantum, currentProcess.getName()));
            } else {
                accumulated += currentRunTime;
                currentProcess.setReturnTime(accumulated);
                currentProcess.setWaitTime(accumulated - currentProcess.getRunTime());

                serie.getData().add(new XYChart.Data<>(currentRunTime, currentProcess.getName()));
            }

            //Barra transparente
            XYChart.Series<Integer, String> transparentSerie = new XYChart.Series<>();
            final XYChart.Data<Integer, String> data = new XYChart.Data<>(currentWaitTime, currentProcess.getName());
            //Cambiar color a la barra
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    data.getNode().setStyle("-fx-bar-fill: transparent;");
                }
            });
            //
            transparentSerie.getData().add(data);

            //Se añaden las barras
            series.add(transparentSerie);
            series.add(serie);
        }
        return series;
    }
}
