package algz.platform.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQLiteServiceImpl implements SQLiteService {
	@Autowired
	private SQLiteDao dao;
	
/* (non-Javadoc)
 * @see algz.platform.test.SQLiteService#getTestDate()
 */
public String getTestDate(){
	return dao.inputDate();
}
}
