package task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Journal {
    public HashMap<String, Group> groups;

    public Journal(ArrayList<Group> groups) {
        this.groups = new HashMap<>();

        for(var group : groups) {
            this.groups.put(group.getGroupCode(), group);
        }
    }

    public void printMarks() {
        var sortedGroups = groups.keySet().stream().sorted().collect(Collectors.toList());
        for (String groupName : sortedGroups) {
            System.out.println("Group " + groupName);

            var sortedStudents = groups.get(groupName).marks.keySet().stream().sorted().collect(Collectors.toList());
            for (Integer student : sortedStudents ) {
                System.out.println("Student num" + student + ":");

                for (int mark : groups.get(groupName).marks.get(student)) {
                    System.out.printf("%d ", mark);
                }

                System.out.println();
            }

            System.out.println();
        }
    }
}

