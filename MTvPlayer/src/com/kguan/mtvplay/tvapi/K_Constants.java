package com.kguan.mtvplay.tvapi;

import java.util.Hashtable;
import com.mstar.android.tv.TvAtscChannelManager;
import com.mstar.android.tv.TvAudioManager;
import com.mstar.android.tv.TvCcManager;
import com.mstar.android.tv.TvCecManager;
import com.mstar.android.tv.TvChannelManager;
import com.mstar.android.tv.TvChannelManager.DvbcScanParam;
import com.mstar.android.tv.TvCiManager;
import com.mstar.android.tv.TvCommonManager;
import com.mstar.android.tv.TvCountry;
import com.mstar.android.tv.TvDvbChannelManager;
import com.mstar.android.tv.TvEpgManager;
import com.mstar.android.tv.TvLanguage;
import com.mstar.android.tv.TvPictureManager;
import com.mstar.android.tv.TvS3DManager;
import com.mstar.android.tvapi.atv.vo.EnumGetProgramInfo;
import com.mstar.android.tvapi.common.vo.EnumAvdVideoStandardType;
import com.mstar.android.tvapi.common.vo.EnumPipModes;
import com.mstar.android.tvapi.common.vo.EnumServiceType;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumTimeZone;
import com.mstar.android.tvapi.common.vo.VideoInfo.EnumScanType;
import com.mstar.android.tvapi.dtv.vo.EnumDtvScanStatus;
import com.mstar.android.tvapi.factory.vo.EnumAcOnPowerOnMode;

public class K_Constants {
	private static Hashtable<Integer, Integer> htK_MAPPING_VIDEO_ARC_ENUM= new Hashtable<Integer, Integer>();
	private static Hashtable<Integer, Integer> htK_MAPPING_SOUND_MODE_ENUM= new Hashtable<Integer, Integer>();

	// 缩放转换
	public static final int PICTURE_VIDEO_ARC_16x9 = 0;
	public static final int PICTURE_VIDEO_ARC_4x3 = 1;
	public static final int PICTURE_VIDEO_ARC_ZOOM1 = 2;
	public static final int PICTURE_VIDEO_ARC_ZOOM2 = 3;
	public static final int PICTURE_VIDEO_ARC_DOTBYDOT = 4;
	public static final int PICTURE_VIDEO_ARC_AUTO = 5;

	static enum K_MAPPING_VIDEO_ARC_ENUM {
		K_MAPPING_VIDEO_ARC_16x9(TvPictureManager.VIDEO_ARC_16x9), 
		K_MAPPING_VIDEO_ARC_4x3(TvPictureManager.VIDEO_ARC_4x3), 
		K_MAPPING_VIDEO_ARC_ZOOM1(TvPictureManager.VIDEO_ARC_ZOOM1),
		K_MAPPING_VIDEO_ARC_ZOOM2(TvPictureManager.VIDEO_ARC_ZOOM2), 
		K_MAPPING_VIDEO_ARC_DOTBYDOT(TvPictureManager.VIDEO_ARC_DOTBYDOT), 
		K_MAPPING_VIDEO_ARC_AUTO(TvPictureManager.VIDEO_ARC_AUTO);	
		private final int mstValue;
        private static int seq = 0;

		private K_MAPPING_VIDEO_ARC_ENUM(int value) {
			this.mstValue = value;
			setHashtableValue(value);
		}
		private static void setHashtableValue(int value) {
        	htK_MAPPING_VIDEO_ARC_ENUM.put(new Integer(value), new Integer(seq));
            seq++;
        }
		public int getMstValue() {
			return this.mstValue;
		}
        public static int getKTCOrdinalThroughValue(int key) {
            Integer ordinal = (Integer) htK_MAPPING_VIDEO_ARC_ENUM.get(key);
            if (ordinal != null) {
                return ordinal.intValue();
            } else {
                return -1;
            }
        }
	}

	// 声音模式转换
	public static final int SOUND_MODE_STANDARD = 0;
	public static final int SOUND_MODE_MUSIC = 1;
	public static final int SOUND_MODE_MOVIE = 2;
	public static final int SOUND_MODE_USER = 3;

	static enum K_MAPPING_SOUND_MODE_ENUM {
		K_MAPPING_SOUND_MODE_STANDARD(TvAudioManager.SOUND_MODE_STANDARD), 
		K_MAPPING_SOUND_MODE_MUSIC(TvAudioManager.SOUND_MODE_MUSIC), 
		K_MAPPING_K_MAPPING_SOUND_MODE_MOVIE(TvAudioManager.SOUND_MODE_MOVIE), 
		K_MAPPING_K_MAPPING_SOUND_MODE_USER(TvAudioManager.SOUND_MODE_USER);
		int mstValue;
		private static int seq = 0;

