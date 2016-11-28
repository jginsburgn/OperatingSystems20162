import java.util.*;

public class Gantt {
    public static class Instant {
        public double time;
        public JGProcess workingProcess;
        public HashMap<Integer, Double> remainingTime;
        public ArrayList<JGProcess> queue;

        private boolean end = false;

        Instant(double newTime) {
            time = newTime;
            end = true;
        }

        Instant(
        double newTime,
        JGProcess newWorkingProcess,
        HashMap<Integer, Double> newRemainingTime,
        ArrayList<JGProcess> newQueue) {

            if (newWorkingProcess == null) {

            }
            time = newTime;
            workingProcess = newWorkingProcess.clone();
            remainingTime = new HashMap<Integer, Double>(newRemainingTime);
            queue = new ArrayList<JGProcess>();
            for (JGProcess p: newQueue) {
                queue.add(p.clone());
            }
        }

        public String summarizeProcess(JGProcess p) {
            String retVal =
            "(Name: " +
            p.name +
            ", Remaining Time: " +
            remainingTime.get(p.PID) +
            ", Priority: " +
            p.priority +
            ")";
            return retVal;
        }

        public String toString() {
            String retVal = "";
            retVal += "[" + time + "] ";
            if (end) {
                retVal+="End";
            }
            else {
                retVal += "Running process: " + summarizeProcess(workingProcess) + "\n";
                if (queue.isEmpty()) {
                    retVal += "\tQueue: Empty\n";
                }
                else {
                    retVal += "\tQueue (Pops from top):\n";
                    for (JGProcess p : queue) {
                        retVal += "\t\t" + summarizeProcess(p) + "\n";
                    }
                }
            }
            return retVal;
        }
    }

    public ArrayList<Instant> instants = new ArrayList<Instant>();
    private ArrayList<JGProcess> processes = new ArrayList<JGProcess>();

    Gantt(ArrayList<JGProcess> newProcesses){
        for (int i = 0; i < newProcesses.size(); i++) {
            processes.add((JGProcess)newProcesses.get(i).clone());
        }
    };

    public void pushInstant(Instant i) {
        instants.add(i);
    }

    public double averageWaitingTime() {
        if (instants.get(instants.size()-1).workingProcess != null) {
            return -1.0;
        }
        HashMap<Integer, Double> waitingTime = new HashMap<Integer, Double>();
        for (int i = 0; i < instants.size() - 1; i++) {
            Instant current = instants.get(i);
            Instant next = instants.get(i + 1);
            double currentDuration = next.time - current.time;
            ArrayList<JGProcess> queue = current.queue;
            for (int j = 0; j < queue.size(); j++) {
                JGProcess queuedProcess = queue.get(j);
                if (waitingTime.get(queuedProcess.PID) == null) {
                    waitingTime.put(queuedProcess.PID, currentDuration);
                }
                else {
                    waitingTime.put(queuedProcess.PID, waitingTime.get(queuedProcess.PID) + currentDuration);
                }
            }
        }
        Set<Integer> keySet = waitingTime.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        double cummulativeWaitingTimes = 0;
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            cummulativeWaitingTimes += waitingTime.get(key);
        }
        return cummulativeWaitingTimes/processes.size();
    }

    public double averageTurnaroundTime() {
        if (instants.get(instants.size()-1).workingProcess != null) {
            return -1.0;
        }
        HashMap<Integer, Double> runningTime = new HashMap<Integer, Double>();
        for (int i = 0; i < instants.size() - 1; i++) {
            Instant current = instants.get(i);
            Instant next = instants.get(i + 1);
            double currentDuration = next.time - current.time;
            JGProcess wp = current.workingProcess;
            if (runningTime.get(wp.PID) == null) {
                runningTime.put(wp.PID, currentDuration);
            }
            else {
                runningTime.put(wp.PID, runningTime.get(wp.PID) + currentDuration);
            }
        }
        Set<Integer> keySet = runningTime.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        double cummulativeRunningTimes = 0;
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            cummulativeRunningTimes += runningTime.get(key);
        }
        return cummulativeRunningTimes/processes.size() + averageWaitingTime();
    }

    public String toString() {
        String retVal = "";
        for (int i = 0; i < instants.size(); i++) {
            retVal+=instants.get(i);
        }
        return retVal;
    }
}
