package elle.home.protocol;

import java.util.TimeZone;

public class GetTimeZone {

	public GetTimeZone(){
		
	}
	
	/**
	 * 获取当前时区
	 * @return
	 */
	public static byte getTimeZone(){
		byte tmp = 0;
		TimeZone tz = TimeZone.getDefault();
		int offsetMillis = tz.getRawOffset();
		int offsetMinutes = offsetMillis / 60000; 
		int offsetHours = offsetMinutes / 60;
		if(offsetHours>=0){
			tmp = (byte) (101+offsetHours);
		}else{
			tmp = (byte)(125+offsetHours);
		}
		
		return tmp;
	}
	
}
