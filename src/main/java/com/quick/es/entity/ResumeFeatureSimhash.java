package com.quick.es.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author vector
 * @since 2019-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResumeFeatureSimhash implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String resumeId;

    private String realName;

    private String lastName;

    private String school;

    private String pinyinName;

    /**
     * 姓名simhash
     */
    private Long realNameSim;

    private Long pinyinNameSim;

    private Long companySim;

    private Long majorSim;

    private Long schoolSim;

    private Long titleSim;

    /**
     * 工作开始时间(第一段工作经历起始时间)
     */
    private LocalDateTime workStartDate;

    /**
     * 教育开始时间(第一段教育经历开始时间)
     */
    private LocalDateTime eduStartDate;

    private LocalDateTime eduEndDate;

    private String companyListSim;

    /**
     * 用户ID
     */
    private String unionId;

    /**
     * 公司ID
     */
    private String firmId;

    private LocalDateTime created;

    private LocalDateTime updated;


}
