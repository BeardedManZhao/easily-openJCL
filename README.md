![图片7](https://github.com/BeardedManZhao/easily-openJCL/assets/113756063/41d0b04f-2c02-43a9-8e7e-69bd36702e3c) 
# easily-openJCL

## 什么是 easily-openJCL
通过 Java 调用 GPU 计算的一个库，使用非常简单的API就可以轻松应付 Java 数据类型在 GPU 中的计算操作！easily-openJCL 提供了诸多中计算模式，让我们的计算组件更灵活！

## 为什么要使用 easily-openJCL

### 获取方式简单

您可以直接使用 maven 左边来讲此依赖导入到您的项目中，这是非常快速且方便有效的！下面是依赖的坐标

```xml

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

### 内置的计算模式

我们提供了一些常见的内置计算模式，我们可以通过这些模式实现有效的数据计算操作，接下来的表格中详细介绍了不同的计算模式信息！

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

            // 准备两个 double 数组 第二个整形数组只有一个元素 因为 ARRAY_MUL_NUMBER 元素代表的就是 数组和一个元素进行计算
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

```
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

### 自定义计算模式

