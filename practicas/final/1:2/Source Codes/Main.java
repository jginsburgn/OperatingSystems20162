import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = JGHelper.pasteStrings(args);
        JGFileReader fr = new JGFileReader(filePath);
        System.out.println("++++++++++++++++++++++++Printing File Contents:");
        String fileContents = fr.getFileContents();
        System.out.print(fileContents);
        System.out.println("***********************************************");

        ArrayList contents = JGHelper.separateDocumentContents(fileContents);
        int quantum = (int)contents.get(0);
        contents.remove(0);
        ArrayList<JGProcess> processes = JGHelper.processEncodingsToProcesses(contents);

        JGRoundRobin roundRobin = new JGRoundRobin(processes, quantum);
        JGPriorityScheduler priorityScheduler = new JGPriorityScheduler(processes);

        System.out.println("++++++Printing Gantt for Round Robin Scheduler:");
        System.out.println(roundRobin.gantt);
        System.out.println("***********************************************");

        System.out.println("+++++++++Printing Gantt for Priority Scheduler:");
        System.out.println(priorityScheduler.gantt);
        System.out.println("***********************************************");
    }
}
