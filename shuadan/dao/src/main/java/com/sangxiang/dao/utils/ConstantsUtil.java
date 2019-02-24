package com.sangxiang.dao.utils;

/**
 * 常量工具类
 *
 * @author zhangxq
 * @date 2018-03-18
 */
public final class ConstantsUtil {

    public interface LoopWork {
        // t_loop_work 1常规任务 2指派任务 3改善任务 4 巡店安排 5工作备忘
        public static final Integer TYPE_ONE = 1;
        public static final Integer TYPE_TWO = 2;
        public static final Integer TYPE_THREE = 3;
        public static final Integer TYPE_FOUR = 4;
        public static final Integer TYPE_FIVE = 5;

        // 1：未执行 2：已执行提交 3：执行过期 4: 已审核 5: 审核过期
        public static final Integer WORK_STATUS_1 = 1;
        public static final Integer WORK_STATUS_2 = 2;
        public static final Integer WORK_STATUS_3 = 3;
        public static final Integer WORK_STATUS_4 = 4;
        public static final Integer WORK_STATUS_5 = 5;
    }

    // 1: 待定维修日期， 2： 确定维修日期， 3： 过期， 4： 完成维修待审核， 5：完成审核
    public interface RepairWork {
        public static final int STATUS_1 = 1;
        public static final int STATUS_2 = 2;
        public static final int STATUS_3 = 3;
        public static final int STATUS_4 = 4;
        public static final int STATUS_5 = 5;
    }

    public interface WorkDataAppend {
        // t_work_data_append 1数值 2百分比 3货币
        public static final Integer DATA_TYPE_ONE = 1;
        public static final Integer DATA_TYPE_TWO = 2;
        public static final Integer DATA_TYPE_THREE = 3;
    }

    public interface WorkImgAppend {
        // t_work_img_append 1：执行者提交，2：审核者提交
        public static final Integer INPUT_TYPE_ONE = 1;
        public static final Integer INPUT_TYPE_TWO = 2;
    }

    /**
     * 分隔符
     */
    public interface Separator {
        // 图片
        public static final String SEPARATOR_IMGURL = ",";
        // 经纬度
        public static final String SEPARATOR_LOCATION = "|";
        // 时间戳
        public static final String SEPARATOR_DATE = ",";
    }

}
