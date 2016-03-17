package beans;

import ejb.PeopleCounterRemote;
import ejb.StudentDAO;
import ejb.WizardStudentLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import models.Student;

@Named
@SessionScoped
public class StudentBean implements Serializable {

    @EJB
    private StudentDAO dao;

    @EJB
    private PeopleCounterRemote counter;

    @EJB
    private WizardStudentLocal wizard;

    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> list() {
        return dao.readAllStudents();
    }

    public void visit() {
        counter.incCounter();
    }
    
    public long getCounter() {
        return counter.getCounter();
    }
    
    public String createStudent(){
        wizard.startWizard();
        return "createStudent";
    }
    
    public String createStudentSecond(){
        return "secondPage";
    }
    
    public String finishCreate() {
        wizard.saveStudent(student);
        student = new Student();
        return "index";
    }
    
    @PostConstruct
    private void onCreate(){
        student = new Student();
    }

}
