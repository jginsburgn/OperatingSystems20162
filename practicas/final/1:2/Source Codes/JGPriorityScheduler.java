import java.util.*;

public class JGPriorityScheduler {

    Comparator<JGProcess> JGComparator = new Comparator<JGProcess>() {
        public int compare(JGProcess first, JGProcess second) {
            if (first.priority > second.priority) return +1;
            if (first.priority == second.priority) return 0;
            return -1;
        }
    };

    PriorityQueue<JGProcess> queue =
        new PriorityQueue<JGProcess>(10, JGComparator);
    private ArrayList<JGProcess> processes = new ArrayList<JGProcess>();
    private HashMap<Integer, Double> remainingTime = new HashMap<Integer, Double>();
    private int currentProcess = -1;
    private double time = 0;
    public Gantt gantt;

    private enum Event {
        NewProcess,
        ProcessFinished
    }

    JGPriorityScheduler(ArrayList<JGProcess> newProcesses) {
        for (int i = 0; i < newProcesses.size(); i++) {
            processes.add((JGProcess)newProcesses.get(i).clone());
            remainingTime.put(processes.get(i).PID, processes.get(i).duration);
        }
        gantt = new Gantt(processes);
        start();
    }

    private ArrayList<JGProcess> queueToArrayList(PriorityQueue<JGProcess> queue) {
        Object[] arr = queue.toArray();
        JGProcess[] arrr = new JGProcess[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrr[i] = (JGProcess) arr[i];
        }
        Arrays.sort(arrr, JGComparator);
        return new ArrayList<JGProcess>(Arrays.asList(arrr));
    }

    private ArrayList<JGProcess> getQueueAsArrayList() {
        return queueToArrayList(queue);
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

    //Returns PID of next earliest arriving process or -1 if there is none
    private JGProcess nextEarliestArrivingProcess() {
        JGProcess retVal = null;
        double earliestFound = Double.MAX_VALUE;
        for (int i = 0; i < processes.size(); i++) {
            JGProcess process = processes.get(i);
            if (process.arrivalTime > time && process.arrivalTime < earliestFound) {
                retVal = process;
                earliestFound = process.arrivalTime;
            }
        }
        return retVal;
    }

    //Selects process that arrives at the given time
    private JGProcess arrivingProcess() {
        JGProcess retVal = null;
        for (int i = 0; i < processes.size(); i++) {
            JGProcess process = processes.get(i);
            if (process.arrivalTime == time) {
                return process;
            }
        }
        return retVal;
    }

    //Returns the earliest time when scheduling needs to happen and the event corresponding
    private Object[] nextEventAndTime() {
        Object[] retVal = new Object[2];
        JGProcess neapP = nextEarliestArrivingProcess();
        int neap = -1;
        if (neapP != null) {
            neap = neapP.PID;
        }
        double neapTime = Double.MAX_VALUE;
        if (neap != -1) {
        	neapTime = JGHelper.getProcess(neap, processes).arrivalTime;
        }
        int cp = currentProcess;
        double cpTime = time + remainingTime.get(cp);
        if (neapTime <= cpTime) {
            retVal[0] = neapTime;
            retVal[1] = Event.NewProcess;
        }
        else {
            retVal[0] = cpTime;
            retVal[1] = Event.ProcessFinished;
        }
        return retVal;
    }

    private void enqueue(JGProcess process) {
        if (process != null && remainingTime.get(process.PID) > 0) {
            queue.offer(process);
        }
    }

    private void enqueue(int PID) {
        JGProcess process = JGHelper.getProcess(PID, processes);
        if (process != null && remainingTime.get(process.PID) > 0) {
            queue.offer(process);
        }
    }

    private void start() {
        enqueue(arrivingProcess());
        if (queue.isEmpty()) {
            return;
        }
        currentProcess = queue.poll().PID;
        recordStatus();
        Object[] neat = nextEventAndTime();
        double nextTime = (double)neat[0];
        Event nextEvent = (Event)neat[1];
        double difference = nextTime - time;
        remainingTime.put(currentProcess, remainingTime.get(currentProcess) - difference);
        time = nextTime;
        digest(nextEvent);
    }

    private void digest(Event event) {
    	if (time == 53.5) {
    		int x = 0;
    	}
        switch (event) {
            case NewProcess:
                newProcess();
                break;
            case ProcessFinished:
                processFinished();
                break;
        }
    }

    private void processFinished() {
        JGProcess ap = arrivingProcess();
        if (ap != null) {
            enqueue(ap);
        }
        if (queue.isEmpty()) {
            currentProcess = -1;
            recordStatus();
            return;
        }
        currentProcess = queue.poll().PID;
        recordStatus();
        Object[] neat = nextEventAndTime();
        double nextTime = (double)neat[0];
        Event nextEvent = (Event)neat[1];
        double difference = nextTime - time;
        remainingTime.put(currentProcess, remainingTime.get(currentProcess) - difference);
        time = nextTime;
        digest(nextEvent);
    }

    private void newProcess() {
        JGProcess ap = arrivingProcess();
        JGProcess cp = JGHelper.getProcess(currentProcess, processes);
        if (ap.priority < cp.priority) {
            enqueue(cp);
            currentProcess = ap.PID;
        }
        else {
            enqueue(ap);
            if (remainingTime.get(currentProcess) == 0) {
                currentProcess = queue.poll().PID;
            }
        }
        recordStatus();
        Object[] neat = nextEventAndTime();
        double nextTime = (double)neat[0];
        Event nextEvent = (Event)neat[1];
        double difference = nextTime - time;
        remainingTime.put(currentProcess, remainingTime.get(currentProcess) - difference);
        time = nextTime;
        digest(nextEvent);
    }
};
