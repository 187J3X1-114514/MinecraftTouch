#include <Windows.h>
#include <jni.h>
#include <winuser.h>
#include "WindowsTouch.h"

#define TOUCH_COORD_TO_PIXEL(l)         ((l) / 100)

JNIEXPORT jboolean JNICALL Java_org_lwjgl_WindowsTouchImplementation_nRegisterTouchWindow
(JNIEnv* env, jclass unused, jlong hwnd_ptr) {
    HWND hwnd = (HWND)(INT_PTR)hwnd_ptr;
    return RegisterTouchWindow(hwnd, 0);
}

JNIEXPORT jobjectArray JNICALL Java_org_lwjgl_WindowsTouchImplementation_nGetTouches
(JNIEnv* env, jclass unused, jlong wParam, jlong lParam, jlong hwnd_ptr) {
    UINT cInputs = LOWORD(wParam);
    PTOUCHINPUT pInputs = (TOUCHINPUT*)malloc(sizeof(TOUCHINPUT) * cInputs);
    HWND hwnd = (HWND)(INT_PTR)hwnd_ptr;
    jclass touchClass = (*env)->FindClass(env, "org/lwjgl/input/touchInput");
    jmethodID touchInputConstructor = (*env)->GetMethodID(env, touchClass, "<init>", "(IIII)V");
    jobjectArray ret = (*env)->NewObjectArray(env, cInputs, touchClass, NULL);
    if (touchInputConstructor == NULL) {
        return NULL;
    }

    if (pInputs) {
        if (GetTouchInputInfo((HTOUCHINPUT)lParam, cInputs, pInputs, sizeof(TOUCHINPUT))) {
            for (UINT i = 0; i < cInputs; i++) {
                TOUCHINPUT ti = pInputs[i];
                POINT ptInput = { ti.x, ti.y };
                ScreenToClient(hwnd, &ptInput);
                long x = TOUCH_COORD_TO_PIXEL(ptInput.x);
                long y = TOUCH_COORD_TO_PIXEL(ptInput.y);
                DWORD id = ti.dwID;
                DWORD flag = ti.dwFlags;
                jobject touch = (*env)->NewObject(env, touchClass, touchInputConstructor, x, y, id, flag);
                (*env)->SetObjectArrayElement(env, ret, i, touch);
            }
            free(pInputs);
            return ret;
        }
        else {
            free(pInputs);
            return NULL;
        }
    }
    else {
        return NULL;
    }
}
