package elle.home.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * 所有 Adapter 都用此 ViewHolder
 * com.eebbk.videoclassjunior.adapter.ViewHolder
 * @author 张建峰 <br/>
 * create at 2014-2-25 上午9:34:34
 */
public class ViewHolder
{
    
    private ViewHolder()
    {
    }

    /**
     * 传入 convertView 及 id 即可返回对应的 view
     * @param view convertView
     * @param id 对应 view 的 id
     * @return view对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id)
    {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        
        if (viewHolder == null)
        {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        
        View childView = viewHolder.get(id);
        
        if (childView == null)
        {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        
        return (T) childView;
    }
}
