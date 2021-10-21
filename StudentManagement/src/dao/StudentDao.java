package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Student;

public class StudentDao {
	private static String jdbcURL = "jdbc:mysql://localhost:3306/sona?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "root";

    private static final String INSERT_STUDENT_SQL = "INSERT INTO Student" + "  (student_name,student_dob,student_doj) VALUES " +
        " (?, ?, ?);";

    private static final String SELECT_STUDENT_BY_ID = "select student_no,student_name,student_dob,student_doj from Student where student_no =?";
    private static final String SELECT_ALL_STUDENTS = "select * from Student";
    private static final String DELETE_STUDENTS_SQL = "delete from Student where student_no = ?;";
    private static final String UPDATE_STUDENTS_SQL = "update Student set student_name = ?,student_dob= ?, student_doj =? where student_no = ?;";

    public StudentDao() {}

    protected static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
           
            e.printStackTrace();
        }
        return connection;
    }

    public void insertStudent(Student student) throws SQLException {
        System.out.println(INSERT_STUDENT_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); 
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            preparedStatement.setString(1, student.getStudent_name());
            preparedStatement.setDate(2, student.getStudent_dob());
            preparedStatement.setDate(3, student.getStudent_doj());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static Student selectStudent(int student_no) {
        Student student = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID);) {
            preparedStatement.setInt(1, student_no);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String student_name = rs.getString("student_name");
                Date student_dob = rs.getDate("student_dob");
                Date student_doj = rs.getDate("student_doj");
                student = new Student(student_name, student_dob, student_doj);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return student;
    }

    public  List < Student > selectAllStudents() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Student > student = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int student_no = rs.getInt("student_no");
                String student_name = rs.getString("student_name");
                Date student_dob = rs.getDate("student_dob");
                Date student_doj = rs.getDate("student_doj");
                student.add(new Student(student_no,student_name, student_dob, student_doj));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return student;
    }

    public boolean deleteStudent(int student_no) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_STUDENTS_SQL);) {
            statement.setInt(1, student_no);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateStudent(Student student) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); 
        PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENTS_SQL);) {
            statement.setString(1, student.getStudent_name());
            statement.setDate(2, student.getStudent_dob());
            statement.setDate(3, student.getStudent_doj());
            statement.setInt(4, student.getStudent_no());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
