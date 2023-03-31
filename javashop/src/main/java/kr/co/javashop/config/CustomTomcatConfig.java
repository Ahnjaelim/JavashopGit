package kr.co.javashop.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomTomcatConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

  @Override
  public void customize(TomcatServletWebServerFactory factory) {
    TomcatConnectorCustomizer parseBodyMethodCustomizer = connector -> {
      connector.setParseBodyMethods("POST,PUT,DELETE");
    };
    factory.addConnectorCustomizers(parseBodyMethodCustomizer);
  }

}
