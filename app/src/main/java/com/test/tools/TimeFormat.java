package com.test.tools;

import android.content.Context;


import com.test.chattest.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.jpush.android.Configs;


/**
 * Created by Ken on 2015/2/26.
 */
public class TimeFormat {

    private long mTimeStamp;
    private Context mContext;

    public TimeFormat(Context context, long timeStamp) {
        this.mContext = context;
        this.mTimeStamp = timeStamp;
    }

    //用于显示会话时间
    public String getTime() {
        long currentTime = Configs.getReportTime();
        Date date1 = new Date(currentTime);
        Date date2 = new Date(mTimeStamp);
        SimpleDateFormat format = new SimpleDateFormat(mContext.getString(R.string.time_format_hours),
                Locale.CHINA);
        SimpleDateFormat format1 = new SimpleDateFormat(mContext.getString(R.string.time_format_accuracy),
                Locale.CHINA);
        String date = format.format(mTimeStamp);
        int hour = Integer.parseInt(date.substring(0, 2));
        //今天
        if (date1.getDate() - date2.getDate() == 0) {
            if (hour < 6) {
                return mContext.getString(R.string.before_dawn) + " " + date;
            } else if (hour < 12) {
                return mContext.getString(R.string.morning) + " " + date;
            } else if (hour < 18) {
                return mContext.getString(R.string.afternoon) + " " + date;
            } else {
                return mContext.getString(R.string.night) + " " + date;
            }
            //昨天
        } else if (date1.getDate() - date2.getDate() == 1) {
            return mContext.getString(R.string.yesterday);
        } else if (date1.getDay() - date2.getDay() > 0) {
            switch (date2.getDay()) {
                case 1:
                    return mContext.getString(R.string.monday);
                case 2:
                    return mContext.getString(R.string.tuesday);
                case 3:
                    return mContext.getString(R.string.wednesday);
                case 4:
                    return mContext.getString(R.string.thursday);
                case 5:
                    return mContext.getString(R.string.friday);
                case 6:
                    return mContext.getString(R.string.saturday);
                default:
                    return mContext.getString(R.string.sunday);
            }
        } else if (date1.getYear() == date2.getYear()) {
            return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                    + mContext.getString(R.string.day);
        } else {
            return format1.format(mTimeStamp);
        }
    }

    //用于显示消息具体时间
    public String getDetailTime() {
        long currentTime = Configs.getReportTime();
        Date date1 = new Date(currentTime);
        Date date2 = new Date(mTimeStamp);
        SimpleDateFormat format = new SimpleDateFormat(mContext.getString(R.string.time_format_hours),
                Locale.CHINA);
        String date = format.format(mTimeStamp);
        SimpleDateFormat format1 = new SimpleDateFormat(mContext.getString(R.string.time_format_year_month_day),
                Locale.CHINA);
        String date3 = format1.format(mTimeStamp);
        int hour = Integer.parseInt(date.substring(0, 2));
        if (date1.getDate() - date2.getDate() == 0) {
            if (hour < 6) {
                return mContext.getString(R.string.before_dawn) + date;
            } else if (hour < 12) {
                return mContext.getString(R.string.morning) + date;
            } else if (hour < 18) {
                return mContext.getString(R.string.afternoon) + date;
            } else {
                return mContext.getString(R.string.night) + date;
            }
        } else if (date1.getDate() - date2.getDate() == 1) {
            if (hour < 6) {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.before_dawn) + date;
            } else if (hour < 12) {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.morning) + date;
            } else if (hour < 18) {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.afternoon) + date;
            } else {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.night) + date;
            }
        } else if (date1.getYear() == date2.getYear()) {
            if (hour < 6) {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.before_dawn) + date;
            } else if (hour < 12) {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.morning) + date;
            } else if (hour < 18) {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.afternoon) + date;
            } else {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.night) + date;
            }
        } else if (hour < 6) {
            return date3 + " " + mContext.getString(R.string.before_dawn) + date;
        } else if (hour < 12) {
            return date3 + " " + mContext.getString(R.string.morning) + date;
        } else if (hour < 18) {
            return date3 + " " + mContext.getString(R.string.afternoon) + date;
        } else {
            return date3 + " " + mContext.getString(R.string.night) + date;
        }
    }
}
