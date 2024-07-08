import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 准备一个 显卡计算组件！ 在其中的结尾部分加上我们要使用的计算模式
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                // 计算模式 这里是 float 加法和减法
                KernelSource.ARRAY_ADD_NUMBER_FLOAT, KernelSource.ARRAY_SUB_NUMBER_FLOAT,
                // 还有 double 的乘法和除法
                KernelSource.ARRAY_MUL_NUMBER_DOUBLE, KernelSource.ARRAY_DIV_NUMBER_DOUBLE
        );

        // 判断是否已经释放 如果没有释放才可以继续操作
        if (easilyOpenJCL.isNotReleased()) {
            // 准备两个 float 数组 第二个整形数组只有一个元素 因为 ARRAY_ADD_NUMBER_* 模式代表的就是 数组和一个元素进行 ADD 计算
            // 这里代表 srcArrayA 的元素 每个都加 10
            final float[] srcArrayA = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            final float[] srcArrayB = new float[]{10};
            final float[] dstArray = new float[srcArrayA.length];
            // 直接开始 使用 ARRAY_ADD_NUMBER_FLOAT 模式计算
            easilyOpenJCL.calculate(srcArrayA, srcArrayB, dstArray, KernelSource.ARRAY_ADD_NUMBER_FLOAT);
            // 获取到结果
            System.out.println(Arrays.toString(dstArray));

            System.out.println("================");

            // 准备两个 double 数组 第二个整形数组只有一个元素 因为 ARRAY_MUL_NUMBER_* 模式代表的就是 数组和一个元素进行 MUL 计算
            // 这里代表 srcArrayA1 的元素 每个都乘 2
            final double[] srcArrayA1 = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            final double[] srcArrayB1 = new double[]{2};
            final double[] dstArray1 = new double[srcArrayA1.length];
            // 直接开始 使用 ARRAY_MUL_NUMBER_DOUBLE 模式计算
            easilyOpenJCL.calculate(srcArrayA1, srcArrayB1, dstArray1, KernelSource.ARRAY_MUL_NUMBER_DOUBLE);
            // 获取到结果
            System.out.println(Arrays.toString(dstArray1));
        }
        // 释放组件
        easilyOpenJCL.releaseResources();
    }
}