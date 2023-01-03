# Synchronization
## Restaurant-Multithreading
### Description
Let's try to write an implementation of a small restaurant where there are several waiters and sometimes visitors come in. Sometimes visitors come to the restaurant, 
choose a dish, eat and leave.

#### Program operation
1. Creating a waiter flow and a visitor flow
2. A visitor enters a restaurant and, after some time, comes up with what to order
3. The waiter takes the order and after a while brings it
4. The visitor thread completes its work with a delay, thus emulating a meal.

#### Program requirements
1. Each key stage should be accompanied by the output of the current status to the console, for example: The waiter1 carried the order to the Visitor 2
2. All delays (cooking time, visitors' call timeout) must be registered in constants (no "Magic numbers")
3. The restaurant must serve 5 visitors and close

<a href="https://github.com/netology-code/jd-homeworks/blob/master/synchronization/task3/README.md">(RUS version of description)</a>
