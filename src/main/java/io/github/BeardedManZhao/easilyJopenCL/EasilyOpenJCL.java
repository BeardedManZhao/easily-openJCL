package io.github.BeardedManZhao.easilyJopenCL;

import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;
import org.jocl.*;

import java.util.HashMap;

import static org.jocl.CL.*;

/**
 * 超简单的OpenCL调用组件，您可以使用它来进行非常简单且容易的 openCL 显卡操作！
 * <p>
 * Ultra simple OpenCL calling component, you can use it to perform very simple and easy OpenCL graphics card operations!
 *
 * @author zhao - 赵凌宇
 */
public class EasilyOpenJCL {
    private final cl_context context;
    private final cl_command_queue commandQueue;
    private final cl_program program;
    private final HashMap<String, cl_kernel> kernels = new HashMap<>();

    private boolean isNotReleased = true;

    public EasilyOpenJCL(cl_context context, cl_command_queue commandQueue, KernelSource... kernelSources) {
        this.context = context;
        this.commandQueue = commandQueue;

        final String[] strings = new String[kernelSources.length];
        final long[] ints = new long[kernelSources.length];
        int index = -1;
        for (KernelSource kernelSource : kernelSources) {
            final String kernelSource1 = kernelSource.getKernelSource();
            strings[++index] = kernelSource1;
            ints[index] = kernelSource1.length();
        }

        // 从源代码创建程序
        this.program = clCreateProgramWithSource(this.context,
                strings.length, strings, ints, null);

        // 编译程序
        clBuildProgram(this.program, 0, null, null, null, null);

        // 创建内核
        for (KernelSource kernelSource : kernelSources) {
            kernels.put(kernelSource.name(), clCreateKernel(this.program, kernelSource.name(), null));
        }
    }

    public static EasilyOpenJCL initOpenCLEnvironment(KernelSource... kernelSources) {
        cl_context context;
        cl_command_queue commandQueue;
        // 启用异常处理，从而省略后续的错误检查
        CL.setExceptionsEnabled(true);

        // 指定平台、设备类型和设备索引
        final int platformIndex = 0;
        final long deviceType = CL_DEVICE_TYPE_ALL;
        final int deviceIndex = 0;

        // 获取平台的数量
        int[] numPlatformsArray = new int[1];
        clGetPlatformIDs(0, null, numPlatformsArray);
        int numPlatforms = numPlatformsArray[0];

        // 获取平台ID
        cl_platform_id[] platforms = new cl_platform_id[numPlatforms];
        clGetPlatformIDs(platforms.length, platforms, null);
        cl_platform_id platform = platforms[platformIndex];

        // 初始化上下文属性
        cl_context_properties contextProperties = new cl_context_properties();
        contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);

        // 获取指定平台的设备数量
        int[] numDevicesArray = new int[1];
        clGetDeviceIDs(platform, deviceType, 0, null, numDevicesArray);
        int numDevices = numDevicesArray[0];

        // 获取设备ID
        cl_device_id[] devices = new cl_device_id[numDevices];
        clGetDeviceIDs(platform, deviceType, numDevices, devices, null);
        cl_device_id device = devices[deviceIndex];

        // 为选定的设备创建上下文
        context = clCreateContext(
                contextProperties, 1, new cl_device_id[]{device},
                null, null, null);

