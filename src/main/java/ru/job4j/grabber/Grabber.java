package ru.job4j.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.Properties;

public class Grabber implements Grab {
    private final Properties cfg = new Properties();

    public Store store() {
        Store store = new PsqlStore(cfg);
        return store;
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {

    }
}
