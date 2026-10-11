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

    // AYUSH: tests for C3, C4, C5, C6 go below this line




    // HARSHAL: tests for C7, C8, C9 and the all-valid case go below this line




}