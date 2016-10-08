package org.scorpio.microscope.enginex.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class MLogConnectionProxy implements InvocationHandler {
    private Connection conn;

    public MLogConnectionProxy() {
    }

    public MLogConnectionProxy(Connection conn) {
        super();
        this.conn = conn;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("before save");
        Object o = method.invoke(conn, args);
        System.out.println("after save");
        return o;
    }

    public static Connection proxy(Connection real) {
        Class cls = real.getClass();
        return (Connection) Proxy.newProxyInstance(cls.getClassLoader(),
                cls.getInterfaces(), new MLogConnectionProxy(real));
    }
}
