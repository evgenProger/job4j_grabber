package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;

public class Grabber implements Grab {
    private final Properties cfg = new Properties();

    public Store store() {
        Store store = new PsqlStore(cfg);

        return store;
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public void cfg() throws IOException {
        try (InputStream in = new FileInputStream("post.properties")) {
            cfg.load(in);
        }
    }


    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
       /* JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();


    }

        */


    }



}
