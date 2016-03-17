package ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Remote;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

@Singleton
@ApplicationScoped
@Remote(PeopleCounterRemote.class)
@Startup //инициализируется во время запуска приложения
public class PeopleCounter implements PeopleCounterRemote {

    private long counter;

    @Override
    public long getCounter() {
        return counter;
    }

    @Override
    public void incCounter() {
        counter++;
    }

    @PostConstruct
    private void initZero() {
        counter = 0;
    }

}
