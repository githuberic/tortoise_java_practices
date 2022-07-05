package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e5;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lgq
 */
public class Proxy {
    //定义换行符
    private static final String ln = "\r\n";

    public static Object newProxyInstance(ClassLoader classLoader, Class clazz, InvocationHandler handler) {
        try {
            // 1.生成源代码
            String src = generateSrc(clazz);

            // 2.将源码写入磁盘，生成.java文件
            File file = createJavaFile(src);

            // 3.将生成的.java文件编译成.class文件
            compile(file);

            // 4.类加载器将.class文件加载到JVM
            Class proxyClass = classLoader.loadClass("$Proxy0");
            Constructor proxyConstructor = proxyClass.getConstructor(InvocationHandler.class);

            file.delete();

            return proxyConstructor.newInstance(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class intfce) {

        //获取接口所在包名
        String packageName = intfce.getPackage().getName() + "." + intfce.getSimpleName();

        StringBuilder sb = new StringBuilder();
        sb.append("package com.lgq.jbasic.proxy.dynamicproxy.example.e5;").append(ln)
                .append("import ").append(packageName).append(";").append(ln)
                .append("import java.lang.reflect.*;").append(ln)
                .append("public class $Proxy0 implements ").append(intfce.getName()).append(" { ").append(ln)
                .append("    private InvocationHandler h;").append(ln)
                .append("    public $Proxy0(InvocationHandler h) {").append(ln)
                .append("        this.h = h;").append(ln)
                .append("    }").append(ln).append(ln)

                .append(generateMethodsSrc(intfce))

                .append("}").append(ln).append(ln);

        System.out.println(sb.toString());
        return sb.toString();
    }

    private static StringBuilder generateMethodsSrc(Class intfce) {
        StringBuilder sb = new StringBuilder();

        for (Method m : intfce.getMethods()) {
            sb.append("    @Override").append(ln);

            Class<?>[] params = m.getParameterTypes();
            StringBuilder paramNames = new StringBuilder();
            StringBuilder paramValues = new StringBuilder();
            StringBuilder paramClasses = new StringBuilder();

            for (int i = 0; i < params.length; i++) {
                Class clazz = params[i];
                String type = clazz.getName();
                String paramName = toLowerFirstCase(clazz.getSimpleName()) + i;
                paramNames.append(type + " " + paramName);
                paramValues.append(paramName);
                paramClasses.append(clazz.getName() + ".class");
                if (i < params.length - 1) {
                    paramNames.append(",");
                    paramValues.append(",");
                    paramClasses.append(",");
                }
            }

            sb.append("    public ").append(m.getReturnType().getName()).append(" ").append(m.getName())
                    .append("(").append(paramNames).append("){").append(ln);
            sb.append("        try{").append(ln);
            sb.append("            Method m = ").append(intfce.getName()).append(".class.getMethod(").append("\"" + m.getName() + "\",").append("new Class[]{").append(paramClasses.toString()).append("});").append(ln);
            sb.append(hasReturnValue(m.getReturnType()) ? "            return " : "            ").append(getReturnCode("this.h.invoke(this, m, new Object[]{" + paramValues + "})", m.getReturnType())).append(";").append(ln);

            sb.append(getReturnEmptyCode(m.getReturnType()));
            sb.append("        }catch(Throwable e){}").append(ln);
            sb.append("    }").append(ln).append(ln);

        }

        return sb;
    }

    private static Map<Class, Class> mappings = new HashMap<Class, Class>();

    static {
        mappings.put(int.class, Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "return 0;";
        } else if (returnClass == void.class) {
            return "";
        } else {
            return "return null;";
        }
    }

    private static boolean hasReturnValue(Class<?> clazz) {
        return clazz != void.class;
    }

    private static String getReturnCode(String code, Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "((" + mappings.get(returnClass).getName() + ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static String toLowerFirstCase(final String src) {
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private static File createJavaFile(final String src) throws Exception {
        String filePath = Proxy.class.getResource("").getPath();

        File file = new File(filePath + "$Proxy0.java");
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
