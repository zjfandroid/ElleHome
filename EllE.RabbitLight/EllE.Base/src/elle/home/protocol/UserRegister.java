package elle.home.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import elle.home.publicfun.DataExchange;
import elle.home.utils.ShowInfo;

/**
 * 用户注册
 * @author jason
 *
 */
public class UserRegister {

	private static UserRegister mUserRegister;
	
	private UserRegister(){
	}
	
	public static UserRegister getInstance(){
		if(null == mUserRegister){
			mUserRegister = new UserRegister();
		}
		
		return mUserRegister;
	}
	
	public void getVerificitionCode(OnDataListener listener){
		JSONObject json = new JSONObject();
		try {
			json.put("fun", "getVerificitionCode");
			getSocketBuffer(30011, json, listener);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * fun:regUser userName:xxxx userPassword:xxxx userPasswordMD5:xxxx userRealName:xxxx userMail:xxxxxxx
	 * @param name
	 * @param pwd
	 */
	public void register(String name, String pwd, String realname, String regUserEmail, OnDataListener listener){
		JSONObject json = new JSONObject();
		try {
			json.put("fun", "regUser");
			json.put("userName", name);
			json.put("userPassword", pwd);
			json.put("userPasswordMD5", getStringMD5(pwd));
			json.put("userRealName", realname);
			json.put("userMail", regUserEmail);
			
			getSocketBuffer(30010, json, listener);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * fun:verificationUser userName:xxxxxx userPasswordMD5:xxxxxxx
	 * @param name
	 * @param pwd
	 */
	public void login(String name, String pwd, OnDataListener listener){
		JSONObject json = new JSONObject();
		try {
			json.put("fun", "verificationUser");
			json.put("userName", name);
			json.put("userPassword", pwd);
			json.put("userPasswordMD5", getStringMD5(pwd));
			getSocketBuffer(30012, json, listener);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public void backupDevData(String userName, JSONObject data, OnDataListener listener){
		backupDevData("json", userName, Long.toString(System.currentTimeMillis()), data, listener);
	}
	
	/**
	 * 备份数据 fun:backupDevData ver:当前硬件存储的格式版本 userName:xxxxx backupDate:xxxx-xx-xx xx:xx:xx userData:xxxxxxxxxxx
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public void backupDevData(String ver, String userName, String backupDate, JSONObject data, OnDataListener listener){
		JSONObject json = new JSONObject();
		try {
			json.put("fun", "backupDevData");
			json.put("ver", ver);
			json.put("userName", userName);
			json.put("backupDate", backupDate);
			json.put("userData", data.toString());
			
			ShowInfo.printLogW("______backupDevData json_____" + json);
			
			getSocketBuffer(30020, json, listener);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到当前版本 fun:getBackup userName:xxxxx
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public void getBackupVer(String userName, OnDataListener listener){
		JSONObject json = new JSONObject();
		try {
			json.put("fun", "getBackup");
			json.put("userName", userName);
			getSocketBuffer(30021, json, listener);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getStringMD5(String str) throws NoSuchAlgorithmException {
        byte [] buf = str.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(buf);
        byte [] tmp = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b:tmp) {
            sb.append(Integer.toHexString(b&0xff));
        }

        return sb.toString();
    }
	
	private void getSocketBuffer(final int dstPort, JSONObject json, final OnDataListener listener) throws UnsupportedEncodingException {
		byte[] jsons = json.toString().getBytes("utf-8");
		ShowInfo.printLogW("_______getSocketBuffer_______"+ jsons.length);
		final byte[] sendtmp = new byte[6 + jsons.length];
		sendtmp[0] = 0x01;
		sendtmp[1] = 0x02;
		System.arraycopy(DataExchange.intToFourByte(6 + jsons.length), 0, sendtmp, 2, 4);
		System.arraycopy(jsons, 0, sendtmp, 6, jsons.length);
		ShowInfo.printLogW(sendtmp.length + "_____allsize_____" + new String(sendtmp));
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					startSocketThread(dstPort, listener, sendtmp);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void startSocketThread(int dstPort, OnDataListener listener,
			byte[] sendtmp) throws UnknownHostException, IOException,
			SocketException {
		Socket socket = new Socket("198.199.115.33", dstPort);
//		Socket socket = new Socket("192.168.30.102", dstPort);
		socket.setSoTimeout(8000);
		OutputStream out = socket.getOutputStream();
		out.write(sendtmp);
		out.flush();
		
		byte[] frame = new byte[1024];
		InputStream in = socket.getInputStream();
		int size = in.read(frame);
		int allsize = 0;
		int readlen = 0;
		if(size>6){
			if(frame[0]==0x01&&frame[1]==0x02){
				allsize = DataExchange.fourByteToInt(frame[2], frame[3], frame[4], frame[5]);
				if(allsize <= 6){
					socket.close();
					return;
				}
				byte[] tmp = new byte[allsize-6];
				ShowInfo.printLogW(tmp.length + "_____allsize_____" + allsize);
				readlen = size-6;
				System.arraycopy(frame, 6, tmp, 0, readlen);
				
				if(allsize != size){
					while((size = in.read(frame))!=-1){
						System.arraycopy(frame, 0, tmp, readlen, size);
						readlen = readlen + size;
						if(readlen == (allsize-6)){
							break;
						}
					}
				}
				
				// string is too short
//				StringBuffer buf = new StringBuffer();
//				for(int x=0, count=0; x<allsize;){
//					byte[] ss = new byte[100];
//					count++;
//					if(count*100 < allsize){
//						System.arraycopy(tmp, x, ss, 0, 100);
//						x = x + 100;
//					}else{
//						System.arraycopy(tmp, x, ss, 0, allsize-6-(100*(count-1)));
//						x = x + (allsize-(100*(count-1)));
//					}
//					
//					buf.append(new String(ss, "utf-8"));
//				}
//				socket.close();
//				ShowInfo.printLogW(buf.length() + "____buf.toString()______" + buf.toString());
				
				String str = new String(tmp, "utf-8");
				ShowInfo.printLogW(str.length() + "____buf.toString()______" + str);
				
				try {
					JSONObject result = new JSONObject(str);
					listener.onDataBack(result);
				} catch (JSONException e) {
					e.printStackTrace();
					listener.onDataBack(null);
				} 
			}
		}
	}
	
	public interface OnDataListener{
		void onDataBack(JSONObject json);
	}
}
