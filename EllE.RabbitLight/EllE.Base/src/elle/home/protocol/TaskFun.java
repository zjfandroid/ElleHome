package elle.home.protocol;

public class TaskFun{
	public byte num;
	public byte day;
	public byte hour;
	public byte min;
	public byte ss;
	public byte fun;
	
	public TaskFun(byte num,byte hour,byte min,byte ss,byte fun){
		this(num, hour, min, ss, fun, (byte)0);
	}
	
	public TaskFun(byte num,byte hour,byte min,byte ss,byte fun,byte day){
		this.num = num;
		this.hour = hour;
		this.min = min;
		this.ss = ss;
		this.fun = fun;
		this.day = day;
	}
	
	public void setDay(byte mon,byte tues,byte wen,byte thur,byte fri,byte sat,byte sun,byte isCycyle){
		day = 0;
		if(mon!=0){
			day = (byte) (day|0x01);
		}
		if(tues!=0){
			day = (byte) (day|0x02);
		}
		if(wen!=0){
			day = (byte) (day|0x04);
		}
		if(thur!=0){
			day = (byte) (day|0x08);
		}
		if(fri!=0){
			day = (byte) (day|0x10);
		}
		if(sat!=0){
			day = (byte) (day|0x20);
		}
		if(sun!=0){
			day = (byte) (day|0x40);
		}
		if(isCycyle!=0){
			day = (byte) (day|0x80);
		}
	}
}
