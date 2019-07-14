package com.crecg.staffshield.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;

import com.crecg.crecglibrary.network.model.CheckVersionModelData;
import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.MainActivity;
import com.crecg.staffshield.dialog.CheckVersionDialog;

public class UpdateManager {
    private Context context;

    public void checkVersion(Context context, CheckVersionModelData version) {
        if (!(context instanceof Activity)) {
            return;
        }
        if (((Activity) context).isFinishing()) {
            return;
        }
        if (version == null || TextUtils.isEmpty(version.url)) {
            return;
        }

        this.context = context;

        showDialog(version);
    }

    private void showDialog(final CheckVersionModelData version) {
        String content = TextUtils.isEmpty(version.display) ? "发现新版本,是否更新" : version.display;
        boolean isForceUpdate = version.isForce == 1;
        CheckVersionDialog dialog = new CheckVersionDialog(context, content, isForceUpdate, new CheckVersionDialog.OnCheckVersion() {
            @Override
            public void onConfirm() {
                startDownload(version.url);
            }
        });
        dialog.show();
    }

    private void startDownload(String url) {
        new DownloadFileDialog(context, url).show();
    }
}
