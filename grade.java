import java.util.*;

class Main {

    static Scanner scanner = new Scanner(System.in);
    static List<Student> students = new ArrayList<>();
    static List<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\nStudent Grade Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Assign Grade");
            System.out.println("4. Calculate GPA");
            System.out.println("5. View Students");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addCourse();
                    break;
                case 3:
                    assignGrade();
                    break;
                case 4:
                    calculateGPA();
                    break;
                case 5:
                    viewStudents();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Add a student to the system
    private static void addStudent() {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        students.add(new Student(name, studentId));
        System.out.println("Student added successfully.");
    }

    // Add a course to the system
    private static void addCourse() {
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        courses.add(new Course(courseName, courseCode));
        System.out.println("Course added successfully.");
    }

    // Assign grade to a student for a course
    private static void assignGrade() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course course = findCourse(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter Grade: ");
        double grade = scanner.nextDouble();
        student.assignGrade(course, grade);
        System.out.println("Grade assigned successfully.");
    }

    // Calculate GPA of a student
    private static void calculateGPA() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        double gpa = student.calculateGPA();
        System.out.println("GPA of " + student.getName() + ": " + gpa);
    }

    // View all students and their details
    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Helper method to find a student by ID
    private static Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    // Helper method to find a course by code
    private static Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

// Student class
class Student {
    private String name;
    private String studentId;
    private Map<Course, Double> grades = new HashMap<>();

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void assignGrade(Course course, double grade) {
        grades.put(course, grade);
    }

    public double calculateGPA() {
        double totalGrades = 0;
        for (double grade : grades.values()) {
            totalGrades += grade;
        }
        return grades.isEmpty() ? 0 : totalGrades / grades.size();
    }

    @Override
    public String toString() {
        return "Student[Name: " + name + ", ID: " + studentId + ", GPA: " + calculateGPA() + "]";
    }
}

// Course class
class Course {
    private String courseName;
    private String courseCode;

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public String toString() {
        return "Course[Name: " + courseName + ", Code: " + courseCode + "]";
    }
}
