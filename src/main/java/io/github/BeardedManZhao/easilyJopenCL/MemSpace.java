package io.github.BeardedManZhao.easilyJopenCL;

import org.jocl.Pointer;
import org.jocl.cl_context;
import org.jocl.cl_mem;

import static org.jocl.CL.*;

/**
 * 内存空间分配器，在这里我们可以创建计算时间使用的内存空间
 * <p>
 * 当然 这里有关于数据拷贝的一些操作
 *
 * @author zhao - 赵凌宇
 */
public class MemSpace {
    final cl_mem srcMemA;
    final cl_mem srcMemB;
    final cl_mem dstMem;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     * @param srcA    操作数1
     * @param srcB    操作数2
     * @param n1      操作数1的长度
     * @param n2      操作数2的长度
     * @param n3      计算结果的长度
     * @param sizeOf  每个操作数的大小
     */
    public MemSpace(cl_context context, Pointer srcA,
                    Pointer srcB,
                    long n1, long n2, long n3, int sizeOf) {

        // 创建输入和输出数据的内存对象，使用CL_MEM_USE_HOST_PTR
        srcMemA = clCreateBuffer(context,
                CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR,
                sizeOf * n1, srcA, null);
        srcMemB = clCreateBuffer(context,
                CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR,
                sizeOf * n2, srcB, null);
        dstMem = clCreateBuffer(context,
                CL_MEM_READ_WRITE | CL_MEM_ALLOC_HOST_PTR,
                sizeOf * n3, null, null);
    }
}
