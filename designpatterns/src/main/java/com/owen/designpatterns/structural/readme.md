### 结构型模式

#### 享元模式（Flyweight）
1. 简述：
    - 享元模式的主要目的是实现对象的共享 如DB连接池
    - 提高性能为目的
    - 核型思想：如果一个系统中存在多个相同的对象，那么只需共享一份对象的拷贝，而不必为每一次使用都创建新的对象。
2. 效果：对性能提升：
    - 可以节省重复创建的对象的开销，因为被享元模式维护的相同对象只会被创建一次，当创建对象比较消耗时，便可以节省大量时间。
    - 由于创建的对象减少，对系统内存的需求也减少，这将GC的压力也相对降低，进而使得系统拥有一个健康的内存结构和更快的反应速度。
3. 角色
    - 享元工厂 用于创建具体享元类，它保证相同的享元对象可以被系统共享，其内部使用了类似单例模式的算法，请求对象存在时，则直接返回，不存在，则创建。
    - 抽象享元 定于共享对象的业务接口。
    - 具体享元类  实现抽象享元类的接口，完成某一定的具体逻辑。
    - Main 使用享元模式的组件，通过享元工厂取得享元对象
    
#### 装饰者模式（decorator） 
1. 动态地将责任附加到对象上。
2. 若要扩展功能，装饰者提供了比继承更有弹性地替代方案。
3. 装饰者，被装饰者。
                
    
    
      
