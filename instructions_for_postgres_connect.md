

```
postgres_pod=$(oc get pods  -lname=postgresql | grep postgresql| awk '{ print $1}')
oc rsync $postgres_pod:/var/lib/pgsql/data/userdata/postgresql.conf .
```

Open it and modify 

(ideally do a oc write of the file then mount on boot)
```
wal_level=logical
max_wal_senders=1
max_replication_slots=1

```

There’s also an openshift related one..maybe that’s the one you change..but if db is not ephemeral then you can restart


Add to config map then mount on startup
```
oc create configmap db-config-postgresql-conf --from-file=postgresql.conf
```

postgres deployment config
```yaml
    spec:
      volumes:
        - name: postgresql-data
          persistentVolumeClaim:
            claimName: postgresql
        - name: db-config-postgresql-conf
          configMap:
            name: db-config-postgresql-conf
...
...
...
          volumeMounts:
            - name: postgresql-data
              mountPath: /var/lib/pgsql/data
            - name: db-config-postgresql-conf
              mountPath: /var/lib/pgsql/data/userdata/postgresql.conf
```
