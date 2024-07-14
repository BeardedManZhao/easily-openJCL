package io.github.BeardedManZhao.easilyJopenCL.kernel;

import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_kernel;

import static org.jocl.CL.clSetKernelArg;

/**
 * 本核相较于父类具有一个长度计算功能，它会将长度一起传递给函数！
 *
 * @author zhao
 */
@SuppressWarnings("unused")
public class LengthKernelSource extends KernelSource {

    public static final LengthKernelSource ARRAY_KRONECKER_PRODUCT_ARRAY_INT = new LengthKernelSource(args -> "    int index_start = gid * len0, index_end = index_start + len0;\n" +
            "    for (int i = index_start; i < index_end; ++i)\n" +
            "        c[i] = b[gid] * a[i - index_start];", "int", "ARRAY_KRONECKER_PRODUCT_ARRAY_INT");

    public static final LengthKernelSource ARRAY_KRONECKER_PRODUCT_ARRAY_FLOAT = new LengthKernelSource(args -> "    int index_start = gid * len0, index_end = index_start + len0;\n" +
            "    for (int i = index_start; i < index_end; ++i)\n" +
            "        c[i] = b[gid] * a[i - index_start];", "float", "ARRAY_KRONECKER_PRODUCT_ARRAY_FLOAT");

    public static final LengthKernelSource ARRAY_KRONECKER_PRODUCT_ARRAY_DOUBLE = new LengthKernelSource(args -> "    int index_start = gid * len0, index_end = index_start + len0;\n" +
            "    for (int i = index_start; i < index_end; ++i)\n" +
            "        c[i] = b[gid] * a[i - index_start];", "double", "ARRAY_KRONECKER_PRODUCT_ARRAY_DOUBLE");

    public static final LengthKernelSource ARRAY_ENCODE_XOR_ARRAY_CHAR2 = new LengthKernelSource(args -> args[3] + "[" + args[2] + "] = " + args[0] + "[" + args[2] + "] ^ " + args[1] + "[" + args[2] + " % len1];", "char2", "ARRAY_ENCODE_XOR_ARRAY_CHAR2");
    public static final LengthKernelSource ARRAY_DECODE_XOR_ARRAY_CHAR2 = new LengthKernelSource(args -> args[3] + "[" + args[2] + "] = " + args[0] + "[" + args[2] + "] ^ " + args[1] + "[" + args[2] + " % len1];", "char2", "ARRAY_DECODE_XOR_ARRAY_CHAR2");

    /**
     * 对于一个内核计算源码的构造函数
     *
     * @param kernelFunction 内核的计算源码实现函数对象。
     *                       参数说明：
     *                       参数1（a）=数组1的引用对象
     *                       参数2（b）=数组2的引用对象
     *                       参数3（gid）=当前计算操作位于的索引
     *                       参数4（c）=结果数组的引用对象
     *                       参数5（len0）=数组1的长度
     *                       参数6（len1）=数组2的长度
     *                       参数7（len2）=结果数组的长度
     * @param type           内核计算的类型 若设置为 int 则此内核只能计算 int 的值
     * @param name           计算内核的名称
     */
    public LengthKernelSource(KernelFunction kernelFunction, String type, String name) {
        super(kernelFunction, type, name);
    }

    @Override
    public String getKernelSource() {
        final String type1 = this.getType(), kName = this.name();
        return "__kernel void " + kName + "(__global const " + type1 + " *a,\n" +
                "             __global const " + type1 + " *b,\n" +
                "             __global " + type1 + " *c,\n" +
                "             __global const long *len)\n" +
                "{\n" +
                "    int gid = get_global_id(0), len0 = len[0], len1 = len[1], len2 = len[2];\n" +
                super.kernelFunction.calculateKernelSource("a", "b", "gid", "c", "len0", "len1", "len2") +
                "\n}";
    }

    @Override
    public boolean needLength() {
        return true;
    }

    @Override
    public int setKernelParam(cl_kernel clKernel, Pointer p1, Pointer p2, Pointer p3, Pointer lePoint) {
        int argIndex = super.setKernelParam(clKernel, p1, p2, p3, lePoint);
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, lePoint);
        return argIndex;
    }
}
