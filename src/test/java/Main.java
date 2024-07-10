import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

public class Main {
    public static void main(String[] args0) {
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                KernelSource.ARRAY_ADD_ARRAY_INT
        );
        // 准备两个数组
        final int[] srcArrayA = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
        final int[] srcArrayB = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000, 3100, 3200, 3300, 3400, 3500, 3600, 3700};
        // 直接将两个数组进行求和计算 值得一提的是 我们没有准备结果数组的容器 而是直接在回调哈数中将其打印了出来
        easilyOpenJCL.calculate(srcArrayA, srcArrayB, (result) -> {
            for (int i = 0; i < srcArrayA.length; i++) {
                // 值得注意的是，在这里由于我们是直接操作的内存，一个 int 数值占4个字节，所以 i * 4 才可以获取到正确的结果 因为 每 4 个字节读取一次 正好读取到下一个数值
                System.out.println(result.getInt(i * 4));
            }
        }, srcArrayA.length, KernelSource.ARRAY_ADD_ARRAY_INT);
    }
}
