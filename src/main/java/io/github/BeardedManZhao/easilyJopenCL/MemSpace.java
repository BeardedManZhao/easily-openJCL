package io.github.BeardedManZhao.easilyJopenCL;

import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;
import io.github.BeardedManZhao.easilyJopenCL.kernel.LengthKernelSource;
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

    final int sizeOf;
    final long n1, n2, n3;

    final boolean mergeA, mergeB;
    final cl_context cl_context;
    final MemSpace srcMemSpace;
    boolean isNotRelease = true;
    cl_mem lenMem;
    KernelSource kernelSource;

    /**
     * 构造函数
     *
     * @param context      上下文对象
     * @param srcA         操作数1
     * @param srcB         操作数2
     * @param n1           操作数1的长度
     * @param n2           操作数2的长度
     * @param n3           计算结果的长度
     * @param sizeOf       每个操作数的大小
     * @param kernelSource 本次计算要使用的内核名称
     *                     <p>
     *                     The kernel name to be used in this calculation
     */
    public MemSpace(cl_context context, Pointer srcA,
                    Pointer srcB,
                    long n1, long n2, long n3, int sizeOf, KernelSource kernelSource) {
        this(
                clCreateBuffer(context,
                        CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR,
                        sizeOf * n1, srcA, null),
                clCreateBuffer(context,
                        CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR,
                        sizeOf * n2, srcB, null),
                clCreateBuffer(context,
                        CL_MEM_READ_WRITE | CL_MEM_ALLOC_HOST_PTR,
                        sizeOf * n3, null, null),
                sizeOf, n1, n2, n3, context,
                kernelSource.needLength() ? clCreateBuffer(context,
                        CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR,
                        sizeOf * 3L, Pointer.to(new long[]{n1, n2, n3}), null) : null,
                kernelSource, false, false, null
        );
    }

    public MemSpace(cl_mem srcMemA, cl_mem srcMemB, cl_mem dstMem, int sizeOf, long n1, long n2, long n3, org.jocl.cl_context cl_context, cl_mem lenMem, KernelSource kernelSource, boolean mergeA, boolean mergeB, MemSpace srcSpace) {
        this.srcMemA = srcMemA;
        this.srcMemB = srcMemB;
        this.dstMem = dstMem;
        this.sizeOf = sizeOf;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.cl_context = cl_context;
        this.lenMem = lenMem;
        this.kernelSource = kernelSource;
        this.mergeA = mergeA;
        this.mergeB = mergeB;
        this.srcMemSpace = srcSpace;

    }

    /**
     * @return 当前内存空间要用于哪个内核计算!
     */
    public KernelSource getKernelSource() {
        return kernelSource;
    }

    /**
     * 若您需要改变计算内核，则可以在这里设置！
     *
     * @param kernelSource 内核名称
     */
    public void setKernelSource(KernelSource kernelSource) {
        if (kernelSource instanceof LengthKernelSource && this.lenMem == null) {
            // 如果修改前没有len内存，而修改后的内核需要len内存，则需要重新创建长度内存
            lenMem = kernelSource.needLength() ? clCreateBuffer(this.cl_context,
                    CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR,
                    sizeOf * 3L, Pointer.to(new long[]{n1, n2, n3}), null) : null;
        }
        this.kernelSource = kernelSource;
    }

    /**
     * 将当前内存空间复制出来，并获取到新的内存空间 通常情况下，您可以使用此函数来将这个内存空间的一部分数值和新的操作数合并起来！
     * <p>
     * Copy the current memory space and obtain a new memory space. Usually, you can use this function to merge a portion of the values in this memory space with the new operand!
     *
     * @param srcA 操作数1 如果此参数为 null 代表您要复用当前内存对象中的 操作数1 以及新的 操作数2 因此操作数2 不得为 null
     * @param srcB 操作数2 如果此参数为 null 代表您要复用当前内存对象中的 操作数2 以及新的 操作数1 因此操作数1 不得为 null
     * @param n1   操作数1的长度 若 操作数1 为 null 则此参数不会使用到
     * @param n2   操作数2的长度 若 操作数2 为 null 则此参数不会使用到
     * @param n3   结果的长度
     * @return 将当前内存对象中的一个参数与您所设置的新操作数合并成一个新的内存空间/
     */
    public MemSpace merge(Pointer srcA, Pointer srcB, long n1, long n2, long n3) {
        // 首先看一下谁是 null 是 null 的代表是要复用的
        if (srcA == null) {
            // 复用操作数1
            return new MemSpace(
                    this.srcMemA,
                    clCreateBuffer(this.cl_context, CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR, sizeOf * n2, srcB, null),
                    clCreateBuffer(this.cl_context, CL_MEM_READ_WRITE | CL_MEM_ALLOC_HOST_PTR, sizeOf * n3, null, null),
                    this.sizeOf, this.n1, n2, n3, this.cl_context, this.lenMem, kernelSource, true, false, this);
        } else if (srcB == null) {
            // 复用操作数2
            return new MemSpace(
                    clCreateBuffer(this.cl_context, CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR, sizeOf * n1, srcA, null),
                    this.srcMemB,
                    clCreateBuffer(this.cl_context, CL_MEM_READ_WRITE | CL_MEM_ALLOC_HOST_PTR, sizeOf * n3, null, null),
                    this.sizeOf, n1, this.n2, n3, this.cl_context, this.lenMem, kernelSource, false, true, this);
        }
        throw new RuntimeException("Either 'srcA n1' or 'srcB n2' must be null!");
    }

    /**
     * @return 如果第一个操作数是合并的，则返回 true
     */
    public boolean isMergeA() {
        return mergeA;
    }

    /**
     * @return 如果第二个操作数是合并的，则返回 true
     */
    public boolean isMergeB() {
        return mergeB;
    }

    /**
     * @return 如果当前内存空间没有被释放，则返回 true
     */
    public boolean isNotRelease() {
        return isNotRelease;
    }

    /**
     * 释放当前内存空间对象，以及此内存空间对象引用的所有内存空间对象，这是一个链式效应，当调用了此方法后，此内存空间对象以及此处引用的所有内存空间对象都会被释放。
     * <p>
     * Releasing the current memory space object, as well as all memory space objects referenced by this memory space object, is a chain effect. When this method is called, this memory space object and all memory space objects referenced here will be released.
     */
    public void releaseResources() {
        // A 是合并的 我们在这里不要释放 A 要把 A 给 src 用
        // 另一 情况 B 是合并的 我们在这里不要释放 B 要把 B 给 src 用
        if (!this.isMergeA()) {
            clReleaseMemObject(srcMemA);
        } else if (!this.isMergeB()) {
            clReleaseMemObject(srcMemB);
        }

        clReleaseMemObject(dstMem);
        if (lenMem != null) {
            clReleaseMemObject(lenMem);
        }
        this.isNotRelease = false;
        // 然后释放源
        if (srcMemSpace != null && srcMemSpace.isNotRelease()) {
            srcMemSpace.releaseResources();
        }
    }
}
