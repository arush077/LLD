# Load Balancer (Round Robin)

## Entity Diagram

```text
LoadBalancerService
├── ServerPool
│   ├── Server S1
│   ├── Server S2
│   └── Server S3
|
└── LoadBalancingStrategy
    └── RoundRobin
        └── prevIndex
```

## Request Flow

```text
Client → Request → LoadBalancerService → LoadBalancingStrategy → RoundRobin

```

Example:

```text
Request1 → S1
Request2 → S2
Request3 → S3
Request4 → S1
```


LoadBalancingStrategy has a routeRequest(serverList ,req)