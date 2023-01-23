package com.crud.config.auth;

import static com.crud.config.auth.jwt.JwtManager.AUTHORIZATION_KEY;

import com.crud.config.auth.jwt.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 세션에 대한 처리를 여기서 해줄 수 있게 resolver 등록
 */
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
        if (authorizeJwt == null) {
            return null;
        }
        return jwtParser.getNameFromToken(authorizeJwt);
    }
}
