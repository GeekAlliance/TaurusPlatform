package com.geekalliance.taurus.base.oauth.controller;


import com.geekalliance.taurus.base.api.auth.dto.LoginUserDTO;
import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.api.auth.enums.GrantTypeEnum;
import com.geekalliance.taurus.base.api.auth.vo.CurrUserVO;
import com.geekalliance.taurus.base.api.auth.vo.CustomTokenVO;
import com.geekalliance.taurus.base.oauth.config.ClientAuthenticationManager;
import com.geekalliance.taurus.base.oauth.config.OauthClientProperties;
import com.geekalliance.taurus.base.oauth.service.ResourceService;
import com.geekalliance.taurus.base.oauth.service.UsernameUserDetailService;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.core.holder.UserContextHolder;
import com.geekalliance.taurus.core.result.Result;
import com.geekalliance.taurus.core.utils.JwtUtils;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author maxuqiang
 * @description
 * @date 2019/12/18
 **/
@Api(value = "授权管理", tags = {"授权管理"})
@RestController
@RequestMapping("/oauth")
public class OauthController extends BaseController {
    @Resource
    private OauthClientProperties oauthClient;

    @Autowired
    private ClientAuthenticationManager authenticationManager;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UsernameUserDetailService usernameUserDetailService;

    @ApiOperation(value = "获取Token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "grant_type", value = "授权类型 用户名密码模式：password,刷新Token模式：refresh_token,客户端模式：client_credentials", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新Token"),
            @ApiImplicitParam(name = "client_id", value = "客户端编号", required = true),
            @ApiImplicitParam(name = "client_secret", value = "客户端授权码", required = true)
    })
    @RequestMapping(value = "/token", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<CustomTokenVO> token(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return custom(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    @ApiOperation(value = "刷新Token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refreshToken", value = "刷新Token", required = true),
    })
    @PostMapping(value = "/refreshToken")
    public Result<CustomTokenVO> refreshToken(@RequestParam String refreshToken) throws HttpRequestMethodNotSupportedException {
        Map<String, String> parameters = new HashMap<>(1);
        parameters.put(GrantTypeEnum.REFRESH_TOKEN.getCode(), refreshToken);
        return getTokenByType(GrantTypeEnum.REFRESH_TOKEN.getCode(), parameters);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result<CustomTokenVO> login(@RequestBody LoginUserDTO loginUser) throws HttpRequestMethodNotSupportedException {
        Map<String, String> parameters = beanGenerator.convert(loginUser, HashMap.class);
        return getTokenByType(GrantTypeEnum.PASSWORD.getCode(), parameters);
    }

    @ApiOperation(value = "获取当前登录用户")
    @GetMapping(value = "/currUser")
    public Result<CurrUserVO> currUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            BaseUser baseUser = usernameUserDetailService.getUser(authentication.getName());
            CurrUserVO userVO = beanGenerator.convert(baseUser, CurrUserVO.class);
            userVO.setRoles(new ArrayList<>());
            return Result.success(userVO);
        } catch (RuntimeException e) {
            return Result.success();
        }
    }

    @ApiOperation(value = "用户退出登录")
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String userId = UserContextHolder.getInstance().getTokenUser().getId();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.replace(JwtUtils.AUTHORIZATION_PREFIX, StringPool.EMPTY);
        consumerTokenServices.revokeToken(token);
        return Result.success();
    }

    private Result getTokenByType(String type, Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(oauthClient.getBrowserClientId(), oauthClient.getBrowserClientSecret());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.getOAuth2AuthenticationManager().authenticate(authRequest);
        } catch (Exception e) {
            throwApiException(SystemErrorType.INVALID_CLIENT);
        }
        parameters.put("client_id", oauthClient.getBrowserClientId());
        parameters.put("grant_type", type);
        parameters.put("client_secret", oauthClient.getBrowserClientSecret());
        return custom(tokenEndpoint.postAccessToken(authentication, parameters).getBody());
    }

    /**
     * 封装token
     *
     * @param accessToken token内容
     * @return 返回封装后的 Result
     */
    private Result custom(OAuth2AccessToken accessToken) {
        CustomTokenVO customToken = new CustomTokenVO();
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        customToken.setAccessToken(token.getValue());
        if (Objects.nonNull(token.getRefreshToken())) {
            customToken.setRefreshToken(token.getRefreshToken().getValue());
        }
        customToken.setExpiresIn(token.getExpiresIn());
        customToken.setTokenType(token.getTokenType());
        customToken.setInformation(token.getAdditionalInformation());
        return Result.success(customToken);
    }
}
