//package com.iyysoft.msdp.gateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//
///**
// * <p class="detail">
// * 功能:网关解决跨域问题
// * </p>
// *
// * @author cm
// * @ClassName Cross origin filter.
// * @Version V1.0.
// * @date 2019.05.23 15:24:15
// */
//@Component
//public class CrossOriginFilter implements GlobalFilter, Ordered {
//
//	private static final String ALL = "*";
//	private static final String MAX_AGE = "18000L";
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		ServerHttpRequest request = exchange.getRequest();
//		ServerHttpResponse response = exchange.getResponse();
//		HttpHeaders headers = response.getHeaders();
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
//		headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
//		//logger.info("跨域解决添加headers:{}",headers);
//		if (request.getMethod() == HttpMethod.OPTIONS) {
//			response.setStatusCode(HttpStatus.OK);
//			return Mono.empty();
//		}
//		return chain.filter(exchange);
//	}
//
//	@Override
//	public int getOrder() {
//		return -300;
//	}
//}
