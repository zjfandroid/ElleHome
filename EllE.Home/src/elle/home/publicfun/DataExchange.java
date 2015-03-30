package elle.home.publicfun;

public class DataExchange {
	
	public static byte[] longToEightByte(long data){
		byte[] x = new byte[8];
		x[7] = (byte)(data&0xff);
		x[6] = (byte)((data>>8)&0xff);
		x[5] = (byte)((data>>16)&0xff);
		x[4] = (byte)((data>>24)&0xff);
		x[3] = (byte)((data>>32)&0xff);
		x[2] = (byte)((data>>40)&0xff);
		x[1] = (byte)((data>>48)&0xff);
		x[0] = (byte)((data>>56)&0xff);
		return x;
	}
	
	public static long eightByteToLong(byte data0,byte data1,byte data2,byte data3,byte data4,byte data5,byte data6,byte data7){
		long tmp = 0;
		tmp = ((((long)data0&0xff)<<56)|(((long)data1&0xff)<<48)|(((long)data2&0xff)<<40)|(((long)data3&0xff)<<32)|(((long)data4&0xff)<<24)|(((long)data5&0xff)<<16)|(((long)data6&0xff)<<8)|((long)data7&0xff));
		return tmp;
	}
	
	public static byte[] intToFourByte(int data){
		byte[] x = new byte[4];
		x[3] = (byte)(data&0xff);
		x[2] = (byte)((data>>8)&0xff);
		x[1] = (byte)((data>>16)&0xff);
		x[0] = (byte)((data>>24)&0xff);
		
		return x;
	}
	
	public static int fourByteToInt(byte data0,byte data1,byte data2,byte data3){
		int tmp;
		tmp = (((data0&0xff)<<24)|((data1&0xff)<<16)|((data2&0xff)<<8)|(data3&0xff));
		return tmp;
	}
	
	
	public static byte[] intToTwoByte(int data){
		byte[] x = new byte[2];
		x[1] = (byte)(data&0xff);
		x[0] = (byte)((data>>8)&0xff);
		return x;
	}
	
	public static int twoByteToInt(byte datah,byte datal){
		int temp ;
		temp = ((datah&0xff)<<8)|(datal&0xff);
		return temp;
	}
	
	public static byte strByteToByte(byte a){
		byte x;
		if(a>=48&&a<=57){
			x = (byte) (a-48);
		}else if(a>=65&&a<=70){
			x = (byte) (a-55);
		}else if(a>=97&&a<=102){
			x = (byte) (a-87);
		}
		else{
			x = 0;
		}
		return x;
	}
	
	public static byte strTobyte(String str){
		byte data = 0 ;
		byte[] tmp = str.getBytes();
		if(tmp.length>=2){
			data = (byte) ((strByteToByte(tmp[0])<<4)|strByteToByte(tmp[1]));
		}else{
			
		}
		//data = (byte) (((int)strByteToByte(tmp[0]))*16+strByteToByte(tmp[1]));
		
		return data;
	}
	
	public static byte[] adStringToBytes(String str){
		byte[] data = new byte[8];
		String xstr[] = str.split("-");
		if(xstr.length==8){
			for(int i=0;i<8;i++){
				data[i] = strTobyte(xstr[i]);
			}
		}
		return data;
	}
	
	public static byte[] dbStringToBytes(String str){
		byte[] data = null;
		if(str == null)
			return null;
		String[] xstr = str.split(" ");
		data = new byte[xstr.length];
		for(int i=0;i<data.length;i++){
			data[i] = strTobyte(xstr[i]);
		}
		return data;
	}
	
	public static String dbBytesToString(byte[] data){
		StringBuffer hexString = new StringBuffer();
		for(int i=0;i<data.length;i++){
			int intVal = data[i] & 0xff;			
			if (intVal < 0x10)
				hexString.append("0");
			hexString.append(Integer.toHexString(intVal));
			if((i!=(data.length-1)))
				hexString.append(" ");
		}			
		return hexString.toString().toUpperCase();
	}
	
	public static String byteToHexString(byte a){
		StringBuffer hexString = new StringBuffer();
		int intVal = a & 0xff;
		hexString.append(" 0x");
		if (intVal < 0x10)
			hexString.append("0");
		hexString.append(Integer.toHexString(intVal));
		return hexString.toString();
	}
	
	public static String byteArrayToHexString(byte[] array) {
		StringBuffer hexString = new StringBuffer();
		for (byte b : array) {
			int intVal = b & 0xff;
			hexString.append(" 0x");
			if (intVal < 0x10)
				hexString.append("0");
			hexString.append(Integer.toHexString(intVal));
		}	
		return hexString.toString();    
	}
}
