package ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import models.Student;


@Stateless
@LocalBean
public class StudentDAO {

    @Resource(lookup = "jdbc/students")
    private DataSource dataSources;

    public List<Student> readAllStudents() {
        try (Connection conn = dataSources.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * "
                    + "FROM students");
            ResultSet res = statement.executeQuery();
            ArrayList<Student> students = new ArrayList<>();
            while (res.next()) {
                Student student = new Student();
                student.setId(res.getInt(1));
                student.setName(res.getString(2));
                student.setDate(res.getDate(3));
                student.setHeight(res.getInt(4));
                students.add(student);
            }
            return students;
        } catch (Exception e) {
            throw new RuntimeException("Error: readAllStudents", e);
        }
    }

    public Student readStudent(int idStudent) {
        try (Connection conn = dataSources.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT name, "
                    + "dateOfBirth, height "
                    + "FROM students "
                    + "WHERE idStudent=?");
            statement.setInt(1, idStudent);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                Student student = new Student();
                student.setId(idStudent);
                student.setName(res.getString(1));
                student.setDate(res.getDate(2));
                student.setHeight(res.getInt(3));
                return student;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error: readStudent", e);
        }
    }

    public int createNewStudent(Student student) {
        try (Connection conn = dataSources.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO students "
                    + "(name, dateOfBirth, height) VALUES (?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, student.getName());
            statement.setDate(2, student.getDate());
            statement.setInt(3, student.getHeight());
            statement.execute();
            ResultSet key = statement.getGeneratedKeys();
            if (key.next()) {
                return key.getInt(1);
            }
            return -1;
        } catch (Exception e) {
            throw new RuntimeException("Error: createNewStudent", e);
        }
    }

    public boolean updateStudent(Student student) {
        try (Connection conn = dataSources.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE students "
                    + "SET name=?, dateOfBirth=?, height=? "
                    + "WHERE idStudent=?");
            statement.setString(1, student.getName());
            statement.setDate(2, student.getDate());
            statement.setInt(3, student.getHeight());
            statement.setInt(4, student.getId());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error: updateStudent", e);
        }
    }

    public boolean deleteStudent(int idStudent) {
        try (Connection conn = dataSources.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE "
                    + "FROM students "
                    + "WHERE idStudent=?");
            statement.setInt(1, idStudent);
            statement.execute();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error: deleteStudent", e);
        }
    }

}
