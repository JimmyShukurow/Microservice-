package io.smartir.ApiGateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Value("${spring.auth.service.uri}")
    private String baseUri;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info(exchange.getRequest().getPath().toString());
        if (permitAll(exchange)) return chain.filter(exchange);

        String token = null;
        try {
            var header = exchange.getRequest().getHeaders().get("Authorization");
            token = header.get(0).split(" ")[1];
        } catch (NullPointerException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        var user =  getUser(token);

        if (permitAdmin(exchange, user)) return chain.filter(exchange);
        if (permitWithAnyRole(exchange, user)) return chain.filter(exchange);

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    public ApiResponse getUser(String token) {
        try {
            URI uri = new URI(baseUri + "/user/get-user?token=" + token);
            var restTemplate = new RestTemplate();
            var response = restTemplate.getForObject(uri, ApiResponse.class);
            log.info("user is => "+response);
            return response;
        } catch (URISyntaxException | HttpClientErrorException e) {
            log.info("URI syntax error!");
            throw new WrongTokenException();
        }
    }

    private static boolean matchesEndpoint(String inputString, String endpointPattern) {
        Pattern pattern = Pattern.compile(endpointPattern);

        Matcher matcher = pattern.matcher(inputString);

        return matcher.matches();
    }

    public boolean permitAll(ServerWebExchange exchange) {
        return exchange.getRequest().getPath().toString().equals("/user/register") ||
                exchange.getRequest().getPath().toString().equals("/user/login") ||
                exchange.getRequest().getPath().toString().equals("/user/validate-JWT") ||
                exchange.getRequest().getPath().toString().equals("/article/get-all") ||
                exchange.getRequest().getPath().toString().equals("/tag/get-all-tags") ||
                exchange.getRequest().getPath().toString().equals("/mail/send-email") ||
                exchange.getRequest().getPath().toString().equals("/mail/add-user") ||
                exchange.getRequest().getPath().toString().endsWith("/status") ||
                exchange.getRequest().getPath().toString().startsWith("/BannerImage/") ||
                exchange.getRequest().getPath().toString().startsWith("/Image/");
    }

    public boolean permitAdmin(ServerWebExchange exchange, ApiResponse user) {
        return (user.getRoles().stream().anyMatch(role -> role.equals(Roles.ADMIN.toString()))) &&
                (
                        matchesEndpoint(exchange.getRequest().getPath().toString(), "^/user/[0-9]+/assign-role/[a-zA-Z]+$") ||
                                exchange.getRequest().getMethod().equals(HttpMethod.DELETE) ||
                                exchange.getRequest().getPath().toString().equals("/tag/get-all") ||
                                exchange.getRequest().getPath().toString().equals("/user/get-all")
                );
    }

    public boolean permitWithAnyRole(ServerWebExchange exchange, ApiResponse user) {
        return !user.getRoles().isEmpty() && !exchange.getRequest().getMethod().equals(HttpMethod.DELETE) && (
                exchange.getRequest().getPath().toString().equals("/user/logout") ||
                exchange.getRequest().getPath().toString().contains("/article/") ||
                exchange.getRequest().getPath().toString().contains("/tag/") ||
                exchange.getRequest().getPath().toString().contains("/type/") ||
                exchange.getRequest().getPath().toString().contains("/BannerImage/") ||
                exchange.getRequest().getPath().toString().contains("/Image/")
        );
    }


}
