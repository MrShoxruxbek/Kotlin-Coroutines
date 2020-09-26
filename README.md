![](https://travis-ci.org/mr-shoxruxbek/Kotlin-Coroutines.svg?branch=master)
# Kotlin-Coroutines
Create 1000 threads and classic thread. Adam's law.

The execution time of the whole task before the improvement of the resources of the system is denoted as T {\displaystyle T} T. It includes the execution time of the part that would not benefit from the improvement of the resources and the execution time of the one that would benefit from it. The fraction of the execution time of the task that would benefit from the improvement of the resources is denoted by p {\displaystyle p} p. The one concerning the part that would not benefit from it is therefore 1 − p {\displaystyle 1-p} {\displaystyle 1-p}. Then:

    T = ( 1 − p ) T + p T . {\displaystyle T=(1-p)T+pT.} T=(1-p)T+pT.

Amdahl's law gives the theorety that the most time consuming job must not be longer than hole execution.

![](/app/photos/Coroutin3.png)

Here we can see Difference between async scope, scope, classic thread.
    #The calculation done with sorting algorithm with 1.000 threads filled with random arrays

![](/app/photos/Coroutine1.png)
