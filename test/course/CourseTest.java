package course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 JUnit 5 tests for Course.createNewAssessment.
 */
public class CourseTest {

    // New Course before every test, so the list starts empty
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
    }

    // NANDAN: tests for C1, C2, C10, C11
    // Author: Nandan Patel S4253850

    // Valid default values, so only the field under test is invalid
    private static final String N_DESC = "this is a valid description";
    private static final String N_PUBLISH = "01/11/2026 09:00";
    private static final String N_DUE = "08/11/2026 23:59";
    private static final String N_HIDE = "15/11/2026 23:59";

    // Helper: only the name and the three dates vary between my tests
    private boolean nandanCreate(String name, String publish, String due, String hide) {
        return course.createNewAssessment(name, N_DESC, 10, "pdf", false, "",
                publish, due, hide);
    }

    // TC-C1: duplicate name -> false
    @Test
    void testC1_duplicateNameRejected() {
        // Preconditions: empty list, then first assessment accepted
        assertEquals(0, course.getAssessments().size());
        assertTrue(nandanCreate("assignment 1", N_PUBLISH, N_DUE, N_HIDE));
        assertEquals(1, course.getAssessments().size());

        boolean result = nandanCreate("assignment 1", N_PUBLISH, N_DUE, N_HIDE);

        assertFalse(result);
        assertEquals(1, course.getAssessments().size()); // nothing added
    }

    // TC-C2: blank name (empty string) -> false
    @Test
    void testC2_blankNameRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = nandanCreate("", N_PUBLISH, N_DUE, N_HIDE);

        assertFalse(result);
        assertEquals(0, course.getAssessments().size());
    }

    // TC-C10a: 29/02/2026 is not a real date (not a leap year) -> false
    @Test
    void testC10a_invalidLeapDayRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = nandanCreate("assignment 2",
                "29/02/2026 20:00", "01/03/2026 20:00", "02/03/2026 20:00");

        assertFalse(result);
        assertEquals(0, course.getAssessments().size());
    }

    // TC-C10b: 29/02/2028 is a real date (leap year) -> true
    @Test
    void testC10b_validLeapDayAccepted() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = nandanCreate("assignment 2",
                "29/02/2028 20:00", "01/03/2028 20:00", "02/03/2028 20:00");

        assertTrue(result);
        assertEquals(1, course.getAssessments().size());
        Assessment stored = course.getAssessments().get(0);
        assertEquals("assignment 2", stored.getName());
        assertEquals("29/02/2028 20:00", stored.getPublishDateTime());
        assertEquals("01/03/2028 20:00", stored.getDueDateTime());
        assertEquals("02/03/2028 20:00", stored.getHideDateTime());
    }

    // TC-C11a: hide equals due (not strictly after) -> false
    @Test
    void testC11a_hideEqualToDueRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = nandanCreate("assignment 3",
                "01/11/2026 09:00", "08/11/2026 23:59", "08/11/2026 23:59");

        assertFalse(result);
        assertEquals(0, course.getAssessments().size());
    }

    // TC-C11b: each date one minute after the previous -> true
    @Test
    void testC11b_datesOneMinuteApartAccepted() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = nandanCreate("assignment 3",
                "23/11/2026 23:58", "23/11/2026 23:59", "24/11/2026 00:00");

        assertTrue(result);
        assertEquals(1, course.getAssessments().size());
        Assessment stored = course.getAssessments().get(0);
        assertEquals("assignment 3", stored.getName());
        assertEquals("23/11/2026 23:58", stored.getPublishDateTime());
        assertEquals("23/11/2026 23:59", stored.getDueDateTime());
        assertEquals("24/11/2026 00:00", stored.getHideDateTime());
    }

    // AYUSH: tests for C3, C4, C5, C6
    // Author: Ayush Dadhaniya S4199023
    private static final String A_PUBLISH = "01/11/2026 09:00";
    private static final String A_DUE = "08/11/2026 23:59";
    private static final String A_HIDE = "15/11/2026 23:59";

    // Valid defaults keep each test focused on the Ayush-owned condition.
    private boolean ayushCreate(String name, String description, int points) {
        return course.createNewAssessment(name, description, points, "pdf", false, "",
                A_PUBLISH, A_DUE, A_HIDE);
    }

    // TC-C3a: exactly three words is the minimum valid description.
    @Test
    void testC3_minimumThreeWordDescriptionAccepted() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = ayushCreate("assignment c3a", "one two three", 10);

        assertTrue(result);
        assertEquals(1, course.getAssessments().size());
        assertEquals("one two three", course.getAssessments().get(0).getDescription());
    }

    // TC-C3b: two words is below the minimum.
    @Test
    void testC3_twoWordDescriptionRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = ayushCreate("assignment c3b", "one two", 10);

        assertFalse(result);
        assertEquals(0, course.getAssessments().size());
    }

    // TC-C4: zero is the boundary value and must be rejected.
    @Test
    void testC4_zeroPointsRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = ayushCreate("assignment c4", "valid assessment description", 0);

        assertFalse(result);
        assertEquals(0, course.getAssessments().size());
    }

    // TC-C5: exactly 50 points is valid for one assessment.
    @Test
    void testC5_fiftyPointsAccepted() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = ayushCreate("assignment c5", "valid assessment description", 50);

        assertTrue(result);
        assertEquals(1, course.getAssessments().size());
        assertEquals(50, course.getAssessments().get(0).getPoints());
    }

    // TC-C5: 51 points exceeds the single-assessment limit.
    @Test
    void testC5_fiftyOnePointsRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition

        boolean result = ayushCreate("assignment c5b", "valid assessment description", 51);

        assertFalse(result);
        assertEquals(0, course.getAssessments().size());
    }

    // TC-C6a: the course total may reach exactly 100 points.
    @Test
    void testC6_courseTotalOfOneHundredAccepted() {
        assertEquals(0, course.getAssessments().size()); // precondition
        assertTrue(ayushCreate("assignment c6a first", "valid assessment description", 50));
        assertEquals(1, course.getAssessments().size());

        boolean result = ayushCreate("assignment c6a second", "valid assessment description", 50);

        assertTrue(result);
        assertEquals(2, course.getAssessments().size());
        assertEquals(100, course.getAssessments().stream().mapToInt(Assessment::getPoints).sum());
    }

    // TC-C6b: adding one point beyond the course limit must fail without storing it.
    @Test
    void testC6_courseTotalOverOneHundredRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition
        assertTrue(ayushCreate("assignment c6b first", "valid assessment description", 50));
        assertTrue(ayushCreate("assignment c6b second", "valid assessment description", 50));
        assertEquals(2, course.getAssessments().size());

        boolean result = ayushCreate("assignment c6b third", "valid assessment description", 1);

        assertFalse(result);
        assertEquals(2, course.getAssessments().size());
    }

    // HARSHAL: tests for C7, C8, C9 and the all-valid case go below this line

