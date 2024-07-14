import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.MemSpace;
import io.github.BeardedManZhao.easilyJopenCL.kernel.LengthKernelSource;
import org.jocl.Pointer;
import org.jocl.Sizeof;

public class Main {
    public static void main(String[] args0) {
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                LengthKernelSource.ARRAY_DECODE_XOR_ARRAY_CHAR2,
                LengthKernelSource.ARRAY_ENCODE_XOR_ARRAY_CHAR2
        );
        // 这个数组的长度代表的是操作数的数据量 这个数值越大 计算量会成倍增大，同时数据拷贝量也会成倍增加，这将会让 GPU 计算处于劣势
        final char[] a = new char[Integer.MAX_VALUE >> 5];
        final char[] b = "3009088782343454567566t57".toCharArray();
        final char[] c = new char[a.length];
        // 为 a 生成很多随机字母
        for (int gid = 0; gid < a.length; gid++) {
            a[gid] = (char) (((Math.random() * 100) % 26) + 46);
        }
        // 设置 a 的第2个元素为 A
        a[1] = 'A';
        // 获取到内存空间
        final MemSpace memSpace = easilyOpenJCL.createMemSpace(Pointer.to(a), Pointer.to(b), a.length, b.length, c.length, Sizeof.cl_char2, LengthKernelSource.ARRAY_ENCODE_XOR_ARRAY_CHAR2);

        // 热身
        easilyOpenJCL.calculate(a, b, c, LengthKernelSource.ARRAY_ENCODE_XOR_ARRAY_CHAR2);

        // 正式计算 使用 b 密钥 对 a 进行异或加密操作
        final long l = System.currentTimeMillis();
        easilyOpenJCL.calculate(byteBuffer -> {
            final long l1 = System.currentTimeMillis();
            System.out.println("GPU加密耗时：" + (l1 - l) + " 第2个元素 【" + byteBuffer.getChar(2) + '】');
        }, memSpace, false);

        final long l1 = System.currentTimeMillis();
        for (int gid = 0; gid < a.length; gid++) {
            c[gid] = (char) (a[gid] ^ b[gid % b.length]);
        }
        final long l2 = System.currentTimeMillis();
        System.out.println("CPU加密耗时：" + (l2 - l1) + " 第2个元素 【" + c[1] + '】');

        // 进行解密校验 以及 释放资源
        // 首先将源内存空间中的数据和新数据合并到一个空间 这个空间中 a b 是操作数！
        final MemSpace merge = memSpace.merge(Pointer.to(c), null, c.length, -1, a.length);
        // 修改内存空间要使用的计算组件 这里改为解密组件！
        merge.setKernelSource(LengthKernelSource.ARRAY_DECODE_XOR_ARRAY_CHAR2);
        // 开始进行解密操作 并打印出结果！
        easilyOpenJCL.calculate(byteBuffer -> System.out.println("经过解密，a 第2个元素结果 = " + byteBuffer.getChar(2)), merge, true);
        System.out.println("实际上 a 第2个元素结果为：" + a[1]);
    }
}