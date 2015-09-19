package elle.home.protocol;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProtocolDataList {
	
	public String TAG = "ProtocolDataList";
	
	public ProtocolDataList(){	
		timer.schedule(task, 200,100);
	};
	//缓冲队列
	LocalDevCheckList checktmplist;
	
	//发送队列
	public ArrayList<BasicPacket> sendList = new ArrayList<BasicPacket>();
	
	public int sendtime;
	
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){

		@Override
		public void run() {
			sendtime++;
			if(sendtime >30000){
				sendtime = 0;
			}
		}};
	
	public void StopTimer(){
		timer.cancel();
	}	
		
	public void setCheckListTmp(LocalDevCheckList a){
		this.checktmplist = a;
	}
	
	/**将一个包加入到发送队列中
	 * @param packet
	 */
	public synchronized void addOnePacketToSend(BasicPacket packet){
//		Log.d(TAG,"添加了一个需要发送的包:"+DataExchange.byteArrayToHexString(packet.data));
		this.sendList.add(packet);
	}
	
	/**
	 * 根据seq将某个包删除掉，主要是针对ImportanceStatic的包
	 * */
	public synchronized void delOnePacketFromList(int seq){
		for(int i=0;i<this.sendList.size();i++){
			if(this.sendList.get(i).seq == seq){
				this.sendList.remove(i);
			}
		}
	}
	
	/**获取一个需要发送udp包
	 * @return
	 */
	public synchronized DatagramPacket getOnePacketFromSendList(){
		DatagramPacket tmp = null;
		//Log.d(TAG,"sendlist length:"+sendList.size());
		if(this.sendList.size()>0){
			for(int i=0;i<this.sendList.size();i++){
				if(this.sendList.get(i).getImportance()==BasicPacket.ImportLow){
					tmp = this.sendList.get(i).getUdpData();
					this.sendList.remove(i);
					//Log.d(TAG,"send data low import:"+DataExchange.byteArrayToHexString(tmp.getData()));
					return tmp;
				}else if(this.sendList.get(i).getImportance() == BasicPacket.ImportNormal){
					int ret = this.sendList.get(i).couldSend(sendtime);
					if(ret >= 1&& ret<= 3 ){
						tmp = this.sendList.get(i).getUdpData();
						//Log.d(TAG,"~send data normal import:"+DataExchange.byteArrayToHexString(tmp.getData()));
						return tmp;
					}else if(ret>5){
						if(this.sendList.get(i).listener!=null)
							this.sendList.get(i).listener.OnRecvData(null);
						this.sendList.remove(i);
						break;
					}					
				}else if(this.sendList.get(i).getImportance() == BasicPacket.ImportStatic){
					int ret = this.sendList.get(i).couldSend(sendtime);
					if(ret>0&&ret<=3){
						tmp = this.sendList.get(i).getUdpData();
						//Log.d(TAG,"send data high import:"+DataExchange.byteArrayToHexString(tmp.getData()));
						return tmp;
					}else if(ret==25){
						//超时了
						if(this.sendList.get(i).listener!=null)
							this.sendList.get(i).listener.OnRecvData(null);
						if(i<sendList.size()){
							this.sendList.remove(i);
						}
						break;
					}
				}else{
					int ret = this.sendList.get(i).couldSend(sendtime);
					if(ret>0&&ret<=5){
						tmp = this.sendList.get(i).getUdpData();
						//Log.d(TAG,"send data high import:"+DataExchange.byteArrayToHexString(tmp.getData()));
						return tmp;
					}else if(ret>7){
						if(this.sendList.get(i).listener!=null)
							this.sendList.get(i).listener.OnRecvData(null);
						if(i<sendList.size()){
							this.sendList.remove(i);
						}
						break;
					}
				}
			}
		}
		return tmp;
	}
	
	/**收到包进行处理
	 * @param packet
	 */
	public synchronized void dealRecvPacket(PacketCheck packet){
//		Log.d(TAG,"收到数据："+DataExchange.byteArrayToHexString(packet.data));
		if(packet.seq == 0){
			//本地查询数据包回来，内部处理
			//checktmplist.dealOnePacket(packet);
		}else{
			for(int i=0;i<this.sendList.size();i++){
				if(this.sendList.get(i).getSeq()==packet.seq){
					if(this.sendList.get(i).getImportance() == BasicPacket.ImportStatic){
						//静态包的发送流程中，如果回复包的速度太慢的话就会造成发送两次静态包而直接进入到第二个recvcount,可以在listener中判断是否为自己需要的包，并由调用程序删除掉list中的静态包，也就是list不允许自己删除掉静态包。
						this.sendList.get(i).recvcount++;
						if(this.sendList.get(i).recvcount == 1){
							this.sendList.get(i).sendcount = 5;
						}else if(this.sendList.get(i).recvcount >= 2){
							if(this.sendList.get(i).listener!=null){
								this.sendList.get(i).listener.OnRecvData(packet);
							}
							//this.sendList.remove(i);
						}else{
							//异常删除
							this.sendList.remove(i);
						}
					}else{
						if(this.sendList.get(i).listener!=null){
							this.sendList.get(i).listener.OnRecvData(packet);
						}
						this.sendList.remove(i);
					}
					return;
				}
			}
		}
	}
	
	
}