		K_MAPPING_SOUND_MODE_ENUM(int value) {
			this.mstValue = value;
			setHashtableValue(value);
		}
		private static void setHashtableValue(int value) {
        	htK_MAPPING_SOUND_MODE_ENUM.put(new Integer(value), new Integer(seq));
            seq++;
        }
		public int getMstValue() {
			return this.mstValue;
		}
        public static int getKTCOrdinalThroughValue(int key) {
            Integer ordinal = (Integer) htK_MAPPING_SOUND_MODE_ENUM.get(key);
            if (ordinal != null) {
                return ordinal.intValue();
            } else {
                return -1;
            }
        }

	}

	public static final int TV_SYSTEM_ATSC = TvCommonManager.TV_SYSTEM_ATSC;
	public static final int TV_SYSTEM_ISDB = TvCommonManager.TV_SYSTEM_ISDB;
	public static final int TV_SYSTEM_DVBT = TvCommonManager.TV_SYSTEM_DVBT;
	public static final int TV_SYSTEM_DVBC = TvCommonManager.TV_SYSTEM_DVBC;
	public static final int TV_SYSTEM_DVBS = TvCommonManager.TV_SYSTEM_DVBS;
	public static final int TV_SYSTEM_DVBT2 = TvCommonManager.TV_SYSTEM_DVBT2;
	public static final int TV_SYSTEM_DVBS2 = TvCommonManager.TV_SYSTEM_DVBS2;
	public static final int TV_SYSTEM_DTMB = TvCommonManager.TV_SYSTEM_DTMB;
	public static final int INPUT_SOURCE_DTV = TvCommonManager.INPUT_SOURCE_DTV;
	public static final int MODULE_TV_CONFIG_THREED_DEPTH = TvCommonManager.MODULE_TV_CONFIG_THREED_DEPTH;
	public static final int INPUT_SOURCE_HDMI = TvCommonManager.INPUT_SOURCE_HDMI;
	public static final int INPUT_SOURCE_HDMI2 = TvCommonManager.INPUT_SOURCE_HDMI2;
	public static final int INPUT_SOURCE_HDMI3 = TvCommonManager.INPUT_SOURCE_HDMI3;
	public static final int INPUT_SOURCE_HDMI4 = TvCommonManager.INPUT_SOURCE_HDMI4;
	public static final int INPUT_SOURCE_STORAGE = TvCommonManager.INPUT_SOURCE_STORAGE;
	public static final int INPUT_SOURCE_VGA = TvCommonManager.INPUT_SOURCE_VGA;
	public static final int INPUT_SOURCE_HDMI_MAX = TvCommonManager.INPUT_SOURCE_HDMI_MAX;
	public static final int INPUT_SOURCE_YPBPR = TvCommonManager.INPUT_SOURCE_YPBPR;
	public static final int INPUT_SOURCE_YPBPR2 = TvCommonManager.INPUT_SOURCE_YPBPR2;
	public static final int INPUT_SOURCE_YPBPR3 = TvCommonManager.INPUT_SOURCE_YPBPR3;
	public static final int INPUT_SOURCE_YPBPR_MAX = TvCommonManager.INPUT_SOURCE_YPBPR_MAX;
	public static final int INPUT_SOURCE_ATV = TvCommonManager.INPUT_SOURCE_ATV;
	public static final int INPUT_SOURCE_CVBS = TvCommonManager.INPUT_SOURCE_CVBS;
	public static final int INPUT_SOURCE_CVBS2 = TvCommonManager.INPUT_SOURCE_CVBS2;
	public static final int INPUT_SOURCE_CVBS3 = TvCommonManager.INPUT_SOURCE_CVBS3;
	public static final int INPUT_SOURCE_CVBS4 = TvCommonManager.INPUT_SOURCE_CVBS4;
	public static final int INPUT_SOURCE_CVBS5 = TvCommonManager.INPUT_SOURCE_CVBS5;
	public static final int INPUT_SOURCE_CVBS6 = TvCommonManager.INPUT_SOURCE_CVBS6;
	public static final int INPUT_SOURCE_CVBS7 = TvCommonManager.INPUT_SOURCE_CVBS7;
	public static final int INPUT_SOURCE_CVBS8 = TvCommonManager.INPUT_SOURCE_CVBS8;
	public static final int INPUT_SOURCE_DVI = TvCommonManager.INPUT_SOURCE_DVI;
	public static final int INPUT_SOURCE_DVI2 = TvCommonManager.INPUT_SOURCE_DVI2;
	public static final int INPUT_SOURCE_DVI3 = TvCommonManager.INPUT_SOURCE_DVI3;
	public static final int INPUT_SOURCE_DVI4 = TvCommonManager.INPUT_SOURCE_DVI4;
	public static final int INPUT_SOURCE_DVI_MAX = TvCommonManager.INPUT_SOURCE_DVI_MAX;
	public static final int INPUT_SOURCE_NONE = TvCommonManager.INPUT_SOURCE_NONE;
	public static final int INPUT_SOURCE_CVBS_MAX = TvCommonManager.INPUT_SOURCE_CVBS_MAX;
	public static final int INPUT_SOURCE_SVIDEO = TvCommonManager.INPUT_SOURCE_SVIDEO;
	public static final int INPUT_SOURCE_SVIDEO2 = TvCommonManager.INPUT_SOURCE_SVIDEO2;
	public static final int INPUT_SOURCE_SVIDEO3 = TvCommonManager.INPUT_SOURCE_SVIDEO3;
	public static final int INPUT_SOURCE_SVIDEO4 = TvCommonManager.INPUT_SOURCE_SVIDEO4;
	public static final int INPUT_SOURCE_SVIDEO_MAX = TvCommonManager.INPUT_SOURCE_SVIDEO_MAX;
	public static final int INPUT_SOURCE_SCART_MAX = TvCommonManager.INPUT_SOURCE_SCART_MAX;
	public static final int INPUT_SOURCE_SCART = TvCommonManager.INPUT_SOURCE_SCART;
	public static final int INPUT_SOURCE_SCART2 = TvCommonManager.INPUT_SOURCE_SCART2;
	public static final int POPUP_DIALOG_HIDE = TvCommonManager.POPUP_DIALOG_HIDE;
	public static final int MODULE_OFFLINE_DETECT = TvCommonManager.MODULE_OFFLINE_DETECT;
	public static final int MODULE_PREVIEW_MODE = TvCommonManager.MODULE_PREVIEW_MODE;
	public static final int MODULE_CC = TvCommonManager.MODULE_CC;
	public static final int MODULE_BRAZIL_CC = TvCommonManager.MODULE_BRAZIL_CC;
	public static final int POPUP_DIALOG_SHOW = TvCommonManager.POPUP_DIALOG_SHOW;

