![图片7](https://github.com/BeardedManZhao/easily-openJCL/assets/113756063/41d0b04f-2c02-43a9-8e7e-69bd36702e3c) 
# easily-openJCL

## 什么是 easily-openJCL

easily-openJCL 是一个轻量级的 Java 语言下的 GPU 显卡 计算库，它提供了一套简单易用的 API，让用户能够轻松实现 GPU 计算操作。

通过 Java 调用 GPU 计算的一个库，使用非常简单的API就可以轻松应付 Java 数据类型在 GPU 中的计算操作！easily-openJCL 提供了诸多中计算模式，让我们的计算组件更灵活！

## 为什么要使用 easily-openJCL

### 获取方式简单

您可以直接使用 maven 坐标来把此依赖导入到您的项目中，这是非常快速且方便有效的！下面是依赖的坐标

```xml

<dependencies>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>easily-openJCL</artifactId>
        <version>1.0.1</version>
    </dependency>
</dependencies>
```

### 非常简单的使用

您无需关注一些底层的显存调用，且内置了一些计算内核，若这些已有的计算内核能够满足您，您甚至都不需要去关心计算的实现！下面是一个简单且通用的示例，将两个数组对应元素进行乘法计算，实例中有详细的注释，应该可以让您了解如何使用 `easilyOpenJCL`!

> 值得注意的是 `easilyOpenJCL.calculate` 操作并不会检查您的参数是否符合要求，因为并不是所有的计算模式都必须要满足 操作数的长度相同 的前提！

```java
import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 准备一个 显卡计算组件！ 在其中的结尾部分加上我们要使用的计算模式
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                // 在这里我们要做的就是为计算器装载内核，每个内核就是一种计算模式，计算组件装载了哪种模式 它就可以使用哪种计算模式。
                // 计算模式的名称格式为 "第一个操作数_操作类型_第二个操作数_操作数类型"
                // 例如第一个就是 两个float数组进行对应元素求和
                KernelSource.ARRAY_ADD_ARRAY_FLOAT,
                // 第二个就是 float数组和float数 进行对应元素求和（注意这里不是数组，是数）
                KernelSource.ARRAY_ADD_NUMBER_FLOAT
        );

        // 判断是否已经释放 如果没有释放才可以继续操作
        if (easilyOpenJCL.isNotReleased()) {
            // 准备数据 前两个是操作数 第三个是结果存储容器数组
            float[] srcArrayA = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            float[] srcArrayB = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            float[] dstArray = new float[srcArrayA.length];

            // 开始计算 在这里我们要指定好模式！
            easilyOpenJCL.calculate(srcArrayA, srcArrayB, dstArray, KernelSource.ARRAY_ADD_ARRAY_FLOAT);
            // 计算结果
            System.out.println(Arrays.toString(dstArray));
        }

        // 最后释放 值得一提的是 easilyOpenJCL 在没有释放前，calculate 函数可以无限次的调用
        easilyOpenJCL.releaseResources();
    }
}
```

## 更加详细的文档

您可以在这个章节中了解到更详细的文档哦~~~ 其中介绍了计算模式，介绍了自定义实现计算内核等知识！

### 繁多的内置计算模式

我们提供了一些常见的内置计算模式，我们可以通过这些模式实现有效的数据计算操作，接下来的表格中详细介绍了不同的计算模式信息！

| 计算模式名称                  | 计算模式支持版本 | 操作数长度规则     | 计算组件解释                       |
|-------------------------|----------|-------------|------------------------------|
| ARRAY_ADD_ARRAY_INT     | v1.0     | 两个操作数一致     | 两个 int 数组之间进行加法计算            |
| ARRAY_SUB_ARRAY_INT     | v1.0     | 两个操作数一致     | 两个 int 数组之间进行减法计算            |
| ARRAY_MUL_ARRAY_INT     | v1.0     | 两个操作数一致     | 两个 int 数组之间进行乘法计算            |
| ARRAY_DIV_ARRAY_INT     | v1.0     | 两个操作数一致     | 两个 int 数组之间进行除法计算            |
| ARRAY_LS_ARRAY_INT      | v1.0     | 两个操作数一致     | 两个 int 数组之间进行左移计算            |
| ARRAY_RS_ARRAY_INT      | v1.0     | 两个操作数一致     | 两个 int 数组之间进行右移计算            |
| ARRAY_ADD_ARRAY_FLOAT   | v1.0     | 两个操作数一致     | 两个 float 数组之间进行加法计算          |
| ARRAY_SUB_ARRAY_FLOAT   | v1.0     | 两个操作数一致     | 两个 float 数组之间进行减法计算          |
| ARRAY_MUL_ARRAY_FLOAT   | v1.0     | 两个操作数一致     | 两个 float 数组之间进行乘法计算          |
| ARRAY_DIV_ARRAY_FLOAT   | v1.0     | 两个操作数一致     | 两个 float 数组之间进行除法计算          |
| ARRAY_LS_ARRAY_FLOAT    | v1.0     | 两个操作数一致     | 两个 float 数组之间进行左移计算          |
| ARRAY_RS_ARRAY_FLOAT    | v1.0     | 两个操作数一致     | 两个 float 数组之间进行右移计算          |
| ARRAY_ADD_ARRAY_DOUBLE  | v1.0     | 两个操作数一致     | 两个 double 数组之间进行加法计算         |
| ARRAY_SUB_ARRAY_DOUBLE  | v1.0     | 两个操作数一致     | 两个 double 数组之间进行减法计算         |
| ARRAY_MUL_ARRAY_DOUBLE  | v1.0     | 两个操作数一致     | 两个 double 数组之间进行乘法计算         |
| ARRAY_DIV_ARRAY_DOUBLE  | v1.0     | 两个操作数一致     | 两个 double 数组之间进行除法计算         |
| ARRAY_LS_ARRAY_DOUBLE   | v1.0     | 两个操作数一致     | 两个 double 数组之间进行左移计算         |
| ARRAY_RS_ARRAY_DOUBLE   | v1.0     | 两个操作数一致     | 两个 double 数组之间进行右移计算         |
| ARRAY_ADD_NUMBER_INT    | v1.0     | 第二个操作数为1个元素 | int 数组和 int数值 之间进行加法计算       |
| ARRAY_SUB_NUMBER_INT    | v1.0     | 第二个操作数为1个元素 | int 数组和 int数值 之间进行加法计算       |
| ARRAY_MUL_NUMBER_INT    | v1.0     | 第二个操作数为1个元素 | int 数组和 int数值 之间进行加法计算       |
| ARRAY_DIV_NUMBER_INT    | v1.0     | 第二个操作数为1个元素 | int 数组和 int数值 之间进行加法计算       |
| ARRAY_LS_NUMBER_INT     | v1.0     | 第二个操作数为1个元素 | int 数组和 int数值 之间进行加法计算       |
| ARRAY_RS_NUMBER_INT     | v1.0     | 第二个操作数为1个元素 | int 数组和 int数值 之间进行加法计算       |
| ARRAY_ADD_NUMBER_FLOAT  | v1.0     | 第二个操作数为1个元素 | float 数组和 float数值 之间进行加法计算   |
| ARRAY_SUB_NUMBER_FLOAT  | v1.0     | 第二个操作数为1个元素 | float 数组和 float数值 之间进行加法计算   |
| ARRAY_MUL_NUMBER_FLOAT  | v1.0     | 第二个操作数为1个元素 | float 数组和 float数值 之间进行加法计算   |
| ARRAY_DIV_NUMBER_FLOAT  | v1.0     | 第二个操作数为1个元素 | float 数组和 float数值 之间进行加法计算   |
| ARRAY_LS_NUMBER_FLOAT   | v1.0     | 第二个操作数为1个元素 | float 数组和 float数值 之间进行加法计算   |
| ARRAY_RS_NUMBER_FLOAT   | v1.0     | 第二个操作数为1个元素 | float 数组和 float数值 之间进行加法计算   |
| ARRAY_ADD_NUMBER_DOUBLE | v1.0     | 第二个操作数为1个元素 | double 数组和 double数值 之间进行加法计算 |
| ARRAY_SUB_NUMBER_DOUBLE | v1.0     | 第二个操作数为1个元素 | double 数组和 double数值 之间进行加法计算 |
| ARRAY_MUL_NUMBER_DOUBLE | v1.0     | 第二个操作数为1个元素 | double 数组和 double数值 之间进行加法计算 |
| ARRAY_DIV_NUMBER_DOUBLE | v1.0     | 第二个操作数为1个元素 | double 数组和 double数值 之间进行加法计算 |
| ARRAY_LS_NUMBER_DOUBLE  | v1.0     | 第二个操作数为1个元素 | double 数组和 double数值 之间进行加法计算 |
| ARRAY_RS_NUMBER_DOUBLE  | v1.0     | 第二个操作数为1个元素 | double 数组和 double数值 之间进行加法计算 |

#### 数组与数组的计算模式

```java
import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 准备一个 显卡计算组件！ 在其中的结尾部分加上我们要使用的计算模式
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                // 计算模式 这里是 float 加法和减法
                KernelSource.ARRAY_ADD_ARRAY_FLOAT, KernelSource.ARRAY_SUB_ARRAY_FLOAT,
                // 还有 double 的乘法和除法
                KernelSource.ARRAY_MUL_ARRAY_DOUBLE, KernelSource.ARRAY_DIV_ARRAY_DOUBLE
        );

        // 判断是否已经释放 如果没有释放才可以继续操作
        if (easilyOpenJCL.isNotReleased()) {
            // 准备两个数组
            final float[] srcArrayA = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            final float[] srcArrayB = new float[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
            final float[] dstArray = new float[srcArrayA.length];
            // 直接开始 使用 ARRAY_ADD_ARRAY_FLOAT 模式计算
            easilyOpenJCL.calculate(srcArrayA, srcArrayB, dstArray, KernelSource.ARRAY_ADD_ARRAY_FLOAT);
            // 获取到结果
            System.out.println(Arrays.toString(dstArray));

            System.out.println("================");

            // 准备两个 double 数组
            final double[] srcArrayA1 = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            final double[] srcArrayB1 = new double[]{2, 4, 2, 4, 2, 4, 2, 4, 2, 4};
            final double[] dstArray1 = new double[srcArrayA1.length];
            // 直接开始 使用 ARRAY_MUL_ARRAY_DOUBLE 模式计算
            easilyOpenJCL.calculate(srcArrayA1, srcArrayB1, dstArray1, KernelSource.ARRAY_MUL_ARRAY_DOUBLE);
            // 获取到结果
            System.out.println(Arrays.toString(dstArray1));
        }
        // 释放组件
        easilyOpenJCL.releaseResources();
    }
}
```
下面是计算结果
```
[11.0, 22.0, 33.0, 44.0, 55.0, 66.0, 77.0, 88.0, 99.0, 110.0]
================
[2.0, 8.0, 6.0, 16.0, 10.0, 24.0, 14.0, 32.0, 18.0, 40.0]
```
#### 数组与数值的计算模式

```java
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
```

下面是计算结果

```
[11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0]
================
[2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0]
```
### 自定义计算模式

```java
import io.github.BeardedManZhao.easilyJopenCL.EasilyOpenJCL;
import io.github.BeardedManZhao.easilyJopenCL.kernel.KernelSource;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 自定义的实现一个计算模式
        final KernelSource kernelSourceUDF = new KernelSource(
                // args1[0]=数组1的引用对象  args1[1]=数组2的引用对象  args1[2]=当前计算操作位于的索引  args1[3]=结果数组的引用对象
                // 这里是 结果数组[0] = 数组1[i] + 数组2[i] + 1
                // 第二个参数代表的是该计算模式 可计算的类型为 int
                // 第三个参数代表的是该计算模式的名称
                args1 -> String.format("%s[%s] = %s[%s] + %s[%s] + 1;", args1[3], args1[2], args1[0], args1[2], args1[1], args1[2]), "int", "MyUdfModel"
        );

        // 准备一个 显卡计算组件！ 在其中的结尾部分加上我们要使用的计算模式
        final EasilyOpenJCL easilyOpenJCL = EasilyOpenJCL.initOpenCLEnvironment(
                // 在这里我们直接将自己实现的计算组件传递进来
                kernelSourceUDF
        );

        // 判断是否已经释放 如果没有释放才可以继续操作
        if (easilyOpenJCL.isNotReleased()) {
            // 这里代表 srcArrayA 和 srcArrayB 的相同索引元素相加 最后加1 实现赋值操作
            final int[] srcArrayA = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            final int[] srcArrayB = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 11};
            final int[] dstArray = new int[srcArrayA.length];
            // 直接开始 使用我们自己实现的 kernelSourceUDF 模式计算
            easilyOpenJCL.calculate(srcArrayA, srcArrayB, dstArray, kernelSourceUDF);
            // 获取到结果
            System.out.println(Arrays.toString(dstArray));
        }
        // 释放组件
        easilyOpenJCL.releaseResources();
    }
}
```
这是计算结果
```
[3, 5, 7, 9, 11, 13, 15, 17, 19, 22]
```

## 更新记录

### 2024-07-10 1.0.1 版本正在开发中

- 在本次更新中，我们针对获取到 EasilyOpenJCL 实例的函数进行了优化，允许使用者自己来决定要使用的平台和设备等信息，下面是关于该函数的使用示例。

```java
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
```
