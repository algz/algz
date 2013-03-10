package algz.platform.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SQLiteDaoImpl implements SQLiteDao {
	@Autowired
	private SessionFactory sf;
	
	/* (non-Javadoc)
	 * @see algz.platform.test.SQLiteDao#inputDate()
	 */
	@Transactional
	public String inputDate(){
    	Session s=sf.openSession();//.getCurrentSession();
    	s.getTransaction().begin();
    	Object[] objs=(Object[])s.createSQLQuery("select * from person").setMaxResults(1).uniqueResult();
        System.out.println("DAO:"+objs[0]);
        s.createSQLQuery("insert into person (id,name) values ('3','name211')").executeUpdate();
//        s.getTransaction().rollback();
        s.getTransaction().commit();
        return "";
	}
}
