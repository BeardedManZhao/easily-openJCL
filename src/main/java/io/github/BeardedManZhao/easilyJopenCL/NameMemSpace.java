package io.github.BeardedManZhao.easilyJopenCL;

import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;
import org.jocl.Pointer;
import org.jocl.cl_mem;
import top.lingyuzhao.varFormatter.utils.DataObj;

import static org.jocl.CL.*;

/**
 * 这个类继承了 MemSpace 类，其相对于 MemSpace 具有名称以及树状图规划的功能，此对象可以实现内存空间引用树状图的获取！
 * <p>
 * This class inherits from the MemSpace class, which has the function of naming and tree diagram planning relative to MemSpace. This object can achieve the retrieval of memory space reference tree diagram!
 */
public class NameMemSpace extends MemSpace {

    private String memSpaceName;

    public NameMemSpace(org.jocl.cl_context context, Pointer srcA, Pointer srcB, long n1, long n2, long n3, int sizeOf, KernelSource kernelSource, String memSpaceName) {
        super(context, srcA, srcB, n1, n2, n3, sizeOf, kernelSource);
        this.memSpaceName = memSpaceName;
    }

    public NameMemSpace(cl_mem srcMemA, cl_mem srcMemB, cl_mem dstMem, int sizeOf, long n1, long n2, long n3, org.jocl.cl_context cl_context, cl_mem lenMem, KernelSource kernelSource, boolean mergeA, boolean mergeB, MemSpace srcSpace, String memSpaceName) {
        super(srcMemA, srcMemB, dstMem, sizeOf, n1, n2, n3, cl_context, lenMem, kernelSource, mergeA, mergeB, srcSpace);
        this.memSpaceName = memSpaceName;
    }

    public String getMemSpaceName() {
        return memSpaceName;
    }

    public void setMemSpaceName(String memSpaceName) {
        this.memSpaceName = memSpaceName;
    }

    public DataObj explain() {
        // 首先计算当前的内存空间的名字
        String memSpaceName1 = this.getMemSpaceName();
        String i = "EoCl" + memSpaceName1.hashCode();
        final DataObj dataObj = new DataObj(i);
        String i1 = "N" + this.srcMemA.hashCode();
        String i2 = "N" + this.srcMemB.hashCode();
        dataObj.setPrefix(
                i + '[' + memSpaceName1 + '_' + (this.isNotRelease() ? "used" : "released") + "]\n" +
                        i1 + "[srcMemA]\n" +
                        i2 + "[srcMemB]"

        );
        dataObj.put(new DataObj(i1));
        dataObj.put(new DataObj(i2));
        // 开始计算此内存空间的引用空间
        MemSpace srcMemSpace1 = this.srcMemSpace;
        if (srcMemSpace1 == null) {
            DataObj dataObj1 = new DataObj(i + "_end[" + "end]");
            dataObj.put(dataObj1);
            // 如果没有引用的空间就直接返回
            return dataObj;
        }
        // 有引用就准备继续计算
        if (srcMemSpace1 instanceof NameMemSpace) {
            // 如果引用空间可以计算图就计算
            dataObj.put(((NameMemSpace) srcMemSpace1).explain());
        } else {
            // 如果引用空间不可以计算就写 Interrupted
            DataObj dataObj1 = new DataObj("Interrupted");
            dataObj.put(dataObj1);
        }
        return dataObj;
    }

    @Override
    public NameMemSpace merge(Pointer srcA, Pointer srcB, long n1, long n2, long n3) {
        // 首先看一下谁是 null 是 null 的代表是要复用的
        if (srcA == null) {
            // 复用操作数1
            return new NameMemSpace(
                    this.srcMemA,
                    clCreateBuffer(this.cl_context, CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR, sizeOf * n2, srcB, null),
                    clCreateBuffer(this.cl_context, CL_MEM_READ_WRITE | CL_MEM_ALLOC_HOST_PTR, sizeOf * n3, null, null),
                    this.sizeOf, this.n1, n2, n3, this.cl_context, this.lenMem, kernelSource, true, false, this, this.getMemSpaceName() + "_sub");
        } else if (srcB == null) {
            // 复用操作数2
            return new NameMemSpace(
                    clCreateBuffer(this.cl_context, CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR, sizeOf * n1, srcA, null),
                    this.srcMemB,
                    clCreateBuffer(this.cl_context, CL_MEM_READ_WRITE | CL_MEM_ALLOC_HOST_PTR, sizeOf * n3, null, null),
                    this.sizeOf, n1, this.n2, n3, this.cl_context, this.lenMem, kernelSource, false, true, this, this.getMemSpaceName() + "_sub");
        }
        throw new RuntimeException("Either 'srcA n1' or 'srcB n2' must be null!");
    }
}
