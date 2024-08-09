package local.ateng.boot.common.config.gracefulResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feiniaojin.gracefulresponse.data.Response;
import com.feiniaojin.gracefulresponse.data.ResponseStatus;

import java.util.Collections;

/**
 * 类的模板注释
 *
 * @author 孔余
 * @since 2024-02-05 15:23
 */
public class CustomResponseImpl implements Response {

    private String code;

    //private String dateTime = DateUtil.now();

    private String msg;

    private Object data = Collections.EMPTY_MAP;

    @Override
    @JsonIgnore
    public ResponseStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(ResponseStatus statusLine) {
        this.code = statusLine.getCode();
        this.msg = statusLine.getMsg();
    }

    @Override
    @JsonIgnore
    public Object getPayload() {
        return null;
    }

    @Override
    public void setPayload(Object payload) {
        this.data = payload;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /*public String getDateTime() {
        return dateTime;
    }*/
}
