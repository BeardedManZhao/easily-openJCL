import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

import java.util.Arrays;

import static org.jocl.CL.CL_DEVICE_TYPE_GPU;

public class Main {
    public static void main(String[] args) {
        // 准备一个 显卡计算组件！这里我们的构造函数多了两个操作
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                // 在这里我们可以使用函数的方式实现获取平台了！
                clPlatformIds -> {
                    // 输入参数是所有平台的对象组成的数组 我们在这里直接返回要使用的平台的索引就可以咯！
                    System.out.println("Platforms: " + Arrays.toString(clPlatformIds));
                    return 0;
                },
                // 在这里设置的是设备类型哦
                CL_DEVICE_TYPE_GPU,
                // 在这里我们可以使用函数的方式实现获取设备了！
                clDeviceIds -> {
                    // 输入参数是所有设备的对象组成的数组 我们在这里直接返回要使用的设备的索引就可以咯！
                    System.out.println("Devices: " + Arrays.toString(clDeviceIds));
                    return 0;
                },
                // 后面就没有什么区别了
                KernelSource.ARRAY_ADD_NUMBER_FLOAT, KernelSource.ARRAY_SUB_NUMBER_FLOAT,
                KernelSource.ARRAY_MUL_NUMBER_DOUBLE, KernelSource.ARRAY_DIV_NUMBER_DOUBLE
        );

        if (easilyOpenJCL.isNotReleased()) {
            final float[] srcArrayA = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            final float[] srcArrayB = new float[]{10};
            final float[] dstArray = new float[srcArrayA.length];
            easilyOpenJCL.calculate(srcArrayA, srcArrayB, dstArray, KernelSource.ARRAY_ADD_NUMBER_FLOAT);
            System.out.println(Arrays.toString(dstArray));
        }
        easilyOpenJCL.releaseResources();
    }
}