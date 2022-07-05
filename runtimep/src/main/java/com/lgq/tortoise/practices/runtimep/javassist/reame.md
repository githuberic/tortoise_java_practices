# javassist
javassist是一个动态生成字节码的框架，生成的字节码可以输出或实时加载到jvm。

Javassist的官方网站如下：
http://www.csg.ci.i.u-tokyo.ac.jp/~chiba/javassist/

### 问题
要想将编译时不存在的类在运行时动态创建并加载，通常有两种策略： 
- 动态编译 
- 动态生成二进制字节码（.class）

对于第二种策略，实际上已经有诸多比较成熟的开源项目提供支持，如CGLib、ASM、Javassist等。这些开源项目通常都具备两方面的功能： 
- 动态创建新类或新接口的二进制字节码 
- 动态扩展现有类或接口的二进制字节码

- CGLib的底层基于ASM实现，是一个高效高性能的生成库；
- 而ASM是一个轻量级的类库，但需要涉及到JVM的操作和指令；
- Javassist要简单的多，完全是基于Java的API，但其性能相比前二者要差一些。 尽管如此，在性能要求相对低的场合，Javassist仍然十分有用，如JBoss中就调用了Javassist。
