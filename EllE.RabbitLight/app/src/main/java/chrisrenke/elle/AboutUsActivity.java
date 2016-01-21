package chrisrenke.elle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import elle.home.utils.SaveDataPreferences;

/**
 * Created by jason on 15/8/21.
 */
public class AboutUsActivity extends Activity {

    private static final String ABOUT_URL = "http://www.elletechnology.com/contact.html";
    private static final String ABOUT_KEY = "about";

    private TextView mTextView;
//    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

//        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webView.loadUrl(ABOUT_URL);

        mTextView = (TextView) findViewById(R.id.tv_content);
        mTextView.setText(SaveDataPreferences.load(this, ABOUT_KEY, ""));

        new Loadhtml().execute();
    }

    public void doCloseClick(View view){
        finish();
    }

//    //改写物理按键——返回的逻辑
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK)
//        {
//            if(webView.canGoBack())
//            {
//                webView.goBack();//返回上一页面
//                return true;
//            }
//            else
//            {
//                finish();
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    class Loadhtml extends AsyncTask<String, String, String>
    {
//        ProgressDialog bar;
        Document doc;

        @Override
        protected String doInBackground(String... params) {
            String content = "";

            try {
                doc = Jsoup.connect(ABOUT_URL).timeout(5000).get();
                Document document = Jsoup.parse(doc.toString());
                Element element = document.select("META").last();
                content = element.attr("content");

//                Elements elements = document.select("META");
//                content = elements.get(elements.size() - 2 ).attr("content");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("doc result", result);
            mTextView.setText(result);
            SaveDataPreferences.save(AboutUsActivity.this, ABOUT_KEY, "");
//            bar.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            bar = new ProgressDialog(AboutUsActivity.this);
//            bar.setMessage("正在加载数据····");
//            bar.setIndeterminate(false);
//            bar.setCancelable(false);
//            bar.show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
