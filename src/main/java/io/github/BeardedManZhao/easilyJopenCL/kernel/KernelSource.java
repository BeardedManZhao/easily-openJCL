package io.github.BeardedManZhao.easilyJopenCL.kernel;

/**
 * openCL 中计算操作的源码实现
 *
 * @author zhao - 赵凌宇
 */
@SuppressWarnings("unused")
public class KernelSource {

    public static final KernelSource ARRAY_ADD_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] + %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_ADD_ARRAY_INT");
    public static final KernelSource ARRAY_SUB_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] - %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_SUB_ARRAY_INT");
    public static final KernelSource ARRAY_MUL_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] * %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_MUL_ARRAY_INT");
    public static final KernelSource ARRAY_DIV_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] / %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_DIV_ARRAY_INT");
    public static final KernelSource ARRAY_LS_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] << %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_LS_ARRAY_INT");
    public static final KernelSource ARRAY_RS_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] >> %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_RS_ARRAY_INT");
    public static final KernelSource ARRAY_ADD_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] + %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_ADD_ARRAY_FLOAT");
    public static final KernelSource ARRAY_SUB_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] - %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_SUB_ARRAY_FLOAT");
    public static final KernelSource ARRAY_MUL_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] * %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_MUL_ARRAY_FLOAT");
    public static final KernelSource ARRAY_DIV_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] / %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_DIV_ARRAY_FLOAT");
    public static final KernelSource ARRAY_LS_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] << %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_LS_ARRAY_FLOAT");
    public static final KernelSource ARRAY_RS_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] >> %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_RS_ARRAY_FLOAT");
    public static final KernelSource ARRAY_ADD_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] + %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_ADD_ARRAY_DOUBLE");
    public static final KernelSource ARRAY_SUB_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] - %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_SUB_ARRAY_DOUBLE");
    public static final KernelSource ARRAY_MUL_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] * %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_MUL_ARRAY_DOUBLE");
    public static final KernelSource ARRAY_DIV_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] / %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_DIV_ARRAY_DOUBLE");
    public static final KernelSource ARRAY_LS_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] << %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_LS_ARRAY_DOUBLE");
    public static final KernelSource ARRAY_RS_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] >> %s[%s];", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_RS_ARRAY_DOUBLE");

    /* 上面是 数组与数组之间的计算内核 下面是数组与数值之间的计算内核 */


    public static final KernelSource ARRAY_ADD_NUMBER_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] + %s[0];", args[3], args[2], args[0], args[2], args[1]), "int", "ARRAY_ADD_NUMBER_INT");
    public static final KernelSource ARRAY_SUB_NUMBER_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] - %s[0];", args[3], args[2], args[0], args[2], args[1]), "int", "ARRAY_SUB_NUMBER_INT");
    public static final KernelSource ARRAY_MUL_NUMBER_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] * %s[0];", args[3], args[2], args[0], args[2], args[1]), "int", "ARRAY_MUL_NUMBER_INT");
    public static final KernelSource ARRAY_DIV_NUMBER_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] / %s[0];", args[3], args[2], args[0], args[2], args[1]), "int", "ARRAY_DIV_NUMBER_INT");
    public static final KernelSource ARRAY_LS_NUMBER_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] << %s[0];", args[3], args[2], args[0], args[2], args[1]), "int", "ARRAY_LS_NUMBER_INT");
    public static final KernelSource ARRAY_RS_NUMBER_INT = new KernelSource(args -> String.format("%s[%s] = %s[%s] >> %s[0];", args[3], args[2], args[0], args[2], args[1]), "int", "ARRAY_RS_NUMBER_INT");
    public static final KernelSource ARRAY_ADD_NUMBER_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] + %s[0];", args[3], args[2], args[0], args[2], args[1]), "float", "ARRAY_ADD_NUMBER_FLOAT");
    public static final KernelSource ARRAY_SUB_NUMBER_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] - %s[0];", args[3], args[2], args[0], args[2], args[1]), "float", "ARRAY_SUB_NUMBER_FLOAT");
    public static final KernelSource ARRAY_MUL_NUMBER_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] * %s[0];", args[3], args[2], args[0], args[2], args[1]), "float", "ARRAY_MUL_NUMBER_FLOAT");
    public static final KernelSource ARRAY_DIV_NUMBER_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] / %s[0];", args[3], args[2], args[0], args[2], args[1]), "float", "ARRAY_DIV_NUMBER_FLOAT");
    public static final KernelSource ARRAY_LS_NUMBER_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] << %s[0];", args[3], args[2], args[0], args[2], args[1]), "float", "ARRAY_LS_NUMBER_FLOAT");
    public static final KernelSource ARRAY_RS_NUMBER_FLOAT = new KernelSource(args -> String.format("%s[%s] = %s[%s] >> %s[0];", args[3], args[2], args[0], args[2], args[1]), "float", "ARRAY_RS_NUMBER_FLOAT");
    public static final KernelSource ARRAY_ADD_NUMBER_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] + %s[0];", args[3], args[2], args[0], args[2], args[1]), "double", "ARRAY_ADD_NUMBER_DOUBLE");
    public static final KernelSource ARRAY_SUB_NUMBER_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] - %s[0];", args[3], args[2], args[0], args[2], args[1]), "double", "ARRAY_SUB_NUMBER_DOUBLE");
    public static final KernelSource ARRAY_MUL_NUMBER_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] * %s[0];", args[3], args[2], args[0], args[2], args[1]), "double", "ARRAY_MUL_NUMBER_DOUBLE");
    public static final KernelSource ARRAY_DIV_NUMBER_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] / %s[0];", args[3], args[2], args[0], args[2], args[1]), "double", "ARRAY_DIV_NUMBER_DOUBLE");
    public static final KernelSource ARRAY_LS_NUMBER_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] << %s[0];", args[3], args[2], args[0], args[2], args[1]), "double", "ARRAY_LS_NUMBER_DOUBLE");
    public static final KernelSource ARRAY_RS_NUMBER_DOUBLE = new KernelSource(args -> String.format("%s[%s] = %s[%s] >> %s[0];", args[3], args[2], args[0], args[2], args[1]), "double", "ARRAY_RS_NUMBER_DOUBLE");

    private final KernelFunction kernelFunction;
    private final String type, name;

    /**
     * 对于一个内核计算源码的构造函数
     *
     * @param kernelFunction 内核的计算源码实现函数对象。
     *                       参数说明：参数1=数组1的引用对象  参数2=数组2的引用对象  参数3=当前计算操作位于的索引  参数4=结果数组的引用对象
     * @param type           内核计算的类型 若设置为 int 则此内核只能计算 int 的值
     * @param name           计算内核的名称
     */
    public KernelSource(KernelFunction kernelFunction, String type, String name) {
        this.kernelFunction = kernelFunction;
        this.type = type;
        this.name = name;
    }

    public String name(){
        return this.name;
    }

    /**
     * 获取计算源码的计算类型
     *
     * @return 计算类型
     */
    public String getType() {
        return type;
    }

    /**
     * 获取计算内核源码
     *
     * @param type1 内核计算函数，操作数的类型
     * @param kernelFunction 内核计算函数
     * @param kName 内核计算模式的名称
     * @return 计算源码
     */
    public static String getKernelSource(String type1, String kName, KernelFunction kernelFunction) {
        return "__kernel void " + kName + "(__global const " + type1 + " *a,\n" +
                "             __global const " + type1 + " *b,\n" +
                "             __global " + type1 + " *c)\n" +
                "{\n" +
                "    int gid = get_global_id(0);\n" +
                "    " +
                kernelFunction.calculateKernelSource("a", "b", "gid", "c") +
                "\n}";
    }

    /**
     * 获取计算内核源码
     * @return 计算源码
     */
    public String getKernelSource() {
        return getKernelSource(this.getType(), this.name(), this.kernelFunction);
    }

    /**
     * 获取计算源码的计算维度
     *
     * @param global_work_size 全局计算尺寸
     * @return 您可以在这里设置您的计算操作要作用在哪个维度层，默认是与全局尺寸相同的维度，这样我们的计算操作就会作用在每一个元素上。
     */
    public int work_dim_algorithm(long[] global_work_size) {
        return global_work_size.length;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