        // 为选定的设备创建命令队列
        cl_queue_properties properties = new cl_queue_properties();
        commandQueue = clCreateCommandQueueWithProperties(
                context, device, properties, null);
        return new EasilyOpenJCL(context, commandQueue, kernelSources);
    }

    /**
     * 调用显卡 进行计算数组与数组之间的计算操作
     * <p>
     * Calling the graphics card to perform calculation operations between arrays
     *
     * @param srcArrayA  需要被计算的数组1
     *                   <p>
     *                   Array 1 that needs to be calculated
     * @param srcArrayB  需要被计算的数组2
     *                   <p>
     *                   Array 2 that needs to be calculated
     * @param dstArray   计算之后的结果存储位
     *                   <p>
     *                   Storage bits for calculated results
     * @param kernelName 本次计算要使用的内核名称
     *                   <p>
     *                   The kernel name to be used in this calculation
     */
    public void calculate(int[] srcArrayA, int[] srcArrayB, int[] dstArray, KernelSource kernelName) {
        final cl_kernel clKernel = getOrThrow(kernelName);
        final Pointer srcA = Pointer.to(srcArrayA);
        final Pointer srcB = Pointer.to(srcArrayB);
        final Pointer dst = Pointer.to(dstArray);
        final MemSpace memSpace = new MemSpace(this.context, srcA, srcB, srcArrayA.length, srcArrayB.length, dstArray.length, Sizeof.cl_int);
        kernelCalculate(dstArray.length, clKernel, memSpace.srcMemA, memSpace.srcMemB, memSpace.dstMem, dst, Sizeof.cl_int, new long[]{Math.max(srcArrayA.length, srcArrayB.length)}, kernelName);
    }

    /**
     * 调用显卡 进行计算数组与数组之间的计算操作
     * <p>
     * Calling the graphics card to perform calculation operations between arrays
     *
     * @param srcArrayA  需要被计算的数组1
     *                   <p>
     *                   Array 1 that needs to be calculated
     * @param srcArrayB  需要被计算的数组2
     *                   <p>
     *                   Array 2 that needs to be calculated
     * @param dstArray   计算之后的结果存储位
     *                   <p>
     *                   Storage bits for calculated results
     * @param kernelName 本次计算要使用的内核名称
     *                   <p>
     *                   The kernel name to be used in this calculation
     */
    public void calculate(float[] srcArrayA, float[] srcArrayB, float[] dstArray, KernelSource kernelName) {
        final cl_kernel clKernel = getOrThrow(kernelName);
        final Pointer srcA = Pointer.to(srcArrayA);
        final Pointer srcB = Pointer.to(srcArrayB);
        final Pointer dst = Pointer.to(dstArray);
        final MemSpace memSpace = new MemSpace(this.context, srcA, srcB, srcArrayA.length, srcArrayB.length, dstArray.length, Sizeof.cl_float);
        kernelCalculate(dstArray.length, clKernel, memSpace.srcMemA, memSpace.srcMemB, memSpace.dstMem, dst, Sizeof.cl_float, new long[]{Math.max(srcArrayA.length, srcArrayB.length)}, kernelName);
    }

    /**
     * 调用显卡 进行计算数组与数组之间的计算操作
     * <p>
     * Calling the graphics card to perform calculation operations between arrays
     *
     * @param srcArrayA  需要被计算的数组1
     *                   <p>
     *                   Array 1 that needs to be calculated
     * @param srcArrayB  需要被计算的数组2
     *                   <p>
     *                   Array 2 that needs to be calculated
     * @param dstArray   计算之后的结果存储位
     *                   <p>
     *                   Storage bits for calculated results
     * @param kernelName 本次计算要使用的内核名称
     *                   <p>
     *                   The kernel name to be used in this calculation
     */
    public void calculate(double[] srcArrayA, double[] srcArrayB, double[] dstArray, KernelSource kernelName) {
        final cl_kernel clKernel = getOrThrow(kernelName);
        final Pointer srcA = Pointer.to(srcArrayA);
        final Pointer srcB = Pointer.to(srcArrayB);
        final Pointer dst = Pointer.to(dstArray);
        final MemSpace memSpace = new MemSpace(this.context, srcA, srcB, srcArrayA.length, srcArrayB.length, dstArray.length, Sizeof.cl_double);
        kernelCalculate(dstArray.length, clKernel, memSpace.srcMemA, memSpace.srcMemB, memSpace.dstMem, dst, Sizeof.cl_double, new long[]{Math.max(srcArrayA.length, srcArrayB.length)}, kernelName);
    }

    /**
     * 获取到内核 获取不到就报错
     *
     * @param kernelName 内核名称
     * @return 对应的内核对象
     */
    private cl_kernel getOrThrow(KernelSource kernelName) {
        final cl_kernel clKernel = this.kernels.get(kernelName.name());
        if (clKernel == null) {
            throw new RuntimeException("Kernel " + kernelName + " not found!");
        }
        return clKernel;
    }

    /**
     * 内核计算
     *
     * @param n                结果数组的长度
     * @param clKernel         计算时要使用的内核对象
     * @param srcMemA          要被计算的数组1
     * @param srcMemB          要被计算的数组2
     * @param dstMem           计算之后的结果存储位
     * @param dst              计算之后的结果存储位
     * @param sizeOf           数组中每个元素所占的大小
     * @param global_work_size 当前要计算的全局尺寸 通常情况下，描述的就是我们的操作数的长宽，一维数组就只有一个元素 代表长度
     * @param kernelName       当前计算操作中 使用的核类型，用于计算工作维度
     */
    private void kernelCalculate(long n, cl_kernel clKernel, cl_mem srcMemA, cl_mem srcMemB, cl_mem dstMem, Pointer dst, int sizeOf, long[] global_work_size, KernelSource kernelName) {
        // 设置内核参数
        int argIndex = 0;
        // cl_mem 是一个类型，代表了 OpenCL 内存对象，它是 OpenCL 中用于在设备（如 GPU 或 CPU）之间传输数据的主要机制。cl_mem 对象可以表示各种类型的内存区域，包括缓冲区（buffers）、图像（images）和其他设备上的内存资源。
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, Pointer.to(srcMemA));
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, Pointer.to(srcMemB));
        clSetKernelArg(clKernel, argIndex, Sizeof.cl_mem, Pointer.to(dstMem));

        // 执行内核
        clEnqueueNDRangeKernel(this.commandQueue, clKernel, kernelName.work_dim_algorithm(global_work_size), null,
                global_work_size, null, 0, null, null);

        // 读取输出数据
        clEnqueueReadBuffer(this.commandQueue, dstMem, CL_TRUE, 0,
                n * sizeOf, dst, 0, null, null);

        // 释放内存对象
        clReleaseMemObject(srcMemA);
        clReleaseMemObject(srcMemB);
        clReleaseMemObject(dstMem);
    }

    /**
     * 释放资源
     */
    public void releaseResources() {
        // 释放内核、程序、命令队列和上下文
        isNotReleased = false;
        this.kernels.forEach((s, cl_kernel) -> clReleaseKernel(cl_kernel));
        this.kernels.clear();
        clReleaseProgram(this.program);
        clReleaseCommandQueue(this.commandQueue);
        clReleaseContext(this.context);
    }

    /**
     * @return 如果返回 true 代表还没有释放 可以使用！
     * <p>
     * If true is returned, it means it has not been released yet and can be used!
     */
    public boolean isNotReleased() {
        return isNotReleased;
    }
}