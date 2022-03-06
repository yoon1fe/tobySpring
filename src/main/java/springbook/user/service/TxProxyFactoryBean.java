package springbook.user.service;

import lombok.Setter;
import org.springframework.aop.Pointcut;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

@Setter
public class TxProxyFactoryBean implements FactoryBean<Object> {

    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    Class<?> serviceInterface;      // 다이나믹 프록시 생성할 때 필요

    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = TransactionHandler.builder()
                .target(target)
                .transactionManager(transactionManager)
                .pattern(pattern)
                .build();

        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{serviceInterface}, txHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public boolean isSingleton() {
        return false;
    }
}
