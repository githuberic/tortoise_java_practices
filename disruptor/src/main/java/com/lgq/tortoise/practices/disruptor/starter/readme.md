# Disruptor

在Disruptor中有几个比较关键的:

- ThreadFactory：这是一个线程工厂，用于我们Disruptor中生产、消费的时候需要的线程。
- EventFactory：事件工厂，用于产生我们队列元素的工厂。在Disruptor中，他会在初始化的时候直接填充满RingBuffer，一次到位。
- EventHandler：用于处理Event的handler，这里一个EventHandler可以看做是一个消费者，但是多个EventHandler他们都是独立消费的队列。
- WorkHandler:也是用于处理Event的handler，和上面区别在于，多个消费者都是共享同一个队列。
- WaitStrategy：等待策略，在Disruptor中有多种策略，来决定消费者在消费时，如果没有数据采取的策略是什么？下面简单列举一下Disruptor中的部分策略
  - BlockingWaitStrategy：通过线程阻塞的方式，等待生产者唤醒，被唤醒后，再循环检查依赖的sequence是否已经消费。
  - BusySpinWaitStrategy：线程一直自旋等待，可能比较耗cpu
  - YieldingWaitStrategy：尝试100次，然后Thread.yield()让出cpu

https://www.jianshu.com/p/bad7b4b44e48

https://blog.csdn.net/qiyeliuli/article/details/102560504
