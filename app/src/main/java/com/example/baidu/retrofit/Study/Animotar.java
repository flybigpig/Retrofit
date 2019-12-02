package com.example.baidu.retrofit.Study;

public class Animotar {

    /*

     区别和特性
        1.  补间动画：只产生了一个动画效果，其真实的坐标并没有发生改变（只是改变了View的显示效果而已，并不会真正的改变View的属性）。

        View做在做动画的时候，它并没有真正的移动它的位置，而是根据动画时间的插值，计算出一个Matrix，然后不停的invalidate，

        在onDraw中的Canvas上使用这个计算出来的Matrix去draw这个View的内容，并有onLayout中还是原来的位置，所以点击事件只能点击到原来的位置才能触发

        2. ObjectAnimator：一般直接用与View，要求作用的View提供该属性（如View的scaleX属性）的getter、setter方法（如setScaleX()方法），

        可以直接改变view的属性所以View的位置也跟随属性的改变而改变，点击事件的触发位置为动画结束的位置。

        3. ValueAnimator：属性动画的核心，这个我理解为数值动画，ObjectAnimator也只不过是通过不断改变的数值然后赋值给相应的属性而已。

        通过设置初始和终点值，ValueAnimator 会通过相应的Interpolator  和duration 计算出平滑的数值变化，然后可以通过得到的Value进行任意操作

     */
}
