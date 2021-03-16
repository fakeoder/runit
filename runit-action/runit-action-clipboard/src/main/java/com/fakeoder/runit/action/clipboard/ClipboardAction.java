package com.fakeoder.runit.action.clipboard;

import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * @author zhuo
 */
public class ClipboardAction extends Action {
    @Override
    public ActionResult run() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);

        if (clipTf != null) {
            // 检查内容是否是文本类型
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new ActionResult(this.id, ret);
    }
}
