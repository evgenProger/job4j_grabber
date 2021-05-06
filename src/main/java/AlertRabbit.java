import org.quartz.*;
import org.quartz.impl.StdJobRunShellFactory;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobBuilder;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Connection connection = connect();
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Properties read() throws IOException {
        Properties properties = new Properties();
        FileReader reader = new FileReader("src/main/resources/rabbit.properties");
        properties.load(reader);
        return properties;
    }

    private static Connection connect() throws ClassNotFoundException, SQLException, IOException {
        Properties pr = read();
        String driver = pr.getProperty("driver");
        Class.forName(driver);
        String url = pr.getProperty("url");
        String login = pr.getProperty("login");
        String password = pr.getProperty("password");
        return DriverManager.getConnection(url, login, password);
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit execute");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement ps = connection.prepareStatement("insert into rabbit (create_data) values ( ? )")) {
                    ps.setDate(1, Date.valueOf(LocalDate.now()));
                    ps.execute();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
