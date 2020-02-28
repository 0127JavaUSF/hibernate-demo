package com.revature.daos;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Cave;
import com.revature.util.HibernateUtil;

public class CaveDao {

	public Cave getById(int id) {
		try(Session session = HibernateUtil.openSession()) {
			Cave cave = session.get(Cave.class, id);
			Hibernate.initialize(cave.getBears());
			return cave;
		}
	}

	public Cave save(Cave cave) {
		try(Session session = HibernateUtil.openSession()) {
			Transaction tx = session.beginTransaction();
			session.persist(cave);
			tx.commit();
			return cave;
		}
	}

	public Cave update(Cave cave) {
		try(Session session = HibernateUtil.openSession()) {
			Transaction tx = session.beginTransaction();
			cave = (Cave) session.merge(cave);
			tx.commit();
			return cave;
		}
	}

}
