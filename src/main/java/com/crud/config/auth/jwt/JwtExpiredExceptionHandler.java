package com.crud.config.auth.jwt;

import static com.crud.config.auth.jwt.JwtManager.REFRESH_KEY;

import com.crud.domain.token.AuthToken;
import com.crud.service.token.AuthTokenService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 기간이 만료된 토큰에 대한 검증을 하는 핸들러
 * 요청에 사용된 access 토큰과 일치하는 refresh 토큰이 있다면 단순 만료로 보고, access 토큰을 갱신해서 준다.
 * 일치하는 refresh 토큰이 없다면, 로그아웃한 사용자의 토큰 정보를 사용한 것으로 간주. *
 */
@RequiredArgsConstructor
@Component
public class JwtExpiredExceptionHandler {

    private final JwtParser jwtParser;
    private final AuthTokenService authTokenService;

    public AuthToken doHandle(HttpServletRequest request, String accessToken) {
        String refreshToken = paresRefreshToken(request);
        return tryAccessTokenUpdate(accessToken, refreshToken);
    }

    private String paresRefreshToken(HttpServletRequest request) {
        String refreshJWT = request.getHeader(REFRESH_KEY);
        return jwtParser.getRefreshTokenFromToken(refreshJWT);
    }

    private AuthToken tryAccessTokenUpdate(String accessToken, String refreshToken) {
        AuthToken authToken = authTokenService.getAuthToken(accessToken);
        if (refreshToken.equals(authToken.getRefreshToken())) {
            System.out.println("current : " + authToken.getAccessToken());
            authToken.updateAccessToken();
            authTokenService.save(authToken);
            System.out.println("new : " + authToken.getAccessToken());
            return authToken;
        }
        return null;
    }
}
