package xyz.codingmentor.entity;

/**
 *
 * @author Olivér
 */
public abstract class QueryName {

    public static String COURSE_findForUser = "COURSE.findForUser";
    public static String COURSE_findAll = "COURSE.findAll";
    public static String COURSE_searchByNameAndTime = "COURSE.searchByNameAndTime";
    public static String USERS_findNonAccepted = "USERS.findNonAccepted";
    public static String USERS_findAll = "USERS.findAll";
    public static String USERS_findByEmail = "USERS.findByEmail";
    public static String TEST_findAll = "TEST.findAll";
    public static String TEST_searchByName = "TEST.searchByName";
    public static String STUDENT_getByEmail = "STUDENT.getByEmail";
    public static String STUDENT_getSubscribers = "STUDENT.getSubscribers";
    public static String ROLE_ByName = "ROLE.ByName";
    public static String ROLE_findAll = "ROLE.findAll";
    public static String ROLE_findByUser = "ROLE.findByUser";
    public static String TEACHER_findByEmail = "TEACHER.findByEmail";
    public static String FILLEDTEST_countReadyByTestId = "FILLEDTEST.countReadyByTestId";
    public static String FILLEDTEST_countNotReadyByTestId = "FILLEDTEST.countNotReadyByTestId";
    public static String COURSE_countStudentsById = "COURSE.countStudentsById";
    public static String TEST_findByTeacherId = "TEST.findByTeacherId";
    public static String COURSE_findByTeacher = "COURSE.findByTeacher";
    public static String FILLED_TEST_findByTestIdAndReady = "FILLEDTEST.findByTestIdAndReady";
    
}
