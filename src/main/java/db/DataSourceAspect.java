package db;

import org.aspectj.lang.JoinPoint;
import org.springframework.asm.Handle;

/**
 * Created by zmc on 2017/6/9.
 */
public class DataSourceAspect {

    private static final String WRITE = "write";
    private static final String READ = "read";


    public void before(JoinPoint point) {

        //String className = point.getSignature().getClass().getName();
        String method = point.getSignature().getName();

        if (method.contains("insert") || method.contains("add") || method.contains("update") ||
                method.contains("delete") || method.contains("remove")) {
            HandleDataSource.putDataSource(WRITE);
        }


        else {
            HandleDataSource.putDataSource(READ);
        }

    }

}
