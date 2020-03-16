//package so.dian.oauthdemo.oauthdemo.controller;
//
//
//import org.apache.oltu.oauth2.as.issuer.MD5Generator;
//import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
//import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
//import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
//import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
//import org.apache.oltu.oauth2.as.response.OAuthASResponse;
//import org.apache.oltu.oauth2.client.OAuthClient;
//import org.apache.oltu.oauth2.client.URLConnectionClient;
//import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
//import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
//import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
//import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
//import org.apache.oltu.oauth2.common.OAuth;
//import org.apache.oltu.oauth2.common.message.OAuthResponse;
//import org.apache.oltu.oauth2.common.message.types.GrantType;
//import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
//import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//@RequestMapping("/oauth")
//@Controller
//public class OauthController {
//    String clientId = "clientId";
//    String clientSecret = "clientSecret";
//    String response_type = "code";
//    String authorizationCode = "authorizationCode";
//    String redirectUrlPage = "redirectUrlPage";
//
//
//
//    /**
//     * 请求通过凭证地址
//     */
//    String getAccessTokenURL = "http://localhost:8080/oauth/getAccessToken";
//    /**
//     * 请求资源地址
//     */
//    String userInfoUrl = "http://localhost:8080/oauth/getResource";
//
//
//    /**
//     * 客户端 第一次发起请求，为了获取code.....
//     *   重定向到请求 授权码的url
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/redirectToRequestAuthorizationCodeURL")
//    public String redirectToRequestAuthorizationCodeURL() throws Exception {
//        String url = "getAuthorizationCode";
//        // accessTokenRequest 是用来描述请求对象的，描述了请求地址，和请求参数
//        OAuthClientRequest accessTokenRequest = OAuthClientRequest.authorizationLocation(url)
//                .setResponseType(response_type).setClientId(clientId).setRedirectURI( redirectUrlPage ).
//                        buildQueryMessage();
//
//        return "redirect:" + accessTokenRequest.getLocationUri();
//    }
//
//
//
//
////    @RequestMapping("/token")
////    @ResponseBody
////    public Object token(){
////        return "token";
////    }
//
//
//
//
//    /**
//     * 认证 服务端 处理 请求，返回code给客户端
//     *   返回授权码
//     * @param model
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/getAuthorizationCode")
//    public Object getAuthorizationCode(Model model, HttpServletRequest request) throws Exception {
//        OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
//
//
//        String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
//        String clientId =  oauthRequest.getClientId();
//        String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
//        //得到数据以后应该检查数据
//
//
//        //把 state  写到一个 重定向的响应
//        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
//                OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
//        builder.setCode(authorizationCode);
//        OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
//
//        return "redirect:" + response.getLocationUri();
//
//    }
//
//
//    /**
//     *  客户端向认证服务端发起请求， 根据 code 获取 token
//     *   请求通过凭证
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/redirectUrlPage")
//    public Object redirectUrlPage(HttpServletRequest request) throws Exception {
//        String code = request.getParameter("code");
//
//        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//
//
//        OAuthClientRequest accessTokenRequest = OAuthClientRequest.tokenLocation(getAccessTokenURL)
//                .setGrantType(GrantType.AUTHORIZATION_CODE)
//                .setClientId(clientId)
//                .setClientSecret(clientSecret)
//                .setCode(code)
//                .setRedirectURI(redirectUrlPage)
//                .buildQueryMessage();
//
//        OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
//
//        //得到通过凭证和过期时间
//        String accessToken = oAuthResponse.getAccessToken();
//        Long expiresIn = oAuthResponse.getExpiresIn();
//
//        return "redirect:requestResourcePage?accessToken=" + accessToken;
//
//    }
//
//    /**
//     * 服务端接收 客户端 请求，返回 token
//     *   返回通过凭证
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/getAccessToken", method = RequestMethod.POST)
//    public HttpEntity<String> getAccessToken(HttpServletRequest request) throws Exception {
//        // 构建OAuth请求
//        OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
//
//        String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
//        String clientSecret = oauthRequest.getClientSecret();
//        //应该验证授权码
//
//
//
//        // 生成Access Token
//        OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());
//        String accessToken = oauthIssuer.accessToken();
//
//        OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).setExpiresIn("120").buildJSONMessage();
//        return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
//    }
//
//    /**
//     * 客户端 向 服务端 发起 请求，根据tocken 获取资源
//     * 请求资源
//     * @param accessToken
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/requestResourcePage")
//    @ResponseBody
//    public String requestResourcePage(String accessToken) throws Exception {
//        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//
//        OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(userInfoUrl).setAccessToken(accessToken).buildQueryMessage();
//
//        OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET,OAuthResourceResponse.class);
//        String resource = resourceResponse.getBody();
//
//        return resource;
//    }
//
//
//    /**
//     * 服务端 返回 资源，根据token 返回响应资源
//     *  返回资源
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/getResource")
//    public HttpEntity<String> getResource(HttpServletRequest request) throws Exception {
//        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
//        String accessToken = oauthRequest.getAccessToken();
//        //这里应该验证accessToken
//
//        return new ResponseEntity<String>("我就是资源", HttpStatus.OK);
//    }
//}
