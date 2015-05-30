#ifndef _AIR_COMPARE_
#define _AIR_COMPARE_

#define AIR_BRAND_MIDEA			1
#define AIR_BRAND_MIDEA_LEN		63

#define AIR_BRAND_TOSHIBA		2
#define AIR_BRAND_TOSHIBA_LEN	58

#define AIR_BRAND_HAIER			3
#define AIR_BRAND_HAIER_LEN		123

#define AIR_BRAND_GREE 			4
#define AIR_BRAND_GREE_LEN		126

unsigned short getAirCompareResult(unsigned char *data,unsigned short brand);
unsigned char stringToHex(unsigned char *data);

//由品牌及产品id得到数据库中对应的id
unsigned int getAirIdInDb(unsigned short brand, unsigned short id);

void testExchange(void);

#endif