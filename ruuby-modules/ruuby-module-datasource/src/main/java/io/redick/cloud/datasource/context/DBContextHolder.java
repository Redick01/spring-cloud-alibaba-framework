package io.redick.cloud.datasource.context;

import com.redick.util.LogUtil;
import io.redick.cloud.datasource.enums.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Redick01
 */
@Slf4j
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> CONTEXT_HOLDER = new ThreadLocal<>();

    private static final AtomicInteger COUNTER = new AtomicInteger(-1);

    public static synchronized void set(DBTypeEnum dbType) {
        CONTEXT_HOLDER.set(dbType);
    }

    public static synchronized DBTypeEnum get() {
        return CONTEXT_HOLDER.get();
    }

    public static void master() {
        log.info(LogUtil.marker(), "master数据库");
        set(DBTypeEnum.MASTER);
    }

    public static void slave() {
        int index = COUNTER.getAndIncrement() % 2;
        if (COUNTER.get() > 9999) {
            COUNTER.set(1);
        }
        if (index == 0) {
            log.info(LogUtil.marker(), "slave1数据库");
            set(DBTypeEnum.SLAVE1);
        } else {
            log.info(LogUtil.marker(), "slave2数据库");
            set(DBTypeEnum.SLAVE2);
        }
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
