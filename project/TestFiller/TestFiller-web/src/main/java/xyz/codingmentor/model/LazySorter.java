package xyz.codingmentor.model;

import java.util.Comparator;
import org.primefaces.model.SortOrder;
import xyz.codingmentor.entity.Course;

public class LazySorter implements Comparator<Course> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Course course1, Course course2) {
        try {
            Object value1 = Course.class.getField(this.sortField).get(course1);
            Object value2 = Course.class.getField(this.sortField).get(course2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
