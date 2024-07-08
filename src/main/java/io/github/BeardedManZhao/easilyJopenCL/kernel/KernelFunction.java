package io.github.BeardedManZhao.easilyJopenCL.kernel;

/**
 * 内核计算函数实现组件
 */
public interface KernelFunction {

    /**
     * 内核计算函数实现逻辑
     *
     * @param args 内核计算函数实现逻辑所需参数，不同的 KernelSource 类型组件所需的参数不同 可以在 KernelSource 中的注释中查看！
     *             <p>
     *             The parameters required for implementing logic in kernel computing functions are different for different KernelSource type components, which can be viewed in the comments in KernelSource!
     * @return 使用 args 拼接的 kernelSource 代码。
     * <p>
     * Use kernelSource code concatenated with args.
     */
    String calculateKernelSource(String... args);

}
