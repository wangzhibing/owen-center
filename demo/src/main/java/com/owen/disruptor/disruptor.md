```
1、无锁
2、所有访问者都有记录自己的序号的实现方式，允许多个生产者与多个消费者共享的数据结构。
3、环形队列ringbuffer
4、用数组实现，解决了链表节点分散，不利于cache预读问题
```