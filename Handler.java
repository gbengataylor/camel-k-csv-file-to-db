// camel-k: language=java
// trait=quarkus.enabled=true



//kamel run --resource data/resource.txt Handler.java --dev


import org.apache.camel.builder.RouteBuilder;

public class Handler extends RouteBuilder {
  @Override
  public void configure() throws Exception {

      // Write your routes here, for example:
      // from("timer:java?period=1000")
      //   .routeId("java")
      //   .setBody()
      //     .simple("Hello Camel K from ${routeId}")
      //   .to("log:info");

      from("timer:resources?repeatCount=1")
      .routeId("resources")
      .setBody()
          .simple("resource:classpath:resource.txt")
          .process(exchange -> {
            String input = (String) exchange.getIn().getBody();
            //System.out.println("Input to be persisted : " + input);
            String insertQuery = "INSERT INTO credit_card_transactions values ( " + input+")";
          //  System.out.println("Insert Query is : " + insertQuery);
            exchange.getIn().setBody(insertQuery);
        })
        .log("${body}");

  }
}
