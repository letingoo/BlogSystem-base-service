package db;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by zmc on 2017/6/9.
 */
public class RouteDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        return HandleDataSource.getDataSource();
    }
}
