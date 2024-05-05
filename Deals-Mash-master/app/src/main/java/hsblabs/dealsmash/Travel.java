package hsblabs.dealsmash;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Haseeb on 2/17/2017.
 */

public class Travel extends AsyncTask<Void,Void,Void> {
    ProgressDialog dialog ;
    Context c;
    private Document htmlDocument;
    private String htmlPageUrl = "http://couponslisto.pk/category/travel/deals";
    List<Object> list = new ArrayList<Object>();
    ListFetcher.OnDataloadListListener onDataloadListListener;

    public Travel(ListFetcher.OnDataloadListListener onDataloadListListener,Context c){
        this.onDataloadListListener = onDataloadListListener;
        this.c = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(c);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Fetching the best deals for you.\nPlease wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            htmlDocument = Jsoup.connect(htmlPageUrl).timeout(10000).get();
            Elements ul = htmlDocument.select("ul[class=blocks blocks-100 blocks-xlg-3 blocks-lg-3 blocks-md-2 blocks-sm-1 masonry-container]");
            Elements cardItems = ul.select("li");
            for (Element link : cardItems) {
                Elements title = link.select("h4[class=widget-title]");
                Elements desc = link.select("p[class=widget-metas type-link]");
                Elements img = link.select("img[class=cover-image]");
                list.add(new DealItem(title.text(),img.attr("abs:src").toString(),desc.text(),"null"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        dialog.dismiss();
        if(onDataloadListListener != null){
            onDataloadListListener.onDataloadListReady(list);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
