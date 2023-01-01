package com.crud.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 매개변수 위치에 선언돼
 * 세션의 인자값을 바로 받아올 수 있게 함
 * @Target(ElementType.PARAMETER) : 매개변수에 선언돼야 함
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
