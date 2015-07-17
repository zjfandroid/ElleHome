package elle.fun.infra;

import java.util.ArrayList;

public interface OnBrandListListener {

	public void recvBrandList(boolean isok,int len,ArrayList<String> brandlist);
	
}
