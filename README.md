# Task Critical Path Procedure
### Description, as given
The Critical Path Procedure (CPM) can be described as follows:
We assume there are a precedence constraints described by acyclic graph and there is only one final task( if there is no final task you may create a dummy task with zero time  to complete). 
The parameters ES(earliest start time ) , EF(earliest finish time), LS(latest start time), and LF(latest start time) of every task is computed by two passes through the graph. The first two parameters are computing during a forward pass from the initial tasks (those without any predecessor) to the final task as follows:

For each initial task ES=0 and EF= the time needed to complete the task
If the task A has P1 , P2,…Pk as immediate predecessor tasks ,  then ES of A = max(EF of P1  ,EF of P2,……,EF of Pk), and 
EF of A = ES + the time needed to complete the task A
The parameters LS and LF are computed during a backward pass from the final task to the initial tasks as follows:
LS and LF of the final task are exactly ES and EF of the final task, respectively.
If the task B has P1, P2 ,…., Pk as immediate successor tasks, then LF of B= min( LS of P1, LS of P2,…..LS of Pk),   and
 LS of B= LF -the time needed to complete the task B.

Slack is the amount of time that an activity can be delayed past its earliest start or earliest finish without delaying the project.
The parameter slack is defined for reach task as LS-ES. A task is critical if and only if its slack = 0


### Description, in english
This algorithm simulates the completion of a project by executing subtasks that each require a certain amount of time to finish.
These tasks are input into the algorithm, each with a name (any positive number), a time required to complete (given as a number), 
and the names of any prerequisite tasks (such that these tasks must be completed before this task begins).

The format for this input would be in a text file, with the following format:
- each line represents a task, and must begin with the string "task"
- following the "task" string, a set of at least two numbers should be input, with the following format:
  - the first number represents the task's name
  - the last number represents the task's time to complete
  - all numbers inbetween will be the name of all prerequisite tasks, if there are any.
  
Each task has several fields to help us determine an optimal schedule:
- Earliest Start Time (ES)
- Earliest Finish Time (EF)
- Lastest Start Time (LS)
- Lastest Finish Time (LF)
- Slack
- Critical Status

`Slack` is the amount of time a task can be delayed without affecting project finish time  
A task is considered `critical` only if its slack value = 0
  
  
Example input (testdata.txt):  
```
    task 0 2  
    task 1 0 4  
    task 2 0 5  
    task 3 0 9  
    task 4 1 3  
    task 5 1 2 3 2  
    task 6 4 5 1  
    task 7 5 10  
    task 8 3 11  
    task 9 6 7 6  
    task 10 8 9  
    task 11 8 8  
    task 12 9 10 11 7  
 ```
    
This input would represent the following task table:
|     Task    |     Prerequisites |     Time needed for the task    |
|-------------|-------------------|---------------------------------|
|     0       |     Null          |     2                           |
|     1       |     0             |     4                           |
|     2       |     0             |     5                           |
|     3       |     0             |     9                           |
|     4       |     1             |     3                           |
|     5       |     1,2,3         |     2                           |
|     6       |     4,5           |     1                           |
|     7       |     5             |     10                          |
|     8       |     3             |     11                          |
|     9       |     6,7           |     6                           |
|     10      |     8             |     9                           |
|     11      |     8             |     8                           |
|     12      |     9,10,11       |     7                           |

Using this data, the algorithm creates an acyclic graph (represented by an adjacency matrix) and uses topological sorting to order the tasks.
Once sorted, we run through the list of tasks and determing ES and EF values, then back down the list to determine LS and LF values, as well as slack.
Once complete, we can easily find the critical path and output the results.

Example Output:
```
Please enter file input
testdata.txt
Tasks inputted and have been determined to be processed in following order:
0 1 2 3 4 5 6 7 8 9 10 11 12 
----------------------------------------------------------------------------------
Node    ES      EF      LS      LF      Slack   Critical?
0       0       2       0       2       0       !      
1       2       6       9       13      7       
2       2       7       8       13      6       
3       2       11      2       11      0       !      
4       6       9       21      24      15      
5       11      13      13      15      2       
6       13      14      24      25      11      
7       13      23      15      25      2       
8       11      22      11      22      0       !      
9       23      29      25      31      2       
10      22      31      22      31      0       !      
11      22      30      23      31      1       
12      31      38      31      38      0       !      

Critical Path is as follows:
0 3 8 10 12 
```
