package com.hema.myplayer.https;

/**
 * 常量类
 * 
 * @author wwj_748
 * 
 */
public class Constants {
	// 博客每一项的类型
	public class DEF_BLOG_ITEM_TYPE {
		public static final int TITLE = 1; // 标题
		public static final int SUMMARY = 2; // 摘要
		public static final int CONTENT = 3; // 内容
		public static final int IMG = 4; // 图片
		public static final int BOLD_TITLE = 5; // 加粗标题
		public static final int CODE = 6; // 代码
	}

	public class DEF_NEWS_TYPE {
		public static final int YEJIE = 1;
		public static final int YIDONG = 2;
		public static final int YANFA = 3;
		public static final int ZAZHI = 4;
		public static final int YUNJISUAN = 5;
	}

	// 评论类型
	public class DEF_COMMENT_TYPE {
		public static final int PARENT = 1;
		public static final int CHILD = 2;
	}

	// 操作结果类型
	public class DEF_RESULT_CODE {
		public static final int ERROR = 1; // 错误
		public static final int NO_DATA = 2;// 无数据
		public static final int REFRESH = 3;// 刷新
		public static final int LOAD = 4; // 加载
		public static final int FIRST = 5;// 第一次加载
	}

	// 任务类型
	public class DEF_TASK_TYPE {
		public static final String FIRST = "first";
		public static final String NOR_FIRST = "not_first";
		public static final String REFRESH = "REFRESH";
		public static final String LOAD = "LOAD";
	}

	/**
	 * 视频类型
	 */
	public class DEF_VIDEO_TYPE {
		// 动画区
		public static final int DONG_HUA = 1;
		public static final int MAD_AMV = 24;	// MAD.AMV
		public static final int MMD_3D = 25;	// MMD.3D
		public static final int DONG_HUA_DUAN_PIAN = 47;	// 动画短片
		public static final int DONG_HUA_ZONG_HE = 27;		// 动画综合
		// 番剧区
		public static final int FAN_JU = 13;
		public static final int LIAN_ZAI_DONG_HUA = 33; // 连载动画
		public static final int WAN_JIE_DONG_HUA = 32;	// 完结动画
		public static final int ZHI_XUN = 51;	// 资讯
		public static final int GUAN_FANG_YAN_SHEN = 152;	// 官方延伸
		public static final int GUO_CHAN_DONG_HUA = 153;	// 国产动画
		// 音乐区
		public static final int YIN_YUE = 3;
		public static final int FAN_CHANG = 31;	// 翻唱
		public static final int VOCALOID_UTAU = 30;	// VOCALOID-UTAU
		public static final int YAN_ZOU = 59;	// 演奏
		public static final int YIN_YUE_XUAN_JI = 130;	//音乐选集
		// 舞蹈区
		public static final int WU_DAO = 129;
		public static final int ZHAI_WU = 20;	// 宅舞
		public static final int SAN_CHIYUAN_WU_DAO = 154;	//三次元舞蹈
		public static final int WU_DAO_JIAOCHENG = 156;	// 舞蹈教程
		//科技区
		public static final int KE_JI = 36;
		public static final int JI_LU_PIAN = 37;	//纪录片
		public static final int KE_PU_REN_WEN = 124;	//科普人文
		public static final int YE_SHENG_JI_SHU = 122;	//野生技术
		public static final int YAN_JIANG = 39;	//演讲·公开课
		public static final int JUN_SHI = 96;	//军事
		public static final int SHU_MA = 95;	//数码
		public static final int JI_XIE = 98;	//机械
		//娱乐
		public static final int YU_LE = 5;
		public static final int GAO_XIAO = 138;	//搞笑
		public static final int SHENG_HUO = 21;	//生活
		public static final int DONG_WU = 75;	//动物
		public static final int MEI_SHI = 76;	//美食
		public static final int ZONG_YI = 71;	//综艺
		public static final int YU_LE_QUAN = 137;	//娱乐圈
		public static final int KOREA = 131;	//KOREA相关
		//电影
		public static final int DIAN_YIN = 23;
		public static final int DIAN_YIN_XIANG_GUANG = 82;	//电影相关
		public static final int DUAN_PIAN = 85;	//短片
		public static final int OU_MEI_DIAN_YIN = 145;	//欧美电影
		public static final int RI_BEN_DIAN_YIN = 146;	//日本电影
		public static final int GUO_CHAN_DIAN_YIN = 147;	//国产电影
		public static final int QI_TA_GUOJIA = 83;	//其他国家
		//游戏
		public static final int YOU_XI = 4;
		public static final int DIAN_JI = 17;	//单机联机
		public static final int WANG_LUO_DIAN_YIN = 65;	//网络电竞
		public static final int YIN_YOU = 136;	//音游
		public static final int MUGEN = 19;	//MUGEN
		public static final int GMV = 121;	//GMV
		//鬼畜
		public static final int GUI_CU = 119;
		public static final int GUI_CU_TIAOJIAO = 22;	//鬼畜调教
		public static final int YING_MAD = 26;	//音MAD
		public static final int REN_LI = 126;	//人力vocaloid
		public static final int JIAOCHENG = 127;	//教程演示
		//电视剧
		public static final int DIAN_SHI_JU = 11;
		public static final int LIAN_ZAI_JU = 15;	//连载剧集
		public static final int WANG_JIE_JU = 34;	//完结剧集
		public static final int TE_SHE = 86;	//特摄·布袋
		public static final int DIAN_SHI_JU_XIANGUANG = 128;	//电视剧相关
		//时尚
		public static final int SHI_SHANG = 155;
		public static final int MEI_ZHUANG = 157;	//美妆健身
		public static final int FU_SHI = 158;	//服饰
		public static final int SHI_SHANG_ZHI_XUN = 159;	//时尚资讯
	}
	/**
	 * 分区类型
	 */
	public class DEF_AREA_TYPE {
		public static final int DONG_HUA_QU = 1;    // 动画区
		public static final int FAN_JU_QU = 2;		// 番剧区
		public static final int YIN_YUE_QU = 3;		// 音乐区
		public static final int KE_JI_QU = 4;		// 科技区
		public static final int YU_LE_QU = 5;		// 娱乐区
		public static final int YOU_XI_QU = 6;		// 游戏区
	}
}
