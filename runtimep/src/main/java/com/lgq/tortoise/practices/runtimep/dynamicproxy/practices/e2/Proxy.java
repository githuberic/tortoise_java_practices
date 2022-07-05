package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e2;


import com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.Alipay;
import com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.Payable;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;

/**
 * @author lgq
 */
public class Proxy {
    //定义换行符
    private static final String ln = "\r\n";

    public static Object newProxyInstance(ClassLoader classLoader) {
        try {

            // 1.生成源代码
            String src = generateSrc();

            // 2.将源码写入磁盘，生成.java文件
            File file = createJavaFile(src);

            // 3.将生成的.java文件编译成.class文件
            compile(file);

            // 4.类加载器将.class文件加载到JVM
            Class<?> proxyClass = classLoader.loadClass("AlipayLogProxy");
            Constructor<?> proxyConstructor = proxyClass.getConstructor(Payable.class);

            file.delete();

            // 5.反射进行实例化对象
            return proxyConstructor.newInstance(new Alipay());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String generateSrc() {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.lgq.jbasic.proxy.dynamicproxy.example.e2;").append(ln)
                .append("import com.lgq.jbasic.proxy.dynamicproxy.example.Payable;").append(ln)
                .append("public class AlipayLogProxy implements Payable { ").append(ln)
                .append("    private Payable payable;").append(ln)
                .append("    public AlipayLogProxy(Payable payable) {").append(ln)
                .append("        this.payable = payable;").append(ln)
                .append("    }").append(ln)
                .append("    @Override").append(ln)
                .append("    public void pay() {").append(ln)
                .append("        System.out.println(\"Print log(before invoke)>>>\");").append(ln)
                .append("        payable.pay();").append(ln)
                .append("        System.out.println(\"Print log(after invoke)>>>\");").append(ln)
                .append("    }").append(ln)
                .append("}");
        return sb.toString();
    }

    private static File createJavaFile(String src) throws Exception {
        String filePath = Proxy.class.getResource("").getPath();

        File file = new File(filePath + "AlipayLogProxy.java");
        FileWriter fw = new FileWriter(file);
        fw.write(src);
        fw.flush();
        fw.close();

        return file;
    }

    private static void compile(final File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable iterable = manager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
        task.call();
        manager.close();
    }
}
