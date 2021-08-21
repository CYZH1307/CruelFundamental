How kubernetes works:

Kubernates has Pod(replica), Service(load balancer), Doloyment/Replica Set
In the deloy.yaml file, we create 3 pods. the kubernetes create 3 containers in the virtual machine as the pods.
The kubernetes has the service to expose it to the consumer. The service is the load balancer which takes the traffic from outside or another service object in kubernetes. Then the service routes the traffic to the pods. 
The kubernetes is responsible to talk to the cloud and request the load balancer to the existence with IP address which maps to the cloud load balancer then to the service.
