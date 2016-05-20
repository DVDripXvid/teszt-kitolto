package xyz.codingmentor.entity;

/**
 *
 * @author Olivér
 */
public abstract class QueryName {

    public static String COURSE_findAll = "COURSE.findAll";
    public static String COURSE_searchByNameAndTime = "COURSE.searchByNameAndTime";
    public static String USERS_findNonAccepted = "USERS.findNonAccepted";
    public static String USERS_findAll = "USERS.findAll";
    public static String USERS_findByEmail = "USERS.findByEmail";
    public static String TEST_findAll = "TEST.findAll";
    public static String TEST_searchByName = "TEST.searchByName";
    public static String STUDENT_getByEmail = "STUDENT.getByEmail";
    public static String STUDENT_getSubscribers = "STUDENT.getSubscribers";
    
}
