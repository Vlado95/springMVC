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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.loader.custom.Return;
import org.springframework.stereotype.Repository;

import fitec.dao.IDao;
import fitec.metier.Editeur;
import fitec.metier.Livre;
import fitec.metier.User;

//@Repository
public class HbnDaoEditeur implements IDao<Editeur> {
	
	private static SessionFactory sessionFactory;
	
	private Session session ;
	
	
	public Session getSession() {
		if ( session == null ){
			session = sessionFactory.openSession();
		}
		return session;
	}
	
	

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	public static void setSessionFactory(SessionFactory sessionFactory) {
		HbnDaoEditeur.sessionFactory = sessionFactory;
	}



	public void setSession(Session session) {
		this.session = session;
	}



	public HbnDaoEditeur() {
		//session = getSession();
	}

	public List<Editeur> selectAll() {
		List<Editeur> editeurs = new ArrayList<Editeur>();
		try {
			session = getSession();
			session.beginTransaction();
			String query = "From Editeur ";
			editeurs = session.createQuery(query).list();
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("problème sur selectAll editeur : " + e.getLocalizedMessage());
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur selectAll editeur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}

		return editeurs;

	}

	@Override
	public Editeur selectById(Editeur editeur) {

		try {
			session.beginTransaction();
			int id = editeur.getId();
			editeur = session.find(Editeur.class, id);
			if (editeur == null) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"Problème sur selectById editeur, Editeur introuvable " + editeur.getId());
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur selectById editeur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return editeur;

	}

	@Override
	public ResultSet selectAllByResultSet() {

		return null;
	}

	@Override
	public int insert(Editeur editeur) {
		try {
			session.beginTransaction();
			session.save(editeur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème d'insertion sur editeur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return editeur.getId();

	}

	@Override
	public boolean update(Editeur editeur) {
		try {
			session.beginTransaction();
			session.update(editeur);
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Problème sur update de editeur : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}

		return (editeur != null ? true : false);
	}

	@Override
	public boolean delete(Editeur editeur) throws Exception {
		boolean status = false;
		// Vérifier si un livre référence ancore l'objet Editeur
		// Gestion de la contrainte référentielle au niveau de la couche
		// Persistence
		
		Query query = session.createQuery("from Livre as l where l.editeur.id = :id");
		query.setParameter("id", editeur.getId());

		List<Livre> l = query.getResultList();

		if (l != null && l.size() > 0) {
			throw new Exception("Editeur encore référencé par au moins un livre");
		}
		
		try {
			session.getTransaction().begin();
			editeur = session.find(Editeur.class, editeur.getId());
			session.delete(editeur);
			session.getTransaction().commit();
			status = true;

		} catch (Exception e) {

			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Methode DELETE EDITEUR : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return status;
	}

	@Override
	public List<Editeur> searchLike(String str) {
		List<Editeur> editeurs= new ArrayList<Editeur>();
		try {
			session.beginTransaction();
			Query req = session.createQuery("from Editeur as e where e.nom like  '%" + str + "%' ");
			editeurs = req.getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"SearchLike error! : " + e.getLocalizedMessage());
			session.getTransaction().rollback();
		}
		return editeurs;

	}

}
