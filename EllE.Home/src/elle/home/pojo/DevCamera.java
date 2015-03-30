package elle.home.pojo;

/**
 * 摄像头
 * @author jason
 *
 */
public class DevCamera {
	
	private int cameraType;
	private String strMac;
	private String strName;
	private String strDeviceID;
	private String strIpAddr;
	private int port;
	
	public DevCamera(int cameraType, String strMac, String strName,
			String strDeviceID, String strIpAddr, int port) {
		super();
		this.cameraType = cameraType;
		this.strMac = strMac;
		this.strName = strName;
		this.strDeviceID = strDeviceID;
		this.strIpAddr = strIpAddr;
		this.port = port;
	}

	public int getCameraType() {
		return cameraType;
	}

	public void setCameraType(int cameraType) {
		this.cameraType = cameraType;
	}

	public String getStrMac() {
		return strMac;
	}

	public void setStrMac(String strMac) {
		this.strMac = strMac;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrDeviceID() {
		return strDeviceID;
	}

	public void setStrDeviceID(String strDeviceID) {
		this.strDeviceID = strDeviceID;
	}

	public String getStrIpAddr() {
		return strIpAddr;
	}

	public void setStrIpAddr(String strIpAddr) {
		this.strIpAddr = strIpAddr;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
