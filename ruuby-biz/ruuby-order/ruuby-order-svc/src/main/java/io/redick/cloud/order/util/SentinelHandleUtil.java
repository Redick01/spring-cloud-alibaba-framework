package io.redick.cloud.order.util;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson2.JSON;
import io.redick.cloud.common.constants.Constants;
import io.redick.cloud.common.domain.R;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.reactive.ClientHttpResponse;

/**
 * @author Redick01
 */
public class SentinelHandleUtil {

    public static SentinelClientHttpResponse blockHandle(HttpRequest request, byte[] body, ClientHttpRequestExecution execution
            , BlockException exception) {
        return new SentinelClientHttpResponse(JSON.toJSONString(R.fail(null, Constants.FLOW, "Sentinel flow block!")));
    }

    public static SentinelClientHttpResponse fallback(HttpRequest request, byte[] body, ClientHttpRequestExecution execution
            , BlockException exception) {
        return new SentinelClientHttpResponse(JSON.toJSONString(R.fail(null, Constants.FLOW, "Sentinel flow block!")));
    }
}
