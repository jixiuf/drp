import org.jixiuf.drp.service.DistribService;
import org.jixiuf.drp.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DocTest{
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");
 DistribService ds= (DistribService)ctx.getBean("distribService");
 UserService users= (UserService)ctx.getBean("userService");

	// ds.reportDistribLevelPieInWord("-1", users.loadUser("ff80808126a990800126a99081c90001"));

}}