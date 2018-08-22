package cn.fintecher.simple.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ChenChang on 2017/10/10.
 */
public class AccessFilter extends OncePerRequestFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
//    @Autowired
//    private OperatorService operatorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
//        if (StringUtils.contains(request.getRequestURL().toString(), "api/auth/login")
//                || StringUtils.contains(request.getRequestURL().toString(), "swagger")
//                || StringUtils.contains(request.getRequestURL().toString(), "api-docs")
//                || StringUtils.contains(request.getRequestURL().toString(), "QRCode")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String accessToken = request.getHeader("X-OperatorToken");
//
//        if (StringUtils.isBlank(accessToken)) {
//            log.debug("access token is empty");
//            response.setStatus(403);
//            return;
//        }
//        Operator operator = operatorService.findOperatorByToken(accessToken);
//        if (Objects.isNull(operator)) {
//            log.debug("access token is wrong");
//            response.setStatus(403);
//            return;
//        }

        log.debug("access token ok");
        filterChain.doFilter(request, response);

    }
}
