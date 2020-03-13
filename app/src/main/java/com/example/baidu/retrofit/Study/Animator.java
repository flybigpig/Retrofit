package com.example.baidu.retrofit.Study;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Scroller;

/**
 * @author
 * @date 2020/3/13.
 * GitHub：
 * email：
 * description：
 * /*
 * <p>
 *  区别和特性
 * 1.  补间动画：只产生了一个动画效果，其真实的坐标并没有发生改变（只是改变了View的显示效果而已，并不会真正的改变View的属性）。
 * <p>
 * View做在做动画的时候，它并没有真正的移动它的位置，而是根据动画时间的插值，计算出一个Matrix，然后不停的invalidate，
 * <p>
 * 在onDraw中的Canvas上使用这个计算出来的Matrix去draw这个View的内容，并有onLayout中还是原来的位置，所以点击事件只能点击到原来的位置才能触发
 * <p>
 * 2. ObjectAnimator：一般直接用与View，要求作用的View提供该属性（如View的scaleX属性）的getter、setter方法（如setScaleX()方法），
 * <p>
 * 可以直接改变view的属性所以View的位置也跟随属性的改变而改变，点击事件的触发位置为动画结束的位置。
 * <p>
 * 3. ValueAnimator：属性动画的核心，这个我理解为数值动画，ObjectAnimator也只不过是通过不断改变的数值然后赋值给相应的属性而已。
 * <p>
 * 通过设置初始和终点值，ValueAnimator 会通过相应的Interpolator  和duration 计算出平滑的数值变化，然后可以通过得到的Value进行任意操作
 */
public class Animator {

    private Scroller mScroller;

    /**
     * 透明度
     *
     * @param view
     */
    private void voidstartAlphaAnim(View view) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1.0f);

        animator.setDuration(3000);

        animator.setRepeatCount(-1);

        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.start();

    }

    /**
     * 位移
     *
     * @param mView
     */
    private void startTranslationAnimtor(View mView) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, "TranslationX", 0, 500);

        animator.setDuration(3000);

        animator.setRepeatCount(-1);

        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.start();

    }

    /**
     * 缩放
     *
     * @param view
     */
    public static void playAnimationDaShang(View view) {
        AnimatorSet animatorSetPeople = new AnimatorSet();  //多个动画 动画集
        animatorSetPeople.setDuration(1000);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -110, 110, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 2, 1);//从原始状态放大2倍再回到原始状态
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 2, 1);
        translationX.setRepeatCount(-1);//设置动画重复次数
        translationX.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式
        translationX.setStartDelay(1000);//动画延时执行
        translationX.setInterpolator(new AccelerateInterpolator());//Interpolator可以定义动画播放的速度
        /*
            after(Animator anim) 将现有动画插入到传入的动画之后执行
            after(long delay) 将现有动画延迟指定毫秒后执行
            before(Animator anim) 将现有动画插入到传入的动画之前执行
            with(Animator anim) 将现有动画和传入的动画同时执行
          */
        animatorSetPeople.play(translationX).before(scaleX).before(scaleY);
        // animatorSetPeople.playTogether(translationX, scaleX, scaleY);
        animatorSetPeople.start();
        animatorSetPeople.end();
        animatorSetPeople.cancel();
    }

    /**
     * 旋转动画
     */

    private void startRotationAnimtor(View mView) {

        ObjectAnimator rotation = ObjectAnimator.ofFloat(mView, "Rotation", 0, 180);

        // rotation.setRepeatCount(-1);

        rotation.setRepeatMode(ValueAnimator.REVERSE);

        rotation.setDuration(2000);

        rotation.start();

    }
}
