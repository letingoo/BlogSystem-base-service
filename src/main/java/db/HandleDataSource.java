package db;

/**
 * Created by zmc on 2017/6/9.
 */
public class HandleDataSource {


    public static final ThreadLocal<String> holder = new ThreadLocal<String>();


    public static void putDataSource(String dataSource) {
        holder.set(dataSource);
    }


    public static String getDataSource() {
        return holder.get();
    }
}
