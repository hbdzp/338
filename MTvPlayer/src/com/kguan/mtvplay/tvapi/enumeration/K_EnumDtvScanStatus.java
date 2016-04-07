package com.kguan.mtvplay.tvapi.enumeration;

public enum K_EnumDtvScanStatus {
    K_E_STATUS_SCAN_NONE,
    // / auto tuning process
    K_E_STATUS_AUTOTUNING_PROGRESS,
    // / signal quality
    K_E_STATUS_SIGNAL_QUALITY,
    // / get programes
    K_E_STATUS_GET_PROGRAMS,
    // / set region
    K_E_STATUS_SET_REGION,
    // / favorite region
    K_E_STATUS_SET_FAVORITE_REGION,
    // / exit to OAD download
    K_E_STATUS_EXIT_TO_DL,
    // / LCN conflict
    K_E_STATUS_LCN_CONFLICT,
    // / end of scan
    K_E_STATUS_SCAN_END,
    // /Scan end and rearrange done to set first prog. done
    K_E_STATUS_SCAN_SETFIRSTPROG_DONE
}
