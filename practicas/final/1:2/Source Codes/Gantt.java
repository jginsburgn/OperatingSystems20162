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
    Gantt(){

    };

    public void pushInstant(Instant i) {
        instants.add(i);
    }

    public String toString() {
        String retVal = "";
        for (int i = 0; i < instants.size(); i++) {
            retVal+=instants.get(i);
        }
        return retVal;
    }
}
