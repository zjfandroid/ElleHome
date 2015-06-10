#ifndef _INFRA_LIB_
#define _INFRA_LIB_

#define TYPE_AIR			1
#define TYPE_AIR_LEN		4

#define TYPE_TV				2
#define TYPE_TV_LEN			807

//根据传输进来的码值以及品牌
unsigned short getInfraCompareResult(unsigned char *data,unsigned char type,unsigned int brand);

//得到某个类型的所有品牌的长度
unsigned short getBrandLenByType(unsigned char type);
//得到某个类型的某个品牌的名字
unsigned short getBrandNameById(unsigned char type, unsigned short id, unsigned char language , char *name);
//得到某个品牌产品的长度
unsigned short getBrandProductLenById(unsigned char type, unsigned short id);

//得到空调的指令
unsigned short getAirControlData(unsigned char *data, unsigned short id, unsigned char temp, unsigned char airflowRate,
	unsigned char manualFlow, unsigned char autoFlow, unsigned char onoff, unsigned char key, unsigned char model);

//得到电视的指令
unsigned short getTvControlData(unsigned char *data,unsigned short id,unsigned short fun);

//转换学习到的数据
void exchangeLearnData(unsigned char *dataIn,unsigned char *dataOut);

	
	
#endif