package com.stats.daqing.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class ColumnsBean implements Parcelable {


    /**
     * counts : 3
     * totalPage : 1
     * pageSize : 20
     * currentPage : 1
     * columnsList : [{"columnIco":"http://202.97.194.240:9080/upload/1496314390839.jpg","columnName":"test1","createTime":1496314393000,"createUser":"18210281168","id":4,"isShow":0},{"columnIco":"","columnName":"数据解读","createTime":1495986391000,"createUser":"admin","id":2,"isShow":0},{"columnIco":"","columnName":"数据发布","createTime":1495986371000,"createUser":"admin","id":1,"isShow":0}]
     */

    private int counts;
    private int totalPage;
    private int pageSize;
    private int currentPage;
    private List<ColumnsListBean> columnsList;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<ColumnsListBean> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<ColumnsListBean> columnsList) {
        this.columnsList = columnsList;
    }

    public static class ColumnsListBean implements Parcelable {
        /**
         * columnIco : http://202.97.194.240:9080/upload/1496314390839.jpg
         * columnName : test1
         * createTime : 1496314393000
         * createUser : 18210281168
         * id : 4
         * isShow : 0
         */

        private String columnIco;
        private String columnName;
        private long createTime;
        private String createUser;
        private int id;
        /** 0:显示    1:不显示 **/
        private int isShow;

        public String getColumnIco() {
            return columnIco;
        }

        public void setColumnIco(String columnIco) {
            this.columnIco = columnIco;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.columnIco);
            dest.writeString(this.columnName);
            dest.writeLong(this.createTime);
            dest.writeString(this.createUser);
            dest.writeInt(this.id);
            dest.writeInt(this.isShow);
        }

        public ColumnsListBean() {
        }

        protected ColumnsListBean(Parcel in) {
            this.columnIco = in.readString();
            this.columnName = in.readString();
            this.createTime = in.readLong();
            this.createUser = in.readString();
            this.id = in.readInt();
            this.isShow = in.readInt();
        }

        public static final Creator<ColumnsListBean> CREATOR = new Creator<ColumnsListBean>() {
            @Override
            public ColumnsListBean createFromParcel(Parcel source) {
                return new ColumnsListBean(source);
            }

            @Override
            public ColumnsListBean[] newArray(int size) {
                return new ColumnsListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.counts);
        dest.writeInt(this.totalPage);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.currentPage);
        dest.writeList(this.columnsList);
    }

    public ColumnsBean() {
    }

    protected ColumnsBean(Parcel in) {
        this.counts = in.readInt();
        this.totalPage = in.readInt();
        this.pageSize = in.readInt();
        this.currentPage = in.readInt();
        this.columnsList = new ArrayList<ColumnsListBean>();
        in.readList(this.columnsList, ColumnsListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ColumnsBean> CREATOR = new Parcelable.Creator<ColumnsBean>() {
        @Override
        public ColumnsBean createFromParcel(Parcel source) {
            return new ColumnsBean(source);
        }

        @Override
        public ColumnsBean[] newArray(int size) {
            return new ColumnsBean[size];
        }
    };
}
