package io.redick.cloud.account.filter;

import io.redick.cloud.account.auth.impl.SignHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author Redick01
 */
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private SignHandler signHandler;

    public AuthFilter() {
        super(AuthFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> signHandler.sign(exchange, chain, config);
    }

    public static class Config {

        /**
         * 鉴权类型
         * jwt/sign
         */
        private String type = "jwt";

        /**
         * 签名算法类型
         * MD5withRSA,SHA1withRSA,SHA256withRSA,Hash
         */
        private String signType;

        private String publicKey;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
    }
}
