
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.StatefulJob;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//因为 报表统计阶段会生成一些临时文件，这些文件如果不定时清除会充满服务器硬盘空间，
//此利用quartz 的定时功能 ，每隔15天对临时目录进行清理
public class Job1 implements Job ,StatefulJob {
	Logger log = LoggerFactory.getLogger(Job1.class);

	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		try {
			Thread.sleep(5 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(new SimpleDateFormat("yyyyMMdd-hhmmss")
				.format(new Date()));
	}

	Scheduler sched = null;

	public void afterPropertiesSet() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();

		sched = sf.getScheduler();
		JobDetail deleteTmpFilesJob = new JobDetail("delete tmp word file ",
				"group1", Job1.class);

		SimpleTrigger trigger = new SimpleTrigger(
				"trigger for deleting tmp word file ", "group1");
		trigger.setRepeatInterval(3*1000);

		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);// 无限次运行
		Date d =sched.scheduleJob(deleteTmpFilesJob, trigger);

  System.out.println(trigger.getMisfireInstruction());
		sched.start();
	}

	 public static void main(String[] args) {
		try {
			new Job1().afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
