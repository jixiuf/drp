package org.jixiuf.drp.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.jixiuf.drp.pojo.Distrib;
import org.jixiuf.drp.pojo.Inventory;
import org.jixiuf.drp.pojo.Material;
import org.jixiuf.drp.pojo.Region;
import org.jixiuf.drp.pojo.User;
import org.jixiuf.drp.pojo.model.PageModel;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {
	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
			Session session = sessionFactory.openSession();
			// sessionFactory = new
			// Configuration().configure("/hibernate.cfg.xml")
			// .buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed.:" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Test
	public void testloadAllUsers() {
		SessionFactory sf = this.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		Query q = s.createQuery("from User");
		List<User> list = q.list();
		System.out.println(list.size());
		for (User u : list) {
			System.out.println(u.getId() + u.getName());
		}
		s.getTransaction().commit();
		s.close();

	}

	@Test
	public void testsaveUser() {
		SessionFactory sf = this.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		User u = new User();
		u.setName("cc");
		s.save(u);
		System.out.println(u.getId());
		s.getTransaction().commit();
		s.close();

	}

	@Test
	public void delRegion() {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		RegionService regionService = (RegionService) ctx
				.getBean("regionService");
		Region r = regionService.findById("69ecc472260744f5a44feb93c02045ac");
		regionService.deleteDistribRegion(r);

	}

	@Test
	public void testLoadClientByClientNo() {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		DistribService clientService = (DistribService) ctx
				.getBean("clientService");
		List<Distrib> clients = clientService.getDao().findByClientno("a");
		System.out.println(clients.size());

	}

	@Test
	public void testSearchMaterial() {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		MaterialService materialService = (MaterialService) ctx
				.getBean("materialService");
		PageModel p = new PageModel(2, 2);
		List<Material> ms = materialService.searchMaterials("b", p);
		System.out.println(ms.size());
		System.out.println(p);

	}

	@Test
	public void testloadUser() {
		SessionFactory sf = this.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		User u = (User) s.load(User.class, "ff80808126a98c9a0126a98c9cd60001");
		System.out.println(u.getName());
		s.getTransaction().commit();
		s.close();

	}

	@Test
	public void testLazy() {
		SessionFactory sf = Test1.getSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		Inventory it = (Inventory) s.load(Inventory.class,
				"j30feebsscw8sserewc3w82wwl9mcw10");
		System.out.println(it.getInitcount());
		System.out.println(it.getDistrib().getName());

		s.getTransaction().commit();
		s.close();

	}

	@Test
	public void testfindAllFlowcard() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		FlowcardService flowcardService = (FlowcardService) ctx
				.getBean("flowcardService");
		PageModel pm = new PageModel();
		// List<Flowcard> clients = flowcardService.getDao().findAll(pm);

		// System.out.println(clients.size());

	}

	@Test
	public void test23() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		RegionService regionServer = (RegionService) ctx
				.getBean("regionService");

		Region r = regionServer.findById("087cef91e2954890a1b9c1ce60212891");

		PageModel pm = new PageModel();

	}
}
