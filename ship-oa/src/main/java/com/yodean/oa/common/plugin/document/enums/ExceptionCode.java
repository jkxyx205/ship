package com.yodean.oa.common.plugin.document.enums;

/**
 * Created by rick on 7/18/18.
 */
public enum ExceptionCode {
    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    BAD_REQUEST_ERROR(400, "请求出现错误"),
    CERTIFIED_ERROR(401, "没有提供认证信息"),
    AUTHORITY_ERROR(403, "请求的资源不允许访问"),
    NOT_FOUND_ERROR(404, "请求的内容不存在"),
    METHOD_REQUEST_ERROR(405, "请求的方法不允许使用"),
    SERVER_ERROR(500, "服务器错误"),
    MATERIAL_INCOMING_SERIAL_NOT_MATCH(40003, "设备序列号不一致"),
    MATERIAL_INVENTORY_NOT_ENOUGH(40000, "没有足够库存"),
    MATERIAL_INVENTORY_MOVING_SAME_STORAGE(40001, "出入库不能为同一个库位"),
    MATERIAL_INVENTORY_MOVING_EMPTY(40002, "移库物料列表不能为空"),

    VALIDATE_ERROR(50001, "验证错误"),
    TOKEN_ERROR(50002, "拦截token出错"),
    EXISTS_ERROR(60000, "纪录已经存在"),
    NULL_ERROR(40001, "不合法的参数ID"),
    FILE_NAME_DUPLICATE_ERROR(40003, "文件名不能重复"),
    FILE_NAME_EMPTY_ERROR(40004, "文件名不能空"),
    FILE_VIEW_ERROR(40005, "文件预览失败"),
    FILE_DOWNLOAD_ERROR(40006, "文件下载失败"),
    FILE_PREVIEW_ERROR(40008, "文件架不支持在线预览"),
    FILE_UPLOAD_ERROR(40004, "文件上传失败"),
    FILE_COPY_ERROR(40004, "不能将文件复制到自身或其子目录下"),
    FILE_MOVE_ERROR(40004, "不能将文件移动到自身或其子目录下"),

    UNIT_FORMAT_EXCEPTION(40007, "单位无法转换"),
    NUM_NOT_ENOUGH(80007, "没有足够的数量");

    private Integer code;

    private String message;

    ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
