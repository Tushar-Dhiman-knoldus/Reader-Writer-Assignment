# Reader-Writer-Assignment

### Problem Statement
Implement a writers-preference reader-writer lock.
In the reader-writer problem we consider that some threads may read and some may write. We want to prevent more than one thread modifying the shared resource simultaneously and allow for two or more readers to access the shared resource at the same time.
Make sure that if there is a writer thread waiting then it should get access to the resource as soon as possible i.e. as soon as it is available for someone to acquire it.

