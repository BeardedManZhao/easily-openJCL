import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

public class Main {
    public static void main(String[] args0) {
        // 这个地方的数学表达式 以及下面 for 循环中的 数学表达式 都是相同的，只是为了测试GPU 和 CPU 的计算差异，这个表达式越复杂 计算量就会越大，但数据拷贝量不会有变化，这可能会让 CPU 处于劣势
        final KernelSource kernelSource = new KernelSource(args -> "c[gid] = ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (1024 * a[gid]))))) - ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (1024 * a[gid]))))) + 1;", "double", "MY");
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                kernelSource
        );
        // 这个数组的长度代表的是操作数的数据量 这个数值越大 计算量会成倍增大，同时数据拷贝量也会成倍增加，这将会让 GPU 计算处于劣势
        final double[] a = new double[Integer.MAX_VALUE >> 7];
        for (int i = 0; i < a.length; i++) {
            a[i] = i + 10;
        }
        final double[] b = {2};
        final double[] c = new double[a.length];

        // 热身
        easilyOpenJCL.calculate(a, b, c, kernelSource);

        // 正式计算
        final long l = System.currentTimeMillis();
        easilyOpenJCL.calculate(a, b, byteBuffer -> {
            final long l1 = System.currentTimeMillis();
            System.out.println("GPU计算耗时：" + (l1 - l) + " 第一个元素结果 = " + c[0]);
        }, c.length, kernelSource);

        final long l1 = System.currentTimeMillis();
        for (int gid = 0; gid < a.length; gid++) {
            c[gid] = ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (1024 * a[gid]))))) - ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) + ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - 1024)))) * ((a[gid] / b[0] * a[0] + (a[gid] / 100 * 33 + 210.5 / 5)) * 2) * 2 / 3 + (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (a[gid] * a[gid] - (1024 * a[gid]))))) + 1;
        }
        final long l2 = System.currentTimeMillis();
        System.out.println("CPU计算耗时：" + (l2 - l1) + " 第一个元素结果 = " + c[0]);
    }
}