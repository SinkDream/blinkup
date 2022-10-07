package cn.blinkup.orm.proxy;

/**
 * @author joe
 * 代理接口
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain);
}
