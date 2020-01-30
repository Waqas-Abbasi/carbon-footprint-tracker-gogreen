//package application.security;
//
//import org.apache.catalina.connector.Connector;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TomcatHttpAndHttpsConfig {
//
//  @Value("${http.port}")
//  private int httpPort;
//
//  @Bean
//  public ServletWebServerFactory servletContainer() {
//    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//    };
//    tomcat.addAdditionalTomcatConnectors(httpConnector());
//    return tomcat;
//  }
//
//  private Connector httpConnector() {
//    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//    connector.setScheme("http");
//    connector.setPort(httpPort);
//    return connector;
//  }
//}
