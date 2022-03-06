package springbook.learningtest.junit;

import org.junit.Test;
import springbook.user.hello.Hello;
import springbook.user.hello.HelloTarget;
import springbook.user.hello.HelloUppercase;
import springbook.user.hello.UppercaseHandler;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HelloTest {

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();    // 타겟은 인터페이스를 통해 접근

        assertThat(hello.sayHello("Toby"), is("Hello Toby"));
        assertThat(hello.sayHi("Toby"), is("Hi Toby"));
        assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));

//        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget()));
        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));

    }
}
