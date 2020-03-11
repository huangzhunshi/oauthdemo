package so.dian.oauthdemo.oauthdemo.controller;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/yihui")
public class YihuiOauthContrller {

    String clientId = "clientId";
    String clientSecret = "clientSecret";
    String response_type = "code";
    String authorizationCode = "authorizationCode";
    String redirectUrlPage = "redirectUrlPage";

    @RequestMapping("/step1")
    public String step1() throws Exception {
        String url = "step2";
        // accessTokenRequest 是用来描述请求对象的，描述了请求地址，和请求参数
        OAuthClientRequest accessTokenRequest = OAuthClientRequest.authorizationLocation(url)
                .setResponseType(response_type).setClientId(clientId).setRedirectURI( "step3" ).
                        buildQueryMessage();

        return "redirect:" + accessTokenRequest.getLocationUri();
    }

    /**
     * esponse_type=code
     * &redirect_uri=step3
     * &client_id=clientId
     * @param model
     * @param request
     * @return
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @RequestMapping("/step2")
//    @ResponseBody
    public String step2(Model model, HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
//        return "ok";
        OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);


        String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
        String clientId =  oauthRequest.getClientId();
        String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
        //得到数据以后应该检查数据


        //把 state  写到一个 重定向的响应
        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
        builder.setCode(authorizationCode);
        OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

        return "redirect:" + response.getLocationUri();
    }

    /**
     * code=authorizationCode
     * @return
     */
    @RequestMapping("/step3")
    @ResponseBody
    public String step3(){

        return "ok";
    }

}