	public static final int ATV_AUDIOMODE_INVALID = TvCommonManager.ATV_AUDIOMODE_INVALID;
	public static final int ATV_AUDIOMODE_MONO = TvCommonManager.ATV_AUDIOMODE_MONO;
	public static final int ATV_AUDIOMODE_FORCED_MONO = TvCommonManager.ATV_AUDIOMODE_FORCED_MONO;
	public static final int ATV_AUDIOMODE_G_STEREO = TvCommonManager.ATV_AUDIOMODE_G_STEREO;
	public static final int ATV_AUDIOMODE_K_STEREO = TvCommonManager.ATV_AUDIOMODE_K_STEREO;
	public static final int ATV_AUDIOMODE_MONO_SAP = TvCommonManager.ATV_AUDIOMODE_MONO_SAP;
	public static final int ATV_AUDIOMODE_STEREO_SAP = TvCommonManager.ATV_AUDIOMODE_STEREO_SAP;
	public static final int ATV_AUDIOMODE_DUAL_A = TvCommonManager.ATV_AUDIOMODE_DUAL_A;
	public static final int ATV_AUDIOMODE_DUAL_B = TvCommonManager.ATV_AUDIOMODE_DUAL_B;
	public static final int ATV_AUDIOMODE_DUAL_AB = TvCommonManager.ATV_AUDIOMODE_DUAL_AB;
	public static final int ATV_AUDIOMODE_NICAM_MONO = TvCommonManager.ATV_AUDIOMODE_NICAM_MONO;
	public static final int ATV_AUDIOMODE_NICAM_STEREO = TvCommonManager.ATV_AUDIOMODE_NICAM_STEREO;
	public static final int ATV_AUDIOMODE_NICAM_DUAL_A = TvCommonManager.ATV_AUDIOMODE_NICAM_DUAL_A;
	public static final int ATV_AUDIOMODE_NICAM_DUAL_B = TvCommonManager.ATV_AUDIOMODE_NICAM_DUAL_B;
	public static final int ATV_AUDIOMODE_NICAM_DUAL_AB = TvCommonManager.ATV_AUDIOMODE_NICAM_DUAL_AB;
	public static final int ATV_AUDIOMODE_HIDEV_MONO = TvCommonManager.ATV_AUDIOMODE_HIDEV_MONO;
	public static final int ATV_AUDIOMODE_LEFT_LEFT = TvCommonManager.ATV_AUDIOMODE_LEFT_LEFT;
	public static final int ATV_AUDIOMODE_RIGHT_RIGHT = TvCommonManager.ATV_AUDIOMODE_RIGHT_RIGHT;
	public static final int ATV_AUDIOMODE_LEFT_RIGHT = TvCommonManager.ATV_AUDIOMODE_LEFT_RIGHT;

