{\rtf1\ansi\ansicpg1252\cocoartf2580
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\fnil\fcharset134 PingFangSC-Regular;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww27520\viewh16380\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 How kubernetes works:\
\
Kubernates has Pod(replica)
\f1 , Service(load balancer), Doloyment/Replica Set\
In the deloy.yaml file, we create 3 pods. the kubernetes create 3 containers in the virtual machine as the pods.\
The kubernetes has the service to expose it to the consumer. The service is the load balancer which takes the traffic from outside or another service object in kubernetes. Then the service routes the traffic to the pods. \
The kubernetes is responsible to talk to the cloud and request the load balancer to the existence with IP address which maps to the cloud load balancer then to the service.\
\
\
}