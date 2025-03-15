package com.idt.aio.resolver;

import com.idt.aio.annotation.UserId;
import com.idt.aio.exception.JwtExceptionCode;
import com.idt.aio.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class JwtTokenResolver implements HandlerMethodArgumentResolver {
    private final TokenProvider provider;
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && String.class.equals(
                parameter.getParameterType());
    }

    //Jwt 토큰을 추출해서 파라미터로 넘겨준다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        final String tokenWithBearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.isNull(tokenWithBearer) || !tokenWithBearer.startsWith(TOKEN_PREFIX)) {
            throw new IllegalArgumentException(
                    JwtExceptionCode.JWT_NO_AUTHORIZATION_HEADER.newInstance());
        }

        final String token = tokenWithBearer.substring(TOKEN_PREFIX.length());

        if (!provider.validateToken(token)) {
            throw new IllegalArgumentException(JwtExceptionCode.JWT_UNAUTHORIZED.newInstance("토큰 검증 실패"));
        }

        return provider.extractToValueFrom(token);
    }
}