	public static final int MWE_DEMO_MODE_OFF = TvPictureManager.MWE_DEMO_MODE_OFF;
	public static final int NR_MODE_AUTO = TvPictureManager.NR_MODE_AUTO;
	public static final int NR_MODE_OFF = TvPictureManager.NR_MODE_OFF;
	public static final int MWE_DEMO_MODE_SQUAREMOVE = TvPictureManager.MWE_DEMO_MODE_SQUAREMOVE;
	public static final int VIDEO_ARC_MAX = TvPictureManager.VIDEO_ARC_MAX;
	public static final int PICTURE_MODE_NORMAL = TvPictureManager.PICTURE_MODE_NORMAL;
	public static final int PICTURE_MODE_GAME = TvPictureManager.PICTURE_MODE_GAME;
	public static final int PICTURE_MODE_AUTO = TvPictureManager.PICTURE_MODE_AUTO;
	public static final int PICTURE_MODE_PC = TvPictureManager.PICTURE_MODE_PC;
	public static final int PICTURE_MODE_VIVID = TvPictureManager.PICTURE_MODE_VIVID;
	public static final int PICTURE_MODE_SPORTS = TvPictureManager.PICTURE_MODE_SPORTS;
	public static final int PICTURE_MODE_NATURAL = TvPictureManager.PICTURE_MODE_NATURAL;
	public static final int PICTURE_MODE_USER = TvPictureManager.PICTURE_MODE_USER;
	public static final int COLOR_TEMP_USER1 = TvPictureManager.COLOR_TEMP_USER1;
	public static final int PICTURE_CONTRAST = TvPictureManager.PICTURE_CONTRAST;
	public static final int PICTURE_BRIGHTNESS = TvPictureManager.PICTURE_BRIGHTNESS;
	public static final int PICTURE_SHARPNESS = TvPictureManager.PICTURE_SHARPNESS;
	public static final int PICTURE_HUE = TvPictureManager.PICTURE_HUE;
	public static final int PICTURE_SATURATION = TvPictureManager.PICTURE_SATURATION;

	public static final int PROGRAM_COUNT_DTV = TvChannelManager.PROGRAM_COUNT_DTV;
	public static final int PROGRAM_COUNT_ATV = TvChannelManager.PROGRAM_COUNT_ATV;
	public static final int PROGRAM_COUNT_ATV_DTV = TvChannelManager.PROGRAM_COUNT_ATV_DTV;
	public static final int TV_ROUTE_DVBS = TvChannelManager.TV_ROUTE_DVBS;
	public static final int TV_ROUTE_DVBS2 = TvChannelManager.TV_ROUTE_DVBS2;
	public static final int TV_ROUTE_NONE = TvChannelManager.TV_ROUTE_NONE;
	public static final int RF_INFO = TvChannelManager.RF_INFO;
	public static final int FIRST_TO_SHOW_RF = TvChannelManager.FIRST_TO_SHOW_RF;
	public static final int PREVIOUS_RF = TvChannelManager.PREVIOUS_RF;
	public static final int NEXT_RF = TvChannelManager.NEXT_RF;

	public static final int RF_CHANNEL_BANDWIDTH_7_MHZ = TvChannelManager.RF_CHANNEL_BANDWIDTH_7_MHZ;
	public static final int RF_CHANNEL_BANDWIDTH_8_MHZ = TvChannelManager.RF_CHANNEL_BANDWIDTH_8_MHZ;
	public static final int DTV_ANTENNA_TYPE_NONE = TvChannelManager.DTV_ANTENNA_TYPE_NONE;
	public static final int FIRST_SERVICE_INPUT_TYPE_ALL = TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ALL;
	public static final int FIRST_SERVICE_DEFAULT = TvChannelManager.FIRST_SERVICE_DEFAULT;
	public static final int FIRST_SERVICE_INPUT_TYPE_ATV = TvChannelManager.FIRST_SERVICE_INPUT_TYPE_ATV;
	public static final int FIRST_SERVICE_INPUT_TYPE_DTV = TvChannelManager.FIRST_SERVICE_INPUT_TYPE_DTV;
	public static final int FIRST_SERVICE_MENU_SCAN = TvChannelManager.FIRST_SERVICE_MENU_SCAN;
	public static final int DTV_ANTENNA_TYPE_AIR = TvChannelManager.DTV_ANTENNA_TYPE_AIR;
	public static final int TV_ROUTE_DVBT = TvChannelManager.TV_ROUTE_DVBT;
	public static final int TV_ROUTE_DVBT2 = TvChannelManager.TV_ROUTE_DVBT2;
	public static final int DTV_ANTENNA_TYPE_CABLE = TvChannelManager.DTV_ANTENNA_TYPE_CABLE;
	public static final int DTV_ROUTE_INDEX_MAX_COUNT = TvChannelManager.DTV_ROUTE_INDEX_MAX_COUNT;
	public static final int TV_ROUTE_DTMB = TvChannelManager.TV_ROUTE_DTMB;
	public static final int TV_ROUTE_DVBC = TvChannelManager.TV_ROUTE_DVBC;
	public static final int TV_SCAN_ALL = TvChannelManager.TV_SCAN_ALL;
	public static final int TV_SCAN_DTV = TvChannelManager.TV_SCAN_DTV;
	public static final int TV_SCAN_ATV = TvChannelManager.TV_SCAN_ATV;
	public static final int PROGRAM_FAVORITE_ID_1 = TvChannelManager.PROGRAM_FAVORITE_ID_1;
	public static final int PROGRAM_ATTRIBUTE_SKIP = TvChannelManager.PROGRAM_ATTRIBUTE_SKIP;
	public static final int PROGRAM_ATTRIBUTE_LOCK = TvChannelManager.PROGRAM_ATTRIBUTE_LOCK;
	public static final int PROGRAM_ATTRIBUTE_DELETE = TvChannelManager.PROGRAM_ATTRIBUTE_DELETE;
	public static final int CHANNEL_SWITCH_MODE_FREEZESCREEN = TvChannelManager.CHANNEL_SWITCH_MODE_FREEZESCREEN;
	public static final int TTX_MODE_SUBTITLE_NAVIGATION = TvChannelManager.TTX_MODE_SUBTITLE_NAVIGATION;
	public static final int TTX_MODE_CLOCK = TvChannelManager.TTX_MODE_CLOCK;
	public static final int TTX_MODE_NORMAL = TvChannelManager.TTX_MODE_NORMAL;

