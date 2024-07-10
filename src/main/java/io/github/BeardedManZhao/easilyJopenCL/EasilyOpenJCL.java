package io.github.BeardedManZhao.easilyJopenCL;

import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;
import org.jocl.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

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

    /**
     * 初始化OpenCL环境并返回一个EasilyOpenJCL操作实例，用于执行OpenCL计算任务。值得注意的是，此函数将会使用默认的OpenCL平台和默认的设备类型。您可以调用 {@code initOpenCLEnvironment(int platformIndex, long deviceType, int deviceIndex, KernelSource... kernelSources)} 方法来选择其他平台或设备类型。
     *
     * @param kernelSources 内核源对象数组 - 包含要编译和加载的OpenCL内核源代码。
     *                      这些内核将在选定的设备上运行，选择了哪些，则返回的 EasilyOpenJCL 就可以支持哪些内核计算。
     * @return EasilyOpenJCL 操作对象 - 提供了对OpenCL上下文、命令队列和内核的访问，
     * 允许用户执行异步计算任务。
     */
    public static EasilyOpenJCL initOpenCLEnvironment(KernelSource... kernelSources) {
        return initOpenCLEnvironment((c) -> 0, CL_DEVICE_TYPE_ALL, (c) -> 0, kernelSources);
    }

    /**
     * 初始化OpenCL环境并返回一个EasilyOpenJCL操作实例，用于执行OpenCL计算任务。
     *
     * @param platformIndex 实现此函数，返回的应是 平台索引 - 用于指定从哪些可用的OpenCL平台上进行选择。
     *                      OpenCL平台通常代表了不同的硬件供应商，例如Intel、AMD或NVIDIA，
     *                      并且每个平台可能支持不同版本的OpenCL标准。
     * @param deviceType    设备类型 - 用于指定所选平台上的设备类别。
     *                      设备类型可以是CPU、GPU或其他类型的加速器。
     *                      可以通过枚举{@code CL_DEVICE_TYPE_ALL}、{@code CL_DEVICE_TYPE_CPU}、
     *                      {@code CL_DEVICE_TYPE_GPU}或它们的组合来指定。
     * @param deviceIndex   实现此函数，返回的应是 设备索引 - 在给定的平台和设备类型下，用于选择具体的设备。
     *                      如果同一平台上有多个相同类型的设备，此参数用于区分它们。
     * @param kernelSources 内核源对象数组 - 包含要编译和加载的OpenCL内核源代码。
     *                      这些内核将在选定的设备上运行，选择了哪些，则返回的 EasilyOpenJCL 就可以支持哪些内核计算。
     * @return EasilyOpenJCL 操作对象 - 提供了对OpenCL上下文、命令队列和内核的访问，
     * 允许用户执行异步计算任务。
     */
    public static EasilyOpenJCL initOpenCLEnvironment(Function<cl_platform_id[], Integer> platformIndex, long deviceType, Function<cl_device_id[], Integer> deviceIndex, KernelSource... kernelSources) {
        cl_context context;
        cl_command_queue commandQueue;
        // 启用异常处理，从而省略后续的错误检查
        CL.setExceptionsEnabled(true);

        // 获取平台的数量
        int[] numPlatformsArray = new int[1];
        clGetPlatformIDs(0, null, numPlatformsArray);
        int numPlatforms = numPlatformsArray[0];

        // 获取平台ID
        cl_platform_id[] platforms = new cl_platform_id[numPlatforms];
        clGetPlatformIDs(platforms.length, platforms, null);
        cl_platform_id platform = platforms[platformIndex.apply(platforms)];

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
        cl_device_id device = devices[deviceIndex.apply(devices)];

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
        calculate(srcArrayA, srcArrayB, byteBuffer -> byteBuffer.asIntBuffer().get(dstArray), dstArray.length, kernelName);
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
        calculate(srcArrayA, srcArrayB, byteBuffer -> byteBuffer.asFloatBuffer().get(dstArray), dstArray.length, kernelName);
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
        calculate(srcArrayA, srcArrayB, byteBuffer -> byteBuffer.asDoubleBuffer().get(dstArray), dstArray.length, kernelName);
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
     * @param resultFunc 结果处理函数，此函数会在计算成功之后，将内存中的结果映射到ByteBuffer中，我们可以通过这个函数直接操作结果，有效的避免拷贝
     *                   <p>
     *                   Result processing function, which maps the results in memory to ByteBuffer after successful calculation. We can directly manipulate the results through this function, effectively avoiding copying
     * @param dstArrayLength 结果数组的长度
     *                       <p>
     *                       The length of the result array
     * @param kernelName 本次计算要使用的内核名称
     *                   <p>
     *                   The kernel name to be used in this calculation
     */
    public void calculate(int[] srcArrayA, int[] srcArrayB, Consumer<ByteBuffer> resultFunc, long dstArrayLength, KernelSource kernelName) {
        final cl_kernel clKernel = getOrThrow(kernelName);
        kernelCalculate(srcArrayA.length, srcArrayB.length, resultFunc, dstArrayLength, kernelName, Pointer.to(srcArrayA), Pointer.to(srcArrayB), clKernel, Sizeof.cl_int);
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
     * @param resultFunc 结果处理函数，此函数会在计算成功之后，将内存中的结果映射到ByteBuffer中，我们可以通过这个函数直接操作结果，有效的避免拷贝
     *                   <p>
     *                   Result processing function, which maps the results in memory to ByteBuffer after successful calculation. We can directly manipulate the results through this function, effectively avoiding copying
     * @param dstArrayLength 结果数组的长度
     *                       <p>
     *                       The length of the result array
     * @param kernelName 本次计算要使用的内核名称
     *                   <p>
     *                   The kernel name to be used in this calculation
     */
    public void calculate(float[] srcArrayA, float[] srcArrayB, Consumer<ByteBuffer> resultFunc, long dstArrayLength, KernelSource kernelName) {
        final cl_kernel clKernel = getOrThrow(kernelName);
        kernelCalculate(srcArrayA.length, srcArrayB.length, resultFunc, dstArrayLength, kernelName, Pointer.to(srcArrayA), Pointer.to(srcArrayB), clKernel, Sizeof.cl_float);
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
     * @param resultFunc 结果处理函数，此函数会在计算成功之后，将内存中的结果映射到ByteBuffer中，我们可以通过这个函数直接操作结果，有效的避免拷贝
     *                   <p>
     *                   Result processing function, which maps the results in memory to ByteBuffer after successful calculation. We can directly manipulate the results through this function, effectively avoiding copying
     * @param dstArrayLength 结果数组的长度
     *                       <p>
     *                       The length of the result array
     * @param kernelName 本次计算要使用的内核名称
     *                   <p>
     *                   The kernel name to be used in this calculation
     */
    public void calculate(double[] srcArrayA, double[] srcArrayB, Consumer<ByteBuffer> resultFunc, long dstArrayLength, KernelSource kernelName) {
        final cl_kernel clKernel = getOrThrow(kernelName);
        kernelCalculate(srcArrayA.length, srcArrayB.length, resultFunc, dstArrayLength, kernelName, Pointer.to(srcArrayA), Pointer.to(srcArrayB), clKernel, Sizeof.cl_double);
    }

    /**
     * 调用显卡 进行计算数组与数组之间的计算操作
     *
     * @param srcArrayA      需要被计算的数组1 的长度
     * @param srcArrayB      需要被计算的数组2 的长度
     * @param resultFunc     结果处理函数，此函数会在计算成功之后，将内存中的结果映射到ByteBuffer中，我们可以通过这个函数直接操作结果，有效的避免拷贝
     * @param dstArrayLength 结果长度
     * @param kernelName     本次计算要使用的内核名称
     * @param p1             数组1的内存地址
     * @param p2             数组2的内存地址
     * @param clKernel       获取到的计算内核
     * @param sizeOf         数组1和数组2中元素的大小
     */
    private void kernelCalculate(long srcArrayA, long srcArrayB, Consumer<ByteBuffer> resultFunc, long dstArrayLength, KernelSource kernelName, Pointer p1, Pointer p2, cl_kernel clKernel, int sizeOf) {
        final MemSpace memSpace = new MemSpace(this.context, p1, p2, srcArrayA, srcArrayB, dstArrayLength, sizeOf);
        kernelCalculateWithMapping(srcArrayA, clKernel, memSpace.srcMemA, memSpace.srcMemB, memSpace.dstMem, sizeOf, new long[]{Math.max(srcArrayA, srcArrayB)}, kernelName, resultFunc);
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
     * @param sizeOf           数组中每个元素所占的大小
     * @param global_work_size 当前要计算的全局尺寸 通常情况下，描述的就是我们的操作数的长宽，一维数组就只有一个元素 代表长度
     * @param kernelName       当前计算操作中 使用的核类型，用于计算工作维度
     * @param resultFunc       结果处理函数，此函数会在计算成功之后，将内存中的结果映射到ByteBuffer中，我们可以通过这个函数直接操作结果，有效的避免拷贝
     *                         <p>
     *                         Result processing function, which maps the results in memory to ByteBuffer after successful calculation. We can directly manipulate the results through this function, effectively avoiding copying
     */
    private void kernelCalculateWithMapping(long n, cl_kernel clKernel, cl_mem srcMemA, cl_mem srcMemB, cl_mem dstMem,
                                            int sizeOf, long[] global_work_size, KernelSource kernelName,
                                            Consumer<ByteBuffer> resultFunc) {
        // 设置内核参数
        int argIndex = 0;
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, Pointer.to(srcMemA));
        clSetKernelArg(clKernel, argIndex++, Sizeof.cl_mem, Pointer.to(srcMemB));
        clSetKernelArg(clKernel, argIndex, Sizeof.cl_mem, Pointer.to(dstMem));

        // 执行内核并获取事件
        cl_event kernelEvent = new cl_event();
        clEnqueueNDRangeKernel(this.commandQueue, clKernel, kernelName.work_dim_algorithm(global_work_size), null,
                global_work_size, null, 0, null, kernelEvent);

        // 映射输出数据缓冲区到主机内存，使用内核事件作为依赖
        // 映射内存对象以拷贝数据
        ByteBuffer dstMapped = clEnqueueMapBuffer(this.commandQueue, dstMem, CL_TRUE, CL_MAP_READ, 0,
                n * sizeOf, 1, new cl_event[]{kernelEvent}, null, null);

        dstMapped.order(ByteOrder.nativeOrder());

        // 等待内核执行完成，虽然在这里调用clWaitForEvents不是必须的，
        // 因为映射操作已经依赖于内核事件，但保留此行可以提供额外的安全性
        clWaitForEvents(1, new cl_event[]{kernelEvent});

        // 使用映射后的数据
        resultFunc.accept(dstMapped);

        // 解映射缓冲区
        clEnqueueUnmapMemObject(this.commandQueue, dstMem, dstMapped, 0, null, null);

        // 释放内存对象和事件
        clReleaseMemObject(srcMemA);
        clReleaseMemObject(srcMemB);
        clReleaseMemObject(dstMem);
        clReleaseEvent(kernelEvent);
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