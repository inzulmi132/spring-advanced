package org.example.expert.domain.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j(topic = "AdminUseLogAop")
@Aspect
@Component
@RequiredArgsConstructor
public class AdminUseLogAop {

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    private void commentAdminController() {}
    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    private void userAdminController() {}

    @Before("commentAdminController() || userAdminController()")
    public void beforeAdvice(JoinPoint joinPoint) {
        String startTime = LocalDateTime.now().toString();

        for(Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest request) {
                Long userId = (Long) request.getAttribute("userId");
                String url = request.getRequestURI();
                log.info("요청한 사용자 ID : {}", userId);
                log.info("API 요청 시각: {}", startTime);
                log.info("API 요청 URL: {}", url);
            }
        }

        for(Object arg : joinPoint.getArgs()) {
            if(arg instanceof UserRoleChangeRequest request) {
                log.info("request body: {}", request);
            }
        }
    }
}