	public static final int SERVICE_TYPE_ATV = TvChannelManager.SERVICE_TYPE_ATV;
	public static final int SERVICE_TYPE_DTV = TvChannelManager.SERVICE_TYPE_DTV;
	public static final int SERVICE_TYPE_RADIO = TvChannelManager.SERVICE_TYPE_RADIO;
	public static final int SERVICE_TYPE_DATA = TvChannelManager.SERVICE_TYPE_DATA;
	public static final int SERVICE_TYPE_UNITED_TV = TvChannelManager.SERVICE_TYPE_UNITED_TV;
	public static final int SERVICE_TYPE_INVALID = TvChannelManager.SERVICE_TYPE_INVALID;
	public static final int ATV_PROG_CTRL_GET_MAX_CHANNEL = TvChannelManager.ATV_PROG_CTRL_GET_MAX_CHANNEL;

	public static final int ATV_AUDIO_STANDARD_BG = TvChannelManager.ATV_AUDIO_STANDARD_BG;
	public static final int ATV_AUDIO_STANDARD_DK = TvChannelManager.ATV_AUDIO_STANDARD_DK;
	public static final int ATV_AUDIO_STANDARD_I = TvChannelManager.ATV_AUDIO_STANDARD_I;
	public static final int ATV_AUDIO_STANDARD_L = TvChannelManager.ATV_AUDIO_STANDARD_L;
	public static final int ATV_AUDIO_STANDARD_M = TvChannelManager.ATV_AUDIO_STANDARD_M;

	public static final int TTX_COMMAND_DIGIT_0 = TvChannelManager.TTX_COMMAND_DIGIT_0;
	public static final int TTX_COMMAND_DIGIT_1 = TvChannelManager.TTX_COMMAND_DIGIT_1;
	public static final int TTX_COMMAND_DIGIT_2 = TvChannelManager.TTX_COMMAND_DIGIT_2;
	public static final int TTX_COMMAND_DIGIT_3 = TvChannelManager.TTX_COMMAND_DIGIT_3;
	public static final int TTX_COMMAND_DIGIT_4 = TvChannelManager.TTX_COMMAND_DIGIT_4;
	public static final int TTX_COMMAND_DIGIT_5 = TvChannelManager.TTX_COMMAND_DIGIT_5;
	public static final int TTX_COMMAND_DIGIT_6 = TvChannelManager.TTX_COMMAND_DIGIT_6;
	public static final int TTX_COMMAND_DIGIT_7 = TvChannelManager.TTX_COMMAND_DIGIT_7;
	public static final int TTX_COMMAND_DIGIT_8 = TvChannelManager.TTX_COMMAND_DIGIT_8;
	public static final int TTX_COMMAND_DIGIT_9 = TvChannelManager.TTX_COMMAND_DIGIT_9;
	public static final int TTX_COMMAND_PAGE_UP = TvChannelManager.TTX_COMMAND_PAGE_UP;
	public static final int TTX_COMMAND_PAGE_DOWN = TvChannelManager.TTX_COMMAND_PAGE_DOWN;
	public static final int TTX_COMMAND_SUBPAGE = TvChannelManager.TTX_COMMAND_SUBPAGE;
	public static final int TTX_COMMAND_PAGE_RIGHT = TvChannelManager.TTX_COMMAND_PAGE_RIGHT;
	public static final int TTX_COMMAND_PAGE_LEFT = TvChannelManager.TTX_COMMAND_PAGE_LEFT;
	public static final int TTX_COMMAND_RED = TvChannelManager.TTX_COMMAND_RED;
	public static final int TTX_COMMAND_GREEN = TvChannelManager.TTX_COMMAND_GREEN;
	public static final int TTX_COMMAND_YELLOW = TvChannelManager.TTX_COMMAND_YELLOW;
	public static final int TTX_COMMAND_CYAN = TvChannelManager.TTX_COMMAND_CYAN;
	public static final int TTX_COMMAND_MIX = TvChannelManager.TTX_COMMAND_MIX;
	public static final int TTX_COMMAND_TEXT = TvChannelManager.TTX_COMMAND_TEXT;
	public static final int TTX_COMMAND_TV = TvChannelManager.TTX_COMMAND_TV;
	public static final int TTX_COMMAND_UPDATE = TvChannelManager.TTX_COMMAND_UPDATE;
	public static final int TTX_COMMAND_INDEX = TvChannelManager.TTX_COMMAND_INDEX;
	public static final int TTX_COMMAND_HOLD = TvChannelManager.TTX_COMMAND_HOLD;
	public static final int TTX_COMMAND_LIST = TvChannelManager.TTX_COMMAND_LIST;
	public static final int TTX_COMMAND_TIME = TvChannelManager.TTX_COMMAND_TIME;
	public static final int TTX_COMMAND_SIZE = TvChannelManager.TTX_COMMAND_SIZE;
	public static final int TTX_COMMAND_REVEAL = TvChannelManager.TTX_COMMAND_REVEAL;
	public static final int TTX_COMMAND_CLOCK = TvChannelManager.TTX_COMMAND_CLOCK;
	public static final int TTX_COMMAND_SUBTITLE_TTX_ON = TvChannelManager.TTX_COMMAND_SUBTITLE_TTX_ON;
	public static final int TTX_COMMAND_SUBTITLE_NAVIGATION = TvChannelManager.TTX_COMMAND_SUBTITLE_NAVIGATION;
	public static final int TTX_COMMAND_STATUS_DISPLAY = TvChannelManager.TTX_COMMAND_STATUS_DISPLAY;
	public static final int TTX_COMMAND_CLEAR_LIST = TvChannelManager.TTX_COMMAND_CLEAR_LIST;
	public static final int TTX_COMMAND_MIX_TEXT = TvChannelManager.TTX_COMMAND_MIX_TEXT;
	public static final int TTX_COMMAND_SETUP_BEFORE_UPDATE_PAGE_HANDLER = TvChannelManager.TTX_COMMAND_SETUP_BEFORE_UPDATE_PAGE_HANDLER;
	public static final int TTX_COMMAND_NORMAL_MODE_NEXT_PHASE = TvChannelManager.TTX_COMMAND_NORMAL_MODE_NEXT_PHASE;
	public static final int TTX_COMMAND_GOTO_PAGE = TvChannelManager.TTX_COMMAND_GOTO_PAGE;
	public static final int TTX_COMMAND_MAX_TEXT_COMMAND = TvChannelManager.TTX_COMMAND_MAX_TEXT_COMMAND;

