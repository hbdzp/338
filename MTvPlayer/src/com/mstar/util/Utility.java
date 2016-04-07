//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2014 MStar Semiconductor, Inc. All rights reserved.
// All software, firmware and related documentation herein ("MStar Software") are
// intellectual property of MStar Semiconductor, Inc. ("MStar") and protected by
// law, including, but not limited to, copyright law and international treaties.
// Any use, modification, reproduction, retransmission, or republication of all
// or part of MStar Software is expressly prohibited, unless prior written
// permission has been granted by MStar.
//
// By accessing, browsing and/or using MStar Software, you acknowledge that you
// have read, understood, and agree, to be bound by below terms ("Terms") and to
// comply with all applicable laws and regulations:
//
// 1. MStar shall retain any and all right, ownership and interest to MStar
//    Software and any modification/derivatives thereof.
//    No right, ownership, or interest to MStar Software and any
//    modification/derivatives thereof is transferred to you under Terms.
//
// 2. You understand that MStar Software might include, incorporate or be
//    supplied together with third party's software and the use of MStar
//    Software may require additional licenses from third parties.
//    Therefore, you hereby agree it is your sole responsibility to separately
//    obtain any and all third party right and license necessary for your use of
//    such third party's software.
//
// 3. MStar Software and any modification/derivatives thereof shall be deemed as
//    MStar's confidential information and you agree to keep MStar's
//    confidential information in strictest confidence and not disclose to any
//    third party.
//
// 4. MStar Software is provided on an "AS IS" basis without warranties of any
//    kind. Any warranties are hereby expressly disclaimed by MStar, including
//    without limitation, any warranties of merchantability, non-infringement of
//    intellectual property rights, fitness for a particular purpose, error free
//    and in conformity with any international standard.  You agree to waive any
//    claim against MStar for any loss, damage, cost or expense that you may
//    incur related to your use of MStar Software.
//    In no event shall MStar be liable for any direct, indirect, incidental or
//    consequential damages, including without limitation, lost of profit or
//    revenues, lost or damage of data, and unauthorized system use.
//    You agree that this Section 4 shall still apply without being affected
//    even if MStar Software has been modified by MStar in accordance with your
//    request or instruction for your use, except otherwise agreed by both
//    parties in writing.
//
// 5. If requested, MStar may from time to time provide technical supports or
//    services in relation with MStar Software to you for your use of
//    MStar Software in conjunction with your or your customer's product
//    ("Services").
//    You understand and agree that, except otherwise agreed by both parties in
//    writing, Services are provided on an "AS IS" basis and the warranty
//    disclaimer set forth in Section 4 above shall apply.
//
// 6. Nothing contained herein shall be construed as by implication, estoppels
//    or otherwise:
//    (a) conferring any license or right to use MStar name, trademark, service
//        mark, symbol or any other identification;
//    (b) obligating MStar or any of its affiliates to furnish any person,
//        including without limitation, you and your customers, any assistance
//        of any kind whatsoever, or any information; or
//    (c) conferring any license or right under any intellectual property right.
//
// 7. These terms shall be governed by and construed in accordance with the laws
//    of Taiwan, R.O.C., excluding its conflict of law rules.
//    Any and all dispute arising out hereof or related hereto shall be finally
//    settled by arbitration referred to the Chinese Arbitration Association,
//    Taipei in accordance with the ROC Arbitration Law and the Arbitration
//    Rules of the Association by three (3) arbitrators appointed in accordance
//    with the said Rules.
//    The place of arbitration shall be in Taipei, Taiwan and the language shall
//    be English.
//    The arbitration award shall be final and binding to both parties.
//
//******************************************************************************
//<MStar Software>

package com.mstar.util;

import java.util.Arrays;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import com.kguan.mtvplay.tvapi.K_TvCommonManager;
import com.kguan.mtvplay.tvapi.vo.common.K_TvTypeInfo;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvAudioManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvCountry;
import com.mstar.android.tv.TvParentalControlManager;
import com.mstar.android.tv.TvPvrManager;
import com.mstar.tv.tvplayer.ui.R;
import com.mstar.tv.tvplayer.ui.RootActivity;
import com.mstar.tv.tvplayer.ui.TVRootApp;
import com.mstar.tv.tvplayer.ui.TvIntent;

public class Utility {

    private static final String TAG = "Utility";

    private static Context sContext = null;

    private static int[] sRouteTable = null;

    private static String[] sRouteTableName = null;

    private static final int DO_CHANNEL_UP = 1;

