package elle.fun.infra;

import java.util.ArrayList;

public interface OnDevListListener {

	public void recvDevListListener(boolean isOk,int len,ArrayList<String> datalist);
	
}
