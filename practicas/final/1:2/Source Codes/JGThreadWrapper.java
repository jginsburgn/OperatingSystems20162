import java.util.*;

public class JGThreadWrapper extends Thread {

    public static enum Kind {
        RoundRobin,
        Priority
    }

    private Kind kind;
    private int quantum;
    private ArrayList<JGProcess> processes;
    public JGRoundRobin roundRobin;
    public JGPriorityScheduler priorityScheduler;

    public JGThreadWrapper(Kind newKind, ArrayList<JGProcess> newProcesses, int newQuantum){
        kind = newKind;
        switch (kind) {
            case RoundRobin:
                quantum = newQuantum;
                break;
            case Priority:
                break;
        }
        processes = newProcesses;
    }

    public void run() {
        switch (kind) {
            case RoundRobin:
                roundRobin = new JGRoundRobin(processes, quantum);
                break;
            case Priority:
                priorityScheduler = new JGPriorityScheduler(processes);
                break;
        }
    }
}