    private static final int DO_CHANNEL_DOWN = 2;

    private static final int DO_CHANNEL_RETURN = 3;

    private static final int DO_CHANNEL_SELECT = 4;

    /**
     * Parental guidance spec for Singapore, Australia and Default
     *
     */
    private static final int[] sParentalValueDefault = new int[] {0, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

    private static final int[] sParentalValueSingapore = new int[] {0, 4, 7, 13, 16, 18, 21};

    private static final int[] sParentalValueAustralian = new int[] {0, 1, 5, 7, 9, 11, 13, 15, 17, 18};

    private static final int AUSTRALIAN_PARENTAL_VALUE_BLOCK_ALL_VALUE = 1;

    /**
     * TvSystem
     *
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_DVBT
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_DVBC
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_DVBS
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_DVBT2
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_DVBS2
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_DTMB
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_ATSC
     * @see com.mstar.android.tv.TvCommonManager#TV_SYSTEM_ISDB
     */
    private static int sTvSystem = -1;

    public static final int SCROLL_DIRECTION_UP = 1;

    public static final int SCROLL_DIRECTION_DOWN = 2;

    public void Utility() {

    }

    private static Context getContext() {
        if (null == sContext) {
            sContext = TVRootApp.getContext();
        }
        return sContext;
    }

    public static int getCurrentTvSystem() {
        if (sTvSystem < 0) {
            sTvSystem = TvCommonManager.getInstance().getCurrentTvSystem();
        }
        return sTvSystem;
    }

    public static boolean isATSC() {
        if (sTvSystem > 0) {
            return TvCommonManager.TV_SYSTEM_ATSC == sTvSystem;
        }
        return TvCommonManager.TV_SYSTEM_ATSC == getCurrentTvSystem();
    }

    public static boolean isISDB() {
        if (sTvSystem > 0) {
            return TvCommonManager.TV_SYSTEM_ISDB == sTvSystem;
        }
        return TvCommonManager.TV_SYSTEM_ISDB == getCurrentTvSystem();
    }

    public static boolean isDVBS() {
        boolean ret = false;
        int currentSystem = getCurrentTvSystem();
        if ((TvCommonManager.TV_SYSTEM_ATSC != currentSystem)
            && (TvCommonManager.TV_SYSTEM_ISDB != currentSystem)) {
                int currentRoute = TvChannelManager.TV_ROUTE_NONE;
                K_TvTypeInfo tvinfo = K_TvCommonManager.getInstance().K_getTvInfo();
                int routeIdx = TvChannelManager.getInstance().getCurrentDtvRouteIndex();
                currentRoute = tvinfo.getTvTypeInfo().routePath[routeIdx];
                if ((TvChannelManager.TV_ROUTE_DVBS == currentRoute)
                    || (TvChannelManager.TV_ROUTE_DVBS2 == currentRoute)) {
                    ret = true;
                }
        }
        return ret;
    }

    public static int[] getRouteTable() {
        if (null == sRouteTable) {
            switch (getCurrentTvSystem()) {
                case TvCommonManager.TV_SYSTEM_DVBT:
                case TvCommonManager.TV_SYSTEM_DVBC:
                case TvCommonManager.TV_SYSTEM_DVBS:
                case TvCommonManager.TV_SYSTEM_DVBT2:
                case TvCommonManager.TV_SYSTEM_DVBS2:
                case TvCommonManager.TV_SYSTEM_DTMB:
                case TvCommonManager.TV_SYSTEM_ISDB:
                    K_TvTypeInfo tvinfo = K_TvCommonManager.getInstance().K_getTvInfo();
                    sRouteTable = Arrays.copyOf(tvinfo.getTvTypeInfo().routePath, tvinfo.getTvTypeInfo().routePath.length);
                    break;
                case TvCommonManager.TV_SYSTEM_ATSC:
                    sRouteTable = new int[] {TvChannelManager.DTV_ANTENNA_TYPE_NONE, TvChannelManager.DTV_ANTENNA_TYPE_AIR, TvChannelManager.DTV_ANTENNA_TYPE_CABLE};
                    break;
                default:
                    break;
            }
        }
        return sRouteTable;
    }

    public static String[] getRouteTableName() {
        if (null == sRouteTableName) {
            int tvSystem = getCurrentTvSystem();
            int[] routeTable = getRouteTable();

            if (0 < routeTable.length) {
                sRouteTableName = new String[routeTable.length];
                /* Fill the literal representation of tv route */
                for (int i = 0; i < sRouteTableName.length; ++i ) {
                    sRouteTableName[i] = tvRouteToString(routeTable[i]);
                }
            } else {
                sRouteTableName = new String[] { tvRouteToString(-1) };
            }
        }
        return sRouteTableName;
    }

    /**
     * DTV Route ToString() function
     *
     * <p>This function often called at most 4 times when the findView() called
     * and findView() should called only once when the viewholder initiated,
     * for saving memsize, we're not gonna to store the result</p>
     *
     * @param nTvRoute, int represent tv route of ATSC or DVB system
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_NONE}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_DVBT}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_DVBC}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_DVBS}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_ATSC}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_ISDB}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_DVBT2}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_DVBS2}
     * @see {@link com.mstar.android.tv.TvChannelManager#TV_ROUTE_DTMB}
     * @see {@link com.mstar.android.tv.TvChannelManager#DTV_ANTENNA_TYPE_NONE}
     * @see {@link com.mstar.android.tv.TvChannelManager#DTV_ANTENNA_TYPE_AIR}
     * @see {@link com.mstar.android.tv.TvChannelManager#DTV_ANTENNA_TYPE_CABLE}
     */
    private static String tvRouteToString(int nTvRoute) {
        switch (getCurrentTvSystem()) {
            case TvCommonManager.TV_SYSTEM_DVBT:
            case TvCommonManager.TV_SYSTEM_DVBC:
            case TvCommonManager.TV_SYSTEM_DVBS:
            case TvCommonManager.TV_SYSTEM_DVBT2:
            case TvCommonManager.TV_SYSTEM_DVBS2:
            case TvCommonManager.TV_SYSTEM_DTMB:
                switch (nTvRoute) {
                    case TvChannelManager.TV_ROUTE_NONE:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_none);
                    case TvChannelManager.TV_ROUTE_DVBT:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_dvbt);
                    case TvChannelManager.TV_ROUTE_DVBC:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_dvbc);
                    case TvChannelManager.TV_ROUTE_DVBS:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_dvbs);
                    case TvChannelManager.TV_ROUTE_DVBT2:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_dvbt2);
                    case TvChannelManager.TV_ROUTE_DVBS2:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_dvbs2);
                    case TvChannelManager.TV_ROUTE_DTMB:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_dtmb);
                    default:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_unknown);
                }
            case TvCommonManager.TV_SYSTEM_ATSC:
                switch (nTvRoute) {
                    case TvChannelManager.DTV_ANTENNA_TYPE_NONE:
                        return getContext().getResources().getString(R.string.str_cha_antannatype_none);
                    case TvChannelManager.DTV_ANTENNA_TYPE_AIR:
                        return getContext().getResources().getString(R.string.str_cha_antannatype_air);
                    case TvChannelManager.DTV_ANTENNA_TYPE_CABLE:
                        return getContext().getResources().getString(R.string.str_cha_antannatype_cable);
                    default:
                        return getContext().getResources().getString(R.string.str_cha_tv_route_unknown);
                }
            case TvCommonManager.TV_SYSTEM_ISDB:
                return getContext().getResources().getString(R.string.str_cha_tv_route_unknown);
            default:
                // Log.d(TAG, "tvRouteToString():: Do Not Fall into the default case ! Handle Different DTV System Above !!");
                return getContext().getResources().getString(R.string.str_cha_tv_route_unknown);
        }
    }

    public static void refreshFocusOrder(View view) {
        if (null != view) {
            refreshFocusOrder((ViewGroup)view);
        }
    }

    public static void refreshFocusOrder(ViewParent viewParent) {
        if (null != viewParent) {
            refreshFocusOrder((ViewGroup)viewParent);
        }
    }

    public static void refreshFocusOrder(ViewGroup viewGroup) {
        if (null != viewGroup) {
            View view = null;
            View viewNearest = null;
            for (int index = 0; index < viewGroup.getChildCount(); ++index) {
                view = viewGroup.getChildAt(index);
                if (null != view) {
                    if (true == view.isFocusable()) {
                        viewNearest = view.focusSearch(View.FOCUS_BACKWARD);
                        if (null != viewNearest) {
                            view.setNextFocusUpId(viewNearest.getId());
                        }
                        viewNearest = view.focusSearch(View.FOCUS_FORWARD);
                        if (null != viewNearest) {
                            view.setNextFocusDownId(viewNearest.getId());
                        }
                    }
                }
            }
        }
    }

    public static int getATVRealChNum(int chNo) {
        int num = chNo;
        if (TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_ATV_PAL_ENABLE)) {
            /*
             * In Pal system, ATV channel number saving in db is start from 0
             * but display number is start from 1 (NTSC system has no this problem)
             */
            num -= 1;
        } else if (TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_ATV_NTSC_ENABLE)) {
            if (isISDB()) {
                num -= 1;
            }
        }
        return num;
    }

    public static int getATVDisplayChNum(int chNo) {
        int num = chNo;
        if (TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_ATV_PAL_ENABLE)) {
            /*
             * In Pal system, ATV channel number saving in db is start from 0
             * but display number is start from 1 (NTSC system has no this problem)
             */
            num += 1;
        } else if (TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_ATV_NTSC_ENABLE)) {
            if (isISDB()) {
                num += 1;
            }
        }

        return num;
    }

    public static boolean isSupportATV() {
        if ((false == TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_ATV_NTSC_ENABLE))
            && (false == TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_ATV_PAL_ENABLE))) {
            return false;
        }
        return true;
    }

    public static int getDefaultInputSource() {
        int src = TvCommonManager.INPUT_SOURCE_ATV;
        if (false == isSupportATV()) {
            src = TvCommonManager.INPUT_SOURCE_DTV;
        }
        return src;
    }

    public static boolean isSupportInputSourceLock() {
        if (true == isATSC()) {
            return false;
        }
        return TvCommonManager.getInstance().isSupportModule(TvCommonManager.MODULE_TV_CONFIG_INPUT_SOURCE_LOCK);
    }

    /*
     * FIXME: By Android Api Guide, getRunningTasks should not be used in our
     * code's core section. It is only using for debugging. But we do not have a
     * better method to determinant whether RootActivity is in top or not, So we
     * used this method temporarily.
     */
    public static boolean isTopActivity(Activity activity, String className) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = manager.getRunningTasks(1).get(0).topActivity;
        String topActivityName = cn.getClassName();
        return topActivityName.equals(className);
    }

    public static void startSourceInfo(Activity activity) {
        boolean isSystemLocked = false;
        boolean isCurrentProgramLocked = false;
        int curInput = TvCommonManager.getInstance().getCurrentTvInputSource();
        if (TvCommonManager.getInstance().getCurrentTvSystem() == TvCommonManager.TV_SYSTEM_ATSC) {
            isSystemLocked = TvAtscChannelManager.getInstance().getCurrentVChipBlockStatus();
        } else {
            isSystemLocked = TvParentalControlManager.getInstance().isSystemLock();
        }
        if ((TvCommonManager.INPUT_SOURCE_ATV == curInput)
                || (TvCommonManager.INPUT_SOURCE_DTV == curInput)) {
            isCurrentProgramLocked = TvChannelManager.getInstance().getCurrentProgramInfo().isLock;
        }
        if (!(isSystemLocked && isCurrentProgramLocked)) {
            /*
             * when RootActivity is not running, we don't start activity to
             * interrupt other menu, so we send SIGNAL_LOCK action to source
             * info for updating content if SourceInfoActivity is alive, its
             * BoradcastReceiver will handle this event.
             */
            if (false == isTopActivity(activity, RootActivity.class.getName())) {
                activity.sendBroadcast(new Intent(TvIntent.ACTION_SIGNAL_LOCK));
            } else {
                Intent intent = new Intent(TvIntent.ACTION_SOURCEINFO);
                activity.startActivity(intent);
            }
        }
    }

    public static void doChannelChange(Activity activity, int operation, int channelSelectNum) {
        TvChannelManager channelManager = TvChannelManager.getInstance();
        if (DO_CHANNEL_UP == operation) {
            channelManager.programUp();
        } else if (DO_CHANNEL_DOWN == operation) {
            channelManager.programDown();
        } else if (DO_CHANNEL_RETURN == operation) {
            channelManager.returnToPreviousProgram();
        } else if (DO_CHANNEL_SELECT == operation) {
            channelManager.selectProgram(channelSelectNum, TvChannelManager.SERVICE_TYPE_DTV);
        } else {
            return;
        }
        if (TvCommonManager.getInstance().getCurrentTvSystem() != TvCommonManager.TV_SYSTEM_ATSC) {
            startSourceInfo(activity);
        }
    }

    public static boolean isForegroundRecording() {
        boolean isForegroundRecord = false;
        /* Always time shift recording will auto stop by tvsystem. */
        final TvPvrManager pvr = TvPvrManager.getInstance();
        if ((true == pvr.isRecording()) && (false == pvr.isAlwaysTimeShiftRecording())) {
            isForegroundRecord = true;
        }
        return isForegroundRecord;
    }

