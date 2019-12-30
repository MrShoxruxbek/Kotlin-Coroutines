# Kotlin-Coroutines
Create 1000 threads and classic thread. Adam's law.

The execution time of the whole task before the improvement of the resources of the system is denoted as T {\displaystyle T} T. It includes the execution time of the part that would not benefit from the improvement of the resources and the execution time of the one that would benefit from it. The fraction of the execution time of the task that would benefit from the improvement of the resources is denoted by p {\displaystyle p} p. The one concerning the part that would not benefit from it is therefore 1 − p {\displaystyle 1-p} {\displaystyle 1-p}. Then:

    T = ( 1 − p ) T + p T . {\displaystyle T=(1-p)T+pT.} T=(1-p)T+pT.

It is the execution of the part that benefits from the improvement of the resources that is accelerated by the factor s {\displaystyle s} s after the improvement of the resources. Consequently, the execution time of the part that does not benefit from it remains the same, while the part that benefits from it becomes:

    p s T . {\displaystyle {\frac {p}{s}}T.} {\frac {p}{s}}T.

The theoretical execution time T ( s ) {\displaystyle T(s)} {\displaystyle T(s)} of the whole task after the improvement of the resources is then:

    T ( s ) = ( 1 − p ) T + p s T . {\displaystyle T(s)=(1-p)T+{\frac {p}{s}}T.} {\displaystyle T(s)=(1-p)T+{\frac {p}{s}}T.}

Amdahl's law gives the theoretical speedup in latency of the execution of the whole task at fixed workload W {\displaystyle W} W, which yields

    S latency ( s ) = T W T ( s ) W = T T ( s ) = 1 1 − p + p s . {\displaystyle S_{\text{latency}}(s)={\frac {TW}{T(s)W}}={\frac {T}{T(s)}}={\frac {1}{1-p+{\frac {p}{s}}}}.} {\displaystyle S_{\text{latency}}(s)={\frac {TW}{T(s)W}}={\frac {T}{T(s)}}={\frac {1}{1-p+{\frac {p}{s}}}}.}

