package EllE.Home.RegService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DevRegControlSql {

	Connection conn;
	
	public DevRegControlSql(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EllEHome","ellehome","dbnow608");			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopCon(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**根据mac查找一个设备
	 * @param info
	 * @return
	 */
	public DevRegInfo findOneDev(long mac){
		DevRegInfo tmp = null;
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from devregcontrol where devmac='"+mac+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				tmp = new DevRegInfo(rs.getLong("devmac"),rs.getString("devcontrolip"),rs.getShort("devcontrolport"));
				break;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return tmp;
	}
	
	/**更新某个设备的信息
	 * @param info
	 * @return
	 */
	public boolean updataOneDev(DevRegInfo info){
		try {
			Statement stmt = conn.createStatement();
			String sql = "update devregcontrol set devcontrolip='"+info.devip+"' , devcontrolport='"+info.devport+"' where devmac='"+info.devmac+"'";
			if(stmt.executeUpdate(sql)!=-1){
				stmt.close();
				return true;
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**得到所有信息的资源
	 * @return
	 */
	public List<DevRegInfo> getAllDev(){
		List<DevRegInfo> tmp = new ArrayList<DevRegInfo>();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from devregcontrol";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				DevRegInfo info = new DevRegInfo(rs.getLong("devmac"),rs.getString("devcontrolip"),rs.getShort("devcontrolport"));
				tmp.add(info);
			}
			rs.close();
			stmt.close();
			return tmp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return tmp;
		}
	}
	
	/**加入一个新的设备
	 * @param info
	 * @return
	 */
	public boolean addNewOne(DevRegInfo info){
		try {
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO devregcontrol(devmac,devcontrolip,devcontrolport) values('"+info.devmac+"','"+info.devip+"','"+info.devport+"')";
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	
}
