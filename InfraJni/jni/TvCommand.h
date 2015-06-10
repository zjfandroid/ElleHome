#ifndef _TV_COMMAND_
#define _TV_COMMAND_

#define TvFunVolCut				0x01
#define TvFunChPlus				0x02
#define TvFunMenu				0x03
#define TvFunChCut				0x04
#define TvFunVolPlus			0x05
#define TvFunPower				0x06
#define TvFunMute				0x07		//静音
#define TvFun1					0x08
#define TvFun2					0x09
#define TvFun3					0x0a
#define TvFun4					0x0b
#define TvFun5					0x0c
#define TvFun6					0x0d
#define TvFun7					0x0e
#define TvFun8					0x0f
#define TvFun9					0x10
#define TvFunNum				0x11
#define TvFun0					0x12
#define TvFunTVAV				0x13
#define TvFunBack				0x14
#define TvFunSure				0x15
#define TvFunUp					0x16
#define TvFunLeft				0x17
#define TvFunRight				0x18
#define TvFunDown				0x19



unsigned short getTvCommand(unsigned char *data,unsigned short id,unsigned short fun);

#endif