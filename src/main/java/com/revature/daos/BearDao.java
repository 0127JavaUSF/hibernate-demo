package com.revature.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Bear;
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
 *
 */

public class BearDao {

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

}
