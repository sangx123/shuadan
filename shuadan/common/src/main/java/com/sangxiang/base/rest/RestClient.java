package com.sangxiang.base.rest;

import com.sangxiang.exception.ApiSignatureException;
import com.sangxiang.util.RestSignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

import static java.util.Collections.singletonList;


/**
 * 
 * @author fujg
 */
public class RestClient {
    private Logger                       logger       = LoggerFactory.getLogger(RestClient.class);

    private RestTemplate restTemplate = new RestTemplate();
    private ClientHttpRequestInterceptor signInterceptor;
    private ClientHttpRequestInterceptor headerWrapperInterceptor;
    private HttpHeaders httpHeaders  = new HttpHeaders();

    /**
     * 不使用自动签名
     */
    public RestClient() {
        reInitMessageConverter();
        headerWrapperInterceptor = new HttpRequestHeaderWrapperInterceptor();
        this.restTemplate.setInterceptors(singletonList(headerWrapperInterceptor));
    }

    /**
     * 初始化签名拦截器
     * 
     * @param appkey
     *            应用标识
     * @param privateKey
     *            应用私钥
     */
    public RestClient(String appkey, String privateKey) {
        reInitMessageConverter();
        signInterceptor = new HttpRequestSignInterceptor(appkey, privateKey);
        headerWrapperInterceptor = new HttpRequestHeaderWrapperInterceptor();
        this.restTemplate.setInterceptors(Arrays.asList(signInterceptor,headerWrapperInterceptor));
    }

    /**
     * 设置是否启用签名<br>
     * 如果签名过滤器没有初始化，则只会打出警告日志
     * 
     * @param needSign
     *            true:启动签名 false:关闭签名
     */
    public void setNeedSign(boolean needSign) {
        if (signInterceptor == null) {
            logger.warn("签名过滤器（HttpRequestSignInterceptor）没有初始化");
        } else {
            ((HttpRequestSignInterceptor) signInterceptor).setNeedSign(needSign);
        }
    }

    /**
     * 添加Http 头信息，同一http头可以可以有多个取值
     * 
     * @param name
     *            -自定义或标准的http header名称
     * @param value
     *            - 取值
     * @return
     */
    public RestClient addHeader(String name, String value) {

        httpHeaders.add(name, value);
        return this;
    }

    /**
     * 移除header
     */
    public void removeHeader(String name) {
        httpHeaders.remove(name);
    }

    /**
     * 设置Http 头信息，重名的http 头的取值将会被新值替换。
     * 
     * @param name
     *            -自定义或标准的http header名称
     * @param value
     *            - 取值
     * @return
     */
    public RestClient setHeader(String name, String value) {
        httpHeaders.set(name, value);

        return this;
    }

    /**
     * 清除自定义Http Header
     * 
     * @return
     */
    public RestClient clearHeaders() {
        this.httpHeaders.clear();
        return this;
    }

