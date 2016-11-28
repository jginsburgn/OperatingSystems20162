import java.util.*;
import java.io.*;

public class Main {

    private static String showAndGetFileContents(String[] args) {
        String filePath = JGHelper.pasteStrings(args);
        JGFileReader fr = new JGFileReader(filePath);
        System.out.println("++++++++++++++++++++++++Printing File Contents:");
        String fileContents = fr.getFileContents();
        System.out.print(fileContents);
        System.out.println("***********************************************");
        return fileContents;
    }

    public static void writeToServer(String str) {
        try {
            JGNetClient client = new JGNetClient();
            client.write(str);
            client.finish();
        }
        catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void sendAvgTurnaroundAndWaitingTimes(JGRoundRobin roundRobin, JGPriorityScheduler priorityScheduler) {
        String message = "Round Robin:\n" +
            "\tAverage Waiting Time: " + roundRobin.gantt.averageWaitingTime() + "\n" +
            "\tAverage Turnaround Time: " + roundRobin.gantt.averageTurnaroundTime() + "\n";
        message += "Priority Scheduler:\n" +
            "\tAverage Waiting Time: " + priorityScheduler.gantt.averageWaitingTime() + "\n" +
            "\tAverage Turnaround Time: " + priorityScheduler.gantt.averageTurnaroundTime() +  "\n";
        writeToServer(message);
    }

    public static void main(String[] args) {
        String fileContents = showAndGetFileContents(args);
        ArrayList contents = JGHelper.separateDocumentContents(fileContents);
        int quantum = (int)contents.get(0);
        contents.remove(0);
        ArrayList<JGProcess> processes = JGHelper.processEncodingsToProcesses(contents);

        JGThreadWrapper roundRobinThread = new JGThreadWrapper(JGThreadWrapper.Kind.RoundRobin, processes, quantum);
        roundRobinThread.start();
        JGThreadWrapper prioritySchedulerThread = new JGThreadWrapper(JGThreadWrapper.Kind.Priority, processes, -1);
        prioritySchedulerThread.start();

        try {
            roundRobinThread.join();
            prioritySchedulerThread.join();
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }

        JGRoundRobin roundRobin = roundRobinThread.roundRobin;
        JGPriorityScheduler priorityScheduler = prioritySchedulerThread.priorityScheduler;

        System.out.println("++++++Printing Gantt for Round Robin Scheduler:");
        System.out.println(roundRobin.gantt);
        System.out.println("***********************************************");

        System.out.println("+++++++++Printing Gantt for Priority Scheduler:");
        System.out.println(priorityScheduler.gantt);
        System.out.println("***********************************************");

        JGFileWriter psfw = new JGFileWriter("priority_scheduler.txt");
        psfw.printAndClose(priorityScheduler.gantt.toString());
        JGFileWriter rrfw = new JGFileWriter("round_robin.txt");
        rrfw.printAndClose(roundRobin.gantt.toString());

        sendAvgTurnaroundAndWaitingTimes(roundRobin, priorityScheduler);
    }
}
