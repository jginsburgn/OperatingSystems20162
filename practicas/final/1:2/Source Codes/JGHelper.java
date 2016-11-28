import java.util.*;

public class JGHelper {
    //Pastes strings in an array separated by a single space
    public static String pasteStrings(String[] strings) {
        String retVal = "";
        for (int i = 0; i < strings.length ; i++) {
            if (i == strings.length - 1) {
                retVal += strings[i];
            }
            else {
                retVal += strings[i] + " ";
            }
        }
        return retVal;
    }

    //Takes a string where the first line is a quantum time, and the rest are process encondings.
    //Process encoding: Name, Arrival Time, Duration, Priority separated by spaces
    //Returns array where first object is a double representing quantum time,
    //And the rest are process encodings.
    public static ArrayList separateDocumentContents(String document) {
        ArrayList retVal = new ArrayList();
        String[] lines = document.split("\n");
        retVal.add(Integer.parseInt(lines[0]));
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            retVal.add(line);
        }
        return retVal;
    }

    public static ArrayList<JGProcess> processEncodingsToProcesses(ArrayList<String> pes) { //pes stands for process encondings
        ArrayList<JGProcess> retVal = new ArrayList<JGProcess>();
        for (int i = 0; i < pes.size(); i++) {
            String pe = pes.get(i); //pe stands for process encoding
            String[] pep = pe.split(" "); //pep stands for process encoding properties
            JGProcess np = new JGProcess( //np stands for new process
                i, //Process id
                pep[0], //Name
                Double.parseDouble(pep[1]), //Arrival time
                Double.parseDouble(pep[2]), //Duration
                Integer.parseInt(pep[3]) //Priority
            );
            retVal.add(np);
        }
        return retVal;
    }

    public static void printProcesses(ArrayList<JGProcess> processes) {
        for (int i = 0; i < processes.size(); i++) {
            System.out.println(processes.get(i));
        }
    }

    //Get process out of an array through PID, if none is found null is returned
    public static JGProcess getProcess(int PID, ArrayList<JGProcess> processes) {
        for (int i = 0; i < processes.size(); i++) {
            JGProcess process = processes.get(i);
            if (process.PID == PID) {
                return process;
            }
        }
        return null;
    }

    public static void printArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static void printArrayList(ArrayList array) {
        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }
    }

    public static void print(String string) {
        System.out.println(string);
    }
}
