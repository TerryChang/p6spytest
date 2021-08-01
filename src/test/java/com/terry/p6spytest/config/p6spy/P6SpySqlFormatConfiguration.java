package com.terry.p6spytest.config.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.util.Locale;
import org.hibernate.engine.jdbc.internal.FormatStyle;

public class P6SpySqlFormatConfiguration implements MessageFormattingStrategy {

    /*
    이 파라미터들에 대한 전반적인 설명은 https://p6spy.readthedocs.io/en/latest/configandusage.html#logmessageformat 를 참조
    connectionId: Connection Id
    now: 현재 시간을 millisecond 값으로 표현(System.getCurrentTimeMillis() 메소드 실행 결과 값)
    elapsed: 해당 SQL문이 실행되는데 걸린 시간
             주의해야 할 부분은 결과가 return 되기까지의 시간이 포함된 것은 아니라는 것이다.
             예를 들어 select 문을 실행한다고 가정하면 select 문을 실행하라고 명령을 내리는데 걸린 시간과 실행하라고 명령을 내린 후 그 결과값을 받아오는데 걸린 시간으로 이렇게 구분지어질 수 있다
             여기서는 결과값을 받아오는데 걸린 시간은 포함되어 있지 않다.
             select 문을 실행하라고 명령을 내리는데 걸린 시간을 이 항목에서 return 하기 때문에 0으로 나오는 것이 당연한것이다.(실행 결과를 받아오는데 걸린 시간은 빠져 있으니까..)
    category: 명령문(statement)문의 종류(https://p6spy.readthedocs.io/en/latest/configandusage.html#excludecategories 참조)
    prepared: ? 가 들어가는 SQL문(파라미터에 대한 실제 값이 binding 되기 전의 SQL문)
    sql: 파라미터에 대한 실제 값이 binding 된 SQL문
    url: DB Connection URL
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
        String prepared, String sql, String url) {
        sql = formatSql(category, sql);

        String message = "\nCategory : " + category;
        message += "\nSQL : " + sql;

        return message;
    }

    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().equals("")) {
            return sql;
        }

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.getName().equals(category)) {
            String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
            if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith(
                "comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
            }
        }

        return sql;
    }
}
