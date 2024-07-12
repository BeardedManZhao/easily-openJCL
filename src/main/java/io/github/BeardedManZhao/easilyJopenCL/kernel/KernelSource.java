package io.github.BeardedManZhao.easilyJopenCL.kernel;

import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_kernel;

import static org.jocl.CL.clSetKernelArg;

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

    /* 下面是幂运算 */

    public static final KernelSource ARRAY_POW2_NULL_INT = new KernelSource(args -> String.format("int n = %s[%s]; %s[%s] = n * n;", args[0], args[2], args[3], args[2]), "int", "ARRAY_POW2_NULL_INT");
    public static final KernelSource ARRAY_POW4_NULL_INT = new KernelSource(args -> String.format("int n = %s[%s]; %s[%s] = n * n * n * n;", args[0], args[2], args[3], args[2]), "int", "ARRAY_POW4_NULL_INT");
    public static final KernelSource ARRAY_POW6_NULL_INT = new KernelSource(args -> String.format("int n = %s[%s]; %s[%s] = n * n * n * n * n * n;", args[0], args[2], args[3], args[2]), "int", "ARRAY_POW6_NULL_INT");
    public static final KernelSource ARRAY_POW8_NULL_INT = new KernelSource(args -> String.format("int n = %s[%s]; %s[%s] = n * n * n * n * n * n * n * n;", args[0], args[2], args[3], args[2]), "int", "ARRAY_POW8_NULL_INT");
    public static final KernelSource ARRAY_POW2_NULL_FLOAT = new KernelSource(args -> String.format("float n = %s[%s]; %s[%s] = n * n;", args[0], args[2], args[3], args[2]), "float", "ARRAY_POW2_NULL_INT");
    public static final KernelSource ARRAY_POW4_NULL_FLOAT = new KernelSource(args -> String.format("float n = %s[%s]; %s[%s] = n * n * n * n;", args[0], args[2], args[3], args[2]), "float", "ARRAY_POW4_NULL_INT");
    public static final KernelSource ARRAY_POW6_NULL_FLOAT = new KernelSource(args -> String.format("float n = %s[%s]; %s[%s] = n * n * n * n * n * n;", args[0], args[2], args[3], args[2]), "float", "ARRAY_POW6_NULL_INT");
    public static final KernelSource ARRAY_POW8_NULL_FLOAT = new KernelSource(args -> String.format("float n = %s[%s]; %s[%s] = n * n * n * n * n * n * n * n;", args[0], args[2], args[3], args[2]), "int", "ARRAY_POW8_NULL_INT");
    public static final KernelSource ARRAY_POW2_NULL_DOUBLE = new KernelSource(args -> String.format("double n = %s[%s]; %s[%s] = n * n;", args[0], args[2], args[3], args[2]), "double", "ARRAY_POW2_NULL_DOUBLE");
    public static final KernelSource ARRAY_POW4_NULL_DOUBLE = new KernelSource(args -> String.format("double n = %s[%s]; %s[%s] = n * n * n * n;", args[0], args[2], args[3], args[2]), "double", "ARRAY_POW4_NULL_DOUBLE");
    public static final KernelSource ARRAY_POW6_NULL_DOUBLE = new KernelSource(args -> String.format("double n = %s[%s]; %s[%s] = n * n * n * n * n * n;", args[0], args[2], args[3], args[2]), "double", "ARRAY_POW6_NULL_DOUBLE");
    public static final KernelSource ARRAY_POW8_NULL_DOUBLE = new KernelSource(args -> String.format("double n = %s[%s]; %s[%s] = n * n * n * n * n * n * n * n;", args[0], args[2], args[3], args[2]), "double", "ARRAY_POW8_NULL_DOUBLE");

    /* 下面是最值运算 */

    public static final KernelSource ARRAY_MAX_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = max(%s[%s], %s[%s]);", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_MAX_ARRAY_INT");
    public static final KernelSource ARRAY_MAX_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = max(%s[%s], %s[%s]);", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_MAX_ARRAY_FLOAT");
    public static final KernelSource ARRAY_MAX_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = max(%s[%s], %s[%s]);", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_MAX_ARRAY_DOUBLE");
    public static final KernelSource ARRAY_MIN_ARRAY_INT = new KernelSource(args -> String.format("%s[%s] = min(%s[%s], %s[%s]);", args[3], args[2], args[0], args[2], args[1], args[2]), "int", "ARRAY_MIN_ARRAY_INT");
    public static final KernelSource ARRAY_MIN_ARRAY_FLOAT = new KernelSource(args -> String.format("%s[%s] = min(%s[%s], %s[%s]);", args[3], args[2], args[0], args[2], args[1], args[2]), "float", "ARRAY_MIN_ARRAY_FLOAT");
    public static final KernelSource ARRAY_MIN_ARRAY_DOUBLE = new KernelSource(args -> String.format("%s[%s] = min(%s[%s], %s[%s]);", args[3], args[2], args[0], args[2], args[1], args[2]), "double", "ARRAY_MIN_ARRAY_DOUBLE");
    protected final KernelFunction kernelFunction;
    private final String type, name;

    /**
     * 对于一个内核计算源码的构造函数
     *
     * @param kernelFunction 内核的计算源码实现函数对象。
     *                       参数说明：
     *                          参数1（a）=数组1的引用对象
     *                          参数2（b）=数组2的引用对象
     *                          参数3（gid）=当前计算操作位于的索引
     *                          参数4（c）=结果数组的引用对象
     *                          参数5（len0）=数组1的长度
     *                          参数6（len1）=数组2的长度
     *                          参数7（len2）=结果数组的长度
     * @param type           内核计算的类型 若设置为 int 则此内核只能计算 int 的值
     * @param name           计算内核的名称
     */
    public KernelSource(KernelFunction kernelFunction, String type, String name) {
        this.kernelFunction = kernelFunction;
        this.type = type;
        this.name = name;
    }


    /**
     * 当前的组件是否需要传递长度参数 如果为 false 则不会为长度创建内存空间
     *
     * @return 如果需要长度参数 则返回 true
     */
    public boolean needLength() {
        return false;
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
     * @return 计算源码
     */
    public String getKernelSource() {
        final String type1 = this.getType(), kName = this.name();
        return "__kernel void " + kName + "(__global const " + type1 + " *a,\n" +
                "             __global const " + type1 + " *b,\n" +
                "             __global " + type1 + " *c)\n" +
                "{\n" +
                "    int gid = get_global_id(0);\n" +
                this.kernelFunction.calculateKernelSource("a", "b", "gid", "c") +
                "\n}";
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

    /**
     * 设置内核参数
     *
     * @param clKernel 内核对象
     * @param p1       操作数1
     * @param p2       操作数2
     * @param p3       结果指针
     * @param lePoint  长度指针（不是所有的都会使用到此参数）
     * @return 参数索引 可以用来进行继续追加等操作
     */
    public int setKernelParam(cl_kernel clKernel, Pointer p1, Pointer p2, Pointer p3, Pointer lePoint) {
        int argIndex = 0;
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, p1);
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, p2);
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, p3);
        return argIndex;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
