package io.redick.cloud.datasource.plugin;

import io.redick.cloud.datasource.context.DBContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;

/**
 * @author Redick01
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class }) })
@Slf4j
@Deprecated
public class DynamicDataSourcePlugin implements Interceptor {

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            boolean syncActive = TransactionSynchronizationManager.isSynchronizationActive();
            if (!syncActive) {
                Object[] objects = invocation.getArgs();
                MappedStatement ms = (MappedStatement) objects[0];
                // 读方法
                if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)
                        && !ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    DBContextHolder.slave();
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    if (sql.matches(REGEX)) {
                        // 写
                        DBContextHolder.master();
                    } else {
                        DBContextHolder.slave();
                    }
                }
            } else {
                DBContextHolder.master();
            }
            return invocation.proceed();
        } finally {
            DBContextHolder.clear();
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

}
