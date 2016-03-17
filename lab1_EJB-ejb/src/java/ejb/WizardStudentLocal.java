package ejb;

import models.Student;

public interface WizardStudentLocal {
    void startWizard();
    boolean saveStudent(Student student);

}
