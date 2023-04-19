package io.redick.cloud.account.filter;

import io.redick.cloud.account.utils.ServletUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 基于URL的黑名单过滤器
 *
 * @author Redick01
 */
@Component
public class BlackListUrlFilter extends AbstractGatewayFilterFactory<BlackListUrlFilter.Config> {

    public BlackListUrlFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();
            if (config.match(url)) {
                return ServletUtils.webFluxResponseWriter(exchange.getResponse(),
                        "This url:" + url + " in url black list!");
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

        private List<String> blackListUrl;

        private final List<Pattern> blackListPattern = new ArrayList<>();

        private boolean match(String url) {
            return !blackListPattern.isEmpty()
                    && blackListPattern.stream().anyMatch(pattern -> pattern.matcher(url).find());
        }

        public List<String> getBlackListUrl() {
            return blackListUrl;
        }

        public void setBlackListUrl(List<String> blackListUrl) {
            this.blackListUrl = blackListUrl;
            this.blackListPattern.clear();
            this.blackListUrl.forEach(b -> this.blackListPattern.add(Pattern.compile(b.replaceAll("\\*\\*", "(.*?)"), Pattern.CASE_INSENSITIVE)));
        }
    }
}
