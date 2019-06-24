package com.xnumberkeyboard.android;

/**
 * 数字键盘的监听事件
 */
public interface OnNumberKeyboardListener {

    /**
     * 数字键盘按键的点击事件
     *
     * @param keyCode keyCode
     * @param insert 输入的文字
     */
    void onNumberKey(int keyCode, String insert);
}
