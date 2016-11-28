import java.util.*;

public class JGRoundRobin {
    private double quantum = -1;
    private ArrayList<JGProcess> processes = new ArrayList<JGProcess>();
    private HashMap<Integer, Double> remainingTime = new HashMap<Integer, Double>();
    private LinkedList<Integer> queue = new LinkedList<Integer>();
    private int currentProcess = -1;
    private double time = 0;
    private double nextQuantum = -1;
    public Gantt gantt = new Gantt();

    public enum Event {
        NewProcess,
        ProcessFinished,
        QuantumUp
    }

    JGRoundRobin(ArrayList<JGProcess> newProcesses, int newQuantum) {
        quantum = newQuantum;
        nextQuantum = quantum;
        for (int i = 0; i < newProcesses.size(); i++) {
            processes.add((JGProcess)newProcesses.get(i).clone());
            remainingTime.put(processes.get(i).PID, processes.get(i).duration);
        }
        start();
    }

    //Selects process that arrives at the given time
    private int arrivingProcess() {
        int retVal = -1;
        for (int i = 0; i < processes.size(); i++) {
            JGProcess process = processes.get(i);
            if (process.arrivalTime == time) {
                return process.PID;
            }
        }
        return retVal;
    }

    //Enqueues a process to the ready queue
    private void enqueue(int PID) {
        if (PID != -1 && remainingTime.get(PID) != 0) {
            queue.add(PID);
        }
    }

    //Returns PID of next ready process
    private int dequeue() {
        return queue.remove();
    }

    //Returns PID of next earliest arriving process or -1 if there is none
    private int nextEarliestArrivingProcess() {
        int retVal = -1;
        double earliestFound = Double.MAX_VALUE;
        for (int i = 0; i < processes.size(); i++) {
            JGProcess process = processes.get(i);
            if (process.arrivalTime > time && process.arrivalTime < earliestFound) {
                retVal = process.PID;
                earliestFound = process.arrivalTime;
            }
        }
        return retVal;
    }

    //Returns the earliest time when scheduling needs to happen and the event corresponding
    private Object[] nextEventAndTime() {
        Object[] retVal = new Object[2];
        int neap = nextEarliestArrivingProcess();
        double neapTime = Double.MAX_VALUE;
        if (neap != -1) {
        	neapTime = JGHelper.getProcess(neap, processes).arrivalTime;
        }
        int cp = currentProcess;
        double cpTime = time + remainingTime.get(cp);
        if (nextQuantum <= neapTime && nextQuantum <= cpTime) {
            retVal[0] = nextQuantum;
            retVal[1] = Event.QuantumUp;
        }
        else if (neapTime <= nextQuantum && neapTime <= cpTime) {
            retVal[0] = neapTime;
            retVal[1] = Event.NewProcess;
        }
        else {
            retVal[0] = cpTime;
            retVal[1] = Event.ProcessFinished;
        }
        return retVal;
    }

    private void digest(Event event) {
        switch (event) {
            case NewProcess:
                newProcess();
                break;
            case QuantumUp:
                quantumUp();
                break;
            case ProcessFinished:
                processFinished();
                break;
        }
    }

    // Start execution
    private void start() {
        currentProcess = arrivingProcess();
        recordStatus();
        Object[] neat = nextEventAndTime();
        Event nextEvent = (Event)neat[1];
        double nextTime = (double)neat[0];
        remainingTime.put(currentProcess, remainingTime.get(currentProcess) - nextTime);
        time += nextTime;
        digest(nextEvent);
    }

    //Digest a new process event
    private void newProcess() {
        enqueue(arrivingProcess());
    	recordStatus();
        Object[] neat = nextEventAndTime();
        Event nextEvent = (Event)neat[1];
        double nextTime = (double)neat[0];
        double difference = nextTime - time;
        remainingTime.put(currentProcess, remainingTime.get(currentProcess) - difference);
        time = nextTime;
        digest(nextEvent);
    }

    //Digest a quantum up event
    private void quantumUp() {
        enqueue(arrivingProcess());
        if (queue.isEmpty()) {
        	currentProcess = -1;
        	recordStatus();
            return;
        }
        else {
        	enqueue(currentProcess);
            currentProcess = dequeue();
            nextQuantum = time + quantum;
            recordStatus();
            Object[] neat = nextEventAndTime();
            Event nextEvent = (Event)neat[1];
            double nextTime = (double)neat[0];
            double difference = nextTime - time;
            remainingTime.put(currentProcess, remainingTime.get(currentProcess) - difference);
            time = nextTime;
            digest(nextEvent);
        }
    }

    //Digest a process finished event
    private void processFinished() {
        enqueue(arrivingProcess());
        if (queue.isEmpty()) {
        	currentProcess = -1;
        	recordStatus();
            return;
        }
        else {
            currentProcess = dequeue();
            nextQuantum = time + quantum;
            recordStatus();
            Object[] neat = nextEventAndTime();
            Event nextEvent = (Event)neat[1];
            double nextTime = (double)neat[0];
            double difference = nextTime - time;
            remainingTime.put(currentProcess, remainingTime.get(currentProcess) - difference);
            time = nextTime;
            digest(nextEvent);
        }
    }

    private ArrayList<JGProcess> getQueueAsArrayList() {
        ArrayList<JGProcess> retVal = new ArrayList<JGProcess>();
        for (int i = 0; i < queue.size(); i++) {
            JGProcess np = JGHelper.getProcess(queue.get(i), processes).clone();
            retVal.add(np);
        }
        return retVal;
    }

    private void recordStatus() {
        Gantt.Instant i = new Gantt.Instant(time);
        if (currentProcess != -1) {
            i = new Gantt.Instant(time,
                JGHelper.getProcess(currentProcess, processes),
                remainingTime,
                getQueueAsArrayList());
        }
        gantt.pushInstant(i);
    }
};
