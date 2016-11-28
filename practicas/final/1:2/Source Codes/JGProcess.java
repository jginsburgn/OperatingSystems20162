public class JGProcess {
    public int PID = -1;
    public String name = "";
    public double arrivalTime;
    public double duration;
    public int priority;

    JGProcess(int newPID, String newName, double newArrivalTime, double newDuration, int newPriority) {
        PID = newPID;
        name = newName;
        arrivalTime = newArrivalTime;
        duration = newDuration;
        priority = newPriority;
    }

    public JGProcess clone() {
        return new JGProcess(this.PID, this.name, this.arrivalTime, this.duration, this.priority);
    }
    public String toString() {
        String retVal =
            "[Process] Name: " +
            name +
            ", Arrival Time: " +
            arrivalTime +
            ", Duration: " +
            duration +
            ", Priority: " +
            priority +
            ".";
            return retVal;
    }
}
