package ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import models.Student;

@Stateful
@Local(WizardStudentLocal.class)
@ConversationScoped
public class WizardStudent implements WizardStudentLocal {

    @Inject
    private Conversation conversation;
    @EJB
    private StudentDAO dao;

    @Override
    public void startWizard() {
        conversation.begin();
    }

    @Override
    public boolean saveStudent(Student student) {
        if (dao.createNewStudent(student) != -1) {
            conversation.end();
            return true;
        }
        return false;
    }

}
