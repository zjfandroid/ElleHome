#include "TvInfo.h"
#include <stdio.h>
#include <string.h>

unsigned short getTvBrandNameById(unsigned short id, unsigned char language, char *name) 
{
	switch(id)
	{
		case 1:
			if(language)
			{
				strcpy(name, "长虹");
				return strlen("长虹");
			}
			else
			{
				strcpy(name, "Changhong");
				return strlen("Changhong");
			}
			break;
		case 2:
			if(language)
			{
				strcpy(name, "康佳");
				return strlen("康佳");
			}
			else
			{
				strcpy(name, "Konka");
				return strlen("Konka");
			}
			break;
		case 3:
			if(language)
			{
				strcpy(name, "TCL");
				return strlen("TCL");
			}
			else
			{
				strcpy(name, "TCL");
				return strlen("TCL");
			}
			break;
		case 4:
			if(language)
			{
				strcpy(name, "厦华");
				return strlen("厦华");
			}
			else
			{
				strcpy(name, "Prima");
				return strlen("Prima");
			}
			break;
		case 5:
			if(language)
			{
				strcpy(name, "创维");
				return strlen("创维");
			}
			else
			{
				strcpy(name, "SKYWORTH");
				return strlen("SKYWORTH");
			}
			break;
		case 6:
			if(language)
			{
				strcpy(name, "海信");
				return strlen("海信");
			}
			else
			{
				strcpy(name, "Hisense");
				return strlen("Hisense");
			}
			break;
		case 7:
			if(language)
			{
				strcpy(name, "海尔");
				return strlen("海尔");
			}
			else
			{
				strcpy(name, "Haier");
				return strlen("Haier");
			}
			break;
		case 8:
			if(language)
			{
				strcpy(name, "金星");
				return strlen("金星");
			}
			else
			{
				strcpy(name, "JINXING");
				return strlen("JINXING");
			}
			break;
		case 9:
			if(language)
			{
				strcpy(name, "熊猫");
				return strlen("熊猫");
			}
			else
			{
				strcpy(name, "PANDA");
				return strlen("PANDA");
			}
			break;
		case 10:
			if(language)
			{
				strcpy(name, "索尼");
				return strlen("索尼");
			}
			else
			{
				strcpy(name, "sony");
				return strlen("sony");
			}
			break;
		case 11:
			if(language)
			{
				strcpy(name, "松下");
				return strlen("松下");
			}
			else
			{
				strcpy(name, "Panasonic");
				return strlen("Panasonic");
			}
			break;
		case 12:
			if(language)
			{
				strcpy(name, "乐声");
				return strlen("乐声");
			}
			else
			{
				strcpy(name, "Panasonic");
				return strlen("Panasonic");
			}
			break;
		case 13:
			if(language)
			{
				strcpy(name, "东芝");
				return strlen("东芝");
			}
			else
			{
				strcpy(name, "TOSHIBA");
				return strlen("TOSHIBA");
			}
			break;
		case 14:
			if(language)
			{
				strcpy(name, "日立");
				return strlen("日立");
			}
			else
			{
				strcpy(name, "HITACHI");
				return strlen("HITACHI");
			}
			break;
		case 15:
			if(language)
			{
				strcpy(name, "夏普");
				return strlen("夏普");
			}
			else
			{
				strcpy(name, "SHARP");
				return strlen("SHARP");
			}
			break;
		case 16:
			if(language)
			{
				strcpy(name, "声宝");
				return strlen("声宝");
			}
			else
			{
				strcpy(name, "Sampo");
				return strlen("Sampo");
			}
			break;
		case 17:
			if(language)
			{
				strcpy(name, "飞利浦");
				return strlen("飞利浦");
			}
			else
			{
				strcpy(name, "Philips");
				return strlen("Philips");
			}
			break;
		case 18:
			if(language)
			{
				strcpy(name, "三星");
				return strlen("三星");
			}
			else
			{
				strcpy(name, "SAMSUNG");
				return strlen("SAMSUNG");
			}
			break;
		case 19:
			if(language)
			{
				strcpy(name, "三洋");
				return strlen("三洋");
			}
			else
			{
				strcpy(name, "SANYO");
				return strlen("SANYO");
			}
			break;
		case 20:
			if(language)
			{
				strcpy(name, "日电");
				return strlen("日电");
			}
			else
			{
				strcpy(name, "Xinhua");
				return strlen("Xinhua");
			}
			break;
		case 21:
			if(language)
			{
				strcpy(name, "西湖");
				return strlen("西湖");
			}
			else
			{
				strcpy(name, "XIHU");
				return strlen("XIHU");
			}
			break;
		case 22:
			if(language)
			{
				strcpy(name, "北京");
				return strlen("北京");
			}
			else
			{
				strcpy(name, "BEIJING");
				return strlen("BEIJING");
			}
			break;
		case 23:
			if(language)
			{
				strcpy(name, "高路华");
				return strlen("高路华");
			}
			else
			{
				strcpy(name, "CONROWA");
				return strlen("CONROWA");
			}
			break;
		case 24:
			if(language)
			{
				strcpy(name, "乐华");
				return strlen("乐华");
			}
			else
			{
				strcpy(name, "ROWA");
				return strlen("ROWA");
			}
			break;
		case 25:
			if(language)
			{
				strcpy(name, "福日");
				return strlen("福日");
			}
			else
			{
				strcpy(name, "Furi");
				return strlen("Furi");
			}
			break;
		case 26:
			if(language)
			{
				strcpy(name, "LG");
				return strlen("LG");
			}
			else
			{
				strcpy(name, "LG");
				return strlen("LG");
			}
			break;
		case 27:
			if(language)
			{
				strcpy(name, "百乐");
				return strlen("百乐");
			}
			else
			{
				strcpy(name, "Tupper");
				return strlen("Tupper");
			}
			break;
		case 28:
			if(language)
			{
				strcpy(name, "长城");
				return strlen("长城");
			}
			else
			{
				strcpy(name, "CHANGCHENG");
				return strlen("CHANGCHENG");
			}
			break;
		case 29:
			if(language)
			{
				strcpy(name, "黄河");
				return strlen("黄河");
			}
			else
			{
				strcpy(name, "Yellow River");
				return strlen("Yellow River");
			}
			break;
		case 30:
			if(language)
			{
				strcpy(name, "黄山");
				return strlen("黄山");
			}
			else
			{
				strcpy(name, "Huangshan");
				return strlen("Huangshan");
			}
			break;
		case 31:
			if(language)
			{
				strcpy(name, "三垦");
				return strlen("三垦");
			}
			else
			{
				strcpy(name, "sanken");
				return strlen("sanken");
			}
			break;
		case 32:
			if(language)
			{
				strcpy(name, "宝立创");
				return strlen("宝立创");
			}
			else
			{
				strcpy(name, "Polytron");
				return strlen("Polytron");
			}
			break;
		case 33:
			if(language)
			{
				strcpy(name, "爱华");
				return strlen("爱华");
			}
			else
			{
				strcpy(name, "Aiwa");
				return strlen("Aiwa");
			}
			break;
		case 34:
			if(language)
			{
				strcpy(name, "清华同方");
				return strlen("清华同方");
			}
			else
			{
				strcpy(name, "Tongfang");
				return strlen("Tongfang");
			}
			break;
		case 35:
			if(language)
			{
				strcpy(name, "三菱");
				return strlen("三菱");
			}
			else
			{
				strcpy(name, "SANLING");
				return strlen("SANLING");
			}
			break;
		case 36:
			if(language)
			{
				strcpy(name, "大宇");
				return strlen("大宇");
			}
			else
			{
				strcpy(name, "Daewoo");
				return strlen("Daewoo");
			}
			break;
		case 37:
			if(language)
			{
				strcpy(name, "牡丹");
				return strlen("牡丹");
			}
			else
			{
				strcpy(name, "MUDAN");
				return strlen("MUDAN");
			}
			break;
		case 38:
			if(language)
			{
				strcpy(name, "夏华");
				return strlen("夏华");
			}
			else
			{
				strcpy(name, "Xiahua");
				return strlen("Xiahua");
			}
			break;
		case 39:
			if(language)
			{
				strcpy(name, "泰山");
				return strlen("泰山");
			}
			else
			{
				strcpy(name, "Taishan");
				return strlen("Taishan");
			}
			break;
		case 40:
			if(language)
			{
				strcpy(name, "雪莲");
				return strlen("雪莲");
			}
			else
			{
				strcpy(name, "XUELIAN");
				return strlen("XUELIAN");
			}
			break;
		case 41:
			if(language)
			{
				strcpy(name, "红岩");
				return strlen("红岩");
			}
			else
			{
				strcpy(name, "HONGYAN");
				return strlen("HONGYAN");
			}
			break;
		case 42:
			if(language)
			{
				strcpy(name, "奥林匹亚");
				return strlen("奥林匹亚");
			}
			else
			{
				strcpy(name, "AOLINPIKE");
				return strlen("AOLINPIKE");
			}
			break;
		case 43:
			if(language)
			{
				strcpy(name, "DETRON");
				return strlen("DETRON");
			}
			else
			{
				strcpy(name, "DETRON");
				return strlen("DETRON");
			}
			break;
		case 44:
			if(language)
			{
				strcpy(name, "彩星");
				return strlen("彩星");
			}
			else
			{
				strcpy(name, "Playmates");
				return strlen("Playmates");
			}
			break;
		case 45:
			if(language)
			{
				strcpy(name, "南宝");
				return strlen("南宝");
			}
			else
			{
				strcpy(name, "South Po");
				return strlen("South Po");
			}
			break;
		case 46:
			if(language)
			{
				strcpy(name, "飞燕");
				return strlen("飞燕");
			}
			else
			{
				strcpy(name, "FEIYAN");
				return strlen("FEIYAN");
			}
			break;
		case 47:
			if(language)
			{
				strcpy(name, "宝花石");
				return strlen("宝花石");
			}
			else
			{
				strcpy(name, "Treasure flower stone");
				return strlen("Treasure flower stone");
			}
			break;
		case 48:
			if(language)
			{
				strcpy(name, "豁达");
				return strlen("豁达");
			}
			else
			{
				strcpy(name, "Minded");
				return strlen("Minded");
			}
			break;
		case 49:
			if(language)
			{
				strcpy(name, "港泰");
				return strlen("港泰");
			}
			else
			{
				strcpy(name, "GANGTAI");
				return strlen("GANGTAI");
			}
			break;
		case 50:
			if(language)
			{
				strcpy(name, "彩虹");
				return strlen("彩虹");
			}
			else
			{
				strcpy(name, "CAIHONG");
				return strlen("CAIHONG");
			}
			break;
		case 51:
			if(language)
			{
				strcpy(name, "宝声");
				return strlen("宝声");
			}
			else
			{
				strcpy(name, "Po Sing");
				return strlen("Po Sing");
			}
			break;
		case 52:
			if(language)
			{
				strcpy(name, "东凌");
				return strlen("东凌");
			}
			else
			{
				strcpy(name, "TOS");
				return strlen("TOS");
			}
			break;
		case 53:
			if(language)
			{
				strcpy(name, "安华");
				return strlen("安华");
			}
			else
			{
				strcpy(name, "On Wah");
				return strlen("On Wah");
			}
			break;
		case 54:
			if(language)
			{
				strcpy(name, "百花");
				return strlen("百花");
			}
			else
			{
				strcpy(name, "BAIHUA");
				return strlen("BAIHUA");
			}
			break;
		case 55:
			if(language)
			{
				strcpy(name, "百合花");
				return strlen("百合花");
			}
			else
			{
				strcpy(name, "BAIHEHUA");
				return strlen("BAIHEHUA");
			}
			break;
		case 56:
			if(language)
			{
				strcpy(name, "成都");
				return strlen("成都");
			}
			else
			{
				strcpy(name, "CHENGDU");
				return strlen("CHENGDU");
			}
			break;
		case 57:
			if(language)
			{
				strcpy(name, "长风");
				return strlen("长风");
			}
			else
			{
				strcpy(name, "Changfeng");
				return strlen("Changfeng");
			}
			break;
		case 58:
			if(language)
			{
				strcpy(name, "长飞");
				return strlen("长飞");
			}
			else
			{
				strcpy(name, "CHANGFEI");
				return strlen("CHANGFEI");
			}
			break;
		case 59:
			if(language)
			{
				strcpy(name, "长海");
				return strlen("长海");
			}
			else
			{
				strcpy(name, "Changhai");
				return strlen("Changhai");
			}
			break;
		case 60:
			if(language)
			{
				strcpy(name, "春兰");
				return strlen("春兰");
			}
			else
			{
				strcpy(name, "Chunlan");
				return strlen("Chunlan");
			}
			break;
		case 61:
			if(language)
			{
				strcpy(name, "春风");
				return strlen("春风");
			}
			else
			{
				strcpy(name, "Spring breeze");
				return strlen("Spring breeze");
			}
			break;
		case 62:
			if(language)
			{
				strcpy(name, "春笋");
				return strlen("春笋");
			}
			else
			{
				strcpy(name, "Chunsun");
				return strlen("Chunsun");
			}
			break;
		case 63:
			if(language)
			{
				strcpy(name, "东大");
				return strlen("东大");
			}
			else
			{
				strcpy(name, "Dongda");
				return strlen("Dongda");
			}
			break;
		case 64:
			if(language)
			{
				strcpy(name, "东海");
				return strlen("东海");
			}
			else
			{
				strcpy(name, "East China Sea");
				return strlen("East China Sea");
			}
			break;
		case 65:
			if(language)
			{
				strcpy(name, "飞鹿");
				return strlen("飞鹿");
			}
			else
			{
				strcpy(name, "Flying Deer");
				return strlen("Flying Deer");
			}
			break;
		case 66:
			if(language)
			{
				strcpy(name, "黄海美");
				return strlen("黄海美");
			}
			else
			{
				strcpy(name, "Yellow America");
				return strlen("Yellow America");
			}
			break;
		case 67:
			if(language)
			{
				strcpy(name, "海燕");
				return strlen("海燕");
			}
			else
			{
				strcpy(name, "Swallow");
				return strlen("Swallow");
			}
			break;
		case 68:
			if(language)
			{
				strcpy(name, "华日");
				return strlen("华日");
			}
			else
			{
				strcpy(name, "Huari");
				return strlen("Huari");
			}
			break;
		case 69:
			if(language)
			{
				strcpy(name, "篅");
				return strlen("篅");
			}
			else
			{
				strcpy(name, "Hai Le");
				return strlen("Hai Le");
			}
			break;
		case 70:
			if(language)
			{
				strcpy(name, "虹美");
				return strlen("虹美");
			}
			else
			{
				strcpy(name, "Hongmei");
				return strlen("Hongmei");
			}
			break;
		case 71:
			if(language)
			{
				strcpy(name, "海虹");
				return strlen("海虹");
			}
			else
			{
				strcpy(name, "Haihong");
				return strlen("Haihong");
			}
			break;
		case 72:
			if(language)
			{
				strcpy(name, "菊花");
				return strlen("菊花");
			}
			else
			{
				strcpy(name, "Chrysanthemum");
				return strlen("Chrysanthemum");
			}
			break;
		case 73:
			if(language)
			{
				strcpy(name, "韶峰");
				return strlen("韶峰");
			}
			else
			{
				strcpy(name, "Shaofeng");
				return strlen("Shaofeng");
			}
			break;
		case 74:
			if(language)
			{
				strcpy(name, "三键");
				return strlen("三键");
			}
			else
			{
				strcpy(name, "Three key");
				return strlen("Three key");
			}
			break;
		case 75:
			if(language)
			{
				strcpy(name, "威牌");
				return strlen("威牌");
			}
			else
			{
				strcpy(name, "Vira brand");
				return strlen("Vira brand");
			}
			break;
		case 76:
			if(language)
			{
				strcpy(name, "环宇");
				return strlen("环宇");
			}
			else
			{
				strcpy(name, "Huanyu");
				return strlen("Huanyu");
			}
			break;
		case 77:
			if(language)
			{
				strcpy(name, "皇冠");
				return strlen("皇冠");
			}
			else
			{
				strcpy(name, "Crowne");
				return strlen("Crowne");
			}
			break;
		case 78:
			if(language)
			{
				strcpy(name, "黄龙");
				return strlen("黄龙");
			}
			else
			{
				strcpy(name, "Huanglong");
				return strlen("Huanglong");
			}
			break;
		case 79:
			if(language)
			{
				strcpy(name, "奥林普");
				return strlen("奥林普");
			}
			else
			{
				strcpy(name, "AOLINPU");
				return strlen("AOLINPU");
			}
			break;
		case 80:
			if(language)
			{
				strcpy(name, "数源");
				return strlen("数源");
			}
			else
			{
				strcpy(name, "Soyea");
				return strlen("Soyea");
			}
			break;
		case 81:
			if(language)
			{
				strcpy(name, "富士通");
				return strlen("富士通");
			}
			else
			{
				strcpy(name, "Fujitsu");
				return strlen("Fujitsu");
			}
			break;
		case 82:
			if(language)
			{
				strcpy(name, "明基");
				return strlen("明基");
			}
			else
			{
				strcpy(name, "BenQ");
				return strlen("BenQ");
			}
			break;
		case 83:
			if(language)
			{
				strcpy(name, "三灵");
				return strlen("三灵");
			}
			else
			{
				strcpy(name, "Sanling");
				return strlen("Sanling");
			}
			break;
		case 84:
			if(language)
			{
				strcpy(name, "CYBEX");
				return strlen("CYBEX");
			}
			else
			{
				strcpy(name, "CYBEX");
				return strlen("CYBEX");
			}
			break;
		case 85:
			if(language)
			{
				strcpy(name, "金鹊");
				return strlen("金鹊");
			}
			else
			{
				strcpy(name, "Gold Magpie");
				return strlen("Gold Magpie");
			}
			break;
		case 86:
			if(language)
			{
				strcpy(name, "金凤");
				return strlen("金凤");
			}
			else
			{
				strcpy(name, "JINFENG");
				return strlen("JINFENG");
			}
			break;
		case 87:
			if(language)
			{
				strcpy(name, "天鹅");
				return strlen("天鹅");
			}
			else
			{
				strcpy(name, "Swan");
				return strlen("Swan");
			}
			break;
		case 88:
			if(language)
			{
				strcpy(name, "翔宇");
				return strlen("翔宇");
			}
			else
			{
				strcpy(name, "Xiangyu");
				return strlen("Xiangyu");
			}
			break;
		case 89:
			if(language)
			{
				strcpy(name, "金塔");
				return strlen("金塔");
			}
			else
			{
				strcpy(name, "Urn");
				return strlen("Urn");
			}
			break;
		case 90:
			if(language)
			{
				strcpy(name, "金雀");
				return strlen("金雀");
			}
			else
			{
				strcpy(name, "Broom");
				return strlen("Broom");
			}
			break;
		case 91:
			if(language)
			{
				strcpy(name, "凯歌");
				return strlen("凯歌");
			}
			else
			{
				strcpy(name, "Veuve");
				return strlen("Veuve");
			}
			break;
		case 92:
			if(language)
			{
				strcpy(name, "康力");
				return strlen("康力");
			}
			else
			{
				strcpy(name, "Kang Li");
				return strlen("Kang Li");
			}
			break;
		case 93:
			if(language)
			{
				strcpy(name, "康虹");
				return strlen("康虹");
			}
			else
			{
				strcpy(name, "Kang Hong");
				return strlen("Kang Hong");
			}
			break;
		case 94:
			if(language)
			{
				strcpy(name, "上海");
				return strlen("上海");
			}
			else
			{
				strcpy(name, "Shanghai");
				return strlen("Shanghai");
			}
			break;
		case 95:
			if(language)
			{
				strcpy(name, "快乐");
				return strlen("快乐");
			}
			else
			{
				strcpy(name, "Joyful");
				return strlen("Joyful");
			}
			break;
		case 96:
			if(language)
			{
				strcpy(name, "梦寐");
				return strlen("梦寐");
			}
			else
			{
				strcpy(name, "Dream");
				return strlen("Dream");
			}
			break;
		case 97:
			if(language)
			{
				strcpy(name, "南声");
				return strlen("南声");
			}
			else
			{
				strcpy(name, "South Sound");
				return strlen("South Sound");
			}
			break;
		case 98:
			if(language)
			{
				strcpy(name, "康艺");
				return strlen("康艺");
			}
			else
			{
				strcpy(name, "Kang Yi");
				return strlen("Kang Yi");
			}
			break;
		case 99:
			if(language)
			{
				strcpy(name, "狮龙");
				return strlen("狮龙");
			}
			else
			{
				strcpy(name, "Lion and dragon");
				return strlen("Lion and dragon");
			}
			break;
		case 100:
			if(language)
			{
				strcpy(name, "青岛");
				return strlen("青岛");
			}
			else
			{
				strcpy(name, "Qingdao");
				return strlen("Qingdao");
			}
			break;
		case 101:
			if(language)
			{
				strcpy(name, "康立");
				return strlen("康立");
			}
			else
			{
				strcpy(name, "Kang");
				return strlen("Kang");
			}
			break;
		case 102:
			if(language)
			{
				strcpy(name, "如意");
				return strlen("如意");
			}
			else
			{
				strcpy(name, "Ruyi");
				return strlen("Ruyi");
			}
			break;
		case 103:
			if(language)
			{
				strcpy(name, "孔雀");
				return strlen("孔雀");
			}
			else
			{
				strcpy(name, "Peacock");
				return strlen("Peacock");
			}
			break;
		case 104:
			if(language)
			{
				strcpy(name, "神彩");
				return strlen("神彩");
			}
			else
			{
				strcpy(name, "God Choi");
				return strlen("God Choi");
			}
			break;
		case 105:
			if(language)
			{
				strcpy(name, "龙江");
				return strlen("龙江");
			}
			else
			{
				strcpy(name, "Longjiang");
				return strlen("Longjiang");
			}
			break;
		case 106:
			if(language)
			{
				strcpy(name, "山茶");
				return strlen("山茶");
			}
			else
			{
				strcpy(name, "Camellia");
				return strlen("Camellia");
			}
			break;
		case 107:
			if(language)
			{
				strcpy(name, "利华");
				return strlen("利华");
			}
			else
			{
				strcpy(name, "Lihua");
				return strlen("Lihua");
			}
			break;
		case 108:
			if(language)
			{
				strcpy(name, "美乐");
				return strlen("美乐");
			}
			else
			{
				strcpy(name, "Melody");
				return strlen("Melody");
			}
			break;
		case 109:
			if(language)
			{
				strcpy(name, "星海");
				return strlen("星海");
			}
			else
			{
				strcpy(name, "Xinghai");
				return strlen("Xinghai");
			}
			break;
		case 110:
			if(language)
			{
				strcpy(name, "沈阳");
				return strlen("沈阳");
			}
			else
			{
				strcpy(name, "Shenyang");
				return strlen("Shenyang");
			}
			break;
		case 111:
			if(language)
			{
				strcpy(name, "塞格");
				return strlen("塞格");
			}
			else
			{
				strcpy(name, "Sager");
				return strlen("Sager");
			}
			break;
		case 112:
			if(language)
			{
				strcpy(name, "襄阳");
				return strlen("襄阳");
			}
			else
			{
				strcpy(name, "Yangyang");
				return strlen("Yangyang");
			}
			break;
		case 113:
			if(language)
			{
				strcpy(name, "松柏");
				return strlen("松柏");
			}
			else
			{
				strcpy(name, "Evergreen");
				return strlen("Evergreen");
			}
			break;
		case 114:
			if(language)
			{
				strcpy(name, "创佳");
				return strlen("创佳");
			}
			else
			{
				strcpy(name, "CHUANGJIA");
				return strlen("CHUANGJIA");
			}
			break;
		case 115:
			if(language)
			{
				strcpy(name, "日声");
				return strlen("日声");
			}
			else
			{
				strcpy(name, "Day Sound");
				return strlen("Day Sound");
			}
			break;
		case 116:
			if(language)
			{
				strcpy(name, "幸福");
				return strlen("幸福");
			}
			else
			{
				strcpy(name, "Happiness");
				return strlen("Happiness");
			}
			break;
		case 117:
			if(language)
			{
				strcpy(name, "优视达");
				return strlen("优视达");
			}
			else
			{
				strcpy(name, "As of gifted");
				return strlen("As of gifted");
			}
			break;
		case 118:
			if(language)
			{
				strcpy(name, "优拉纳斯");
				return strlen("优拉纳斯");
			}
			else
			{
				strcpy(name, "Excellent Lana Si");
				return strlen("Excellent Lana Si");
			}
			break;
		case 119:
			if(language)
			{
				strcpy(name, "新日松");
				return strlen("新日松");
			}
			else
			{
				strcpy(name, "New Day Song");
				return strlen("New Day Song");
			}
			break;
		case 120:
			if(language)
			{
				strcpy(name, "健生");
				return strlen("健生");
			}
			else
			{
				strcpy(name, "JIANSHENG");
				return strlen("JIANSHENG");
			}
			break;
		case 121:
			if(language)
			{
				strcpy(name, "WARUMAIA");
				return strlen("WARUMAIA");
			}
			else
			{
				strcpy(name, "WARUMAIA");
				return strlen("WARUMAIA");
			}
			break;
		case 122:
			if(language)
			{
				strcpy(name, "尼康");
				return strlen("尼康");
			}
			else
			{
				strcpy(name, "Nikon");
				return strlen("Nikon");
			}
			break;
		case 123:
			if(language)
			{
				strcpy(name, "富丽");
				return strlen("富丽");
			}
			else
			{
				strcpy(name, "Wealthy");
				return strlen("Wealthy");
			}
			break;
		case 124:
			if(language)
			{
				strcpy(name, "英特尔");
				return strlen("英特尔");
			}
			else
			{
				strcpy(name, "INTEL");
				return strlen("INTEL");
			}
			break;
		case 125:
			if(language)
			{
				strcpy(name, "莺歌");
				return strlen("莺歌");
			}
			else
			{
				strcpy(name, "Yingge");
				return strlen("Yingge");
			}
			break;
		case 126:
			if(language)
			{
				strcpy(name, "宇航");
				return strlen("宇航");
			}
			else
			{
				strcpy(name, "Aerospace");
				return strlen("Aerospace");
			}
			break;
		case 127:
			if(language)
			{
				strcpy(name, "永固");
				return strlen("永固");
			}
			else
			{
				strcpy(name, "Everlasting");
				return strlen("Everlasting");
			}
			break;
		case 128:
			if(language)
			{
				strcpy(name, "德基德克");
				return strlen("德基德克");
			}
			else
			{
				strcpy(name, "DIGITEC");
				return strlen("DIGITEC");
			}
			break;
		case 129:
			if(language)
			{
				strcpy(name, "永宝");
				return strlen("永宝");
			}
			else
			{
				strcpy(name, "Yongbao");
				return strlen("Yongbao");
			}
			break;
		case 130:
			if(language)
			{
				strcpy(name, "SUMO");
				return strlen("SUMO");
			}
			else
			{
				strcpy(name, "SUMO");
				return strlen("SUMO");
			}
			break;
		case 131:
			if(language)
			{
				strcpy(name, "珠海");
				return strlen("珠海");
			}
			else
			{
				strcpy(name, "Zhuhai");
				return strlen("Zhuhai");
			}
			break;
		case 132:
			if(language)
			{
				strcpy(name, "赣新");
				return strlen("赣新");
			}
			else
			{
				strcpy(name, "Jiangxi");
				return strlen("Jiangxi");
			}
			break;
		case 133:
			if(language)
			{
				strcpy(name, "华发");
				return strlen("华发");
			}
			else
			{
				strcpy(name, "Wafa");
				return strlen("Wafa");
			}
			break;
		case 134:
			if(language)
			{
				strcpy(name, "华强");
				return strlen("华强");
			}
			else
			{
				strcpy(name, "HUAQIANG");
				return strlen("HUAQIANG");
			}
			break;
		case 135:
			if(language)
			{
				strcpy(name, "康华");
				return strlen("康华");
			}
			else
			{
				strcpy(name, "KANGHUA");
				return strlen("KANGHUA");
			}
			break;
		case 136:
			if(language)
			{
				strcpy(name, "飞浪");
				return strlen("飞浪");
			}
			else
			{
				strcpy(name, "Fei Long");
				return strlen("Fei Long");
			}
			break;
		case 137:
			if(language)
			{
				strcpy(name, "日本胜利");
				return strlen("日本胜利");
			}
			else
			{
				strcpy(name, "JVC");
				return strlen("JVC");
			}
			break;
		case 138:
			if(language)
			{
				strcpy(name, "飞跃");
				return strlen("飞跃");
			}
			else
			{
				strcpy(name, "Leap");
				return strlen("Leap");
			}
			break;
		case 139:
			if(language)
			{
				strcpy(name, "鑫萌板");
				return strlen("鑫萌板");
			}
			else
			{
				strcpy(name, "Xin Meng board");
				return strlen("Xin Meng board");
			}
			break;
		case 140:
			if(language)
			{
				strcpy(name, "东王");
				return strlen("东王");
			}
			else
			{
				strcpy(name, "East King");
				return strlen("East King");
			}
			break;
		case 141:
			if(language)
			{
				strcpy(name, "康维");
				return strlen("康维");
			}
			else
			{
				strcpy(name, "Comverse");
				return strlen("Comverse");
			}
			break;
		case 142:
			if(language)
			{
				strcpy(name, "新思达");
				return strlen("新思达");
			}
			else
			{
				strcpy(name, "New Star");
				return strlen("New Star");
			}
			break;
		case 143:
			if(language)
			{
				strcpy(name, "塞河");
				return strlen("塞河");
			}
			else
			{
				strcpy(name, "SEYE");
				return strlen("SEYE");
			}
			break;
		case 144:
			if(language)
			{
				strcpy(name, "天庚板");
				return strlen("天庚板");
			}
			else
			{
				strcpy(name, "Day Geng board");
				return strlen("Day Geng board");
			}
			break;
		case 145:
			if(language)
			{
				strcpy(name, "日红");
				return strlen("日红");
			}
			else
			{
				strcpy(name, "Rihong");
				return strlen("Rihong");
			}
			break;
		case 146:
			if(language)
			{
				strcpy(name, "金鑫板");
				return strlen("金鑫板");
			}
			else
			{
				strcpy(name, "Jinxin board");
				return strlen("Jinxin board");
			}
			break;
		case 147:
			if(language)
			{
				strcpy(name, "汇佳板");
				return strlen("汇佳板");
			}
			else
			{
				strcpy(name, "Huijia board");
				return strlen("Huijia board");
			}
			break;
		case 148:
			if(language)
			{
				strcpy(name, "上广电");
				return strlen("上广电");
			}
			else
			{
				strcpy(name, "SVA");
				return strlen("SVA");
			}
			break;
		case 149:
			if(language)
			{
				strcpy(name, "天科板");
				return strlen("天科板");
			}
			else
			{
				strcpy(name, "Tianke board");
				return strlen("Tianke board");
			}
			break;
		case 150:
			if(language)
			{
				strcpy(name, "欧凌");
				return strlen("欧凌");
			}
			else
			{
				strcpy(name, "Ouling");
				return strlen("Ouling");
			}
			break;
		case 151:
			if(language)
			{
				strcpy(name, "昆仑");
				return strlen("昆仑");
			}
			else
			{
				strcpy(name, "Kunlun");
				return strlen("Kunlun");
			}
			break;
		case 152:
			if(language)
			{
				strcpy(name, "雅佳");
				return strlen("雅佳");
			}
			else
			{
				strcpy(name, "Akai");
				return strlen("Akai");
			}
			break;
		case 153:
			if(language)
			{
				strcpy(name, "日芝");
				return strlen("日芝");
			}
			else
			{
				strcpy(name, "Day Chicago");
				return strlen("Day Chicago");
			}
			break;
		case 154:
			if(language)
			{
				strcpy(name, "高士达");
				return strlen("高士达");
			}
			else
			{
				strcpy(name, "Goldstar");
				return strlen("Goldstar");
			}
			break;
		case 155:
			if(language)
			{
				strcpy(name, "彩凌");
				return strlen("彩凌");
			}
			else
			{
				strcpy(name, "Cai Ling");
				return strlen("Cai Ling");
			}
			break;
		case 156:
			if(language)
			{
				strcpy(name, "松电");
				return strlen("松电");
			}
			else
			{
				strcpy(name, "Loose electrical");
				return strlen("Loose electrical");
			}
			break;
		case 157:
			if(language)
			{
				strcpy(name, "胜利");
				return strlen("胜利");
			}
			else
			{
				strcpy(name, "Victory");
				return strlen("Victory");
			}
			break;
		case 158:
			if(language)
			{
				strcpy(name, "满天星");
				return strlen("满天星");
			}
			else
			{
				strcpy(name, "Gypsophila");
				return strlen("Gypsophila");
			}
			break;
		case 159:
			if(language)
			{
				strcpy(name, "安化");
				return strlen("安化");
			}
			else
			{
				strcpy(name, "ANHUA");
				return strlen("ANHUA");
			}
			break;
		case 160:
			if(language)
			{
				strcpy(name, "佰乐");
				return strlen("佰乐");
			}
			else
			{
				strcpy(name, "BAILE");
				return strlen("BAILE");
			}
			break;
		case 161:
			if(language)
			{
				strcpy(name, "宝生");
				return strlen("宝生");
			}
			else
			{
				strcpy(name, "BAOSHENG");
				return strlen("BAOSHENG");
			}
			break;
		case 162:
			if(language)
			{
				strcpy(name, "宝华视");
				return strlen("宝华视");
			}
			else
			{
				strcpy(name, "BaoHuaShi");
				return strlen("BaoHuaShi");
			}
			break;
		case 163:
			if(language)
			{
				strcpy(name, "彩铃");
				return strlen("彩铃");
			}
			else
			{
				strcpy(name, "CAILING");
				return strlen("CAILING");
			}
			break;
		case 164:
			if(language)
			{
				strcpy(name, "彩行");
				return strlen("彩行");
			}
			else
			{
				strcpy(name, "CAIXING");
				return strlen("CAIXING");
			}
			break;
		case 165:
			if(language)
			{
				strcpy(name, "长丰");
				return strlen("长丰");
			}
			else
			{
				strcpy(name, "CHANGFENG");
				return strlen("CHANGFENG");
			}
			break;
		case 166:
			if(language)
			{
				strcpy(name, "东杰");
				return strlen("东杰");
			}
			else
			{
				strcpy(name, "DUONGJIE");
				return strlen("DUONGJIE");
			}
			break;
		case 167:
			if(language)
			{
				strcpy(name, "大禹");
				return strlen("大禹");
			}
			else
			{
				strcpy(name, "DAYU");
				return strlen("DAYU");
			}
			break;
		case 168:
			if(language)
			{
				strcpy(name, "富力");
				return strlen("富力");
			}
			else
			{
				strcpy(name, "FULI");
				return strlen("FULI");
			}
			break;
		case 169:
			if(language)
			{
				strcpy(name, "淦鑫");
				return strlen("淦鑫");
			}
			else
			{
				strcpy(name, "GANXIN");
				return strlen("GANXIN");
			}
			break;
		case 170:
			if(language)
			{
				strcpy(name, "福建");
				return strlen("福建");
			}
			else
			{
				strcpy(name, "HITCHI FUFIAN");
				return strlen("HITCHI FUFIAN");
			}
			break;
		case 171:
			if(language)
			{
				strcpy(name, "瓦里");
				return strlen("瓦里");
			}
			else
			{
				strcpy(name, "HUARI");
				return strlen("HUARI");
			}
			break;
		case 172:
			if(language)
			{
				strcpy(name, "海盐");
				return strlen("海盐");
			}
			else
			{
				strcpy(name, "HAIYAN");
				return strlen("HAIYAN");
			}
			break;
		case 173:
			if(language)
			{
				strcpy(name, "黑尔");
				return strlen("黑尔");
			}
			else
			{
				strcpy(name, "HAILE");
				return strlen("HAILE");
			}
			break;
		case 174:
			if(language)
			{
				strcpy(name, "红梅");
				return strlen("红梅");
			}
			else
			{
				strcpy(name, "HONGMEI");
				return strlen("HONGMEI");
			}
			break;
		case 175:
			if(language)
			{
				strcpy(name, "慧佳伴");
				return strlen("慧佳伴");
			}
			else
			{
				strcpy(name, "HUIJIABAN");
				return strlen("HUIJIABAN");
			}
			break;
		case 176:
			if(language)
			{
				strcpy(name, "伙大科技");
				return strlen("伙大科技");
			}
			else
			{
				strcpy(name, "HUODAKEJI");
				return strlen("HUODAKEJI");
			}
			break;
		case 177:
			if(language)
			{
				strcpy(name, "帝国科恩");
				return strlen("帝国科恩");
			}
			else
			{
				strcpy(name, "IMPERIAL COWN");
				return strlen("IMPERIAL COWN");
			}
			break;
		case 178:
			if(language)
			{
				strcpy(name, "佳丽彩");
				return strlen("佳丽彩");
			}
			else
			{
				strcpy(name, "JIALICAI");
				return strlen("JIALICAI");
			}
			break;
		case 179:
			if(language)
			{
				strcpy(name, "金利普");
				return strlen("金利普");
			}
			else
			{
				strcpy(name, "JINGLIPU");
				return strlen("JINGLIPU");
			}
			break;
		case 180:
			if(language)
			{
				strcpy(name, "静海");
				return strlen("静海");
			}
			else
			{
				strcpy(name, "JINGHAI");
				return strlen("JINGHAI");
			}
			break;
		case 181:
			if(language)
			{
				strcpy(name, "康宏");
				return strlen("康宏");
			}
			else
			{
				strcpy(name, "KANGHONG");
				return strlen("KANGHONG");
			}
			break;
		case 182:
			if(language)
			{
				strcpy(name, "康利");
				return strlen("康利");
			}
			else
			{
				strcpy(name, "KANGLI");
				return strlen("KANGLI");
			}
			break;
		case 183:
			if(language)
			{
				strcpy(name, "甘基");
				return strlen("甘基");
			}
			else
			{
				strcpy(name, "KANGYI");
				return strlen("KANGYI");
			}
			break;
		case 184:
			if(language)
			{
				strcpy(name, "康威");
				return strlen("康威");
			}
			else
			{
				strcpy(name, "KANGWEI");
				return strlen("KANGWEI");
			}
			break;
		case 185:
			if(language)
			{
				strcpy(name, "丽华");
				return strlen("丽华");
			}
			else
			{
				strcpy(name, "LIHUA");
				return strlen("LIHUA");
			}
			break;
		case 186:
			if(language)
			{
				strcpy(name, "梦美");
				return strlen("梦美");
			}
			else
			{
				strcpy(name, "MENGMEI");
				return strlen("MENGMEI");
			}
			break;
		case 187:
			if(language)
			{
				strcpy(name, "南盛");
				return strlen("南盛");
			}
			else
			{
				strcpy(name, "NANSHENG");
				return strlen("NANSHENG");
			}
			break;
		case 188:
			if(language)
			{
				strcpy(name, "南堡");
				return strlen("南堡");
			}
			else
			{
				strcpy(name, "NANBAO");
				return strlen("NANBAO");
			}
			break;
		case 189:
			if(language)
			{
				strcpy(name, "欧琳");
				return strlen("欧琳");
			}
			else
			{
				strcpy(name, "OULIN");
				return strlen("OULIN");
			}
			break;
		case 190:
			if(language)
			{
				strcpy(name, "日升");
				return strlen("日升");
			}
			else
			{
				strcpy(name, "RISHENG");
				return strlen("RISHENG");
			}
			break;
		case 191:
			if(language)
			{
				strcpy(name, "神采");
				return strlen("神采");
			}
			else
			{
				strcpy(name, "SHENCAI");
				return strlen("SHENCAI");
			}
			break;
		case 192:
			if(language)
			{
				strcpy(name, "赛格");
				return strlen("赛格");
			}
			else
			{
				strcpy(name, "SAIGE");
				return strlen("SAIGE");
			}
			break;
		case 193:
			if(language)
			{
				strcpy(name, "三间");
				return strlen("三间");
			}
			else
			{
				strcpy(name, "SANJIAN");
				return strlen("SANJIAN");
			}
			break;
		case 194:
			if(language)
			{
				strcpy(name, "山水");
				return strlen("山水");
			}
			else
			{
				strcpy(name, "Landscape");
				return strlen("Landscape");
			}
			break;
		case 195:
			if(language)
			{
				strcpy(name, "剩菜");
				return strlen("剩菜");
			}
			else
			{
				strcpy(name, "SHENGCAI");
				return strlen("SHENGCAI");
			}
			break;
		case 196:
			if(language)
			{
				strcpy(name, "学院");
				return strlen("学院");
			}
			else
			{
				strcpy(name, "SHUYAN");
				return strlen("SHUYAN");
			}
			break;
		case 197:
			if(language)
			{
				strcpy(name, "生力");
				return strlen("生力");
			}
			else
			{
				strcpy(name, "SHENGLI");
				return strlen("SHENGLI");
			}
			break;
		case 198:
			if(language)
			{
				strcpy(name, "伍德");
				return strlen("伍德");
			}
			else
			{
				strcpy(name, "SHERWOOD");
				return strlen("SHERWOOD");
			}
			break;
		case 199:
			if(language)
			{
				strcpy(name, "TIANKEBAN");
				return strlen("TIANKEBAN");
			}
			else
			{
				strcpy(name, "TIANKEBAN");
				return strlen("TIANKEBAN");
			}
			break;
		case 200:
			if(language)
			{
				strcpy(name, "TIANGENGBAN");
				return strlen("TIANGENGBAN");
			}
			else
			{
				strcpy(name, "TIANGENGBAN");
				return strlen("TIANGENGBAN");
			}
			break;
		case 201:
			if(language)
			{
				strcpy(name, "台山");
				return strlen("台山");
			}
			else
			{
				strcpy(name, "TAISHAN");
				return strlen("TAISHAN");
			}
			break;
		case 202:
			if(language)
			{
				strcpy(name, "同光");
				return strlen("同光");
			}
			else
			{
				strcpy(name, "TONGGUANG");
				return strlen("TONGGUANG");
			}
			break;
		case 203:
			if(language)
			{
				strcpy(name, "东洋");
				return strlen("东洋");
			}
			else
			{
				strcpy(name, "TOBO");
				return strlen("TOBO");
			}
			break;
		case 204:
			if(language)
			{
				strcpy(name, "威派");
				return strlen("威派");
			}
			else
			{
				strcpy(name, "WEIPAI");
				return strlen("WEIPAI");
			}
			break;
		case 205:
			if(language)
			{
				strcpy(name, "XINAGHAI");
				return strlen("XINAGHAI");
			}
			else
			{
				strcpy(name, "XINAGHAI");
				return strlen("XINAGHAI");
			}
			break;
		case 206:
			if(language)
			{
				strcpy(name, "XINGMENBAN");
				return strlen("XINGMENBAN");
			}
			else
			{
				strcpy(name, "XINGMENBAN");
				return strlen("XINGMENBAN");
			}
			break;
		case 207:
			if(language)
			{
				strcpy(name, "新抚");
				return strlen("新抚");
			}
			else
			{
				strcpy(name, "XINFU");
				return strlen("XINFU");
			}
			break;
		case 208:
			if(language)
			{
				strcpy(name, "XINRISONG");
				return strlen("XINRISONG");
			}
			else
			{
				strcpy(name, "XINRISONG");
				return strlen("XINRISONG");
			}
			break;
		case 209:
			if(language)
			{
				strcpy(name, "余杭");
				return strlen("余杭");
			}
			else
			{
				strcpy(name, "YUHANG");
				return strlen("YUHANG");
			}
			break;
		case 210:
			if(language)
			{
				strcpy(name, "永保");
				return strlen("永保");
			}
			else
			{
				strcpy(name, "YONGBAO");
				return strlen("YONGBAO");
			}
			break;
		case 211:
			if(language)
			{
				strcpy(name, "YOULANASI");
				return strlen("YOULANASI");
			}
			else
			{
				strcpy(name, "YOULANASI");
				return strlen("YOULANASI");
			}
			break;
		case 212:
			if(language)
			{
				strcpy(name, "优斯达");
				return strlen("优斯达");
			}
			else
			{
				strcpy(name, "YOUSIDA");
				return strlen("YOUSIDA");
			}
			break;
		case 213:
			if(language)
			{
				strcpy(name, "888品牌");
				return strlen("888品牌");
			}
			else
			{
				strcpy(name, "888");
				return strlen("888");
			}
			break;
		case 214:
			if(language)
			{
				strcpy(name, "丽格美");
				return strlen("丽格美");
			}
			else
			{
				strcpy(name, "lgm,Le42A3030");
				return strlen("lgm,Le42A3030");
			}
			break;
		case 215:
			if(language)
			{
				strcpy(name, "AOC电视T2242we");
				return strlen("AOC电视T2242we");
			}
			else
			{
				strcpy(name, "yuyu168");
				return strlen("yuyu168");
			}
			break;
		case 216:
			if(language)
			{
				strcpy(name, "天敏电视");
				return strlen("天敏电视");
			}
			else
			{
				strcpy(name, "Days Min TV");
				return strlen("Days Min TV");
			}
			break;
		case 217:
			if(language)
			{
				strcpy(name, "内视电视卡");
				return strlen("内视电视卡");
			}
			else
			{
				strcpy(name, "fzfe");
				return strlen("fzfe");
			}
			break;
		case 218:
			if(language)
			{
				strcpy(name, "LT360W");
				return strlen("LT360W");
			}
			else
			{
				strcpy(name, "sbmtv");
				return strlen("sbmtv");
			}
			break;
		case 219:
			if(language)
			{
				strcpy(name, "10moons");
				return strlen("10moons");
			}
			else
			{
				strcpy(name, "10moons");
				return strlen("10moons");
			}
			break;
		case 220:
			if(language)
			{
				strcpy(name, "索映电视");
				return strlen("索映电视");
			}
			else
			{
				strcpy(name, "htboy");
				return strlen("htboy");
			}
			break;
		case 221:
			if(language)
			{
				strcpy(name, "先锋");
				return strlen("先锋");
			}
			else
			{
				strcpy(name, "Pioneer TV");
				return strlen("Pioneer TV");
			}
			break;
		case 222:
			if(language)
			{
				strcpy(name, "夏新");
				return strlen("夏新");
			}
			else
			{
				strcpy(name, "Amoi TV");
				return strlen("Amoi TV");
			}
			break;
		case 223:
			if(language)
			{
				strcpy(name, "LC-22ST1");
				return strlen("LC-22ST1");
			}
			else
			{
				strcpy(name, "lingxf");
				return strlen("lingxf");
			}
			break;
		case 224:
			if(language)
			{
				strcpy(name, "三环宇");
				return strlen("三环宇");
			}
			else
			{
				strcpy(name, "Universal");
				return strlen("Universal");
			}
			break;
		case 225:
			if(language)
			{
				strcpy(name, "三穗");
				return strlen("三穗");
			}
			else
			{
				strcpy(name, "SANSUI");
				return strlen("SANSUI");
			}
			break;
		case 226:
			if(language)
			{
				strcpy(name, "嘉华");
				return strlen("嘉华");
			}
			else
			{
				strcpy(name, "Jiahua");
				return strlen("Jiahua");
			}
			break;
		case 227:
			if(language)
			{
				strcpy(name, "通广");
				return strlen("通广");
			}
			else
			{
				strcpy(name, "Tongguang");
				return strlen("Tongguang");
			}
			break;
		case 228:
			if(language)
			{
				strcpy(name, "建盛");
				return strlen("建盛");
			}
			else
			{
				strcpy(name, "JIAN SHENG");
				return strlen("JIAN SHENG");
			}
			break;
		case 229:
			if(language)
			{
				strcpy(name, "东林");
				return strlen("东林");
			}
			else
			{
				strcpy(name, "DONGLIN");
				return strlen("DONGLIN");
			}
			break;
		case 230:
			if(language)
			{
				strcpy(name, "冠捷");
				return strlen("冠捷");
			}
			else
			{
				strcpy(name, "AOC");
				return strlen("AOC");
			}
			break;
		case 231:
			if(language)
			{
				strcpy(name, "金正");
				return strlen("金正");
			}
			else
			{
				strcpy(name, "Nintaus");
				return strlen("Nintaus");
			}
			break;
		case 232:
			if(language)
			{
				strcpy(name, "宏基");
				return strlen("宏基");
			}
			else
			{
				strcpy(name, "Acer");
				return strlen("Acer");
			}
			break;
		case 233:
			if(language)
			{
				strcpy(name, "极致");
				return strlen("极致");
			}
			else
			{
				strcpy(name, "Acme");
				return strlen("Acme");
			}
			break;
		case 234:
			if(language)
			{
				strcpy(name, "阿尔茨");
				return strlen("阿尔茨");
			}
			else
			{
				strcpy(name, "ADC");
				return strlen("ADC");
			}
			break;
		case 235:
			if(language)
			{
				strcpy(name, "海军上将");
				return strlen("海军上将");
			}
			else
			{
				strcpy(name, "Admiral");
				return strlen("Admiral");
			}
			break;
		case 236:
			if(language)
			{
				strcpy(name, "基督降临");
				return strlen("基督降临");
			}
			else
			{
				strcpy(name, "Advent");
				return strlen("Advent");
			}
			break;
		case 237:
			if(language)
			{
				strcpy(name, "阿加齐");
				return strlen("阿加齐");
			}
			else
			{
				strcpy(name, "Agazi");
				return strlen("Agazi");
			}
			break;
		case 238:
			if(language)
			{
				strcpy(name, "爱子 ");
				return strlen("爱子 ");
			}
			else
			{
				strcpy(name, "Aiko");
				return strlen("Aiko");
			}
			break;
		case 239:
			if(language)
			{
				strcpy(name, "秋叶");
				return strlen("秋叶");
			}
			else
			{
				strcpy(name, "Akiba");
				return strlen("Akiba");
			}
			break;
		case 240:
			if(language)
			{
				strcpy(name, "樱花");
				return strlen("樱花");
			}
			else
			{
				strcpy(name, "Akura");
				return strlen("Akura");
			}
			break;
		case 241:
			if(language)
			{
				strcpy(name, "阿尔巴");
				return strlen("阿尔巴");
			}
			else
			{
				strcpy(name, "Alba");
				return strlen("Alba");
			}
			break;
		case 242:
			if(language)
			{
				strcpy(name, "Alkos");
				return strlen("Alkos");
			}
			else
			{
				strcpy(name, "Alkos");
				return strlen("Alkos");
			}
			break;
		case 243:
			if(language)
			{
				strcpy(name, "Allorgan");
				return strlen("Allorgan");
			}
			else
			{
				strcpy(name, "Allorgan");
				return strlen("Allorgan");
			}
			break;
		case 244:
			if(language)
			{
				strcpy(name, "阿姆斯特德");
				return strlen("阿姆斯特德");
			}
			else
			{
				strcpy(name, "Amstrad");
				return strlen("Amstrad");
			}
			break;
		case 245:
			if(language)
			{
				strcpy(name, "Anitech");
				return strlen("Anitech");
			}
			else
			{
				strcpy(name, "Anitech");
				return strlen("Anitech");
			}
			break;
		case 246:
			if(language)
			{
				strcpy(name, "埃佩克斯");
				return strlen("埃佩克斯");
			}
			else
			{
				strcpy(name, "Apex");
				return strlen("Apex");
			}
			break;
		case 247:
			if(language)
			{
				strcpy(name, "亚比央");
				return strlen("亚比央");
			}
			else
			{
				strcpy(name, "ASA");
				return strlen("ASA");
			}
			break;
		case 248:
			if(language)
			{
				strcpy(name, "明日香");
				return strlen("明日香");
			}
			else
			{
				strcpy(name, "AsuKa");
				return strlen("AsuKa");
			}
			break;
		case 249:
			if(language)
			{
				strcpy(name, "华硕");
				return strlen("华硕");
			}
			else
			{
				strcpy(name, "Asus");
				return strlen("Asus");
			}
			break;
		case 250:
			if(language)
			{
				strcpy(name, "亚特兰大");
				return strlen("亚特兰大");
			}
			else
			{
				strcpy(name, "Atlantic");
				return strlen("Atlantic");
			}
			break;
		case 251:
			if(language)
			{
				strcpy(name, "音频动力学 ");
				return strlen("音频动力学 ");
			}
			else
			{
				strcpy(name, "Audio Dynamics");
				return strlen("Audio Dynamics");
			}
			break;
		case 252:
			if(language)
			{
				strcpy(name, "Audiosonic");
				return strlen("Audiosonic");
			}
			else
			{
				strcpy(name, "Audiosonic");
				return strlen("Audiosonic");
			}
			break;
		case 253:
			if(language)
			{
				strcpy(name, "Audiovox");
				return strlen("Audiovox");
			}
			else
			{
				strcpy(name, "Audiovox");
				return strlen("Audiovox");
			}
			break;
		case 254:
			if(language)
			{
				strcpy(name, "奥特华 ");
				return strlen("奥特华 ");
			}
			else
			{
				strcpy(name, "Autovox");
				return strlen("Autovox");
			}
			break;
		case 255:
			if(language)
			{
				strcpy(name, "Avol");
				return strlen("Avol");
			}
			else
			{
				strcpy(name, "Avol");
				return strlen("Avol");
			}
			break;
		case 256:
			if(language)
			{
				strcpy(name, "贝尔德");
				return strlen("贝尔德");
			}
			else
			{
				strcpy(name, "Baird");
				return strlen("Baird");
			}
			break;
		case 257:
			if(language)
			{
				strcpy(name, "基本线 ");
				return strlen("基本线 ");
			}
			else
			{
				strcpy(name, "Baisic Line");
				return strlen("Baisic Line");
			}
			break;
		case 258:
			if(language)
			{
				strcpy(name, "贝科 ");
				return strlen("贝科 ");
			}
			else
			{
				strcpy(name, "Beko");
				return strlen("Beko");
			}
			break;
		case 259:
			if(language)
			{
				strcpy(name, "贝斯特");
				return strlen("贝斯特");
			}
			else
			{
				strcpy(name, "Best");
				return strlen("Best");
			}
			break;
		case 260:
			if(language)
			{
				strcpy(name, "吉星");
				return strlen("吉星");
			}
			else
			{
				strcpy(name, "Bestar");
				return strlen("Bestar");
			}
			break;
		case 261:
			if(language)
			{
				strcpy(name, "黑钻石");
				return strlen("黑钻石");
			}
			else
			{
				strcpy(name, "Black Diamond");
				return strlen("Black Diamond");
			}
			break;
		case 262:
			if(language)
			{
				strcpy(name, "勃兰特");
				return strlen("勃兰特");
			}
			else
			{
				strcpy(name, "Brandt");
				return strlen("Brandt");
			}
			break;
		case 263:
			if(language)
			{
				strcpy(name, "Broksonic");
				return strlen("Broksonic");
			}
			else
			{
				strcpy(name, "Broksonic");
				return strlen("Broksonic");
			}
			break;
		case 264:
			if(language)
			{
				strcpy(name, "Brokwood");
				return strlen("Brokwood");
			}
			else
			{
				strcpy(name, "Brokwood");
				return strlen("Brokwood");
			}
			break;
		case 265:
			if(language)
			{
				strcpy(name, "布什");
				return strlen("布什");
			}
			else
			{
				strcpy(name, "Bush");
				return strlen("Bush");
			}
			break;
		case 266:
			if(language)
			{
				strcpy(name, "圣餐杯");
				return strlen("圣餐杯");
			}
			else
			{
				strcpy(name, "Calix");
				return strlen("Calix");
			}
			break;
		case 267:
			if(language)
			{
				strcpy(name, "蜡烛");
				return strlen("蜡烛");
			}
			else
			{
				strcpy(name, "Candle");
				return strlen("Candle");
			}
			break;
		case 268:
			if(language)
			{
				strcpy(name, "Capsonic");
				return strlen("Capsonic");
			}
			else
			{
				strcpy(name, "Capsonic");
				return strlen("Capsonic");
			}
			break;
		case 269:
			if(language)
			{
				strcpy(name, "家乐福");
				return strlen("家乐福");
			}
			else
			{
				strcpy(name, "Carrefour");
				return strlen("Carrefour");
			}
			break;
		case 270:
			if(language)
			{
				strcpy(name, "克拉珀龙");
				return strlen("克拉珀龙");
			}
			else
			{
				strcpy(name, "CCE");
				return strlen("CCE");
			}
			break;
		case 271:
			if(language)
			{
				strcpy(name, "中枢");
				return strlen("中枢");
			}
			else
			{
				strcpy(name, "Centrum");
				return strlen("Centrum");
			}
			break;
		case 272:
			if(language)
			{
				strcpy(name, "电器公司");
				return strlen("电器公司");
			}
			else
			{
				strcpy(name, "CGE");
				return strlen("CGE");
			}
			break;
		case 273:
			if(language)
			{
				strcpy(name, "奇美");
				return strlen("奇美");
			}
			else
			{
				strcpy(name, "Chimei");
				return strlen("Chimei");
			}
			break;
		case 274:
			if(language)
			{
				strcpy(name, "cllL");
				return strlen("cllL");
			}
			else
			{
				strcpy(name, "cllL");
				return strlen("cllL");
			}
			break;
		case 275:
			if(language)
			{
				strcpy(name, "辛姆莱 ");
				return strlen("辛姆莱 ");
			}
			else
			{
				strcpy(name, "Cimline");
				return strlen("Cimline");
			}
			break;
		case 276:
			if(language)
			{
				strcpy(name, "西铁城牌");
				return strlen("西铁城牌");
			}
			else
			{
				strcpy(name, "Citizen");
				return strlen("Citizen");
			}
			break;
		case 277:
			if(language)
			{
				strcpy(name, "clatronic");
				return strlen("clatronic");
			}
			else
			{
				strcpy(name, "clatronic");
				return strlen("clatronic");
			}
			break;
		case 278:
			if(language)
			{
				strcpy(name, "科比");
				return strlen("科比");
			}
			else
			{
				strcpy(name, "Coby");
				return strlen("Coby");
			}
			break;
		case 279:
			if(language)
			{
				strcpy(name, "康比特");
				return strlen("康比特");
			}
			else
			{
				strcpy(name, "Combitech");
				return strlen("Combitech");
			}
			break;
		case 280:
			if(language)
			{
				strcpy(name, "协奏曲");
				return strlen("协奏曲");
			}
			else
			{
				strcpy(name, "Concerto");
				return strlen("Concerto");
			}
			break;
		case 281:
			if(language)
			{
				strcpy(name, "神鹰");
				return strlen("神鹰");
			}
			else
			{
				strcpy(name, "Condor");
				return strlen("Condor");
			}
			break;
		case 282:
			if(language)
			{
				strcpy(name, "康泰克");
				return strlen("康泰克");
			}
			else
			{
				strcpy(name, "Contec");
				return strlen("Contec");
			}
			break;
		case 283:
			if(language)
			{
				strcpy(name, "王冠");
				return strlen("王冠");
			}
			else
			{
				strcpy(name, "Crown");
				return strlen("Crown");
			}
			break;
		case 284:
			if(language)
			{
				strcpy(name, "司令");
				return strlen("司令");
			}
			else
			{
				strcpy(name, "Cybercom");
				return strlen("Cybercom");
			}
			break;
		case 285:
			if(language)
			{
				strcpy(name, "金卫信");
				return strlen("金卫信");
			}
			else
			{
				strcpy(name, "cybermax");
				return strlen("cybermax");
			}
			break;
		case 286:
			if(language)
			{
				strcpy(name, "cybermaxx");
				return strlen("cybermaxx");
			}
			else
			{
				strcpy(name, "cybermaxx");
				return strlen("cybermaxx");
			}
			break;
		case 287:
			if(language)
			{
				strcpy(name, "Cytron");
				return strlen("Cytron");
			}
			else
			{
				strcpy(name, "Cytron");
				return strlen("Cytron");
			}
			break;
		case 288:
			if(language)
			{
				strcpy(name, "Dantax");
				return strlen("Dantax");
			}
			else
			{
				strcpy(name, "Dantax");
				return strlen("Dantax");
			}
			break;
		case 289:
			if(language)
			{
				strcpy(name, "德格拉夫");
				return strlen("德格拉夫");
			}
			else
			{
				strcpy(name, "De Graaf");
				return strlen("De Graaf");
			}
			break;
		case 290:
			if(language)
			{
				strcpy(name, "迪卡");
				return strlen("迪卡");
			}
			else
			{
				strcpy(name, "Decca");
				return strlen("Decca");
			}
			break;
		case 291:
			if(language)
			{
				strcpy(name, "戴尔");
				return strlen("戴尔");
			}
			else
			{
				strcpy(name, "Dell");
				return strlen("Dell");
			}
			break;
		case 292:
			if(language)
			{
				strcpy(name, "天龙");
				return strlen("天龙");
			}
			else
			{
				strcpy(name, "Denon");
				return strlen("Denon");
			}
			break;
		case 293:
			if(language)
			{
				strcpy(name, "Digitor");
				return strlen("Digitor");
			}
			else
			{
				strcpy(name, "Digitor");
				return strlen("Digitor");
			}
			break;
		case 294:
			if(language)
			{
				strcpy(name, "迪希");
				return strlen("迪希");
			}
			else
			{
				strcpy(name, "Dixi");
				return strlen("Dixi");
			}
			break;
		case 295:
			if(language)
			{
				strcpy(name, "杜阿河");
				return strlen("杜阿河");
			}
			else
			{
				strcpy(name, "Dua");
				return strlen("Dua");
			}
			break;
		case 296:
			if(language)
			{
				strcpy(name, "双重");
				return strlen("双重");
			}
			else
			{
				strcpy(name, "Dual");
				return strlen("Dual");
			}
			break;
		case 297:
			if(language)
			{
				strcpy(name, "杜蒙特");
				return strlen("杜蒙特");
			}
			else
			{
				strcpy(name, "Dumont");
				return strlen("Dumont");
			}
			break;
		case 298:
			if(language)
			{
				strcpy(name, "尼克斯");
				return strlen("尼克斯");
			}
			else
			{
				strcpy(name, "Dynex");
				return strlen("Dynex");
			}
			break;
		case 299:
			if(language)
			{
				strcpy(name, "传真");
				return strlen("传真");
			}
			else
			{
				strcpy(name, "Electrograph");
				return strlen("Electrograph");
			}
			break;
		case 300:
			if(language)
			{
				strcpy(name, "Electrohome");
				return strlen("Electrohome");
			}
			else
			{
				strcpy(name, "Electrohome");
				return strlen("Electrohome");
			}
			break;
		case 301:
			if(language)
			{
				strcpy(name, "电子");
				return strlen("电子");
			}
			else
			{
				strcpy(name, "Electron");
				return strlen("Electron");
			}
			break;
		case 302:
			if(language)
			{
				strcpy(name, "电响");
				return strlen("电响");
			}
			else
			{
				strcpy(name, "Electrophonic");
				return strlen("Electrophonic");
			}
			break;
		case 303:
			if(language)
			{
				strcpy(name, "元电子");
				return strlen("元电子");
			}
			else
			{
				strcpy(name, "Element Electronics");
				return strlen("Element Electronics");
			}
			break;
		case 304:
			if(language)
			{
				strcpy(name, "埃琳");
				return strlen("埃琳");
			}
			else
			{
				strcpy(name, "Elin");
				return strlen("Elin");
			}
			break;
		case 305:
			if(language)
			{
				strcpy(name, "爱默生");
				return strlen("爱默生");
			}
			else
			{
				strcpy(name, "Emerson");
				return strlen("Emerson");
			}
			break;
		case 306:
			if(language)
			{
				strcpy(name, "欧元");
				return strlen("欧元");
			}
			else
			{
				strcpy(name, "Euro-Feel");
				return strlen("Euro-Feel");
			}
			break;
		case 307:
			if(language)
			{
				strcpy(name, "Euroline");
				return strlen("Euroline");
			}
			else
			{
				strcpy(name, "Euroline");
				return strlen("Euroline");
			}
			break;
		case 308:
			if(language)
			{
				strcpy(name, "专家");
				return strlen("专家");
			}
			else
			{
				strcpy(name, "Expert");
				return strlen("Expert");
			}
			break;
		case 309:
			if(language)
			{
				strcpy(name, "六角瓦");
				return strlen("六角瓦");
			}
			else
			{
				strcpy(name, "Favi");
				return strlen("Favi");
			}
			break;
		case 310:
			if(language)
			{
				strcpy(name, "芬纳");
				return strlen("芬纳");
			}
			else
			{
				strcpy(name, "Fenner");
				return strlen("Fenner");
			}
			break;
		case 311:
			if(language)
			{
				strcpy(name, "保真度");
				return strlen("保真度");
			}
			else
			{
				strcpy(name, "Fidelty");
				return strlen("Fidelty");
			}
			break;
		case 312:
			if(language)
			{
				strcpy(name, "芬兰");
				return strlen("芬兰");
			}
			else
			{
				strcpy(name, "Finlandia");
				return strlen("Finlandia");
			}
			break;
		case 313:
			if(language)
			{
				strcpy(name, "Finlux");
				return strlen("Finlux");
			}
			else
			{
				strcpy(name, "Finlux");
				return strlen("Finlux");
			}
			break;
		case 314:
			if(language)
			{
				strcpy(name, "费希尔");
				return strlen("费希尔");
			}
			else
			{
				strcpy(name, "Fisher");
				return strlen("Fisher");
			}
			break;
		case 315:
			if(language)
			{
				strcpy(name, "弗林特");
				return strlen("弗林特");
			}
			else
			{
				strcpy(name, "Flint");
				return strlen("Flint");
			}
			break;
		case 316:
			if(language)
			{
				strcpy(name, "福尔门蒂");
				return strlen("福尔门蒂");
			}
			else
			{
				strcpy(name, "Formenti");
				return strlen("Formenti");
			}
			break;
		case 317:
			if(language)
			{
				strcpy(name, "堡垒");
				return strlen("堡垒");
			}
			else
			{
				strcpy(name, "Fortress");
				return strlen("Fortress");
			}
			break;
		case 318:
			if(language)
			{
				strcpy(name, "富士康");
				return strlen("富士康");
			}
			else
			{
				strcpy(name, "Foxconn");
				return strlen("Foxconn");
			}
			break;
		case 319:
			if(language)
			{
				strcpy(name, "藤丸");
				return strlen("藤丸");
			}
			else
			{
				strcpy(name, "Fujimaru");
				return strlen("Fujimaru");
			}
			break;
		case 320:
			if(language)
			{
				strcpy(name, "船井");
				return strlen("船井");
			}
			else
			{
				strcpy(name, "Funai");
				return strlen("Funai");
			}
			break;
		case 321:
			if(language)
			{
				strcpy(name, "银河系");
				return strlen("银河系");
			}
			else
			{
				strcpy(name, "Galaxy");
				return strlen("Galaxy");
			}
			break;
		case 322:
			if(language)
			{
				strcpy(name, "盖特韦");
				return strlen("盖特韦");
			}
			else
			{
				strcpy(name, "Gateway");
				return strlen("Gateway");
			}
			break;
		case 323:
			if(language)
			{
				strcpy(name, "游戏机");
				return strlen("游戏机");
			}
			else
			{
				strcpy(name, "GBC");
				return strlen("GBC");
			}
			break;
		case 324:
			if(language)
			{
				strcpy(name, "GEC");
				return strlen("GEC");
			}
			else
			{
				strcpy(name, "GEC");
				return strlen("GEC");
			}
			break;
		case 325:
			if(language)
			{
				strcpy(name, "高仕达");
				return strlen("高仕达");
			}
			else
			{
				strcpy(name, "Goldstar");
				return strlen("Goldstar");
			}
			break;
		case 326:
			if(language)
			{
				strcpy(name, "古德曼");
				return strlen("古德曼");
			}
			else
			{
				strcpy(name, "Goodmans");
				return strlen("Goodmans");
			}
			break;
		case 327:
			if(language)
			{
				strcpy(name, "塞尔维亚");
				return strlen("塞尔维亚");
			}
			else
			{
				strcpy(name, "Gorenje");
				return strlen("Gorenje");
			}
			break;
		case 328:
			if(language)
			{
				strcpy(name, "宏处理机");
				return strlen("宏处理机");
			}
			else
			{
				strcpy(name, "GPM");
				return strlen("GPM");
			}
			break;
		case 329:
			if(language)
			{
				strcpy(name, "格拉纳达");
				return strlen("格拉纳达");
			}
			else
			{
				strcpy(name, "Granada");
				return strlen("Granada");
			}
			break;
		case 330:
			if(language)
			{
				strcpy(name, "格兰丁");
				return strlen("格兰丁");
			}
			else
			{
				strcpy(name, "Grandin");
				return strlen("Grandin");
			}
			break;
		case 331:
			if(language)
			{
				strcpy(name, "根德");
				return strlen("根德");
			}
			else
			{
				strcpy(name, "Grundig");
				return strlen("Grundig");
			}
			break;
		case 332:
			if(language)
			{
				strcpy(name, "百禾慧");
				return strlen("百禾慧");
			}
			else
			{
				strcpy(name, "H&B");
				return strlen("H&B");
			}
			break;
		case 333:
			if(language)
			{
				strcpy(name, "瀚斯宝丽");
				return strlen("瀚斯宝丽");
			}
			else
			{
				strcpy(name, "Hanspree");
				return strlen("Hanspree");
			}
			break;
		case 334:
			if(language)
			{
				strcpy(name, "汉萨");
				return strlen("汉萨");
			}
			else
			{
				strcpy(name, "Hanseatic");
				return strlen("Hanseatic");
			}
			break;
		case 335:
			if(language)
			{
				strcpy(name, "Hantarex");
				return strlen("Hantarex");
			}
			else
			{
				strcpy(name, "Hantarex");
				return strlen("Hantarex");
			}
			break;
		case 336:
			if(language)
			{
				strcpy(name, "HCM");
				return strlen("HCM");
			}
			else
			{
				strcpy(name, "HCM");
				return strlen("HCM");
			}
			break;
		case 337:
			if(language)
			{
				strcpy(name, "禾联");
				return strlen("禾联");
			}
			else
			{
				strcpy(name, "Heran");
				return strlen("Heran");
			}
			break;
		case 338:
			if(language)
			{
				strcpy(name, "惠普");
				return strlen("惠普");
			}
			else
			{
				strcpy(name, "Hewlett Packard");
				return strlen("Hewlett Packard");
			}
			break;
		case 339:
			if(language)
			{
				strcpy(name, "成果");
				return strlen("成果");
			}
			else
			{
				strcpy(name, "Hinari");
				return strlen("Hinari");
			}
			break;
		case 340:
			if(language)
			{
				strcpy(name, "Hisawa");
				return strlen("Hisawa");
			}
			else
			{
				strcpy(name, "Hisawa");
				return strlen("Hisawa");
			}
			break;
		case 341:
			if(language)
			{
				strcpy(name, "国际劳工组织");
				return strlen("国际劳工组织");
			}
			else
			{
				strcpy(name, "ILO");
				return strlen("ILO");
			}
			break;
		case 342:
			if(language)
			{
				strcpy(name, "因皮里尔");
				return strlen("因皮里尔");
			}
			else
			{
				strcpy(name, "Imperial");
				return strlen("Imperial");
			}
			break;
		case 343:
			if(language)
			{
				strcpy(name, "创新");
				return strlen("创新");
			}
			else
			{
				strcpy(name, "Innovation");
				return strlen("Innovation");
			}
			break;
		case 344:
			if(language)
			{
				strcpy(name, "Insiginia");
				return strlen("Insiginia");
			}
			else
			{
				strcpy(name, "Insiginia");
				return strlen("Insiginia");
			}
			break;
		case 345:
			if(language)
			{
				strcpy(name, "徽章");
				return strlen("徽章");
			}
			else
			{
				strcpy(name, "Insignia");
				return strlen("Insignia");
			}
			break;
		case 346:
			if(language)
			{
				strcpy(name, "Intertronic");
				return strlen("Intertronic");
			}
			else
			{
				strcpy(name, "Intertronic");
				return strlen("Intertronic");
			}
			break;
		case 347:
			if(language)
			{
				strcpy(name, "电话电信公司");
				return strlen("电话电信公司");
			}
			else
			{
				strcpy(name, "ITT");
				return strlen("ITT");
			}
			break;
		case 348:
			if(language)
			{
				strcpy(name, "教学电视");
				return strlen("教学电视");
			}
			else
			{
				strcpy(name, "ITV");
				return strlen("ITV");
			}
			break;
		case 349:
			if(language)
			{
				strcpy(name, "JCPenney");
				return strlen("JCPenney");
			}
			else
			{
				strcpy(name, "JCPenney");
				return strlen("JCPenney");
			}
			break;
		case 350:
			if(language)
			{
				strcpy(name, "国会");
				return strlen("国会");
			}
			else
			{
				strcpy(name, "JEC");
				return strlen("JEC");
			}
			break;
		case 351:
			if(language)
			{
				strcpy(name, "延森");
				return strlen("延森");
			}
			else
			{
				strcpy(name, "Jensen");
				return strlen("Jensen");
			}
			break;
		case 352:
			if(language)
			{
				strcpy(name, "卡普什");
				return strlen("卡普什");
			}
			else
			{
				strcpy(name, "Kapsch");
				return strlen("Kapsch");
			}
			break;
		case 353:
			if(language)
			{
				strcpy(name, "剑道");
				return strlen("剑道");
			}
			else
			{
				strcpy(name, "Kendo");
				return strlen("Kendo");
			}
			break;
		case 354:
			if(language)
			{
				strcpy(name, "Kneissel");
				return strlen("Kneissel");
			}
			else
			{
				strcpy(name, "Kneissel");
				return strlen("Kneissel");
			}
			break;
		case 355:
			if(language)
			{
				strcpy(name, "柯冈");
				return strlen("柯冈");
			}
			else
			{
				strcpy(name, "Kogan");
				return strlen("Kogan");
			}
			break;
		case 356:
			if(language)
			{
				strcpy(name, "科林");
				return strlen("科林");
			}
			else
			{
				strcpy(name, "kolin");
				return strlen("kolin");
			}
			break;
		case 357:
			if(language)
			{
				strcpy(name, "联想");
				return strlen("联想");
			}
			else
			{
				strcpy(name, "Lenovo");
				return strlen("Lenovo");
			}
			break;
		case 358:
			if(language)
			{
				strcpy(name, "Leyco");
				return strlen("Leyco");
			}
			else
			{
				strcpy(name, "Leyco");
				return strlen("Leyco");
			}
			break;
		case 359:
			if(language)
			{
				strcpy(name, "乐金");
				return strlen("乐金");
			}
			else
			{
				strcpy(name, "LG");
				return strlen("LG");
			}
			break;
		case 360:
			if(language)
			{
				strcpy(name, "人生");
				return strlen("人生");
			}
			else
			{
				strcpy(name, "Life");
				return strlen("Life");
			}
			break;
		case 361:
			if(language)
			{
				strcpy(name, "Lifetec");
				return strlen("Lifetec");
			}
			else
			{
				strcpy(name, "Lifetec");
				return strlen("Lifetec");
			}
			break;
		case 362:
			if(language)
			{
				strcpy(name, "林恩");
				return strlen("林恩");
			}
			else
			{
				strcpy(name, "Linn");
				return strlen("Linn");
			}
			break;
		case 363:
			if(language)
			{
				strcpy(name, "洛伊");
				return strlen("洛伊");
			}
			else
			{
				strcpy(name, "Loewe");
				return strlen("Loewe");
			}
			break;
		case 364:
			if(language)
			{
				strcpy(name, "搜集");
				return strlen("搜集");
			}
			else
			{
				strcpy(name, "Logik");
				return strlen("Logik");
			}
			break;
		case 365:
			if(language)
			{
				strcpy(name, "热塑光阀");
				return strlen("热塑光阀");
			}
			else
			{
				strcpy(name, "Lumatron");
				return strlen("Lumatron");
			}
			break;
		case 366:
			if(language)
			{
				strcpy(name, "卢克索");
				return strlen("卢克索");
			}
			else
			{
				strcpy(name, "Luxor");
				return strlen("Luxor");
			}
			break;
		case 367:
			if(language)
			{
				strcpy(name, "MEIectronic");
				return strlen("MEIectronic");
			}
			else
			{
				strcpy(name, "MEIectronic");
				return strlen("MEIectronic");
			}
			break;
		case 368:
			if(language)
			{
				strcpy(name, "Maganavox");
				return strlen("Maganavox");
			}
			else
			{
				strcpy(name, "Maganavox");
				return strlen("Maganavox");
			}
			break;
		case 369:
			if(language)
			{
				strcpy(name, "Magnasonic");
				return strlen("Magnasonic");
			}
			else
			{
				strcpy(name, "Magnasonic");
				return strlen("Magnasonic");
			}
			break;
		case 370:
			if(language)
			{
				strcpy(name, "沃克斯");
				return strlen("沃克斯");
			}
			else
			{
				strcpy(name, "Magnavox");
				return strlen("Magnavox");
			}
			break;
		case 371:
			if(language)
			{
				strcpy(name, "大酒瓶");
				return strlen("大酒瓶");
			}
			else
			{
				strcpy(name, "Magnum");
				return strlen("Magnum");
			}
			break;
		case 372:
			if(language)
			{
				strcpy(name, "门多尔");
				return strlen("门多尔");
			}
			else
			{
				strcpy(name, "Mandor");
				return strlen("Mandor");
			}
			break;
		case 373:
			if(language)
			{
				strcpy(name, "Manesth");
				return strlen("Manesth");
			}
			else
			{
				strcpy(name, "Manesth");
				return strlen("Manesth");
			}
			break;
		case 374:
			if(language)
			{
				strcpy(name, "马兰士");
				return strlen("马兰士");
			}
			else
			{
				strcpy(name, "Marantz");
				return strlen("Marantz");
			}
			break;
		case 375:
			if(language)
			{
				strcpy(name, "马尔塔");
				return strlen("马尔塔");
			}
			else
			{
				strcpy(name, "Marta");
				return strlen("Marta");
			}
			break;
		case 376:
			if(language)
			{
				strcpy(name, "松井");
				return strlen("松井");
			}
			else
			{
				strcpy(name, "Matsui");
				return strlen("Matsui");
			}
			break;
		case 377:
			if(language)
			{
				strcpy(name, "中位数");
				return strlen("中位数");
			}
			else
			{
				strcpy(name, "Medion");
				return strlen("Medion");
			}
			break;
		case 378:
			if(language)
			{
				strcpy(name, "梅莫雷克斯");
				return strlen("梅莫雷克斯");
			}
			else
			{
				strcpy(name, "Memorex");
				return strlen("Memorex");
			}
			break;
		case 379:
			if(language)
			{
				strcpy(name, "梅斯");
				return strlen("梅斯");
			}
			else
			{
				strcpy(name, "Metz");
				return strlen("Metz");
			}
			break;
		case 380:
			if(language)
			{
				strcpy(name, "Micromaxx");
				return strlen("Micromaxx");
			}
			else
			{
				strcpy(name, "Micromaxx");
				return strlen("Micromaxx");
			}
			break;
		case 381:
			if(language)
			{
				strcpy(name, "微软之星");
				return strlen("微软之星");
			}
			else
			{
				strcpy(name, "Microstar");
				return strlen("Microstar");
			}
			break;
		case 382:
			if(language)
			{
				strcpy(name, "Mikomi");
				return strlen("Mikomi");
			}
			else
			{
				strcpy(name, "Mikomi");
				return strlen("Mikomi");
			}
			break;
		case 383:
			if(language)
			{
				strcpy(name, "密涅瓦");
				return strlen("密涅瓦");
			}
			else
			{
				strcpy(name, "Minerva");
				return strlen("Minerva");
			}
			break;
		case 384:
			if(language)
			{
				strcpy(name, "Mintek");
				return strlen("Mintek");
			}
			else
			{
				strcpy(name, "Mintek");
				return strlen("Mintek");
			}
			break;
		case 385:
			if(language)
			{
				strcpy(name, "墨菲");
				return strlen("墨菲");
			}
			else
			{
				strcpy(name, "Murphy");
				return strlen("Murphy");
			}
			break;
		case 386:
			if(language)
			{
				strcpy(name, "日本电气");
				return strlen("日本电气");
			}
			else
			{
				strcpy(name, "Nec");
				return strlen("Nec");
			}
			break;
		case 387:
			if(language)
			{
				strcpy(name, "耐卡尔曼");
				return strlen("耐卡尔曼");
			}
			else
			{
				strcpy(name, "Neckermann");
				return strlen("Neckermann");
			}
			break;
		case 388:
			if(language)
			{
				strcpy(name, "内蒙古");
				return strlen("内蒙古");
			}
			else
			{
				strcpy(name, "Nei");
				return strlen("Nei");
			}
			break;
		case 389:
			if(language)
			{
				strcpy(name, "纽带");
				return strlen("纽带");
			}
			else
			{
				strcpy(name, "Nexus");
				return strlen("Nexus");
			}
			break;
		case 390:
			if(language)
			{
				strcpy(name, "日本开闭器工业公司");
				return strlen("日本开闭器工业公司");
			}
			else
			{
				strcpy(name, "Nikkai");
				return strlen("Nikkai");
			}
			break;
		case 391:
			if(language)
			{
				strcpy(name, "诺基亚");
				return strlen("诺基亚");
			}
			else
			{
				strcpy(name, "Nokia");
				return strlen("Nokia");
			}
			break;
		case 392:
			if(language)
			{
				strcpy(name, "宏盛");
				return strlen("宏盛");
			}
			else
			{
				strcpy(name, "Norcent");
				return strlen("Norcent");
			}
			break;
		case 393:
			if(language)
			{
				strcpy(name, "奥卡诺河");
				return strlen("奥卡诺河");
			}
			else
			{
				strcpy(name, "Okano");
				return strlen("Okano");
			}
			break;
		case 394:
			if(language)
			{
				strcpy(name, "冲电气工业公司");
				return strlen("冲电气工业公司");
			}
			else
			{
				strcpy(name, "Oki");
				return strlen("Oki");
			}
			break;
		case 395:
			if(language)
			{
				strcpy(name, "奥利维亚");
				return strlen("奥利维亚");
			}
			else
			{
				strcpy(name, "OLEVIA");
				return strlen("OLEVIA");
			}
			break;
		case 396:
			if(language)
			{
				strcpy(name, "擎天柱");
				return strlen("擎天柱");
			}
			else
			{
				strcpy(name, "Optimus");
				return strlen("Optimus");
			}
			break;
		case 397:
			if(language)
			{
				strcpy(name, "奥图码");
				return strlen("奥图码");
			}
			else
			{
				strcpy(name, "Optoma");
				return strlen("Optoma");
			}
			break;
		case 398:
			if(language)
			{
				strcpy(name, "猎户座");
				return strlen("猎户座");
			}
			else
			{
				strcpy(name, "Orion");
				return strlen("Orion");
			}
			break;
		case 399:
			if(language)
			{
				strcpy(name, "大崎");
				return strlen("大崎");
			}
			else
			{
				strcpy(name, "Osaki");
				return strlen("Osaki");
			}
			break;
		case 400:
			if(language)
			{
				strcpy(name, "奥托邮购");
				return strlen("奥托邮购");
			}
			else
			{
				strcpy(name, "Otto Versand");
				return strlen("Otto Versand");
			}
			break;
		case 401:
			if(language)
			{
				strcpy(name, "帕卡德-贝尔");
				return strlen("帕卡德-贝尔");
			}
			else
			{
				strcpy(name, "Packard bell");
				return strlen("Packard bell");
			}
			break;
		case 402:
			if(language)
			{
				strcpy(name, "Pantel");
				return strlen("Pantel");
			}
			else
			{
				strcpy(name, "Pantel");
				return strlen("Pantel");
			}
			break;
		case 403:
			if(language)
			{
				strcpy(name, "百代马可尼");
				return strlen("百代马可尼");
			}
			else
			{
				strcpy(name, "Pathe Marconi");
				return strlen("Pathe Marconi");
			}
			break;
		case 404:
			if(language)
			{
				strcpy(name, "题");
				return strlen("题");
			}
			else
			{
				strcpy(name, "Perido");
				return strlen("Perido");
			}
			break;
		case 405:
			if(language)
			{
				strcpy(name, "宝丽来");
				return strlen("宝丽来");
			}
			else
			{
				strcpy(name, "Polaroid");
				return strlen("Polaroid");
			}
			break;
		case 406:
			if(language)
			{
				strcpy(name, "空间");
				return strlen("空间");
			}
			else
			{
				strcpy(name, "Pro2");
				return strlen("Pro2");
			}
			break;
		case 407:
			if(language)
			{
				strcpy(name, "Proscan");
				return strlen("Proscan");
			}
			else
			{
				strcpy(name, "Proscan");
				return strlen("Proscan");
			}
			break;
		case 408:
			if(language)
			{
				strcpy(name, "天鸿");
				return strlen("天鸿");
			}
			else
			{
				strcpy(name, "Prosonic");
				return strlen("Prosonic");
			}
			break;
		case 409:
			if(language)
			{
				strcpy(name, "Protech");
				return strlen("Protech");
			}
			else
			{
				strcpy(name, "Protech");
				return strlen("Protech");
			}
			break;
		case 410:
			if(language)
			{
				strcpy(name, "派伊");
				return strlen("派伊");
			}
			else
			{
				strcpy(name, "Pye");
				return strlen("Pye");
			}
			break;
		case 411:
			if(language)
			{
				strcpy(name, "奎尔");
				return strlen("奎尔");
			}
			else
			{
				strcpy(name, "Quelle");
				return strlen("Quelle");
			}
			break;
		case 412:
			if(language)
			{
				strcpy(name, "Questa");
				return strlen("Questa");
			}
			else
			{
				strcpy(name, "Questa");
				return strlen("Questa");
			}
			break;
		case 413:
			if(language)
			{
				strcpy(name, "Radioette");
				return strlen("Radioette");
			}
			else
			{
				strcpy(name, "Radioette");
				return strlen("Radioette");
			}
			break;
		case 414:
			if(language)
			{
				strcpy(name, "铺子");
				return strlen("铺子");
			}
			else
			{
				strcpy(name, "RadioShack");
				return strlen("RadioShack");
			}
			break;
		case 415:
			if(language)
			{
				strcpy(name, "Radiotone");
				return strlen("Radiotone");
			}
			else
			{
				strcpy(name, "Radiotone");
				return strlen("Radiotone");
			}
			break;
		case 416:
			if(language)
			{
				strcpy(name, "雷迪克斯");
				return strlen("雷迪克斯");
			}
			else
			{
				strcpy(name, "Radix");
				return strlen("Radix");
			}
			break;
		case 417:
			if(language)
			{
				strcpy(name, "兰克");
				return strlen("兰克");
			}
			else
			{
				strcpy(name, "Rank");
				return strlen("Rank");
			}
			break;
		case 418:
			if(language)
			{
				strcpy(name, "国际化");
				return strlen("国际化");
			}
			else
			{
				strcpy(name, "RBM");
				return strlen("RBM");
			}
			break;
		case 419:
			if(language)
			{
				strcpy(name, "无线电");
				return strlen("无线电");
			}
			else
			{
				strcpy(name, "RCA");
				return strlen("RCA");
			}
			break;
		case 420:
			if(language)
			{
				strcpy(name, "现实");
				return strlen("现实");
			}
			else
			{
				strcpy(name, "Realistic");
				return strlen("Realistic");
			}
			break;
		case 421:
			if(language)
			{
				strcpy(name, "射频变压器");
				return strlen("射频变压器");
			}
			else
			{
				strcpy(name, "RFT");
				return strlen("RFT");
			}
			break;
		case 422:
			if(language)
			{
				strcpy(name, "敞篷");
				return strlen("敞篷");
			}
			else
			{
				strcpy(name, "Roadstar");
				return strlen("Roadstar");
			}
			break;
		case 423:
			if(language)
			{
				strcpy(name, "Saisho");
				return strlen("Saisho");
			}
			else
			{
				strcpy(name, "Saisho");
				return strlen("Saisho");
			}
			break;
		case 424:
			if(language)
			{
				strcpy(name, "酒井");
				return strlen("酒井");
			}
			else
			{
				strcpy(name, "SAKAI SIO");
				return strlen("SAKAI SIO");
			}
			break;
		case 425:
			if(language)
			{
				strcpy(name, "Salora");
				return strlen("Salora");
			}
			else
			{
				strcpy(name, "Salora");
				return strlen("Salora");
			}
			break;
		case 426:
			if(language)
			{
				strcpy(name, "西门子");
				return strlen("西门子");
			}
			else
			{
				strcpy(name, "Siemens");
				return strlen("Siemens");
			}
			break;
		case 427:
			if(language)
			{
				strcpy(name, "锡尔弗");
				return strlen("锡尔弗");
			}
			else
			{
				strcpy(name, "Silver");
				return strlen("Silver");
			}
			break;
		case 428:
			if(language)
			{
				strcpy(name, "冠");
				return strlen("冠");
			}
			else
			{
				strcpy(name, "Silvercrest");
				return strlen("Silvercrest");
			}
			break;
		case 429:
			if(language)
			{
				strcpy(name, "Sinudyne");
				return strlen("Sinudyne");
			}
			else
			{
				strcpy(name, "Sinudyne");
				return strlen("Sinudyne");
			}
			break;
		case 430:
			if(language)
			{
				strcpy(name, "Solavox");
				return strlen("Solavox");
			}
			else
			{
				strcpy(name, "Solavox");
				return strlen("Solavox");
			}
			break;
		case 431:
			if(language)
			{
				strcpy(name, "sonitron");
				return strlen("sonitron");
			}
			else
			{
				strcpy(name, "sonitron");
				return strlen("sonitron");
			}
			break;
		case 432:
			if(language)
			{
				strcpy(name, "Sonolor");
				return strlen("Sonolor");
			}
			else
			{
				strcpy(name, "Sonolor");
				return strlen("Sonolor");
			}
			break;
		case 433:
			if(language)
			{
				strcpy(name, "声音与视觉");
				return strlen("声音与视觉");
			}
			else
			{
				strcpy(name, "Sound&Vision");
				return strlen("Sound&Vision");
			}
			break;
		case 434:
			if(language)
			{
				strcpy(name, "索约");
				return strlen("索约");
			}
			else
			{
				strcpy(name, "Soyo");
				return strlen("Soyo");
			}
			break;
		case 435:
			if(language)
			{
				strcpy(name, "斯坦达德");
				return strlen("斯坦达德");
			}
			else
			{
				strcpy(name, "Standard");
				return strlen("Standard");
			}
			break;
		case 436:
			if(language)
			{
				strcpy(name, "Sunbright");
				return strlen("Sunbright");
			}
			else
			{
				strcpy(name, "Sunbright");
				return strlen("Sunbright");
			}
			break;
		case 437:
			if(language)
			{
				strcpy(name, "西尔韦尼亚");
				return strlen("西尔韦尼亚");
			}
			else
			{
				strcpy(name, "Sylvania");
				return strlen("Sylvania");
			}
			break;
		case 438:
			if(language)
			{
				strcpy(name, "交响乐");
				return strlen("交响乐");
			}
			else
			{
				strcpy(name, "Symphonic");
				return strlen("Symphonic");
			}
			break;
		case 439:
			if(language)
			{
				strcpy(name, "切分");
				return strlen("切分");
			}
			else
			{
				strcpy(name, "Synco");
				return strlen("Synco");
			}
			break;
		case 440:
			if(language)
			{
				strcpy(name, "语法");
				return strlen("语法");
			}
			else
			{
				strcpy(name, "Syntax");
				return strlen("Syntax");
			}
			break;
		case 441:
			if(language)
			{
				strcpy(name, "语法奥丽维亚");
				return strlen("语法奥丽维亚");
			}
			else
			{
				strcpy(name, "Syntax Olevia");
				return strlen("Syntax Olevia");
			}
			break;
		case 442:
			if(language)
			{
				strcpy(name, "康迪");
				return strlen("康迪");
			}
			else
			{
				strcpy(name, "Tandy");
				return strlen("Tandy");
			}
			break;
		case 443:
			if(language)
			{
				strcpy(name, "Tashiko");
				return strlen("Tashiko");
			}
			else
			{
				strcpy(name, "Tashiko");
				return strlen("Tashiko");
			}
			break;
		case 444:
			if(language)
			{
				strcpy(name, "大同");
				return strlen("大同");
			}
			else
			{
				strcpy(name, "Tatung");
				return strlen("Tatung");
			}
			break;
		case 445:
			if(language)
			{
				strcpy(name, "中医");
				return strlen("中医");
			}
			else
			{
				strcpy(name, "TCM");
				return strlen("TCM");
			}
			break;
		case 446:
			if(language)
			{
				strcpy(name, "Technika");
				return strlen("Technika");
			}
			else
			{
				strcpy(name, "Technika");
				return strlen("Technika");
			}
			break;
		case 447:
			if(language)
			{
				strcpy(name, "Techwood");
				return strlen("Techwood");
			}
			else
			{
				strcpy(name, "Techwood");
				return strlen("Techwood");
			}
			break;
		case 448:
			if(language)
			{
				strcpy(name, "东元");
				return strlen("东元");
			}
			else
			{
				strcpy(name, "Teco");
				return strlen("Teco");
			}
			break;
		case 449:
			if(language)
			{
				strcpy(name, "Telavia");
				return strlen("Telavia");
			}
			else
			{
				strcpy(name, "Telavia");
				return strlen("Telavia");
			}
			break;
		case 450:
			if(language)
			{
				strcpy(name, "德律风根");
				return strlen("德律风根");
			}
			else
			{
				strcpy(name, "Telefunken");
				return strlen("Telefunken");
			}
			break;
		case 451:
			if(language)
			{
				strcpy(name, "Teletech");
				return strlen("Teletech");
			}
			else
			{
				strcpy(name, "Teletech");
				return strlen("Teletech");
			}
			break;
		case 452:
			if(language)
			{
				strcpy(name, "天际");
				return strlen("天际");
			}
			else
			{
				strcpy(name, "Tensai");
				return strlen("Tensai");
			}
			break;
		case 453:
			if(language)
			{
				strcpy(name, "Tevion");
				return strlen("Tevion");
			}
			else
			{
				strcpy(name, "Tevion");
				return strlen("Tevion");
			}
			break;
		case 454:
			if(language)
			{
				strcpy(name, "汤姆逊");
				return strlen("汤姆逊");
			}
			else
			{
				strcpy(name, "THOMSON");
				return strlen("THOMSON");
			}
			break;
		case 455:
			if(language)
			{
				strcpy(name, "棘丛");
				return strlen("棘丛");
			}
			else
			{
				strcpy(name, "Thorn");
				return strlen("Thorn");
			}
			break;
		case 456:
			if(language)
			{
				strcpy(name, "Trutech");
				return strlen("Trutech");
			}
			else
			{
				strcpy(name, "Trutech");
				return strlen("Trutech");
			}
			break;
		case 457:
			if(language)
			{
				strcpy(name, "博士");
				return strlen("博士");
			}
			else
			{
				strcpy(name, "Uher");
				return strlen("Uher");
			}
			break;
		case 458:
			if(language)
			{
				strcpy(name, "联华");
				return strlen("联华");
			}
			else
			{
				strcpy(name, "Umc");
				return strlen("Umc");
			}
			break;
		case 459:
			if(language)
			{
				strcpy(name, "联合");
				return strlen("联合");
			}
			else
			{
				strcpy(name, "United");
				return strlen("United");
			}
			break;
		case 460:
			if(language)
			{
				strcpy(name, "优信咨询");
				return strlen("优信咨询");
			}
			else
			{
				strcpy(name, "Universum");
				return strlen("Universum");
			}
			break;
		case 461:
			if(language)
			{
				strcpy(name, "Vlnc");
				return strlen("Vlnc");
			}
			else
			{
				strcpy(name, "Vlnc");
				return strlen("Vlnc");
			}
			break;
		case 462:
			if(language)
			{
				strcpy(name, "载体研究");
				return strlen("载体研究");
			}
			else
			{
				strcpy(name, "Vector Research");
				return strlen("Vector Research");
			}
			break;
		case 463:
			if(language)
			{
				strcpy(name, "等");
				return strlen("等");
			}
			else
			{
				strcpy(name, "Vestel");
				return strlen("Vestel");
			}
			break;
		case 464:
			if(language)
			{
				strcpy(name, "Vexa");
				return strlen("Vexa");
			}
			else
			{
				strcpy(name, "Vexa");
				return strlen("Vexa");
			}
			break;
		case 465:
			if(language)
			{
				strcpy(name, "维克托");
				return strlen("维克托");
			}
			else
			{
				strcpy(name, "Victor");
				return strlen("Victor");
			}
			break;
		case 466:
			if(language)
			{
				strcpy(name, "视频的概念");
				return strlen("视频的概念");
			}
			else
			{
				strcpy(name, "Video Concepts");
				return strlen("Video Concepts");
			}
			break;
		case 467:
			if(language)
			{
				strcpy(name, "Videon");
				return strlen("Videon");
			}
			else
			{
				strcpy(name, "Videon");
				return strlen("Videon");
			}
			break;
		case 468:
			if(language)
			{
				strcpy(name, "优派");
				return strlen("优派");
			}
			else
			{
				strcpy(name, "ViewSonic");
				return strlen("ViewSonic");
			}
			break;
		case 469:
			if(language)
			{
				strcpy(name, "Viore");
				return strlen("Viore");
			}
			else
			{
				strcpy(name, "Viore");
				return strlen("Viore");
			}
			break;
		case 470:
			if(language)
			{
				strcpy(name, "幻想探索");
				return strlen("幻想探索");
			}
			else
			{
				strcpy(name, "Vision Quest");
				return strlen("Vision Quest");
			}
			break;
		case 471:
			if(language)
			{
				strcpy(name, "景新");
				return strlen("景新");
			}
			else
			{
				strcpy(name, "VITO");
				return strlen("VITO");
			}
			break;
		case 472:
			if(language)
			{
				strcpy(name, "瑞旭");
				return strlen("瑞旭");
			}
			else
			{
				strcpy(name, "Vizio");
				return strlen("Vizio");
			}
			break;
		case 473:
			if(language)
			{
				strcpy(name, "Vortec");
				return strlen("Vortec");
			}
			else
			{
				strcpy(name, "Vortec");
				return strlen("Vortec");
			}
			break;
		case 474:
			if(language)
			{
				strcpy(name, "Voxson");
				return strlen("Voxson");
			}
			else
			{
				strcpy(name, "Voxson");
				return strlen("Voxson");
			}
			break;
		case 475:
			if(language)
			{
				strcpy(name, "华森");
				return strlen("华森");
			}
			else
			{
				strcpy(name, "Waltham");
				return strlen("Waltham");
			}
			break;
		case 476:
			if(language)
			{
				strcpy(name, "沃森");
				return strlen("沃森");
			}
			else
			{
				strcpy(name, "Watson");
				return strlen("Watson");
			}
			break;
		case 477:
			if(language)
			{
				strcpy(name, "维嘉");
				return strlen("维嘉");
			}
			else
			{
				strcpy(name, "Wega");
				return strlen("Wega");
			}
			break;
		case 478:
			if(language)
			{
				strcpy(name, "Weltblick");
				return strlen("Weltblick");
			}
			else
			{
				strcpy(name, "Weltblick");
				return strlen("Weltblick");
			}
			break;
		case 479:
			if(language)
			{
				strcpy(name, "西屋");
				return strlen("西屋");
			}
			else
			{
				strcpy(name, "Westinghouse");
				return strlen("Westinghouse");
			}
			break;
		case 480:
			if(language)
			{
				strcpy(name, "约科");
				return strlen("约科");
			}
			else
			{
				strcpy(name, "Yoko");
				return strlen("Yoko");
			}
			break;
		case 481:
			if(language)
			{
				strcpy(name, "扎努西");
				return strlen("扎努西");
			}
			else
			{
				strcpy(name, "Zanussi");
				return strlen("Zanussi");
			}
			break;
		case 482:
			if(language)
			{
				strcpy(name, "真利时");
				return strlen("真利时");
			}
			else
			{
				strcpy(name, "Zenith");
				return strlen("Zenith");
			}
			break;
		case 483:
			if(language)
			{
				strcpy(name, "Zinwell");
				return strlen("Zinwell");
			}
			else
			{
				strcpy(name, "Zinwell");
				return strlen("Zinwell");
			}
			break;
		case 484:
			if(language)
			{
				strcpy(name, "A.R.SYSTEMS");
				return strlen("A.R.SYSTEMS");
			}
			else
			{
				strcpy(name, "A.R.SYSTEMS");
				return strlen("A.R.SYSTEMS");
			}
			break;
		case 485:
			if(language)
			{
				strcpy(name, "极品");
				return strlen("极品");
			}
			else
			{
				strcpy(name, "ACURA");
				return strlen("ACURA");
			}
			break;
		case 486:
			if(language)
			{
				strcpy(name, "阿德米拉");
				return strlen("阿德米拉");
			}
			else
			{
				strcpy(name, "ADMIRA");
				return strlen("ADMIRA");
			}
			break;
		case 487:
			if(language)
			{
				strcpy(name, "AGEF");
				return strlen("AGEF");
			}
			else
			{
				strcpy(name, "AGEF");
				return strlen("AGEF");
			}
			break;
		case 488:
			if(language)
			{
				strcpy(name, "目的");
				return strlen("目的");
			}
			else
			{
				strcpy(name, "AIM");
				return strlen("AIM");
			}
			break;
		case 489:
			if(language)
			{
				strcpy(name, "阿吉");
				return strlen("阿吉");
			}
			else
			{
				strcpy(name, "AKIB");
				return strlen("AKIB");
			}
			break;
		case 490:
			if(language)
			{
				strcpy(name, "普联");
				return strlen("普联");
			}
			else
			{
				strcpy(name, "AKITO");
				return strlen("AKITO");
			}
			break;
		case 491:
			if(language)
			{
				strcpy(name, "阿拉伦");
				return strlen("阿拉伦");
			}
			else
			{
				strcpy(name, "ALARON");
				return strlen("ALARON");
			}
			break;
		case 492:
			if(language)
			{
				strcpy(name, "ALLSAR");
				return strlen("ALLSAR");
			}
			else
			{
				strcpy(name, "ALLSAR");
				return strlen("ALLSAR");
			}
			break;
		case 493:
			if(language)
			{
				strcpy(name, "奥园");
				return strlen("奥园");
			}
			else
			{
				strcpy(name, "ALLSTAR");
				return strlen("ALLSTAR");
			}
			break;
		case 494:
			if(language)
			{
				strcpy(name, "阿尔瓦");
				return strlen("阿尔瓦");
			}
			else
			{
				strcpy(name, "ALWA");
				return strlen("ALWA");
			}
			break;
		case 495:
			if(language)
			{
				strcpy(name, "大使");
				return strlen("大使");
			}
			else
			{
				strcpy(name, "AMBASSADOR");
				return strlen("AMBASSADOR");
			}
			break;
		case 496:
			if(language)
			{
				strcpy(name, "AMPLIVISION");
				return strlen("AMPLIVISION");
			}
			else
			{
				strcpy(name, "AMPLIVISION");
				return strlen("AMPLIVISION");
			}
			break;
		case 497:
			if(language)
			{
				strcpy(name, "该国家");
				return strlen("该国家");
			}
			else
			{
				strcpy(name, "ANAM NATIONAL");
				return strlen("ANAM NATIONAL");
			}
			break;
		case 498:
			if(language)
			{
				strcpy(name, "艾森贝格");
				return strlen("艾森贝格");
			}
			else
			{
				strcpy(name, "ASBERG");
				return strlen("ASBERG");
			}
			break;
		case 499:
			if(language)
			{
				strcpy(name, "易美逊");
				return strlen("易美逊");
			}
			else
			{
				strcpy(name, "Envision");
				return strlen("Envision");
			}
			break;
		case 500:
			if(language)
			{
				strcpy(name, "国科光电");
				return strlen("国科光电");
			}
			else
			{
				strcpy(name, "Guoke photoelectric");
				return strlen("Guoke photoelectric");
			}
			break;
		case 501:
			if(language)
			{
				strcpy(name, "东宝");
				return strlen("东宝");
			}
			else
			{
				strcpy(name, "Dongbao");
				return strlen("Dongbao");
			}
			break;
		case 502:
			if(language)
			{
				strcpy(name, "奥林比亚");
				return strlen("奥林比亚");
			}
			else
			{
				strcpy(name, "Olympia");
				return strlen("Olympia");
			}
			break;
		case 503:
			if(language)
			{
				strcpy(name, "DRTRON");
				return strlen("DRTRON");
			}
			else
			{
				strcpy(name, "DRTRON");
				return strlen("DRTRON");
			}
			break;
		case 504:
			if(language)
			{
				strcpy(name, "京东方");
				return strlen("京东方");
			}
			else
			{
				strcpy(name, "BOE");
				return strlen("BOE");
			}
			break;
		case 505:
			if(language)
			{
				strcpy(name, "惠科");
				return strlen("惠科");
			}
			else
			{
				strcpy(name, "Huike");
				return strlen("Huike");
			}
			break;
		case 506:
			if(language)
			{
				strcpy(name, "惠浦");
				return strlen("惠浦");
			}
			else
			{
				strcpy(name, "HPC");
				return strlen("HPC");
			}
			break;
		case 507:
			if(language)
			{
				strcpy(name, "苹果");
				return strlen("苹果");
			}
			else
			{
				strcpy(name, "Appe");
				return strlen("Appe");
			}
			break;
		case 508:
			if(language)
			{
				strcpy(name, "瑞轩科技");
				return strlen("瑞轩科技");
			}
			else
			{
				strcpy(name, "AMTRAN");
				return strlen("AMTRAN");
			}
			break;
		case 509:
			if(language)
			{
				strcpy(name, "PROMAC");
				return strlen("PROMAC");
			}
			else
			{
				strcpy(name, "PROMAC");
				return strlen("PROMAC");
			}
			break;
		case 510:
			if(language)
			{
				strcpy(name, "威斯达");
				return strlen("威斯达");
			}
			else
			{
				strcpy(name, "VISTAR");
				return strlen("VISTAR");
			}
			break;
		case 511:
			if(language)
			{
				strcpy(name, "佳能");
				return strlen("佳能");
			}
			else
			{
				strcpy(name, "Canon");
				return strlen("Canon");
			}
			break;
		case 512:
			if(language)
			{
				strcpy(name, "佳美");
				return strlen("佳美");
			}
			else
			{
				strcpy(name, "Camry");
				return strlen("Camry");
			}
			break;
		case 513:
			if(language)
			{
				strcpy(name, "步步高");
				return strlen("步步高");
			}
			else
			{
				strcpy(name, "step by step");
				return strlen("step by step");
			}
			break;
		case 514:
			if(language)
			{
				strcpy(name, "3B科技");
				return strlen("3B科技");
			}
			else
			{
				strcpy(name, "technology");
				return strlen("technology");
			}
			break;
		case 515:
			if(language)
			{
				strcpy(name, "3S数字");
				return strlen("3S数字");
			}
			else
			{
				strcpy(name, "Digital");
				return strlen("Digital");
			}
			break;
		case 516:
			if(language)
			{
				strcpy(name, "AlDisplay");
				return strlen("AlDisplay");
			}
			else
			{
				strcpy(name, "AlDisplay");
				return strlen("AlDisplay");
			}
			break;
		case 517:
			if(language)
			{
				strcpy(name, "AcceleVision");
				return strlen("AcceleVision");
			}
			else
			{
				strcpy(name, "AcceleVision");
				return strlen("AcceleVision");
			}
			break;
		case 518:
			if(language)
			{
				strcpy(name, "口音");
				return strlen("口音");
			}
			else
			{
				strcpy(name, "Accent");
				return strlen("Accent");
			}
			break;
		case 519:
			if(language)
			{
				strcpy(name, "Accurian");
				return strlen("Accurian");
			}
			else
			{
				strcpy(name, "Accurian");
				return strlen("Accurian");
			}
			break;
		case 520:
			if(language)
			{
				strcpy(name, "高画质");
				return strlen("高画质");
			}
			else
			{
				strcpy(name, "AccuView");
				return strlen("AccuView");
			}
			break;
		case 521:
			if(language)
			{
				strcpy(name, "王牌");
				return strlen("王牌");
			}
			else
			{
				strcpy(name, "ACE");
				return strlen("ACE");
			}
			break;
		case 522:
			if(language)
			{
				strcpy(name, "声解");
				return strlen("声解");
			}
			else
			{
				strcpy(name, "Acoustic Solutions");
				return strlen("Acoustic Solutions");
			}
			break;
		case 523:
			if(language)
			{
				strcpy(name, "作用");
				return strlen("作用");
			}
			else
			{
				strcpy(name, "Action");
				return strlen("Action");
			}
			break;
		case 524:
			if(language)
			{
				strcpy(name, "广告");
				return strlen("广告");
			}
			else
			{
				strcpy(name, "Ad Notam");
				return strlen("Ad Notam");
			}
			break;
		case 525:
			if(language)
			{
				strcpy(name, "艾迪生");
				return strlen("艾迪生");
			}
			else
			{
				strcpy(name, "Addison");
				return strlen("Addison");
			}
			break;
		case 526:
			if(language)
			{
				strcpy(name, "ADL");
				return strlen("ADL");
			}
			else
			{
				strcpy(name, "ADL");
				return strlen("ADL");
			}
			break;
		case 527:
			if(language)
			{
				strcpy(name, "ADT");
				return strlen("ADT");
			}
			else
			{
				strcpy(name, "ADT");
				return strlen("ADT");
			}
			break;
		case 528:
			if(language)
			{
				strcpy(name, "通用电力");
				return strlen("通用电力");
			}
			else
			{
				strcpy(name, "AEG");
				return strlen("AEG");
			}
			break;
		case 529:
			if(language)
			{
				strcpy(name, "该公司");
				return strlen("该公司");
			}
			else
			{
				strcpy(name, "Aftron");
				return strlen("Aftron");
			}
			break;
		case 530:
			if(language)
			{
				strcpy(name, "明石");
				return strlen("明石");
			}
			else
			{
				strcpy(name, "Akashi");
				return strlen("Akashi");
			}
			break;
		case 531:
			if(language)
			{
				strcpy(name, "阿基拉");
				return strlen("阿基拉");
			}
			else
			{
				strcpy(name, "Akira");
				return strlen("Akira");
			}
			break;
		case 532:
			if(language)
			{
				strcpy(name, "青云");
				return strlen("青云");
			}
			else
			{
				strcpy(name, "Albatron");
				return strlen("Albatron");
			}
			break;
		case 533:
			if(language)
			{
				strcpy(name, "外星");
				return strlen("外星");
			}
			else
			{
				strcpy(name, "Alien");
				return strlen("Alien");
			}
			break;
		case 534:
			if(language)
			{
				strcpy(name, "安南");
				return strlen("安南");
			}
			else
			{
				strcpy(name, "Anam");
				return strlen("Anam");
			}
			break;
		case 535:
			if(language)
			{
				strcpy(name, "安南地区");
				return strlen("安南地区");
			}
			else
			{
				strcpy(name, "Anam Nationnal");
				return strlen("Anam Nationnal");
			}
			break;
		case 536:
			if(language)
			{
				strcpy(name, "安德松");
				return strlen("安德松");
			}
			else
			{
				strcpy(name, "Andersson");
				return strlen("Andersson");
			}
			break;
		case 537:
			if(language)
			{
				strcpy(name, "跨音速");
				return strlen("跨音速");
			}
			else
			{
				strcpy(name, "Ansonic");
				return strlen("Ansonic");
			}
			break;
		case 538:
			if(language)
			{
				strcpy(name, "Aolonpiya");
				return strlen("Aolonpiya");
			}
			else
			{
				strcpy(name, "Aolonpiya");
				return strlen("Aolonpiya");
			}
			break;
		case 539:
			if(language)
			{
				strcpy(name, "先端数字");
				return strlen("先端数字");
			}
			else
			{
				strcpy(name, "Apex Digital");
				return strlen("Apex Digital");
			}
			break;
		case 540:
			if(language)
			{
				strcpy(name, "Ardem");
				return strlen("Ardem");
			}
			else
			{
				strcpy(name, "Ardem");
				return strlen("Ardem");
			}
			break;
		case 541:
			if(language)
			{
				strcpy(name, "阿里纳");
				return strlen("阿里纳");
			}
			else
			{
				strcpy(name, "Arena");
				return strlen("Arena");
			}
			break;
		case 542:
			if(language)
			{
				strcpy(name, "阿里斯顿");
				return strlen("阿里斯顿");
			}
			else
			{
				strcpy(name, "Aristona");
				return strlen("Aristona");
			}
			break;
		case 543:
			if(language)
			{
				strcpy(name, "艺术");
				return strlen("艺术");
			}
			else
			{
				strcpy(name, "ART");
				return strlen("ART");
			}
			break;
		case 544:
			if(language)
			{
				strcpy(name, "阿斯特拉");
				return strlen("阿斯特拉");
			}
			else
			{
				strcpy(name, "Astra");
				return strlen("Astra");
			}
			break;
		case 545:
			if(language)
			{
				strcpy(name, "ATD");
				return strlen("ATD");
			}
			else
			{
				strcpy(name, "ATD");
				return strlen("ATD");
			}
			break;
		case 546:
			if(language)
			{
				strcpy(name, "Audioworld");
				return strlen("Audioworld");
			}
			else
			{
				strcpy(name, "Audioworld");
				return strlen("Audioworld");
			}
			break;
		case 547:
			if(language)
			{
				strcpy(name, "阿文图拉");
				return strlen("阿文图拉");
			}
			else
			{
				strcpy(name, "Aventura");
				return strlen("Aventura");
			}
			break;
		case 548:
			if(language)
			{
				strcpy(name, "阿波");
				return strlen("阿波");
			}
			else
			{
				strcpy(name, "Awa");
				return strlen("Awa");
			}
			break;
		case 549:
			if(language)
			{
				strcpy(name, "Axxon");
				return strlen("Axxon");
			}
			else
			{
				strcpy(name, "Axxon");
				return strlen("Axxon");
			}
			break;
		case 550:
			if(language)
			{
				strcpy(name, "巴可");
				return strlen("巴可");
			}
			else
			{
				strcpy(name, "Barco");
				return strlen("Barco");
			}
			break;
		case 551:
			if(language)
			{
				strcpy(name, "基本路线");
				return strlen("基本路线");
			}
			else
			{
				strcpy(name, "Basic Line");
				return strlen("Basic Line");
			}
			break;
		case 552:
			if(language)
			{
				strcpy(name, "保尔");
				return strlen("保尔");
			}
			else
			{
				strcpy(name, "Baur");
				return strlen("Baur");
			}
			break;
		case 553:
			if(language)
			{
				strcpy(name, "Baysonic");
				return strlen("Baysonic");
			}
			else
			{
				strcpy(name, "Baysonic");
				return strlen("Baysonic");
			}
			break;
		case 554:
			if(language)
			{
				strcpy(name, "Beaumark");
				return strlen("Beaumark");
			}
			else
			{
				strcpy(name, "Beaumark");
				return strlen("Beaumark");
			}
			break;
		case 555:
			if(language)
			{
				strcpy(name, "贝尔森");
				return strlen("贝尔森");
			}
			else
			{
				strcpy(name, "Belson");
				return strlen("Belson");
			}
			break;
		case 556:
			if(language)
			{
				strcpy(name, "博尔斯达");
				return strlen("博尔斯达");
			}
			else
			{
				strcpy(name, "Belstar");
				return strlen("Belstar");
			}
			break;
		case 557:
			if(language)
			{
				strcpy(name, "在");
				return strlen("在");
			}
			else
			{
				strcpy(name, "Beon");
				return strlen("Beon");
			}
			break;
		case 558:
			if(language)
			{
				strcpy(name, "Berthen");
				return strlen("Berthen");
			}
			else
			{
				strcpy(name, "Berthen");
				return strlen("Berthen");
			}
			break;
		case 559:
			if(language)
			{
				strcpy(name, "蓝宝");
				return strlen("蓝宝");
			}
			else
			{
				strcpy(name, "Blaupunkt");
				return strlen("Blaupunkt");
			}
			break;
		case 560:
			if(language)
			{
				strcpy(name, "蓝天");
				return strlen("蓝天");
			}
			else
			{
				strcpy(name, "Blue Sky");
				return strlen("Blue Sky");
			}
			break;
		case 561:
			if(language)
			{
				strcpy(name, "博卡");
				return strlen("博卡");
			}
			else
			{
				strcpy(name, "Boca");
				return strlen("Boca");
			}
			break;
		case 562:
			if(language)
			{
				strcpy(name, "博克");
				return strlen("博克");
			}
			else
			{
				strcpy(name, "Bork");
				return strlen("Bork");
			}
			break;
		case 563:
			if(language)
			{
				strcpy(name, "合伙");
				return strlen("合伙");
			}
			else
			{
				strcpy(name, "BPL");
				return strlen("BPL");
			}
			break;
		case 564:
			if(language)
			{
				strcpy(name, "Brikmann");
				return strlen("Brikmann");
			}
			else
			{
				strcpy(name, "Brikmann");
				return strlen("Brikmann");
			}
			break;
		case 565:
			if(language)
			{
				strcpy(name, "Brionvega");
				return strlen("Brionvega");
			}
			else
			{
				strcpy(name, "Brionvega");
				return strlen("Brionvega");
			}
			break;
		case 566:
			if(language)
			{
				strcpy(name, "赛百纳");
				return strlen("赛百纳");
			}
			else
			{
				strcpy(name, "Ctech");
				return strlen("Ctech");
			}
			break;
		case 567:
			if(language)
			{
				strcpy(name, "采诗");
				return strlen("采诗");
			}
			else
			{
				strcpy(name, "Caishi");
				return strlen("Caishi");
			}
			break;
		case 568:
			if(language)
			{
				strcpy(name, "卡梅伦");
				return strlen("卡梅伦");
			}
			else
			{
				strcpy(name, "Cameron");
				return strlen("Cameron");
			}
			break;
		case 569:
			if(language)
			{
				strcpy(name, "露营");
				return strlen("露营");
			}
			else
			{
				strcpy(name, "Camper");
				return strlen("Camper");
			}
			break;
		case 570:
			if(language)
			{
				strcpy(name, "加拿大");
				return strlen("加拿大");
			}
			else
			{
				strcpy(name, "Canca");
				return strlen("Canca");
			}
			break;
		case 571:
			if(language)
			{
				strcpy(name, "Carad");
				return strlen("Carad");
			}
			else
			{
				strcpy(name, "Carad");
				return strlen("Carad");
			}
			break;
		case 572:
			if(language)
			{
				strcpy(name, "卡伦纳");
				return strlen("卡伦纳");
			}
			else
			{
				strcpy(name, "Carena");
				return strlen("Carena");
			}
			break;
		case 573:
			if(language)
			{
				strcpy(name, "叶栅");
				return strlen("叶栅");
			}
			else
			{
				strcpy(name, "Cascade");
				return strlen("Cascade");
			}
			break;
		case 574:
			if(language)
			{
				strcpy(name, "卡西欧");
				return strlen("卡西欧");
			}
			else
			{
				strcpy(name, "Casio");
				return strlen("Casio");
			}
			break;
		case 575:
			if(language)
			{
				strcpy(name, "国泰");
				return strlen("国泰");
			}
			else
			{
				strcpy(name, "Cathay");
				return strlen("Cathay");
			}
			break;
		case 576:
			if(language)
			{
				strcpy(name, "名人");
				return strlen("名人");
			}
			else
			{
				strcpy(name, "Celebrity");
				return strlen("Celebrity");
			}
			break;
		case 577:
			if(language)
			{
				strcpy(name, "天体");
				return strlen("天体");
			}
			else
			{
				strcpy(name, "Celestial");
				return strlen("Celestial");
			}
			break;
		case 578:
			if(language)
			{
				strcpy(name, "Centrex");
				return strlen("Centrex");
			}
			else
			{
				strcpy(name, "Centrex");
				return strlen("Centrex");
			}
			break;
		case 579:
			if(language)
			{
				strcpy(name, "Centrurion");
				return strlen("Centrurion");
			}
			else
			{
				strcpy(name, "Centrurion");
				return strlen("Centrurion");
			}
			break;
		case 580:
			if(language)
			{
				strcpy(name, "春云");
				return strlen("春云");
			}
			else
			{
				strcpy(name, "Chun Yun");
				return strlen("Chun Yun");
			}
			break;
		case 581:
			if(language)
			{
				strcpy(name, "中兴");
				return strlen("中兴");
			}
			else
			{
				strcpy(name, "Chung Hsin");
				return strlen("Chung Hsin");
			}
			break;
		case 582:
			if(language)
			{
				strcpy(name, "Clarivox");
				return strlen("Clarivox");
			}
			else
			{
				strcpy(name, "Clarivox");
				return strlen("Clarivox");
			}
			break;
		case 583:
			if(language)
			{
				strcpy(name, "克莱顿");
				return strlen("克莱顿");
			}
			else
			{
				strcpy(name, "Clayton");
				return strlen("Clayton");
			}
			break;
		case 584:
			if(language)
			{
				strcpy(name, "商业方");
				return strlen("商业方");
			}
			else
			{
				strcpy(name, "Commercial Solutions");
				return strlen("Commercial Solutions");
			}
			break;
		case 585:
			if(language)
			{
				strcpy(name, "康尼亚");
				return strlen("康尼亚");
			}
			else
			{
				strcpy(name, "Conia");
				return strlen("Conia");
			}
			break;
		case 586:
			if(language)
			{
				strcpy(name, "Cosmel");
				return strlen("Cosmel");
			}
			else
			{
				strcpy(name, "Cosmel");
				return strlen("Cosmel");
			}
			break;
		case 587:
			if(language)
			{
				strcpy(name, "克罗斯利");
				return strlen("克罗斯利");
			}
			else
			{
				strcpy(name, "Crosley");
				return strlen("Crosley");
			}
			break;
		case 588:
			if(language)
			{
				strcpy(name, "霍乱");
				return strlen("霍乱");
			}
			else
			{
				strcpy(name, "CTX");
				return strlen("CTX");
			}
			break;
		case 589:
			if(language)
			{
				strcpy(name, "柯蒂斯马特斯");
				return strlen("柯蒂斯马特斯");
			}
			else
			{
				strcpy(name, "Curtis Mathes");
				return strlen("Curtis Mathes");
			}
			break;
		case 590:
			if(language)
			{
				strcpy(name, "DVision");
				return strlen("DVision");
			}
			else
			{
				strcpy(name, "DVision");
				return strlen("DVision");
			}
			break;
		case 591:
			if(language)
			{
				strcpy(name, "Dansai");
				return strlen("Dansai");
			}
			else
			{
				strcpy(name, "Dansai");
				return strlen("Dansai");
			}
			break;
		case 592:
			if(language)
			{
				strcpy(name, "Datsura");
				return strlen("Datsura");
			}
			else
			{
				strcpy(name, "Datsura");
				return strlen("Datsura");
			}
			break;
		case 593:
			if(language)
			{
				strcpy(name, "达瓦");
				return strlen("达瓦");
			}
			else
			{
				strcpy(name, "Dawa");
				return strlen("Dawa");
			}
			break;
		case 594:
			if(language)
			{
				strcpy(name, "Daytron");
				return strlen("Daytron");
			}
			else
			{
				strcpy(name, "Daytron");
				return strlen("Daytron");
			}
			break;
		case 595:
			if(language)
			{
				strcpy(name, "DEC");
				return strlen("DEC");
			}
			else
			{
				strcpy(name, "DEC");
				return strlen("DEC");
			}
			break;
		case 596:
			if(language)
			{
				strcpy(name, "丹佛");
				return strlen("丹佛");
			}
			else
			{
				strcpy(name, "Denver");
				return strlen("Denver");
			}
			break;
		case 597:
			if(language)
			{
				strcpy(name, "迪斯美特");
				return strlen("迪斯美特");
			}
			else
			{
				strcpy(name, "Desmet");
				return strlen("Desmet");
			}
			break;
		case 598:
			if(language)
			{
				strcpy(name, "钻石");
				return strlen("钻石");
			}
			else
			{
				strcpy(name, "Diamond");
				return strlen("Diamond");
			}
			break;
		case 599:
			if(language)
			{
				strcpy(name, "迪克史密斯电");
				return strlen("迪克史密斯电");
			}
			else
			{
				strcpy(name, "Dick Smith Elec");
				return strlen("Dick Smith Elec");
			}
			break;
		case 600:
			if(language)
			{
				strcpy(name, "Digatron");
				return strlen("Digatron");
			}
			else
			{
				strcpy(name, "Digatron");
				return strlen("Digatron");
			}
			break;
		case 601:
			if(language)
			{
				strcpy(name, "Digihome");
				return strlen("Digihome");
			}
			else
			{
				strcpy(name, "Digihome");
				return strlen("Digihome");
			}
			break;
		case 602:
			if(language)
			{
				strcpy(name, "Digiline");
				return strlen("Digiline");
			}
			else
			{
				strcpy(name, "Digiline");
				return strlen("Digiline");
			}
			break;
		case 603:
			if(language)
			{
				strcpy(name, "笛极玛");
				return strlen("笛极玛");
			}
			else
			{
				strcpy(name, "DigiMax");
				return strlen("DigiMax");
			}
			break;
		case 604:
			if(language)
			{
				strcpy(name, "数字生活");
				return strlen("数字生活");
			}
			else
			{
				strcpy(name, "Digital Life");
				return strlen("Digital Life");
			}
			break;
		case 605:
			if(language)
			{
				strcpy(name, "Digitex");
				return strlen("Digitex");
			}
			else
			{
				strcpy(name, "Digitex");
				return strlen("Digitex");
			}
			break;
		case 606:
			if(language)
			{
				strcpy(name, "消旋");
				return strlen("消旋");
			}
			else
			{
				strcpy(name, "DL");
				return strlen("DL");
			}
			break;
		case 607:
			if(language)
			{
				strcpy(name, "Domeos");
				return strlen("Domeos");
			}
			else
			{
				strcpy(name, "Domeos");
				return strlen("Domeos");
			}
			break;
		case 608:
			if(language)
			{
				strcpy(name, "东菱");
				return strlen("东菱");
			}
			else
			{
				strcpy(name, "Dongling");
				return strlen("Dongling");
			}
			break;
		case 609:
			if(language)
			{
				strcpy(name, "梦想");
				return strlen("梦想");
			}
			else
			{
				strcpy(name, "Drean");
				return strlen("Drean");
			}
			break;
		case 610:
			if(language)
			{
				strcpy(name, "试验");
				return strlen("试验");
			}
			else
			{
				strcpy(name, "DSE");
				return strlen("DSE");
			}
			break;
		case 611:
			if(language)
			{
				strcpy(name, "Durabrand");
				return strlen("Durabrand");
			}
			else
			{
				strcpy(name, "Durabrand");
				return strlen("Durabrand");
			}
			break;
		case 612:
			if(language)
			{
				strcpy(name, "达克斯");
				return strlen("达克斯");
			}
			else
			{
				strcpy(name, "Dux");
				return strlen("Dux");
			}
			break;
		case 613:
			if(language)
			{
				strcpy(name, "负阻");
				return strlen("负阻");
			}
			else
			{
				strcpy(name, "Dynatron");
				return strlen("Dynatron");
			}
			break;
		case 614:
			if(language)
			{
				strcpy(name, "荷子");
				return strlen("荷子");
			}
			else
			{
				strcpy(name, "Dyon");
				return strlen("Dyon");
			}
			break;
		case 615:
			if(language)
			{
				strcpy(name, "欧洲");
				return strlen("欧洲");
			}
			else
			{
				strcpy(name, "ECE");
				return strlen("ECE");
			}
			break;
		case 616:
			if(language)
			{
				strcpy(name, "易北河");
				return strlen("易北河");
			}
			else
			{
				strcpy(name, "Elbe");
				return strlen("Elbe");
			}
			break;
		case 617:
			if(language)
			{
				strcpy(name, "Electroband");
				return strlen("Electroband");
			}
			else
			{
				strcpy(name, "Electroband");
				return strlen("Electroband");
			}
			break;
		case 618:
			if(language)
			{
				strcpy(name, "艾丽卡");
				return strlen("艾丽卡");
			}
			else
			{
				strcpy(name, "Elektra");
				return strlen("Elektra");
			}
			break;
		case 619:
			if(language)
			{
				strcpy(name, "Elfunk");
				return strlen("Elfunk");
			}
			else
			{
				strcpy(name, "Elfunk");
				return strlen("Elfunk");
			}
			break;
		case 620:
			if(language)
			{
				strcpy(name, "埃尔格");
				return strlen("埃尔格");
			}
			else
			{
				strcpy(name, "ELG");
				return strlen("ELG");
			}
			break;
		case 621:
			if(language)
			{
				strcpy(name, "精英");
				return strlen("精英");
			}
			else
			{
				strcpy(name, "Elite");
				return strlen("Elite");
			}
			break;
		case 622:
			if(language)
			{
				strcpy(name, "恩泽");
				return strlen("恩泽");
			}
			else
			{
				strcpy(name, "Enzer");
				return strlen("Enzer");
			}
			break;
		case 623:
			if(language)
			{
				strcpy(name, "Erres");
				return strlen("Erres");
			}
			else
			{
				strcpy(name, "Erres");
				return strlen("Erres");
			}
			break;
		case 624:
			if(language)
			{
				strcpy(name, "欧空局");
				return strlen("欧空局");
			}
			else
			{
				strcpy(name, "ESA");
				return strlen("ESA");
			}
			break;
		case 625:
			if(language)
			{
				strcpy(name, "ESC");
				return strlen("ESC");
			}
			else
			{
				strcpy(name, "ESC");
				return strlen("ESC");
			}
			break;
		case 626:
			if(language)
			{
				strcpy(name, "智邦");
				return strlen("智邦");
			}
			else
			{
				strcpy(name, "Etron");
				return strlen("Etron");
			}
			break;
		case 627:
			if(language)
			{
				strcpy(name, "主角");
				return strlen("主角");
			}
			else
			{
				strcpy(name, "Euroman");
				return strlen("Euroman");
			}
			break;
		case 628:
			if(language)
			{
				strcpy(name, "Euroopa");
				return strlen("Euroopa");
			}
			else
			{
				strcpy(name, "Euroopa");
				return strlen("Euroopa");
			}
			break;
		case 629:
			if(language)
			{
				strcpy(name, "Eurphon");
				return strlen("Eurphon");
			}
			else
			{
				strcpy(name, "Eurphon");
				return strlen("Eurphon");
			}
			break;
		case 630:
			if(language)
			{
				strcpy(name, "伊夫舍姆");
				return strlen("伊夫舍姆");
			}
			else
			{
				strcpy(name, "Evesham");
				return strlen("Evesham");
			}
			break;
		case 631:
			if(language)
			{
				strcpy(name, "演化");
				return strlen("演化");
			}
			else
			{
				strcpy(name, "Evolution");
				return strlen("Evolution");
			}
			break;
		case 632:
			if(language)
			{
				strcpy(name, "艾克");
				return strlen("艾克");
			}
			else
			{
				strcpy(name, "Excello");
				return strlen("Excello");
			}
			break;
		case 633:
			if(language)
			{
				strcpy(name, "别致");
				return strlen("别致");
			}
			else
			{
				strcpy(name, "Exquisit");
				return strlen("Exquisit");
			}
			break;
		case 634:
			if(language)
			{
				strcpy(name, "弗格森爵士");
				return strlen("弗格森爵士");
			}
			else
			{
				strcpy(name, "Ferguson");
				return strlen("Ferguson");
			}
			break;
		case 635:
			if(language)
			{
				strcpy(name, "富达");
				return strlen("富达");
			}
			else
			{
				strcpy(name, "Fidelity");
				return strlen("Fidelity");
			}
			break;
		case 636:
			if(language)
			{
				strcpy(name, "道");
				return strlen("道");
			}
			else
			{
				strcpy(name, "Firstline");
				return strlen("Firstline");
			}
			break;
		case 637:
			if(language)
			{
				strcpy(name, "Fraba");
				return strlen("Fraba");
			}
			else
			{
				strcpy(name, "Fraba");
				return strlen("Fraba");
			}
			break;
		case 638:
			if(language)
			{
				strcpy(name, "Friac");
				return strlen("Friac");
			}
			else
			{
				strcpy(name, "Friac");
				return strlen("Friac");
			}
			break;
		case 639:
			if(language)
			{
				strcpy(name, "富士通西门子");
				return strlen("富士通西门子");
			}
			else
			{
				strcpy(name, "Fujitsu Siemens");
				return strlen("Fujitsu Siemens");
			}
			break;
		case 640:
			if(language)
			{
				strcpy(name, "富瑞驰");
				return strlen("富瑞驰");
			}
			else
			{
				strcpy(name, "Furichi");
				return strlen("Furichi");
			}
			break;
		case 641:
			if(language)
			{
				strcpy(name, "该");
				return strlen("该");
			}
			else
			{
				strcpy(name, "Futronic");
				return strlen("Futronic");
			}
			break;
		case 642:
			if(language)
			{
				strcpy(name, "GHanz");
				return strlen("GHanz");
			}
			else
			{
				strcpy(name, "GHanz");
				return strlen("GHanz");
			}
			break;
		case 643:
			if(language)
			{
				strcpy(name, "氨基丁酸");
				return strlen("氨基丁酸");
			}
			else
			{
				strcpy(name, "Gaba");
				return strlen("Gaba");
			}
			break;
		case 644:
			if(language)
			{
				strcpy(name, "加拉克塞");
				return strlen("加拉克塞");
			}
			else
			{
				strcpy(name, "Galaxi");
				return strlen("Galaxi");
			}
			break;
		case 645:
			if(language)
			{
				strcpy(name, "星际总动员");
				return strlen("星际总动员");
			}
			else
			{
				strcpy(name, "Galaxis");
				return strlen("Galaxis");
			}
			break;
		case 646:
			if(language)
			{
				strcpy(name, "通用电气");
				return strlen("通用电气");
			}
			else
			{
				strcpy(name, "GE");
				return strlen("GE");
			}
			break;
		case 647:
			if(language)
			{
				strcpy(name, "创世纪");
				return strlen("创世纪");
			}
			else
			{
				strcpy(name, "Genesis");
				return strlen("Genesis");
			}
			break;
		case 648:
			if(language)
			{
				strcpy(name, "Genexxa");
				return strlen("Genexxa");
			}
			else
			{
				strcpy(name, "Genexxa");
				return strlen("Genexxa");
			}
			break;
		case 649:
			if(language)
			{
				strcpy(name, "Gericom");
				return strlen("Gericom");
			}
			else
			{
				strcpy(name, "Gericom");
				return strlen("Gericom");
			}
			break;
		case 650:
			if(language)
			{
				strcpy(name, "金融市场");
				return strlen("金融市场");
			}
			else
			{
				strcpy(name, "GFM");
				return strlen("GFM");
			}
			break;
		case 651:
			if(language)
			{
				strcpy(name, "Goldfunk");
				return strlen("Goldfunk");
			}
			else
			{
				strcpy(name, "Goldfunk");
				return strlen("Goldfunk");
			}
			break;
		case 652:
			if(language)
			{
				strcpy(name, "金币");
				return strlen("金币");
			}
			else
			{
				strcpy(name, "GP");
				return strlen("GP");
			}
			break;
		case 653:
			if(language)
			{
				strcpy(name, "Gradiente");
				return strlen("Gradiente");
			}
			else
			{
				strcpy(name, "Gradiente");
				return strlen("Gradiente");
			}
			break;
		case 654:
			if(language)
			{
				strcpy(name, "格雷茨");
				return strlen("格雷茨");
			}
			else
			{
				strcpy(name, "Graetz");
				return strlen("Graetz");
			}
			break;
		case 655:
			if(language)
			{
				strcpy(name, "Grunkel");
				return strlen("Grunkel");
			}
			else
			{
				strcpy(name, "Grunkel");
				return strlen("Grunkel");
			}
			break;
		case 656:
			if(language)
			{
				strcpy(name, "日内瓦");
				return strlen("日内瓦");
			}
			else
			{
				strcpy(name, "GVA");
				return strlen("GVA");
			}
			break;
		case 657:
			if(language)
			{
				strcpy(name, "Haaz");
				return strlen("Haaz");
			}
			else
			{
				strcpy(name, "Haaz");
				return strlen("Haaz");
			}
			break;
		case 658:
			if(language)
			{
				strcpy(name, "标志");
				return strlen("标志");
			}
			else
			{
				strcpy(name, "Hallmark");
				return strlen("Hallmark");
			}
			break;
		case 659:
			if(language)
			{
				strcpy(name, "韩泰");
				return strlen("韩泰");
			}
			else
			{
				strcpy(name, "Hankook");
				return strlen("Hankook");
			}
			break;
		case 660:
			if(language)
			{
				strcpy(name, "Hantor");
				return strlen("Hantor");
			}
			else
			{
				strcpy(name, "Hantor");
				return strlen("Hantor");
			}
			break;
		case 661:
			if(language)
			{
				strcpy(name, "哈伍德");
				return strlen("哈伍德");
			}
			else
			{
				strcpy(name, "Harwood");
				return strlen("Harwood");
			}
			break;
		case 662:
			if(language)
			{
				strcpy(name, "霍波格");
				return strlen("霍波格");
			}
			else
			{
				strcpy(name, "Hauppauge");
				return strlen("Hauppauge");
			}
			break;
		case 663:
			if(language)
			{
				strcpy(name, "海兰");
				return strlen("海兰");
			}
			else
			{
				strcpy(name, "Highline");
				return strlen("Highline");
			}
			break;
		case 664:
			if(language)
			{
				strcpy(name, "奥克斯");
				return strlen("奥克斯");
			}
			else
			{
				strcpy(name, "AUX");
				return strlen("AUX");
			}
			break;
		case 665:
			if(language)
			{
				strcpy(name, "宝花视");
				return strlen("宝花视");
			}
			else
			{
				strcpy(name, "Bao spend");
				return strlen("Bao spend");
			}
			break;
		case 666:
			if(language)
			{
				strcpy(name, "鼎科");
				return strlen("鼎科");
			}
			else
			{
				strcpy(name, "Ding branch");
				return strlen("Ding branch");
			}
			break;
		case 667:
			if(language)
			{
				strcpy(name, "黃河");
				return strlen("黃河");
			}
			else
			{
				strcpy(name, "The Yellow River");
				return strlen("The Yellow River");
			}
			break;
		case 668:
			if(language)
			{
				strcpy(name, "柯蒂斯");
				return strlen("柯蒂斯");
			}
			else
			{
				strcpy(name, "curtis");
				return strlen("curtis");
			}
			break;
		case 669:
			if(language)
			{
				strcpy(name, "帕卡德");
				return strlen("帕卡德");
			}
			else
			{
				strcpy(name, "Packard");
				return strlen("Packard");
			}
			break;
		case 670:
			if(language)
			{
				strcpy(name, "Amstrad");
				return strlen("Amstrad");
			}
			else
			{
				strcpy(name, "Amstrad");
				return strlen("Amstrad");
			}
			break;
		case 671:
			if(language)
			{
				strcpy(name, "Apex");
				return strlen("Apex");
			}
			else
			{
				strcpy(name, "Apex");
				return strlen("Apex");
			}
			break;
		case 672:
			if(language)
			{
				strcpy(name, "Astro");
				return strlen("Astro");
			}
			else
			{
				strcpy(name, "Astro");
				return strlen("Astro");
			}
			break;
		case 673:
			if(language)
			{
				strcpy(name, "Asuka");
				return strlen("Asuka");
			}
			else
			{
				strcpy(name, "Asuka");
				return strlen("Asuka");
			}
			break;
		case 674:
			if(language)
			{
				strcpy(name, "贝灵巧");
				return strlen("贝灵巧");
			}
			else
			{
				strcpy(name, "Bei Lingqiao");
				return strlen("Bei Lingqiao");
			}
			break;
		case 675:
			if(language)
			{
				strcpy(name, "波特兰");
				return strlen("波特兰");
			}
			else
			{
				strcpy(name, "Portland");
				return strlen("Portland");
			}
			break;
		case 676:
			if(language)
			{
				strcpy(name, "丹尼克斯");
				return strlen("丹尼克斯");
			}
			else
			{
				strcpy(name, "Dan Nikos");
				return strlen("Dan Nikos");
			}
			break;
		case 677:
			if(language)
			{
				strcpy(name, "丹特声");
				return strlen("丹特声");
			}
			else
			{
				strcpy(name, "Dante Sound");
				return strlen("Dante Sound");
			}
			break;
		case 678:
			if(language)
			{
				strcpy(name, "飞歌");
				return strlen("飞歌");
			}
			else
			{
				strcpy(name, "Song");
				return strlen("Song");
			}
			break;
		case 679:
			if(language)
			{
				strcpy(name, "Fisher ");
				return strlen("Fisher ");
			}
			else
			{
				strcpy(name, "Fisher");
				return strlen("Fisher");
			}
			break;
		case 680:
			if(language)
			{
				strcpy(name, "富奈");
				return strlen("富奈");
			}
			else
			{
				strcpy(name, "Funai");
				return strlen("Funai");
			}
			break;
		case 681:
			if(language)
			{
				strcpy(name, "富士通先端");
				return strlen("富士通先端");
			}
			else
			{
				strcpy(name, "Fujitsu Apex");
				return strlen("Fujitsu Apex");
			}
			break;
		case 682:
			if(language)
			{
				strcpy(name, "高飞");
				return strlen("高飞");
			}
			else
			{
				strcpy(name, "Goofy");
				return strlen("Goofy");
			}
			break;
		case 683:
			if(language)
			{
				strcpy(name, "GE");
				return strlen("GE");
			}
			else
			{
				strcpy(name, "GE");
				return strlen("GE");
			}
			break;
		case 684:
			if(language)
			{
				strcpy(name, "歌林");
				return strlen("歌林");
			}
			else
			{
				strcpy(name, "Song Lin");
				return strlen("Song Lin");
			}
			break;
		case 685:
			if(language)
			{
				strcpy(name, "广东广电网络");
				return strlen("广东广电网络");
			}
			else
			{
				strcpy(name, "Guangdong Radio And TV Network");
				return strlen("Guangdong Radio And TV Network");
			}
			break;
		case 686:
			if(language)
			{
				strcpy(name, "海乐");
				return strlen("海乐");
			}
			else
			{
				strcpy(name, "Haile");
				return strlen("Haile");
			}
			break;
		case 687:
			if(language)
			{
				strcpy(name, "佳彩");
				return strlen("佳彩");
			}
			else
			{
				strcpy(name, "Good Luck");
				return strlen("Good Luck");
			}
			break;
		case 688:
			if(language)
			{
				strcpy(name, "捷威");
				return strlen("捷威");
			}
			else
			{
				strcpy(name, "Gateway");
				return strlen("Gateway");
			}
			break;
		case 689:
			if(language)
			{
				strcpy(name, "集品");
				return strlen("集品");
			}
			else
			{
				strcpy(name, "Set Pproduct");
				return strlen("Set Pproduct");
			}
			break;
		case 690:
			if(language)
			{
				strcpy(name, "九联");
				return strlen("九联");
			}
			else
			{
				strcpy(name, "Jiulian");
				return strlen("Jiulian");
			}
			break;
		case 691:
			if(language)
			{
				strcpy(name, "JNC ");
				return strlen("JNC ");
			}
			else
			{
				strcpy(name, "JNC");
				return strlen("JNC");
			}
			break;
		case 692:
			if(language)
			{
				strcpy(name, "乐视");
				return strlen("乐视");
			}
			else
			{
				strcpy(name, "Music");
				return strlen("Music");
			}
			break;
		case 693:
			if(language)
			{
				strcpy(name, "丽讯");
				return strlen("丽讯");
			}
			else
			{
				strcpy(name, "Vivitek");
				return strlen("Vivitek");
			}
			break;
		case 694:
			if(language)
			{
				strcpy(name, "摩托罗拉");
				return strlen("摩托罗拉");
			}
			else
			{
				strcpy(name, "Motorola");
				return strlen("Motorola");
			}
			break;
		case 695:
			if(language)
			{
				strcpy(name, "NAD");
				return strlen("NAD");
			}
			else
			{
				strcpy(name, "NAD");
				return strlen("NAD");
			}
			break;
		case 696:
			if(language)
			{
				strcpy(name, "NEC");
				return strlen("NEC");
			}
			else
			{
				strcpy(name, "NEC");
				return strlen("NEC");
			}
			break;
		case 697:
			if(language)
			{
				strcpy(name, "OLEVIA ");
				return strlen("OLEVIA ");
			}
			else
			{
				strcpy(name, "OLEVIA");
				return strlen("OLEVIA");
			}
			break;
		case 698:
			if(language)
			{
				strcpy(name, "欧迪福斯");
				return strlen("欧迪福斯");
			}
			else
			{
				strcpy(name, "Audiovox");
				return strlen("Audiovox");
			}
			break;
		case 699:
			if(language)
			{
				strcpy(name, "讴歌");
				return strlen("讴歌");
			}
			else
			{
				strcpy(name, "Eulogize");
				return strlen("Eulogize");
			}
			break;
		case 700:
			if(language)
			{
				strcpy(name, "普腾");
				return strlen("普腾");
			}
			else
			{
				strcpy(name, "Proton");
				return strlen("Proton");
			}
			break;
		case 701:
			if(language)
			{
				strcpy(name, "Quasar");
				return strlen("Quasar");
			}
			else
			{
				strcpy(name, "Quasar");
				return strlen("Quasar");
			}
			break;
		case 702:
			if(language)
			{
				strcpy(name, "RCA");
				return strlen("RCA");
			}
			else
			{
				strcpy(name, "RCA");
				return strlen("RCA");
			}
			break;
		case 703:
			if(language)
			{
				strcpy(name, "RFT");
				return strlen("RFT");
			}
			else
			{
				strcpy(name, "RFT");
				return strlen("RFT");
			}
			break;
		case 704:
			if(language)
			{
				strcpy(name, "日高");
				return strlen("日高");
			}
			else
			{
				strcpy(name, "NIKKO");
				return strlen("NIKKO");
			}
			break;
		case 705:
			if(language)
			{
				strcpy(name, "睿侠");
				return strlen("睿侠");
			}
			else
			{
				strcpy(name, "Rui Xia");
				return strlen("Rui Xia");
			}
			break;
		case 706:
			if(language)
			{
				strcpy(name, "Runco");
				return strlen("Runco");
			}
			else
			{
				strcpy(name, "Runco");
				return strlen("Runco");
			}
			break;
		case 707:
			if(language)
			{
				strcpy(name, "Sears ");
				return strlen("Sears ");
			}
			else
			{
				strcpy(name, "Sears");
				return strlen("Sears");
			}
			break;
		case 708:
			if(language)
			{
				strcpy(name, "声光");
				return strlen("声光");
			}
			else
			{
				strcpy(name, "Acousto");
				return strlen("Acousto");
			}
			break;
		case 709:
			if(language)
			{
				strcpy(name, "视丽");
				return strlen("视丽");
			}
			else
			{
				strcpy(name, "Shili");
				return strlen("Shili");
			}
			break;
		case 710:
			if(language)
			{
				strcpy(name, "施耐德");
				return strlen("施耐德");
			}
			else
			{
				strcpy(name, "Schneider");
				return strlen("Schneider");
			}
			break;
		case 711:
			if(language)
			{
				strcpy(name, "数码星空");
				return strlen("数码星空");
			}
			else
			{
				strcpy(name, "Digital Sky");
				return strlen("Digital Sky");
			}
			break;
		case 712:
			if(language)
			{
				strcpy(name, "SONYO");
				return strlen("SONYO");
			}
			else
			{
				strcpy(name, "SONYO");
				return strlen("SONYO");
			}
			break;
		case 713:
			if(language)
			{
				strcpy(name, "Synco");
				return strlen("Synco");
			}
			else
			{
				strcpy(name, "Synco");
				return strlen("Synco");
			}
			break;
		case 714:
			if(language)
			{
				strcpy(name, "TELEFUNKEN");
				return strlen("TELEFUNKEN");
			}
			else
			{
				strcpy(name, "TELEFUNKEN");
				return strlen("TELEFUNKEN");
			}
			break;
		case 715:
			if(language)
			{
				strcpy(name, "腾博");
				return strlen("腾博");
			}
			else
			{
				strcpy(name, "Tang Bo");
				return strlen("Tang Bo");
			}
			break;
		case 716:
			if(language)
			{
				strcpy(name, "统帅");
				return strlen("统帅");
			}
			else
			{
				strcpy(name, "Commander In Chief");
				return strlen("Commander In Chief");
			}
			break;
		case 717:
			if(language)
			{
				strcpy(name, "TOPCON");
				return strlen("TOPCON");
			}
			else
			{
				strcpy(name, "TOPCON");
				return strlen("TOPCON");
			}
			break;
		case 718:
			if(language)
			{
				strcpy(name, "VDiGi");
				return strlen("VDiGi");
			}
			else
			{
				strcpy(name, "VDiGi");
				return strlen("VDiGi");
			}
			break;
		case 719:
			if(language)
			{
				strcpy(name, "Victor");
				return strlen("Victor");
			}
			else
			{
				strcpy(name, "Victor");
				return strlen("Victor");
			}
			break;
		case 720:
			if(language)
			{
				strcpy(name, "Videocon");
				return strlen("Videocon");
			}
			else
			{
				strcpy(name, "Videocon");
				return strlen("Videocon");
			}
			break;
		case 721:
			if(language)
			{
				strcpy(name, "Viewpia");
				return strlen("Viewpia");
			}
			else
			{
				strcpy(name, "Viewpia");
				return strlen("Viewpia");
			}
			break;
		case 722:
			if(language)
			{
				strcpy(name, "ViViD");
				return strlen("ViViD");
			}
			else
			{
				strcpy(name, "ViViD");
				return strlen("ViViD");
			}
			break;
		case 723:
			if(language)
			{
				strcpy(name, "万利达");
				return strlen("万利达");
			}
			else
			{
				strcpy(name, "Malata");
				return strlen("Malata");
			}
			break;
		case 724:
			if(language)
			{
				strcpy(name, "唯冠");
				return strlen("唯冠");
			}
			else
			{
				strcpy(name, "Proview");
				return strlen("Proview");
			}
			break;
		case 725:
			if(language)
			{
				strcpy(name, "沃夫德尔");
				return strlen("沃夫德尔");
			}
			else
			{
				strcpy(name, "Wolf Del");
				return strlen("Wolf Del");
			}
			break;
		case 726:
			if(language)
			{
				strcpy(name, "先科");
				return strlen("先科");
			}
			else
			{
				strcpy(name, "SAST");
				return strlen("SAST");
			}
			break;
		case 727:
			if(language)
			{
				strcpy(name, "西凡尼亚");
				return strlen("西凡尼亚");
			}
			else
			{
				strcpy(name, "Sylvania");
				return strlen("Sylvania");
			}
			break;
		case 728:
			if(language)
			{
				strcpy(name, "喜事来");
				return strlen("喜事来");
			}
			else
			{
				strcpy(name, "Event To");
				return strlen("Event To");
			}
			break;
		case 729:
			if(language)
			{
				strcpy(name, "雅马哈");
				return strlen("雅马哈");
			}
			else
			{
				strcpy(name, "Yamaha");
				return strlen("Yamaha");
			}
			break;
		case 730:
			if(language)
			{
				strcpy(name, "怡敏信");
				return strlen("怡敏信");
			}
			else
			{
				strcpy(name, "Yi Minxin");
				return strlen("Yi Minxin");
			}
			break;
		case 731:
			if(language)
			{
				strcpy(name, "友利电");
				return strlen("友利电");
			}
			else
			{
				strcpy(name, "Uniden");
				return strlen("Uniden");
			}
			break;
		case 732:
			if(language)
			{
				strcpy(name, "优图");
				return strlen("优图");
			}
			else
			{
				strcpy(name, "Excellent Map");
				return strlen("Excellent Map");
			}
			break;
		case 733:
			if(language)
			{
				strcpy(name, "圆刚");
				return strlen("圆刚");
			}
			else
			{
				strcpy(name, "AVerMedia");
				return strlen("AVerMedia");
			}
			break;
		case 734:
			if(language)
			{
				strcpy(name, "Zenith ");
				return strlen("Zenith ");
			}
			else
			{
				strcpy(name, "Zenith");
				return strlen("Zenith");
			}
			break;
		case 735:
			if(language)
			{
				strcpy(name, "兆赫");
				return strlen("兆赫");
			}
			else
			{
				strcpy(name, "MHz");
				return strlen("MHz");
			}
			break;
		case 736:
			if(language)
			{
				strcpy(name, "爱家乐");
				return strlen("爱家乐");
			}
			else
			{
				strcpy(name, "Angostura");
				return strlen("Angostura");
			}
			break;
		case 737:
			if(language)
			{
				strcpy(name, "爱普生");
				return strlen("爱普生");
			}
			else
			{
				strcpy(name, "Epson");
				return strlen("Epson");
			}
			break;
		case 738:
			if(language)
			{
				strcpy(name, "爱其");
				return strlen("爱其");
			}
			else
			{
				strcpy(name, "Love");
				return strlen("Love");
			}
			break;
		case 739:
			if(language)
			{
				strcpy(name, "Ampro");
				return strlen("Ampro");
			}
			else
			{
				strcpy(name, "Ampro");
				return strlen("Ampro");
			}
			break;
		case 740:
			if(language)
			{
				strcpy(name, "百视通");
				return strlen("百视通");
			}
			else
			{
				strcpy(name, "Bestv");
				return strlen("Bestv");
			}
			break;
		case 741:
			if(language)
			{
				strcpy(name, "倍科");
				return strlen("倍科");
			}
			else
			{
				strcpy(name, "Beko");
				return strlen("Beko");
			}
			break;
		case 742:
			if(language)
			{
				strcpy(name, "朝野");
				return strlen("朝野");
			}
			else
			{
				strcpy(name, "Government");
				return strlen("Government");
			}
			break;
		case 743:
			if(language)
			{
				strcpy(name, "晨星");
				return strlen("晨星");
			}
			else
			{
				strcpy(name, "Morning Star");
				return strlen("Morning Star");
			}
			break;
		case 744:
			if(language)
			{
				strcpy(name, "大亚");
				return strlen("大亚");
			}
			else
			{
				strcpy(name, "Daya");
				return strlen("Daya");
			}
			break;
		case 745:
			if(language)
			{
				strcpy(name, "蒂雅克");
				return strlen("蒂雅克");
			}
			else
			{
				strcpy(name, "Teac");
				return strlen("Teac");
			}
			break;
		case 746:
			if(language)
			{
				strcpy(name, "Dwin");
				return strlen("Dwin");
			}
			else
			{
				strcpy(name, "Dwin");
				return strlen("Dwin");
			}
			break;
		case 747:
			if(language)
			{
				strcpy(name, "Eight ");
				return strlen("Eight ");
			}
			else
			{
				strcpy(name, "Eight");
				return strlen("Eight");
			}
			break;
		case 748:
			if(language)
			{
				strcpy(name, "飞越");
				return strlen("飞越");
			}
			else
			{
				strcpy(name, "Fly");
				return strlen("Fly");
			}
			break;
		case 749:
			if(language)
			{
				strcpy(name, "丰泽");
				return strlen("丰泽");
			}
			else
			{
				strcpy(name, "Fortress");
				return strlen("Fortress");
			}
			break;
		case 750:
			if(language)
			{
				strcpy(name, "GlobeCast");
				return strlen("GlobeCast");
			}
			else
			{
				strcpy(name, "GlobeCast");
				return strlen("GlobeCast");
			}
			break;
		case 751:
			if(language)
			{
				strcpy(name, "海美迪");
				return strlen("海美迪");
			}
			else
			{
				strcpy(name, "Sea Di");
				return strlen("Sea Di");
			}
			break;
		case 752:
			if(language)
			{
				strcpy(name, "海人草");
				return strlen("海人草");
			}
			else
			{
				strcpy(name, "Tanaka");
				return strlen("Tanaka");
			}
			break;
		case 753:
			if(language)
			{
				strcpy(name, "哈曼卡顿 ");
				return strlen("哈曼卡顿 ");
			}
			else
			{
				strcpy(name, "Harman Kardon");
				return strlen("Harman Kardon");
			}
			break;
		case 754:
			if(language)
			{
				strcpy(name, "Hanso");
				return strlen("Hanso");
			}
			else
			{
				strcpy(name, "Hanso");
				return strlen("Hanso");
			}
			break;
		case 755:
			if(language)
			{
				strcpy(name, "HCT");
				return strlen("HCT");
			}
			else
			{
				strcpy(name, "HCT");
				return strlen("HCT");
			}
			break;
		case 756:
			if(language)
			{
				strcpy(name, "贺曼");
				return strlen("贺曼");
			}
			else
			{
				strcpy(name, "Herman");
				return strlen("Herman");
			}
			break;
		case 757:
			if(language)
			{
				strcpy(name, "华生");
				return strlen("华生");
			}
			else
			{
				strcpy(name, "Wahson");
				return strlen("Wahson");
			}
			break;
		case 758:
			if(language)
			{
				strcpy(name, "华为");
				return strlen("华为");
			}
			else
			{
				strcpy(name, "Huawei");
				return strlen("Huawei");
			}
			break;
		case 759:
			if(language)
			{
				strcpy(name, "i潘多拉");
				return strlen("i潘多拉");
			}
			else
			{
				strcpy(name, "I Pandora");
				return strlen("I Pandora");
			}
			break;
		case 760:
			if(language)
			{
				strcpy(name, "Jerrold");
				return strlen("Jerrold");
			}
			else
			{
				strcpy(name, "Jerrold");
				return strlen("Jerrold");
			}
			break;
		case 761:
			if(language)
			{
				strcpy(name, "佳美的");
				return strlen("佳美的");
			}
			else
			{
				strcpy(name, "Good");
				return strlen("Good");
			}
			break;
		case 762:
			if(language)
			{
				strcpy(name, "江西");
				return strlen("江西");
			}
			else
			{
				strcpy(name, "Jiangxi");
				return strlen("Jiangxi");
			}
			break;
		case 763:
			if(language)
			{
				strcpy(name, "江西有线 ");
				return strlen("江西有线 ");
			}
			else
			{
				strcpy(name, "Jiangxi Cable");
				return strlen("Jiangxi Cable");
			}
			break;
		case 764:
			if(language)
			{
				strcpy(name, "集成");
				return strlen("集成");
			}
			else
			{
				strcpy(name, "Integrate");
				return strlen("Integrate");
			}
			break;
		case 765:
			if(language)
			{
				strcpy(name, "杰科");
				return strlen("杰科");
			}
			else
			{
				strcpy(name, "Jieke");
				return strlen("Jieke");
			}
			break;
		case 766:
			if(language)
			{
				strcpy(name, "凯擘");
				return strlen("凯擘");
			}
			else
			{
				strcpy(name, "KBrO");
				return strlen("KBrO");
			}
			break;
		case 767:
			if(language)
			{
				strcpy(name, "凯驰");
				return strlen("凯驰");
			}
			else
			{
				strcpy(name, "Kai Chi");
				return strlen("Kai Chi");
			}
			break;
		case 768:
			if(language)
			{
				strcpy(name, "科朗");
				return strlen("科朗");
			}
			else
			{
				strcpy(name, "Konon");
				return strlen("Konon");
			}
			break;
		case 769:
			if(language)
			{
				strcpy(name, "浪潮");
				return strlen("浪潮");
			}
			else
			{
				strcpy(name, "Wave");
				return strlen("Wave");
			}
			break;
		case 770:
			if(language)
			{
				strcpy(name, "蓝星");
				return strlen("蓝星");
			}
			else
			{
				strcpy(name, "LAN-STAR");
				return strlen("LAN-STAR");
			}
			break;
		case 771:
			if(language)
			{
				strcpy(name, "陇华");
				return strlen("陇华");
			}
			else
			{
				strcpy(name, "Long Hua");
				return strlen("Long Hua");
			}
			break;
		case 772:
			if(language)
			{
				strcpy(name, "罗意威");
				return strlen("罗意威");
			}
			else
			{
				strcpy(name, "Loewe");
				return strlen("Loewe");
			}
			break;
		case 773:
			if(language)
			{
				strcpy(name, "Mag");
				return strlen("Mag");
			}
			else
			{
				strcpy(name, "Mag");
				return strlen("Mag");
			}
			break;
		case 774:
			if(language)
			{
				strcpy(name, "梅捷");
				return strlen("梅捷");
			}
			else
			{
				strcpy(name, "Soyo");
				return strlen("Soyo");
			}
			break;
		case 775:
			if(language)
			{
				strcpy(name, "美如画");
				return strlen("美如画");
			}
			else
			{
				strcpy(name, "Picture");
				return strlen("Picture");
			}
			break;
		case 776:
			if(language)
			{
				strcpy(name, "Mod");
				return strlen("Mod");
			}
			else
			{
				strcpy(name, "Mod");
				return strlen("Mod");
			}
			break;
		case 777:
			if(language)
			{
				strcpy(name, "盘古");
				return strlen("盘古");
			}
			else
			{
				strcpy(name, "Pangu");
				return strlen("Pangu");
			}
			break;
		case 778:
			if(language)
			{
				strcpy(name, "pro2");
				return strlen("pro2");
			}
			else
			{
				strcpy(name, "Pro2");
				return strlen("Pro2");
			}
			break;
		case 779:
			if(language)
			{
				strcpy(name, "清华紫光 ");
				return strlen("清华紫光 ");
			}
			else
			{
				strcpy(name, "Thunis");
				return strlen("Thunis");
			}
			break;
		case 780:
			if(language)
			{
				strcpy(name, "清远广电");
				return strlen("清远广电");
			}
			else
			{
				strcpy(name, "Qingyuan Radio And Television");
				return strlen("Qingyuan Radio And Television");
			}
			break;
		case 781:
			if(language)
			{
				strcpy(name, "日派");
				return strlen("日派");
			}
			else
			{
				strcpy(name, "Day School");
				return strlen("Day School");
			}
			break;
		case 782:
			if(language)
			{
				strcpy(name, "瑞安广电");
				return strlen("瑞安广电");
			}
			else
			{
				strcpy(name, "Ruian Radio And Television");
				return strlen("Ruian Radio And Television");
			}
			break;
		case 783:
			if(language)
			{
				strcpy(name, "赛博");
				return strlen("赛博");
			}
			else
			{
				strcpy(name, "Cyber");
				return strlen("Cyber");
			}
			break;
		case 784:
			if(language)
			{
				strcpy(name, "赛普特");
				return strlen("赛普特");
			}
			else
			{
				strcpy(name, "Seibt");
				return strlen("Seibt");
			}
			break;
		case 785:
			if(language)
			{
				strcpy(name, "萨基姆");
				return strlen("萨基姆");
			}
			else
			{
				strcpy(name, "Sagem");
				return strlen("Sagem");
			}
			break;
		case 786:
			if(language)
			{
				strcpy(name, "三元");
				return strlen("三元");
			}
			else
			{
				strcpy(name, "Three Yuan");
				return strlen("Three Yuan");
			}
			break;
		case 787:
			if(language)
			{
				strcpy(name, "Sentra");
				return strlen("Sentra");
			}
			else
			{
				strcpy(name, "Sentra");
				return strlen("Sentra");
			}
			break;
		case 788:
			if(language)
			{
				strcpy(name, "视朗");
				return strlen("视朗");
			}
			else
			{
				strcpy(name, "As Long");
				return strlen("As Long");
			}
			break;
		case 789:
			if(language)
			{
				strcpy(name, "索佳");
				return strlen("索佳");
			}
			else
			{
				strcpy(name, "Sokkia");
				return strlen("Sokkia");
			}
			break;
		case 790:
			if(language)
			{
				strcpy(name, "Technics");
				return strlen("Technics");
			}
			else
			{
				strcpy(name, "Technics");
				return strlen("Technics");
			}
			break;
		case 791:
			if(language)
			{
				strcpy(name, "同洲");
				return strlen("同洲");
			}
			else
			{
				strcpy(name, "Tongzhou");
				return strlen("Tongzhou");
			}
			break;
		case 792:
			if(language)
			{
				strcpy(name, "UT斯达康");
				return strlen("UT斯达康");
			}
			else
			{
				strcpy(name, "UT Starcom");
				return strlen("UT Starcom");
			}
			break;
		case 793:
			if(language)
			{
				strcpy(name, "武进");
				return strlen("武进");
			}
			else
			{
				strcpy(name, "Wujin");
				return strlen("Wujin");
			}
			break;
		case 794:
			if(language)
			{
				strcpy(name, "现代");
				return strlen("现代");
			}
			else
			{
				strcpy(name, "Modern");
				return strlen("Modern");
			}
			break;
		case 795:
			if(language)
			{
				strcpy(name, "雅俊");
				return strlen("雅俊");
			}
			else
			{
				strcpy(name, "Ya Jun");
				return strlen("Ya Jun");
			}
			break;
		case 796:
			if(language)
			{
				strcpy(name, "宜家");
				return strlen("宜家");
			}
			else
			{
				strcpy(name, "IKEA");
				return strlen("IKEA");
			}
			break;
		case 797:
			if(language)
			{
				strcpy(name, "英非克");
				return strlen("英非克");
			}
			else
			{
				strcpy(name, "Yingfeike");
				return strlen("Yingfeike");
			}
			break;
		case 798:
			if(language)
			{
				strcpy(name, "影雅");
				return strlen("影雅");
			}
			else
			{
				strcpy(name, "Shadow Ya");
				return strlen("Shadow Ya");
			}
			break;
		case 799:
			if(language)
			{
				strcpy(name, "银河");
				return strlen("银河");
			}
			else
			{
				strcpy(name, "Galaxy");
				return strlen("Galaxy");
			}
			break;
		case 800:
			if(language)
			{
				strcpy(name, "优群");
				return strlen("优群");
			}
			else
			{
				strcpy(name, "Excellent Group");
				return strlen("Excellent Group");
			}
			break;
		case 801:
			if(language)
			{
				strcpy(name, "有用");
				return strlen("有用");
			}
			else
			{
				strcpy(name, "Useful");
				return strlen("Useful");
			}
			break;
		case 802:
			if(language)
			{
				strcpy(name, "忠冠");
				return strlen("忠冠");
			}
			else
			{
				strcpy(name, "Honest Crown");
				return strlen("Honest Crown");
			}
			break;
		case 803:
			if(language)
			{
				strcpy(name, "中国电信");
				return strlen("中国电信");
			}
			else
			{
				strcpy(name, "China Telecom");
				return strlen("China Telecom");
			}
			break;
		case 804:
			if(language)
			{
				strcpy(name, "中恒");
				return strlen("中恒");
			}
			else
			{
				strcpy(name, "In The Constant");
				return strlen("In The Constant");
			}
			break;
		case 805:
			if(language)
			{
				strcpy(name, "声光智能科技");
				return strlen("声光智能科技");
			}
			else
			{
				strcpy(name, "Acousto-optic intelligent science and technology");
				return strlen("Acousto-optic intelligent science and technology");
			}
			break;
		case 806:
			if(language)
			{
				strcpy(name, "全部");
				return strlen("全部");
			}
			else
			{
				strcpy(name, "All");
				return strlen("All");
			}
			break;
		case 807:
			if(language)
			{
				strcpy(name, "其它");
				return strlen("其它");
			}
			else
			{
				strcpy(name, "Other");
				return strlen("Other");
			}
			break;
	}
}


/* 每一行的第一个数据表示,数据的总数,作为对码计数,第二个数据以后,为对应上述表格中的序号,从0开始,作为地区对码时的选择范围 */
/* 电视智能库索引 */
const	int TV_info[][2500]= /*[][10494] 2014-6-22 13:55 10493*/
{

    {449,/*  7516前 */
    10810,10811,10812,10813,10814,
    10788,10789,10790,10791,10792,10530,10531,10532,10533,10534,10508,10509,10510,10511,10512,6625,5416,5438,7514,7517,7557,7559,7532,7534,7518,7526,7533,7535,7540,7528,8864,7515,6620,7516,9864,9865,9866,9867,9868,9869,9870,9871,9872,9873,9874,9875,9876,9877,9878,9879,9880,9881,9882,9883,9884,9885,9886,9887,9888,9889,9890,9891,9892,9893,7519,7520,7521,7522,7523,7524,7525,7527,7529,7530,7531,7536,7537,7538,7539,7541,7542,7543,7544,7545,7546,7547,7548,7549,7550,7551,7552,7553,7554,7555,7556,7558,7560,7561,7562,7563,7564,7565,
    5814,9263,9264,8820,8821,8822,8823,8824,8825,8826,8827,8828,8829,8830,8831,8832,8833,8834,8835,8836,8837,8838,8839,8840,8841,8842,8843,8844,8845,8846,8847,8848,8849,8850,8851,8852,8853,8854,8855,8856,8857,8858,8859,8860,8861,8862,8863,8865,8866,8867,8868,8869,8622,8623,8624,8625,8626,8627,
    6616,6617,6618,6619,6621,6622,6623,6624,6626,6627,6628,6629,6630,6631,6632,5815,1065,5411,5412,5413,5414,5415,5417,5418,5419,5420,5421,5422,5423,5424,5425,5426,5427,5428,5429,5430,5431,5432,5433,5434,5435,5436,5437,4696,4697,4698,4699,4700,4701,4702,4703,4704,4705,4706,4707,4708,4709,4710,4711,4712,
    4713,4714,4715,4716,4717,4718,4719,4720,4721,4722,4723,4724,4725,4726,4727,4728,4729,4730,4731,4732,4733,4734,4735,1038,1039,1040,1041,1042,1043,1001,1002,1022,1031,65,305,122,51,53,346,109,35,73,293,17,86,177,45,19,276,194,186,253,288,358,328,6,283,377, 21,231,334,254,316,
    268,20,298,292, 40,221, /* 43 */
    841,842,843,844,845,556,558,559,560,561,562,563,564,565,566,567,621,623,624,625,
    661,662,663,664,725,749,819,824,828,852,853,854,855,856,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,712,713,714,715,
    716,717,718,719,720,721,722,723,724,726,727,728,729,730,731,789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,
    807,808,809,810,811,812,813,814,815,816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879,1,70,125,284,350,57},/* 长虹(Changhong)@48(+1*/
    {395,   /* 7566前*/
    10835,10836,10837,10838,10839,
    10793,10555,10556,10557,10558,10559,10513,6810,7583,7585,7577,7584,7595,7581,6812,6821,7575,7592,7578,7587,7593,7574,7582,7566,7567,7568,7569,7570,7571,7572,7573,7576,7579,7580,7586,7588,7589,7590,7591,7594,7596,7597,7598,7599,7600,7601,7602,7603,7604,7605,7606,7607,7608,7609,7610,7611,7612,7613,
    6054,9270,9271,8783,8784,8785,8786,8787,8788,8789,8790,8791,8792,8793,8794,8795,8796,8797,8798,8799,8800,8801,8802,8803,8804,8805,8806,8807,8808,8809,8810,8811,8812,8813,8814,8815,8816,8817,8818,8819,8614,8615,8616,8617,8618,8619,8620,6802,6803,6804,6805,6806,6807,6808,6809,6811,6813,6814,6815,6816,6817,6818,6819,6820,
    6822,6823,6824,6825,6826,6827,6828,6829,6055,6056,6057,6058,6059,5545,5546,5547,5548,5549,5550,5551,5552,5553,5554,5555,5556,5557,4965,4966,4967,4968,4969,4970,4971,4972,4973,4974,4975,4976,4977,4978,4979,4980,4981,4982,4983,4984,4985,4986,
    4987,4988,4989,4990,4991,4992,4993,4994,4995,4996,4997,4998,4999,5000,5001,5002,5003,5004,5005,5006,5007,1084,1085,1086,10282,
    1087,1088,1089,1090,1091,1092,1093,1094,1095,1096,1097,1098,1099,1100,1101,1102,1103,1104,1105,1106,1107,1108,1109,1110,1111,
    1112,1113,1114,1115,1116,1117,1118,1119,1120,1121,1122,1123,1124,1125,1126,1127,1128,1129,1130,1131,1132,1133,1134,1135,1136,
    1137,1138,1139,1140,1141,1142,1143,1144,1145,1146,1147,1148,1149,1150,1151,1152,1153,1154,1155,1156,2,246,297,53,99,205,106,131,159,19,193,276,105,145,187,170,233,148,146,66,332,247,368,84,317,72,275,301,140,39,93,176,
    18,229,356,314,119,272,208,230,320,173,357,326,789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,
    808,809,810,811,812,813,814,815,816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879},/* 康佳(Konka)@48(+80  */
    {400,/* 7679前 */
    10851,10852,10853,10854,10855,10794,
    10571,10572,10573,10574,10575,10514,7682,7684,7687,7712,8968,8967,8977,7683,7692,7693,7681,7685,7679,7680,7686,7688,7689,7690,7691,7694,7695,7696,7697,7698,7699,7700,7701,7702,7703,7704,7705,7706,7707,7708,7709,7710,7711,7713,7714,7715,7716,7717,7718,7719,
    6973,8956,8957,8958,8959,8960,8961,8962,8963,8964,8965,8966,8969,8970,8971,8972,8973,8974,8975,8976,8978,8979,8980,8981,8982,8983,8984,8985,8986,8987,8988,8989,8990,8991,8992,8993,8994,8995,8996,8997,8998,8999,9000,9001,9002,9003,
    8621,6974,6975,6976,6977,6978,6978,6980,6981,6982,6983,6984,6985,6986,6987,6988,6989,6990,6991,6992,6993,6994,6995,6996,1405,5607,5608,5609,5610,5611,5612,5613,5614,5615,5616,5617,5618,5619,5620,5621,5622,5623,5624,5625,5626,5627,5628,5629,1406,1407,1408,1409,1410,1411,1412,1413,1414,1415,1416,1417,1418,1419,1420,1421,1422,1423,1424,1425,1426,1427,1428,1429,1430,1431,1432,
    1433,1434,1435,1436,1437,1438,1439,1440,1441,1442,1443,1444,1445,1446,1447,1448,1449,1450,1451,1452,1453,1454,1455,1456,1457,1458,1459,1460,
    1461,1462,1463,1464,1465,1466,1467,1468,1469,1470,1471,1472,1473,1474,1475,1476,1477,1478,1479,1480,1481,1482,1483,1484,1485,1486,1487,1488,
    1489,1490,1491,1492,1493,1494,1495,1496,1497,1498,1499,1500,1501,1502,1503,1504,371,298,17,134,218,276,263,342,181,183,150,360,285,62,199,257,206,132,207,239,42,212,200,30,12,379,312,318,259,143,270,180,101,161,
    581,582,583,584,585, 586,587,588,589,590,591,592,593,594,594,595,596,597,598,599,600,601,602,603,604,605,606,607,
    608,609,610,611,612,613,619,620,622,626,627,628,629,630,631,632,633,634,635,636,637,641,642,643,644,645,646,647,
    648,649,650,651,652,653,654,655,656,659,659,660,661,662,663,664,665,666,667,668,669,670,671,672,673,674,675,676,
    677,678,679,680,681,682,683,685,685,686,687,688,689,690,691,692,693,694,695,696,697,698,699,700,701,702,703,704,6352,6353,6504},/*TCL(TCL)@48(+112 */
    {242,/* 6997前插入1组 */
    8458,6997,6998,6999,7000,7001,7002,7003,7004,7005,7006,7007,7008,7009,7010,7011,7012,7013,7014,7015,7016,7017,7018,7019,7020,7021,7022,5154,5630,5631,5632,5633,5634,5635,5636,5637,5638,5639,5640,5641,5642,5643,5644,5645,5646,5647,5648,5649,5650,5651,5652,5653,5654,5655,5656,5657,5658,5659,5660,5661,5662,5663,5664,5665,5155,5156,5157,5158,5159,5160,5161,5162,5163,5164,5165,5166,
    5167,5168,5169,5170,5171,5172,5173,5174,5175,5176,5177,5178,5179,
    5180,5181,5182,5183,5184,5185,5186,5231,5232,5233,5234,5235,5236,5237,5238,5239,5240,5241,5242,5243,5244,5245,5246,5247,5248,5249,5250,
    5251,5252,5253,5254,5255,5256,5257,5258,5259,5260,5261,5262,5263,5264,5265,5266,5267,5268,5269,5270,5271,5272,5273,5274,5275,5276,5277,
    5278,5279,5280,5281,5282,5283,5284,5285,5286,5287,5288,5289,5290,5291,5292,5,19,52,53,109,174,295,91,293,134,159,359,335,167,354,148,
    16,233,263,206,38,245,120,97,361,343,0,77,96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,
    614,615,616,618,621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932},/* 厦华(Prima)@48(+56   */
    {806,         /* 7614前 */
    10815,10816,10817,10818,10819,
    10535,10536,10537,10538,10539,7628,7647,7660,7620,7621,8688,8689,7632,7651,7623,7637,7643,6638,5063,7630,7617,7649,7657,7635,8710,8715,8716,7614,7615,7616,7618,7619,7622,7624,7625,7626,7627,7629,7631,7633,7634,7636,7638,7639,7640,7641,7642,7644,7645,7646,7648,7650,7652,7653,7654,7655,7656,7657,7658,7659,7661,7662,7663,7664,7665,7666,7667,7668,7669,7670,7671,7672,7673,7674,7675,7676,7677,7678,
    6312,9272,9273,9274,9275,9276,9277,9278,8661,8662,8663,8664,8665,8666,8667,8668,8669,8670,8671,8672,8673,8674,8675,8676,8677,8678,8679,8680,8681,8682,8683,8684,8685,8687,8690,8691,8692,8693,8694,8695,8696,8697,8698,8699,8700,8701,8702,8703,8704,8705,8706,8707,8708,8709,8711,8712,8713,8714,8717,8718,8719,8720,8721,8722,8723,8724,8725,8726,8727,8728,8729,8730,
    8731,8732,8733,8734,8735,8736,8737,8738,8739,8740,8741,8742,8743,8744,8745,8746,8747,8748,8749,8750,8751,8752,8753,8754,8755,8756,8757,8758,8759,8760,8761,8762,8763,8764,8765,8766,8767,8768,8769,8770,8771,8772,8773,8774,8775,8776,8777,8778,8779,8780,8781,
    8602,8603,8604,8605,8606,8607,6633,6634,6635,6636,6637,6639,6640,6641,6642,6643,6644,6645,6646,6647,6648,6649,6650,6651,6652,6653,6654,6655,6656,6657,6658,6659,6660,6661,6662,6663,6664,6665,6666,6667,6668,6669,6670,6671,6672,6673,6313,6314,6315,6316,6317,5706,5707,5708,5709,5710,5053,5439,5440,5441,5442,5443,5444,5445,5446,5447,5448,5449,5450,
    5451,5452,5453,5454,5455,5456,5457,5458,5459,5460,5461,5462,5054,5055,5056,5057,5058,5059,5060,5061,5062,5064,5065,5066,5067,5068,5069,5070,5071,5072,5073,5074,5075,9899,9900,9901,9902,9903,9904,9905,9906,9907,9908,9909,9910,9911,9912,9913,9914,9915,9916,9917,9918,9919,9920,9921,9922,9923,9924,9925,9926,9927,9928,9929,9930,9931,9932,9933,9934,9935,9936,9937,9938,9939,9940,9941,9942,9943,9944,9945,
    5076,5077,5078,5079,5080,5081,5082,5083,5084,5085,5086,5087,5088,5089,5090,5091,5092,5093,5094,5095,5096,5097,5098,
    5099,5100,5101,5102,5103,5104,5105,5106,5107,5108,8661,8662,8663,8664,8665,8666,8667,8668,8669,8670,8671,8672,8673,8674,8675,8676,8677,8678,8679,8700,8701,8702,8703,8704,8705,8706,8707,8708,8709,
    8710,8711,8712,8713,8714,8715,8716,8717,8718,8719,8720,8721,8722,8723,8724,8725,8726,8727,8728,8729,8730,8731,8732,8733,8734,8735,8736,8737,8738,8739,8740,8741,8742,8743,8744,8745,8746,8747,8748,8749,8750,8751,8752,8753,8754,8755,8756,8757,8758,8759,
    8760,8761,8762,8763,8764,8765,8766,8767,8768,8769,8770,8771,8772,8773,8774,8775,8776,8777,8778,8779,8780,8781,1157,1158,1159,1160,1161,1162,1163,1164,1165,1166,1167,1168,1169,1170,1171,1172,1173,1174,1175,1176,1177,1178,1179,
    1180,1181,1182,1183,1184,1185,1186,1187,1188,1189,1190,1191,1192,1193,1194,1195,1196,1197,1198,1199,1200,1201,1202,
    1203,1204,1205,1206,1207,1208,1209,1210,1211,1212,1213,1214,1215,1216,1217,1218,1219,1220,1221,1222,1223,1224,1225,
    1226,1227,1228,1229,1230,1231,1232,1233,1234,1235,1236,1237,1238,1239,1240,1241,1242,1243,1244,1245,1246,1247,1248,
    1249,1250,1251,1252,1253,1254,1255,1256,1257,1258,1259,1260,1261,1262,1263,1264,1265,1266,1267,1268,1269,1270,1271,
    1272,1273,1274,1275,1276,1277,1278,1279,1280,1281,1282,1283,1284,1285,1286,1287,1288,880,881,882,883,884,885,886,887,888,889,890,891,892,893,894,896,
    897,898,899,900,901,902,903,904,905,906,907,909,910,911,912,913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,
    928,929,930,931,932,933,934,935,936,937,938,939,940,941,942,943,944,945,946,947,948,949,950,951,952,953,954,955,956,957,
    958,959,960,961,962,963,964,965,966,967,968,969,970,971,972,973,974,975,976,977,978,979,980,981,982,983,984,985,986,987,988,989,
    990,991,992,993,994,995,996,997,998,999,1000,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,3,126,233,53,91,293,195,261,167,
    252,276,129,263,5,233,181,190,66,299,78,370,256,327,60,344,303,244,353,149,197,204,198,192,257,182,158,308,68,286,333,
    92,201,262,55,74,54,188,215,258,306,341,44,255,117,164},/*创维(SKYWORTH)@48(+131 */
    {588, /* 7757前 */
    10830,10831,10832,10833,10834,
    10550,10551,10552,10553,10554,10492,7810,7811,7812,7772,7781,7800,7802,8906,7764,7794,7761,7769,7770,8908,7757,10208,10209,10210,10211,10212,10213,10214,10215,10216,10217,10218,10219,10220,10221,10222,10223,10224,10225,10226,10227,10228,10229,10230,10231,7758,7759,7760,7762,7763,7765,7766,7767,7768,7771,7773,7774,7775,7776,7777,7778,7779,7780,7782,7783,7784,7785,7786,7787,7788,7789,7790,7791,7792,7793,7795,7796,7797,7798,7799,7801,7803,7804,7805,7806,7807,7808,7809,7813,7814,7815,7816,7817,7818,7819,7820,7821,7822,7823,7824,
    6010,8871,8872,8873,8874,8875,8876,8877,8878,8879,8880,8881,8882,8883,8884,8885,8886,8887,8888,8889,8890,8891,8892,8893,8894,8895,8896,8897,8898,8899,8900,8901,8902,8903,8904,8905,8907,8906,8907,8908,8909,8910,8911,8912,8913,8914,8915,8916,8917,8918,8919,8920,8921,8922,8923,8924,8925,8926,8927,8928,8929,8930,8931,8932,8933,8934,8935,8936,8937,8938,
    8608,8609,8610,8611,8612,8613,6746,6747,6748,6749,6750,6751,6752,6753,6754,6755,6756,6757,6758,6759,6760,6761,6762,6763,6764,6765,6766,6767,6768,6769,6770,6771,6772,6773,6774,6775,6776,6777,6778,6779,6780,6781,6782,6783,6784,6785,6786,6011,6012,6013,5695,5696,5697,5698,4751,5503,5504,5505,5506,5507,5508,5509,5510,5511,5512,5513,5514,5515,5516,5517,5518,5519,5520,5521,5522,5523,5524,5525,5526,5527,5528,
    5529,5530,5531,5532,5533,4752,4753,4754,4755,4756,4757,4758,4759,4760,4761,4762,4763,4764,4765,4766,4767,4768,4769,4770,4771,
    4772,4773,4774,4775,4776,4777,4778,4779,4780,4781,4782,4783,4784,4785,4786,4787,4788,4789,4790,4791,4792,
    793,4794,4795,4796,4797,4798,4799,4800,4801,4802,4803,4804,4805,4806,4807,1289,1290,1291,1292,1293,1294,1295,
    1296,1297,1298,1299,1300,1301,1302,1303,1304,1305,1306,1307,1308,1309,1310,1311,1312,1313,1314,1315,1316,1317,
    1318,1319,1320,1321,1322,1323,1324,1325,1326,1327,1328,1329,1330,1331,1332,1333,1334,1335,1336,1337,1338,1339,
    1340,1341,1342,1343,1344,1345,1346,1347,1348,1349,1350,1351,1352,1353,1354,1355,1356,1357,1358,1359,1360,1361,
    1362,1363,1364,1365,1366,1367,1368,1369,1370,1371,1372,1373,1374,1375,1376,1377,1378,1379,1380,1381,1382,1383,
    1384,1385,1386,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,614,615,616,618,
    621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932,
    944,1001,1002,1022,1031,556,558,559,560,561,562,563,564,565,566,567,568,569,570,571,572,573,574,575,576,577,580,6,579,639,284,183,374,350,51,346,249,91,195,261,177,136,69,302,66,269,237,340,278,365,88,366,294,
    37,369,287,24,168,228,213,63,110,50,98,111,144,83,47,225,25,104,349,133,243,152,130,330,296,274,240,58,304,196,363,331,291,172,
    29,658,684,90,217,177,263,187,171,190,136,69,147,82,311,202,290,220,264,32,338,70,123,107,95,18,24,66,72,84,
     96,108,142,144,},/* 海信(Hisense)@48(+113 */
    {356, /* 7825,前 */
    10825,10826,10827,10828,10829,
    10795,10545,10546,10547,10548,10549,10515,7841,7843,7828,7830,10417,5499,7827,7833,7842,7844,7831,7834,7836,6724,6716,10177,6717,6719,10414,10424,6727,6715,8950,6718,7825,10168,10169,10170,10171,10172,10173,10174,10175,10176,10178,10179,10180,10181,10182,10183,10184,10185,10186,10187,10188,10189,10190,10191,10192,10193,10194,10195,10196,10197,10198,10199,10200,10201,10202,10203,10204,10205,10206,10207,7826,7829,7832,7835,7837,7838,7839,7840,7845,7846,7847,7848,7849,7850,10415,10416,10418,10419,10420,10421,10422,10423,10425,10426,10427,10428,10429,10430,10431,10432,10433,10434,10435,10436,10437,10438,10439,10440,
    5993,9265,9266,9267,9268,9269,8939,8940,8941,8942,8943,8944,8945,8946,8947,8948,8949,8951,8952,8953,8954,8955,8628,8629,8630,8631,8632,6720,6721,6722,6723,6725,6726,6728,6729,6730,6731,6732,6733,6734,6735,6736,6737,6738,6739,6740,6741,6742,6743,6744,6745,5994,5995,5996,1505,5484,5485,5486,5487,5488,5489,5490,5491,5492,5493,5494,5495,5496,5497,5498,5500,5501,
    5502,1506,1507,1508,1509,1510,1511,1512,1513,1514,1515,1516,1517,1518,1519,1520,1521,1522,1523,1524,1525,1526,1527,
    1528,9689,1529,1530,1531,1532,1533,1534,1535,1536,1537,1538,1539,1540,1541,1542,1543,1544,1545,1546,1547,1548,1549,1550,1551,
    1552,1553,1554,1555,1556,1557,1558,1559,1560,1561,1562,1563,1564,1565,1566,1567,1568,1569,1570,1571,1572,1573,1574,1575,1576,
    7,640,657,69,302,372,2,9,29,202,6,228,213,63,59,14,234,120,250,
    841,842,843,844,845,1038,1039,1040,1041,1042,1043,1001,1002,1022,1031,556,558,559,560,561,562,563,564,565,566,567,621,623,624,625,
    661,662,663,664,725,749,819,824,828,852,853,854,855,856,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,712,713,714,715,
    716,717,718,719,720,721,722,723,724,726,727,728,729,730,731},/* 海尔(Haier)@48(+39 */
    {325,/* 2150前插入25组 */
    7851,7852,7853,7854,7855,7856,7857,7858,7859,7860,7861,7862,7863,7864,7865,7866,7867,7868,7869,7870,7871,7872,7873,7874,7875,10244,10245,10246,10247,10248,10249,10250,10251,10252,10253,10254,10255,10256,10257,
    10258,10259,10260,10261,10262,10263,10264,10265,10266,10267,10268,10269,10270,10271,10272,10273,10274,10275,10276,10277,10278,10279,10280,10281,2150,9164,9165,9166,9167,9168,9169,9170,9171,9172,9173,9174,9175,9176,6787,6788,6789,2151,2152,2153,2154,2155,2156,2157,2158,2159,2160,2161,2162,2163,2164,2165,2166,2167,2168,2169,2170,2171,2172,2173,2174,2175,
    2176,2177,2178,2179,2180,2181,2182,2183,2184,2185,2186,2187,2188,2189,2190,2191,2192,2193,2194,2195,2196,2197,2198,2199,2200,2201,
    2202,2203,2204,2205,2206,2207,2208,2209,2210,2211,2212,2213,2214,2215,2216,2217,2218,2219,2220,2221,2222,2223,2224,2225,2226,2227,
    2228,2229,2230,2231,2232,2233,2234,2235,2236,2237,2238,2239,2240,2241,2242,2243,2244,2245,2246,2247,2248,2249,2250,2251,2252,2253,
    2254,4910,5194,5011,195,8,473,506,374,350,53,108,174,91,106,293,96,17,177,103,276,263,233,65,171,136,66,366,144,28,
    581,582,583,584,585, 586,587,588,589,590,591,592,593,594,594,595,596,597,598,599,600,601,602,603,604,605,606,607,
    608,609,610,611,612,613,619,620,622,626,627,628,629,630,631,632,633,634,635,636,637,641,642,643,644,645,646,647,
    648,649,650, 651,652,653,654,655,656,659,659,660,661,662,663,664,665,666,667,668,669,670,671,672,673,674,675,676,
    677,678,679,680,681,682,683,685,685,686,687,688,689,690,691,692,693,694,695,696,697,698,699,700,701,702,703,704},/* 金星(JINXING)@48(+112    */
    {396,/* 7040前插入37组 */
    7876,7877,7878,7879,7880,7881,7882,7883,7884,7885,7886,7887,7888,7889,7890,7891,7892,7893,7894,7895,7896,7897,7898,7899,7900,7901,7902,7903,7904,7905,7906,7907,7908,7909,7910,7911,7912,
    7040,7041,7042,7043,7044,7045,7046,7047,7048,7049,7050,7051,7052,7053,7054,7055,7056,7057,7058,7059,7060,6226,9243,9244,9245,9246,9247,9248,9249,9250,9251,9252,9253,9254,9255,9256,6227,1924,5680,5681,5682,5683,5684,5685,5686,5687,5688,5689,5690,5691,5692,5693,5694,1925,1926,1927,1928,1929,1930,1931,1932,1933,1934,1935,1936,1937,1938,1939,1940,1941,1942,
    1943,1944,1945,1946,1947,1948,1949,1950,1951,1952,
    1953,1954,1955,1956,1957,1958,1959,1960,1961,1962,1963,1964,1965,1966,1967,1968,1969,1970,1971,1972,1973,1974,1975,1976,1977,1978,1979,1980,1981,
    1982,1983,1984,1985,1986,1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,
    2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035,2036,2037,2038,2039,
    2040,2041,2042,2043,2044,2045,2046,2047,2048,2049,2050,2051,2052,2053,2054,2055,2056,2057,2058,2059,2060,2061,9,418,444,142,53,109,155,166,114,174,
    91,35,73,293,102,300,134,335,57,177,45,17,241,263,233,322,65,179,223,127,353,135,157,156,48,251,128,190,880,881,882,883,884,885,886,887,888,889,890,
    891,892,893,894,896,897,898,899,900,901,902,903,904,905,906,907,909,910,911,912,913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,928,929,930,
    931,932,933,934,935,936,937,938,939,940,941,942,943,944,945,946,947,948,949,950,951,952,953,954,955,956,957,958,959,960,961,962,963,964,965,966,967,968,
    969,970,971,972,973,974,975,976,977,978,979,980,981,982,983,984,985,986,987,988,989,990,991,992,993,994,995,996,997,998,999,1000,1003,1004,1005,1006,
    1007,1008,1009,1010,1011,1012,1013,1014},/* 熊猫(PANDA)@48(+131 */
    {232,/* 7913前 */
    10845,10846,10847,10848,10849,10850,
    10565,10566,10567,10568,10569,10773,10774,10775,10776,10777,10570,10493,10494,10495,10496,10497,7914,7915,7916,7919,7920,7921,7922,7923,7924,7925,7926,7927,7929,7930,7931,7932,7933,7934,7935,7936,7937,7938,7939,7940,7941,7942,7943,7944,7945,7946,7947,7948,7949,7950,
    7951,9279,9060,9062,9063,9064,9065,9066,9067,9068,9069,6969,6322,5712,5713,2062,5599,5600,5601,5602,5603,5604,
    5605,2063,2064,2067,2068,2069,2070,2072,2073,2074,2075,2077,2079,2083,2085,2086,2087,6960,9061,6962,6963,
    6964,6965,6967,6970,6972,6321,5606,2078,2080,2081,2088,2089,2090,2091,2092,2094,2096,2097,2098,2100,2101,2102,2105,2106,2107,10,210,234,6961,7913,10289,10290,10291,10292,10293,10294,10295,7917,7918,7928,
    8633,6966,6968,6971,5711,5598,2065,2066,2071,2076,2082,2084,2093,2095,2099,2103,2104,453,472,248,351,94,46,82,266,100,280,
     71,64,1073,1074,1075,1076,1077,1078,1079,1080,1081,1082,96,108,142,144,155,191,192,198,216,222,228,246,282,294,366,430,431,546,553,
     554,555,557,614,615,616,618,621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932
    },/* 索尼(sony)@48(+56 */
    {231,/*7954前*/
     10784,10785,10786,10504,10505,10506,7952,7953,9041,7957,7959,9042,9048,9050,9055,8645,6941,6949,1577,5588,5589,5590,5591,5593,5594,5596,5597,1581,1584,1585,1591,1593,1596,1597,1598,1600,1603,
     1609,1611,1622,142,346,795,796,797,798,799,800,801,805,10308,10309,10310,10311,10312,10313,10314,10315,10316,10317,10318,7954,7955,7956,7958,7960,7961,7962,9043,9044,9045,9046,9047,9049,9051,9052,9053,9054,8646,8647,8648,8649,8650,8651,6942,6943,6944,6945,6946,6947,6948,6950,6951,6952,
     6953,6954,6955,6956,6957,6958,6959,6225,5587,5592,5595,1578,1579,1580,1582,1583,1586,1587,1588,1589,1590,1592,
     1594,1595,1599,1601,1602,
     1604,1605,1606,1607,1608,1610,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1623,1624,1625,1626,1627,1628,
     1629,1630,1631,1632,11,399,417,41,10,249,155,166,23,802,803,804,
     806,807,808,809,810,811,812,813,814,815,816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,
     843,844,845,846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879},/* 松下(Panasonic)@481(+80*/
    {160,/*1577前插入11组*/
     7952,7953,7954,7955,7956,7957,7958,7959,7960,7961,7962,1577,1578,1579,1580,1581,1582,1583,1584,1585,1586,1587,1588,1589,1590,1591,1592,1593,1594,1595,1596,1597,1598,1599,1600,1601,1602,
     1603,1604,1605,1606,1607,1608,1609,1610,1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621,1622,1623,1624,1625,1626,1627,1628,
     1629,1630,1631,1632,11,399,417,41,142,10,346,249,155,166,23,34,789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,
     806,807,808,809,810,811,812,813,814,815,816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,
     843,844,845,846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879},/*乐声(Panasonic)@48(+80 */
    {344,/* 7994前 */
     10804,10805,10806,10807,10808,10809,
     10524,10525,10526,10527,10528,10529,7992,7993,6440,6689,10049,10051,6676,6425,10337,7996,7997,6432,10353,7994,10319,10320,10321,10322,10323,10324,10325,10326,10327,10328,10329,10330,10331,10332,10333,10334,10335,10336,10338,10339,10340,10341,10342,10343,10344,10345,10346,10347,10348,10349,10350,10351,
     10352,10354,10355,10356,10357,10358,10359,10360,10361,10362,10363,10364,10365,10366,10367,10368,7995,7998,7999,8000,8001,8002,8003,8004,8005,8006,8007,8008,8009,8010,8011,8012,8013,6424,9070,9071,9072,9073,9074,9075,9076,9077,9078,9079,9080,9081,9082,9083,9084,9085,9086,9087,9088,9089,9090,
     8653,8654,8655,8656,6674,6675,6677,6678,6679,6680,6681,6682,6683,6684,6685,6686,6687,6688,6690,6691,6692,6426,6427,6428,6429,6430,6431,6433,6434,6435,6436,6437,6438,6439,6441,6442,2620,5463,5464,5465,5466,5467,5468,5469,5470,5471,5472,5473,5474,5475,2621,2622,2623,2624,2625,2626,2627,2628,2629,2630,2631,2632,2633,2634,2635,2636,2637,2638,2639,2640,2641,2642,2643,2644,2645,2646,2647,
     2648,2649,2650,2651,2652,2653,2654,2655,2656,2657,2658,2659,2660,2661,2662,2663,2664,2665,2666,2667,2668,2669,2670,2671,2672,2673,2674,2675,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10050,10052,10053,10054,10055,10056,10057,
     2676,2677,2678,2679,2680,2681,2682,2683,2684,0,14,18,284,346,109,295,293,134,335,374,350,249,73,226,322,11,65,233,358,352,371,310,96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,614,615,616,618,
     621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932,750,751,752,753,754,755,756,757
    },/* 东芝(TOSHIBA)@48(+64 */
    {220,/* 7965前*/
    7963,6867,6886,6015,7964,7965,7966,7967,7968,7969,7970,7971,7972,7973,7974,7975,7976,9212,9213,9214,9215,9216,9217,9218,9219,9220,9221,9222,8657,8658,8659,8660,6868,6869,6870,6871,6872,6873,6874,6875,6876,6877,6878,6879,6880,6881,6882,6883,6884,6885,6887,6888,6889,6890,6014,6016,6017,1676,5568,5569,5570,1677,1678,1679,1680,1681,1682,1683,1684,1685,1686,1687,1688,1689,
    1690,1691,1692,1693,1694,1695,1696,1697,1698,1699,1700,1701,1702,1703,
    1704,1705,1706,1707,1708,1709,1710,1711,1712,1713,1714,1715,1716,1717,1718,1719,1720,1721,1722,1723,1724,1725,1726,1727,1728,1729,1730,1731,
    1732,1733,1734,1735,1736,1737,1738,1739,1740,1741,1742,1743,12,382,398,374,249,346,295,284,183,350, 51,351,362,321,
    841,842,843,844,845,1038,1039,1040,1041,1042,1043,1001,1002,1022,1031,556,558,559,560,561,562,563,564,565,566,567,621,623,624,625,
    661,662,663,664,725,749,819,824,828,852,853,854,855,856,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,712,713,714,715,
    716,717,718,719,720,721,722,723,724,726,727,728,729,730,731
    },/* 日立(HITACHI)@48(+39 */
    {195,/*7979前*/
    10856,10857,10858,10859,10787,
    10576,10577,10578,10579,10507,7977,7978,5674,10375,7673,7034,7979,7980,7981,7982,7983,7984,7985,7986,7030,8634,8635,8636,8637,7031,7032,7033,7035,7036,6306,9232,9233,9234,9235,9236,9237,9238,9239,9240,9241,9242,
    6307,5704,5705,1633,5666,5667,5668,5669,5670,5671,5672,5675,5676,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643,1644,1645,1646,1647,1648,1649,1650,1651,1652,1653,1654,1655,1656,1657,1658,
    1659,1660,1661,1662,1663,1664,1665,1666,1667,1668,1669,1670,1671,1672,1673,1674,1675,13,445,452,373,238,109,91,178,307,191,10369,10370,10371,10372,10373,10374,
    841,842,843,844,845,1038,1039,1040,1041,1042,1043,1001,1002,1022,1031,556,558,559,560,561,562,563,564,565,566,567,621,623,624,625,
    661,662,663,664,725,749,819,824,828,852,853,854,855,856,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,712,713,714,715,
    716,717,718,719,720,721,722,723,724,726,727,728,729,730,731
    },/*夏普(SHARP)@48(+39 */
    {147,/*6296前插入10组*/
    7977,7978,7979,7980,7981,7982,7983,7984,7985,7986,6296,6297,6298,6299,6300,6301,1633,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643,1644,1645,1646,1647,1648,1649,1650,1651,1652,1653,1654,1655,1656,1657,1658,
    1659,1660,1661,1662,1663,1664,1665,1666,1667,1668,1669,1670,1671,1672,1673,1674,1675,13,445,452,373,238,109,91,178,307,191,
    841,842,843,844,845,1038,1039,1040,1041,1042,1043,1001,1002,1022,1031,556,558,559,560,561,562,563,564,565,566,567,621,623,624,625,
    661,662,663,664,725,749,819,824,828,852,853,854,855,856,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,712,713,714,715,
    716,717,718,719,720,721,722,723,724,726,727,728,729,730,731
    },/* 声宝(Sampo)@48(+39 */
    {234,/* 8018前 */
    10820,10821,10822,10823,10824,
    10540,10541,10542,10543,10544,8014,8015,8016,8017,8022,8024,9135,9142,1744,10064,10067,8018,8019,8020,8021,8023,6231,9136,9137,9138,9139,9140,9141,
    8652,6694,6695,6696,6697,6698,6699,6700,6701,6702,6703,6704,6705,6706,6707,6708,6709,6710,6711,6712,6713,6714,6232,6233,6234,6235,
    6236,6237,6238,6239,1745,1746,1747,1748,1749,1750,1751,1752,1753,1754,1755,1756,1757,1758,1759,1760,1761,1762,1763,1764,1765,1766,1767,1768,1769,
    1770,1771,10058,10059,10060,10061,10062,10063,10065,10066,10068,10069,10070,
    10071,10072,10073,10074,10075,10076,10077,10078,10079,10080,10081,1772,1773,1774,1775,1776,1777,1778,1779,1780,1781,1782,1783,1784,1785,1786,1787,1788,1789,1790,1791,1792,1793,1794,1795,1796,10376,
    10377,10378,10379,5476,5477,5478,234,245,108,114,174,96,102,300,246,210,216,
    10,453,472,248,351,94,46,82,266,100,280,71,64,1073,1074,1075,1076,1077,1078,1079,1080,1081,1082,
    96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,614,615,616,618,
     621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932,
    },/* 飞利浦(Philips)@48(+79*/
    {304,/* 插入20组 8025前*/
    10840,10841,10842,10843,10844,
    10560,10561,10562,10563,10564,10772,8043,8035,8036,8642,5700,5571,2374,10285,10286,2415,9019,10283,6304,5702,8030,8034,9013,6892,6898,
    6901,6902,2376,2380,2381,2382,2391,2398,2402,2403,2404,2413,2414,2419,2438,2439,2440,553,174,96,102,300,601,619,620,622,660,661,662,663,664,680,
    681,682,683,685,685,686,687,688,689,690,8041,6907,2387,2409,2428,2452,67,2420,2446,8025,8026,
    8027,8028,8029,8031,8032,8033,8038,8039,8040,8044,9004,9011,9012,9014,9015,9017,9018,8640,8641,8643,8644,6893,6894,6895,6896,6897,6899,6900,6903,6904,6905,6906,6908,6909,6302,6303,6305,5699,5701,5703,2372,5572,5573,5574,5575,2373,
    2375,2377,2378,2379,2383,2384,2385,2386,2388,2389,2390,2392,
    2393,2394,2395,2396,2397,2399,2400,2401,2405,2406,2407,2408,2410,2411,2412,10284,10287,10288,
    2416,2417,2418,2421,2422,2423,2424,2425,2426,2427,2429,2430,2431,2432,2433,2434,2435,2436,
    2437,2441,2442,2443,2444,2445,2447,2448,2449,2450,2451,2453,16,530,350, 53,109,155,
     91,293,185,355,17,65,332,581,582,583,584,585,586,587,588,589,590,591,592,593,594,594,595,596,
    597,598,599,600,602,603,604,605,606,607,608,609,610,611,612,613,626,627,628,629,630,631,632,633,
    634,635,636,637,641,642,643,644,645,646,647,648,649,650, 651,652,653,654,655,656,659,659,665,
    666,667,668,669,670,671,672,673,674,675,676,677,678,679,691,692,693,
    694,695,696,697,698,699,700,701,702,703,704},/* 三星(SAMSUNG)@481(+112 */
    {213,/* 6910前插入19组 */
    8058,8059,8060,8061,8062,8063,8064,8065,8066,8067,8068,8069,8070,8071,8072,8073,8074,8075,8076,6910,9020,9021,9022,9023,9024,9025,9026,9027,9028,9029,6911,6912,6913,6914,6915,6916,6917,6918,6919,6920,6921,6922,6923,6924,6925,6926,6927,6928,6929,6930,6931,6932,6933,6934,2454,5576,5577,5578,5579,5580,5581,2455,2456,2457,2458,2459,2460,2461,2462,2463,2464,2465,2466,2467,2468,2469,2470,2471,2472,2473,2474,2475,
    2476,2477,2478,2479,2480,2481,2482,2483,2484,2485,2486,2487,2488,2489,2490,2491,2492,2493,2494,2495,2496,2497,10380,10381,10382,10383,
    2498,2499,2500,2501,2502,2503,2504,2505,2506,2507,2508,2509,2510,2511,2512,2513,2514,2515,2516,2517,2518,2519,
    2520,2521,2522,2523,2524,2525,2526,2527,17,53,69,350,284,374,346,249,293,189,134,335,233,302,141,273,125,315,56,
    96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,614,615,616,618,
     621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932
    },/* 三洋(SANYO)@48(+56 */
    {140,/*前插入13组*/
    8077,8078,8079,8080,8081,8082,8083,8084,8085,8086,8087,8088,8089,183,9204,9205,9206,9207,9208,9209,9210,9211,53,109,309, 91,293,134,335,174,233,2528,2529,2530,2531,2532,2533,2534,2535,2536,2537,2538,2539,2540,2541,2542,2543,2544,2545,
    2546,2547,2548,2549,2550,2551,2552,2553,2554,2555,2556,2557,2558,841,842,843,844,845,1038,1039,1040,1041,1042,1043,1001,1002,1022,
    1031,556,558,559,560,561,562,563,564,565,566,567,621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,921,922,923,
    924,925,926,927,928,929,930,931,932,933,934,935,712,713,714,715,716,717,718,719,720,721,722,723,724,726,727,728,729,730,731
    },/* 日电(Xinhua)@48(+39 */
    {184,/*前插入16组*/
    8090,8091,8092,8093,8094,8095,8096,8097,8098,8099,8100,8101,8102,8103,8104,8105,2559,9225,9226,9227,9228,9229,9230,9231,
    2560,2561,2562,2563,2564,2565,2566,2567,2568,2569,2570,2571,2572,2573,2574,2575,2576,2577,2578,2579,2580,2581,2582,2583,
    2584,2585,2586,2587,2588,2589,2590,2591,2592,2593,2594,2595,2596,2597,2598,2599,2600,2601,2602,2603,2604,2605,2606,2607,2608,
    2609,2610,2611,2612,2613,2614,2615,2616,2617,2618,2619,19,507,529,53,114,174,293,227,102,300,134,233,354,88,157,209,203,367,113,
    789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,
    816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879
    },/* 西湖(XIHU)@481(+80 */
    {286,/*2255前插入33组*/
    8110,8111,8112,8113,8114,8115,8116,8117,8118,8119,8120,8121,8122,8123,8124,8125,8126,8127,8128,8129,8130,8131,8132,8133,8134,8135,8136,8137,8138,8139,8140,8141,8142,
    2255,9091,9092,9093,9094,9095,9096,9097,9098,9099,9100,9101,9102,9103,9104,9105,9106,9107,9108,9109,9110,2256,2257,2258,2259,2260,2261,2262,2263,2264,2265,2266,2267,2268,2269,2270,2271,2272,2273,2274,2275,2276,
    2277,9791,9792,9793,9794,9795,9796,9797,9798,9799,9800,9801,9802,9803,9804,9805,9806,9807,9808,9809,9810,9811,9812,9813,9814,9815,9816,9817,9818,2278,2279,2280,2281,2282,2283,2284,2285,2286,2287,2288,2289,2290,2291,2292,2293,2294,2295,2296,2297,2298,
    2299,2300,2301,2302,2303,2304,2305,2306,2307,2308,2309,2310,2311,2312,2313,2314,2315,2316,2317,2318,2319,2320,
    2321,2322,2323,2324,2325,2326,2327,2328,2329,2330,2331,2332,2333,2334,2335,2336,2337,2338,2339,2340,2341,2342,
    2343,2344,2345,2346,2347,2348,2349,2350,2351,2352,2353,2354,2355,2356,2357,2358,2359,2360,2361,2362,2363,2364,
    2365,2366,2367,2368,2369,2370,2371,350,49,53,43,109,114,174,91,295,73,205,226,85,293,102,300,355,134,335,35,233,
    65,96,31,17,155,1,163,83,47,8,118, 96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,
    554,555,557,614,615,616,618,621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,
    895,908,929,930,931,932},/* 北京(BEIJING)@48(+56 */
    {214,/*21前插入19组*/
    8143,8144,8145,8146,8147,8148,8149,8150,8151,8152,8153,8154,8155,8156,8157,8158,8159,8160,8161,21,9487,9488,9489,9490,9491,9492,9493,9494,9495,9496,9497,9498,9499,9500,9501,9502,9503,
    2787,2788,2789,2790,2791,2792,2793,2794,2795,2796,2797,2798,2799,2800,2801,2802,2803,2804,2805,2806,2807,2808,
    2809,2810,2811,2812,2813,2814,2815,2816,2817,2818,2819,2820,2821,2822,2823,2824,2825,2826,2827,2828,2829,2830,2831,2832,2833,
    2834,2835,2836,2837,2838,2839,2840,2841,2842,2843,2844,2845,2846,2847,2848,2849,2850,2851,2852,2853,2854,2855,2856,2857,2858,
    2859,2860,2861,554,578,284,183,374,350,51,109,91,293,134,359,335,195,261,224,263,153,88,157,116,
    789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,
    816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879
    },/* 高路华(CONROWA)@482(+80 */
    {180,/* 6290前插入13组 */
    8162,8163,8164,8165,8166,8167,8168,8169,8170,8171,8172,8173,8174,6290,9177,9178,9179,9180,9181,6830,6831,6832,6833,6834,6835,6836,6837,6838,6839,6840,6841,6842,2706,5558,5559,2707,2708,2709,2710,2711,2712,2713,2714,2715,2716,2717,2718,2719,2720,2721,2722,2723,2724,2725,2726,2727,2728,2729,2730,2731,
    2732,2733,2734,2735,2736,2737,2738,2739,2740,2741,2742,2743,2744,2745,2746,2747,2748,2749,2750,2751,2752,2753,2754,2755,2756,2757,
    22,554,578,53,108,109,114,174,91,102,300,165,265, 30,222,271,
    841,842,843,844,845,1038,1039,1040,1041,1042,1043,1001,1002,1022,1031,556,558,559,560,561,562,563,564,565,566,567,621,623,624,625,
    661,662,663,664,725,749,819,824,828,852,853,854,855,856,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,712,713,714,715,
    716,717,718,719,720,721,722,723,724,726,727,728,729,730,731
     },/*  乐华(ROWA)@48(+39 */
    {98,
    109,91,293,134,335,187,233,190,69,3181,3182,3183,3184,3185,3186,3187,3188,789,790,791,792,793,794,795,796,797,
    798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,
    816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879
    },/* 东宝TCBO_东凌TOS@482(+80 */
    {127,
    8459,8460,8461,8462,8463,8464,8465,8466,8467,8468,8469,8470,8471,8472,8473,8474,8475,374,53,9632,9633,9634,9635,9636,9637,9638,9639,9640,9641,2877,2878,2879,2880,2881,2882,2883,2884,2885,2886,2887,2888,2889,2890,2891,2892,2893,2894,2895,2896,2897,
    2898,2899,2900,2901,2902,2903,249,114,174,73,293,131,102,300,134,335,167,179,103,233,136,
    96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,614,615,616,618,
     621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932
    },/* 福日(Furi)@58(+56 */
    {160,/* 8057前*/
    10778,10779,10780,10781,10782,10783,10498,10499,10500,10501,10502,10503,8053,8054,8055,4288,9037,9038,9040,8638,6843,5560,5561,5562,5563,5564,5565,5567,4289,4291,4296,4301,4302,4303,4304,4305,4307,325,364,345,10304,10306,8049,8050,8051,8052,8056,8639,6844,6846,6848,5566,4294,4308,
    9039,4290,4292,4293,4297,4298,4299,4300,4306,4310,372,174,102,354,300,36,1001,1002,1022,1031,556,621,623,624,625,661,662,663,664,725,819,853,854,855,856,927,928,929,930,931,932,933,934,935,718,719,720,721,722,
    723,724,726,727,728,729,730,731,8057,6845,6847,6849,6850,4295,4309,25,381,10303,10305,10307,
    841,842,843,844,845,1038,1039,1040,1041,1042,1043,558,559,560,561,562,563,564,565,566,567,
    749,824,828,852,921,922,923,924,925,926,712,713,714,715,
    716,717,
    },/*LG(LG)@58(+39 */
    {99,
    109,91,49,43,35,295,73,205,226,31,85,9337,9338,2995,2996,2997,2998,2999,3000,3001,3002,3003,3004,3005,3006,3007,3008,3009,
    3010,3011,3012,3013,3014,3015,3016,3017,3018,3019,3020,3021,3022,3023,3024,
    96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,614,615,616,618,
    621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932
    },/* 百乐(Tupper)@582(+56 */
    {286,/*8488前*/
     8496,9125,8491,8494,8485,8497,9122,8486,8487,8498,8500,8499,8501,8502,8503,8504,8505,8495,8488,8489,8490,8492,8493,8506,5986,9111,9112,9113,9114,9115,9116,9117,9118,9119,9120,9121,9123,9124,9126,
     6615,9431,9432,9433,9434,9435,9436,9437,9438,9439,9440,9441,9442,9443,9444,9445,9446,3264,3265,3266,3267,3268,3269,3270,3271,3272,3273,3274,3275,3276,3277,3278,3279,3280,3281,3282,3283,3284,3285,
     3286,3287,9819,9820,9821,9822,9823,9824,9825,9826,9827,9828,9829,9830,9831,9832,9833,9834,9835,9836,9837,9838,9839,9840,9841,9842,9843,9844,9845,9846,9847,9848,9849,9850,9851,9852,9853,9854,9855,
     9856,9857,9858,9859,9860,9861,9862,9863,3288,3289,3290,3291,3292,3293,3294,3295,3296,3297,3298,3299,3300,3301,3302,3303,3304,3305,3306,3307,
     3308,3309,3310,3311,3312,3313,3314,3315,3316,3317,3318,3319,3320,3321,3322,3323,3324,3325,3326,3327,3328,3329,
     3330,3331,3332,3333,3334,3335,3336,3337,3338,3339,3340,3341,3342,3343,3344,3345,53,109,99,114,174,91,293,102,
     300,134,335,142,49,43,295,35,73,205,226,85,31,233,
     789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,
    816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879
     },/* 长城(CHANGCHENG)@58(+80 */
    {122,
     8513,8514,8515,8516,8517,8518,8519,8520,8521,8522,8523,2912,9150,9151,9152,9153,9154,2913,2914,2915,2916,2917,2918,2919,2920,2921,2922,2923,2924,2925,2926,2927,2928,2929,2930,2931,2932,2933,
     2934,2935,2936,2937,2938,2939,2940,2941,4825,4826,4827,4828,4829,4830,4831,4832,4833,4834,4835,932,929,908,895,882,877,866,865,856,855,854,853,852,828,824,819,749,725,664,663,662,661,625,624,
     623,621,618,616,615,614,557,555,554,553,546,430,431,300,366,210,216,222,228,234,246,282,294,191,192,198,102,108,109,114,127,174,142,144,155,96,91,53,69,17,13
     },/* 黄河(Yellow River)@58(+54   */
    {111,
    3541,3542,3543,3544,3545,3546,3547,3548,3549,3550,3551,3552,3553,3554,3555,3556,3557,3558,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,
    816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,789,790,791,792,793,794,795,796,797,798,799,335,233,300,293,106,102,134,109,114,174,91,53
     },/* 黄山(Huangshan)@58(+80*/



    {160,
    8551,4371,4372,4373,4374,4375,4376,4377,4378,4379,4380,4381,1072,1019,1020,1021,1023,1024,1029,1025,1001,1002,1022,1031,908,929,930,931,932,944,831,832,833,819,824,828,852,853,854,855,856,865,866,877,882,895,725,749,
    614,615,616,618,621,623,624,625,661,662,663,664,658,684,546,553,554,555,557,556,558,559,560,561,562,563,564,565,566,567,568,569,570,571,572,573,574,575,576,577,580,430,431,363,331,366,330,349,304,338,291,210,216,222,228,234,246,282,294,217,263,243,225,296,274,240,202,290,220,264,
    111,144,104,133,152,130,196,172,177,187,171,190,136,147,311,123,107,108,142,144,155,191,192,198,98,95,18,24,66,72,84,96,29,70,82,69,83,47,25,90,58,32,
    },/* 三垦(sanken)@58(+113 */
    {149,
    8552,8553,8554,4353,4354,4355,4356,4357,4358,4359,4360,4361,4362,4363,1083,1069,1070,1071,986,9741,9742,9743,9744,9745,9746,9747,9748,9749,9750,9751,9752,9753,9754,9755,9756,9757,9758,9759,9760,9761,9762,9763,9764,9765,9766,9767,9768,9769,9770,9771,9772,9773,9774,9775,9776,9777,9778,9779,9780,9781,9782,9783,987,988,989,992,993,994,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,
    816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879,789,790,791,792,793,794,795,796,797,798,799,
    },      /* 宝立创(Polytron)@58(+80 */
    {90,
    9703,9704,9705,9706,9707,8382,8383,8384,8385,6514,6515,5732,5731,4493,4494,4495,4496,4497,4498,1066,1067,1068,1043,1044,1045,988,989,992,908,929,930,931,932,819,824,828,852,853,854,855,856,865,866,877,882,895,770,1071,790,791,792,725,749,
    614,615,616,618,621,623,624,625,661,662,663,664,546,553,554,555,557,430,431,366,210,216,222,228,234,246,282,294,108,142,144,155,191,192,198,96,30
    },      /* 爱华(Aiwa)@58(+56      */
    {29,/*6498前插入5组*/
    9203,8045,8046,8047,8048,6498,6420,6421,5194,5011,5195,1387,1388,1389,1390,1391,1392,1393,1394,1395,1396,1397,1398,1399,1400,1401,1402,1403,1404}, /* 清华同方(Tongfang)@58 */
      /* ========================2013-8-22 19:09加================================================================= */
     {21,/*6185前插入5组*/
     7987,7988,7989,7990,7991,6185,6186,6187,6188,1800,1801,1802,1803,1804,1805,1797,1798,1799,}, /* 三菱(SANLING)@58 */
     {35,/*5899前插入4组*/
     9516,9517,9518,9519,9520,9521,9522,9523,9524,9525,9526,9527,9528,9529,9530,9970,9971,9972,8106,8107,8108,8109,5899,5900,5901,5902,1806,1807,1808,1809,1810,1811,1812,1813,1814}, /* 大宇(Daewoo)@58   */
     {146,/*前插入27组*/
     9191,9192,9193,9194,9195,9196,9197,9198,9199,9200,9201,8175,8176,8177,8178,8179,8180,8182,8183,8184,8185,8186,8187,8188,8189,8190,8191,8192,8193,8194,8195,8196,8197,8198,8199,8200,8201,1900,1901,1902,1903,1904,1905,1906,1907,1908,1909,1910,1911,1912,1913,1914,1915,1916,1917,1918,1919,1920,1921,1922,1923,
     1815,1816,1817,1818,1819,1820,1821,1822,1823,1824,1825,1826,1827,1828,1829,1830,1831,1832,1833,1834,1835,1836,1837,1838,1839,1840,1841,1842,
     1843,1844,1845,1846,1847,1848,1849,1850,1851,1852,1853,1854,1855,1856,1857,1858,1859,1860,1861,1862,1863,1864,1865,1866,1867,1868,1869,1870,
     1871,1872,1873,1874,1875,1876,1877,1878,1879,1880,1881,1882,1883,1884,1885,1886,1887,1888,1889,1890,1891,1892,1893,1894,1895,
     1896,1897,1898,1899,}, /* 牡丹(MUDAN)@58 */
      {79,/*2108前插入37组*/
    7720,7721,7722,7723,7724,7725,7726,7727,7728,7729,7730,7731,7732,7733,7734,7735,7736,7737,7738,7739,7740,7741,7742,7743,7744,7745,7746,7747,7748,7749,7750,7751,7752,7753,7754,7755,7756,
    2108,2109,2110,2111,2112,2113,2114,2115,2116,2117,2118,2119,2120,2121,2122,2123,2124,2125,2126,2127,2128,2129,2130,2131,
    2132,2133,2134,2135,2136,2137,2138,2139,2140,2141,2142,2143,2144,2145,2146,2147,2148,2149}, /* 夏华(Xiahua)@58 */
     {29,
     2758,2759,2760,2761,2762,2763,2764,2765,2766,2767,2768,2769,2770,2771,2772,2773,2774,2775,2776,2777,2778,2779,2780,2781,2782,2783,2784,2785,2786}, /* 泰山(Taishan)@58 */
     {15,
     2862,2863,2864,2865,2866,2867,2868,2869,2870,2871,2872,2873,2874,2875,2876}, /* 雪莲(XUELIAN)@58 */
     {22,
     2942,2943,2944,2945,2946,2947,2948,2949,2950,2951,2952,2953,2954,2955,2956,2957,2958,2959,2960,2961,2962,2963}, /* 红岩(HONGYAN)@58 */
     {16,
     2964,2965,2966,2967,2968,2969,2970,2971,2972,2973,2974,2975,2976,2977,2978,2979}, /* 奥林匹亚(AOLINPIKE)@58 */
     {15,
     2980,2981,2982,2983,2984,2985,2986,2987,2988,2989,2990,2991,2992,2993,2994}, /*DETRON(DETRON)@58 */
     {22,
    8480,8481,8482,8483,8484,3025,3026,3027,3028,3029,3030,3031,3032,3033,9413,3034,3035,3036,3037,3038,3039,3040}, /* 彩星(Playmates)@58 */
     {37,
     8350,8351,8352,8353,8354,8355,8356,8357,8358,3041,3042,3043,3044,3045,3046,3047,3048,3049,3050,3051,3052,3053,3054,3055,3056,3057,3058,3059,3060,3061,3062,3063,3064,3065,3066,3067,3068}, /* 南宝(South Po)@58 */
     {17,
     3069,3070,3071,3072,3073,3074,3075,3076,3077,3078,3079,3080,3081,3082,3083,3084,3085}, /* 飞燕(FEIYAN)@58  */
     {22,
     3117,3118,3119,3120,3121,9340,9341,9342,9343,3122,3123,3124,3125,3126,9730,9731,9732,9733,9734,3127,3128,3129}, /* 宝花石(Treasure flower stone)@58 */
      {14,
     3130,3131,3132,3133,3134,3135,3136,3137,3138,3139,3140,3141,3142,3143}, /* 豁达(Minded)@58   */
     {28,
     8445,3144,3145,3146,3147,3148,3149,3150,3151,3152,3153,3154,3155,3156,3157,3158,3159,3160,3161,9655,10111,10112,10113,10114,10115,10116,10117,10118}, /* 港泰(GANGTAI)@58 */
     {12,
     3162,6613,6614,3163,9409,9410,3164,3165,3166,3167,3168,3169}, /* 彩虹(CAIHONG)@58 */
     {15,
    3170,3171,3172,3173,3174,9784,9785,9786,9787,3175,3176,3177,3178,3179,3180}, /* 宝声(Po Sing)@58 */
     {85,
    8455,8296,8297,8298,8299,8300,8301,8302,8303,8304,3189,3190,3191,3192,3193,3194,3195,3196,3197,3198,3199,3200,3201,3202,3203,3204,3205,3206,
    3207,3208,3209,3210,3211,3212,3213,3214,3215,3216,3217,3218,3219,3220,3221,3222,3223,3224,
    3225,3226,3227,3228,3229,3230,3231,3232,3233,3234,3235,3236,3237,3238,3239,3240,3241,3242,
    3243,3244,3245,3246,3247,3248,3249,3250,3251,3252,3253,3254,3255,3256,3257,3258,3259,3260,3261,3262,3263}, /* 东凌(TOS)@58 */
     {19,
     6208,3346,3347,3348,9708,9709,9710,9711,3349,3350,3351,3352,3353,3354,3355,3356,3357,3358,3359}, /* 安华(On Wah)@58 */
     {23,
     9336,3360,3361,3362,3363,3364,3365,3366,3367,3368,3369,3370,3371,3372,3373,3374,3375,3376,3377,3378,3379,3380,3381}, /* 百花(BAIHUA)@58 */
     {22,
     8476,8477,8478,8479,9335,3382,3383,3384,3385,3386,3387,3388,3389,3390,3391,3392,3393,3394,3395,3396,3397,3398}, /* 百合花(BAIHEHUA)@58 */
     {17,
     3399,3400,3401,3402,3403,3404,3405,3406,9461,9462,3407,3408,3409,9894,9895,9896,9897}, /* 成都(CHENGDU)@58 */
     {32,
     3410,3411,3412,3413,3414,3415,3416,3417,3418,9451,9452,9453,9454,9455,9456,3419,3420,3421,3422,3423,3424,3425,3426,3427,3428,3429,3430,3431,3432,3433,3434,3435}, /* 长风(Changfeng)@58 */
      {27,
    3436,3437,3438,3439,3440,3441,3442,9447,9448,9449,9450,3443,3444,3445,3446,3447,3448,3449,3450,3451,3452,3453,3454,3455,3456,3457,3458}, /* 长飞(CHANGFEI)@58 */
     {18,
     3459,3460,3461,3462,3463,3464,3465,3466,3467,9457,9458,9459,9460,3468,3469,3470,3471,3472}, /* 长海(Changhai)@58 */
     {23,
     8507,8508,8509,3473,3474,3475,3476,3477,3478,3479,9473,9474,3480,3481,3482,3483,3484,3485,9951,9952,9953,9954,9955}, /* 春兰(Chunlan)@58 */
     {27,
     3486,3487,3488,3489,3490,3491,3492,3493,3494,3495,3496,3497,9468,9469,9470,9471,3498,3499,3500,3501,3502,3503,9946,9947,9948,9949,9950}, /* 春风(Spring breeze)@58 */
     {13,
     3504,3505,3506,3507,3508,9475,9476,3509,3510,9956,9957,9958,9959}, /* 春笋(Chunsun)@58 */
     {15,
     3511,3512,3513,9562,3514,3515,3516,3517,3518,10020,10021,10022,10023,10024,10025}, /* 东大(Dongda)@58 */
     {11,
     6423,3519,3520,3521,3522,3523,3524,5194,5011,5195,9563}, /* 东海(East China Sea)@58 */
     {16,
     8510,8511,8512,3525,3526,3527,3528,3529,9610,9611,3530,3531,3532,3533,3534,3535}, /* 飞鹿(Flying Deer)@58 */
     {13,
     5194,4836,4837,5011,5195,3536,3537,3538,3539,3540,5194,5011,5195}, /* 黄海美(Yellow America)@58 */
     {14,
     3559,3560,3561,3562,3563,3564,3565,3566,3567,3568,3569,3570,3571,3572}, /* 海燕(Swallow)@58 */
     {16,
     3573,3574,3575,3576,3577,3578,3579,3580,3581,3582,3583,3584,3585,3586,3587,3588}, /* 华日(Huari)@58 */
     {12,
     3589,3590,3591,3592,3593,3594,3595,3596,3597,3598,3599,3600}, /* 篅(Hai Le)@58 */
     {35,
     8236,8237,8238,8239,8240,8241,8242,8243,8244,8245,8246,8247,8248,8249,8250,3601,3602,3603,3604,3605,3606,3607,3608,3609,3610,3611,3612,3613,3614,3615,3616,3617,3618,3619,3620}, /* 虹美(Hongmei)@58 */
     {18,
     3621,3622,3623,3624,3625,3626,3627,3628,3629,3630,3631,3632,3633,3634,3635,3636,3637,3638}, /* 海虹(Haihong)@58 */
     {21,
     3639,3640,3641,3642,3643,3644,3645,3646,3647,3648,3649,3650,3651,3652,3653,3654,3655,3656,3657,3658,3659}, /* 菊花(Chrysanthemum)@58 */
     {15,
     3660,3661,3662,3663,3664,3665,3666,3667,3668,3669,3670,3671,3672,3673,3674}, /* 韶峰(Shaofeng)@58 */
     {8,
     3675,3676,3677,3678,3679,3680,3681,3682}, /* 三键(Three key)@58 */
     {5,
     3683,3684,3685,3686,3687}, /* 威牌(Vira brand)@58 */
     {42,/*前插入13组*/
      8223,8224,8225,8226,8227,8228,8229,8230,8231,8232,8233,8234,8235,6020,3688,3689,3690,3691,3692,3693,3694,3695,3696,
      3697,3698,3699,3700,3701,3702,4854,4855,4856,4857,4858,4859,4860,4861,4862,4863,4864,4865,4866}, /* 环宇(Huanyu)@58 */
      {35,
     8406,8407,8408,8409,8410,8411,8412,8413,8414,8415,8416,8417,8418,8419,3703,3704,3705,3706,3707,3708,3709,3710,3711,3712,3713,3714,3715,3716,3717,3718,3719,3720,3721,3722,3723}, /* 皇冠(Crowne)@58 */
     {8,
     8524,8525,3724,3725,3726,3727,3728,3729}, /* 黄龙(Huanglong)@58 */
     {19,
     8360,9320,3730,3731,3732,3733,3734,3735,3736,5194,9722,9723,9724,9725,9726,9727,9728,5011,5195}, /* 奥林普(AOLINPU)@58 */
     {36,
     10994,10995,10996,10997,10998,10999,11000,11001,10714,10715,10716,10717,10718,10719,10720,10721,10710,10711,10712,10713,
     8438,8439,3745,9224,3746,3747,3748,3749,3737,3738,3739,3740,3741,3742,3743,3744}, /* 数源(Soyea)@58 */
     {25,/*9146前*/
     9143,3750,9144,3751,9147,3752,3753,10108,9148,8420,9145,3757,5965,10412,9146,9149,9643,3754,3755,3756,10105,10106,10107,10109,10110,}, /* 富士通(Fujitsu)@58 */
     {55,
     8215,8216,8217,5781,9190,6851,5782,5783,5784,5785,5786,3758,3759,9374,3760,3761,3762,3763,3764,3765,3766,3767,3768,3769,3770,3771,3772,3773,3774,3775,3776,3777,
     3778,3779,3780,3781,3782,3783,3784,3785,3786,3787,3788,3789,3790,3791,3792,3793,3794,3795,3796,3797,3798,3799,3800}, /* 明基(BenQ)@58 */
     {9,
     8425,8426,3801,3802,3803,3804,3805,3806,3807}, /* 三灵(Sanling)@58 */
     {7,
     3808,3809,3810,3811,5194,5011,5195}, /*CYBEX(CYBEX)@58 */
     {10,
     3812,3813,3814,3815,3816,3817,3818,3819,3820,3821}, /* 金鹊(Gold Magpie)@58 */
     {25,
     10936,10937,10938,10939,10940,10656,10657,10658,10659,10660,
     3822,3823,3824,3825,3826,3827,3828,3829,3830,3831,3832,3833,3834,3835,3836}, /* 金凤(JINFENG)@58 */
     {7,
     3837,3838,3839,3840,3841,3842,3843}, /* 天鹅(Swan)@58 */
     {9,
     3844,3845,3846,3847,3848,3849,5194,5011,5195}, /* 翔宇(Xiangyu)@58 */
     {16,
     3850,3851,3852,3853,3854,3855,3856,3857,3858,3859,3860,3861,3862,3863,3864,3865}, /* 金塔(Urn)@58 */
     {22,
     8251,8252,8253,8254,8255,3866,3867,3868,3869,3870,3871,3872,3873,3874,5315,5316,5317,5318,5319,5320,5321,5322}, /* 金雀(Broom)@58 */
      {16,
    3875,3876,3877,3878,3879,3880,3881,3882,3883,3884,3885,3886,3887,3888,3889,3890}, /* 凯歌(Veuve)@58 */
     {40,
     8275,8276,8277,8278,8279,8280,8281,8282,8283,8284,8285,8286,8287,8288,3891,3892,
     3893,3894,3895,3896,3897,3898,3899,3900,3901,3902,3903,3904,3905,3906,3907,3908,3909,3910,3911,3912,3913,3914,3915,3916}, /* 康力(Kang Li)@58 */
     {7,
    3917,3918,3919,3920,3921,3922,3923}, /* 康虹(Kang Hong)@58 */
     {48,
     8318,8319,8320,8321,8322,8323,8324,8325,8326,8327,8328,8329,8330,8331,8332,8333,8334,3924,3925,3926,3927,3928,3929,3930,3931,3932,3933,3934,3935,3936,3937,3938,3939,3940,3941,3942,3943,3944,3945,3946,3947,
     3948,3949,3950,3951,3952,3953,3954}, /* 上海(Shanghai)@58 */
     {8,
     3955,3956,3957,3958,3959,3960,3961,3962}, /* 快乐(Joyful)@58 */
      {14,
     8305,8306,8307,8308,3963,3964,3965,3966,3967,3968,5194,5011,5195,6693}, /* 梦寐(Dream)@58 */
     {8,
     3969,3970,3971,3972,3973,3974,3975,3976}, /* 南声(South Sound)@58 */
     {17,
     8289,8290,8291,8292,8293,8294,8295,3977,3978,3979,3980,3981,3982,3983,3984,3985,3986}, /* 康艺(Kang Yi)@58 */
     {6,
     8400,8401,3987,3988,3989,3990}, /* 狮龙(Lion and dragon)@58 */
     {24,
    8309,8310,8311,8312,8313,8314,8315,8316,8317,3991,3992,3993,3994,3995,3996,3997,3998,3999,4000,4001,4002,4003,4004,4005}, /* 青岛(Qingdao)@58 */
     {18,
    4006,4007,4008,4009,4010,4011,4012,4013,4014,4015,4016,4017,4018,4019,4020,4021,4022,4023}, /* 康立(Kang)@58 */
      {12,
     4024,4025,4026,4027,4028,4029,4030,4031,4032,4033,4034,4035}, /* 如意(Ruyi)@58 */
     {17,
     4036,4037,4038,4039,4040,4041,4042,4043,4044,4045,4046,4047,4048,4049,4050,4051,4052}, /* 孔雀(Peacock)@58 */
     {22,
     8449,8450,4053,4054,4055,4056,4057,4058,4059,4060,4061,4062,4063,4064,4065,4066,4067,4068,4069,4070,4071,4072}, /* 神彩(God Choi)@58 */
     {9,
    4073,4074,4075,4076,4077,4078,4079,4080,4081}, /* 龙江(Longjiang)@58 */
     {9,
     4082,4083,4084,4085,4086,4087,4088,4089,4090}, /* 山茶(Camellia)@58 */
     {10,
    4091,4092,4093,4094,4095,4096,4097,4098,4099,4100}, /* 利华(Lihua)@58     */
      {25,
    9182,9183,9184,9185,9186,9187,9188,9189,4101,4102,4103,4104,4105,4106,4107,4108,4109,4110,4111,4112,4113,4114,4115,4116,4117}, /* 美乐(Melody)@58 */
     {16,
    4118,4119,4120,4121,4122,4123,4124,4125,4126,4127,4128,4129,4130,4131,4132,4133}, /* 星海(Xinghai)@58 */
     {11,
     4134,4135,4136,4137,4138,4139,4140,4141,4142,4143,4144}, /* 沈阳(Shenyang)@58 */
     {6,
     4145,4146,4147,4148,4149,4150}, /* 塞格(Sager)@58 */
     {14,
     4151,4152,4153,4154,4155,4156,4157,4158,4159,4160,4161,4162,4163,4164}, /* 襄阳(Yangyang)@58 */
     {7,
     4165,4166,4167,4168,4169,4170,4171}, /* 松柏(Evergreen)@58  */
      {22,
     8427,8428,4172,9127,9128,4173,4174,4175,9463,9464,9898,4176,4177,4178,4179,4180,4181,4182,4183,4184,4185,4186}, /* 创佳(CHUANGJIA)@58 */
     {6,
     8434,4187,4188,4189,4190,4191}, /* 日声(Day Sound)@58 */
     {7,
     4192,4193,4194,4195,4196,4197,4198}, /* 幸福(Happiness)@58 */
     {13,
     8377,8378,8379,8380,8381,4199,4200,4201,4202,4203,4204,4205,4206}, /* 优视达(As of gifted)@58 */
     {13,
     8364,8365,8366,8367,8368,4207,4208,4209,4210,4211,4212,4213,4214}, /* 优拉纳斯(Excellent Lana Si)@58 */
     {11,
     8336,8337,8338,8339,4215,4216,4217,4218,4219,4220,4221}, /* 新日松(New Day Song)@58 */
     {8,
     4246,4247,4248,4249,4250,4251,4252,4253}, /* 健生(JIANSHENG)@58 */
     {8,
     8361,8362,8363,4254,4255,4256,4257,4258}, /*WARUMAIA(WARUMAIA)@58 */
     {10,
     8422,8423,8424,4259,4260,4261,4262,4263,4264,4265}, /* 尼康(Nikon)@58 */
     {19,
    8421,4266,4267,4268,4269,4270,9645,9646,9647,9648,4271,4272,10098,10099,10100,10101,10102,10103,10104}, /* 富丽(Wealthy)@58 */
     {8,
     8546,4273,4274,4275,4276,4277,4278,4279}, /* 英特尔(INTEL)@58  */
      {8,
     4280,4281,4282,4283,4284,4285,4286,4287}, /* 莺歌(Yingge)@58 */
     {7,
     4311,4312,4313,4314,4315,4316,4317}, /* 宇航(Aerospace)@58 */
     {8,
     4318,4319,4320,4321,4322,4323,4324,4325}, /* 永固(Everlasting)@58 */
     {17,
     8548,8549,8550,4326,4327,4328,4329,4330,4331,4332,9973,9974,9975,9976,9977,9978,9979}, /* 德基德克(DIGITEC)@58 */
     {6,
     4333,4334,4335,4336,4337,4338}, /* 永宝(Yongbao)@58 */
     {9,
     8547,4339,4340,4341,4342,4343,4344,4345,4346}, /*SUMO(SUMO)@58   */
      {7,
     8542,4347,4348,4349,4350,4351,4352}, /* 珠海(Zhuhai)@58 */
     {15,
    4382,4383,4384,4385,4386,4387,4388,4389,4390,4391,4392,4393,9656,9657,9658}, /* 赣新(Jiangxi)@58 */
     {6,
     4394,4395,4396,4397,4398,4399}, /* 华发(Wafa)@58 */
     {7,
     4400,4401,4402,4403,4404,4405,4406}, /* 华强(HUAQIANG)@58 */
     {14,
    8555,4407,4408,4409,4410,4411,4412,4413,4414,4415,4416,4417,4418,4419}, /* 康华(KANGHUA)@58 */
     {6,
     4420,4421,4422,4423,4424,9609}, /*飞浪(Fei Long)@58 */
      {27,/* 4425前插入3组 */
     8340,8341,8342,4425,6791,6792,6793,6794,6795,6796,6797,6798,6799,6800,6801,6042,10467,10468,10469,5541,5542,5543,5544,4426,4427,4428,4429}, /* 日本胜利(JVC)@58 */
     {19,
     8343,8344,8345,8346,8347,8348,8349,4430,4431,9612,9613,4432,4433,4434,4435,4436,4437,4438,4439}, /* 飞跃(Leap)@58     */
     {11,
    8452,4440,4441,4442,4443,4444,4445,4446,4447,4448,4449}, /* 鑫萌板(Xin Meng board)@58 */
     {9,
     4450,4451,4452,4453,4454,4455,4456,4457,4458}, /* 东王(East King)t@58 */
     {9,
     8435,8436,8437,4459,4460,4461,4462,4463,4464}, /* 康维(Comverse)@58 */
     {20,
     4465,4466,4467,4468,4469,4470,4471,4472,4473,4474,4475,4476,4477,4478,4479,4480,4481,4482,4483,4484}, /* 新思达(New Star)@58     */
      {9,
     8448,4485,4486,4487,4488,4489,4490,4491,4492}, /* 塞河(SEYE)@58 */
     {14,
     8454,4499,4500,4501,4502,4503,4504,4505,4506,4507,4508,4509,4510,4511}, /* 天庚板(Day Geng board)@58 */
     {7,
     8451,4512,4513,4514,4515,4516,4517}, /* 日红(Rihong)@58 */
     {15,
     8443,4518,4519,4520,4521,4522,4523,4524,4525,4526,4527,4528,4529,4530,4531}, /* 金鑫板(Jinxin board)@58 */
     {9,
     8442,4532,4533,4534,4535,4536,4537,4538,4539}, /* 汇佳板(Huijia board)@58 */
     {22,/* 6935前插入5组 */
     8218,8219,8220,8221,8222,6935,6936,6937,6938,4540,5582,5583,5584,5585,5586,4541,4542,4543,4544,4545,4546,4547}, /* 上广电(SVA)@58     */
     {8,
     8453,6422,4548,4549,4550,4551,4552,4553}, /* 天科板(Tianke board)@58 */
     {10,
     8447,4554,4555,4556,4557,4558,4559,4560,4561,4562}, /* 欧凌(Ouling)@58 */
     {45,
     8264,8265,8266,8267,8268,8269,8270,8271,8272,8273,8274,4563,4564,4565,4566,4567,4568,4569,4570,4571,4572,4573,4574,4575,4576,4577,4578,4579,4580,4581,4582,4583,4917,4918,4919,4920,4921,4922,4923,4924,4925,4926,5194,5011,5195}, /* 昆仑(Kunlun)@58 */
     {50,
     8402,8403,8404,8405,5733,9257,9258,9259,9260,9261,9262,9302,6525,6526,6527,6528,6529,6530,6531,6532,6533,6534,6535,6536,6537,6538,6539,6540,6541,6542,6543,6544,5734,5735,5736,5737,5738,5739,5740,5741,5742,5743,5744,4584,4585,4586,4587,4588,4589,4590}, /* 雅佳(Akai)@58 */
     {12,
    8440,8441,4591,4592,4593,4594,4595,4596,4597,4598,4599,4600}, /*  日芝(Day Chicago)@58 */
     {28,
     8369,8370,8371,8372,8373,8374,8375,8376,4601,4602,4603,4604,4605,4606,4607,4608,4609,4610,4611,4612,9672,10126,10127,10128,10129,10130,10131,10132}, /* 高士达(Goldstar)@58 */
     {12,
     8359,4613,4614,4615,4616,9411,4617,4618,4619,4620,4621,4622}, /* 彩凌(Cai Ling)@58     */
    {8,
    8446,4623,4624,4625,4626,4627,4628,4629}, /*  松电(Loose electrical)@58 */
     {11,
     8399,4630,9223,4631,4632,4633,4634,4635,4636,4637,4638}, /* 胜利(Victory)@58 */
     {22,
     8444,4639,4640,4641,4642,4643,4644,4645,4646,4647,4648,4649,4650,4651,4652,4653,4654,4655,4656,4657,4658,4659}, /* 满天星(Gypsophila)@58 */
     {13,
    4660,4661,4662,4663,5194,9712,9713,9714,9715,9716,9717,5011,5195}, /* 安化(ANHUA)@58 */
     {11,
    4664,4665,4666,4667,4668,4669,4670,4671,4672,4673,4674}, /*  佰乐(BAILE)@58 */
     {9,
     4675,4676,4677,5194,5011,5195,9788,9789,9790}, /* 宝生(BAOSHENG)@58 */
     {7,
     4678,4679,4680,4681,5194,5011,5195}, /* 宝华视(BaoHuaShi)@58 */
     {4,
     5194,4682,5011,5195}, /* 彩铃(CAILING)@58    */
     {5,
     4683,4684,4685,4686,4687}, /* 彩行(CAIXING)@58 */
     {8,
    4688,4689,4690,4691,4692,4693,4694,4695}, /*  长丰(CHANGFENG)@58      */
    {10,
     8429,8430,5194,4736,4738,4739,9564,9565,5011,5195}, /* 东杰(DUONGJIE)@58 */
     {12,
     5194,4739,4740,4741,5011,5195,9964,9965,9966,9967,9968,9969}, /* 大禹(DAYU)@58 */
     {7,
     5194,4742,5195,9644,10095,10096,10097}, /* 富力(FULI)@58 */
     {8,
    4743,4744,4745,4746,4747,4748,4749,4750}, /* 淦鑫(GANXIN)@58 */
     {30,
     4808,4809,4810,4811,4812,4813,4814,4815,4816,4817,4818,4819,4820,4821,4822,4823,4824,10082,10083,10084,10085,10086,10087,10088,10089,10090,10091,10092,10093,10094}, /* 福建(HITCHI FUFIAN)@58 */
     {3,
    4838,4839,4840,4841,4842}, /* 瓦里(HUARI)@58 */
     {9,
     4843,4844,4845,4846,4847,4848,4849,4850,4851}, /* 海盐(HAIYAN)@58 */
     {3,
     4852,4853,5011}, /* 黑尔(HAILE)@58 */
    {15,
    4867,4868,4869,4870,4871,4872,4873,4874,4875,4876,4877,4878,4879,4880,4881}, /* 红梅(HONGMEI)@58 */
     {4,
     5194,4882,5011,5195}, /* 慧佳伴(HUIJIABAN)@58 */
     {7,
     4883,4884,4885,4886,5194,5011,5195}, /* 伙大科技(HUODAKEJI)@58 */
     {28,
     4887,4888,4889,4890,4891,4892,4893,4894,4895,4896,4897,4898,4899,4900,9980,9981,9982,9983,9984,9985,9986,9987,9988,9989,9990,9991,9992,9993}, /* 帝国科恩(IMPERIAL COWN)@58 */
     {52,
    8386,8387,8388,8389,8390,8391,8392,8393,8394,3086,3087,3088,3089,3090,3091,3092,3093,3094,3095,3096,3097,3098,3099,3100,3101,3102,3103,3104,3105,3106,3107,
    3108,3109,3110,3111,3112,3113,3114,3115,3116,4901,4902,4903,4904,4905,4906,4907,4908,4909,5194,5011,5195}, /* 佳丽彩(JIALICAI)@58 */
      {8,
     8431,8432,8433,4911,4912,5194,5011,5195}, /* 金利普(JINGLIPU)@58 */
     {7,
    4913,4914,4915,4916,5194,5011,5195}, /* 静海(JINGHAI)@58 */
     {6,
     4941,4942,4943,5194,5011,5195}, /* 康宏(KANGHONG)@58     */
    {27,
     4927,4928,4929,4930,4931,4932,4933,4934,4935,4936,4937,4938,4939,4940,4944,4945,4946,4947,4948,
     4949,4950,4951,4952,4953,4954,4955,4956}, /* 康利(KANGLI)@58 */
      {16,
     4957,4958,4959,4960,4961,4962,4963,5194,5195,10119,10120,10121,10122,10123,10124,10125}, /* 甘基(KANGYI)@58    */
       {5,
     5008,5009,5010,5194,5195}, /* 康威(KANGWEI)@58   */
        {3,
     5194,5011,5195}, /* 丽华(LIHUA)@58 */
       {6,
     5012,5013,5014,5015,5194,5195}, /* 梦美(MENGMEI)@58      */
       {7,
     5016,5017,5018,5019,5020,5194,5195}, /* 南盛(NANSHENG)@58    */
       {11,
     5021,5022,5023,5024,5025,5026,5027,5028,5029,5194,5195}, /* 南堡(NANBAO)@58      */
       {3,
     5030,5194,5195}, /* 欧琳(OULIN)@58   */
       {5,
     5030,5031,5032,5194,5195}, /* 日升(RISHENG)@58   */
       {9,
     5034,5035,5036,5037,5038,5039,5040,5194,5195}, /* 神采(SHENCAI)@58   */
       {10,
     5041,5042,5043,5044,5045,5046,5047,5048,5194,5195}, /* 赛格(SAIGE)@58    */
       {6,
     5049,5050,5051,5052,5194,5195}, /* 三间(SANJIAN)@58      */
       {20,
     8335,6939,5109,5110,5111,5112,5113,5114,5115,5116,5117,5118,5119,9030,9031,9032,9033,9034,9035,9036}, /* 山水(Landscape)@58      */
       {4,
     5120,5121,5194,5195}, /* 剩菜(SHENGCAI)@58   */
       {4,
     5122,5123,5194,5195}, /* 学院(SHUYAN)@58     */
       {3,
     5124,5194,5195}, /* 生力(SHENGLI)@58     */
       {3,
     5125,5126,5195}, /* 伍德(SHERWOOD)@58    */
       {3,
    5127,5194,5195}, /*TIANKEBAN(TIANKEBAN)@58 */
       {3,
     5128,5194,5195}, /*TIANGENGBAN(TIANGENGBAN)@58     */
       {10,
     5129,5130,5131,5132,5133,5134,5135,5136,5137,5138}, /* 台山(TAISHAN)@58      */
       {5,
     5139,5140,5141,5142,5195}, /* 同光(TONGGUANG)@58     */
       {9,
     5143,5144,5145,5146,5147,5148,5149,5150,5151}, /* 东洋(TOBO)@58      */
       {2,
     5152,5153}, /* 威派(WEIPAI)@58   */
       {8,
     5187,5188,5189,5190,5191,5192,5194,5195}, /*XINAGHAI(XINAGHAI)@58   */
       {3,
     5193,5194,5195}, /*XINGMENBAN(XINGMENBAN)@58      */
       {2,
     5194,5195}, /* 新抚(XINFU)@58    */
       {4,
    5196,5197,5198,5199}, /*XINRISONG(XINRISONG)@58 */
       {4,
     5200,5201,5217,5218}, /* 余杭(YUHANG)@58     */
       {4,
     5202,5203,5204,5218}, /* 永保(YONGBAO)@58    */
       {5,
    5205,5206,5207,5208,5209}, /*YOULANASI(YOULANASI)@58      */
       {5,
     5210,5211,5212,5213,5214}, /* 优斯达(YOUSIDA)@58     */
      {11,
     4364,4365,4366,4367,4368,4369,4370,5215,5216,5217,5218}, /* 888品牌(888)@58       */
      {4,
     5353,5221,5220,5229,1404}, /* 丽格美(lgm,Le42A3030)@58   */
      {4,
     5353,5219,5229,1404}, /* AOC电视T2242we(yuyu168)@58      */
      {5,
     5353,5223,5222,5229,1404}, /* 天敏电视(Days Min TV)@58   */
      {4,
     5353,5224,5229,1404}, /* 内视电视卡(fzfe)@58         */
        {4,
     5353,5225,5229,1404}, /* LT360W(sbmtv)@58    */
        {3,
     5353,5226,5229}, /*10moons(10moons)@58     */
        {4,
     5353,5227,5229,1404}, /* 索映电视(htboy)@58      */
       {11,
     7023,7024,7025,7026,7027,7028,7029,5353,5228,5229,1404}, /* 先锋(Pioneer TV)@58          */
      {13,/* 5353后插入3组 */
      9312,7037,7038,7039,5756,5353,5677,5678,5679,5354,5229,1395,1404}, /* 夏新(Amoi TV)@58      */
      {10,
     5348,5230,5349,5350,5351,5352,5353,5354,
     1395,1404}, /* LC-22ST1(lingxf)@58  */
      {21,
     5293,5294,5295,5296,5297,5298,5299,5300,5301,5302,5303,5304,5305,5306,5307,5308,5309,5310,5311,5312,5313}, /* 三环宇(Universal)@58         */
       {9,
     5315,5314,5316,5317,5318,5319,5320,5321,5322}, /* 三穗(SANSUI)@58 */
      {40,
     8256,8257,8258,8259,8260,8261,8262,8263,5323,5324,5325,5326,5327,5328,5329,5330,5331,5332,5333,5334,5348,5349,5350,5351,5352,5353,5354,/*  */
     1395,1404,10233,10234,10235,10236,10237,10238,10239,10240,10241,10242,10243}, /* 嘉华(Jiahua)@58       */
      {22,
     5348,5335,5335,5336,5337,5338,5339,5340,5341,5342,5343,5344,5345,5346,5347,5349,5350,5351,5352,5353,5354,/*  */
     1395,1404}, /* 通广(Tongguang)@58     */
      {9,
     5348,5349,5350,5351,5352,5353,5354,
     1395,1404}, /* 建盛(JIAN SHENG)@58   */
    {19,
      5355,1387,1396,1397,1398,1399,1400,1401,1402,1403,1388,1389,1390,1391,1392,1393,1394,1395,1404}, /* 东林(DONGLIN)@58 */
     {59,/*8214前*/
     10796,10516,8211,8212,8213,5482,10136,10141,10140,10159,10142,10146,10149,8214,9056,9057,5761,5762,5763,5764,5765,5479,5480,5481,5483,
     9319,10133,10134,10135,10137,10138,10139,10143,10144,10145,10147,10148,10150,10151,10152,10153,10154,10155,10156,10157,10158,10160,
     10161,10162,10163,10164,10165,10166,10167,10388,10389,10390,10391,10392},/* 冠捷(AOC)@8  */
     {2,
     5540,6790},/* 金正(Nintaus)@15 */
     {7,/*9290前*/
     10797,10517,8531,8532,5714,5715,9290},/* 宏基(Acer)@15 */
     {5,
     5716,5717,5718,5719,5720},/* 极致(Acme)@15 */
     {1,
     5721},/* 阿尔茨(ADC)@15 */
     {5,
     9296,5722,5723,5724,5725},/* 海军上将(Admiral)@15 */
     {1,
     5726},/* 基督降临(Advent)@15 */
     {1,
     5727},/* 阿加齐(Agazi)@15 */
     {8,
     9300,5728,5729,5730,6516,6517,6518,6519},/* 爱子 (Aiko)@15 */
     {3,
     9304,5745,6546},/* 秋叶(Akiba)@10 */
     {14,
     9307,5746,5747,6550,6551,6552,6553,6554,6555,6556,6557,6558,6559,6560},/* 樱花(Akura)@8 */
     {20,
     9308,5748,6562,6563,6564,6565,6566,6567,6568,6569,6570,6571,6572,6573,6574,5749,5750,5751,5752,5753},/* 阿尔巴(Alba)@8 */
     {1,
     5754},/*Alkos(Alkos)@8 */
     {2,
     5755,6575},/*Allorgan(Allorgan)@10 */
     {22,
     10860,10861,10862,10863,10864,10865,9313,5757,6586,6587,6588,6589,6590,6591,6592,6593,6594,6595,6596,6597,5758,5759},/* 阿姆斯特德(Amstrad)@10 */
     {2,
     5760,9317},/*Anitech(Anitech)@10 */
     {7,
     10866,10867,10868,10869,10870,5766,10393},/* 埃佩克斯(Apex)@10 */
     {8,
     5767,6601,6602,6603,6604,6605,6606,6607},/* 亚比央(ASA)@10 */
     {1,
     5768},/* 明日香(AsuKa)@10 */
      {5,
     10798,10799,5769,10518,10519},/* 华硕(Asus)@10 */
     {2,
     9330,5770},/* 亚特兰大(Atlantic)@10 */
     {1,
     5771},/* 音频动力学 (Audio Dynamics)@10 */
     {1,
     5772},/*Audiosonic(Audiosonic)@10 */
     {5,
     5773,5774,10394,10395,10396},/*Audiovox(Audiovox)@10 */
     {1,
     5775},/* 奥特华 (Autovox)@10 */
      {1,
     5776},/*Avol(Avol)@10 */
     {3,
     5777,5778,9339},/* 贝尔德(Baird)@10 */
     {1,
     5779},/* 基本线 (Baisic Line)@10 */
     {3,
     5780,9370,9371},/* 贝科 (Beko)@10 */
     {1,
     5787},/* 贝斯特(Best)@10 */
     {2,
     5788,9377},/* 吉星(Bestar)@10 */
     {5,
     5789,9378,9379,9380,9381},/* 黑钻石(Black Diamond)@10 */
     {4,
     5790,9394,9395,9396,},/* 勃兰特(Brandt)@10 */
      {3,
     5791,9400,10397},/*Broksonic(Broksonic)@10 */
     {2,
     5792,5793},/*Brokwood(Brokwood)@10 */
     {18,
     8543,5794,5795,5796,5797,5798,5799,9401,9402,9403,9404,9405,9406,5800,5801,5802,5803,5804},/* 布什(Bush)@10 */
     {1,
     5805},/* 圣餐杯(Calix)@10 */
     {2,
     5806,5807},/* 蜡烛(Candle)@10 */
     {8,
     5808,9780,9781,9782,9783,9784,9785,9786},/*Capsonic(Capsonic)@10 */
     {2,
     5809,9420},/* 家乐福(Carrefour)@10 */
     {5,
     10884,10885,10886,5810,9424},/* 克拉珀龙(CCE)@10 */
     {3,
     5811,5812,9429},/* 中枢(Centrum)@10 */
     {8,
     5813,9780,9781,9782,9783,9784,9785,9786},/* 电器公司(CGE)@10 */
     {8,
     8209,8210,5816,9202,5817,5818,5819,5820},/* 奇美(Chimei)@10 */
     {1,
     5821},/*cllL(cllL)@10 */
     {1,
     5822},/* 辛姆莱 (Cimline)@10 */
     {9,
     5823,5824,9780,9781,9782,9783,9784,9785,9786},/* 西铁城牌(Citizen)@10 */
     {2,
     5825,9478},/*clatronic(clatronic)@10 */
     {4,
     5826,9480,9481,10398},/* 科比(Coby)@10 */
     {1,
     5827},/* 康比特(Combitech)@10 */
     {2,
     5828,5829},/* 协奏曲(Concerto)@10 */
     {2,
     5830,9483},/*  神鹰(Condor)@10 */
     {4,
     5831,5832,5833,9504},/* 康泰克(Contec)@10 */
     {8,
     5834,5835,5836,9507,9508,9509,9510,9511},/* 王冠(Crown)@10 */
     {18,
     5837,5838,5839,5840,5841,5842,5843,5844,5845,5846,5847,5848,5849,5850,5851,5852,5853,5854},/* 司令(Cybercom)@10 */
     {8,
     5855,9780,9781,9782,9783,9784,9785,9786},/* 金卫信(cybermax)@10 */
     {22,
     5856,5857,5858,5859,5860,5861,5862,5863,5864,5865,5866,5867,5868,5869,5870,5871,5872,5873,5874,5875,5876,5877},/*cybermaxx(cybermaxx)@10 */
      {21,
     5878,5879,5880,5881,5882,5883,5884,5885,5886,5887,5888,5889,5890,5891,5892,5893,5894,5895,5896,5897,5898},/*Cytron(Cytron)@10 */
     {6,
     5903,5904,5905,5906,9533,9534},/* Dantax(Dantax)@10 */
     {2,
     5907,9538},/* 德格拉夫(De Graaf)@10 */
     {3,
     5908,5909,9540},/* 迪卡(Decca)@10 */
     {5,
     8528,8529,5910,5911,10400},/* 戴尔(Dell)@10 */
     {8,
     5912,9780,9781,9782,9783,9784,9785,9786},/* 天龙(Denon)@10 */
     {3,
     5913,9556,9557,},/* Digitor(Digitor)@10 */
     {2,
     5914,9558},/* 迪希(Dixi)@10 */
      {3,
     5915,5916,5917},/* 杜阿河(Dua)@10 */
     {14,
     5918,5919,5920,5921,5922,5923,5924,5925,5926,5927,5928,5929,5930,9570},/* 双重(Dual)@10 */
     {2,
     5931,5932},/* 杜蒙特(Dumont)@10 */
     {3,
     5933,9577,10401},/* 尼克斯(Dynex)@10 */
     {2,
     5934,9582},/* 传真(Electrograph)@10 */
     {2,
     5935,9583},/*Electrohome(Electrohome)@10 */
     {8,
     5936,9780,9781,9782,9783,9784,9785,9786},/* 电子(Electron)@10 */
     {1,
     5937},/* 电响(Electrophonic)@10 */
     {7,
     5938,5939,5940,10402,10403,10404,10405},/* 元电子(Element Electronics)@10 */
     {9,
     5941,9587,9780,9781,9782,9783,9784,9785,9786},/* 埃琳(Elin)@10 */
     {14,
     5942,5943,9589,9590,9591,9592,9593,9594,10406,10407,10408,10409,10410,10411},/* 爱默生(Emerson)@10 */
     {8,
     5944,9780,9781,9782,9783,9784,9785,9786},/* 欧元(Euro-Feel)@10 */
     {1,
     5945},/*Euroline(Euroline)@10 */
     {8,
     5946,9780,9781,9782,9783,9784,9785,9786},/* 专家(Expert)@10 */
     {1,
     5947},/* 六角瓦(Favi)@10 */
      {8,
     5948,9780,9781,9782,9783,9784,9785,9786},/* 芬纳(Fenner)@10 */
     {1,
     5949},/* 保真度(Fidelty)@10 */
     {2,
     5950,9620},/* 芬兰(Finlandia)@10 */
     {6,
     5951,5952,5953,5954,5955,9621},/*Finlux(Finlux)@10 */
     {4,
     5956,5957,5958,9627},/* 费希尔(Fisher)@10 */
     {2,
     5959,9628},/* 弗林特(Flint)@10 */
     {2,
     5960,9629},/*福尔门蒂(Formenti)@10 */
     {2,
     5961,5962},/* 堡垒(Fortress)@10 */
      {1,
     5963},/* 富士康(Foxconn)@10 */
     {8,
     5964,9780,9781,9782,9783,9784,9785,9786},/* 藤丸(Fujimaru)@10 */
     {8,
     5966,9780,9781,9782,9783,9784,9785,9786},/* 船井(Funai)@10 */
     {1,
     5967},/* 银河系(Galaxy)@10 */
     {4,
     5968,9659,9660,10413},/* 盖特韦(Gateway)@10 */
     {1,
     5969},/* 游戏机(GBC)@10 */
     {3,
     5970,5971,9666},/*GEC(GEC)@10 */
     {3,
     5972,5973,5974},/* 高仕达(Goldstar)@10 */
     {6,
     5975,5976,5977,5978,5979,9673},/* 古德曼(Goodmans)@10 */
     {1,
     5980},/* 塞尔维亚(Gorenje)@10 */
      {1,
     5981},/* 宏处理机(GPM)@10 */
     {5,
     5982,5983,5984,9678,9679,9680,9681},/* 格拉纳达(Granada)@10 */
     {2,
     5985,9682},/* 格兰丁(Grandin)@10 */
     {7,
     5987,5988,5989,5990,9683,9684,9685},/* 根德(Grundig)@10 */
     {2,
     5991,5992},/* 百禾慧(H&B)@10 */
     {5,
     8530,5997,5999,10441,10442},/* 瀚斯宝丽(Hanspree)@10 */
     {2,
     5998,9692},/* 汉萨(Hanseatic)@10 */
     {2,
     6000,9693},/*Hantarex(Hantarex)@10 */
     {2,
     6001,9697},/* HCM(HCM)@10 */
     {3,
     6002,6003,6004},/* 禾联(Heran)@10 */
     {8,/*6018前*/
     10800,10520,6005,10444,6018,6019,6006,9698,},/* 惠普(Hewlett Packard)@10 */
     {4,
     6007,6008,9700,9701},/*成果(Hinari)@10 */
     {2,
     6009,9702},/*Hisawa(Hisawa)@10 */
     {2,
     6021,6022},/* 国际劳工组织(ILO)@10 */
     {1,
     6023},/* 因皮里尔(Imperial)@10 */
     {7,
     6024,6025,6026,6027,6028,6029,6030},/* 创新(Innovation)@10 */
     {1,
     6031},/*Insiginia(Insiginia)@10 */
     {4,
     6032,10464,10465,10466},/* 徽章(Insignia)@10 */
     {1,
     6033},/*Intertronic(Intertronic)@10 */
     {2,
     6034,6035},/* 电话电信公司(ITT)@10 */
      {3,
     6036,6037,6038},/* 教学电视(ITV)@10 */
     {8,
     6039,9780,9781,9782,9783,9784,9785,9786},/*JCPenney(JCPenney)@10 */
     {8,
     6040,9780,9781,9782,9783,9784,9785,9786},/* 国会(JEC)@10 */
     {1,
     6041},/* 延森(Jensen)@10 */
     {1,
     6043},/* 卡普什(Kapsch)@10 */
     {8,
     6044,9780,9781,9782,9783,9784,9785,9786},/* 剑道(Kendo)@10 */
     {1,
     6045},/*Kneissel(Kneissel)@10 */
     {1,
     6046},/* 柯冈(Kogan)@10 */
     {7,
     6047,6048,6049,6050,6051,6052,6053},/* 科林(kolin)@10 */
      {10,
     10801,10802,10803,10516,10517,10520,10521,10522,10523,6060},/* 联想(Lenovo)@10 */
     {1,
     6061},/*Leyco(Leyco)@10 */
     {6,
     6062,6063,6064,6065,6066,6067},/* 乐金(LG)@10 */
     {5,
     6068,6069,6070,6071,6072},/* 人生(Life)@10 */
     {19,
     6073,6074,6075,6076,6077,6078,6079,6080,6081,6082,6083,6084,6085,6086,6087,6088,6089,6090,6091},/*Lifetec(Lifetec)@10 */
     {8,
     6092,9780,9781,9782,9783,9784,9785,9786},/* 林恩(Linn)@10 */
     {8,
     6093,9780,9781,9782,9783,9784,9785,9786},/* 洛伊(Loewe)@10 */
     {2,
     6094,6095},/* 搜集(Logik)@10 */
     {2,
     6096,6097},/* 热塑光阀(Lumatron)@10 */
     {2,
     6098,6099},/* 卢克索(Luxor)@10 */
     {3,
     6100,6101,6102},/*MEIectronic(MEIectronic)@10 */
     {3,
     6103,6104,6105},/*Maganavox(Maganavox)@10 */
     {9,
     6106,6107,9780,9781,9782,9783,9784,9785,9786},/*Magnasonic(Magnasonic)@10 */
     {21,
     6108,6109,6110,6111,6112,6113,6114,6115,6116,10470,10471,10472,10473,10474,10475,10476,10477,10478,10479,10480,10481},/* 沃克斯(Magnavox)@10 */
     {5,
     6117,6118,6119,6120,6121},/* 大酒瓶(Magnum)@10 */
     {8,
     6122,9780,9781,9782,9783,9784,9785,9786},/* 门多尔(Mandor)@10 */
     {1,
     6123},/*Manesth(Manesth)@10 */
     {8,
     6124,9780,9781,9782,9783,9784,9785,9786},/* 马兰士(Marantz)@10 */
     {8,
     6125,9780,9781,9782,9783,9784,9785,9786},/* 马尔塔(Marta)@10 */
     {8,
     6126,6127,6128,6129,6130,6131,6132,6133},/* 松井(Matsui)@10 */
     {22,
     6134,6135,6136,6137,6138,6139,6140,6141,6142,6143,6144,6145,6146,6147,6148,6149,6150,6151,6152,6153,6154,6155},/* 中位数(Medion)@10 */
     {6,
     6156,6157,10482,10483,10484,10485},/* 梅莫雷克斯(Memorex)@10 */
     {8,
     6158,9780,9781,9782,9783,9784,9785,9786},/* 梅斯(Metz)@10 */
     {16,
     6159,6160,6161,6162,6163,6164,6165,6166,6167,6168,6169,6170,6171,6172,6173,6174},/*Micromaxx(Micromaxx)@10 */
     {6,
     6175,6176,6177,6178,6179,6180},/* 微软之星(Microstar)@10 */
     {6,
     6181,9900,9901,9902,9903,9904},/*Mikomi(Mikomi)@10 */
     {6,
     6182,9972,9973,9974,9975,9976},/* 密涅瓦(Minerva)@10 */
     {2,
     6183,6184},/*Mintek(Mintek)@10 */
     {6,
     6189,9900,9901,9902,9903,9904},/* 墨菲(Murphy)@10 */
     {19,
     6852,6853,6854,6855,6856,6857,6858,6859,6860,6861,6862,6863,6864,6865,6866,6190,6191,6192,6193},/* 日本电气(Nec)@10 */
     {2,
     6194,6195},/* 耐卡尔曼(Neckermann)@10 */
     {6,
     6196,9900,9901,9902,9903,9904},/* 内蒙古(Nei)@10 */
     {7,
     6197,6198,9972,9973,9974,9975,9976},/* 纽带(Nexus)@10 */
     {6,
     6199,9972,9973,9974,9975,9976},/* 日本开闭器工业公司(Nikkai)@10 */
     {2,
     6200,6201},/* 诺基亚(Nokia)@10 */
     {2,
     8208,6202},/* 宏盛(Norcent)@10 */
     {6,
     6203,9900,9901,9902,9903,9904},/* 奥卡诺河(Okano)@10 */
     {6,
     6204,9972,9973,9974,9975,9976},/* 冲电气工业公司(Oki)@10 */
     {6,
     8526,8527,8202,6205,6206,6207},/* 奥利维亚(OLEVIA)@10 */
     {6,
     6209,9900,9901,9902,9903,9904},/* 擎天柱(Optimus)@10 */
      {6,
     6210,9972,9973,9974,9975,9976},/* 奥图码(Optoma)@10 */
     {10,
     6211,6212,6213,6214,6215,6216,6217,6218,6219,6220},/* 猎户座(Orion)@10 */
     {6,
     6221,9972,9973,9974,9975,9976},/*  大崎(Osaki)@10 */
     {7,
     6222,6223,9900,9901,9902,9903,9904},/* 奥托邮购(Otto Versand)@10 */
     {6,
     6224,9972,9973,9974,9975,9976},/* 帕卡德-贝尔(Packard bell)@10 */
     {6,
     6228,9900,9901,9902,9903,9904},/*Pantel(Pantel)@10 */
     {6,
     6229,7073,7074,7075,7076,7077},/* 百代马可尼(Pathe Marconi)@10 */
     {6,
     6230,9972,9973,9974,9975,9976},/* 题(Perido)@10 */
     {6,
     6240,9900,9901,9902,9903,9904},/* 宝丽来(Polaroid)@10 */
     {21,
     6241,6242,6243,6244,6245,6246,6247,6248,6249,6250,6251,6252,6253,6254,6255,6256,6257,6258,6259,6260,6261},/* 空间(Pro2)@10 */
     {1,
     6262,6263},/*Proscan(Proscan)@10 */
     {3,
     6264,6265,6266},/* 天鸿(Prosonic)@10 */
     {6,
     6267,9900,9901,9902,9903,9904},/*Protech(Protech)@10 */
     {6,
     6268,9972,9973,9974,9975,9976},/* 派伊(Pye)@10 */
     {5,
     6269,6270,6271,6272,6273},/* 奎尔(Quelle)@10 */
     {1,
     6274},/*Questa(Questa)@10 */
     {6,
     6275,7073,7074,7075,7076,7077},/*Radioette(Radioette)@10 */
     {6,
     6276,9972,9973,9974,9975,9976},/* 铺子(RadioShack)@10 */
     {1,
     6277},/*Radiotone(Radiotone)@10 */
     {6,
     6278,7073,7074,7075,7076,7077},/* 雷迪克斯(Radix)@10 */
      {1,
     6279},/* 兰克(Rank)@10 */
     {6,
     6280,9972,9973,9974,9975,9976},/* 国际化(RBM)@10 */
     {3,
     6281,6282,6283},/* 无线电(RCA)@10 */
     {4,
     6284,6285,6286,6287},/* 现实(Realistic)@10 */
     {6,
     6288,7073,7074,7075,7076,7077},/* 射频变压器(RFT)@10 */
     {1,
     6289},/* 敞篷(Roadstar)@10 */
     {2,
     6291,6292},/*Saisho(Saisho)@10 */
     {6,
     6293,9972,9973,9974,9975,9976},/* 酒井(SAKAI SIO)@10 */
     {2,
     6294,6295},/*Salora(Salora)@10 */
     {6,
     6308,7073,7074,7075,7076,7077},/* 西门子(Siemens)@10 */
      {6,
     6309,9972,9973,9974,9975,9976},/* 锡尔弗(Silver)@10 */
     {6,
     6310,7073,7074,7075,7076,7077},/* 冠(Silvercrest)@10 */
     {1,
     6311},/*Sinudyne(Sinudyne)@10 */
     {6,
     6318,9972,9973,9974,9975,9976},/*Solavox(Solavox)@10 */
     {6,
     6319,9972,9973,9974,9975,9976},/*sonitron(sonitron)@10 */
     {1,
     6320},/*Sonolor(Sonolor)@10 */
     {6,
     6323,10630,10631,10632,10633,10634},/* 声音与视觉(Sound&Vision)@10 */
     {7,
     6324,6325,6326,6327,6328,6329,6330},/* 索约(Soyo)@10 */
     {1,
     6331},/* 斯坦达德(Standard)@10 */
     {6,
     6332,7073,7074,7075,7076,7077},/*Sunbright(Sunbright)@10 */
      {1,
     6333},/* 西尔韦尼亚(Sylvania)@10 */
     {6,
     6334,7073,7074,7075,7076,7077},/* 交响乐(Symphonic)@10 */
     {7,
     6335,6336,6337,6338,6339,6340,6341},/* 切分(Synco)@10 */
     {6,
     6342,7073,7074,7075,7076,7077},/* 语法(Syntax)@10 */
     {6,
     6343,9972,9973,9974,9975,9976},/* 语法奥丽维亚(Syntax Olevia)@10 */
     {6,
     6344,7073,7074,7075,7076,7077},/* 康迪(Tandy)@10 */
     {2,
     6345,6346},/*Tashiko(Tashiko)@10 */
     {9,
     6347,6348,6349,6350,6351,9960,9961,9962,9963},/* 大同(Tatung)@10 */
     {16,
     6354,6355,6356,6357,6358,6359,6360,6361,6362,6363,6364,6365,6366,6367,6368,6369},/* 中医(TCM)@10 */
      {6,
     6370,9972,9973,9974,9975,9976},/*Technika(Technika)@10 */
     {6,
     6371,10630,10631,10632,10633,10634},/*Techwood(Techwood)@10 */
     {11,
     6372,6373,6374,6375,6376,6377,6378,6379,6380,6381,6382},/* 东元(Teco)@10 */
     {7,
     6383,6384,9972,9973,9974,9975,9976},/*Telavia(Telavia)@10 */
     {4,
     6385,6386,6387,6388},/* 德律风根(Telefunken)@10 */
     {2,
     6389,6390},/*Teletech(Teletech)@10 */
     {6,
     6391,10630,10631,10632,10633,10634},/* 天际(Tensai)@10 */
     {17,
     6392,6393,6394,6395,6396,6397,6398,6399,6400,6401,6402,6403,6404,6405,6406,6407,6408},/*Tevion(Tevion)@10 */
     {7,
     6409,6410,6411,6412,6413,6414,6415},/* 汤姆逊(THOMSON)@10 */
     {4,
     6416,6417,6418,6419},/* 棘丛(Thorn)@10 */
     {1,
     6443},/*Trutech(Trutech)@10 */
     {2,
     6444,6445},/* 博士(Uher)@10 */
     {1,
     6446},/* 联华(Umc)@10 */
     {6,
     6447,7073,7074,7075,7076,7077},/* 联合(United)@10 */
     {4,
     6448,6449,6450,6451},/* 优信咨询(Universum)@10 */
     {1,
     6452},/*Vlnc(Vlnc)@10 */
     {1,
     6453},/* 载体研究(Vector Research)@10 */
     {6,
     6454,6455,6456,6457,6458,6459},/* 等(Vestel)@10 */
     {6,
     6460,7073,7074,7075,7076,7077},/*Vexa(Vexa)@10 */
     {2,
     6461,6462},/* 维克托(Victor)@10 */
     {1,
     6463},/* 视频的概念(Video Concepts)@10 */
     {1,
     6464},/*Videon(Videon)@10 */
      {8,
     6465,6466,6467,6468,6469,6470,6471,6472},/* 优派(ViewSonic)@10 */
     {3,
     6473,6474,6475},/*Viore(Viore)@10 */
     {1,
     6476},/* 幻想探索(Vision Quest)@10 */
     {6,
     6477,7073,7074,7075,7076,7077},/* 景新(VITO)@10 */
     {9,
     6478,6479,10296,10297,10298,10299,10300,10301,10302},/* 瑞旭(Vizio)@10 */
     {2,
     6480,6481},/*Vortec(Vortec)@10 */
     {1,
     6482},/*Voxson(Voxson)@10 */
     {1,
     6483},/* 华森(Waltham)@10 */
     {6,
     6484,7073,7074,7075,7076,7077},/* 沃森(Watson)@10 */
     {1,
     6485},/* 维嘉(Wega)@10 */
     {1,
     6486},/*Weltblick(Weltblick)@10 */
     {5,
     8541,6487,6488,6489,6490},/* 西屋(Westinghouse)@10 */
     {2,
     6491,6492},/* 约科(Yoko)@10 */
     {1,
     6493},/* 扎努西(Zanussi)@10 */
     {3,
     6494,6495,6496},/* 真利时(Zenith)@10 */
     {1,
     6497},/*Zinwell(Zinwell)@10 */
     {6,
     6499,6500,6501,6502,6503,9283},/* A.R.SYSTEMS(A.R.SYSTEMS)@11 */
     {2,
     6505,6506},/* 极品(ACURA)@12 */
     {8,
   6507,6508,6509,6510,10384,10385,10386,10387},/* 阿德米拉(ADMIRA)@13 */
   {3,
   6511,6512,6513},/* AGEF(AGEF)@12 */
   {6,
   9301,6520,6521,6522,6523,6524},/* 目的(AIM)@12 */
   {1,
   6545},/* 阿吉(AKIB)@12 */
   {4,
   9306,6547,6548,6549},/* 普联(AKITO)@12 */
   {6,
   6561,10630,10631,10632,10633,10634},/* 阿拉伦(ALARON)@12 */
   {1,
   6576},/* ALLSAR(ALLSAR)@12 */
   {3,
   9311,6577,6578},/* 奥园(ALLSTAR)@12 */
   {3,
   6579,6580,6581},/* 阿尔瓦(ALWA)@12 */
   {6,
   6582,7073,7074,7075,7076,7077},/* 大使(AMBASSADOR)@12 */
   {3,
   6583,6584,6585},/* AMPLIVISION(AMPLIVISION)@12 */
   {3,
   6598,6599,6600},/* 该国家(ANAM NATIONAL)@12 */
   {6,
   9327,6608,6609,6610,6611,6612},/* 艾森贝格(ASBERG)@12 */
   {4,
   8203,8204,8205,9595},/*易美逊(Envision)@12 */
   {2,
   8206,8207},/*国科光电(Guoke photoelectric)@12 */
   {39,
   8296,8297,8298,8299,8300,8301,8302,8303,9129,9130,9131,9132,9133,9134,9995,9996,9997,9998,9999,
   10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019},/*东宝(Dongbao)@12 */
   {8,
   8395,8396,8397,8398,9718,9719,9720,9721},/*奥林比亚(Olympia)@12 */
   {11,
   8456,10527,10528,10529,10530,10531,10532,10533,10534,10535,10536},/*DRTRON(DRTRON)@12 */
   {2,
   8457,9389},/*京东方(BOE)@12 */
   {8,
   8533,10493,10494,10495,10496,10497,10498,10499},/*惠科(Huike)@12 */
   {4,
   8534,8535,8536,8537},/*惠浦(HPC)@12 */
   {11,
   8538,10527,10528,10529,10530,10531,10532,10533,10534,10535,10536},/*苹果(Appe)@12 */
   {7,
   8539,8540,10630,10631,10632,10633,10634},/*瑞轩科技(AMTRAN)@12 */
   {8,
   8544,10493,10494,10495,10496,10497,10498,10499},/*PROMAC(PROMAC)@12 */
   {6,
   8545,10630,10631,10632,10633,10634},/*威斯达(VISTAR)@12 */
   {11,
   8782,10527,10528,10529,10530,10531,10532,10533,10534,10535,10536},/*佳能(Canon)@12 */
   {11,
   8870,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509},/*佳美(Camry)@12 */
   {12,
   9058,9059,10527,10528,10529,10530,10531,10532,10533,10534,10535,10536},/*步步高(step by step)@12 */
   {9,
   9280,9281,10493,10494,10495,10496,10497,10498,10499},/*3B科技(technology)@12 */
   {1,
   9282},/*3S数字(Digital)@12 */
   {11,
   9284,10527,10528,10529,10530,10531,10532,10533,10534,10535,10536},/*AlDisplay(AlDisplay)@12 */
   {7,
   9285,10310,10311,10312,10313,10314,10315},/*AcceleVision(AcceleVision)@12 */
   {11,
   9286,10527,10528,10529,10530,10531,10532,10533,10534,10535,10536},/*口音(Accent)@12 */
   {7,
   9287,10310,10311,10312,10313,10314,10315},/*Accurian(Accurian)@12 */
   {1,
   9288},/*高画质(AccuView)@12 */
   {8,
   9289,10493,10494,10495,10496,10497,10498,10499},/*王牌(ACE)@12 */
   {7,
   9291,10310,10311,10312,10313,10314,10315},/*声解(Acoustic Solutions)@12 */
   {1,
   9292},/*作用(Action)@12 */
   {7,
   9293,10310,10311,10312,10313,10314,10315},/*广告(Ad Notam)@12 */
   {8,
   9294,10493,10494,10495,10496,10497,10498,10499},/*艾迪生(Addison)@12 */
   {7,
   9295,10310,10311,10312,10313,10314,10315},/*ADL(ADL)@12 */
   {11,
   9297,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509},/*ADT(ADT)@12 */
   {11,
   9298,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*通用电力(AEG)@12 */
   {7,
   9299,10310,10311,10312,10313,10314,10315},/*该公司(Aftron)@12 */
   {6,
   9303,10630,10631,10632,10633,10634},/*明石(Akashi)@12 */
   {8,
   9305,10493,10494,10495,10496,10497,10498,10499},/*阿基拉(Akira)@12 */
   {7,
   9309,10310,10311,10312,10313,10314,10315},/*青云(Albatron)@12 */
   {8,
   9310,10493,10494,10495,10496,10497,10498,10499},/*外星(Alien)@12 */
   {11,
   9314,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*安南(Anam)@12 */
   {11,
   9315,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509},/*安南地区(Anam Nationnal)@12 */
   {8,
   9316,10493,10494,10495,10496,10497,10498,10499},/*安德松(Andersson)@12 */
   {7,
   9318,10310,10311,10312,10313,10314,10315},/*跨音速(Ansonic)@12 */
   {6,
   9321,10630,10631,10632,10633,10634},/*Aolonpiya(Aolonpiya)@12 */
   {8,
   9322,10493,10494,10495,10496,10497,10498,10499},/*先端数字(Apex Digital)@12 */
   {7,
   9323,10310,10311,10312,10313,10314,10315},/*Ardem(Ardem)@12 */
   {8,
   9324,10493,10494,10495,10496,10497,10498,10499},/*阿里纳(Arena)@12 */
   {11,
   9325,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*阿里斯顿(Aristona)@12 */
   {11,
   9326,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509},/*艺术(ART)@12 */
   {7,
   9328,10310,10311,10312,10313,10314,10315},/*阿斯特拉(Astra)@12 */
   {1,
   9329},/*ATD(ATD)@12 */
   {11,
   9331,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*Audioworld(Audioworld)@12 */
   {7,
   9332,10310,10311,10312,10313,10314,10315},/*阿文图拉(Aventura)@12 */
   {1,
   9333},/*阿波(Awa)@12 */
   {8,
   9334,10493,10494,10495,10496,10497,10498,10499},/*Axxon(Axxon)@12 */
   {11,
   9344,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509},/*巴可(Barco)@12 */
   {2,
   9345,9346},/*基本路线(Basic Line)@12 */
   {7,
   9347,9348,7073,7074,7075,7076,7077},/*保尔(Baur)@12 */
   {1,
   9349},/*Baysonic(Baysonic)@12 */
   {11,
   9350,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*Beaumark(Beaumark)@12 */
   {11,
   9372,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509},/*贝尔森(Belson)@12 */
   {8,
   9373,10493,10494,10495,10496,10497,10498,10499},/*博尔斯达(Belstar)@12 */
   {11,
   9375,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*在(Beon)@12 */
   {11,
   9376,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509},/*Berthen(Berthen)@12 */
   {7,
   9382,10310,10311,10312,10313,10314,10315},/*蓝宝(Blaupunkt)@12 */
   {5,
   9383,9384,9385,9386,9387},/*蓝天(Blue Sky)@12 */
   {11,
   9388,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*博卡(Boca)@12 */
   {11,
   9390,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*博克(Bork)@12 */
   {13,
   9391,9392,9393,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*合伙(BPL)@12 */
   {2,
   9397,9398},/*Brikmann(Brikmann)@12 */
   {1,
   9399},/*Brionvega(Brionvega)@12 */
   {9,
   9407,9408,10493,10494,10495,10496,10497,10498,10499},/*赛百纳(Ctech)@12 */
   {11,
   9412,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*采诗(Caishi)@12 */
   {11,
   9414,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*卡梅伦(Cameron)@12 */
   {1,
   9415},/*露营(Camper)@12 */
   {8,
   9416,10493,10494,10495,10496,10497,10498,10499},/*加拿大(Canca)@12 */
   {2,
   9417,9418},/*Carad(Carad)@12 */
   {7,
   9419,10310,10311,10312,10313,10314,10315},/*卡伦纳(Carena)@12 */
   {11,
   9421,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*叶栅(Cascade)@12 */
   {18,
   10976,10696,10690,10691,10692,10693,10694,10695,10697,
   9422,9972,9973,9974,9975,9976,9977,9978,9979},/*卡西欧(Casio)@12 */
   {7,
   9423,10310,10311,10312,10313,10314,10315},/*国泰(Cathay)@12 */
   {11,
   9425,10547,10548,10549,10550,10551,10552,10553,10554,10555,10556},/*名人(Celebrity)@12 */
   {2,
   9426,9427},/*天体(Celestial)@12 */
   {8,
   9428,10493,10494,10495,10496,10497,10498,10499},/*Centrex(Centrex)@12 */
   {9,
   9430,9972,9973,9974,9975,9976,9977,9978,9979},/*Centrurion(Centrurion)@12 */
   {3,
   9465,9466,9467},/*春云(Chun Yun)@12 */
   {9,
   9472,9972,9973,9974,9975,9976,9977,9978,9979},/*中兴(Chung Hsin)@12 */
   {6,
   9477,10498,10499,10770,10771,10233},/*Clarivox(Clarivox)@12 */
   {11,
   9479,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*克莱顿(Clayton)@12 */
   {6,
   9482,7073,7074,7075,7076,7077},/*商业方(Commercial Solutions)@12 */
   {3,
   9484,9485,9486},/*康尼亚(Conia)@12 */
   {8,
   9505,10493,10494,10495,10496,10497,10498,10499},/*Cosmel(Cosmel)@12 */
   {6,
   9506,10498,10499,10770,10771,10233},/*克罗斯利(Crosley)@12 */
   {9,
   9512,9972,9973,9974,9975,9976,9977,9978,9979},/*霍乱(CTX)@12 */
   {2,
   9513,9514},/*柯蒂斯马特斯(Curtis Mathes)@12 */
   {9,
   9515,9972,9973,9974,9975,9976,9977,9978,9979},/*DVision(DVision)@12 */
   {2,
   9531,9532},/*Dansai(Dansai)@12 */
   {6,
   9535,10498,10499,10770,10771,10233},/*Datsura(Datsura)@12 */
   {11,
   9536,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*达瓦(Dawa)@12 */
   {11,
   9537,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*Daytron(Daytron)@12 */
   {1,
   9539},/*DEC(DEC)@12 */
   {11,
   9541,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*丹佛(Denver)@12 */
   {6,
   9542,10498,10499,10770,10771,10233},/*迪斯美特(Desmet)@12 */
   {4,
   9543,9544,9545,9546},/*钻石(Diamond)@12 */
   {12,
   9547,9548,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*迪克史密斯电(Dick Smith Elec)@12 */
   {11,
   9549,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*Digatron(Digatron)@12 */
   {11,
   9550,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*Digihome(Digihome)@12 */
   {2,
   9551,9552},/*Digiline(Digiline)@12 */
   {6,
   9553,10498,10499,10770,10771,10233},/*笛极玛(DigiMax)@12 */
   {11,
   9554,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*数字生活(Digital Life)@12 */
   {11,
   9555,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*Digitex(Digitex)@12 */
   {2,
   9559,9560},/*消旋(DL)@12 */
   {6,
   9561,10498,10499,10770,10771,10233},/*Domeos(Domeos)@12 */
   {6,
   9566,7073,7074,7075,7076,7077},/*东菱(Dongling)@12 */
   {11,
   9567,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*梦想(Drean)@12 */
   {2,
   9568,9569},/*试验(DSE)@12 */
   {4,
   9571,9572,9573,9574},/*Durabrand(Durabrand)@12 */
   {11,
   9575,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*达克斯(Dux)@12 */
   {6,
   9576,10498,10499,10770,10771,10233},/*负阻(Dynatron)@12 */
   {8,
   9578,10493,10494,10495,10496,10497,10498,10499},/*荷子(Dyon)@12 */
   {11,
   9579,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*欧洲(ECE)@12 */
   {8,
   9580,10493,10494,10495,10496,10497,10498,10499},/*易北河(Elbe)@12 */
   {11,
   9581,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*Electroband(Electroband)@12 */
   {8,
   9584,10493,10494,10495,10496,10497,10498,10499},/*艾丽卡(Elektra)@12 */
   {6,
   9585,10498,10499,10770,10771,10233},/*Elfunk(Elfunk)@12 */
   {1,
   9586},/*埃尔格(ELG)@12 */
   {11,
   9588,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*精英(Elite)@12 */
   {6,
   9596,7073,7074,7075,7076,7077},/*恩泽(Enzer)@12 */
   {6,
   9597,10498,10499,10770,10771,10233},/*Erres(Erres)@12 */
   {2,
   9598,9599},/*欧空局(ESA)@12 */
   {1,
   9600},/*ESC(ESC)@12 */
   {1,
   9601,7073,7074,7075,7076,7077},/*智邦(Etron)*@12 */
   {11,
   9602,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*主角(Euroman)@12 */
   {6,
   9603,10498,10499,10770,10771,10233},/*Euroopa(Euroopa)@12 */
   {1,
   9604},/*Eurphon(Eurphon)@12 */
   {11,
   9605,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*伊夫舍姆(Evesham)@12 */
   {6,
   9606,10498,10499,10770,10771,10233},/*演化(Evolution)@12 */
   {11,
   9607,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*艾克(Excello)@12 */
   {11,
   9608,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*别致(Exquisit)@12 */
   {4,
   9614,9615,9616,9617},/*弗格森爵士(Ferguson)@12 */
   {7,
   9618,9619,10498,10499,10770,10771,10233},/*富达(Fidelity)@12 */
   {5,
   9622,9623,9624,9625,9626},/*道(Firstline)@12 */
   {11,
   9630,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233},/*Fraba(Fraba)@12 */
   {8,
   9631,10493,10494,10495,10496,10497,10498,10499},/*Friac(Friac)@12 */
   {11,
   9642,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*富士通西门子(Fujitsu Siemens)@12 */
   {1,
   9649},/*富瑞驰(Furichi)@12 */
   {6,
   9650,10500,10501,10502,10503,10504},/*该(Futronic)@12 */
   {11,
   9651,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*GHanz(GHanz)@12 */
   {8,
   9652,10493,10494,10495,10496,10497,10498,10499},/*氨基丁酸(Gaba)@12 */
   {1,
   9653},/*加拉克塞(Galaxi)@12 */
   {6,
   9654,10500,10501,10502,10503,10504},/*星际总动员(Galaxis)@12 */
   {5,
   9661,9662,9663,9664,9665},/*通用电气(GE)@12 */
   {11,
   9667,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*创世纪(Genesis)@12*/
   {11,
   9668,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*Genexxa(Genexxa)@12 */
   {11,
   9669,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*Gericom(Gericom)@12 */
   {1,
   9670},/*金融市场(GFM)@12 */
   {6,
   9671,10500,10501,10502,10503,10504},/*Goldfunk(Goldfunk)@12 */
   {11,
   9674,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*金币(GP)@12 */
   {21,
   10640,10641,10642,10643,10644,10645,10646,10647,10648,10649,
   9675,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*Gradiente(Gradiente)@12 */
   {2,
   9676,9677},/*格雷茨(Graetz)@12 */
   {11,
   9686,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*Grunkel(Grunkel)@12 */
   {6,
   9687,10500,10501,10502,10503,10504},/*日内瓦(GVA)@12 */
   {1,
   9688},/*Haaz(Haaz)@12 */
   {8,
   9690,10493,10494,10495,10496,10497,10498,10499},/*标志(Hallmark)@12 */
   {11,
   9691,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*韩泰(Hankook)@12 */
   {1,
   9694},/*Hantor(Hantor)@12 */
   {11,
   9695,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*哈伍德(Harwood)@12 */
   {6,
   9696,10500,10501,10502,10503,10504},/*霍波格(Hauppauge)@12 */
   {1,
   9699},/*海兰(Highline)@12 */
   {11,
   9729,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*奥克斯(AUX)@12 */
   {6,
   9735,9736,9737,9738,9739,9740},/*宝花视(Bao spend)@12 */
   {11,
   9994,10500,10501,10502,10510,10511,10512,10513,10514,10515,10516},/*鼎科(Ding branch)@12 */
   {6,
   10232,10500,10501,10502,10503,10504},/*黃河(The Yellow River)@12 */
   {11,
   10399,10550,10551,10552,10553,10554,10555,10556,10557,10558,10559},/*柯蒂斯(curtis)@12 */
   {6,
   10443,10500,10501,10502,10503,10504},/*帕卡德(Packard)@12 */
   /*=====================================2014/12/18 11:59=================================================*/
   {6,10580,10581,10582,10583,10584,10585},/*Amstrad(Amstrad)@6+(+10*/
	{4,10586,10587,10588,10589,10590},/*Apex(Apex)@6+(+10*/
	{18,10871,10872,10873,10874,10875,10876,10877,10878,10879,10591,10592,10593,10594,10595,10596,10597,10598,10599},/*Astro(Astro)@6+(+10*/
	{4,10880,10600,10601,10602},/*Asuka(Asuka)@6+(+10*/
	{2,10881,10882,10600,10601,10602},/*贝灵巧(Bei Lingqiao)@6+(+10*/
	{5,10883,10603,10604,10605,10606},/*波特兰(Portland)@6+(+10*/
	{5,10887,10604,10605,10606,10607},/*丹尼克斯(Dan Nikos)@6+(+10*/
	{10,10888,10889,10890,10891,10608,10609,10610,10611,10612,10613},/*丹特声(Dante Sound)@6+(+10*/
	{9,10892,10893,10612,10613,10607,10608,10609,10610,10611},/*飞歌(Song)@6+(+10*/
	{13,10894,10895,10896,10897,10898,10614,10615,10616,10617,10618,10619,10620,10621},/*Fisher (Fisher)@6+(+10*/
	{11,10899,10900,10901,10619,10620,10621,10614,10615,10616,10617,10618},/*富奈(Funai)@6+(+10*/
	{6,10902,10622,10623,10624,10625,10626},/*富士通先端(Fujitsu Apex)@6+(+10*/
	{9,10903,10904,10905,10906,10623,10624,10625,10626,10622},/*高飞(Goofy)@6+(+10*/
	{14,10907,10908,10909,10910,10911,10912,10913,10627,10628,10629,10630,10631,10632,10633},/*GE(GE)@6+(+10*/
	{20,10914,10915,10916,10917,10918,10919,10920,10921,10922,10923,10634,10635,10636,10637,10638,10639,10640,10641,10642,10643},/*歌林(Song Lin)@6+(+10*/
	{7,10924,10644,10645,10646,10647,10648,10649},/*广东广电网络(Guangdong Radio And TV Network)@6+(+10*/
	{11,10925,10926,10927,10928,10929,10645,10646,10647,10648,10649,10644},/*海乐(Haile)@6+(+10*/
	{8,10930,10931,10650,10651,10652,10653,10654,10655},/*佳彩(Good Luck)@6+(+10*/
	{10,10932,10933,10934,10935,10652,10653,10654,10655,10650,10651},/*捷威(Gateway)@6+(+10*/
	{5,10941,10661,10662,10663,10664},/*集品(Set Pproduct)@6+(+10*/
	{5,10942,10661,10662,10663,10664},/*九联(Jiulian)@6+(+10*/
	{6,10943,10944,10663,10664,10661,10662},/*JNC (JNC)@6+(+10*/
	{8,11052,10945,10946,10665,10666,10667,10668,10669},/*乐视(Music)@6+(+10*/
	{6,10947,10665,10666,10667,10668,10669},/*丽讯(Vivitek)@6+(+10*/
	{7,10948,10949,10668,10669,10665,10666,10667},/*摩托罗拉(Motorola)@6+(+10*/
	{13,10950,10951,10670,10671,10672,10673,10674,10675,10676,10677,10678,10679,10680},/*NAD(NAD)@6+(+10*/
	{12,10952,10671,10672,10673,10674,10675,10676,10677,10678,10679,10680,10670},/*NEC(NEC)@6+(+10*/
	{19,10953,10954,10955,10956,10957,10958,10959,10960,10673,10674,10675,10676,10677,10678,10679,10680,10670,10671,10672},/*OLEVIA (OLEVIA)@6+(+10*/
	{18,10961,10962,10963,10964,10965,10966,10967,10968,10969,10681,10682,10683,10684,10685,10686,10687,10688,10689},/*欧迪福斯(Audiovox)@6+(+10*/
	{12,10970,10971,10972,10973,10690,10691,10692,10693,10694,10695,10696,10697},/*讴歌(Eulogize)@6+(+10*/
	{10,10974,10975,10694,10695,10690,10691,10692,10693,10696,10697},/*普腾(Proton)@6+(+10*/
	{9,10977,10697,10690,10691,10692,10693,10694,10695,10696},/*Quasar(Quasar)@6+(+10*/
	{13,10978,10979,10698,10699,10700,10701,10702,10703,10704,10705,10706,10707,10708,10709},/*RCA(RCA)@6+(+10*/
	{12,10980,10698,10699,10700,10701,10702,10703,10704,10705,10706,10707,10708,10709},/*RFT(RFT)@6+(+10*/
	{12,10981,10698,10699,10700,10701,10702,10703,10704,10705,10706,10707,10708,10709},/*日高(NIKKO)@6+(+10*/
	{12,10982,10702,10698,10699,10700,10701,10703,10704,10705,10706,10707,10708,10709},/*睿侠(Rui Xia)@6+(+10*/
	{13,10983,10984,10703,10704,10698,10699,10700,10701,10702,10705,10706,10707,10708,10709},/*Runco(Runco)@6+(+10*/
	{12,10985,10698,10705,10699,10700,10701,10702,10703,10704,10706,10707,10708,10709},/*Sears (Sears)@6+(+10*/
	{11,10706,10707,10708,10698,10699,10700,10701,10702,10703,10704,10705,10709},/*声光(Acousto)@6+(+10*/
	{12,10989,10709,10698,10699,10700,10701,10702,10703,10704,10705,10706,10707,10708},/*视丽(Shili)@6+(+10*/
	{15,10990,10991,10992,10710,10711,10712,10713,10714,10715,10716,10717,10718,10719,10720,10721},/*施耐德(Schneider)@6+(+10*/
	{13,10993,10713,10710,10711,10712,10714,10715,10716,10717,10718,10719,10720,10721},/*数码星空(Digital Sky)@6+(+10*/
	{18,11002,11003,11004,11005,11006,10722,10723,10724,10725,10726,10727,10728,10729,10730,10731,10732,10733,10734},/*SONYO(SONYO)@6+(+10*/
	{17,11007,11008,11009,11010,10727,10728,10729,10730,10722,10723,10724,10725,10726,10731,10732,10733,10734},/*Synco(Synco)@6+(+10*/
	{17,11011,11012,11013,11014,10731,10732,10733,10734,10722,10723,10724,10725,10726,10727,10728,10729,10730},/*TELEFUNKEN(TELEFUNKEN)@6+(+10*/
	{11,11015,10735,10736,10737,10738,10739,10740,10741,10742,10743,10744},/*腾博(Tang Bo)@6+(+10*/
	{11,11016,10735,10736,10737,10738,10739,10740,10741,10742,10743,10744},/*统帅(Commander In Chief)@6+(+10*/
	{12,11017,11018,10735,10736,10737,10738,10739,10740,10741,10742,10743,10744},/*TOPCON(TOPCON)@6+(+10*/
	{13,11019,11020,11021,10738,10739,10740,10735,10736,10737,10741,10742,10743,10744},/*VDiGi(VDiGi)@6+(+10*/
	{13,11022,11023,11024,10742,10743,10744,10735,10736,10737,10738,10739,10740,10741},/*Victor(Victor)@6+(+10*/
	{12,11025,10745,10746,10747,10748,10749,10750,10751,10752,10753,10754,10755},/*Videocon(Videocon)@6+(+10*/
	{12,11026,10745,10746,10747,10748,10749,10750,10751,10752,10753,10754,10755},/*Viewpia(Viewpia)@6+(+10*/
	{10,11027,10747,10748,10749,10750,10751,10752,10753,10754,10755},/*ViViD(ViViD)@6+(+10*/
	{9,11028,10748,10749,10750,10751,10752,10753,10754,10755},/*万利达(Malata)@6+(+10*/
	{8,11029,10749,10750,10751,10752,10753,10754,10755},/*唯冠(Proview)@6+(+10*/
	{4,11030,10750,10745,10746},/*沃夫德尔(Wolf Del)@6+(+10*/
	{14,11031,11032,11033,10751,10752,10753,10745,10746,10747,10748,10749,10750,10754,10755},/*先科(SAST)@6+(+10*/
	{13,11034,11035,10754,10755,10745,10746,10747,10748,10749,10750,10751,10752,10753},/*西凡尼亚(Sylvania)@6+(+10*/
	{13,11036,10756,10757,10758,10759,10760,10761,10762,10763,10764,10765,10766,10767},/*喜事来(Event To)@6+(+10*/
	{12,11037,10757,10758,10759,10760,10761,10762,10763,10764,10765,10766,10767},/*雅马哈(Yamaha)@6+(+10*/
	{14,11038,11039,10756,10757,10758,10759,10760,10761,10762,10763,10764,10765,10766,10767},/*怡敏信(Yi Minxin)@6+(+10*/
	{12,11040,11041,10758,10759,10760,10761,10762,10763,10764,10765,10766,10767},/*友利电(Uniden)@6+(+10*/
	{9,11042,10760,10761,10762,10763,10764,10765,10766,10767},/*优图(Excellent Map)@6+(+10*/
	{6,11043,10763,10764,10765,10766,10767},/*圆刚(AVerMedia)@6+(+10*/
	{16,11044,11045,11046,11047,10764,10765,10766,10767,10756,10757,10758,10759,10760,10761,10762,10763},/*Zenith (Zenith)@6+(+10*/
	{8,11048,11049,11050,11051,10768,10769,10770,10771},/*兆赫(MHz)@6+(+10*/


														/*2014/12/18 12:01====================================================================当天为其它数据？========*/
   {2,10771,10233},/*爱家乐(Angostura)@6+(+10*/
		{5,10505,10506,10507,10508,10509},/*爱普生(Epson)@6+(+10*/
		{9,10511,10512,10513,10514,10515,10516,10517,10518,10519},/*爱其(Love)@6+(+10*/
		{8,10522,10523,10524,10525,10526,10527,10528,10529},/*Ampro(Ampro)@6+(+10*/
		{7,10533,10534,10535,10536,10537,10538,10539},/*百视通(Bestv)@6+(+10*/
		{10,10540,10541,10542,10543,10544,10545,10546,10547,10548,10549},/*倍科(Beko)@6+(+10*/
		{6,10554,10555,10556,10557,10558,10559},/*朝野(Government)@6+(+10*/
		{10,10560,10561,10562,10563,10564,10565,10566,10567,10568,10569},/*晨星(Morning Star)@6+(+10*/
		{7,10573,10574,10575,10576,10577,10578,10579},/*大亚(Daya)@6+(+10*/
		{10,10580,10581,10582,10583,10584,10585,10586,10587,10588,10589},/*蒂雅克(Teac)@6+(+10*/
		{5,10595,10596,10597,10598,10599},/*Dwin(Dwin)@6+(+10*/
		{10,10600,10601,10602,10603,10604,10605,10606,10607,10608,10609},/*Eight (Eight)@6+(+10*/
		{3,10617,10618,10619},/*飞越(Fly)@6+(+10*/
		{10,10620,10621,10622,10623,10624,10625,10626,10627,10628,10629},/*丰泽(Fortress)@6+(+10*/
		{8,10632,10633,10634,10635,10636,10637,10638,10639},/*GlobeCast(GlobeCast)@6+(+10*/
		{10,10650,10651,10652,10653,10654,10655,10656,10657,10658,10659},/*海美迪(Sea Di)@6+(+10*/
		{5,10665,10666,10667,10668,10669},/*海人草(Tanaka)@6+(+10*/
		{10,10670,10671,10672,10673,10674,10675,10676,10677,10678,10679},/*哈曼卡顿 (Harman Kardon)@6+(+10*/
		{6,10684,10685,10686,10687,10688,10689},/*Hanso(Hanso)@6+(+10*/
		{10,10690,10691,10692,10693,10694,10695,10696,10697,10698,10699},/*HCT(HCT)@6+(+10*/
		{7,10703,10704,10705,10706,10707,10708,10709},/*贺曼(Herman)@6+(+10*/
		{10,10710,10711,10712,10713,10714,10715,10716,10717,10718,10719},/*华生(Wahson)@6+(+10*/
		{8,10722,10723,10724,10725,10726,10727,10728,10729},/*华为(Huawei)@6+(+10*/
		{10,10730,10731,10732,10733,10734,10735,10736,10737,10738,10739},/*i潘多拉(I Pandora)@6+(+10*/
		{9,10741,10742,10743,10744,10745,10746,10747,10748,10749},/*Jerrold(Jerrold)@6+(+10*/
		{10,10750,10751,10752,10753,10754,10755,10756,10757,10758,10759},/*佳美的(Good)@6+(+10*/
		{10,10760,10761,10762,10763,10764,10765,10766,10767,10768,10769},/*江西(Jiangxi)@6+(+10*/
		{10,10293,10294,10295,10296,10297,10298,10299,10490,10491,10492},/*江西有线 (Jiangxi Cable)@6+(+10*/
		{3,10307,10308,10309},/*集成(Integrate)@6+(+10*/
		{10,10310,10311,10312,10313,10314,10315,10316,10317,10318,10319},/*杰科(Jieke)@6+(+10*/
		{10,10320,10321,10322,10323,10324,10325,10326,10327,10328,10329},/*凯擘(KBrO)@6+(+10*/
		{7,10333,10334,10335,10336,10337,10338,10339},/*凯驰(Kai Chi)@6+(+10*/
		{10,10340,10341,10342,10343,10344,10345,10346,10347,10348,10349},/*科朗(Konon)@6+(+10*/
		{10,10350,10351,10352,10353,10354,10355,10356,10357,10358,10359},/*浪潮(Wave)@6+(+10*/
		{5,10365,10366,10367,10368,10369},/*蓝星(LAN-STAR)@6+(+10*/
		{7,10373,10374,10375,10376,10377,10378,10379},/*陇华(Long Hua)@6+(+10*/
		{10,10380,10381,10382,10383,10384,10385,10386,10387,10388,10389},/*罗意威(Loewe)@6+(+10*/
		{10,10390,10391,10392,10393,10394,10395,10396,10397,10398,10399},/*Mag(Mag)@6+(+10*/
		{10,10400,10401,10402,10403,10404,10405,10406,10407,10408,10409},/*梅捷(Soyo)@6+(+10*/
		{10,10410,10411,10412,10413,10414,10415,10416,10417,10418,10419},/*美如画(Picture)@6+(+10*/
		{810422,10423,10424,10425,10426,10427,10428,10429},/*Mod(Mod)@6+(+10*/
		{10,10430,10431,10432,10433,10434,10435,10436,10437,10438,10439},/*盘古(Pangu)@6+(+10*/
		{10,10440,10441,10442,10443,10444,10445,10446,10447,10448,10449},/*pro2(Pro2)@6+(+10*/
		{10,10450,10451,10452,10453,10454,10455,10456,10457,10458,10459},/*清华紫光 (Thunis)@6+(+10*/
		{10,10460,10461,10462,10463,10464,10465,10466,10467,10468,10469},/*清远广电(Qingyuan Radio And Television)@6+(+10*/
		{710473,10474,10475,10476,10477,10478,10479},/*日派(Day School)@6+(+10*/
		{10,10480,10481,10482,10483,10484,10485,10486,10487,10488,10489},/*瑞安广电(Ruian Radio And Television)@6+(+10*/
		{10,10072,10073,10074,10075,10076,10077,10078,10290,10291,10292},/*赛博(Cyber)@6+(+10*/
		{10,10080,10081,10082,10083,10084,10085,10086,10087,10088,10089},/*赛普特(Seibt)@6+(+10*/
		{10,10090,10091,10092,10093,10094,10095,10096,10097,10098,10099},/*萨基姆(Sagem)@6+(+10*/
		{10,10100,10101,10102,10103,10104,10105,10106,10107,10108,10109},/*三元(Three Yuan)@6+(+10*/
		{6,10114,10115,10116,10117,10118,10119},/*Sentra(Sentra)@6+(+10*/
		{10,10120,10121,10122,10123,10124,10125,10126,10127,10128,10129},/*视朗(As Long)@6+(+10*/
		{10,10130,10131,10132,10133,10134,10135,10136,10137,10138,10139},/*索佳(Sokkia)@6+(+10*/
		{10,10140,10141,10142,10143,10144,10145,10146,10147,10148,10149},/*Technics(Technics)@6+(+10*/
		{7,10153,10154,10155,10156,10157,10158,10159},/*同洲(Tongzhou)@6+(+10*/
		{10,10160,10161,10162,10163,10164,10165,10166,10167,10168,10169},/*UT斯达康(UT Starcom)@6+(+10*/
		{10,10170,10171,10172,10173,10174,10175,10176,10177,10178,10179},/*武进(Wujin)@6+(+10*/
		{10,10180,10181,10182,10183,10184,10185,10186,10187,10188,10189},/*现代(Modern)@6+(+10*/
		{8,10192,10193,10194,10195,10196,10197,10198,10199},/*雅俊(Ya Jun)@6+(+10*/
		{10,10200,10201,10202,10203,10204,10205,10206,10207,10208,10209},/*宜家(IKEA)@6+(+10*/
		{10,10210,10211,10212,10213,10214,10215,10216,10217,10218,10219},/*英非克(Yingfeike)@6+(+10*/
		{5,10226,10227,10228,10229},/*影雅(Shadow Ya)@6+(+10*/
		{10,10230,10231,10232,10233,10234,10235,10236,10237,10238,10239},/*银河(Galaxy)@6+(+10*/
		{8,10242,10243,10244,10245,10246,10247,10248,10249},/*优群(Excellent Group)@6+(+10*/
		{10,10250,10251,10252,10253,10254,10255,10256,10257,10258,10259},/*有用(Useful)@6+(+10*/
		{6,10264,10265,10266,10267,10268,10269},/*忠冠(Honest Crown)@6+(+10*/
		{5,10275,10276,10277,10278,10279},/*中国电信(China Telecom)@6+(+10*/
		{7,10283,10284,10285,10286,10287,10288,10289},/*中恒(In The Constant)@6+(+10*/
    {3,10986,10987,10988},/*声光智能科技(Acousto-optic intelligent science and technology)@6+(+10 */

	{2061,
	  10493,10494,10495,10496,10497,10498,10499,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509,10510,10511,10512,
		10513,10514,10515,10516,10517,10518,10519,10520,10521,10522,10523,10524,10525,10526,10527,10528,10529,10530,10531,10532,
		10533,10534,10535,10536,10537,10538,10539,10540,10541,10542,10543,10544,10545,10546,10547,10548,10549,10550,10551,10552,
		10553,10554,10555,10556,10557,10558,10559,10560,10561,10562,10563,10564,10565,10566,10567,10568,10569,10570,10571,10572,
		10573,10574,10575,10576,10577,10578,10579,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233,10500,10501,10502,
		10510,10511,10512,10513,10514,10515,10516,10517,10518,10519,10520,10521,10522,10523,10524,10525,10526,10527,10528,10529,
		10530,10531,10532,10533,10534,10535,10536,10537,10538,10539,10540,10541,10542,10543,10544,10545,10546,10547,10548,10549,
		10550,10551,10552,10553,10554,10555,10556,10557,10558,10559,10560,10561,10562,10563,10564,10565,10566,10567,10568,10569,
		10570,10571,10572,10573,10574,10575,10576,10577,10578,10579,10580,10581,10582,10583,10584,10585,10586,10587,10588,10589,
		10590,10591,10592,10593,10594,10595,10596,10597,10598,10599,10600,10601,10602,10603,10604,10605,10606,10607,10608,10609,
		10610,10611,10612,10613,10614,10615,10616,10617,10618,10619,10620,10621,10622,10623,10624,10625,10626,10627,10628,10629,
		10630,10631,10632,10633,10634,10635,10636,10637,10638,10639,10640,10641,10642,10643,10644,10645,10646,10647,10648,10649,
		10650,10651,10652,10653,10654,10655,10656,10657,10658,10659,10660,10661,10662,10663,10664,10665,10666,10667,10668,10669,
		10670,10671,10672,10673,10674,10675,10676,10677,10678,10679,10680,10681,10682,10683,10684,10685,10686,10687,10688,10689,
		10690,10691,10692,10693,10694,10695,10696,10697,10698,10699,10700,10701,10702,10703,10704,10705,10706,10707,10708,10709,
		10710,10711,10712,10713,10714,10715,10716,10717,10718,10719,10720,10721,10722,10723,10724,10725,10726,10727,10728,10729,
		10730,10731,10732,10733,10734,10735,10736,10737,10738,10739,10740,10741,10742,10743,10744,10745,10746,10747,10748,10749,
		10750,10751,10752,10753,10754,10755,10756,10757,10758,10759,10760,10761,10762,10763,10764,10765,10766,10767,10768,10769,
		10293,10294,10295,10296,10297,10298,10299,10490,10491,10492,10300,10301,10302,10303,10304,10305,10306,10307,10308,10309,
		10310,10311,10312,10313,10314,10315,10316,10317,10318,10319,10320,10321,10322,10323,10324,10325,10326,10327,10328,10329,
		10330,10331,10332,10333,10334,10335,10336,10337,10338,10339,10340,10341,10342,10343,10344,10345,10346,10347,10348,10349,
		10350,10351,10352,10353,10354,10355,10356,10357,10358,10359,10360,10361,10362,10363,10364,10365,10366,10367,10368,10369,
		10370,10371,10372,10373,10374,10375,10376,10377,10378,10379,10380,10381,10382,10383,10384,10385,10386,10387,10388,10389,
		10390,10391,10392,10393,10394,10395,10396,10397,10398,10399,10400,10401,10402,10403,10404,10405,10406,10407,10408,10409,
		10410,10411,10412,10413,10414,10415,10416,10417,10418,10419,10420,10421,10422,10423,10424,10425,10426,10427,10428,10429,
		10430,10431,10432,10433,10434,10435,10436,10437,10438,10439,10440,10441,10442,10443,10444,10445,10446,10447,10448,10449,
		10450,10451,10452,10453,10454,10455,10456,10457,10458,10459,10460,10461,10462,10463,10464,10465,10466,10467,10468,10469,
		10470,10471,10472,10473,10474,10475,10476,10477,10478,10479,10480,10481,10482,10483,10484,10485,10486,10487,10488,10489,
		10072,10073,10074,10075,10076,10077,10078,10290,10291,10292,10080,10081,10082,10083,10084,10085,10086,10087,10088,10089,
		10090,10091,10092,10093,10094,10095,10096,10097,10098,10099,10100,10101,10102,10103,10104,10105,10106,10107,10108,10109,
		10110,10111,10112,10113,10114,10115,10116,10117,10118,10119,10120,10121,10122,10123,10124,10125,10126,10127,10128,10129,
		10130,10131,10132,10133,10134,10135,10136,10137,10138,10139,10140,10141,10142,10143,10144,10145,10146,10147,10148,10149,
		10150,10151,10152,10153,10154,10155,10156,10157,10158,10159,10160,10161,10162,10163,10164,10165,10166,10167,10168,10169,
		10170,10171,10172,10173,10174,10175,10176,10177,10178,10179,10180,10181,10182,10183,10184,10185,10186,10187,10188,10189,
		10190,10191,10192,10193,10194,10195,10196,10197,10198,10199,10200,10201,10202,10203,10204,10205,10206,10207,10208,10209,
		10210,10211,10212,10213,10214,10215,10216,10217,10218,10219,10220,10221,10222,10223,10224,10225,10226,10227,10228,10229,
		10230,10231,10232,10233,10234,10235,10236,10237,10238,10239,10240,10241,10242,10243,10244,10245,10246,10247,10248,10249,
		10250,10251,10252,10253,10254,10255,10256,10257,10258,10259,10260,10261,10262,10263,10264,10265,10266,10267,10268,10269,
		10270,10271,10272,10273,10274,10275,10276,10277,10278,10279,10280,10281,10282,10283,10284,10285,10286,10287,10288,10289,
		10503,10504,10505,10506,10507,10508,10509,9772,9773,9774,9775,9776,9777,9778,9779,10067,10068,10069,10070,10071,10072,
		9780,9781,9782,9783,9784,9785,9786,9787,9788,9789,9790,9791,9792,9793,9794,9795,9796,9797,9798,9799,9800,9801,9802,9803,
		9804,9805,9806,9807,9808,9809,9810,9811,9812,9813,9814,9815,9816,9817,9818,9819,9820,9821,9822,9823,9824,9825,9826,9827,
		9828,9829,9830,9831,9832,9833,9834,9835,9836,9837,9838,9839,9840,9841,9842,9843,9844,9845,9846,9847,9848,9849,9850,9851,
		9852,9853,9854,9855,9856,9857,9858,9859,9860,9861,9862,9863,9864,9865,9866,9867,9868,9869,9870,9871,9872,9873,9874,9875,
		9876,9877,9878,9879,9880,9881,9882,9883,9884,9885,9886,9887,9888,9889,9890,9891,9892,9893,9894,9895,9896,9897,9898,9899,
		9900,9901,9902,9903,9904,9905,9906,9907,9908,9909,9910,9911,9912,9913,9914,9915,9916,9917,9918,9919,9920,9921,9922,9923,
		9924,9925,9926,9927,9928,9929,9930,9931,9932,9933,9934,9935,9936,9937,9938,9939,9940,9941,9942,9943,9944,9945,9946,9947,
		9948,9949,9950,9951,9952,9953,9954,9955,9956,9957,9958,9959,9960,9961,9962,9963,9964,9965,9966,9967,9968,9969,9970,9971,
		9972,9973,9974,9975,9976,9977,9978,9979,9980,9981,9982,9983,9984,9985,9986,9987,9988,9989,9990,9991,9992,9993,9994,9995,
		9996,9997,9998,9999,10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,
		10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,
		10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,
		10057,10058,10059,10060,10061,10062,10063,10064,10065,10066,7061,7062,7063,7064,7065,7066,7067,7068,7069,7070,7071,7072,
		7073,7074,7075,7076,7077,7078,7079,7080,7081,7082,7083,7084,7085,7087,7088,
    7090,7091,7092,7093,7094,7095,7096,7097,7098,7099,7100,7101,7102,7103,7104,7105,7106,7107,7108,7109,7110,7111,7112,7113,7114,7115,7116,
    7117,7118,7119,7120,7121,7122,7123,7124,7125,7126,7127,7128,7129,7130,7131,7132,7133,7134,7135,7136,7137,7138,7139,7140,7141,7142,7143,
    7144,7145,7146,7147,7148,7149,7150,7151,7152,7153,7154,7155,7156,7157,7158,7159,7160,7161,7162,7163,7164,7165,7166,7167,7168,7169,7170,
    7171,7172,7173,7174,7175,7176,7177,7178,7179,7180,7181,7182,7183,7184,7185,7186,7187,7188,7189,7190,7191,7192,7193,7194,7195,7196,7197,
    7198,7199,7200,7201,7202,7203,7204,7205,7206,7207,7208,7209,7210,7211,7212,7213,7214,7215,7216,7217,7218,7219,7220,7221,7222,7223,7224,
    7225,7226,7227,7228,7229,7230,7231,7232,7233,7234,7235,7236,7237,7238,7239,7240,7241,7242,7243,7244,7245,7246,7247,7248,7249,7250,7251,
    7252,7253,7254,7255,7256,7257,7258,7259,7260,7261,7262,7263,7264,7265,7266,7267,7268,7269,7270,7271,7272,7273,7274,7275,7276,7277,7278,
    7279,7280,7281,7282,7283,7284,7285,7286,7287,7288,7289,7290,7291,7292,7293,7294,7295,7296,7297,7298,7299,7300,7301,7302,7303,7304,7305,
    7306,7307,7308,7309,7310,7311,7312,7313,7314,7315,7316,7317,7318,7319,7320,7321,7322,7323,7324,7325,7326,7327,7328,7329,7330,7331,7332,
    7333,7334,7335,7336,7337,7338,7339,7340,7341,7342,7343,7344,7345,7346,7347,7348,7349,7350,7351,7352,7353,7354,7355,7356,7357,7358,7359,
    7360,7361,7362,7363,7364,7365,7366,7367,7368,7369,7370,7371,7372,7373,7374,7375,7376,7377,7378,7379,7380,7381,7382,7383,7384,7385,7386,
    7387,7388,7389,7390,7391,7392,7393,7394,7395,7396,7397,7398,7399,7400,7401,7402,7403,7404,7405,7406,7407,7408,7409,7410,7411,7412,7413,
    7414,7415,7416,7417,7418,7419,7420,7421,7422,7423,7424,7425,7426,7427,7428,7429,7430,7431,7432,7433,7434,7435,7436,7437,7438,7439,7440,
    7441,7442,7443,7444,7445,7446,7447,7448,7449,7450,7451,7452,7453,7454,7455,7456,7457,7458,7459,7460,7461,7462,7463,7464,7465,7466,7467,
    7468,7469,7470,7471,7472,7473,7474,7475,7476,7477,7478,7479,7480,7481,7482,7483,7484,7485,7486,7487,7488,7489,7490,7491,7492,7493,7494,
    7495,7496,7497,7498,7499,7500,7501,7502,7503,7504,7505,7506,7507,7508,7509,7510,7511,7512,7513,1058,1059,1060,1061,1062,1063,
    29,658,684,90,217,5356,5357,5358,5359,5360,5361,5362,5363,5364,5365,5366,5367,5368,5369,5370,5371,5372,5373,5374,5375,
    5376,5377,5378,5379,5380,5381,5382,5383,5384,5385,5386,5387,5388,5389,5390,5391,5392,5393,5394,5395,5396,5397,5398,5399,5400,
    5401,5402,5403,5404,5405,5406,5407,5408,5409,5410,177,263,187,171,190,136,69,147,82,311,202,290,220,264,32,338,70,123,107,95,18,24,66,72,84,
    96,108,142,144,155,191,192,198,210,216,222,228,234,246,282,294,366,430,431,546,553,554,555,557,614,615,616,618,
    621,623,624,625,661,662,663,664,725,749,819,824,828,852,853,854,855,856,865,866,877,882,895,908,929,930,931,932,
    944,1001,1002,1022,1031,556,558,559,560,561,562,563,564,565,566,567,568,569,570,571,572,573,574,575,576,577,580,
    581,582,583,584,585, 586,587,588,589,590,591,592,593,594,594,595,596,597,598,599,600,601,602,603,604,605,606,607,
    608,609,610,611,612,613,619,620,622,626,627,628,629,630,631,632,633,634,635,636,637,641,642,643,644,645,646,647,
    648,649,650, 651,652,653,654,655,656,659,659,660,661,662,663,664,665,666,667,668,669,670,671,672,673,674,675,676,
    677,678,679,680,681,682,683,685,685,686,687,688,689,690,691,692,693,694,695,696,697,698,699,700,701,702,703,704,
    705,706,707,708,709,710,711,712,713,714,715,716,717,718,719,720,721,722,723,724,726,727,728,729,730,731,732,733,
    734,735,736,737,738,739,740,741,742,743,744,745,746,747,748,750,751,752,753,754,755,756,757,758,759,760,761,
    762,763,764,765,766,767,768,769,770,771,772,773,774,775,776,777,778,779,780,781,782,783,784,785,786,787,788,
    789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,
    816,817,818,820,821,822,823,825,826,827,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,
    846,847,848,849,850,851,857,858,859,860,861,862,863,864,867,868,869,870,871,872,873,874,875,876,878,879,879,
    880,881,882,883,884,885,886,887,888,889,890,891,892,893,894,896,897,898,899,900,901,902,903,904,905,906,907,
    909,910,911,912,913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,928,929,930,931,932,933,934,935,
    936,937,938,939,940,941,942,943,944,945,946,947,948,949,950,951,952,953,954,955,956,957,958,959,960,961,962,
    963,964,965,966,967,968,969,970,971,972,973,974,975,976,977,978,979,980,981,982,983,984,985,986,987,988,989,
    990,991,992,993,994,995,996,997,998,999,1000,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,
    1015,1016,1017,1018,1019,1020,1021,1023,1024,1024,1025,1026,1027,1028,1029,1030,1032,1033,1034,1035,1036,
    1037,1038,1039,1040,1041,1042,1043,1044,1045,1046,1047,1048,1049,1050,1051,1052,1053,1054,1055,1056,1057},/*全部(All)@6+(+10*/

		{1088,
		10493,10494,10495,10496,10497,10498,10499,10500,10501,10502,10503,10504,10505,10506,10507,10508,10509,10510,10511,10512,
		10513,10514,10515,10516,10517,10518,10519,10520,10521,10522,10523,10524,10525,10526,10527,10528,10529,10530,10531,10532,
		10533,10534,10535,10536,10537,10538,10539,10540,10541,10542,10543,10544,10545,10546,10547,10548,10549,10550,10551,10552,
		10553,10554,10555,10556,10557,10558,10559,10560,10561,10562,10563,10564,10565,10566,10567,10568,10569,10570,10571,10572,
		10573,10574,10575,10576,10577,10578,10579,10493,10494,10495,10496,10497,10498,10499,10770,10771,10233,10500,10501,10502,
		10510,10511,10512,10513,10514,10515,10516,10517,10518,10519,10520,10521,10522,10523,10524,10525,10526,10527,10528,10529,
		10530,10531,10532,10533,10534,10535,10536,10537,10538,10539,10540,10541,10542,10543,10544,10545,10546,10547,10548,10549,
		10550,10551,10552,10553,10554,10555,10556,10557,10558,10559,10560,10561,10562,10563,10564,10565,10566,10567,10568,10569,
		10570,10571,10572,10573,10574,10575,10576,10577,10578,10579,10580,10581,10582,10583,10584,10585,10586,10587,10588,10589,
		10590,10591,10592,10593,10594,10595,10596,10597,10598,10599,10600,10601,10602,10603,10604,10605,10606,10607,10608,10609,
		10610,10611,10612,10613,10614,10615,10616,10617,10618,10619,10620,10621,10622,10623,10624,10625,10626,10627,10628,10629,
		10630,10631,10632,10633,10634,10635,10636,10637,10638,10639,10640,10641,10642,10643,10644,10645,10646,10647,10648,10649,
		10650,10651,10652,10653,10654,10655,10656,10657,10658,10659,10660,10661,10662,10663,10664,10665,10666,10667,10668,10669,
		10670,10671,10672,10673,10674,10675,10676,10677,10678,10679,10680,10681,10682,10683,10684,10685,10686,10687,10688,10689,
		10690,10691,10692,10693,10694,10695,10696,10697,10698,10699,10700,10701,10702,10703,10704,10705,10706,10707,10708,10709,
		10710,10711,10712,10713,10714,10715,10716,10717,10718,10719,10720,10721,10722,10723,10724,10725,10726,10727,10728,10729,
		10730,10731,10732,10733,10734,10735,10736,10737,10738,10739,10740,10741,10742,10743,10744,10745,10746,10747,10748,10749,
		10750,10751,10752,10753,10754,10755,10756,10757,10758,10759,10760,10761,10762,10763,10764,10765,10766,10767,10768,10769,
		10293,10294,10295,10296,10297,10298,10299,10490,10491,10492,10300,10301,10302,10303,10304,10305,10306,10307,10308,10309,
		10310,10311,10312,10313,10314,10315,10316,10317,10318,10319,10320,10321,10322,10323,10324,10325,10326,10327,10328,10329,
		10330,10331,10332,10333,10334,10335,10336,10337,10338,10339,10340,10341,10342,10343,10344,10345,10346,10347,10348,10349,
		10350,10351,10352,10353,10354,10355,10356,10357,10358,10359,10360,10361,10362,10363,10364,10365,10366,10367,10368,10369,
		10370,10371,10372,10373,10374,10375,10376,10377,10378,10379,10380,10381,10382,10383,10384,10385,10386,10387,10388,10389,
		10390,10391,10392,10393,10394,10395,10396,10397,10398,10399,10400,10401,10402,10403,10404,10405,10406,10407,10408,10409,
		10410,10411,10412,10413,10414,10415,10416,10417,10418,10419,10420,10421,10422,10423,10424,10425,10426,10427,10428,10429,
		10430,10431,10432,10433,10434,10435,10436,10437,10438,10439,10440,10441,10442,10443,10444,10445,10446,10447,10448,10449,
		10450,10451,10452,10453,10454,10455,10456,10457,10458,10459,10460,10461,10462,10463,10464,10465,10466,10467,10468,10469,
		10470,10471,10472,10473,10474,10475,10476,10477,10478,10479,10480,10481,10482,10483,10484,10485,10486,10487,10488,10489,
		10072,10073,10074,10075,10076,10077,10078,10290,10291,10292,10080,10081,10082,10083,10084,10085,10086,10087,10088,10089,
		10090,10091,10092,10093,10094,10095,10096,10097,10098,10099,10100,10101,10102,10103,10104,10105,10106,10107,10108,10109,
		10110,10111,10112,10113,10114,10115,10116,10117,10118,10119,10120,10121,10122,10123,10124,10125,10126,10127,10128,10129,
		10130,10131,10132,10133,10134,10135,10136,10137,10138,10139,10140,10141,10142,10143,10144,10145,10146,10147,10148,10149,
		10150,10151,10152,10153,10154,10155,10156,10157,10158,10159,10160,10161,10162,10163,10164,10165,10166,10167,10168,10169,
		10170,10171,10172,10173,10174,10175,10176,10177,10178,10179,10180,10181,10182,10183,10184,10185,10186,10187,10188,10189,
		10190,10191,10192,10193,10194,10195,10196,10197,10198,10199,10200,10201,10202,10203,10204,10205,10206,10207,10208,10209,
		10210,10211,10212,10213,10214,10215,10216,10217,10218,10219,10220,10221,10222,10223,10224,10225,10226,10227,10228,10229,
		10230,10231,10232,10233,10234,10235,10236,10237,10238,10239,10240,10241,10242,10243,10244,10245,10246,10247,10248,10249,
		10250,10251,10252,10253,10254,10255,10256,10257,10258,10259,10260,10261,10262,10263,10264,10265,10266,10267,10268,10269,
		10270,10271,10272,10273,10274,10275,10276,10277,10278,10279,10280,10281,10282,10283,10284,10285,10286,10287,10288,10289,
		10503,10504,10505,10506,10507,10508,10509,9772,9773,9774,9775,9776,9777,9778,9779,10067,10068,10069,10070,10071,10072,
		9780,9781,9782,9783,9784,9785,9786,9787,9788,9789,9790,9791,9792,9793,9794,9795,9796,9797,9798,9799,9800,9801,9802,9803,
		9804,9805,9806,9807,9808,9809,9810,9811,9812,9813,9814,9815,9816,9817,9818,9819,9820,9821,9822,9823,9824,9825,9826,9827,
		9828,9829,9830,9831,9832,9833,9834,9835,9836,9837,9838,9839,9840,9841,9842,9843,9844,9845,9846,9847,9848,9849,9850,9851,
		9852,9853,9854,9855,9856,9857,9858,9859,9860,9861,9862,9863,9864,9865,9866,9867,9868,9869,9870,9871,9872,9873,9874,9875,
		9876,9877,9878,9879,9880,9881,9882,9883,9884,9885,9886,9887,9888,9889,9890,9891,9892,9893,9894,9895,9896,9897,9898,9899,
		9900,9901,9902,9903,9904,9905,9906,9907,9908,9909,9910,9911,9912,9913,9914,9915,9916,9917,9918,9919,9920,9921,9922,9923,
		9924,9925,9926,9927,9928,9929,9930,9931,9932,9933,9934,9935,9936,9937,9938,9939,9940,9941,9942,9943,9944,9945,9946,9947,
		9948,9949,9950,9951,9952,9953,9954,9955,9956,9957,9958,9959,9960,9961,9962,9963,9964,9965,9966,9967,9968,9969,9970,9971,
		9972,9973,9974,9975,9976,9977,9978,9979,9980,9981,9982,9983,9984,9985,9986,9987,9988,9989,9990,9991,9992,9993,9994,9995,
		9996,9997,9998,9999,10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,
		10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,
		10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,
		10057,10058,10059,10060,10061,10062,10063,10064,10065,10066
		},/*其它(Other)@6+(+10*/

};

unsigned short getTvIdByBrandId(unsigned short brand,unsigned short id)
{
	return TV_info[brand-1][id];
}

unsigned short getTvBrandProductLenById(unsigned short id)
{
	return TV_info[id-1][0];
}