/*    private static void stopPvr(final Activity activity, boolean isForegroundRecording, boolean isPlaybacking) {
        if (true == PVRActivity.isPVRActivityActive) {
            Intent intent = new Intent(TvIntent.ACTION_PVR_ACTIVITY);
            if (isForegroundRecording) {
                intent.putExtra(Constant.PVR_CREATE_MODE, Constant.PVR_RECORD_STOP);
            } else {
                intent.putExtra(Constant.PVR_CREATE_MODE, Constant.PVR_PLAYBACK_STOP);
            }
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }
        } else {
            if (isForegroundRecording) {
                TvPvrManager.getInstance().stopRecord();
            }
            if (isPlaybacking) {
                TvPvrManager.getInstance().stopPlayback();
            }
        }
         Return util exit PVRActivity 
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true == PVRActivity.isPVRActivityActive) {
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return;
    }*/
    public static void channelChangeDialog(final Activity activity, final int operation, final int channelSelectNum) {
        /*final boolean isForegroundRecording = isForegroundRecording();
        final boolean isPlaybacking = TvPvrManager.getInstance().isPlaybacking();
        if ((true == isForegroundRecording) || (true == isPlaybacking)) {
            AlertDialog.Builder build = new AlertDialog.Builder(activity);
            if (isForegroundRecording) {
                build.setTitle(R.string.str_stop_record_dialog_title);
                if (isPlaybacking) {
                    build.setMessage(R.string.str_stop_record_playback_dialog_message);
                } else {
                    build.setMessage(R.string.str_stop_record_dialog_message);
                }
            } else {
                build.setTitle(R.string.str_stop_playback_dialog_title);
                build.setMessage(R.string.str_stop_playback_dialog_message);
            }
            build.setPositiveButton(R.string.str_stop_record_dialog_stop,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopPvr(activity, isForegroundRecording, isPlaybacking);
                            doChannelChange(activity, operation, channelSelectNum);
                        }
                    });
            build.setNegativeButton(R.string.str_stop_record_dialog_cancel,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            build.create().show();
            return;
        }*/
        doChannelChange(activity, operation, channelSelectNum);
    }

    public static void channelUp(Activity activity) {
        channelChangeDialog(activity, DO_CHANNEL_UP, 0);
    }

    public static void channelDown(Activity activity) {
        channelChangeDialog(activity, DO_CHANNEL_DOWN, 0);
    }

    public static void channelReturn(Activity activity) {
        channelChangeDialog(activity,DO_CHANNEL_RETURN, 0);
    }

    public static void channelSelect(Activity activity, int channelSelectNum) {
        channelChangeDialog(activity, DO_CHANNEL_SELECT, channelSelectNum);
    }

    public static void changeTvTimeZone(Context context, String timezoneChangeString) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setTimeZone(timezoneChangeString);
        Toast.makeText(context, getContext().getResources().getString(R.string.str_time_change_timezone), Toast.LENGTH_SHORT).show();
    }

    public static boolean scrollPosition(TextView tv, int direction) {
        int lineCount = tv.getLineCount();
        int linesOfOnePage = tv.getHeight()/tv.getLineHeight();
        if (lineCount > linesOfOnePage) {
            Layout layout = tv.getLayout();
            int currentLine = layout.getLineForVertical(tv.getScrollY());
            int newLine = 0;

            if (direction == SCROLL_DIRECTION_UP) {
                /* scroll one page up */
                newLine = currentLine - linesOfOnePage;
                if (0 > newLine) {
                    newLine = 0;
                }
            } else if (direction == SCROLL_DIRECTION_DOWN) {
                /* scroll one page down */
                newLine = currentLine + linesOfOnePage;
                if (newLine >= lineCount) {
                    newLine = currentLine;
                }
            }

            tv.scrollTo(0, layout.getLineTop(newLine));
            return true;
        }

        return false;
    }

    public static String getParentalGuideAgeString(int ageValue, int country) {
        if (0 >= ageValue) {
            return "";
        }

        int ageStringResId = R.array.guidance_list_default_string;
        String[] ageString = null;
        if (TvCountry.AUSTRALIA == country) {
            if (AUSTRALIAN_PARENTAL_VALUE_BLOCK_ALL_VALUE == ageValue) {
                /* The displaying string of rating Block-All is the empty string. */
                return (new String(""));
            }
            ageStringResId = R.array.guidance_list_australian_string;
        } else if (TvCountry.SINGAPORE == country) {
            ageStringResId = R.array.guidance_list_singapore_string;
        } else {
            ageStringResId = R.array.guidance_list_default_string;
        }
        ageString = getContext().getResources().getStringArray(ageStringResId);

        return ageString[getParentalGuideIndex(ageValue,country)];
    }

    public static int getParentalGuideIndex(int ageValue, int country) {
        int[] level = null;
        if (TvCountry.AUSTRALIA == country) {
            level = sParentalValueAustralian;
        } else if (TvCountry.SINGAPORE == country) {
            level = sParentalValueSingapore;
        } else {
            level = sParentalValueDefault;
        }

        int ageIndex = 0;
        for (int i = (level.length - 1); i >= 0 ; i--) {
            if (ageValue >= level[i]) {
                ageIndex = i;
                break;
            }
        }
        return ageIndex;
    }

    public static int getParentalGuideValue(int ageIndex, int country) {
        if (TvCountry.AUSTRALIA == country) {
            return sParentalValueAustralian[ageIndex];
        } else if (TvCountry.SINGAPORE == country) {
            return sParentalValueSingapore[ageIndex];
        } else {
            return sParentalValueDefault[ageIndex];
        }
    }

   public static String getAudioTypeString(int audioType, int aacLevel) {
        int resId = 0;
        switch (audioType) {
            case TvAudioManager.AUDIO_TYPE_MPEG:
                resId = R.string.audio_type_mpeg;
                break;
            case TvAudioManager.AUDIO_TYPE_Dolby_D:
                resId = R.string.audio_type_dolby_d;
                break;
            case TvAudioManager.AUDIO_TYPE_AAC:
                resId = R.string.audio_type_aac;
                switch (aacLevel) {
                    case TvAudioManager.AAC_LEVEL1:
                        resId = R.string.aac_level1;
                        break;
                    case TvAudioManager.AAC_LEVEL2:
                        resId = R.string.aac_level2;
                        break;
                    case TvAudioManager.AAC_LEVEL4:
                        resId = R.string.aac_level4;
                        break;
                    case TvAudioManager.AAC_LEVEL5:
                        resId = R.string.aac_level5;
                        break;
                    case TvAudioManager.HE_AAC_LEVEL2:
                        resId = R.string.he_aac_level2;
                        break;
                    case TvAudioManager.HE_AAC_LEVEL3:
                        resId = R.string.he_aac_level3;
                        break;
                    case TvAudioManager.HE_AAC_LEVEL4:
                        resId = R.string.he_aac_level4;
                        break;
                    case TvAudioManager.HE_AAC_LEVEL5:
                        resId = R.string.he_aac_level5;
                        break;
                    default:
                        if (TvCommonManager.TV_SYSTEM_ISDB == getCurrentTvSystem()) {
                            switch (aacLevel) {
                                case TvAudioManager.AAC_LEVEL1_BRAZIL:
                                    resId = R.string.aac_level1;
                                    break;
                                case TvAudioManager.AAC_LEVEL2_BRAZIL:
                                    resId = R.string.aac_level2;
                                    break;
                                case TvAudioManager.AAC_LEVEL4_BRAZIL:
                                    resId = R.string.aac_level4;
                                    break;
                                case TvAudioManager.AAC_LEVEL5_BRAZIL:
                                    resId = R.string.aac_level5;
                                    break;
                                case TvAudioManager.HE_AAC_LEVEL2_BRAZIL:
                                    resId = R.string.he_aac_level2;
                                    break;
                                case TvAudioManager.HE_AAC_LEVEL3_BRAZIL:
                                    resId = R.string.he_aac_level3;
                                    break;
                                case TvAudioManager.HE_AAC_LEVEL4_BRAZIL:
                                    resId = R.string.he_aac_level4;
                                    break;
                                case TvAudioManager.HE_AAC_LEVEL5_BRAZIL:
                                    resId = R.string.he_aac_level5;
                                    break;
                            }
                        }
                    }
                break;
            case TvAudioManager.AUDIO_TYPE_AC3P:
                resId = R.string.audio_type_ac3p;
                break;
            case TvAudioManager.AUDIO_TYPE_DRA1:
                resId = R.string.audio_type_dra1;
                break;
            default:
                return new String("");
        }

        return getContext().getResources().getString(resId);
    }
}
