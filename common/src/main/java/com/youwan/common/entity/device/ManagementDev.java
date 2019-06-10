package com.youwan.common.entity.device;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "we_Management_Dev", uniqueConstraints = @UniqueConstraint(columnNames = "ip"))
@EntityListeners(AuditingEntityListener.class)
public class ManagementDev implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;
    //设备配置为DHCP模式
    private String isDHCPMod;
    private String gateway;//网关
    private String subnetMask;//子网掩码
    private String dns;

    private String ssId;//Wi-Fi名称，必传。不允许特殊字符
    private String pwd;//Wi-Fi密码，只允许数字、英文和英文字符；若Wi-Fi无密码，则传入空或者任意字符都

    private String companyName;//设备名称，默认显示为“请设置公司名称”，可传入自定义内容
    private Integer identifyDistance;//识别距离，默认0：无限制，1：0.5米以内，2：1米以内，3：1.5米以内，4：2米以内，5：3米以内，6：4米以内。一代设备设置0-6档均有效；二代设备若recRank设置为1，则识别距离可设置0-6档，若recRank设置为2或3，则识别距离仅可设置0-3档
    private Integer identifyScores;//识别分数，识别命中分数，默认为80，传入值务必在60-100之间
    private Integer saveIdentifyTime;//N秒钟只保存一条识别记录配置，用于配置N秒内多次识别只记录一次，默认为0：每次识别记录都会计入数据库
    private Integer ttsModType;//语音模式类型，默认1：不需要语音播报，2：播报名字，... 100：自定义
    private String ttsModContent;//语音播报模式自定义内容，模板中只允许{name}字段，字段格式固定；模板中只允许数字、英文、中文和“{”、“}”；内容长度限制32个字，请自行调整。例：{name}欢迎光临
    private Integer displayModType;//屏幕显示模式类型，默认1：显示名字，... 100：自定义
    private String displayModContent;//显示模式自定义内容，模板中只允许{name}字段，字段格式固定；模板中只允许数字、中英文、中英文符号和“{”、“}”；内容长度限制32个字，请自行调整。例：{name}，签到成功！
    private Integer comModType;//串口模式类型，默认1：开门，2：不输出，3：输出人员id，4：输出身份证/IC卡号idcardNum，... 100：自定义
    private String comModContent;//串口模式自定义输出内容，模板中只允许{id}、{idcardNum}字段，字段格式固定；模板中只允许英文和英文符号；内容长度限制64个字符，请自行调整。例（韦根输出）：#WG{idcardNum}#或#WG{id}#（其中idcardNum、id可配置的范围必须为1-65535，否则信号输出失效；若使用串口输出韦根信号，设备串口需外接定制的信号转换小板）
    private String wg;//韦根口输出韦根信号（非串口），目前只支持输出idcardNum或id，传参格式为#WG{idcardNum}#或#WG{id}#。一代设备只能通过自定义串口输出配置韦根输出，二代设备既可通过串口配置韦根输出，也可使用此参数配置韦根口输出韦根信号（目前只支持韦根26，idcardNum、id可配置的范围必须为1-65535，否则信号输出失效）
    private String slogan;//标语，大屏展示
    private String intro;//公司简介，大屏展示
    private Integer recStrangerType;//陌生人开关（是否进行陌生人识），默认1：不识别陌生人，2：识别陌生人
    private String recStrangerTimesThreshold;//设备判定某人为陌生人所需时间等级（陌生人开关打开情况下设置有效），默认3；1表示快速判定但精确率最低，随着数值增加，判定时间增加，精确度提高生人识），1：不识别陌生人，2：识别陌生人
    private String ttsModStrangerType;//陌生人语音模式类型（陌生人开关打开情况下设置有效）， 默 认1：不需要语音播报；2：陌生人警报；......100：自定义
    private String ttsModStrangerContent;//陌生人语音播报自定义内容，默认1：模板中只允许数字、英文和中文；2：内容长度限制32个字。例：注意陌生人
    private Integer multiplayerDetection;//多个人脸检测设置，默认1：检测多个人脸并进行识别，2：只检测多个人脸中最大的人脸，并进行识别
    private Integer recRank;//识别等级，1：识别速度最快，精确率最低；2：识别速度较快，精确率较高；3：识别速度较慢，精确度最高。此参数仅对二代设备有效，二代设备默认等级2；一代设备此参数无效，默认等级1


    private String callbackUrl;//设备回调地址
    private String url;//设备心跳
    private String urlImg;//自动拍照

    private Integer screenMode;//大屏显示模式接口

}