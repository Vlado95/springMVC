package fitec.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.loader.custom.Return;

import fitec.dao.IDao;
import fitec.metier.Auteur;
import fitec.metier.Livre;


public class HbnDaoAuteur implements IDao<Auteur> {

	private Session session ;
	
	private static SessionFactory sessionFactory;
	
//	public Session getSession() {
//		if ( session == null ){
//			session = HbnFactory.getSession();
//		}
//		return session;
//	}

	
	
	
	public HbnDaoAuteur() {
		//session = getSession();
	}

	public Session getSession() {
		if (session == null) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		HbnDaoAuteur.sessionFactory = sessionFactory;
	}

	public List<Auteur> selectAll() {
		/**
		 * Utilisation des interfaces Criteria et Builder
		 */
		session = getSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Auteur> criteria = (CriteriaQuery) cb.createQuery(Auteur.class);
		criteria.from(Auteur.class);
		List<Auteur> auteurs = session.createQuery(criteria).list();
		
		
	/*	List<Auteur> auteurs = new ArrayList<Auteur>();
		try {
			session.beginTransaction();
			String query = "From Auteur ";
			auteurs = session.createQuery(query).list();
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("problème sur selectAll auteur : " + e.getLocalizedMessage());
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur selectAll auteur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
*/
		return auteurs;

	}

	@Override
	public Auteur selectById(Auteur auteur) {

		try {
//			session.beginTransaction();
			int id = auteur.getId();
			auteur = session.find(Auteur.class, id);
			if (auteur == null) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"Problème sur selectById auteur, Auteur introuvable " + auteur.getId());
			}
//			session.getTransaction().commit();

		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur selectById auteur : " + e.getLocalizedMessage());
//			session.getTransaction().rollback();
		}
		return auteur;

	}

	@Override
	public ResultSet selectAllByResultSet() {

		return null;
	}

	@Override
	public int insert(Auteur auteur) {
		try {
			session.beginTransaction();
			session.save(auteur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème d'insertion sur auteur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return auteur.getId();

	}

	@Override
	public boolean update(Auteur auteur) {
		try {
			session.beginTransaction();
			session.update(auteur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur update de auteur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}

		return (auteur != null ? true : false);
	}

	@Override
	public boolean delete(Auteur auteur) throws Exception {
		boolean status = false;
		// Vérifier si un livre référence ancore l'objet Auteur
		// Gestion de la contrainte référentielle au niveau de la couche
		// Persistence
		
		Query query = session.createQuery("from Livre as l where l.auteur.id = :id");
		query.setParameter("id", auteur.getId());

		List<Livre> l = query.getResultList();

		if (l != null && l.size() > 0) {
			throw new Exception("Auteur encore référencé par au moins un livre");
		}
		try {
			session.getTransaction().begin();
			auteur = session.find(Auteur.class, auteur.getId());
			session.delete(auteur);
			session.getTransaction().commit();
			status = true;

		} catch (Exception e) {

			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Methode DELETE AUTEUR : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return status;
	}

	@Override
	public List<Auteur> searchLike(String str) {
		List<Auteur> auteurs= new ArrayList<Auteur>();
		try {
			session.beginTransaction();
			Query req = session.createQuery("from Auteur as e where e.nom like  '%" + str + "%' ");
			auteurs = req.getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"SearchLike error! : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return auteurs;

	}

}
