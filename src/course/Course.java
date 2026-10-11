package course;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

/**
 Assessment class hold the details of one assessment.
 */
class Assessment {
    private String name;
    private String description;
    private int points;
    private String submissionType;
    private boolean group;
    private String groupingName;
    private String publishDateTime;
    private String dueDateTime;
    private String hideDateTime;

    // Initializes all the attributes from the given parameters
    public Assessment(String name, String description, int points, String submissionType, boolean group, String groupingName, String publishDateTime, String dueDateTime, String hideDateTime) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.submissionType = submissionType;
        this.group = group;
        this.groupingName = groupingName;
        this.publishDateTime = publishDateTime;
        this.dueDateTime = dueDateTime;
        this.hideDateTime = hideDateTime;
    }

    // Getters: used by the validators and the JUnit tests
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPoints() { return points; }
    public String getSubmissionType() { return submissionType; }
    public boolean isGroup() { return group; }
    public String getGroupingName() { return groupingName; }
    public String getPublishDateTime() { return publishDateTime; }
    public String getDueDateTime() { return dueDateTime; }
    public String getHideDateTime() { return hideDateTime; }
}

/**
 Course class for the StudentScholar website.
 Holds the list of assessments and validates new assessments against the 11 conditions before storing them.
 */
public class Course {

    // Stores every assessment that passed validation
    private List<Assessment> assessments = new ArrayList<>();

    // Getter so the JUnit tests can check the list contents and size
    public List<Assessment> getAssessments() {
        return assessments;
    }

    // SHARED method: each member only adds lines inside their own block.
    // Do not edit anyone else's block.
    public boolean createNewAssessment(String name, String description, int points, String submissionType, boolean group, String groupingName, String publishDateTime, String dueDateTime, String hideDateTime) {
        boolean valid = true;

        // Nandan: C1, C2, C10, C11
        valid = valid && isNameUnique(name);
        valid = valid && isNameNotBlank(name);
        valid = valid && areDatesFormatValid(publishDateTime, dueDateTime, hideDateTime);
        valid = valid && areDatesInOrder(publishDateTime, dueDateTime, hideDateTime);

        // AYUSH: C3, C4, C5, C6 (Ayush adds the calls here)



        // HARSHAL: C7, C8, C9 (Harshal adds the calls here)



        // Only create and store the Assessment if every condition passed.
        // If any check failed, nothing is created or added.
        if (valid) {
            assessments.add(new Assessment(name, description, points, submissionType,
                    group, groupingName, publishDateTime, dueDateTime, hideDateTime));
        }
        return valid;
    }

    // NANDAN: C1, C2, C10, C11
    // Author: Nandan Patel S4253850

    // Strict formatter for DD/MM/YYYY hh:mm (24 hour clock).
    // "uuuu" + STRICT means invalid dates such as 29/02/2026 are rejected
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    // Helper function that converts a string to a date time, or returns null if it is null, badly formatted or not a real date. Used by C10 and C11.
    private LocalDateTime parseDateTime(String value) {
        if (value == null) {
            return null;
        }
        try {
            return LocalDateTime.parse(value, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null; // wrong format or impossible date
        }
    }

    // C1: The name must not match any assessment already in the list.
    private boolean isNameUnique(String name) {
        for (Assessment existing : assessments) {
            if (existing.getName().equals(name)) {
                return false; // duplicate found
            }
        }
        return true;
    }

    // C2: The name cannot be null, empty or only whitespace.
    private boolean isNameNotBlank(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // C10: All three date times must match DD/MM/YYYY hh:mm and be real dates.
    private boolean areDatesFormatValid(String p, String d, String h) {
        return parseDateTime(p) != null
                && parseDateTime(d) != null
                && parseDateTime(h) != null;
    }

    // C11: Hide must be after due, and due must be after publish.
    // Equal times are NOT "after", so they are rejected.
    private boolean areDatesInOrder(String p, String d, String h) {
        LocalDateTime publish = parseDateTime(p);
        LocalDateTime due = parseDateTime(d);
        LocalDateTime hide = parseDateTime(h);
        if (publish == null || due == null || hide == null) {
            return false; // C10 reports the format problem
        }
        return hide.isAfter(due) && due.isAfter(publish);
    }

    // AYUSH: validator methods for C3, C4, C5, C6 go below this line



    // HARSHAL: validator methods for C7, C8, C9 go below this line



}