	public static final int TUNING_STATUS_NONE = TvChannelManager.TUNING_STATUS_NONE;
	public static final int TUNING_STATUS_ATV_AUTO_TUNING = TvChannelManager.TUNING_STATUS_ATV_AUTO_TUNING;
	public static final int TUNING_STATUS_DTV_AUTO_TUNING = TvChannelManager.TUNING_STATUS_DTV_AUTO_TUNING;
	public static final int TUNING_STATUS_DTV_FULL_TUNING = TvChannelManager.TUNING_STATUS_DTV_FULL_TUNING;
	public static final int TUNING_STATUS_ATV_SCAN_PAUSING = TvChannelManager.TUNING_STATUS_ATV_SCAN_PAUSING;
	public static final int TUNING_STATUS_DTV_SCAN_PAUSING = TvChannelManager.TUNING_STATUS_DTV_SCAN_PAUSING;
	
	public static final int ATV_MANUAL_TUNE_MODE_SEARCH_UP = TvChannelManager.ATV_MANUAL_TUNE_MODE_SEARCH_UP;
    public static final int ATV_MANUAL_TUNE_MODE_SEARCH_DOWN = TvChannelManager.ATV_MANUAL_TUNE_MODE_SEARCH_DOWN;
    public static final int ATV_MANUAL_TUNE_MODE_FINE_TUNE_ONE_FREQ = TvChannelManager.ATV_MANUAL_TUNE_MODE_FINE_TUNE_ONE_FREQ;
    public static final int ATV_MANUAL_TUNE_MODE_FINE_TUNE_UP = TvChannelManager.ATV_MANUAL_TUNE_MODE_FINE_TUNE_UP;
    public static final int ATV_MANUAL_TUNE_MODE_FINE_TUNE_DOWN = TvChannelManager.ATV_MANUAL_TUNE_MODE_FINE_TUNE_DOWN;
    public static final int ATV_MANUAL_TUNE_MODE_UNDEFINE = TvChannelManager.ATV_MANUAL_TUNE_MODE_UNDEFINE;
    
    public static final int TUNING_STATUS_ATV_MANUAL_TUNING_RIGHT = TvChannelManager.TUNING_STATUS_ATV_MANUAL_TUNING_RIGHT;
    public static final int TUNING_STATUS_ATV_MANUAL_TUNING_LEFT = TvChannelManager.TUNING_STATUS_ATV_MANUAL_TUNING_LEFT;

	public static final int AVD_VIDEO_STANDARD_PAL_BGHI = TvChannelManager.AVD_VIDEO_STANDARD_PAL_BGHI;
	public static final int AVD_VIDEO_STANDARD_NTSC_M = TvChannelManager.AVD_VIDEO_STANDARD_NTSC_M;
	public static final int AVD_VIDEO_STANDARD_SECAM = TvChannelManager.AVD_VIDEO_STANDARD_SECAM;
	public static final int AVD_VIDEO_STANDARD_NTSC_44 = TvChannelManager.AVD_VIDEO_STANDARD_NTSC_44;
	public static final int AVD_VIDEO_STANDARD_PAL_M = TvChannelManager.AVD_VIDEO_STANDARD_PAL_M;
	public static final int AVD_VIDEO_STANDARD_PAL_N = TvChannelManager.AVD_VIDEO_STANDARD_PAL_N;
	public static final int AVD_VIDEO_STANDARD_PAL_60 = TvChannelManager.AVD_VIDEO_STANDARD_PAL_60;
	public static final int AVD_VIDEO_STANDARD_NOTSTANDARD = TvChannelManager.AVD_VIDEO_STANDARD_NOTSTANDARD;
	public static final int AVD_VIDEO_STANDARD_AUTO = TvChannelManager.AVD_VIDEO_STANDARD_AUTO;

