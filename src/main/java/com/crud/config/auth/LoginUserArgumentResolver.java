package com.crud.config.auth;

import static com.crud.config.auth.jwt.JwtManager.AUTHORIZATION_KEY;
import static com.crud.config.auth.jwt.JwtManager.NAME_KEY;

import com.crud.config.auth.jwt.JwtParser;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 세션에 대한 처리를 여기서 해줄 수 있게 resolver 등록
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtParser jwtParser;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = String.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        String authorizeJwt = webRequest.getHeader(AUTHORIZATION_KEY);
        if (authorizeJwt == null)
            return null;
        String name;
        try {
            name = jwtParser.getNameFromToken(authorizeJwt);
        } catch (ExpiredJwtException e) {
            log.info("resolver token expire 발생");
            // filter 단에서 새로운 토큰을 발급해줬으니 다른 체크는 필요하지 않다고 생각
            // 이름만 파싱하기 때문에 추가적인 검증은 불필요하다고 생각한다.
            name = (String)e.getClaims().get(NAME_KEY);
        }
        return name;
    }
}
