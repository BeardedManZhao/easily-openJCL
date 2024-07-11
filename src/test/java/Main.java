import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

public class Main {
    public static void main(String[] args0) {
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                KernelSource.ARRAY_MAX_ARRAY_INT
        );
        // 准备两个数组
        final int[] srcArrayA = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        final int[] srcArrayB = {0, 9, 8, 7, 6, 5, 4, 3, 2};
        // 准备 最大原则 提出出来的新的数组 将会获取到最大的数值 并构成新元素
        easilyOpenJCL.calculate(srcArrayA, srcArrayB, byteBuffer -> {
            for (int i = 0; i < srcArrayA.length; i++) {
                // 在这里直接打印结果
                System.out.print(byteBuffer.getInt() + " ");
            }
        }, srcArrayA.length, KernelSource.ARRAY_MAX_ARRAY_INT);
    }
}
