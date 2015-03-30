package elle.home.hal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import elle.home.publicfun.DataExchange;

public class InfraMatchThread extends Thread{
	
	public String TAG = "InfraMatchThread";
	
	byte[] data;
	onInfraCheck listener;
	
	Socket socket;
	
	public InfraMatchThread(byte[] data,onInfraCheck listener){
		this.data = data;
		this.listener = listener;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			socket = new Socket("198.199.115.33",28880);
			if(socket==null){
				listener.getInfraCheck(null);
				return;
			}
				
			JSONObject json = new JSONObject();
			try {
				json.put("head", "EllE.Home.InfraMatching");
				json.put("type", "air");
				json.put("data", DataExchange.dbBytesToString(data));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] sendtmp = new byte[6+json.toString().length()];
			sendtmp[0] = 0x01;
			sendtmp[1] = 0x02;
			System.arraycopy(DataExchange.intToFourByte(6+json.toString().length()), 0, sendtmp, 2, 4);
			System.arraycopy(json.toString().getBytes(), 0, sendtmp, 6, json.toString().length());
			
			try {
				OutputStream out = socket.getOutputStream();
				out.write(sendtmp);
				out.flush();
				
				InputStream in = socket.getInputStream();
				byte[] frame = new byte[1024];
				int size = in.read(frame);
				int allsize = 0;
				int readlen = 0;
				if(size>6){
					if(frame[0]==0x01&&frame[1]==0x02){
						allsize = DataExchange.fourByteToInt(frame[2], frame[3], frame[4], frame[5]);
						byte[] tmp = new byte[allsize-6];
						readlen = size-6;
						System.arraycopy(frame, 6, tmp, 0, readlen);
						
						if(allsize != size){
							while((size = in.read(frame))!=-1){
								//Log.d(TAG,"recv data once len:"+size);
								System.arraycopy(frame, 0, tmp, readlen, size);
								readlen = readlen + size;
								if(readlen == (allsize-6)){
									Log.d(TAG,"红外匹配收完了返回数据,数据长度为："+allsize+" 读到的长度:"+readlen);
									break;
								}
							}
						}
						
						// string is too short
						StringBuffer buf = new StringBuffer();
						for(int x=0,count=0;x<allsize;){
							byte[] ss = new byte[100];
							count++;
							if(count*100<allsize){
								System.arraycopy(tmp, x, ss, 0, 100);
								x = x+100;
							}else{
								System.arraycopy(tmp, x, ss, 0, allsize-6-(100*(count-1)));
								x = x+(allsize-(100*(count-1)));
							}
							
							buf.append(new String(ss));
						}

						try {
							JSONObject result = new JSONObject(new String(buf.toString()));
							listener.getInfraCheck(result);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							listener.getInfraCheck(null);
						} 
					}
				}
				Log.d(TAG,"红外匹配流程结束");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				listener.getInfraCheck(null);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listener.getInfraCheck(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listener.getInfraCheck(null);
		}
		
	
	}
	
	public interface onInfraCheck{
		void getInfraCheck(JSONObject json);
	}
	
}
