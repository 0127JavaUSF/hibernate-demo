package com.revature.daos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Bear;
import com.revature.models.Cave;
import com.revature.util.HibernateUtil;

/**
 * Entities in Hibernate are in one of three entity states
 * often called the "persistence states"
 *
 * 1. transient - no database representation of this data
 * 2. persistent - Has representation in the database and is currently
 * 			being tracked by Hibernate in an ongoing session
 * 3. detached - Has representation in the database but is not
 * 			being tracked by a session
 * 
 * Hibernate has a feature called Automatic Dirty Checking
 * Hibernate tracks changes persistent entities during an ongoing session
 * and when a transaction commits any 'dirty' entities will automatically
 * have their state persisted to the database. 'dirty' means that the
 * state has changed since the last database update.
 * 
 * Basic CRUD operations on Session
 * 
 * 1. SAVE
 * 2. PERSIST
 * 3. GET
 * 4. LOAD
 * 5. UPDATE
 * 6. MERGE
 * 7. SAVEORUPDATE
 * 8. DELETE
 * 
 * 
 * Hibernate interfaces:
 * 
 * 1. SessionFactory
 * 2. Transaction
 * 3. Session
 * 4. (Configuration)
 * 5. Query
 * 6. Criteria
 */

public class BearDao {

	Logger logger = Logger.getRootLogger();
	
	public Bear createBear(Bear bear) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			/**
			 * Save vs Persist
			 * Both are Create operations.
			 * save returns serializable id value.
			 * persist returns void. Persist is also JPA.
			 */
			session.persist(bear);
			tx.commit();
			return bear;
		}
	}

	public Bear getBearById(int id) {
		try(Session sess = HibernateUtil.openSession()) {
			
			/**
			 * When we get a Bear it will be tracked by the Hibernate Session.
			 * When we mutate the state of a tracked object and then commit a 
			 * transaction, the state will automatically be persisted to the
			 * database.
			 * Additionally, as the session is aware of what is in persistent
			 * state, it will not repeatedly hit the database to retrieve.  It
			 * simply returns its record of the persistent entity - the same
			 * object you already had.
			 */
			Bear bear = sess.get(Bear.class, id);
			Bear bear2 = sess.get(Bear.class, id);
			Bear bear3 = sess.get(Bear.class, id);
			Bear bear4 = sess.get(Bear.class, id);
			Bear bear5 = sess.get(Bear.class, id);
			Bear bear6 = sess.get(Bear.class, id);
			Bear bear7 = sess.get(Bear.class, id);
			
			logger.warn(bear == bear7);
			
			bear.setWeight(bear.getWeight() + 1);
			return bear;
		}
	}
	
	/**
	 * The Session.load method returns a proxy of an entity.
	 * A proxy in hibernate is an object that when needed will resolve to
	 * the entity it stands in place of.  Essentially, it's a lazy loadable entity.
	 * 
	 * A proxy can only be initialized within an ongoing session. Initialization
	 * will be attempted whenever we access a non-id property or otherwise try
	 * mutate the entity.
	 * 
	 * QC Questions
	 * 1. What is a proxy?
	 * 2. How do you get a proxy?
	 * 3. What are exceptions related to proxies?
	 * 4. How do you load a proxy's real data in?
	 * 5. Will load always return a proxy?
	 * 6. Why use proxies at all?
	 * 7. What is the difference between get and load.
	 * 8. If you load an object with an id that does not exist in the
	 * 		database and try to initialize it outside of a session what should
	 * 		happen?
	 */
	public Bear loadBearById(int id) {
		try(Session sess = HibernateUtil.openSession()) {
			logger.warn("Loading bear");
			Bear bear = sess.load(Bear.class, id);
			Hibernate.initialize(bear.getHoneyJar());
			Hibernate.initialize(bear.getCubs());
			Hibernate.initialize(bear.getParents());
			logger.warn("Bear loaded");
			// This will load the proxy, but prefer Hibernate.initialize()
			// bear.getBreed(); 
			return bear;
		}
	}

	/**
	 * Will throw NonUniqueObjectException in the case that we try to update
	 * something already in the persistence context
	 * @param bear
	 * @return
	 */
	public Bear updateBear(Bear bear) {
		try (Session session = HibernateUtil.openSession()) {
			Transaction tx = session.beginTransaction();
//			Bear oldBear = session.get(Bear.class, bear.getId());
			session.update(bear);
			tx.commit();
			return bear;
		}
	}
	
	public Bear mergeBear(Bear bear) {
		try (Session session = HibernateUtil.openSession()) {
			Transaction tx = session.beginTransaction();
//			Bear loadedBear = session.get(Bear.class, bear.getId());
			System.out.println(bear);
			bear = (Bear) session.merge(bear);
//			logger.warn(bear == loadedBear);
			tx.commit();
			return bear;
		}
	}
	
	public Bear deleteBear(Bear bear) {
		try (Session session = HibernateUtil.openSession()) {
			Transaction tx = session.beginTransaction();
			session.delete(bear);
			tx.commit();
			return bear;
		}
	}

	public List<Bear> getSiblingsById(int id) {
		try(Session session = HibernateUtil.openSession()) {
			Bear bear = session.load(Bear.class, id);
			List<Bear> parents = bear.getParents();
			Set<Bear> siblings = new HashSet<>();
			for(Bear parent : parents) {
				siblings.addAll(parent.getCubs());
			}
			siblings.remove(bear);
			List<Bear> siblingsList = new ArrayList<>();
			siblingsList.addAll(siblings);
			return siblingsList;
		}
	}

	public Cave getCaveByBearId(int id) {
		try(Session session = HibernateUtil.openSession()) {
			Bear bear = session.get(Bear.class, id);
			Cave cave = bear.getCave();
			return cave;
		}
	}
}
