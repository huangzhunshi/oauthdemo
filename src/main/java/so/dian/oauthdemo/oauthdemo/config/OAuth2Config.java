package so.dian.oauthdemo.oauthdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory connectionFactory;






    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 定义哪些客户端可以注册到了服务
        clients.inMemory()
                .withClient("clientId")
                .secret("{noop}clientSecret")
                // 支持的授权模式 密码模式和客户端凭证
                .authorizedGrantTypes("refresh_token", "password", "client_credentials","authorization_code")
//                .redirectUris("http://www.baidu.com")
                // 定义访问作用域，也就是当用户使用某一个scope授权之后，可以根据不同的scope封装不同的user信息，比如webclient会封装角色，mobileclient封装角色和资源api，由开发人员定义即可
                .scopes("webclient", "mobileclient");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 使用默认的验证管理器和用户信息服务
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                ;
    }


//    @Override
//    public void configure(
//            AuthorizationServerEndpointsConfigurer endpoints)
//            throws Exception {
//        endpoints
//                .authenticationManager(authenticationManager)
//                .tokenStore(tokenStore());
//    }

    @Bean
    public TokenStore tokenStore() {

        RedisTokenStore redis = new RedisTokenStore(connectionFactory);
        return redis;
    }
}
