redis实现发布/订阅模式：
jedis.subscribe()方法会使线程进入阻塞状态，等待被订阅的频道发布新消息
当有新消息发布时，该线程会回调onMessage()方法
所以处理订阅时需要启动线程，否则线程阻塞后，程序无法继续执行。

程序输出：
Thread-0:s1订阅了频道channel1
Thread-1:s1订阅了频道channel2
Thread-2:s2订阅了频道channel1
Thread-0:s1收到了来自频道channel1的消息：aaaaaaaaaaaaa
Thread-2:s2收到了来自频道channel1的消息：aaaaaaaaaaaaa
main:p1在频道channel1发不了一条消息：aaaaaaaaaaaaa
Thread-1:s1收到了来自频道channel2的消息：bbbbbbbbbbbbb
main:p2在频道channel2发不了一条消息：bbbbbbbbbbbbb

可以看出:
0号线程一直处理s1与频道channel1的订阅，时刻准备接收channel1发布的新消息
1号线程一直处理s1与频道channel2，2号线程一直处理s2与频道channel1
而主线程处理订阅与接收新消息之外的操作
