package org.zkoss.todo.event;

/**
 * Event DAO.
 * 
 * @author robbiecheng
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventDAO {	
	
	private final DataSource ds = DataSource.INSTANCE;
	
	public List<TodoEvent> findAll() {
		List<TodoEvent> allEvents = new ArrayList<TodoEvent>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from event");

			// fetch all events from database
			TodoEvent evt;
			
			while (rs.next()) {
				evt = new TodoEvent();
				evt.setId(rs.getString(1));
		        evt.setName(rs.getString(2));
				evt.setPriority(rs.getInt(3));
				evt.setDate(rs.getDate(4));				
				allEvents.add(evt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
	public boolean delete(TodoEvent evt) {
		return execute("delete from event where evtid = '" + evt.getId() + "'");
	}
	
	public boolean insert(TodoEvent evt) {
		return execute("insert into event(evtid,name,priority,evtdate) " +
                    "values ('" + UUID.randomUUID().toString() + "','" + evt.getName() +
                    "'," + evt.getPriority() + ",'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		            .format(evt.getDate()) + "')");
	}
	
	public boolean update(TodoEvent evt) {
        return execute("update event set name = '" + evt.getName() + 
                    "', priority = " + evt.getPriority() + ", evtdate = '" + 
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(evt.getDate())+ 
                    "' where evtid = '" + evt.getId() + "'");
    }
	
	private boolean execute(String sql) {

		try {
            Statement stmt = ds.getStatement();
            stmt.execute(sql);
            if (stmt != null) {
                stmt.close();
            }
            
            return true;
        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        } finally {
            ds.close();
        }
	}
}