package fitec.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

import fitec.dao.IDao;
import fitec.service.HbnFactory;
import fitec.metier.Auteur;
import fitec.metier.Editeur;
import fitec.metier.Livre;

public class HbnDaoLivre implements IDao<Livre> {

	private Session session ;
	
	public Session getSession() {
		if ( session == null ){
			session = HbnFactory.getSession();
		}
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public List<Livre> selectAll() {
		List<Livre> livres = new ArrayList<Livre>();
		try {
			Session session = getSession();
			livres = session.createQuery("From Livre").getResultList();
		}
		catch(Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode selectAll Livre : " + e.getLocalizedMessage());
		}
		
		return livres;
	}
	
	@Override
	public Livre selectById(Livre l) {
		Livre livre = null;
		
		try {
			Session session = getSession();
			livre = session.get(Livre.class, l.getId());
			if (livre == null)
				Logger.getLogger(this.getClass().getName()).log(Level.INFO, "selectById Livre introuvable pour l'id: " + l.getId());
		}
		catch(Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode selectAll Livre : " + e.getLocalizedMessage());
		}
		
		return livre;
	}
	
	// INSERT - NB : ajouter les controles sur id_editeur et id_auteur
	@Override
	public int insert(Livre l) {
		int idNewLivre = 0;
		
		Session session = HbnFactory.getSession();
		
		try {
			session.beginTransaction();
			idNewLivre = (int) session.save(l);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			if(session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
			
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode INSERT Livre : " + e.getLocalizedMessage());
		}
		
		return idNewLivre;
	}
	
	@Override
	public boolean update(Livre l) { 
		Session session = HbnFactory.getSession();
		Livre livre = selectById(l);
		
		if(livre == null)
		{
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode UPDATE Livre : tentative d'update d'un livre inexistant. Id_livre = " + l.getId());
			return false;
		}
		
		// Vérification si id_editeur existe dans la bdd :
		HbnDaoEditeur hbnEdit = new HbnDaoEditeur();
		Editeur ed = hbnEdit.selectById(l.getEditeur());
		if(ed == null)
		{
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode UPDATE Livre : editeur n'est pas référencé dans la base de données. Id_editeur = " + l.getEditeur().getId());
			return false;
		}
		
		// Vérification si id_auteur existe dans la bdd :
		HbnDaoAuteur hbnAuteur = new HbnDaoAuteur();
		Auteur aut = hbnAuteur.selectById(l.getAuteur());
		if(aut == null)
		{
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode UPDATE Livre : auteur n'est pas référencé dans la base de données. Id_auteur = " + l.getAuteur().getId());
			return false;
		}
		
		livre.setTitre(l.getTitre());
		livre.setNbPages(l.getNbPages());
		livre.setPrix(l.getPrix());
		livre.setAuteur(l.getAuteur());
		livre.setEditeur(l.getEditeur());
		
		try {
			session.beginTransaction();
			session.update(livre);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			if(session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
			
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode UPDATE Livre : " + e.getLocalizedMessage());
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean delete(Livre l) {
		Session session = HbnFactory.getSession();
		Livre livre = selectById(l);
		
		if (livre == null) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Methode DELETE Livre : tentative de delete d'un livre inexistant. Id_livre = " + l.getId());
			return false;
		}

		try {
			session.beginTransaction();
			session.delete(livre);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			if(session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
			
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode DELETE Livre : " + e.getLocalizedMessage());
			
			return false;
		}
		
		Livre deletedLivre = selectById(l);
		return deletedLivre == null;
	}
	
	@Override
	public List<Livre> searchLike(String str) {
		List<Livre> livres = new ArrayList<Livre>();
		try {
			Session session = HbnFactory.getSession();
			livres = session.createQuery("from Livre as l where l.titre like '%" + str + "%'").getResultList();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Methode searchlike Livre : " + e.getLocalizedMessage());
		}
		
		return livres;
	}

	@Override
	public ResultSet selectAllByResultSet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
