package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.models.Bear;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session openSession() {
		return sessionFactory.openSession();
	}
	
	public static void configureHibernate() {
		Configuration configuration = new Configuration()
				.configure()
				// Programmatic setting of properties
				.setProperty("hibernate.connection.username",
						System.getenv("EM_ROLE"))
				.setProperty("hibernate.connection.password", 
						System.getenv("EM_PASS"))
				.addAnnotatedClass(Bear.class);
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static void shutdownFactory() {
		if (!sessionFactory.isClosed()) {
			sessionFactory.close();			
		}
	}
}
