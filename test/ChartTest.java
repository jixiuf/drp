import java.awt.Font;

import org.jixiuf.drp.service.DistribService;
import org.jixiuf.drp.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ChartTest

{
	public static Font getDefaultFont() {
		return getDefaultFont(13);
	}

	public static Font getDefaultFont(int size) {
		return new Font("文泉驿微米黑", Font.PLAIN, size);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
		"applicationContext.xml");
 DistribService ds= (DistribService)ctx.getBean("distribService");
 UserService users= (UserService)ctx.getBean("userService");
 
	 ds.reportDistribLevelInBar("070c35bb9a4b4fa4b1dce4db3bb4a91a", users.loadUser("ff80808126a990800126a99081c9000"));
	 
}}