	public static final int THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_REALTIME = TvS3DManager.THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_REALTIME;
	public static final int THREE_DIMENSIONS_DISPLAY_FORMAT_NONE = TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE;
	public static final int THREE_DIMENSIONS_VIDEO_3DTO2D_NONE = TvS3DManager.THREE_DIMENSIONS_VIDEO_3DTO2D_NONE;
	public static final int THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_OFF = TvS3DManager.THREE_DIMENSIONS_VIDEO_SELF_ADAPTIVE_DETECT_OFF;
	public static final int THREE_DIMENSIONS_DISPLAY_FORMAT_AUTO = TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_AUTO;
	public static final int THREE_DIMENSIONS_DISPLAY_FORMAT_2DTO3D = TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_2DTO3D;
	public static final int THREE_DIMENSIONS_TYPE_FRAME_PACKING_720P = TvS3DManager.THREE_DIMENSIONS_TYPE_FRAME_PACKING_720P;
	public static final int THREE_DIMENSIONS_TYPE_NONE = TvS3DManager.THREE_DIMENSIONS_TYPE_NONE;

	public static final int NORWAY = TvCountry.NORWAY;
	public static final int AUSTRALIA = TvCountry.AUSTRALIA;
	public static final int FRANCE = TvCountry.FRANCE;
	public static final int OTHERS = TvCountry.OTHERS;
	public static final int SPAIN = TvCountry.SPAIN;
	public static final int CLOSED_CAPTION_OFF = TvCcManager.CLOSED_CAPTION_OFF;
	public static final int CHINA = TvCountry.CHINA;

	public static final int CARD_STATE_NO = TvCiManager.CARD_STATE_NO;
	public static final int CARD_STATE_READY = TvCiManager.CARD_STATE_READY;
	public static final int CARD_STATE_INITIALIZING = TvCiManager.CARD_STATE_INITIALIZING;
	public static final int CARD_STATE_MAX = TvCiManager.CARD_STATE_MAX;
	public static final int CARD_STATE_RESET = TvCiManager.CARD_STATE_RESET;
	public static final int TVCI_UI_CARD_INSERTED = TvCiManager.TVCI_UI_CARD_INSERTED;
	public static final int TVCI_UI_CARD_REMOVED = TvCiManager.TVCI_UI_CARD_REMOVED;
	public static final int TVCI_UI_DATA_READY = TvCiManager.TVCI_UI_DATA_READY;
	public static final int TVCI_UI_CLOSEMMI = TvCiManager.TVCI_UI_CLOSEMMI;
	public static final int TVCI_UI_AUTOTEST_MESSAGE_SHOWN = TvCiManager.TVCI_UI_AUTOTEST_MESSAGE_SHOWN;
	public static final int CIMMI_LIST = TvCiManager.CIMMI_LIST;
	public static final int CIMMI_NONE = TvCiManager.CIMMI_NONE;
	public static final int CIMMI_MENU = TvCiManager.CIMMI_MENU;
	public static final int CIMMI_ENQ = TvCiManager.CIMMI_ENQ;

	public static final int CI_NOTIFY_CU_IS_PROGRESS = TvCiManager.CI_NOTIFY_CU_IS_PROGRESS;
	public static final int CI_NOTIFY_OP_IS_TUNING = TvCiManager.CI_NOTIFY_OP_IS_TUNING;
	public static final int TVCI_STATUS_CHANGE_TUNER_OCCUPIED = TvCiManager.TVCI_STATUS_CHANGE_TUNER_OCCUPIED;

	public static final EnumAvdVideoStandardType NTSC_44 = EnumAvdVideoStandardType.NTSC_44;
	public static final EnumAvdVideoStandardType NTSC_M = EnumAvdVideoStandardType.NTSC_M;
	public static final EnumAvdVideoStandardType PAL_BGHI = EnumAvdVideoStandardType.PAL_BGHI;
	public static final EnumAvdVideoStandardType PAL_N = EnumAvdVideoStandardType.PAL_N;
	public static final EnumAvdVideoStandardType PAL_M = EnumAvdVideoStandardType.PAL_M;
	public static final EnumAvdVideoStandardType SECAM = EnumAvdVideoStandardType.SECAM;
	public static final EnumAvdVideoStandardType PAL_60 = EnumAvdVideoStandardType.PAL_60;

	public static final EnumScanType E_PROGRESSIVE = EnumScanType.E_PROGRESSIVE;

