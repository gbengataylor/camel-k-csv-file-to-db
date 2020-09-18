to run

```
kamel run DataIntegrationRoute.java  --dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0 --dependency=mvn:org.postgresql:postgresql:9.4.1212 --name credit-card-generator --dev
```

to scale down deployed kamel Integration deployment

```
oc scale it credit-card-generator --replicas 0
```