    private void reInitMessageConverter() {

        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = converterList.iterator();

        int index;
        for (; iterator.hasNext();) {

            HttpMessageConverter<?> item = iterator.next();
            if (item instanceof StringHttpMessageConverter) {

                index = converterList.indexOf(item);

                iterator.remove();

                HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("utf-8"));
                converterList.add(index, converter);
                break;
            }
        }
    }

    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        restTemplate.setMessageConverters(messageConverters);
    }

    public List<HttpMessageConverter<?>> getMessageConverters() {
        return restTemplate.getMessageConverters();
    }

    public void setErrorHandler(ResponseErrorHandler errorHandler) {
        restTemplate.setErrorHandler(errorHandler);
    }

    public ResponseErrorHandler getErrorHandler() {
        return restTemplate.getErrorHandler();
    }

    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) {
        return restTemplate.getForObject(url, responseType, urlVariables);
    }

    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) {

        return restTemplate.getForObject(url, responseType, urlVariables);
    }

    public <T> T getForObject(URI url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... urlVariables) {
        return restTemplate.getForEntity(url, responseType, urlVariables);
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables) {
        return restTemplate.getForEntity(url, responseType, urlVariables);
    }

    public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) {
        return restTemplate.getForEntity(url, responseType);
    }

    public HttpHeaders headForHeaders(String url, Object... urlVariables) {
        return restTemplate.headForHeaders(url, urlVariables);
    }

    public HttpHeaders headForHeaders(String url, Map<String, ?> urlVariables) {
        return restTemplate.headForHeaders(url, urlVariables);
    }

    public HttpHeaders headForHeaders(URI url) {
        return restTemplate.headForHeaders(url);
    }

    public URI postForLocation(String url, Object request, Object... urlVariables) {
        return restTemplate.postForLocation(url, request, urlVariables);
    }

    public URI postForLocation(String url, Object request, Map<String, ?> urlVariables) {
        return restTemplate.postForLocation(url, request, urlVariables);
    }

    public URI postForLocation(URI url, Object request) {
        return restTemplate.postForLocation(url, request);
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) {
        return restTemplate.postForObject(url, request, responseType, uriVariables);
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.postForObject(url, request, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
                                               Object... uriVariables) {
        return restTemplate.postForEntity(url, request, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
                                               Map<String, ?> uriVariables) {
        return restTemplate.postForEntity(url, request, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType) {
        return restTemplate.postForEntity(url, request, responseType);
    }

    public void put(String url, Object request, Object... urlVariables) {
        restTemplate.put(url, request, urlVariables);
    }

    public void put(String url, Object request, Map<String, ?> urlVariables) {
        restTemplate.put(url, request, urlVariables);
    }

    public void put(URI url, Object request) {
        restTemplate.put(url, request);
    }

    public void delete(String url, Object... urlVariables) {
        restTemplate.delete(url, urlVariables);
    }

    public void delete(String url, Map<String, ?> urlVariables) {
        restTemplate.delete(url, urlVariables);
    }

    public void delete(URI url) {
        restTemplate.delete(url);
    }

    public Set<HttpMethod> optionsForAllow(String url, Object... urlVariables) {
        return restTemplate.optionsForAllow(url, urlVariables);
    }

    public Set<HttpMethod> optionsForAllow(String url, Map<String, ?> urlVariables) {
        return restTemplate.optionsForAllow(url, urlVariables);
    }

    public Set<HttpMethod> optionsForAllow(URI url) {
        return restTemplate.optionsForAllow(url);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          Class<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          Class<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity,
                                          ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(RequestEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.exchange(requestEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(RequestEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(requestEntity, responseType);
    }

    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
                         ResponseExtractor<T> responseExtractor, Object... urlVariables) {
        return restTemplate.execute(url, method, requestCallback, responseExtractor, urlVariables);
    }

    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
                         ResponseExtractor<T> responseExtractor, Map<String, ?> urlVariables) {
        return restTemplate.execute(url, method, requestCallback, responseExtractor, urlVariables);
    }

    public <T> T execute(URI url, HttpMethod method, RequestCallback requestCallback,
                         ResponseExtractor<T> responseExtractor) {
        return restTemplate.execute(url, method, requestCallback, responseExtractor);
    }

    public void setInterceptors(List<ClientHttpRequestInterceptor> interceptors) {
        restTemplate.setInterceptors(interceptors);
    }

    public static class HttpHeaderNames {
        public static final String APP_KEY = "Appkey";
        public static final String SIGN    = "sign";
    }

    private class HttpRequestSignInterceptor implements ClientHttpRequestInterceptor {
        private String  appKey;
        private String  privateKey;
        private boolean needSign = true;

        public HttpRequestSignInterceptor(String appKey, String privateKey) {
            this.appKey = appKey;
            this.privateKey = privateKey;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {

            if (isNeedSign()) {
                String url = request.getURI().getPath(); // 使用相对路径进行签名/验签，防止由于代理使请求地址改变需使验签失败
                HttpHeaders headers = request.getHeaders();
                String query = request.getURI().getQuery();

                Map<String, String> queryMap = null;
                if(request.getMethod().equals(HttpMethod.GET)){
                    queryMap = queryToMap(query);
                }else{
                    queryMap = new HashMap<String,String>();
                }

                sign(url, queryMap, body, headers);
            }

            return execution.execute(request, body);
        }

        private void sign(String url, Map<String, String> queryMap, byte[] body, HttpHeaders headers) {
            if (StringUtils.isEmpty(this.appKey)) {
                throw new ApiSignatureException(HttpStatus.BAD_REQUEST, "签名失败，Appkey不能为空");
            }
            if (StringUtils.isEmpty(this.privateKey)) {
                throw new ApiSignatureException(HttpStatus.BAD_REQUEST, "签名失败，私钥不能为空");
            }

            MediaType contentType = headers.getContentType();
            String sign = null;
            if (contentType != null && contentType.includes(MediaType.MULTIPART_FORM_DATA)) { // 带文件的传输不签名请求体
                sign = RestSignUtil.sign(appKey, url, queryMap, new byte[0], privateKey);
            } else {
                sign = RestSignUtil.sign(appKey, url, queryMap, body, privateKey);
            }

            headers.set(RestClient.HttpHeaderNames.APP_KEY, appKey);
            headers.set(RestClient.HttpHeaderNames.SIGN, sign);
        }

        public boolean isNeedSign() {
            return needSign;
        }

        public void setNeedSign(boolean needSign) {
            this.needSign = needSign;
        }

        private Map<String, String> queryToMap(String query) {
            Map<String, String> queryMap = new HashMap<String, String>();

            if (StringUtils.isEmpty(query)) {
                return queryMap;
            }

            String[] split = query.split("&");
            for (String str : split) {
                String[] split2 = str.split("=");
                if(split2.length == 1){
                    queryMap.put(split2[0], "");
                }else{
                    queryMap.put(split2[0], split2[1]);
                }
            }

            return queryMap;
        }
    }
    
    private class HttpRequestHeaderWrapperInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
            HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
            requestWrapper.getHeaders().putAll(httpHeaders);
            return execution.execute(request, body);
        }
        
    }
}