	public static final EnumAcOnPowerOnMode E_ACON_POWERON_DIRECT = EnumAcOnPowerOnMode.E_ACON_POWERON_DIRECT;

	public static final int TvLanguage_ENGLISH = TvLanguage.ENGLISH;
	public static final int TvLanguage_CHINESE = TvLanguage.CHINESE;
	public static final int TvLanguage_ACHINESE = TvLanguage.ACHINESE;

	public static final EnumPipModes E_PIP_MODE_POP = EnumPipModes.E_PIP_MODE_POP;

	public static final int TVCEC_STANDBY = TvCecManager.TVCEC_STANDBY;
	public static final int TVCEC_SET_MENU_LANGUAGE = TvCecManager.TVCEC_SET_MENU_LANGUAGE;
	public static final int TVCEC_SOURCE_SWITCH = TvCecManager.TVCEC_SOURCE_SWITCH;
	public static final int TVCEC_SEL_DIGITAL_SERVICE_DVB = TvCecManager.TVCEC_SEL_DIGITAL_SERVICE_DVB;
	public static final int TVCEC_UPDATE_EDID = TvCecManager.TVCEC_UPDATE_EDID;
	
	public static final int CEC_DIALOG_HIDE = TvCecManager.CEC_DIALOG_HIDE;
	public static final int CEC_DIALOG_SHOW = TvCecManager.CEC_DIALOG_SHOW;

	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS11_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS11_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS10_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS10_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS9_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS9_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS8_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS8_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS7_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS7_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS6_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS6_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS5_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS4_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS4_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS3_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS3_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS2_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS2_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS1_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS1_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_0_START = EnumTimeZone.E_TIMEZONE_GMT_0_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_1_START = EnumTimeZone.E_TIMEZONE_GMT_0_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_2_START = EnumTimeZone.E_TIMEZONE_GMT_1_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_3_START = EnumTimeZone.E_TIMEZONE_GMT_2_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_4_START = EnumTimeZone.E_TIMEZONE_GMT_3_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_5_START = EnumTimeZone.E_TIMEZONE_GMT_4_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_6_START = EnumTimeZone.E_TIMEZONE_GMT_5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_7_START = EnumTimeZone.E_TIMEZONE_GMT_6_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_8_START = EnumTimeZone.E_TIMEZONE_GMT_7_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_9_START = EnumTimeZone.E_TIMEZONE_GMT_8_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_10_START = EnumTimeZone.E_TIMEZONE_GMT_10_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_11_START = EnumTimeZone.E_TIMEZONE_GMT_11_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_12_START = EnumTimeZone.E_TIMEZONE_GMT_12_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_13_START = EnumTimeZone.E_TIMEZONE_GMT_13_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS4_5_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS4_5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS3_5_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS3_5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_MINUS2_5_START = EnumTimeZone.E_TIMEZONE_GMT_MINUS2_5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_3POINT5_START = EnumTimeZone.E_TIMEZONE_GMT_3POINT5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_4POINT5_START = EnumTimeZone.E_TIMEZONE_GMT_4POINT5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_5POINT5_START = EnumTimeZone.E_TIMEZONE_GMT_5POINT5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_6POINT5_START = EnumTimeZone.E_TIMEZONE_GMT_6POINT5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_9POINT5_START = EnumTimeZone.E_TIMEZONE_GMT_9POINT5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_10POINT5_START = EnumTimeZone.E_TIMEZONE_GMT_10POINT5_START;
	public static final EnumTimeZone E_TIMEZONE_GMT_5POINT45_START = EnumTimeZone.E_TIMEZONE_GMT_5POINT45_START;

	public static final EnumDtvScanStatus E_STATUS_SCAN_END = EnumDtvScanStatus.E_STATUS_SCAN_END;

	public static final EnumServiceType E_SERVICETYPE_ATV = EnumServiceType.E_SERVICETYPE_ATV;
	public static final EnumServiceType E_SERVICETYPE_DTV = EnumServiceType.E_SERVICETYPE_DTV;

	public static final int ONEPARTCHANNEL_MINOR_NUM = TvAtscChannelManager.ONEPARTCHANNEL_MINOR_NUM;

	public static final int EPG_DETAIL_DESCRIPTION = TvEpgManager.EPG_DETAIL_DESCRIPTION;

	public static final int PROGRAM_INFO_TYPE_CURRENT = TvDvbChannelManager.PROGRAM_INFO_TYPE_CURRENT;

	public static final EnumGetProgramInfo E_IS_AFT_NEED = EnumGetProgramInfo.E_IS_AFT_NEED;
	public static final EnumGetProgramInfo E_IS_PROGRAM_SKIPPED = EnumGetProgramInfo.E_IS_PROGRAM_SKIPPED;

	public static final int DVBC_CAB_TYPE_QAM_64 = DvbcScanParam.DVBC_CAB_TYPE_QAM_64;
	public static final int DVBC_CAB_TYPE_QAM_16 = DvbcScanParam.DVBC_CAB_TYPE_QAM_16;
	public static final int DVBC_CAB_TYPE_QAM_256 = DvbcScanParam.DVBC_CAB_TYPE_QAM_256;
	
}
