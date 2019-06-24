package com.xnumberkeyboard.android;

import android.support.annotation.IntDef;

/**
 * 数字键盘的类型
 */
@IntDef({KeyboardType.number, KeyboardType.digit, KeyboardType.idCard})
public @interface KeyboardType {

    /**
     * 纯数字键盘。
     */
    int number = 1;

    /**
     * 带有小数点的数字键盘。
     */
    int digit = 2;

    /**
     * 身份证键盘。
     */
    int idCard = 3;
}
