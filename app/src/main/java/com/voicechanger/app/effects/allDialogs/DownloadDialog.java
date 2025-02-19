package com.voicechanger.app.effects.allDialogs;

import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseDialog;
import com.voicechanger.app.effects.custUi.SetLanguage;
import com.voicechanger.app.effects.custUi.constatnt.AppDataException;
import com.voicechanger.app.effects.custUi.constatnt.TapClick;
import com.voicechanger.app.effects.databinding.DownloadDialogBinding;

import java.util.Calendar;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class DownloadDialog extends BaseDialog<DownloadDialogBinding> {
    private final Activity act;
    private final Function1 function1;

    public int getDialogView() {
        return R.layout.download_dialog;
    }

    public DownloadDialog(Activity activity2, boolean z, Function1<? super String, Unit> function1) {
        super(activity2, z);
        Intrinsics.checkNotNullParameter(activity2, "activity");
        Intrinsics.checkNotNullParameter(function1, "onOk");
        act = activity2;
        this.function1 = function1;
    }

    public void setLanguage() {
        SetLanguage.setLocale(act);
    }

    public void initViews() {

        Window getWin = getWindow();

        WindowManager.LayoutParams layoutParams = getWin == null ? null : getWin.getAttributes();
        if (layoutParams != null) {
            layoutParams.width = (int) (((double) AppDataException.getWithMetrics(this.act)) * 0.9d);
        }
    }

    public void bindId() {
        getDataBinding().input.setText(String.valueOf(Calendar.getInstance().getTime().getTime()));
        TapClick.tap(getDataBinding().tvCancel, view -> {
            dismiss();
            return null;
        });
        TapClick.tap(getDataBinding().tvSet, view -> {
            String fileName = StringsKt.trim(getDataBinding().input.getText().toString()).toString();
            if (Intrinsics.areEqual(fileName, "")) {
                Toast.makeText(act, act.getResources().getText(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
            } else if (AppDataException.getSpecialChar(fileName)) {
                Toast.makeText(act, act.getResources().getText(R.string.there_is_a_special_character), Toast.LENGTH_SHORT).show();
            } else {
                Log.e("vv---", "bindId: fileName :: "+fileName );
                function1.invoke(fileName);
                dismiss();
            }
            return null;
        });

    }
}
