import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.NameMemSpace;
import io.github.BeardedManZhao.easilyJopenCL.kernel.LengthKernelSource;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import top.lingyuzhao.varFormatter.core.VarFormatter;
import top.lingyuzhao.varFormatter.utils.DataObj;

public class Main {
    public static void main(String[] args0) {
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                LengthKernelSource.ARRAY_DECODE_XOR_ARRAY_CHAR2,
                LengthKernelSource.ARRAY_ENCODE_XOR_ARRAY_CHAR2
        );
        // 准备两个数组 作为 A_space 空间的内存
        final char[] a = new char[10];
        final char[] b = "3009088782343454567566t57".toCharArray();
        // 将 a b 数组绑定到带名字的内存空间 A_space
        final NameMemSpace memSpace = easilyOpenJCL.createMemSpace(Pointer.to(a), Pointer.to(b), a.length, b.length, a.length, Sizeof.cl_char2, LengthKernelSource.ARRAY_ENCODE_XOR_ARRAY_CHAR2, "A_space");

        // 准备第二个数组 作为 B_space 中的内存
        final char[] d = new char[a.length];
        // 引用 A_space 中 的 a， 这里存的是其中是 a d 数组（为 null 的就代表使用原空间的引用）
        NameMemSpace merge0 = memSpace.merge(null, Pointer.to(d), -1, d.length, d.length);
        // 对合并之后的内存空间重命名为 B_space
        merge0.setMemSpaceName("B_space");

        // 准备第三个数组 作为 C_space 中的内存
        final char[] e = new char[a.length];
        // 引用 A_space 中 的 a， 这里存的是其中是 a d 数组（为 null 的就代表使用原空间的引用）
        NameMemSpace merge1 = merge0.merge(null, Pointer.to(e), -1, e.length, e.length);
        // 对合并之后的内存空间重命名为 C_space
        merge1.setMemSpaceName("C_space");

        // 准备第四个数组 作为 D_space 中的内存
        final char[] f = new char[a.length];
        // 引用 A_space 中 的 a， 这里存的是其中是 f d 数组（为 null 的就代表使用原空间的引用）
        NameMemSpace merge2 = merge1.merge(Pointer.to(f), null, f.length, -1, e.length);
        // 对合并之后的内存空间重命名为 C_space
        merge1.setMemSpaceName("D_space");

        // 尝试将第三个进行 explain
        DataObj explain = merge2.explain();
        // 直接进行绘图 图中可看到此内存空间引用的所有空间
        explain.setNameJoin(false);
        String format = VarFormatter.MERMAID.getFormatter(true).format(explain);
        System.out.println(format);
    }
}