// Author: Harshal Pawar S4207724

    // Valid default values, so only the field under test is invalid
    private static final String H_DESC = "submit your report";   // exactly 3 words (C3 lower boundary)
    private static final String H_PUBLISH = "01/11/2026 09:00";
    private static final String H_DUE = "08/11/2026 23:59";
    private static final String H_HIDE = "15/11/2026 23:59";

    // Helper: only name, points, submission type and grouping vary between my tests
    private boolean harshalCreate(String name, int points, String submissionType,
                                  boolean group, String groupingName) {
        return course.createNewAssessment(name, H_DESC, points, submissionType,
                group, groupingName, H_PUBLISH, H_DUE, H_HIDE);
    }

    // Helper: checks that every attribute of a stored assessment matches what was passed in (Note 7)
    private void assertStored(Assessment a, String name, int points, String submissionType,
                              boolean group, String groupingName) {
        assertEquals(name, a.getName());
        assertEquals(H_DESC, a.getDescription());
        assertEquals(points, a.getPoints());
        assertEquals(submissionType, a.getSubmissionType());
        assertEquals(group, a.isGroup());
        assertEquals(groupingName, a.getGroupingName());
        assertEquals(H_PUBLISH, a.getPublishDateTime());
        assertEquals(H_DUE, a.getDueDateTime());
        assertEquals(H_HIDE, a.getHideDateTime());
    }

    // TC-C7: "docx" is just outside the allowed set ("doc" is valid) -> false
    @Test
    void testC7_invalidSubmissionTypeRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition: list is empty

        boolean result = harshalCreate("assignment 4", 10, "docx", false, "");

        assertFalse(result);
        assertEquals(0, course.getAssessments().size()); // nothing added
    }

    // TC-C8: group assessment with an empty grouping name -> false
    @Test
    void testC8_groupWithoutGroupingNameRejected() {
        assertEquals(0, course.getAssessments().size()); // precondition: list is empty

        boolean result = harshalCreate("assignment 4", 10, "zip", true, "");

        assertFalse(result);
        assertEquals(0, course.getAssessments().size()); // nothing added
    }

    // TC-C9: second group assessment reuses grouping name "team a" -> false
    @Test
    void testC9_duplicateGroupingNameRejected() {
        // Preconditions: empty list, then a group assessment using "team a" is accepted
        assertEquals(0, course.getAssessments().size());
        assertTrue(harshalCreate("assignment 4", 10, "zip", true, "team a"));
        assertEquals(1, course.getAssessments().size());

        // Different name, same grouping name
        boolean result = harshalCreate("assignment 5", 10, "zip", true, "team a");

        assertFalse(result);
        assertEquals(1, course.getAssessments().size()); // still only the first one
    }

    // TC-AV (data a): individual assessment at the lower boundaries
    // (1 point, 3-word description, "pdf") -> true, all attributes stored
    @Test
    void testAllValid_a_individualLowerBoundaries() {
        assertEquals(0, course.getAssessments().size()); // precondition: list is empty

        boolean result = harshalCreate("quiz 1", 1, "pdf", false, "");

        assertTrue(result);
        assertEquals(1, course.getAssessments().size());
        assertStored(course.getAssessments().get(0), "quiz 1", 1, "pdf", false, "");
    }

    // TC-AV (data b): group assessment at the single-assessment maximum
    // (50 points, "zip", grouping "team a") -> true, all attributes stored
    @Test
    void testAllValid_b_groupUpperBoundary() {
        assertEquals(0, course.getAssessments().size()); // precondition: list is empty

        boolean result = harshalCreate("project 1", 50, "zip", true, "team a");

        assertTrue(result);
        assertEquals(1, course.getAssessments().size());
        assertStored(course.getAssessments().get(0), "project 1", 50, "zip", true, "team a");
    }

    // TC-AV (data c): second group assessment brings the course total to exactly 100
    // with a different name and grouping name, using "doc" -> true, both stored
    @Test
    void testAllValid_c_totalExactly100() {
        // Preconditions: empty list, then a 50-point group assessment is accepted
        assertEquals(0, course.getAssessments().size());
        assertTrue(harshalCreate("project 1", 50, "zip", true, "team a"));
        assertEquals(1, course.getAssessments().size());

        boolean result = harshalCreate("project 2", 50, "doc", true, "team b");

        assertTrue(result);
        assertEquals(2, course.getAssessments().size());
        assertStored(course.getAssessments().get(0), "project 1", 50, "zip", true, "team a"); // first unchanged
        assertStored(course.getAssessments().get(1), "project 2", 50, "doc", true, "team b");
    }


}