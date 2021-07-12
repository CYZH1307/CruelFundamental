deadlock has following characteristics.


Mutual Exclusion
Hold and Wait
No preemption
Circular wait
Deadlock Prevention
We can prevent Deadlock by eliminating any of the above four conditions.



Eliminate Mutual Exclusion
It is not possible to dis-satisfy the mutual exclusion because some resources, such as the tape drive and printer, are inherently non-shareable.

Eliminate Hold and wait

Allocate all required resources to the process before the start of its execution, this way hold and wait condition is eliminated but it will lead to low device utilization. for example, if a process requires printer at a later time and we have allocated printer before the start of its execution printer will remain blocked till it has completed its execution.

The process will make a new request for resources after releasing the current set of resources. This solution may lead to starvation.

Eliminate No Preemption
Preempt resources from the process when resources required by other high priority processes.

Eliminate Circular Wait
Each resource will be assigned with a numerical number. A process can request the resources increasing/decreasing. order of numbering.
For Example, if P1 process is allocated R5 resources, now next time if P1 ask for R4, R3 lesser than R5 such request will not be granted, only request for resources more than R5 will be granted.

Deadlock Avoidance
Deadlock avoidance can be done with Banker’s Algorithm.

Banker’s Algorithm
Bankers’s Algorithm is resource allocation and deadlock avoidance algorithm which test all the request made by processes for resources, it checks for the safe state, if after granting request system remains in the safe state it allows the request and if there is no safe state it doesn’t allow the request made by the process.

Inputs to Banker’s Algorithm:


Max need of resources by each process.
Currently, allocated resources by each process.
Max free available resources in the system.
The request will only be granted under the below condition:

If the request made by the process is less than equal to max need to that process.
If the request made by the process is less than equal to the freely available resource in the system.
