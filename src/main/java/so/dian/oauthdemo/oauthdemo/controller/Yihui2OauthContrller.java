//package so.dian.oauthdemo.oauthdemo.controller;
//
//import org.apache.oltu.oauth2.as.issuer.MD5Generator;
//import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
//import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
//import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
//import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
//import org.apache.oltu.oauth2.as.response.OAuthASResponse;
//import org.apache.oltu.oauth2.client.OAuthClient;
//import org.apache.oltu.oauth2.client.URLConnectionClient;
//import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
//import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
//import org.apache.oltu.oauth2.common.OAuth;
//import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
//import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
//import org.apache.oltu.oauth2.common.message.OAuthResponse;
//import org.apache.oltu.oauth2.common.message.types.GrantType;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.UUID;
//
//@Controller
//@RequestMapping("yihui2")
//public class Yihui2OauthContrller {
//
//    private String clientId="1111";
//    private String clientSecret="clientSecret";
//
//    @RequestMapping("clientRequest")
//    public String clientRequest() throws OAuthSystemException {
//        OAuthClientRequest oAuthClientRequest=OAuthClientRequest.authorizationLocation("getcode")
//                .setRedirectURI("tiaozhuan")
//                .setResponseType(OAuth.OAUTH_CODE)
//                .setClientId(clientId)
//                .buildQueryMessage();
////        return oAuthClientRequest.getLocationUri();
//        return "redirect:" + oAuthClientRequest.getLocationUri();
//
//    }
//
//
//    /**
//     * 获取 code
//     * @param model
//     * @param request
//     * @return
//     * @throws OAuthProblemException
//     * @throws OAuthSystemException
//     */
//    @RequestMapping("getcode")
//    @ResponseBody
//    public String getcode(Model model, HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
//        OAuthAuthzRequest oAuthAuthzRequest=new OAuthAuthzRequest(request);
//        String clientId= oAuthAuthzRequest.getClientId();
//        String url=oAuthAuthzRequest.getRedirectURI();
//        String secret = oAuthAuthzRequest.getClientSecret();
//
//        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
//                OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
//
//        /**
//         * 授权码，程序自动生成
//         */
//        String authorizationCode= UUID.randomUUID().toString();
//
//        builder.setCode(authorizationCode);
//
//        OAuthResponse response = builder.location(url).buildQueryMessage();
//
//        /**
//         * 跳转第三步地址
//         */
//        return "redirect:" + response.getLocationUri();
//
//
//    }
//
//    /**
//     *   请求通过凭证
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/redirectUrlPage")
//    @ResponseBody
//    public Object redirectUrlPage(HttpServletRequest request) throws Exception {
//        String code = request.getParameter("code");
//
//        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//
//
//        OAuthClientRequest accessTokenRequest = OAuthClientRequest.tokenLocation("http://127.0.0.1:8080/yihui2/getToken")
//                .setGrantType(GrantType.AUTHORIZATION_CODE)
//                .setClientId(clientId)
//                .setClientSecret(clientSecret)
//                .setCode(code)
//                .setRedirectURI("redirectUrlPage")
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
//     * 获取token
//     * @return
//     */
//    @RequestMapping(value = "getToken")
//    @ResponseBody
//    public HttpEntity<String> getToken(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
//        OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
//
//        String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
//        String clientSecret = oauthRequest.getClientSecret();
//
//        OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());
//        String accessToken = oauthIssuer.accessToken();
//
//        //授权码不匹配，直接驳回
////        if (!"动态获取code,一般在缓存在redis".equals(authCode)) {
////            return null;
////        }
//
//        OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).setExpiresIn("120").buildJSONMessage();
//        return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
//
////        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
////
////        OAuthClientRequest accessTokenRequest = OAuthClientRequest.tokenLocation(getAccessTokenURL)
////                .setGrantType(GrantType.AUTHORIZATION_CODE)
////                .setClientId(clientId)
////                .setClientSecret(clientSecret)
////                .setCode(code)
//////                .setRedirectURI(redirectUrlPage)
////                .buildQueryMessage();
////        OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
////
////        //得到通过凭证和过期时间
////        String accessToken = oAuthResponse.getAccessToken();
////        Long expiresIn = oAuthResponse.getExpiresIn();
////
////        return "redirect:requestResourcePage?accessToken=" + accessToken;
//    }
//
//}
