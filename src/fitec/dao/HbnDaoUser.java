package fitec.dao;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import fitec.dao.IDao;
import fitec.service.HbnFactory;
import fitec.metier.User;

//@Repository
public class HbnDaoUser implements IDao<User> {

	private Session session ;
	
	private static SessionFactory sessionFactory;
	
	
	
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
		HbnDaoUser.sessionFactory = sessionFactory;
	}



	public void setSession(Session session) {
		this.session = session;
	}



	@Override
	public List<User> selectAll() {
		Session session = getSession();
		String req = "From User";
		List<User> users = session.createQuery(req).getResultList();
		return users;
	}

	@Override
	public ResultSet selectAllByResultSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectById(User objet) {
		Session session = getSession();
		String req = "From User u where id = :id";
		objet = (User) session.createQuery(req).setParameter("id", objet.getId()).getSingleResult();
		return objet;
	}

	@Override
	public int insert(User objet) {
		Session session = getSession();
		session.getTransaction().begin();		
		session.saveOrUpdate(objet);
		session.getTransaction().commit();
		return objet.getId();
	}

	@Override
	public boolean update(User objet) {
		Session session = getSession();
		session.getTransaction().begin();
		session.saveOrUpdate(objet);
		session.flush();
		session.getTransaction().commit();
		return (objet != null?true:false);
	}

	@Override
	public boolean delete(User objet) {
		Session session = getSession();
		session.getTransaction().begin();
		objet = session.find(User.class, objet.getId());
		
		try {
			session.delete(objet);
		} catch (Exception e) {
			return false;
		}
		session.getTransaction().commit();
		return true;
	}

	@Override
	public List<User> searchLike(String str) {
		 Session session = getSession();
		 String req = "From User as u where u.nom like '%"+str+"%' ";
		 List<User> users = session.createQuery(req).getResultList();
		return users;
	}
}
